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


public class VentaActivity extends AppCompatActivity implements AdapterVenta.ItemClickListener{

    private RecyclerView rvVenta;
    private AdapterVenta adapterVenta;
    private FloatingActionButton fabNuevaVenta;

    private Button btnGenerarPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);

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
                // Obtener la venta seleccionada (debes adaptar esto según tu lógica)
                Venta ventaSeleccionada = adapterVenta.getVentaSeleccionada();
                if (ventaSeleccionada != null) {
                    generarFacturaPdf(ventaSeleccionada);
                } else {
                    Toast.makeText(VentaActivity.this, "Seleccione una venta para generar la factura", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onItemClick(Venta venta) {
        adapterVenta.setVentaSeleccionada(venta);
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

        adapterVenta = new AdapterVenta(listaVentas);
        rvVenta.setAdapter(adapterVenta);

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

    // Función para generar la factura en PDF
    public void generarFacturaPdf(Venta venta) {
        // Crear un nuevo documento PDF
        PdfDocument document = new PdfDocument();

        // Crear una página
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
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

        // Guardar el documento en un archivo PDF
        try {
            String filePath = Environment.getExternalStorageDirectory().getPath() + "/Factura_" + venta.getId() + ".pdf";
            File file = new File(filePath);
            document.writeTo(new FileOutputStream(file));
            document.close();

            Log.d("GenerarFactura", "Factura generada y guardada en: " + filePath);

            // Muestra un mensaje al usuario
            Toast.makeText(this, "Factura generada y guardada en: " + filePath, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            // Muestra un mensaje de error al usuario
            Toast.makeText(this, "Error al generar la factura", Toast.LENGTH_SHORT).show();
        }
    }

    private String obtenerFechaActual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dateFormat.format(new Date());
    }
}