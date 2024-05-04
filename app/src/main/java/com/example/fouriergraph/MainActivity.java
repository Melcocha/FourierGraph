package com.example.fouriergraph;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.EditText;



public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText editTextOnda;
    private EditText editTextArmonico;
    private Button buttonGraficar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Splashtheme);
        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);


        spinner = findViewById(R.id.spinner);
        editTextOnda = findViewById(R.id.editTextOnda);
        editTextArmonico = findViewById(R.id.editTextArmonico);
        buttonGraficar = findViewById(R.id.buttonGraficar);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.opciones_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        buttonGraficar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de los campos de entrada
                String selectedOption = spinner.getSelectedItem().toString();
                String ondaValue = editTextOnda.getText().toString();
                String armonicoValue = editTextArmonico.getText().toString();

                // Verificar si los campos están completos
                if (selectedOption.isEmpty() || ondaValue.isEmpty() || armonicoValue.isEmpty()) {
                    // Mostrar un mensaje indicando que faltan datos
                    Toast.makeText(MainActivity.this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Si todos los campos están completos, abrir Graph.java
                    Intent intent = new Intent(MainActivity.this, Graph.class);
                    intent.putExtra("selectedOption", selectedOption);
                    intent.putExtra("ondaValue", ondaValue);
                    intent.putExtra("armonicoValue", armonicoValue);
                    startActivity(intent);
                }
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}