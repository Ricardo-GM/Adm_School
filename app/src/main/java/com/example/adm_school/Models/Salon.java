package com.example.adm_school.Models;

public class Salon {
    private int id;
    private String nombreSalon;
    private int cantidadMaxima;
    private String estado;


    public Salon(int id, String nombreSalon, int cantidadMaxima, String estado) {
        this.id = id;
        this.nombreSalon = nombreSalon;
        this.cantidadMaxima = cantidadMaxima;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
