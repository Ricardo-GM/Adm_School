package com.example.adm_school;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RegisterActivity extends AppCompatActivity {
    private Spinner spineer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spineer1 = (Spinner) findViewById(R.id.spinner);

        String [] opciones = {"CORDINADOR", "DIRECTOR", "PROFESOR", "ALUMNO"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,opciones);
        spineer1.setAdapter(adapter);
    }
}