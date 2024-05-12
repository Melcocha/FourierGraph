package com.example.fouriergraph;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.graphics.Bitmap;
import android.widget.ImageView;
import com.example.fouriergraph.GraphView;
import java.util.ArrayList;
import java.lang.Math;
import android.view.ViewGroup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
                        ArrayList<double[]> puntosSen = generarOndaSinusoidalRectificada(ondaDouble);
                        ArrayList<double[]> puntosSenTransformada = generarOndaSenoRectificadaFourier(ondaDouble, armonicoDouble);

                        Log.i("Onda sinusoidal", "Valores de la onda sinusoidal rectificada:");
                        for (double[] punto : puntosSen) {
                            Log.i("Onda sinusoidal", "Tiempo: " + punto[0] + ", Valor: " + punto[1]);
                        }

                        Log.i("Onda sinusoidal", "Valores de la transformada de Fourier de la onda sinusoidal rectificada:");
                        for (double[] punto : puntosSenTransformada) {
                            Log.i("Onda sinusoidal", "Tiempo: " + punto[0] + ", Valor: " + punto[1]);
                        }
                        break;



                    case "Onda coseno":
                        ArrayList<double[]> puntosCos = generarOndaCosenoidalRectificada(ondaDouble);
                        ArrayList<double[]> puntosCosTransformada = generarOndaCosenoFourier(ondaDouble, armonicoDouble);
                        Graficar(puntosCos, puntosCosTransformada);

                        break;

                    case "Onda cuadrada":
                        ArrayList<double[]> puntosCuadrado = generarOndaCuadrada(ondaDouble);
                        ArrayList<double[]> puntosCuadradoFourier = generarOndaCuadradaFourier(ondaDouble, armonicoDouble);
                        Graficar(puntosCuadrado, puntosCuadradoFourier);

                        break;

                    case "Onda rectangular":
                        ArrayList<double[]> puntosRectangular = generarOndaRectangular(ondaDouble);
                        ArrayList<double[]> puntosRectangularFourier = generarOndaRectangularFourier(ondaDouble, armonicoDouble);

                        break;

                    case "Forma de onda triangular":
                        ArrayList<double[]> puntosTriangular = generarOndaTriangular(ondaDouble);
                        ArrayList<double[]> puntosTriangularFourier = generarOndaTriangularFourier(ondaDouble, armonicoDouble);


                        break;

                    case "Forma de onda de diente de sierra":
                        ArrayList<double[]> puntosSierra = generarOndaDienteSierra(ondaDouble);
                        ArrayList<double[]> puntosSierraFourier = generarOndaDienteSierraFourier(ondaDouble, armonicoDouble);

                        break;


                    case "Forma de onda de pulso o tren de pulso":
                        ArrayList<double[]> puntosTrenPulso = generarOndaTrenPulsos(ondaDouble);
                        ArrayList<double[]> puntosTrenPulsoFourier = generarOndaTrenPulsosFourier(ondaDouble, armonicoDouble);


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


    private ArrayList<double[]> generarOndaSinusoidalRectificada(double periodo) {
        double tiempoInicial = 0.0;
        double tiempoFinal = periodo;
        int numPuntos = 1000;
        double pasoTiempo = (2 * tiempoFinal - tiempoInicial) / numPuntos;

        ArrayList<double[]> puntos = new ArrayList<>();

        for (double tiempo = tiempoInicial; tiempo <= 2 * tiempoFinal; tiempo += pasoTiempo) {
            double valor = Math.abs(Math.sin((2 * Math.PI * tiempo) / periodo));
            double[] punto = {tiempo, valor};
            puntos.add(punto);
        }
        return puntos;
    }


    private ArrayList<double[]> generarOndaSenoRectificadaFourier(double periodo, double armonico) {
        int numPuntos = 1000;
        double pasoTiempo = (2 * periodo) / numPuntos;

        ArrayList<double[]> puntos = new ArrayList<>();

        double[] tiempo = new double[numPuntos + 1];
        for (int i = 0; i <= numPuntos; i++) {
            tiempo[i] = i * pasoTiempo;
        }

        double[] original_wave = new double[numPuntos];
        for (int i = 0; i < numPuntos; i++) {
            double x = i * pasoTiempo;
            if (x % periodo <= periodo / 2) {
                original_wave[i] = Math.sin((2 * Math.PI * x) / periodo);
            } else {
                original_wave[i] = 0;
            }
        }

        double[] fourier_series = new double[numPuntos];
        double paso = (2 * periodo) / (numPuntos - 1);
        for (int i = 0; i < numPuntos; i++) {
            double x = i * paso;
            double suma = 0;
            for (int n = 1; n <= armonico; n++) {
                double an = 0;
                double bn = 0;
                for (int k = 0; k < numPuntos; k++) {
                    double t = k * paso;
                    if (n % 2 != 0) {
                        an += original_wave[k] * Math.cos((2 * Math.PI * n * t) / periodo) * paso;
                    }
                    bn += original_wave[k] * Math.sin((2 * Math.PI * n * t) / periodo) * paso;
                }
                suma += an * Math.cos((2 * Math.PI * n * x) / periodo) + bn * Math.sin((2 * Math.PI * n * x) / periodo);
            }
            fourier_series[i] = suma / armonico;
        }


        for (int i = 0; i < numPuntos; i++) {
            double[] puntoFourier = {tiempo[i], fourier_series[i]*2};
            puntos.add(puntoFourier);
        }

        return puntos;
    }





    private ArrayList<double[]> generarOndaCosenoidalRectificada(double periodo) {
        double tiempoInicial = 0.0;
        double tiempoFinal = periodo;
        int numPuntos = 1000;
        double pasoTiempo = (2 * tiempoFinal - tiempoInicial) / numPuntos;

        ArrayList<double[]> puntos = new ArrayList<>();

        for (double tiempo = tiempoInicial; tiempo <= 2 * tiempoFinal; tiempo += pasoTiempo) {
            double valor = Math.abs(Math.cos((2 * Math.PI * tiempo) / periodo));
            double[] punto = {tiempo, valor};
            puntos.add(punto);
        }
        return puntos;
    }


    private ArrayList<double[]> generarOndaCosenoFourier(double periodo, double armonico) {
        int numPuntos = 1000;
        double pasoTiempo = (2 * periodo) / numPuntos;

        ArrayList<double[]> puntos = new ArrayList<>();

        double[] tiempo = new double[numPuntos + 1];
        for (int i = 0; i <= numPuntos; i++) {
            tiempo[i] = i * pasoTiempo;
        }

        double[] original_wave = new double[numPuntos];
        for (int i = 0; i < numPuntos; i++) {
            double x = i * pasoTiempo;
            if (x % periodo <= periodo / 2) {
                original_wave[i] = Math.cos((2 * Math.PI * x) / periodo);
            } else {
                original_wave[i] = 0;
            }
        }

        double[] fourier_series = new double[numPuntos];
        double paso = (2 * periodo) / (numPuntos - 1);
        for (int i = 0; i < numPuntos; i++) {
            double x = i * paso;
            double suma = 0;
            for (int n = 1; n <= armonico; n++) {
                double an = 0;
                double bn = 0;
                for (int k = 0; k < numPuntos; k++) {
                    double t = k * paso;
                    if (n % 2 != 0) {
                        an += original_wave[k] * Math.cos((2 * Math.PI * n * t) / periodo) * paso;
                    }
                    bn += original_wave[k] * Math.sin((2 * Math.PI * n * t) / periodo) * paso;
                }
                suma += an * Math.cos((2 * Math.PI * n * x) / periodo) + bn * Math.sin((2 * Math.PI * n * x) / periodo);
            }
            fourier_series[i] = suma / armonico;
        }


        for (int i = 0; i < numPuntos; i++) {
            double[] puntoFourier = {tiempo[i], fourier_series[i]*2};
            puntos.add(puntoFourier);
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

            valor = (valor + 1) / 2;
            double[] punto = {tiempo, valor};
            puntos.add(punto);
        }

        return puntos;
    }

    @NonNull
    private static ArrayList<double[]> generarOndaDienteSierraFourier(double periodo, double armonico) {
        double tiempoInicial = 0.0;
        double tiempoFinal = 2 * periodo;
        int numPuntos = 1000;
        double pasoTiempo = (tiempoFinal - tiempoInicial) / numPuntos;

        ArrayList<double[]> puntos = new ArrayList<>();
        for (double tiempo = tiempoInicial; tiempo <= tiempoFinal; tiempo += pasoTiempo) {
            double valor = 0.0;
            double tiempoNormalizado = tiempo % periodo;

            for (int n = 1; n <= armonico; n++) {
                valor += (2.0 / Math.PI) * (1.0 / n) * Math.sin(2.0 * Math.PI * n * tiempoNormalizado / periodo);
            }

            valor = (valor + 1) / 2;
            valor = -valor + 1;
            double[] punto = {tiempo, valor};
            puntos.add(punto);
        }

        return puntos;
    }


    @NonNull
    private ArrayList<double[]> generarOndaCuadrada(double periodo) {
        double tiempoInicial = 0.0;
        double tiempoFinal = 2 * periodo;
        int numPuntos = 1000;
        double pasoTiempo = (tiempoFinal - tiempoInicial) / numPuntos;

        ArrayList<double[]> puntos = new ArrayList<>();

        for (double tiempo = tiempoInicial; tiempo <= tiempoFinal; tiempo += pasoTiempo) {
            double valor;
            double tiempoNormalizado = tiempo % periodo;
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

    private ArrayList<double[]> generarOndaCuadradaFourier(double periodo, double armonico) {
        double tiempoInicial = 0.0;
        double tiempoFinal = 2 * periodo;
        int numPuntos = 1000;
        double pasoTiempo = (tiempoFinal - tiempoInicial) / numPuntos;
        ArrayList<double[]> puntos = new ArrayList<>();
        double[] tiempo = new double[numPuntos + 1];
        for (int i = 0; i <= numPuntos; i++) {
            tiempo[i] = i * pasoTiempo;
        }
        double[] serieFourier = new double[numPuntos + 1];
        for (int i = 0; i <= numPuntos; i++) {
            double tiempoNormalizado = tiempo[i] % periodo;
            for (int n = 1; n <= armonico; n++) {
                serieFourier[i] += (4 / Math.PI) * (1.0 / (2 * n - 1)) * Math.sin(2 * Math.PI * (2 * n - 1) * tiempoNormalizado / periodo);
            }
        }

        for (int i = 0; i <= numPuntos; i++) {
            double[] puntoFourier = {tiempo[i], serieFourier[i]};
            puntos.add(puntoFourier);
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

    private ArrayList<double[]> generarOndaRectangularFourier(double periodo, double armonico) {
        int numPuntos = 1000;
        double pasoTiempo = (2 * periodo) / numPuntos;

        ArrayList<double[]> puntos = new ArrayList<>();


        double[] tiempo = new double[numPuntos + 1];
        for (int i = 0; i <= numPuntos; i++) {
            tiempo[i] = i * pasoTiempo;
        }


        double[] original_wave = new double[numPuntos];
        for (int i = 0; i < numPuntos; i++) {
            double x = i * pasoTiempo;
            if (x % periodo <= periodo / 4) {
                original_wave[i] = 1;
            } else {
                original_wave[i] = -1;
            }
        }


        double[] fourier_series = new double[numPuntos];
        double paso = (2 * periodo) / (numPuntos - 1);
        for (int i = 0; i < numPuntos; i++) {
            double x = i * paso;
            double suma = 0;
            for (int n = 1; n <= armonico; n++) {
                double an = 0;
                double bn = 0;
                for (int k = 0; k < numPuntos; k++) {
                    double t = k * paso;
                    if (n % 2 != 0) {
                        an += original_wave[k] * Math.cos((2 * Math.PI * n * t) / periodo) * paso;
                    }
                    bn += original_wave[k] * Math.sin((2 * Math.PI * n * t) / periodo) * paso;
                }
                suma += an * Math.cos((2 * Math.PI * n * x) / periodo) + bn * Math.sin((2 * Math.PI * n * x) / periodo);
            }
            fourier_series[i] = suma / armonico;
        }


        for (int i = 0; i < numPuntos; i++) {
            double[] puntoFourier = {tiempo[i], fourier_series[i]};
            puntos.add(puntoFourier);
        }

        return puntos;
    }


    private ArrayList<double[]> generarOndaTriangular(double periodo) {

        double tiempoInicial = 0.0;
        double tiempoFinal = periodo;


        int numPuntos = 1000;


        double pasoTiempo = (2 * tiempoFinal - tiempoInicial) / numPuntos;

        // Generar la onda triangular
        ArrayList<double[]> puntos = new ArrayList<>();
        for (double tiempo = tiempoInicial; tiempo <= 2 * tiempoFinal; tiempo += pasoTiempo) {
            double tiempoNormalizado = tiempo % periodo;
            double valor = (2 * tiempoNormalizado / periodo);
            if (valor > 1) {
                valor = 2 - valor;
            }
            valor = valor * 2 - 1;
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
        double omega0 = 2 * Math.PI / periodo;
        for (int i = 0; i <= numPuntos; i++) {
            double tiempoNormalizado = tiempo[i] % periodo;

            double suma = 0;
            for (int n = 1; n <= armonico; n += 2) {
                suma += (8 / (Math.PI * Math.PI)) * (1.0 / (n * n)) * Math.cos(n * omega0 * tiempoNormalizado);
            }
            serieFourier[i] = suma;
        }

        double maxAmplitud = Math.max(Math.abs(serieFourier[0]), Math.abs(serieFourier[numPuntos]));
        for (int i = 0; i <= numPuntos; i++) {
            serieFourier[i] /= maxAmplitud;
        }


        for (int i = 0; i <= numPuntos; i++) {
            double[] puntoFourier = {tiempo[i], serieFourier[i] * -1};
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

    private ArrayList<double[]> generarOndaTrenPulsosFourier(double periodo, double armonico) {
        int numPuntos = 1000;
        double pasoTiempo = (2 * periodo) / numPuntos;

        ArrayList<double[]> puntos = new ArrayList<>();


        double[] tiempo = new double[numPuntos + 1];
        for (int i = 0; i <= numPuntos; i++) {
            tiempo[i] = i * pasoTiempo;
        }


        double[] original_wave = new double[numPuntos];
        for (int i = 0; i < numPuntos; i++) {
            double x = i * pasoTiempo;
            if (x % periodo <= periodo / 2) {
                original_wave[i] = 1;
            } else {
                original_wave[i] = 0;
            }
        }


        double[] fourier_series = new double[numPuntos];
        double paso = (2 * periodo) / (numPuntos - 1);
        for (int i = 0; i < numPuntos; i++) {
            double x = i * paso;
            double suma = 0;
            for (int n = 1; n <= armonico; n++) {
                double an = 0;
                double bn = 0;
                for (int k = 0; k < numPuntos; k++) {
                    double t = k * paso;
                    if (n % 2 != 0) {
                        an += original_wave[k] * Math.cos((2 * Math.PI * n * t) / periodo) * paso;
                    }
                    bn += original_wave[k] * Math.sin((2 * Math.PI * n * t) / periodo) * paso;
                }
                suma += an * Math.cos((2 * Math.PI * n * x) / periodo) + bn * Math.sin((2 * Math.PI * n * x) / periodo);
            }
            fourier_series[i] = suma / armonico;
        }


        for (int i = 0; i < numPuntos; i++) {
            double[] puntoFourier = {tiempo[i], fourier_series[i] + 0.5};
            puntos.add(puntoFourier);
        }

        return puntos;
    }

    public void Graficar(ArrayList<double[]> GraficaOriginal, ArrayList<double[]> GraficaFourier) {

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);


        ImageView imageView = findViewById(R.id.imageView);


        if (imageView != null) {

            int imageViewWidth = 383;
            int imageViewHeight = 518;


            Log.d("PRUEBA", "Ancho del ImageView: " + imageViewWidth);
            Log.d("PRUEBA", "Alto del ImageView: " + imageViewHeight);


            Log.d("PRUEBA", "Estableciendo dimensiones al ImageView");
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width = imageViewWidth;
            layoutParams.height = imageViewHeight;
            imageView.setLayoutParams(layoutParams);


            if (imageViewWidth > 0 && imageViewHeight > 0) {
                // Crear un nuevo Bitmap con las dimensiones del ImageView para dibujar la gráfica
                Log.d("PRUEBA", "Creando Bitmap con dimensiones del ImageView");
                Bitmap bitmap = Bitmap.createBitmap(imageViewWidth, imageViewHeight, Bitmap.Config.ARGB_8888);
                bitmap.setDensity(getResources().getDisplayMetrics().densityDpi);

                Canvas canvas = new Canvas(bitmap);


                Log.d("PRUEBA", "Dibujando gráfica original");
                for (int i = 0; i < GraficaOriginal.size() - 1; i++) {
                    double[] puntoInicio = GraficaOriginal.get(i);
                    double[] puntoFin = GraficaOriginal.get(i + 1);
                    Log.d("PRUEBA", "Dibujando línea de (" + puntoInicio[0] + ", " + puntoInicio[1] + ") a (" + puntoFin[0] + ", " + puntoFin[1] + ")");
                    canvas.drawLine((float) puntoInicio[0], (float) puntoInicio[1], (float) puntoFin[0], (float) puntoFin[1], paint);
                }


                paint.setColor(Color.BLUE);


                Log.d("PRUEBA", "Dibujando gráfica de Fourier");
                for (int i = 0; i < GraficaFourier.size() - 1; i++) {
                    double[] puntoInicio = GraficaFourier.get(i);
                    double[] puntoFin = GraficaFourier.get(i + 1);
                    Log.d("PRUEBA", "Dibujando línea de (" + puntoInicio[0] + ", " + puntoInicio[1] + ") a (" + puntoFin[0] + ", " + puntoFin[1] + ")");
                    canvas.drawLine((float) puntoInicio[0], (float) puntoInicio[1], (float) puntoFin[0], (float) puntoFin[1], paint);
                }


                Log.d("PRUEBA", "Asignando Bitmap al ImageView");
                imageView.setImageBitmap(bitmap);
            } else {
                Log.e("PRUEBA", "Ancho o alto del ImageView no válido.");
            }
        } else {
            Log.e("PRUEBA", "ImageView es nulo. No se puede establecer la imagen.");
        }
    }















}



