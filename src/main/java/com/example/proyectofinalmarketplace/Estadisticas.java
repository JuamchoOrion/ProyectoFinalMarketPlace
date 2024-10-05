package com.example.proyectofinalmarketplace;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Estadisticas {
    private List<Vendedor> listaVendedores;
    private List<Producto> listaProductos;

    public Estadisticas() {
        listaVendedores = new ArrayList<Vendedor>();
        listaProductos = new ArrayList<Producto>();
    }

    // Retorna una lista de productos publicados por un vendedor
    public List<Producto> productosPorVendedor(Vendedor vendedor) {
        List<Producto> productosPublicados = new ArrayList<>();

        for (Producto producto : vendedor.getListaProductos()) {
            if (producto.getEstado() == Estado.PUBLICADO) {
                productosPublicados.add(producto);
            }
        }

        return productosPublicados;
    }

    // Retorna una lista de contactos por vendedor
    public List<Usuario> contactosCadaVendedor(Vendedor vendedor) {
        return new ArrayList<>(vendedor.getListaContactos());
    }

    // Retorna una lista de los diez productos con más likes
    public List<Producto> topDiezProdLike(Marketplace marketplace) {
        List<Producto> productosOrdenados = new ArrayList<>(marketplace.getListaProductos());

        // Ordenar los productos por la cantidad de likes en orden descendente
        productosOrdenados.sort((p1, p2) -> Integer.compare(p2.getLikes(), p1.getLikes()));

        // Obtener los diez productos con más likes (si hay menos de 10, se toma la cantidad disponible)
        return productosOrdenados.stream().limit(10).toList();
    }
}
