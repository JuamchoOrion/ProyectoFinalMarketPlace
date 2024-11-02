package com.example.proyectofinalmarketplace;


import java.io.Serializable;

public abstract class Usuario implements Serializable {
    public String nombre;
    private String contrasenia;

    // Constructor
    public Usuario(String nombre, String contrasenia) {
        this.nombre = nombre;
        this.contrasenia = contrasenia;
    }


    // Métodos getter
    public String getNombre() {
        return nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    // Métodos setter
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
