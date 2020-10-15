package com.example.adm_school.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.adm_school.R;

public class Info_Usuario extends AppCompatActivity {
    private EditText nombre;
    private EditText correo;
    private EditText password;
    private Button btnAsignarUsuario;
    private Spinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_usuario);

        nombre = findViewById(R.id.nombreUsuario);
        correo = findViewById(R.id.correo);
        password = findViewById(R.id.password);
        btnAsignarUsuario = findViewById(R.id.btnAsignarUsuario);
        spinner1 = findViewById(R.id.spinnerRol);

        //CREANDO EL ARRAY DEL SPINNER
        String [] opciones = {"Profesor","Alumno"};

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opciones);
        spinner1.setAdapter(adapter);

        final int id_usuario = getIntent().getIntExtra("id_usuario", 0);
        final String nombre = getIntent().getStringExtra("nombre");
        final String correo = getIntent().getStringExtra("correo");
        final String password = getIntent().getStringExtra("password");

        if (nombre != "") {
            this.nombre.setText(nombre);
        }

        if (correo != "") {
            this.correo.setText(correo);
        }

        if (password != "") {
            this.password.setText(password);
        }

    }
}