package com.example.adm_school.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.adm_school.R;
import com.github.florent37.shapeofview.shapes.RoundRectView;

public class menu_principal_coordinador extends AppCompatActivity {
    private RoundRectView btnSalones;
    private RoundRectView btnUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_coordinador);

        btnSalones = findViewById(R.id.gestion_salones);
        btnSalones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(menu_principal_coordinador.this, recycler_salones.class));
            }
        });

        btnUsuarios = findViewById(R.id.gestion_usuarios);
        btnUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(menu_principal_coordinador.this, recycler_usuarios.class));
            }
        });


    }
}