package com.example.adm_school.Api;

import com.example.adm_school.Models.RegisterResponse;
import com.example.adm_school.Models.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("Insertar_usuario2.php")
    Call<RegisterResponse> createUser(@Body RegisterRequest registerRequest);
}
