package com.example.adm_school.Api;

import com.example.adm_school.Adapter.ListaSalones;
import com.example.adm_school.Models.ActualizarCursoRequest;
import com.example.adm_school.Models.ActualizarSalonRequest;
import com.example.adm_school.Models.Clase;
import com.example.adm_school.Models.ClaseResponse;
import com.example.adm_school.Models.Curso;
import com.example.adm_school.Models.CursoRequest;
import com.example.adm_school.Models.CursoResponse;
import com.example.adm_school.Models.EliminarClaseRequest;
import com.example.adm_school.Models.EliminarCursoRequest;
import com.example.adm_school.Models.EliminarSalonRequest;
import com.example.adm_school.Models.InsertarAlumnoRequest;
import com.example.adm_school.Models.InsertarProfesorRequest;
import com.example.adm_school.Models.LoginRequest;
import com.example.adm_school.Models.LoginResponse;
import com.example.adm_school.Models.Profesores;
import com.example.adm_school.Models.RegisterResponse;
import com.example.adm_school.Models.RegisterRequest;
import com.example.adm_school.Models.Salon;
import com.example.adm_school.Models.SalonRequest;
import com.example.adm_school.Models.SalonResponse;
import com.example.adm_school.Models.TipoUsuarioResponse;
import com.example.adm_school.Models.Usuario;

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

    @GET("Obtener_cursos.php")
    Call<List<Curso>> listCurso();

    @GET("Obtener_usuarios_sn.php")
    Call<List<Usuario>> listUsuariosSn();

    @POST("Insertar_salon.php")
    Call<SalonResponse> createSalon(@Body SalonRequest salonRequest);

    @POST("Insertar_curso.php")
    Call<CursoResponse> createCurso(@Body CursoRequest cursoRequest);

    @POST("Eliminar_salon.php")
    Call<SalonResponse> deleteSalon(@Body EliminarSalonRequest eliminarSalonRequest);

    @POST("Eliminar_curso.php")
    Call<CursoResponse> deleteCurso(@Body EliminarCursoRequest eliminarCursoRequest);

    @POST("Actualizar_salon.php")
    Call<SalonResponse> updateSalon(@Body ActualizarSalonRequest actualizarSalonRequest);

    @POST("Actualizar_curso.php")
    Call<CursoResponse> updateCurso(@Body ActualizarCursoRequest actualizarCursoRequest);

    @POST("Insertar_alumno.php")
    Call<TipoUsuarioResponse> insertarAlumno(@Body InsertarAlumnoRequest insertarAlumnoRequest);

    @POST("Insertar_profesor.php")
    Call<TipoUsuarioResponse> insertarProfesor(@Body InsertarProfesorRequest insertarProfesorRequest);

    @GET("Obtener_salones.php")
    Call<List<ListaSalones>> listarSalon();

    @GET("Obtener_clases.php")
    Call<List<Clase>> listClases();

    @GET("Obtener_profesores.php")
    Call<List<Profesores>> listProfesores();

    @POST("Eliminar_clase.php")
    Call<ClaseResponse> deleteClase(@Body EliminarClaseRequest eliminarClaseRequest);
}
