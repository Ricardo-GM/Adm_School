package com.example.adm_school.Models;

public class Salon {
    private int id_salon;
    private String nombreSalon;
    private int cantidadMaxima;
    private String estado;


    public Salon(int id_salon, String nombreSalon, int cantidadMaxima, String estado) {
        this.id_salon = id_salon;
        this.nombreSalon = nombreSalon;
        this.cantidadMaxima = cantidadMaxima;
        this.estado = estado;
    }

    public int getId() {
        return id_salon;
    }

    public void setId(int id_salon) {
        this.id_salon = id_salon;
    }

    public String getNombreSalon() {
        return nombreSalon;
    }

    public void setNombreSalon(String nombreSalon) {
        this.nombreSalon = nombreSalon;
    }

    public int getCantidadMaxima() {
        return cantidadMaxima;
    }

    public void setCantidadMaxima(int cantidadMaxima) {
        this.cantidadMaxima = cantidadMaxima;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
