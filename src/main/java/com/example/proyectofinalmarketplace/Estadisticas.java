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

    public String productosPorVendedor(List<Vendedor> vendedores) {
        StringBuilder resultado = new StringBuilder("Productos publicados por cada vendedor:\n");

        for (Vendedor vendedor : vendedores) {
            int cantidadPublicados = 0;

            for (Producto producto : vendedor.getListaProductos()) {
                if (producto.getEstado() == Estado.PUBLICADO) {
                    cantidadPublicados++;
                }
            }
            resultado.append("Vendedor: ")
                    .append(vendedor.getNombre())
                    .append(", Productos publicados: ")
                    .append(cantidadPublicados)
                    .append("\n");
        }
        return resultado.toString();
    }
    public String contactosCadaVendedor(List<Vendedor> vendedores) {
        StringBuilder resultado = new StringBuilder("Contactos por vendedor:\n");

        for (Vendedor vendedor : vendedores) {
            int cantidadContactos = vendedor.getListaContactos().size();

            resultado.append("Vendedor: ")
                    .append(vendedor.getNombre())
                    .append(", Contactos: ")
                    .append(cantidadContactos)
                    .append("\n");
        }

        return resultado.toString();
    }
    public String topDiezProdLike(Marketplace marketplace) {
        // Ordenar los productos por la cantidad de likes en orden descendente
        List<Producto> productosOrdenados = new ArrayList<>(marketplace.getListaProductos());

        productosOrdenados.sort((p1, p2) -> Integer.compare(p2.getLikes(), p1.getLikes()));

        // Obtener los diez productos con más likes (si hay menos de 10, se toma la cantidad disponible)
        List<Producto> topDiez = productosOrdenados.stream()
                .limit(10)
                .toList();

        StringBuilder resultado = new StringBuilder("Top 10 productos con más likes:\n");
        for (Producto producto : topDiez) {
            resultado.append("Producto: ")
                    .append(producto.getNombre())
                    .append(", Likes: ")
                    .append(producto.getLikes())
                    .append("\n");
        }

        return resultado.toString();
    }

}
