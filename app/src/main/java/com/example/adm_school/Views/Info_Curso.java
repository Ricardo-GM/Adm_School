package com.example.adm_school.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adm_school.Api.ApiClient;
import com.example.adm_school.Models.ActualizarCursoRequest;
import com.example.adm_school.Models.CursoRequest;
import com.example.adm_school.Models.CursoResponse;
import com.example.adm_school.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Info_Curso extends AppCompatActivity {
    private EditText nombreCurso;
    private EditText descripcionCurso;
    private EditText estadoCurso;
    private Button btnCrearCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_curso);

        nombreCurso = findViewById(R.id.nombreCurso);
        descripcionCurso = findViewById(R.id.descripcionCurso);
        estadoCurso = findViewById(R.id.estadoCurso);
        btnCrearCurso = findViewById(R.id.btnCrearCurso);

        final int actIdCurso = getIntent().getIntExtra("id_curso", 0);
        final String actNombreCurso = getIntent().getStringExtra("nombreCurso");
        final String actDescripcion = getIntent().getStringExtra("descripcionCurso");
        final String estado = getIntent().getStringExtra("estado");

        if( actNombreCurso != ""){
            nombreCurso.setText(actNombreCurso);
        }

        if(actDescripcion != ""){
            descripcionCurso.setText(actDescripcion);
        }

        if(estado != ""){
            estadoCurso.setText(estado);
        }

        btnCrearCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(nombreCurso.getText()) || TextUtils.isEmpty(descripcionCurso.getText()) || TextUtils.isEmpty(estadoCurso.getText())){
                    Toast.makeText(Info_Curso.this, "Debe completar todos los campos", Toast.LENGTH_LONG).show();
                }else{
                    if(actIdCurso != 0){
                        actualizarCurso(actIdCurso);
                    }else{
                        addCurso();
                    }
                }
            }
        });
    }

    public void addCurso(){
        CursoRequest cursoRequest = new CursoRequest();
        cursoRequest.setNombreCurso(nombreCurso.getText().toString());
        cursoRequest.setDescripcionCurso(descripcionCurso.getText().toString());
        cursoRequest.setEstado(estadoCurso.getText().toString());

        Call<CursoResponse> cursoResponseCall = ApiClient.getUserService().createCurso(cursoRequest);
        cursoResponseCall.enqueue(new Callback<CursoResponse>() {
            @Override
            public void onResponse(Call<CursoResponse> call, Response<CursoResponse> response) {
                if(response.isSuccessful()){
                    final CursoResponse cursoResponse = response.body();
                    if(cursoResponse.getEstado().equals("1")){
                        Toast.makeText(Info_Curso.this, "Curso Creado!", Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(Info_Curso.this, recycler_cursos.class));
                            }
                        }, 400);
                    }else{
                        Toast.makeText(Info_Curso.this, "La creación falló!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Info_Curso.this, "El servidor no responde correctamente!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CursoResponse> call, Throwable t) {
                Toast.makeText(Info_Curso.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void actualizarCurso(int id_curso){
        ActualizarCursoRequest actualizarCursoRequest = new ActualizarCursoRequest();
        actualizarCursoRequest.setId_curso(id_curso);
        actualizarCursoRequest.setNombreCurso(nombreCurso.getText().toString());
        actualizarCursoRequest.setDescripcionCurso(descripcionCurso.getText().toString());
        actualizarCursoRequest.setEstado(estadoCurso.getText().toString());

        Call<CursoResponse> cursoResponseCall = ApiClient.getUserService().updateCurso(actualizarCursoRequest);
        cursoResponseCall.enqueue(new Callback<CursoResponse>() {
            @Override
            public void onResponse(Call<CursoResponse> call, Response<CursoResponse> response) {
                if(response.isSuccessful()){
                    final CursoResponse cursoResponse = response.body();
                    if(cursoResponse.getEstado().equals("1")){
                        Toast.makeText(Info_Curso.this, "Curso Actualizado!", Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(Info_Curso.this, recycler_cursos.class));
                            }
                        }, 400);
                    }else{
                        Toast.makeText(Info_Curso.this, "La actualización falló!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Info_Curso.this, "El servidor no responde correctamente!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CursoResponse> call, Throwable t) {
                Toast.makeText(Info_Curso.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}