package com.example.proyectofinalmarketplace;

import com.example.proyectofinalmarketplace.exceptions.ProductoInvalidoException;
import com.example.proyectofinalmarketplace.exceptions.ProductoYaExisteException;
import com.example.proyectofinalmarketplace.exceptions.UsuarioYaExisteException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private Marketplace marketplace;

    public static double getWidth() {
        return WIDTH;
    }

    public static double getHeight() {
        return HEIGHT;
    }

    @Override
    public void start(Stage stage) throws IOException, ProductoYaExisteException, ProductoInvalidoException, UsuarioYaExisteException {
        Utilities utilities = Utilities.getInstance();

        // Deserialización en hilos
        HiloDeserializacion<Admin> hiloAdmins = new HiloDeserializacion<>("C:\\td\\persistencia\\administradores.dat");
        HiloDeserializacion<Vendedor> hiloVendedores = new HiloDeserializacion<>("C:\\td\\persistencia\\vendedores.dat");
        HiloDeserializacion<Producto> hiloProductos = new HiloDeserializacion<>("C:\\td\\persistencia\\productos.dat");
        HiloDeserializacion<Categoria> hiloCategorias = new HiloDeserializacion<>("C:\\td\\persistencia\\categorias.dat");
        HiloDeserializacion<Usuario> hiloUsuarios = new HiloDeserializacion<>("C:\\td\\persistencia\\usuarios.dat");

        hiloAdmins.start();
        hiloVendedores.start();
        hiloProductos.start();
        hiloCategorias.start();
        hiloUsuarios.start();

        try {
            hiloAdmins.join();
            hiloVendedores.join();
            hiloProductos.join();
            hiloCategorias.join();
            hiloUsuarios.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Admin> administradores = hiloAdmins.getListaDeserializada() != null ? hiloAdmins.getListaDeserializada() : new ArrayList<>();
        List<Vendedor> vendedores = hiloVendedores.getListaDeserializada() != null ? hiloVendedores.getListaDeserializada() : new ArrayList<>();
        List<Producto> productos = hiloProductos.getListaDeserializada() != null ? hiloProductos.getListaDeserializada() : new ArrayList<>();
        List<Categoria> categorias = hiloCategorias.getListaDeserializada() != null ? hiloCategorias.getListaDeserializada() : new ArrayList<>();
        List<Usuario> usuarios = hiloUsuarios.getListaDeserializada() != null ? hiloUsuarios.getListaDeserializada() : new ArrayList<>();

        marketplace = new Marketplace("JavaDictos");
        marketplace.setAdministradores(administradores);
        marketplace.setVendedores(vendedores);
        marketplace.setProductos(productos);
        marketplace.setCategorias(categorias);
        marketplace.setUsuarios(usuarios);
        Vendedor vendedorExtra = DatosIniciales.crearVendedorConProductos(marketplace);
        marketplace.getVendedores().add(vendedorExtra);
        marketplace.getUsuarios().add(vendedorExtra);

        MarketplaceManager.setMarketplaceInstance(marketplace);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);

        stage.setTitle("Hello!");
        stage.setScene(scene);

        stage.setOnCloseRequest(event -> {
            try {
                serializarDatos(marketplace);
            } catch (IOException | ProductoYaExisteException | ProductoInvalidoException e) {
                throw new RuntimeException(e);
            }
        });

        stage.show();
    }

    private void serializarDatos(Marketplace marketplace) throws IOException, ProductoYaExisteException, ProductoInvalidoException {
        Utilities utilities = Utilities.getInstance();
        HiloSerializacion<Admin> hiloSerializacionAdmin = new HiloSerializacion<>(marketplace.getAdministradores(), "administradores.dat");
        HiloSerializacion<Vendedor> hiloSerializacionVendedor = new HiloSerializacion<>(marketplace.getVendedores(), "vendedores.dat");
        HiloSerializacion<Producto> productoHiloSerializacion = new HiloSerializacion<>(marketplace.getProductos(), "productos.dat");
        HiloSerializacion<Categoria> categoriaHiloSerializacion = new HiloSerializacion<>(marketplace.getCategorias(), "categorias.dat");
        HiloSerializacion<Usuario> usuarioHiloSerializacion = new HiloSerializacion<>(marketplace.getUsuarios(), "usuarios.dat");

        utilities.generarArchivoXML(marketplace.getListaVendedores(), "vendedores.xml");


        try {
            utilities.guardarListaTXT(marketplace.getAdministradores(), "administradores.txt");
            utilities.guardarListaTXT(marketplace.getVendedores(), "vendedores.txt");
            utilities.guardarListaTXT(marketplace.getProductos(), "productos.txt");
            utilities.guardarListaTXT(marketplace.getUsuarios(), "usuarios.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        hiloSerializacionAdmin.start();
        hiloSerializacionVendedor.start();
        productoHiloSerializacion.start();
        categoriaHiloSerializacion.start();
        usuarioHiloSerializacion.start();
        try {
            hiloSerializacionAdmin.join();
            hiloSerializacionVendedor.join();
            productoHiloSerializacion.join();
            categoriaHiloSerializacion.join();
            usuarioHiloSerializacion.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
