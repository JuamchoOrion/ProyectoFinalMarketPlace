package com.example.proyectofinalmarketplace;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class HiloSerializacionXML<T extends Serializable> extends Thread {
    private List<T> lista;
    private String nombreArchivo; // Nombre del archivo a crear

    public HiloSerializacionXML(List<T> lista, String nombreArchivo) {
        this.lista = lista;
        this.nombreArchivo = nombreArchivo;
    }

    @Override
    public void run() {
        Utilities utilities = Utilities.getInstance();
        try {
            utilities.generarArchivoXML(lista, nombreArchivo); // Usa el nombre de archivo proporcionado
            utilities.logInfo("Serialización a XML completada exitosamente en: " + nombreArchivo);
        } catch (IOException e) {
            utilities.logSevere("Error al serializar la lista: " + e.getMessage());
        }
    }
}
