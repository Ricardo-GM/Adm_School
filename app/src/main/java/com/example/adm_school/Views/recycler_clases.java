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

import com.example.adm_school.Adapter.ClaseAdapter;
import com.example.adm_school.Api.ApiClient;
import com.example.adm_school.Models.Clase;
import com.example.adm_school.Models.ClaseResponse;
import com.example.adm_school.Models.EliminarClaseRequest;
import com.example.adm_school.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class recycler_clases extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Clase> clases;
    private FloatingActionButton btnAddClase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_clases);

        btnAddClase = findViewById(R.id.addClase);
        btnAddClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(recycler_clases.this, Info_Clase.class));
            }
        });

        Call<List<Clase>> call = ApiClient.getUserService().listClases();
        call.enqueue(new Callback<List<Clase>>() {
            @Override
            public void onResponse(Call<List<Clase>> call, Response<List<Clase>> response) {
                generateList(response.body());
            }

            @Override
            public void onFailure(Call<List<Clase>> call, Throwable t) {
                Toast.makeText(recycler_clases.this, "Falló la conexión!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void generateList(List<Clase> lista){
        clases = lista;
        mRecyclerView = findViewById(R.id.recycle_clases);
        mLayoutManager = new LinearLayoutManager(this);

        mAdapter = new ClaseAdapter(clases, R.layout.card_item, new ClaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Clase clase, int position) {
                Toast.makeText(recycler_clases.this, String.valueOf(clase.getId_clase()), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(recycler_clases.this, Info_Clase.class);
                intent.putExtra("id_clase", clase.getId_clase());
                intent.putExtra("id_salon", clase.getId_salon());
                intent.putExtra("id_curso", clase.getId_curso());
                intent.putExtra("id_trabajador", clase.getId_trabajador());
                intent.putExtra("nombreClase", clase.getNombreClase());
                intent.putExtra("dias", clase.getDias());
                intent.putExtra("horaInicio", clase.getHoraInicio());
                intent.putExtra("horaFinal", clase.getHoraFinal());
                startActivity(intent);
            }
        }, new ClaseAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(final Clase clase, int position) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(recycler_clases.this);
                alerta.setMessage("Desea Eliminar esta clase?")
                        .setCancelable(true)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eliminar(clase.getId_clase());
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Eliminar clase");
                titulo.show();
            }
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void eliminar(int id_clase){
        EliminarClaseRequest eliminarClaseRequest = new EliminarClaseRequest();
        eliminarClaseRequest.setId_clase(id_clase);

        Call<ClaseResponse> claseResponseCall = ApiClient.getUserService().deleteClase(eliminarClaseRequest);
        claseResponseCall.enqueue(new Callback<ClaseResponse>() {
            @Override
            public void onResponse(Call<ClaseResponse> call, Response<ClaseResponse> response) {
                if (response.isSuccessful()) {
                    final ClaseResponse claseResponse = response.body();
                    if (claseResponse.getEstado().equals("1")) {
                        finish();
                        startActivity(getIntent());
                        Toast.makeText(recycler_clases.this, "Clase Eliminada!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(recycler_clases.this, "Ocurrió un error!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(recycler_clases.this, "El servidor no responde correctamente", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ClaseResponse> call, Throwable t) {
                Toast.makeText(recycler_clases.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });



    }

}