package com.example.adm_school.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.adm_school.Adapter.SalonAdapter;
import com.example.adm_school.Api.ApiClient;
import com.example.adm_school.Models.Salon;
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



            }
        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

}