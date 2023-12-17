package com.example.sistemadeventasdeproductos.venta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.models.Venta;
import com.example.sistemadeventasdeproductos.api.services.VentaService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Importaciones para la generación de PDF
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import android.util.Log;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.widget.Button;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import com.example.sistemadeventasdeproductos.api.models.Cliente;
import android.net.Uri;
import androidx.core.content.FileProvider;



public class VentaActivity extends AppCompatActivity {

    private RecyclerView rvVenta;
    private AdapterVentas adapterVentas;
    private FloatingActionButton fabNuevaVenta;

    private Button btnGenerarPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);

        // Verificar y solicitar permisos si es necesario
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        rvVenta = findViewById(R.id.rvListadoVentas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvVenta.setLayoutManager(layoutManager);
        String filtro = Objects.requireNonNull(getIntent().getExtras()).getString("consulta");
        cargarVentas(filtro);

        fabNuevaVenta=findViewById(R.id.fabNuevaVenta);
        fabNuevaVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newVentaIntent = new Intent(VentaActivity.this, NewVentaActivity.class);
                startActivity(newVentaIntent);
                finish();
            }
        });

        btnGenerarPDF = findViewById(R.id.btnGenerarPDF);
        btnGenerarPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener todas las ventas (puedes ajustar esto según tus necesidades)
                List<Venta> todasLasVentas = adapterVentas.getListaVentas();
                if (!todasLasVentas.isEmpty()) {
                    generarFacturaPdf(todasLasVentas);
                } else {
                    Toast.makeText(VentaActivity.this, "No hay ventas para generar la factura", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para enviar correo electrónico con archivo adjunto
    private void enviarCorreoConAdjunto(List<Venta> listaVentas) {
        // Iterar sobre todas las ventas
        for (Venta venta : listaVentas) {
            // Acceder a la información del cliente asociado a la venta
            Cliente cliente = venta.getCliente();

            // Verificar si el cliente tiene una dirección de correo electrónico
            if (cliente != null && cliente.getEmail() != null && !cliente.getEmail().isEmpty()) {
                String emailCliente = cliente.getEmail();

                // Crear el intent para enviar correo electrónico
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailCliente});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Asunto del Correo");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Cuerpo del Correo");

                // Adjuntar el archivo PDF
                Uri fileUri = obtenerUriArchivo(venta);
                emailIntent.putExtra(Intent.EXTRA_STREAM, fileUri);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Enviar correo..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "No hay clientes de correo instalados.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "El cliente no tiene una dirección de correo válida.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Obtener Uri del archivo para versiones de Android 7.0 y superiores
    private Uri obtenerUriArchivo(Venta venta) {
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/Factura_" + venta.getId() + ".pdf";
        File file = new File(filePath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(this, "com.example.sistemadeventasdeproductos.fileprovider", file);
        } else {
            return Uri.fromFile(file);
        }
    }

    public void cargarVentas(String filtro) {

        VentaService ventaService = VentaService.getInstance();
        List<Venta> listaVentas = new ArrayList<>();

        if (filtro.equals("")) {
            listaVentas = ventaService.obtenerVentas();
        } else {
            String[] filtroSplit = filtro.split("/");
            listaVentas = ventaService.ventasByCliente(Integer.parseInt(filtroSplit[1]));
        }

        adapterVentas = new AdapterVentas(listaVentas);
        rvVenta.setAdapter(adapterVentas);

    }

    public void eliminarVenta(View view) {

        VentaService ventaService = VentaService.getInstance();
        TextView id = ((RelativeLayout) view.getParent()).findViewById(R.id.txtId);
        int idVenta = Integer.parseInt( id.getText().toString() );

        ventaService.eliminarVentaById( idVenta );

        Intent newVentaIntent = new Intent(this, VentaActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("consulta","");
        newVentaIntent.putExtras(bundle);
        startActivity(newVentaIntent);
        finish();

    }

    public void generarFacturaPdf(List<Venta> listaVentas) {
        // Crear un nuevo documento PDF
        PdfDocument document = new PdfDocument();

        try {
            // Obtener el directorio de archivos específicos de la aplicación
            File directory = new File(getExternalFilesDir(null), "VentasPDF");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Iterar sobre todas las ventas
            for (Venta venta : listaVentas) {
                // Crear una página
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, document.getPages().size() + 1).create();
                PdfDocument.Page page = document.startPage(pageInfo);

                // Dibujar contenido en la página
                Canvas canvas = page.getCanvas();
                Paint paint = new Paint();

                // Título
                paint.setColor(Color.BLACK);
                paint.setTextSize(20);
                canvas.drawText("Factura", 100, 30, paint);

                // Contenido
                paint.setTextSize(14);
                canvas.drawText("Fecha: " + obtenerFechaActual(), 20, 60, paint);
                // Agregar más información de la factura según tus necesidades...

                // Finalizar la página
                document.finishPage(page);
            }

            // Guardar el documento en un archivo PDF
            String filePath = directory.getPath() + "/RegistroVentas.pdf";
            File file = new File(filePath);
            document.writeTo(new FileOutputStream(file));
            document.close();

            Log.d("GenerarFactura", "Registro de ventas generado y guardado en: " + filePath);

            // Llamada al método para enviar el correo con el adjunto
            enviarCorreoConAdjunto(listaVentas);

            // Muestra un mensaje al usuario
            Toast.makeText(this, "Registro de ventas generado y guardado en: " + filePath, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            // Muestra un mensaje de error al usuario
            Toast.makeText(this, "Error al generar el registro de ventas: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private String obtenerFechaActual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dateFormat.format(new Date());
    }
}