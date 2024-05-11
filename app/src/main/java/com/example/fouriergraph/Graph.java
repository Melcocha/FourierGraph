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
                        ArrayList<double[]> puntosSenTransformada = generarOndaSinusoidalTransformada(ondaDouble, armonicoDouble);


                        for (double[] punto : puntosSen) {
                            System.out.println("Puntos de la onda sinusoidal original: Tiempo = " + punto[0] + ", Valor = " + punto[1]);
                        }

                        for (double[] punto : puntosSenTransformada) {
                            System.out.println("Puntos de la serie de Fourier para la onda sinusoidal: Tiempo = " + punto[0] + ", Valor = " + punto[1]);
                        }

                        break;


                    case "Onda coseno":
                        ArrayList<double[]> puntosCos = generarOndaCoseno(ondaDouble);
                        ArrayList<double[]> puntosCosTransformada = generarOndaCosTransformada(ondaDouble, armonicoDouble);


                        for (double[] punto : puntosCos) {
                            System.out.println("Puntos de la onda sinusoidal original: Tiempo = " + punto[0] + ", Valor = " + punto[1]);
                        }

                        for (double[] punto : puntosCosTransformada) {
                            System.out.println("Puntos de la serie de Fourier para la onda sinusoidal: Tiempo = " + punto[0] + ", Valor = " + punto[1]);
                        }
                        break;

                    case "Onda cuadrada":
                        ArrayList<double[]> puntosCuadrado = generarOndaCuadrada(ondaDouble);

                        break;

                    case "Onda rectangular":
                        ArrayList<double[]> puntosRectangular = generarOndaRectangular(ondaDouble);

                        break;

                    case "Forma de onda triangular":
                        ArrayList<double[]> puntosTriangular = generarOndaTriangular(ondaDouble);
                        ArrayList<double[]> puntosTriangularFourier = generarOndaTriangularFourier(ondaDouble, armonicoDouble);
                        for (double[] punto : puntosTriangular) {
                            System.out.println("Puntos de la onda Triangular original: Tiempo = " + punto[0] + ", Valor = " + punto[1]);
                        }

                        for (double[] punto : puntosTriangularFourier) {
                            System.out.println("Puntos de la serie de Fourier para la onda triangular: Tiempo = " + punto[0] + ", Valor = " + punto[1]);
                        }

                        break;

                    case "Forma de onda de diente de sierra":
                        ArrayList<double[]> puntosSierra = generarOndaDienteSierra(ondaDouble);
                        ArrayList<double[]> puntosSierraFourier = generarOndaDienteSierraFourier(ondaDouble,armonicoDouble);
                        for (double[] punto : puntosSierra) {
                            System.out.println("Puntos de la onda sierra original: Tiempo = " + punto[0] + ", Valor = " + punto[1]);
                        }

                        for (double[] punto : puntosSierraFourier) {
                            System.out.println("Puntos Sierra Fourier: Tiempo = " + punto[0] + ", Valor = " + punto[1]);
                        }
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
    private ArrayList<double[]> generarOndaSinusoidalTransformada(double periodo,double numArm) {
        double tiempoInicial = 0.0;
        double tiempoFinal = 2 * periodo;
        int numPuntos = 1000;
        double pasoTiempo = (2 * tiempoFinal - tiempoInicial) / numPuntos;

        ArrayList<double[]> puntos = new ArrayList<>();

        double[] tiempo = new double[numPuntos + 1];
        double[] ondaSinusoidal = new double[numPuntos + 1];
        for (int i = 0; i <= numPuntos; i++) {
            tiempo[i] = tiempoInicial + i * pasoTiempo;
            ondaSinusoidal[i] = Math.sin(2 * Math.PI * tiempo[i] / periodo);
        }

        double[] serieFourier = new double[numPuntos + 1];
        for (int i = 0; i <= numPuntos; i++) {
            double tiempoNormalizado = tiempo[i] % periodo;
            for (int n = 1; n <= numArm; n++) {
                serieFourier[i] += (4 / Math.PI) * (1.0 / n) * Math.sin(2 * Math.PI * (2 * n - 1) * tiempoNormalizado / periodo);
            }
        }

        for (int i = 0; i <= numPuntos; i++) {
            double[] punto = {tiempo[i], serieFourier[i]};
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
    private ArrayList<double[]> generarOndaCosTransformada(double periodo,double numArm) {
        double tiempoInicial = 0.0;
        double tiempoFinal = 2 * periodo;
        int numPuntos = 1000;
        double pasoTiempo = (2 * tiempoFinal - tiempoInicial) / numPuntos;

        ArrayList<double[]> puntos = new ArrayList<>();

        double[] tiempo = new double[numPuntos + 1];
        double[] ondaSinusoidal = new double[numPuntos + 1];
        for (int i = 0; i <= numPuntos; i++) {
            tiempo[i] = tiempoInicial + i * pasoTiempo;
            ondaSinusoidal[i] = Math.cos(2 * Math.PI * tiempo[i] / periodo);
        }

        double[] serieFourier = new double[numPuntos + 1];
        for (int i = 0; i <= numPuntos; i++) {
            double tiempoNormalizado = tiempo[i] % periodo;
            for (int n = 1; n <= numArm; n++) {
                serieFourier[i] += (4 / Math.PI) * (1.0 / n) * Math.cos(2 * Math.PI * (2 * n - 1) * tiempoNormalizado / periodo);
            }
        }

        for (int i = 0; i <= numPuntos; i++) {
            double[] punto = {tiempo[i], serieFourier[i]};
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
    private static ArrayList<double[]> generarOndaDienteSierraFourier(double periodo, double armonico) {
        double tiempoInicial = 0.0;
        double tiempoFinal = 2 * periodo;
        int numPuntos = 1000;
        double pasoTiempo = (tiempoFinal - tiempoInicial) / numPuntos;

        ArrayList<double[]> puntos = new ArrayList<>();
        for (double tiempo = tiempoInicial; tiempo <= tiempoFinal; tiempo += pasoTiempo) {
            double valor = 0.0;
            double tiempoNormalizado = tiempo % periodo;
            // Calcular la serie de Fourier
            for (int n = 1; n <= armonico; n++) {
                valor += (2.0 / Math.PI) * (1.0 / n) * Math.sin(2.0 * Math.PI * n * tiempoNormalizado / periodo);
            }
            // Ajustar el valor al rango [0, 1]
            valor = (valor + 1) / 2;
            valor = -valor + 1;
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
        // Definir el rango de tiempo para la onda (por ejemplo, de 0 a 2π)
        double tiempoInicial = 0.0;
        double tiempoFinal = periodo;

        // Número de puntos para generar en la onda
        int numPuntos = 1000;

        // Calcular el paso de tiempo entre cada punto
        double pasoTiempo = (2 * tiempoFinal - tiempoInicial) / numPuntos;

        // Generar la onda triangular
        ArrayList<double[]> puntos = new ArrayList<>();
        for (double tiempo = tiempoInicial; tiempo <= 2 * tiempoFinal; tiempo += pasoTiempo) {
            double tiempoNormalizado = tiempo % periodo; // Normalizar el tiempo para un solo periodo
            double valor = (2 * tiempoNormalizado / periodo); // Generar onda triangular
            if (valor > 1) {
                valor = 2 - valor; // Invertir la parte superior de la onda para hacerla triangular
            }
            valor = valor * 2 - 1; // Escalar la onda para que oscile entre -1 y 1
            double[] punto = {tiempo, valor};
            puntos.add(punto);
        }
        return puntos;
    }
    private ArrayList<double[]> generarOndaTriangularFourier(double periodo, double armonico) {
        double tiempoInicial = 0.0;
        double tiempoFinal = periodo;
        int numPuntos = 1000;
        double pasoTiempo = (2 * tiempoFinal - tiempoInicial) / numPuntos;
        ArrayList<double[]> puntos = new ArrayList<>();
        double[] tiempo = new double[numPuntos + 1];
        for (int i = 0; i <= numPuntos; i++) {
            tiempo[i] = i * pasoTiempo;
        }
        double[] serieFourier = new double[numPuntos + 1];
        for (int i = 0; i <= numPuntos; i++) {
            double tiempoNormalizado = tiempo[i] % periodo; // Normalizar el tiempo para un solo periodo
            double valor = (2 * tiempoNormalizado / periodo); // Generar onda triangular
            if (valor > 1) {
                valor = 2 - valor; // Invertir la parte superior de la onda para hacerla triangular
            }
            // Escalar la onda para que oscile entre -1 y 1
            valor = valor * 2 - 1;
            // Generar la serie de Fourier para la onda triangular
            for (int n = 1; n <= armonico; n++) {
                serieFourier[i] += (8 / (Math.PI * Math.PI)) * (1.0 / (n * n)) * Math.sin(2 * Math.PI * (2 * n - 1) * tiempoNormalizado / periodo);
            }
        }
        // Agregar puntos de la serie de Fourier a la lista
        for (int i = 0; i <= numPuntos; i++) {
            double[] puntoFourier = {tiempo[i], serieFourier[i]};
            puntos.add(puntoFourier);
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
