package com.example.proyectofinalmarketplace;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class HiloSerializacion<T extends Serializable> extends Thread {
    private List<T> lista;
    private String nombreArchivo;
    public HiloSerializacion(List<T> lista, String nombreArchivo) {
        this.lista = lista;
        this.nombreArchivo = nombreArchivo;
    }

    @Override
    public void run() {
        Utilities utilities = Utilities.getInstance();
        try {
            utilities.generarArchivoDat(lista, nombreArchivo);
        } catch (IOException e) {
            utilities.logSevere("Error al serializar la lista de administradores: " + e.getMessage());
        }
    }
}
