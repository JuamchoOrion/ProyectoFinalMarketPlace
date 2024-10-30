package com.example.proyectofinalmarketplace;

import java.io.IOException;
import java.util.List;

public class HiloDeserializacionXML<T> extends Thread {
    private final String nombreArchivo;
    private List<T> listaDeserializada;
    private Exception exception;

    public HiloDeserializacionXML(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    @Override
    public void run() {
        Utilities utilities = Utilities.getInstance();
        try {
            listaDeserializada = utilities.deserializarListaXML(nombreArchivo);
            utilities.logInfo("Datos XML deserializados correctamente desde el archivo: " + nombreArchivo);
        } catch (IOException e) {
            exception = e;
            utilities.logSevere("Error al deserializar datos XML: " + e.getMessage());
        }
    }

    public List<T> getListaDeserializada() {
        return listaDeserializada;
    }

    public Exception getException() {
        return exception;
    }
}



