package com.example.fouriergraph;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.lang.Math;
import java.util.ArrayList;

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

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                String selectedOption = extras.getString("selectedOption");
                String ondaValue = extras.getString("ondaValue");
                String armonicoValue = extras.getString("armonicoValue");

                double ondaDouble = Double.parseDouble(ondaValue);
                double armonicoDouble = Double.parseDouble(armonicoValue);


                switch (selectedOption) {
                    case "Onda sinusoidal":
                        ArrayList<double[]> puntosSen = generarOndaSinusoidal(ondaDouble);

                        break;


                    case "Onda coseno":
                        ArrayList<double[]> puntosCos = generarOndaCoseno(ondaDouble);

                        break;

                    case "Onda cuadrada":
                        ArrayList<double[]> puntosCuadrado = generarOndaCuadrada(ondaDouble);

                        break;

                    case "Onda rectangular":
                        ArrayList<double[]> puntosRectangular = generarOndaRectangular(ondaDouble);

                        break;

                    case "Forma de onda triangular":
                        ArrayList<double[]> puntosTriangular = generarOndaTriangular(ondaDouble);

                        break;

                    case "Forma de onda de diente de sierra":
                        ArrayList<double[]> puntosSierra = generarOndaDienteSierra(ondaDouble);

                        break;


                    case "Forma de onda de pulso o tren de pulso":
                        ArrayList<double[]> puntosTrenPulso = generarOndaTrenPulsos(ondaDouble);

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

    private ArrayList<double[]> generarOndaSinusoidal(double periodo) {
        double tiempoInicial = 0.0;
        double tiempoFinal = periodo;
        int numPuntos = 1000;
        double pasoTiempo = (2 * tiempoFinal - tiempoInicial) / numPuntos;

        ArrayList<double[]> puntos = new ArrayList<>();

        for (double tiempo = tiempoInicial; tiempo <= 2 * tiempoFinal; tiempo += pasoTiempo) {
            double valor = Math.sin((2 * Math.PI * tiempo) / periodo);
            double[] punto = {tiempo, valor};
            puntos.add(punto);
        }
        return puntos;
    }

    private ArrayList<double[]> generarOndaCoseno(double periodo) {
        double tiempoInicial = 0.0;
        double tiempoFinal = periodo;
        int numPuntos = 100;
        double pasoTiempo = (2 * tiempoFinal - tiempoInicial) / numPuntos;


        ArrayList<double[]> puntos = new ArrayList<>();


        for (double tiempo = tiempoInicial; tiempo <= 2 * tiempoFinal; tiempo += pasoTiempo) {
            double valor = Math.cos((2 * Math.PI * tiempo) / periodo);
            double[] punto = {tiempo, valor};
            puntos.add(punto);
        }


        return puntos;
    }

    private ArrayList<double[]> generarOndaCuadrada(double periodo) {
        double tiempoInicial = 0.0;
        double tiempoFinal = 2 * periodo; // Rango de tiempo para dos periodos
        int numPuntos = 1000;
        double pasoTiempo = (tiempoFinal - tiempoInicial) / numPuntos;

        ArrayList<double[]> puntos = new ArrayList<>();

        for (double tiempo = tiempoInicial; tiempo <= tiempoFinal; tiempo += pasoTiempo) {
            double valor;
            double tiempoNormalizado = tiempo % periodo; // Normalizar el tiempo para un solo periodo
            if (tiempoNormalizado < periodo / 2) {
                valor = 1.0;
            } else {
                valor = -1.0;
            }
            double[] punto = {tiempo, valor};
            puntos.add(punto);
        }

        return puntos;
    }

    private ArrayList<double[]> generarOndaRectangular(double periodo) {
        double tiempoInicial = 0.0;
        double tiempoFinal = 2 * periodo;
        int numPuntos = 1000;
        double pasoTiempo = (tiempoFinal - tiempoInicial) / numPuntos;

        ArrayList<double[]> puntos = new ArrayList<>();

        for (double tiempo = tiempoInicial; tiempo <= tiempoFinal; tiempo += pasoTiempo) {
            double valor;
            double tiempoNormalizado = tiempo % periodo;
            if (tiempoNormalizado < periodo / 4) {
                valor = 1.0;
            } else {
                valor = -1.0;
            }
            double[] punto = {tiempo, valor};
            puntos.add(punto);
        }

        return puntos;
    }

    private ArrayList<double[]> generarOndaTriangular(double periodo) {
        double tiempoInicial = 0.0;
        double tiempoFinal = 2 * periodo;
        int numPuntos = 1000;
        double pasoTiempo = (tiempoFinal - tiempoInicial) / numPuntos;


        ArrayList<double[]> puntos = new ArrayList<>();
        for (double tiempo = tiempoInicial; tiempo <= tiempoFinal; tiempo += pasoTiempo) {
            double tiempoNormalizado = tiempo % periodo;
            double valor;
            if (tiempoNormalizado < periodo / 2) {
                valor = (2 * tiempoNormalizado / periodo);
            } else {
                valor = (-2 * tiempoNormalizado / periodo) + 2;
            }
            double[] punto = {tiempo, valor};
            puntos.add(punto);
        }

        return puntos;
    }

    private ArrayList<double[]> generarOndaDienteSierra(double periodo) {
        double tiempoInicial = 0.0;
        double tiempoFinal = 2 * periodo;
        int numPuntos = 1000;
        double pasoTiempo = (tiempoFinal - tiempoInicial) / numPuntos;

        ArrayList<double[]> puntos = new ArrayList<>();
        for (double tiempo = tiempoInicial; tiempo <= tiempoFinal; tiempo += pasoTiempo) {
            double tiempoNormalizado = tiempo % periodo;
            double valor = (2 * tiempoNormalizado / periodo) - 1;
            // Ajusta el valor al rango [0, 1]
            valor = (valor + 1) / 2;
            double[] punto = {tiempo, valor};
            puntos.add(punto);
        }

        return puntos;
    }

    private ArrayList<double[]> generarOndaTrenPulsos(double periodo) {
        double tiempoInicial = 0.0;
        double tiempoFinal = 2 * periodo;
        int numPuntos = 1000;
        double pasoTiempo = (tiempoFinal - tiempoInicial) / numPuntos;

        ArrayList<double[]> puntos = new ArrayList<>();
        for (double tiempo = tiempoInicial; tiempo <= tiempoFinal; tiempo += pasoTiempo) {
            double tiempoNormalizado = tiempo % periodo;

            double duracionPulso = periodo / 2.0;
            double valor;

            if (tiempoNormalizado < duracionPulso) {
                valor = 1.0;
            } else {
                valor = 0.0;
            }
            double[] punto = {tiempo, valor};
            puntos.add(punto);
        }

        return puntos;
    }


}
