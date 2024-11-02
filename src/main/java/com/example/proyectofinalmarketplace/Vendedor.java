package com.example.proyectofinalmarketplace;
import com.example.proyectofinalmarketplace.exceptions.ListaContactosLlenaException;
import com.example.proyectofinalmarketplace.exceptions.ProductoNoEncontradoException;
import com.example.proyectofinalmarketplace.exceptions.ProductoYaExisteException;
import com.example.proyectofinalmarketplace.exceptions.ProductoInvalidoException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.time.LocalDate;

public class Vendedor extends Usuario implements Serializable{

    private static final long serialVersionUID = 1L;
    private String cedula;
    private String descripcion;
    private String direccion;
    private List<Vendedor> listaContactos;
    private List<Producto> listaProductos;
    private List<Vendedor> solicitudesPendientes = new ArrayList<>();
    transient  Utilities logger = Utilities.getInstance();

    public void setListaContactos(List<Vendedor> listaContactos) {
        this.listaContactos = listaContactos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public List<Vendedor> getSolicitudesPendientes() {
        return solicitudesPendientes;
    }

    public void setSolicitudesPendientes(List<Vendedor> solicitudesPendientes) {
        this.solicitudesPendientes = solicitudesPendientes;
    }

    public Vendedor(String nombre, String cedula, String descripcion, String contrasenia, String direccion) {
        super(nombre, contrasenia);
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.listaContactos = new ArrayList<>();
        this.listaProductos = new ArrayList<>();
        this.cedula = cedula;
    }

    public String getDescripcion() {
        return descripcion;

    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;

    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;

    }

    public List<Vendedor> getListaContactos() {
        return listaContactos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;

    }
    public void darMeGusta(Producto producto) {
        producto.incrementarMeGusta();
        logger.logInfo(this.getNombre() + " dio 'me gusta' al producto " + producto.getNombre());
    }

    @Override
    public String getNombre() {
        return super.getNombre();
    }

    public void enviarSolicitudContacto(Vendedor vendedorDestino) throws ListaContactosLlenaException, Exception {
        if (listaContactos.contains(vendedorDestino)) {
            logger.logWarning("El contacto ya existe: " + vendedorDestino.getNombre());
            throw new Exception("El contacto ya existe en la lista.");
        }
        if (vendedorDestino.solicitudesPendientes.contains(this)) {
            logger.logWarning("Ya se ha enviado una solicitud a este vendedor: " + vendedorDestino.getNombre());
            throw new Exception("Ya se ha enviado una solicitud a este vendedor.");
        }
        if (listaContactos.size() < 10) {
            vendedorDestino.solicitudesPendientes.add(this);
            logger.logInfo("Solicitud de contacto enviada a: " + vendedorDestino.getNombre());
        } else {
            logger.logSevere("Lista de contactos llena. No se puede enviar solicitud a: " + vendedorDestino.getNombre());
            throw new ListaContactosLlenaException("No se pueden agregar más contactos. La lista ya tiene 10 contactos.");
        }
    }
    public void aceptarSolicitud(Vendedor vendedorOrigen) {
        if (solicitudesPendientes.contains(vendedorOrigen) && listaContactos.size() < 10 && vendedorOrigen.listaContactos.size() < 10) {
            // Añadir mutuamente a la lista de contactos de ambos vendedores
            listaContactos.add(vendedorOrigen);
            vendedorOrigen.listaContactos.add(this);

            // Remover la solicitud de la lista de pendientes
            solicitudesPendientes.remove(vendedorOrigen);

            logger.logInfo("Solicitud aceptada. Contactos agregados mutuamente entre: " + vendedorOrigen.getNombre() + " y " + this.getNombre());
        } else {
            logger.logWarning("No se puede aceptar la solicitud de: " + vendedorOrigen.getNombre() + ". Lista de contactos llena o ya existe.");
        }
    }
    public void rechazarSolicitud(Vendedor vendedorOrigen) {
        if (solicitudesPendientes.contains(vendedorOrigen)) {
            solicitudesPendientes.remove(vendedorOrigen);
            logger.logInfo("Solicitud rechazada de: " + vendedorOrigen.getNombre());
        }
    }
    //crea un producto
    public void agregarProducto(Marketplace marketplace,Producto producto) throws ProductoInvalidoException, ProductoYaExisteException {
        if (producto == null || producto.getCodigo() == null || producto.getCodigo().isEmpty()) {
            logger.logSevere("Intento de agregar un producto inválido.");
            throw new ProductoInvalidoException("El producto es inválido o el código es incorrecto.");
        }
        if (listaProductos.contains(producto)) {
            logger.logWarning("El producto ya existe en la lista: " + producto.getCodigo());
            throw new ProductoYaExisteException("El producto ya existe en la lista.");
        }
        marketplace.getListaProductos().add(producto);
        listaProductos.add(producto);
        logger.logInfo("Producto : " + producto.getNombre()+ " fue creado por: "+ nombre);
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void eliminarProducto(Producto producto) {
        if (listaProductos.remove(producto)) {
            logger.logInfo("Producto eliminado: " + producto.getNombre());
        } else {
            logger.logWarning("Intento de eliminar un producto que no existe: " + producto.getCodigo());
        }
    }
    //metodo para encontar un producto segun su codigo
    public Producto encontrarProductoPorCodigo(String codigo) throws ProductoNoEncontradoException {
        for (Producto producto : listaProductos) {
            if (producto.getCodigo().equals(codigo)) {
                logger.logInfo("Producto encontrado: " + producto.getNombre());
                return producto;
            }
        }
        logger.logInfo("No se encontró el producto con el código: " + codigo);
        throw new ProductoNoEncontradoException("No se encontró el producto con el código: " + codigo);
    }

// metodo para actualizar atributos del producto segun id
public void actualizarProducto(String codigo, String nombre, String imagen, String precio,
                               LocalDate fechaPublicacion, Categoria categoria, int likes, Estado estado)
        throws ProductoNoEncontradoException {
    Producto producto = encontrarProductoPorCodigo(codigo);
    producto.setNombre(nombre);
    producto.setImagen(imagen);
    producto.setPrecio(precio);
    producto.setFechaPublicacion(fechaPublicacion);
    producto.setCategoria(categoria);
    producto.setLikes(likes);
    producto.setEstado(estado);
    logger.logInfo("Producto actualizado: " + producto.getNombre());
}
    public List<Comentario> obtenerComentarios(Producto producto, Vendedor solicitante) {
        if (this.listaProductos.contains(producto) && esContacto(solicitante)) {
            return producto.getComentarios();
        } else {
            return Collections.emptyList();
        }
    }
    public void publicarComentario(Producto producto, Vendedor autor, String textoComentario) throws Exception {
        // Verificar si el producto pertenece a este vendedor
        if (this.listaProductos.contains(producto)) {
            if (this.esContacto(autor)) {
                Comentario comentario = new Comentario(textoComentario, autor);
                producto.agregarComentario(comentario);
                logger.logInfo("Comentario agregado por " + autor.getNombre() + " en el producto.");
            } else {
                throw new Exception("Solo los contactos pueden comentar en este producto.");
            }
        } else {
            throw new Exception("Este producto no pertenece a este vendedor.");
        }
    }
public boolean esContacto(Vendedor vendedor) {
        return listaContactos.contains(vendedor);
    }

    @Override
    public String toString() {
        return nombre;
    }


}
