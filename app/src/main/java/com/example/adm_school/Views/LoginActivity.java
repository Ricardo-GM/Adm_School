package com.example.adm_school.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adm_school.Api.ApiClient;
import com.example.adm_school.Models.LoginRequest;
import com.example.adm_school.Models.LoginResponse;
import com.example.adm_school.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView mTextViewRegister;

    private EditText correo;
    private EditText password;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correo = findViewById(R.id.correo);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);


        mTextViewRegister = findViewById(R.id.textViewRegister);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(correo.getText().toString())|| TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(LoginActivity.this, "Debe Completar todos los campos", Toast.LENGTH_LONG).show();
                }else{
                    logear();
                }
            }
        });
    }

    public void logear(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setCorreo(correo.getText().toString());
        loginRequest.setPassword(password.getText().toString());

        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    final LoginResponse loginResponse = response.body();
                    if(loginResponse.getEstado().equals("1")){
                        Toast.makeText(LoginActivity.this, "Acceso Autorizado! Bienvenido "+ucFirst(loginResponse.getRol())+"!", Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                switch(loginResponse.getRol()){
                                    case "COORDINADOR":
                                        startActivity(new Intent(LoginActivity.this, recycler_coordinador.class));
                                        break;
                                    case  "PROFESOR":
                                        startActivity(new Intent(LoginActivity.this, recyclerview_profesor.class));
                                        break;
                                    case "ALUMNO":
                                        startActivity(new Intent(LoginActivity.this, recyclerview_alumno.class));
                                        break;
                                    case "SN":
                                        startActivity(new Intent(LoginActivity.this, sin_rol.class));
                                        break;

                                }
                            }
                        },700);
                    }else{
                        Toast.makeText(LoginActivity.this, "Datos Incorrectos!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "El servidor no responde correctamente", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public static String ucFirst(String str){
        if(str == null || str.isEmpty()) {
            return " ";
        }else{
            return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
        }
    }


}