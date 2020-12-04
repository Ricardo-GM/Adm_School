package com.example.adm_school.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.adm_school.Adapter.ListaSalones;
import com.example.adm_school.Api.ApiClient;
import com.example.adm_school.Models.ActualizarClaseRequest;
import com.example.adm_school.Models.ClaseRequest;
import com.example.adm_school.Models.ClaseResponse;
import com.example.adm_school.Models.Curso;
import com.example.adm_school.Models.Profesores;
import com.example.adm_school.R;

import java.util.ArrayList;
import java.util.Calendar;
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
    //Lo que agregre
    private TextView txtTiempoInicio;
    private TextView getTxtTiempoFinal;
    int tmHour1, tmMinute1;
    int tmHour2, tmMinute2;


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

        txtTiempoInicio = findViewById(R.id.tiempo_inicio);
        getTxtTiempoFinal = findViewById(R.id.tiempo_final);

        final int id_trabajador = getIntent().getIntExtra("id_trabajador",0);
        final int id_clase = getIntent().getIntExtra("id_clase", 0);
        final String nombreClase = getIntent().getStringExtra("nombreClase");
        final int id_salon = getIntent().getIntExtra("id_salon",0);
        final int id_curso = getIntent().getIntExtra("id_curso",0);
        final String horaInicio = getIntent().getStringExtra("horaInicio");
        final String horaFin = getIntent().getStringExtra("horaFin");
        final String diaEscogido = getIntent().getStringExtra("dias");

        txtTiempoInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        Info_Clase.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //inicializamos las variables
                                tmHour1 = hourOfDay;
                                tmMinute1 = minute ;
                                //Inicializamos el calendario
                                Calendar calendar = Calendar.getInstance();
                                //Set hora y Minutos
                                calendar.set(0,0,0,tmHour1,tmMinute1);
                                //Set selected tiempo dentro del text view
                                txtTiempoInicio.setText(DateFormat.format("hh:mm aa", calendar));
                            }
                        }, 12,0,false
                );
                //Displayed previous selected time
                timePickerDialog.updateTime(tmHour1,tmMinute1);
                //show dialog
                timePickerDialog.show();
            }
        });
        getTxtTiempoFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        Info_Clase.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //inicializamos las variables
                                tmHour2 = hourOfDay;
                                tmMinute2 = minute ;
                                //Inicializamos el calendario
                                Calendar calendar = Calendar.getInstance();
                                //Set hora y Minutos
                                calendar.set(0,0,0,tmHour2,tmMinute2);
                                //Set selected tiempo dentro del text view
                                getTxtTiempoFinal.setText(DateFormat.format("hh:mm aa", calendar));
                            }
                        }, 12,0,false
                );
                //Displayed previous selected time
                timePickerDialog.updateTime(tmHour2,tmMinute2);
                //show dialog
                timePickerDialog.show();
            }
        });


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
                        String id_salon = post.getId_salon();
                        listaSalones1.add(id_salon+'-'+nombre);

                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSalones.setAdapter(adapter1);
                    }

                    seleccionarSalon(listaSalones1, id_salon);
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
                        String id_curso = post.getId_curso();
                        listaCursos.add(id_curso+'-'+nombre);

                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCursos.setAdapter(adapter2);
                    }

                    seleccionarCurso(listaCursos, id_curso);
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
                        int id_Profesor= post.getId_usuario();
                        listaProfesores.add(id_Profesor+"-"+nombre);


                        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerProfesores.setAdapter(adapter3);




                    }
                    seleccionarProfesor(listaProfesores, id_trabajador);
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

        final ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opciones);
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
                seleccionarDia(dia, diaEscogido);
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });






        if (nombreClase != "") {
            this.nombreClase.setText(nombreClase);
        }



        btnCrearClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(nombreClase)){
                    Toast.makeText(Info_Clase.this, "Debe completar todos los campos", Toast.LENGTH_LONG).show();
                }else{
                    if(id_clase != 0){
                        actualizarClase(id_clase);
                    }else{
                        addClase();
                    }
                }
            }
        });

    }
    private void seleccionarDia(String dia, String escogido){
        if(escogido != ""){
            String diaN;
            switch (escogido){
                case "LN":
                    diaN = "Lunes";
                    break;
                case "MT":
                    diaN = "Martes";
                    break;
                case "MC":
                    diaN = "Miércoles";
                    break;
                case "JV":
                    diaN = "Jueves";
                    break;
                case "VN":
                    diaN = "Viernes";
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + escogido);
            }
            spinnerDias.setSelection(((ArrayAdapter<String>)spinnerDias.getAdapter()).getPosition(diaN));
        }else{
            spinnerDias.setSelection(((ArrayAdapter<String>)spinnerDias.getAdapter()).getPosition(dia));
        }


    }

    private void addClase() {
        String[] separated1 = nombreSalon.split("-");
        int id_salon = Integer.parseInt(separated1[0]);

        String[] separated2 = nombreCurso.split("-");
        int id_curso = Integer.parseInt(separated2[0]);

        String[] separated3 = nombreProfesor.split("-");
        int id_profesor = Integer.parseInt(separated3[0]);

        ClaseRequest claseRequest = new ClaseRequest();
        claseRequest.setId_salon(id_salon);
        claseRequest.setId_curso(id_curso);
        claseRequest.setId_trabajador(id_profesor);
        claseRequest.setNombreClase(nombreClase.getText().toString());
        claseRequest.setDias(dia);
        claseRequest.setEstado("disponible");

        Call<ClaseResponse> claseResponseCall = ApiClient.getUserService().createClase(claseRequest);
        claseResponseCall.enqueue(new Callback<ClaseResponse>() {
            @Override
            public void onResponse(Call<ClaseResponse> call, Response<ClaseResponse> response) {
                if(response.isSuccessful()){
                    final ClaseResponse claseResponse = response.body();
                    if(claseResponse.getEstado().equals("1")){
                        Toast.makeText(Info_Clase.this, "Datos Creados!", Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(Info_Clase.this, recycler_usuarios.class));
                            }
                        }, 400);
                    }else{
                        Toast.makeText(Info_Clase.this, "La Actualización falló!", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(Info_Clase.this, "El servidor no responde correctamente!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ClaseResponse> call, Throwable t) {
                Toast.makeText(Info_Clase.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void actualizarClase(int id_clase) {
        String[] separated1 = nombreSalon.split("-");
        int id_salon = Integer.parseInt(separated1[0]);

        String[] separated2 = nombreCurso.split("-");
        int id_curso = Integer.parseInt(separated2[0]);

        String[] separated3 = nombreProfesor.split("-");
        int id_profesor = Integer.parseInt(separated3[0]);

        ActualizarClaseRequest actualizarClaseRequest = new ActualizarClaseRequest();
        actualizarClaseRequest.setId_clase(id_clase);
        actualizarClaseRequest.setId_salon(id_salon);
        actualizarClaseRequest.setId_curso(id_curso);
        actualizarClaseRequest.setId_trabajador(id_profesor);
        actualizarClaseRequest.setNombreClase(nombreClase.getText().toString());
        actualizarClaseRequest.setDias(dia);
        actualizarClaseRequest.setEstado("disponible");

        Call<ClaseResponse> claseResponseCall = ApiClient.getUserService().updateClase(actualizarClaseRequest);
        claseResponseCall.enqueue(new Callback<ClaseResponse>() {
            @Override
            public void onResponse(Call<ClaseResponse> call, Response<ClaseResponse> response) {
                if(response.isSuccessful()){
                    final ClaseResponse claseResponse = response.body();
                    if(claseResponse.getEstado().equals("1")){
                        Toast.makeText(Info_Clase.this, "Clase Actualizada!", Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(Info_Clase.this, recycler_cursos.class));
                            }
                        }, 400);
                    }else{
                        Toast.makeText(Info_Clase.this, "La actualización falló!", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(Info_Clase.this, "El servidor no responde correctamente!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ClaseResponse> call, Throwable t) {

            }
        });
    }

    private void seleccionarProfesor(List<String> listaProfesores, int id_trabajador) {
        if(id_trabajador > 0){
            for(int i = 0; i < listaProfesores.size(); i++){
                String[] separated = listaProfesores.get(i).split("-");
                int id = Integer.parseInt(separated[0]);
                if(id_trabajador == id){
                    spinnerProfesores.setSelection(((ArrayAdapter<String>)spinnerProfesores.getAdapter()).getPosition(listaProfesores.get(i)));
                }
            }
        }
    }

    private void seleccionarSalon(List<String> listaSalones1, int id_salon) {
        if(id_salon > 0 ){
            for(int i = 0; i < listaSalones1.size(); i++){
                String[] separated = listaSalones1.get(i).split("-");
                int id = Integer.parseInt(separated[0]);
                if(id_salon == id){
                    spinnerSalones.setSelection(((ArrayAdapter<String>)spinnerSalones.getAdapter()).getPosition(listaSalones1.get(i)));
                }
            }
        }
    }

    private void seleccionarCurso(List<String> listaCursos, int id_curso) {
        if(id_curso > 0 ){
            for(int i = 0; i < listaCursos.size(); i++){
                String[] separated = listaCursos.get(i).split("-");
                int id = Integer.parseInt(separated[0]);
                if(id_curso == id){
                    spinnerCursos.setSelection(((ArrayAdapter<String>)spinnerCursos.getAdapter()).getPosition(listaCursos.get(i)));
                }
            }
        }
    }

}