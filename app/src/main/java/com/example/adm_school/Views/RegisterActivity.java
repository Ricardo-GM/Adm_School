package com.example.adm_school.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.adm_school.Api.ApiClient;
import com.example.adm_school.Models.RegisterRequest;
import com.example.adm_school.Models.RegisterResponse;
import com.example.adm_school.R;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText nombre;
    private EditText correo;
    private EditText password;
    private EditText telefono;
    private EditText direccion;

    private Button btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nombre =findViewById(R.id.nombre);
        correo = findViewById(R.id.correo);
        password = findViewById(R.id.password);
        telefono = findViewById(R.id.telefono);
        direccion = findViewById(R.id.direccion);

        btnRegistro = findViewById(R.id.btnRegistro);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(nombre.getText().toString()) || TextUtils.isEmpty(correo.getText().toString())
                        || TextUtils.isEmpty(password.getText().toString())|| TextUtils.isEmpty(telefono.getText().toString()) || TextUtils.isEmpty(direccion.getText().toString())){

                    Toast.makeText(RegisterActivity.this, "Debe completar todos los campos", Toast.LENGTH_LONG).show();
                }else{
                        registrar();
                }
            }
        });
    }


    public void registrar(){
        RegisterRequest registerRequest= new RegisterRequest();
        registerRequest.setNombre(nombre.getText().toString());
        registerRequest.setCorreo(correo.getText().toString());
        registerRequest.setPassword(password.getText().toString());
        registerRequest.setTelefono(telefono.getText().toString());
        registerRequest.setDireccion(direccion.getText().toString());

        Call<RegisterResponse> registerResponseCall = ApiClient.getUserService().createUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful()){
                    final RegisterResponse registerResponse = response.body();
                    if(registerResponse.getEstado().equals("1")){
                        Toast.makeText(RegisterActivity.this, "Usuario Registrado!", Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            }
                        }, 700);
                    }else{
                        Toast.makeText(RegisterActivity.this, "La validación falló!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "El servidor no responde correctamente!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });



    }
}