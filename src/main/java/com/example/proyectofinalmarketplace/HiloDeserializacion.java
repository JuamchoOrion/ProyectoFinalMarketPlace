package com.example.proyectofinalmarketplace;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class HiloDeserializacion<T extends Serializable> extends Thread {
    private final String nombreArchivo;
    private List<T> listaDeserializada;
    private Exception exception;

    public HiloDeserializacion(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    @Override
    public void run() {
        Utilities utilities = Utilities.getInstance();
        try {
            listaDeserializada = utilities.deserializarLista(nombreArchivo);
            utilities.logInfo("Datos deserializados correctamente desde el archivo: " + nombreArchivo);
        } catch (IOException | ClassNotFoundException e) {
            exception = e;
            utilities.logSevere("Error al deserializar datos: " + e.getMessage());
        }
    }

    public List<T> getListaDeserializada() {
        return listaDeserializada;
    }

    public Exception getException() {
        return exception;
    }
}
