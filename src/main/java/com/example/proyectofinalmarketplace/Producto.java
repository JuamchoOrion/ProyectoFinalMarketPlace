package com.example.proyectofinalmarketplace;

import com.example.proyectofinalmarketplace.exceptions.ComentarioInvalidoException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private String codigo;
    private String imagen;
    private String precio;
    private LocalDateTime fechaPublicacion;
    private Categoria categoria;
    private int likes;
    private Estado estado;
    private List<Comentario> comentarios ;


    public Producto(String nombre, String codigo, String imagen, String precio, LocalDateTime fechaPublicacion, Categoria categoria, int likes,Estado estado) {
    this.nombre = nombre;
    this.codigo = codigo;
    this.imagen = imagen;
    this.precio = precio;
    this.fechaPublicacion = fechaPublicacion;
    this.categoria = categoria;
    this.likes = likes;
    this.estado =estado;
    this.comentarios = new ArrayList<>();

}
    public void incrementarMeGusta() {
        likes++;
    }
    public void decrementarMeGusta() {
        likes--;
    }


    public void agregarComentario(Comentario comentario) throws ComentarioInvalidoException {

        if(!comentario.getTexto().isEmpty()){
            comentarios.add(comentario);
        }
        throw new ComentarioInvalidoException("El comentario no puede estar vacio");
    }

    public List<Comentario> getComentarios() {
            return comentarios;
    }

public String getNombre() {
    return nombre;
}

public String getCodigo() {
    return codigo;
    }
public String getImagen() {
    return imagen;
}
public String getPrecio() {
    return precio;
}
public LocalDateTime getFechaPublicacion() {
    return fechaPublicacion;
}
public int getLikes() {
    return likes;
    }

    public Estado getEstado() {
        return estado;
    }

   public void setEstado(Estado estado) {
    this.estado = estado;
   }
   public void setNombre(String nombre) {
    this.nombre = nombre;
   }
   public void setCodigo(String codigo) {
    this.codigo = codigo;
   }
   public void setImagen(String imagen) {
    this.imagen = imagen;
   }
   public void setPrecio(String precio) {
    this.precio = precio;
   }
   public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
    this.fechaPublicacion = fechaPublicacion;
   }
   public void setLikes(int likes) {
    this.likes = likes;
   }
   public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
   }
    @Override
    public String toString() {
        return "Producto [nombre=" + nombre + ", codigo=" + codigo + ", imagen=" + imagen + ", precio=" + precio
                + ", fechaPublicacion=" + fechaPublicacion + ", categoria=" + categoria + ", likes=" + likes
                + ", estado=" + estado + "]";
    }
}
