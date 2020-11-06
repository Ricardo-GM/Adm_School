package com.example.adm_school.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.adm_school.Adapter.ListaSalones;
import com.example.adm_school.Api.ApiClient;
import com.example.adm_school.Api.UserService;
import com.example.adm_school.Models.InsertarAlumnoRequest;
import com.example.adm_school.Models.InsertarProfesorRequest;
import com.example.adm_school.Models.Salon;
import com.example.adm_school.Models.TipoUsuarioResponse;
import com.example.adm_school.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Info_Usuario extends AppCompatActivity {
    private EditText nombre;
    private EditText correo;
    private EditText password;
    private Button btnAsignarUsuario;
    private Spinner spinner1;
    private EditText txtExtra;
    private String tipoUsuario;
    private Spinner spinnerSalones;
    private LinearLayout contenedorSpinnerSalones;
    private String nombreSalon;

    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_usuario);
        /*
        nombre = findViewById(R.id.nombreUsuario);
        correo = findViewById(R.id.correo);
        password = findViewById(R.id.password);
         */
        btnAsignarUsuario = findViewById(R.id.btnAsignarUsuario);
        spinner1 = findViewById(R.id.spinnerRol);
        txtExtra = findViewById(R.id.datoExtra);
        spinnerSalones = findViewById(R.id.spinnerSalones);
        contenedorSpinnerSalones = findViewById(R.id.contenedorSpinnerSalones);

        contenedorSpinnerSalones.setVisibility(View.GONE);


        //CREANDO EL ARRAY DEL SPINNER
        String [] opciones = {"Profesor","Alumno"};

        final List<String> listaSalones1 = new ArrayList<>();

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaSalones1);
        Call<List<ListaSalones>> call = ApiClient.getUserService().listarSalon();
        call.enqueue(new Callback<List<ListaSalones>>() {
            @Override
            public void onResponse(Call<List<ListaSalones>> call, Response<List<ListaSalones>> response) {
                if(response.isSuccessful()){
                    for(ListaSalones post : response.body()){
                        String nombre = post.getNombreSalon();
                        listaSalones1.add(nombre);

                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSalones.setAdapter(adapter1);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ListaSalones>> call, Throwable t) {
            }
        });

        spinnerSalones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nombreSalon = spinnerSalones.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                txtExtra.setHint("Seleccione un Item en el Spinner");
            }
        });

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opciones);
        spinner1.setAdapter(adapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    txtExtra.setHint("Inserte Salario");
                    tipoUsuario = "profesor";

                }else{
                    txtExtra.setHint("Inserte nombre del Apoderado");
                    tipoUsuario = "alumno";
                    contenedorSpinnerSalones.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                txtExtra.setHint("Seleccione un Item en el Spinner");
            }
        });

        final int id_usuario = getIntent().getIntExtra("id_usuario", 0);
        final String nombre = getIntent().getStringExtra("nombre");
        final String correo = getIntent().getStringExtra("correo");
        final String password = getIntent().getStringExtra("password");
        /*
        if (nombre != "") {
            this.nombre.setText(nombre);
        }

        if (correo != "") {
            this.correo.setText(correo);
        }

        if (password != "") {
            this.password.setText(password);
        }
        */

        btnAsignarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(txtExtra.getText())){
                    Toast.makeText(Info_Usuario.this, "Debe llenar todos los campos", Toast.LENGTH_LONG).show();
                }else{
                    if(tipoUsuario == "profesor"){
                        insertarProfesor(id_usuario);
                    }else{
                        insertarAlumno(id_usuario);
                    }
                }
            }
        });


    }

    public void insertarProfesor(int id){
        InsertarProfesorRequest insertarProfesorRequest = new InsertarProfesorRequest();
        insertarProfesorRequest.setId_usuario(id);
        insertarProfesorRequest.setSueldo(txtExtra.getText().toString());

        Call<TipoUsuarioResponse> tipoUsuarioResponseCall = ApiClient.getUserService().insertarProfesor(insertarProfesorRequest);
        tipoUsuarioResponseCall.enqueue(new Callback<TipoUsuarioResponse>() {
            @Override
            public void onResponse(Call<TipoUsuarioResponse> call, Response<TipoUsuarioResponse> response) {
                if(response.isSuccessful()){
                    final TipoUsuarioResponse tipoUsuarioResponse = response.body();
                    if(tipoUsuarioResponse.getEstado().equals("1")){
                        Toast.makeText(Info_Usuario.this, "Datos Actualizados!", Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(Info_Usuario.this, recycler_usuarios.class));
                            }
                        }, 400);
                    }else{
                        Toast.makeText(Info_Usuario.this, "La Actualización falló!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Info_Usuario.this, "El servidor no responde correctamente!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TipoUsuarioResponse> call, Throwable t) {
                Toast.makeText(Info_Usuario.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void insertarAlumno(int id){
        InsertarAlumnoRequest insertarAlumnoRequest = new InsertarAlumnoRequest();
        insertarAlumnoRequest.setId_usuario(id);
        insertarAlumnoRequest.setApoderado(txtExtra.getText().toString());
        insertarAlumnoRequest.setNombreSalon(nombreSalon);

        Call<TipoUsuarioResponse> tipoUsuarioResponseCall = ApiClient.getUserService().insertarAlumno(insertarAlumnoRequest);
        tipoUsuarioResponseCall.enqueue(new Callback<TipoUsuarioResponse>() {
            @Override
            public void onResponse(Call<TipoUsuarioResponse> call, Response<TipoUsuarioResponse> response) {
                if(response.isSuccessful()){
                    final TipoUsuarioResponse tipoUsuarioResponse = response.body();
                    if(tipoUsuarioResponse.getEstado().equals("1")){
                        Toast.makeText(Info_Usuario.this, "Datos Actualizados!", Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(Info_Usuario.this, recycler_usuarios.class));
                            }
                        }, 400);
                    }else{
                        Toast.makeText(Info_Usuario.this, "Algo ha fallado!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Info_Usuario.this, "Not Success!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TipoUsuarioResponse> call, Throwable t) {
                Toast.makeText(Info_Usuario.this, "Datos Actualizados!", Toast.LENGTH_LONG).show();
            }
        });
    }
}