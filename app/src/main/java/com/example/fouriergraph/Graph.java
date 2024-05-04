package com.example.fouriergraph;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import java.lang.Math;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;



public class Graph extends AppCompatActivity {

    private static final String TAG = "Graph";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_graph);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener datos del Intent
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                String selectedOption = extras.getString("selectedOption");
                String ondaValue = extras.getString("ondaValue");
                String armonicoValue = extras.getString("armonicoValue");
                try {
                    double ondaDouble = Double.parseDouble(ondaValue);
                    double armonicoDouble = Double.parseDouble(armonicoValue);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }



                switch (selectedOption) {
                    case "Onda sinusoidal":

                        break;
                    case "Onda coseno":
                        // Realizar acciones para la onda coseno
                        break;
                    case "Onda cuadrada":
                        // Realizar acciones para la onda cuadrada
                        break;
                    case "Onda rectangular":
                        // Realizar acciones para la onda rectangular
                        break;
                    case "Forma de onda triangular":
                        // Realizar acciones para la forma de onda triangular
                        break;
                    case "Forma de onda de diente de sierra":
                        // Realizar acciones para la forma de onda de diente de sierra
                        break;
                    case "Forma de onda de pulso o tren de pulso":
                        // Realizar acciones para la forma de onda de pulso o tren de pulso
                        break;
                    default:
                        Log.d(TAG, "Tipo de onda desconocido");
                        break;
                }
            }
        }


    }

    public void irAInicio(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
