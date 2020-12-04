package com.example.adm_school.Adapter;

public class ListaSalones {
    public String nombreSalon;
    public String id_salon;


    public ListaSalones(String nombreSalon) {
        this.nombreSalon = nombreSalon;
    }

    public String getNombreSalon() {
        return nombreSalon;
    }

    public void setNombreSalon(String nombreSalon) {
        this.nombreSalon = nombreSalon;
    }

    public String getId_salon() {
        return id_salon;
    }

    public void setId_salon(String id_salon) {
        this.id_salon = id_salon;
    }
}
