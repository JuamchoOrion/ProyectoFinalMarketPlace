package com.example.proyectofinalmarketplace;


import java.io.Serializable;

public abstract class Usuario implements Serializable {
    public String nombre;
    private String contrasenia;

    public Usuario(String nombre, String contrasenia) {
        this.nombre = nombre;
        this.contrasenia = contrasenia;
    }

    public String getNombre() {

        return nombre;
    }

    public String getContrasenia() {

        return contrasenia;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    public void setContrasenia(String contrasenia) {

        this.contrasenia = contrasenia;
    }
}
