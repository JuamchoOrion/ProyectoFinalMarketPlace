package com.example.proyectofinalmarketplace;

import com.example.proyectofinalmarketplace.exceptions.ProductoInvalidoException;
import com.example.proyectofinalmarketplace.exceptions.ProductoYaExisteException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatosIniciales {
    /**
     * Crea una instancia de Marketplace con datos iniciales.
     *
     * @return Un objeto Marketplace preconfigurado con usuarios iniciales.
     */

    public static Vendedor vendedorconProductos() throws ProductoYaExisteException, ProductoInvalidoException {
        Marketplace marketplace = new Marketplace("Javadictos");
        Vendedor v1 = new Vendedor("b", "1", "as", "b", "asdas");
        Categoria c1 = new Categoria("Pelotas", "Esferas de plastico para jugar");
        Producto p1 = new Producto("Pelota Roja", "123", "/imagenes/img.png", "12", LocalDate.of(2024, 7, 11), c1, 2, Estado.PUBLICADO);
        v1.agregarProducto(marketplace, p1);
        return v1;
    }
    public static Marketplace crearMarketplaceConDatosIniciales() throws ProductoYaExisteException, ProductoInvalidoException {
        List<Usuario> usuarios = new ArrayList<>();
        List<Admin> administradores = new ArrayList<>();
        List<Vendedor> vendedores = new ArrayList<>();
        List<Producto> productos = new ArrayList<>();
        List<Categoria> categorias = new ArrayList<>();
        Marketplace marketplace = new Marketplace("JavaDictos");

        // Administradores existentes
        Admin a1 = new Admin("a", "a");
        usuarios.add(a1);
        administradores.add(a1);

        Admin a2 = new Admin("Juancho", "etesech");
        usuarios.add(a2);
        administradores.add(a2);

        Admin a3 = new Admin("Maya", "elpepe");
        usuarios.add(a3);
        administradores.add(a3);

        // Vendedores existentes
        Vendedor v1 = new Vendedor("b", "1", "as", "b", "asdas");
        Vendedor v2 = new Vendedor("c", "1", "as", "c", "asdas");
        usuarios.add(v1);
        usuarios.add(v2);
        vendedores.add(v1);
        vendedores.add(v2);

        // Categorías existentes
        Categoria c1 = new Categoria("Pelotas", "Esferas de plastico para jugar");
        Categoria c2 = new Categoria("Maletas", "Para guardar cosas");
        Categoria c3 = new Categoria("Libros", "Pa leer");
        categorias.add(c1);
        categorias.add(c2);
        categorias.add(c3);

        // Productos existentes
        Producto p1 = new Producto("Pelota Roja", "123", "/imagenes/img.png", "12", LocalDate.of(2024, 7, 11), c1, 2, Estado.PUBLICADO);
        Producto p2 = new Producto("Pelota Verde", "432", "/imagenes/img.png", "18", LocalDate.of(2024, 7, 11), c1, 4, Estado.PUBLICADO);
        Producto p3 = new Producto("Maleta", "456", "/imagenes/logo.png", "10", LocalDate.of(2024, 7, 11), c2, 8, Estado.PUBLICADO);
        Producto p4 = new Producto("Libro", "098", "/imagenes/logo.png", "82", LocalDate.of(2024, 7, 11), c3, 2, Estado.PUBLICADO);
        productos.add(p1);
        productos.add(p2);
        productos.add(p3);
        productos.add(p4);

        // Asignación de productos a vendedores
        v1.agregarProducto(marketplace, p1);
        v1.agregarProducto(marketplace, p2);
        v1.agregarProducto(marketplace, p3);
        v1.agregarProducto(marketplace, p4);

        // Configuración de Marketplace con los datos iniciales
        marketplace.setUsuarios(usuarios);
        marketplace.setAdministradores(administradores);
        marketplace.setVendedores(vendedores);
        marketplace.setProductos(productos);
        marketplace.setCategorias(categorias);

        return marketplace;
    }
}
