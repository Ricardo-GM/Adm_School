package com.example.adm_school.Models;

public class RegisterRequest {
    private String nombre;
    private String correo;
    private String password;
    private String telefono;
    private String rol;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getId_rol() {
        return rol;
    }

    public void setId_rol(String id_rol) {
        this.rol = id_rol;
    }
}
