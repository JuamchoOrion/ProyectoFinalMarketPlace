package com.example.proyectofinalmarketplace;

import java.io.Serializable;
import java.time.LocalDate;

public class Comentario implements Serializable {
    private String texto;
    private Vendedor autor;
    private LocalDate fecha;

    public Comentario(String texto, Vendedor autor) {
        this.texto = texto;
        this.autor = autor;
        this.fecha = LocalDate.now();
    }

    // Getters y Setters
    public String getTexto() { return texto; }
    public Vendedor getAutor() { return autor; }
    public LocalDate getFecha() { return fecha; }
}

