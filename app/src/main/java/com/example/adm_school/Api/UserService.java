package com.example.adm_school.Api;

import com.example.adm_school.Models.LoginRequest;
import com.example.adm_school.Models.LoginResponse;
import com.example.adm_school.Models.RegisterResponse;
import com.example.adm_school.Models.RegisterRequest;
import com.example.adm_school.Models.Salon;
import com.example.adm_school.Models.SalonRequest;
import com.example.adm_school.Models.SalonResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    @POST("Insertar_usuario2.php")
    Call<RegisterResponse> createUser(@Body RegisterRequest registerRequest);

    @POST("Validar_usuario2.php")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @GET("Obtener_salones.php")
    Call<List<Salon>> listSalon();

    @POST("Insertar_salon.php")
    Call<SalonResponse> createSalon(@Body SalonRequest salonRequest);
}
