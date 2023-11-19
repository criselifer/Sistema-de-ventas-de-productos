package com.example.sistemadeventasdeproductos.venta;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sistemadeventasdeproductos.R;
import com.example.sistemadeventasdeproductos.api.models.Cliente;
import com.example.sistemadeventasdeproductos.api.models.Venta;
import com.example.sistemadeventasdeproductos.api.services.VentaService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import android.widget.Button;


public class VentaActivity extends AppCompatActivity {

    private RecyclerView rvVenta;
    private AdapterVenta adapterVenta;
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
        } else {
            // Si los permisos ya están concedidos, carga las ventas
            cargarVentas(Objects.requireNonNull(getIntent().getExtras()).getString("consulta"));
        }

        rvVenta = findViewById(R.id.rvListadoVentas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvVenta.setLayoutManager(layoutManager);

        fabNuevaVenta = findViewById(R.id.fabNuevaVenta);
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
                List<Venta> todasLasVentas = adapterVenta.getListaVentas();
                if (!todasLasVentas.isEmpty()) {
                    generarFacturaPdfYEnviarCorreo(todasLasVentas);
                } else {
                    Toast.makeText(VentaActivity.this, "No hay ventas para generar la factura", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Verificar si la solicitud de permisos fue exitosa
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permisos concedidos, carga las ventas
            cargarVentas(Objects.requireNonNull(getIntent().getExtras()).getString("consulta"));
        } else {
            // Permiso denegado, puedes mostrar un mensaje al usuario si lo deseas
            Toast.makeText(this, "Permiso denegado. La aplicación puede no funcionar correctamente.", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarVentas(String filtro) {
        VentaService ventaService = VentaService.getInstance();
        List<Venta> listaVentas = new ArrayList<>();

        if (filtro.equals("")) {
            listaVentas = ventaService.obtenerVentas();
        } else {
            String[] filtroSplit = filtro.split("/");
            listaVentas = ventaService.ventasByCliente(Integer.parseInt(filtroSplit[1]));
        }

        adapterVenta = new AdapterVenta(listaVentas);
        rvVenta.setAdapter(adapterVenta);
    }

    private void generarFacturaPdfYEnviarCorreo(List<Venta> listaVentas) {
        // Verificar los permisos nuevamente (en caso de que el usuario los haya denegado en onRequestPermissionsResult)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Permisos concedidos, procede con la generación de PDF y envío de correos
            generarFacturaPdf(listaVentas);
        } else {
            // Permiso denegado, puedes mostrar un mensaje al usuario si lo deseas
            Toast.makeText(this, "Permiso denegado. La aplicación puede no funcionar correctamente.", Toast.LENGTH_SHORT).show();
        }
    }

    private void generarFacturaPdf(List<Venta> listaVentas) {
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
            enviarCorreoConAdjunto(listaVentas, file);

            // Muestra un mensaje al usuario
            Toast.makeText(this, "Registro de ventas generado y guardado en: " + filePath, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            // Muestra un mensaje de error al usuario
            Toast.makeText(this, "Error al generar el registro de ventas: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void enviarCorreoConAdjunto(List<Venta> listaVentas, File archivoAdjunto) {
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
                Uri fileUri = obtenerUriArchivo(archivoAdjunto);
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

    private Uri obtenerUriArchivo(File archivoAdjunto) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(getApplicationContext(), "com.example.sistemadeventasdeproductos.fileprovider", archivoAdjunto);
        } else {
            return Uri.fromFile(archivoAdjunto);
        }
    }

    private String obtenerFechaActual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dateFormat.format(new Date());
    }
}