package com.example.proyectofinalmarketplace;
import java.util.ArrayList;
import java.util.List;

public class Muro {
    private List<String> mensajes;
    private List<String> comentarios;
    private List<Producto> productosPublicados;

    public Muro() {
        mensajes = new ArrayList<>();
        comentarios = new ArrayList<>();
        productosPublicados = new ArrayList<>();
    }
    public List<String> getMensajes() {
        return mensajes;
    }
    public void setMensajes(List<String> mensajes) { this.mensajes = mensajes; }

    public List<String> getComentarios() {return comentarios; }

    public void setComentarios(List<String> comentarios) { this.comentarios = comentarios; }

    public List<Producto> getProductosPublicados() {return productosPublicados;
    }

    public void setProductosPublicados(List<Producto> productosPublicados) {this.productosPublicados = productosPublicados;
    }
}
