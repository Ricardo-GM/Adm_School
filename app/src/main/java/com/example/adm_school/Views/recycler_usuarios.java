package com.example.adm_school.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.adm_school.Adapter.UsuarioAdapter;
import com.example.adm_school.Api.ApiClient;
import com.example.adm_school.Models.Usuario;
import com.example.adm_school.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class recycler_usuarios extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Usuario> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_usuarios);

        Call<List<Usuario>> call = ApiClient.getUserService().listUsuariosSn();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                generateList(response.body());
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Toast.makeText(recycler_usuarios.this, "Falló la conexión!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void generateList(List<Usuario> lista){
        usuarios = lista;
        mRecyclerView = findViewById(R.id.recycle_usuarios);
        mLayoutManager = new LinearLayoutManager(this);

        mAdapter = new UsuarioAdapter(usuarios, R.layout.card_item, new UsuarioAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Usuario usuario, int position) {
                Toast.makeText(recycler_usuarios.this, String.valueOf(usuario.getId_usuario()), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(recycler_usuarios.this, Info_Usuario.class);
                intent.putExtra("id_usuario", usuario.getId_usuario());
                intent.putExtra("nombre", usuario.getNombre());
                intent.putExtra("correo", usuario.getCorreo());
                intent.putExtra("password", usuario.getPassword());
                intent.putExtra("rol", usuario.getRol());
                startActivity(intent);
            }
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }

}