package com.example.adm_school.Views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.adm_school.Adapter.SalonAdapter;
import com.example.adm_school.Api.ApiClient;
import com.example.adm_school.Models.EliminarSalonRequest;
import com.example.adm_school.Models.Salon;
import com.example.adm_school.Models.SalonResponse;
import com.example.adm_school.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class recycler_coordinador extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Salon> salones;
    private FloatingActionButton btnAddSalon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_coordinador);

        btnAddSalon = findViewById(R.id.addSalon);

        btnAddSalon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(recycler_coordinador.this, Menu_Coordinador.class));
            }
        });


        Call<List<Salon>> call = ApiClient.getUserService().listSalon();
        call.enqueue(new Callback<List<Salon>>() {
            @Override
            public void onResponse(Call<List<Salon>> call, Response<List<Salon>> response) {
                generateList(response.body());
            }

            @Override
            public void onFailure(Call<List<Salon>> call, Throwable t) {
                Toast.makeText(recycler_coordinador.this, "Falló la conexión!", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void generateList(List<Salon> lista){
        salones = lista;
        mRecyclerView = findViewById(R.id.recycle_salones);
        mLayoutManager = new LinearLayoutManager(this);


        mAdapter = new SalonAdapter(salones, R.layout.card_item, new SalonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Salon salon, int position) {
                Toast.makeText(recycler_coordinador.this, String.valueOf(salon.getId()) , Toast.LENGTH_LONG).show();

                Intent intent = new Intent(recycler_coordinador.this, Menu_Coordinador.class);
                intent.putExtra("id_salon", salon.getId());
                intent.putExtra("nombreSalon", salon.getNombreSalon());
                intent.putExtra("cantidadMaxima", salon.getCantidadMaxima());
                intent.putExtra("estado", salon.getEstado());
                startActivity(intent);
            }
        }, new SalonAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(final Salon salon, int position) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(recycler_coordinador.this);
                alerta.setMessage("Desea Eliminar este salón?")
                        .setCancelable(true)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eliminar(salon.getId());
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Eliminar salón");
                titulo.show();
            }
        });




        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void eliminar(int id_salon){
        EliminarSalonRequest eliminarSalonRequest = new EliminarSalonRequest();
        eliminarSalonRequest.setId(id_salon);

        Call<SalonResponse> salonResponseCall = ApiClient.getUserService().deleteSalon(eliminarSalonRequest);
        salonResponseCall.enqueue(new Callback<SalonResponse>() {
            @Override
            public void onResponse(Call<SalonResponse> call, Response<SalonResponse> response) {
                if(response.isSuccessful()){
                    final SalonResponse salonResponse = response.body();
                    if(salonResponse.getEstado().equals("1")){
                        finish();
                        startActivity(getIntent());
                        Toast.makeText(recycler_coordinador.this, "Salón Eliminado!", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(recycler_coordinador.this, "Ocurrió un error!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(recycler_coordinador.this, "El servidor no responde correctamente", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SalonResponse> call, Throwable t) {
                Toast.makeText(recycler_coordinador.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}