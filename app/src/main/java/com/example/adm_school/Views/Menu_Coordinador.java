package com.example.adm_school.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adm_school.Api.ApiClient;
import com.example.adm_school.Models.ActualizarSalonRequest;
import com.example.adm_school.Models.Salon;
import com.example.adm_school.Models.SalonRequest;
import com.example.adm_school.Models.SalonResponse;
import com.example.adm_school.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu_Coordinador extends AppCompatActivity {
    private EditText nombreSalon;
    private EditText cantidadSalon;
    private EditText estadoSalon;
    private Button btnCrearSalon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__coordinador);

        nombreSalon = findViewById(R.id.nombreSalon);
        cantidadSalon = findViewById(R.id.cantidadMaxima);
        estadoSalon = findViewById(R.id.estadoSalon);
        btnCrearSalon = findViewById(R.id.btnCrearSalon);

        final int actIdSalon = getIntent().getIntExtra("id_salon", 0);
        final String actNombreSalon = getIntent().getStringExtra("nombreSalon");
        final int actCantidadMaxima = getIntent().getIntExtra("cantidadMaxima", 0);
        final String estado = getIntent().getStringExtra("estado");

        if( actNombreSalon != ""){
            nombreSalon.setText(actNombreSalon);
        }

        if(actCantidadMaxima != 0){
            cantidadSalon.setText(String.valueOf(actCantidadMaxima));
        }

        if(estado != ""){
            estadoSalon.setText(estado);
        }





        btnCrearSalon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(nombreSalon.getText()) || TextUtils.isEmpty(cantidadSalon.getText()) || TextUtils.isEmpty(estadoSalon.getText())){
                    Toast.makeText(Menu_Coordinador.this, "Debe completar todos los campos", Toast.LENGTH_LONG).show();
                }else{
                    if(actIdSalon != 0){
                        actualizarSalon(actIdSalon);
                    }else{
                        addSalon();
                    }
                }
            }
        });
    }

    public void addSalon(){
        SalonRequest salonRequest = new SalonRequest();
        salonRequest.setNombreSalon(nombreSalon.getText().toString());
        salonRequest.setCantidadMaxima(Integer.parseInt(cantidadSalon.getText().toString()));
        salonRequest.setEstado(estadoSalon.getText().toString());

        Call<SalonResponse> salonResponseCall = ApiClient.getUserService().createSalon(salonRequest);
        salonResponseCall.enqueue(new Callback<SalonResponse>() {
            @Override
            public void onResponse(Call<SalonResponse> call, Response<SalonResponse> response) {
                if(response.isSuccessful()){
                    final SalonResponse salonResponse = response.body();
                    if(salonResponse.getEstado().equals("1")){
                        Toast.makeText(Menu_Coordinador.this, "Salón Creado!", Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(Menu_Coordinador.this, recycler_coordinador.class));
                            }
                        }, 400);
                    }else{
                        Toast.makeText(Menu_Coordinador.this, "La creación falló!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Menu_Coordinador.this, "El servidor no responde correctamente!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SalonResponse> call, Throwable t) {
                Toast.makeText(Menu_Coordinador.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void actualizarSalon(int id_salon){
        ActualizarSalonRequest actualizarSalonRequest = new ActualizarSalonRequest();
        actualizarSalonRequest.setId_salon(id_salon);
        actualizarSalonRequest.setNombreSalon(nombreSalon.getText().toString());
        actualizarSalonRequest.setCantidadMaxima(Integer.parseInt(cantidadSalon.getText().toString()));
        actualizarSalonRequest.setEstado(estadoSalon.getText().toString());

        Call<SalonResponse> salonResponseCall = ApiClient.getUserService().updateSalon(actualizarSalonRequest);
        salonResponseCall.enqueue(new Callback<SalonResponse>() {
            @Override
            public void onResponse(Call<SalonResponse> call, Response<SalonResponse> response) {
                if(response.isSuccessful()){
                    final SalonResponse salonResponse = response.body();
                    if(salonResponse.getEstado().equals("1")){
                        Toast.makeText(Menu_Coordinador.this, "Salón Actualizado!", Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(Menu_Coordinador.this, recycler_coordinador.class));
                            }
                        },400);
                    }else{
                        Toast.makeText(Menu_Coordinador.this, "La actualización falló!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Menu_Coordinador.this, "El servidor no responde correctamente!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SalonResponse> call, Throwable t) {
                Toast.makeText(Menu_Coordinador.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}