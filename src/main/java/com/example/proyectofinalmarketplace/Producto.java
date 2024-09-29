package com.example.proyectofinalmarketplace;

import java.time.LocalDate;

public class Producto  {
    private String nombre;
    private String codigo;
    private String imagen;
    private String precio;
    private LocalDate fechaPublicacion;
    private Categoria categoria;
    private int likes;
    private Estado estado;

public Producto(String nombre, String codigo, String imagen, String precio, LocalDate fechaPublicacion, Categoria categoria, int likes,Estado estado) {
    this.nombre = nombre;
    this.codigo = codigo;
    this.imagen = imagen;
    this.precio = precio;
    this.fechaPublicacion = fechaPublicacion;
    this.categoria = categoria;
    this.likes = likes;
    this.estado =estado;

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
public LocalDate getFechaPublicacion() {
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
   public void setFechaPublicacion(LocalDate fechaPublicacion) {
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