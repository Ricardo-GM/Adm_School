package com.example.adm_school.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
import com.example.adm_school.Models.Curso;
import com.example.adm_school.Models.Profesores;
import com.example.adm_school.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Info_Clase extends AppCompatActivity {
    private EditText nombreClase;
    private Spinner spinnerSalones;
    private Spinner spinnerCursos;
    private Spinner spinnerProfesores;
    private Spinner spinnerDias;
    private Button btnCrearClase;
    private String nombreSalon;
    private String nombreCurso;
    private String nombreProfesor;
    private String dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_clase);

        nombreClase = findViewById(R.id.nombreClase);
        spinnerSalones = findViewById(R.id.spinnerSalones);
        spinnerCursos = findViewById(R.id.spinnerCursos);
        spinnerProfesores = findViewById(R.id.spinnerProfesores);
        spinnerDias = findViewById(R.id.spinnerDias);
        btnCrearClase = findViewById(R.id.btnCrearClase);

        final List<String> listaSalones1 = new ArrayList<>();
        final List<String> listaCursos = new ArrayList<>();
        final List<String> listaProfesores = new ArrayList<>();

        //CREANDO EL ARRAY DEL SPINNER
        String [] opciones = {"Lunes","Martes","Miércoles","Jueves","Viernes"};


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

            }
        });

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaCursos);
        Call<List<Curso>> call1 = ApiClient.getUserService().listCurso();
        call1.enqueue(new Callback<List<Curso>>() {
            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {
                if(response.isSuccessful()){
                    for(Curso post : response.body()){
                        String nombre = post.getNombreCurso();
                        listaCursos.add(nombre);

                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCursos.setAdapter(adapter2);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Curso>> call, Throwable t) {

            }
        });

        spinnerCursos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nombreCurso = spinnerCursos.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaProfesores);
        Call<List<Profesores>> call2 = ApiClient.getUserService().listProfesores();
        call2.enqueue(new Callback<List<Profesores>>() {
            @Override
            public void onResponse(Call<List<Profesores>> call, Response<List<Profesores>> response) {
                if(response.isSuccessful()){
                    for(Profesores post : response.body()){
                        String nombre = post.getNombre();
                        listaProfesores.add(nombre);

                        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerProfesores.setAdapter(adapter3);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Profesores>> call, Throwable t) {

            }
        });

        spinnerProfesores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nombreProfesor = spinnerProfesores.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opciones);
        spinnerDias.setAdapter(adapter);

        spinnerDias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        dia = "LN";
                        break;
                    case 1:
                        dia = "MT";
                        break;
                    case 2:
                        dia = "MC";
                        break;
                    case 3:
                        dia = "JV";
                        break;
                    case 4:
                        dia = "VN";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final int id_clase = getIntent().getIntExtra("id_clase", 0);
        /*
        final String nombre = getIntent().getStringExtra("nombre");
        final String correo = getIntent().getStringExtra("correo");
        final String password = getIntent().getStringExtra("password");

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

        btnCrearClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(nombreClase.getText())){
                    Toast.makeText(Info_Clase.this, "Debe llenar todos los campos", Toast.LENGTH_LONG).show();
                }
                /*
                else{
                    if(id_clase != 0){
                        actualizarClase(id_clase);
                    }else{
                        crearClase();
                    }
                }

                 */
            }
        });

        /*
        public void crearClase(){

        }

        public void actualizarClase(int id__clase){

        }
        */

    }
}