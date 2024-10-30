package com.example.proyectofinalmarketplace;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Utilities implements Serializable{
    private static final long serialVersionUID = 1L;
    // Instancia única de la clase (para Singleton)
    private static Utilities instanciaUnica;
    String logFilePath = "C:/td/persistencia/log/log.txt";


    // Ruta base para los archivos de persistencia
    private static final String DIRECTORIO_BASE = "C:\\td\\persistencia\\";
    private static final String direccionRespaldo = "C:\\td\\persistencia\\respaldo\\";




    // Método para obtener la única instancia de la clase (patrón Singleton)
    public static Utilities getInstance() {
        if (instanciaUnica == null) {
            synchronized (Utilities.class) {
                if (instanciaUnica == null) {
                    instanciaUnica = new Utilities();
                }
            }
        }
        return instanciaUnica;
    }

    // Logger que manejará los mensajes de log
    private static final Logger logger = Logger.getLogger(Utilities.class.getName());

    // Métodos para escribir en el log con diferentes niveles de severidad
    public void logInfo(String mensaje) {
        escribirLog("INFO: " + mensaje);
    }

    public void logWarning(String mensaje) {
        escribirLog("WARNING: " + mensaje);
    }

    public void logSevere(String mensaje) {
        escribirLog("ERROR: " + mensaje);
    }

    private void escribirLog(String mensaje) {
        verificarOCrearDirectorio();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write(timestamp + " - " + mensaje);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de log: " + e.getMessage());
        }
    }

    // Método para verificar o crear el directorio donde se guardarán los archivos
    private void verificarOCrearDirectorio() {
        File archivo = new File(logFilePath);
        File directorio = archivo.getParentFile(); // Obtener el directorio padre del archivo

        if (directorio != null) {
            if (!directorio.exists()) {
                // Intentar crear toda la estructura de directorios
                if (directorio.mkdirs()) {
                    System.out.println("Directorio creado: " + directorio.getAbsolutePath());
                } else {
                    System.err.println("No se pudo crear el directorio: " + directorio.getAbsolutePath());
                }
            } else {
                System.out.println("El directorio ya existe: " + directorio.getAbsolutePath());
            }
        } else {
            System.err.println("No se pudo obtener el directorio para la ruta: " + logFilePath);
        }
    }

    // Método para escribir una lista en un archivo de texto
    public void escribirListaEnArchivo(String nombreArchivo, List<?> lista) throws IOException {
        verificarOCrearDirectorio();  // Asegura que el directorio exista

        // Crea el archivo en la ruta especificada
        File archivo = new File(DIRECTORIO_BASE + File.separator + nombreArchivo);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            // Itera sobre los elementos de la lista y los escribe en el archivo
            for (Object elemento : lista) {
                writer.write(elemento.toString());  // Escribe cada elemento en el archivo
                writer.newLine();  // Añade una nueva línea después de cada elemento
            }

            // Realiza un último flush para asegurarse de que todo se escribe en disco
            writer.flush();

            logInfo("Archivo escrito con éxito: " + nombreArchivo);
        } catch (IOException e) {
            logSevere("Error al escribir en el archivo: " + nombreArchivo + ". Error: " + e.getMessage());
            throw e;
        }
    }



    // Método para serializar un objeto a un archivo
    public boolean serializarObjeto(String direccionArchivo, Serializable objeto) {
        boolean sw = false;

        // Intenta serializar el objeto y guardarlo en el archivo especificado
        try (FileOutputStream fos = new FileOutputStream(direccionArchivo);
             ObjectOutputStream salida = new ObjectOutputStream(fos)) {

            salida.writeObject(objeto);  // Escribe el objeto serializado en el archivo
            sw = true;
            logInfo("Objeto serializado con éxito: " + direccionArchivo);
        } catch (Exception e) {
            logSevere("Error al serializar objeto: " + e.getMessage());
        }
        return sw;  // Devuelve true si la serialización fue exitosa
    }

    // Método genérico para deserializar una lista de objetos desde un archivo
    public <T> List<T> deserializarLista(String rutaArchivo) throws IOException, ClassNotFoundException {
        List<T> lista = null;

        // Deserializa el archivo y lo castea a una lista de T (tipo genérico)
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            lista = (List<T>) ois.readObject();  // Deserializa el objeto y lo convierte a lista
        }

        return lista;  // Retorna la lista deserializada
    }

    // Método para serializar un objeto a XML usando XMLEncoder
    public boolean serializarObjetoXML(String direccionArchivo, Serializable objeto) {
        boolean sw = false;
        try (FileOutputStream fos = new FileOutputStream(direccionArchivo);
             XMLEncoder encoder = new XMLEncoder(fos)) {
            encoder.writeObject(objeto);
            sw = true;
            logInfo("Objeto serializado a XML con éxito: " + direccionArchivo);
        } catch (IOException e) {
            logSevere("Error al serializar objeto a XML: " + e.getMessage());
        }
        return sw;
    }

    // Método genérico para deserializar cualquier lista desde XML usando XMLDecoder
    public <T> List<T> deserializarListaXML(String rutaArchivo) throws IOException {
        List<T> lista;
        try (FileInputStream fis = new FileInputStream(rutaArchivo);
             XMLDecoder decoder = new XMLDecoder(fis)) {
            lista = (List<T>) decoder.readObject();
            logInfo("Lista deserializada con éxito desde: " + rutaArchivo);
        } catch (IOException e) {
            logSevere("Error al deserializar objeto desde XML: " + e.getMessage());
            throw e;
        }
        return lista;
    }

    // Método genérico para serializar cualquier lista a un archivo XML
    public <T extends Serializable> void generarArchivoXML(List<T> lista, String nombreArchivo) throws IOException {
        if (lista != null) {
            // Serializar en la primera dirección
            boolean result1 = serializarObjetoXML(DIRECTORIO_BASE + nombreArchivo, (Serializable) lista);

            // Obtener la fecha actual y formatearla
            LocalDateTime fechaActual = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String fechaFormateada = fechaActual.format(formatter);

            // Generar el nombre de archivo de respaldo con la fecha
            String nombreArchivoRespaldo = nombreArchivo.replace(".xml", "") + "_" + fechaFormateada + ".xml";

            // Serializar en la segunda dirección de respaldo
            boolean result2 = serializarObjetoXML(direccionRespaldo + nombreArchivoRespaldo, (Serializable) lista);

            // Verificar resultados de ambas serializaciones
            if (!result1 || !result2) {
                throw new IOException("Error al serializar la lista a XML en una o ambas ubicaciones");
            }
        } else {
            throw new IllegalArgumentException("La lista no puede ser nula");
        }
    }
    public <T> void guardarListaTXT(List<T> lista, String nombreArchivo) throws IOException {
        if (lista == null || lista.isEmpty()) {
            throw new IllegalArgumentException("La lista no puede ser nula o vacía");
        }

        // Ruta fija para guardar los archivos
        String rutaBase = "C:/td/persistencia/archivos/";

        // Combinar la ruta fija y el nombre de archivo especificado
        String rutaCompleta = rutaBase + nombreArchivo;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaCompleta))) {
            for (T objeto : lista) {
                // Usa reflection para obtener y escribir los atributos de cada objeto
                StringBuilder linea = new StringBuilder();
                Field[] fields = objeto.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true); // Permite acceder a atributos privados
                    Object valor = field.get(objeto); // Obtiene el valor del atributo
                    linea.append(field.getName()).append(": ").append(valor).append(" | ");
                }
                writer.write(linea.toString());
                writer.newLine();
            }
            logInfo("Datos guardados en " + rutaCompleta + " con éxito.");
        } catch (IOException | IllegalAccessException e) {
            logSevere("Error al guardar datos en archivo .txt: " + e.getMessage());
            throw new IOException("Error al guardar datos en archivo .txt", e);
        }
    }



    // metodo para generar el archivo de Administradores
    public <T extends Serializable> void generarArchivoDat(List<?> list, String nombreArchivo) throws IOException {
        if (list != null) {
            // Serializa en la ruta original
            boolean result = serializarObjeto(DIRECTORIO_BASE + nombreArchivo, (Serializable) list);
            if (!result) {
                throw new IOException("Error al serializar en la ruta principal");
            }

            // Genera el nombre de archivo con la fecha actual
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String fechaActual = LocalDateTime.now().format(formatter);
            String nombreArchivoRespaldo = nombreArchivo.replace(".dat", "_" + fechaActual + ".dat");

            // Serializa en la ruta de respaldo
            boolean resultRespaldo = serializarObjeto(direccionRespaldo + nombreArchivoRespaldo, (Serializable) list);
            if (!resultRespaldo) {
                throw new IOException("Error al serializar en la ruta de respaldo");
            }
        } else {
            throw new IllegalArgumentException("La lista no puede ser nula");
        }
    }
    //asi con todos...
}