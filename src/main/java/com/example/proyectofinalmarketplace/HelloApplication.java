package com.example.proyectofinalmarketplace;

import com.example.proyectofinalmarketplace.exceptions.ProductoInvalidoException;
import com.example.proyectofinalmarketplace.exceptions.ProductoYaExisteException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    private static final int WIDTH = 600;   // Ancho estándar
    private static final int HEIGHT = 400;   // Alto estándar
    private Marketplace marketplace;

    public static double getWidth() {
        return WIDTH;
    }

    public static double getHeight() {
        return HEIGHT;
    }

    @Override
    public void start(Stage stage) throws IOException, ProductoYaExisteException, ProductoInvalidoException {
        Utilities utilities = Utilities.getInstance();

        HiloDeserializacion<Admin> hiloAdmins = new HiloDeserializacion<>("C:\\td\\persistenciaadministradores.dat");
        HiloDeserializacion<Vendedor> hiloVendedores = new HiloDeserializacion<>("C:\\td\\persistenciavendedores.dat");
        HiloDeserializacion<Producto> hiloProductos = new HiloDeserializacion<>("C:\\td\\persistenciaproductos.dat");
        HiloDeserializacion<Categoria> hiloCategorias = new HiloDeserializacion<>("C:\\td\\persistenciacategorias.dat");
        HiloDeserializacion<Usuario> hiloUsuarios = new HiloDeserializacion<>("C:\\td\\persistenciausuarios.dat");


        // Iniciar hilos
        hiloAdmins.start();
        hiloVendedores.start();
        hiloProductos.start();
        hiloCategorias.start();
        hiloUsuarios.start();

        // Esperar a que los hilos terminen
        try {
            hiloAdmins.join();
            hiloVendedores.join();
            hiloProductos.join();
            hiloCategorias.join();
            hiloUsuarios.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Obtener las listas deserializadas o listas vacías en caso de error
        List<Admin> administradores = hiloAdmins.getListaDeserializada() != null ? hiloAdmins.getListaDeserializada() : new ArrayList<>();
        List<Vendedor> vendedores = hiloVendedores.getListaDeserializada() != null ? hiloVendedores.getListaDeserializada() : new ArrayList<>();
        List<Producto> productos = hiloProductos.getListaDeserializada() != null ? hiloProductos.getListaDeserializada() : new ArrayList<>();
        List<Categoria> categorias = hiloCategorias.getListaDeserializada() != null ? hiloCategorias.getListaDeserializada() : new ArrayList<>();
        List<Usuario> usuarios = hiloUsuarios.getListaDeserializada() != null ? hiloUsuarios.getListaDeserializada() : new ArrayList<>();
        // Verificar si alguna lista está vacía, y en ese caso, cargar datos iniciales
        if (administradores.isEmpty() && vendedores.isEmpty() && productos.isEmpty() && categorias.isEmpty() && usuarios.isEmpty()) {
            marketplace = DatosIniciales.crearMarketplaceConDatosIniciales();
        } else {
            marketplace = new Marketplace("JavaDictos");
            marketplace.setAdministradores(administradores);
            marketplace.setVendedores(vendedores);
            marketplace.setProductos(productos);
            marketplace.setCategorias(categorias);
            marketplace.setUsuarios(usuarios);
        }

        MarketplaceManager.setMarketplaceInstance(marketplace);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);

        stage.setTitle("Hello!");
        stage.setScene(scene);

        // Evento para manejar el cierre de la aplicación
        stage.setOnCloseRequest(event -> {
            // Serialización de datos en la salida
            HiloSerializacion<Admin> hiloSerializacionAdmin = new HiloSerializacion<>(marketplace.getAdministradores(), "administradores.dat");
            HiloSerializacion<Vendedor> hiloSerializacionVendedor = new HiloSerializacion<>(marketplace.getVendedores(), "vendedores.dat");
            HiloSerializacion<Producto> productoHiloSerializacion = new HiloSerializacion<>(marketplace.getProductos(), "productos.dat");
            HiloSerializacion<Categoria> categoriaHiloSerializacion = new HiloSerializacion<>(marketplace.getCategorias(), "categorias.dat");
            HiloSerializacion<Usuario> usuarioHiloSerializacion = new HiloSerializacion<>(marketplace.getUsuarios(), "usuarios.dat");
            try {
                utilities.guardarListaTXT(administradores,"administradores.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                utilities.guardarListaTXT(vendedores,"vendedores.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                utilities.guardarListaTXT(productos,"productos.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                utilities.guardarListaTXT(categorias,"categorias.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                utilities.guardarListaTXT(usuarios,"usuarios.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            hiloSerializacionAdmin.start();
            hiloSerializacionVendedor.start();
            productoHiloSerializacion.start();
            categoriaHiloSerializacion.start();
            usuarioHiloSerializacion.start();
        });

        stage.show();
    }

    /**public static void main(String[] args) {
        launch();
    }**/
    public static void main(String[] args) {
        Utilities utilities = Utilities.getInstance();
        try {

            List<Admin> administradores = utilities.deserializarLista("C:\\td\\persistenciaadministradores.dat");
            System.out.println("Administradores deserializados: " + administradores);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        launch();
    }

}
