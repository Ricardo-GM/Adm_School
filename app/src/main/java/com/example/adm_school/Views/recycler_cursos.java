package com.example.adm_school.Views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.adm_school.Adapter.CursoAdapter;
import com.example.adm_school.Api.ApiClient;
import com.example.adm_school.Models.Curso;
import com.example.adm_school.Models.CursoResponse;
import com.example.adm_school.Models.EliminarCursoRequest;
import com.example.adm_school.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class recycler_cursos extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Curso> cursos;
    private FloatingActionButton btnAddCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_cursos);

        btnAddCurso = findViewById(R.id.addCurso);

        btnAddCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(recycler_cursos.this, Info_Curso.class));
            }
        });

        Call<List<Curso>> call = ApiClient.getUserService().listCurso();
        call.enqueue(new Callback<List<Curso>>() {
            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {
                generateList(response.body());
            }

            @Override
            public void onFailure(Call<List<Curso>> call, Throwable t) {
                Toast.makeText(recycler_cursos.this, "Falló la conexión!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void generateList(List<Curso> lista) {
        cursos = lista;
        mRecyclerView = findViewById(R.id.recycle_cursos);
        mLayoutManager = new LinearLayoutManager(this);

        mAdapter = new CursoAdapter(cursos, R.layout.card_item, new CursoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Curso curso, int position) {
                Toast.makeText(recycler_cursos.this, String.valueOf(curso.getId_curso()), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(recycler_cursos.this, Info_Curso.class);
                intent.putExtra("id_curso", curso.getId_curso());
                intent.putExtra("nombreCurso", curso.getNombreCurso());
                intent.putExtra("descripcionCurso", curso.getDescripcionCurso());
                intent.putExtra("estado", curso.getEstado());
                startActivity(intent);
            }
        }, new CursoAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(final Curso curso, int position) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(recycler_cursos.this);
                alerta.setMessage("Desea Eliminar este curso?")
                        .setCancelable(true)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eliminar(curso.getId_curso());
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Eliminar curso");
                titulo.show();
            }
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void eliminar(int id_curso){
        EliminarCursoRequest eliminarCursoRequest = new EliminarCursoRequest();
        eliminarCursoRequest.setId_curso(id_curso);

        Call<CursoResponse> cursoResponseCall = ApiClient.getUserService().deleteCurso(eliminarCursoRequest);
        cursoResponseCall.enqueue(new Callback<CursoResponse>() {
            @Override
            public void onResponse(Call<CursoResponse> call, Response<CursoResponse> response) {
                if (response.isSuccessful()) {
                    final CursoResponse cursoResponse = response.body();
                    if (cursoResponse.getEstado().equals("1")) {
                        finish();
                        startActivity(getIntent());
                        Toast.makeText(recycler_cursos.this, "Curso Eliminado!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(recycler_cursos.this, "Ocurrió un error!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(recycler_cursos.this, "El servidor no responde correctamente", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CursoResponse> call, Throwable t) {
                Toast.makeText(recycler_cursos.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });



    }
}