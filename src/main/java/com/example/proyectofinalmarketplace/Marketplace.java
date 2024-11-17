package com.example.proyectofinalmarketplace;

import com.example.proyectofinalmarketplace.exceptions.UsuarioNoExisteException;
import com.example.proyectofinalmarketplace.exceptions.UsuarioYaExisteException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
import java.util.List;
public class Marketplace implements Serializable {
    private String nombre;
    private List<Producto> productos;
    private List<Usuario> usuarios;
    private List<Admin> administradores;
    private List<Vendedor> vendedores;
    private List<Categoria> categorias;
    private Estadisticas estadistica;
    private Vendedor vendedorPorAgregar;
    private static Usuario usuarioActual;
    transient Utilities logger = Utilities.getInstance();


    public Marketplace(String nombre) {
        this.nombre = nombre;
        this.usuarios = new ArrayList<Usuario>();
        this.vendedores = new ArrayList<Vendedor>();
        this.administradores = new ArrayList<Admin>();
        this.productos = new ArrayList<>();
        this.estadistica = new Estadisticas();
    }




    public Usuario autenticacionUsuario(String nombre, String contrasenia) throws UsuarioNoExisteException  {
        for (Admin admin : administradores) {
            if (admin.getNombre().equals(nombre) && admin.getContrasenia().equals(contrasenia)) {
                setUsuarioActual(admin);
                logger.logInfo("El admin "+ admin.getNombre()+" ha ingresado a la app");
                return admin;
            }
        }
        for (Vendedor vendedor : vendedores) {
            if (vendedor.getNombre().equals(nombre) && vendedor.getContrasenia().equals(contrasenia)) {
                setUsuarioActual(vendedor);
                logger.logInfo("El vendedor "+ vendedor.getNombre()+" ha ingresado a la app");
                return vendedor;
            }
        }
        logger.logWarning("Intento de autenticación fallido para el usuario: " + nombre);
        throw new UsuarioNoExisteException("El usuario no existe o las credenciales son incorrectas.");
    }
    public List<Producto> topDiezProdLike() {
        List<Producto> productosOrdenados = new ArrayList<>(productos);

        productosOrdenados.sort((p1, p2) -> Integer.compare(p2.getLikes(), p1.getLikes()));

        return productosOrdenados.stream().limit(10).toList();
    }
    public List<Producto> topDiezProdLikeDeVendedor(Vendedor vendedor) {
        List<Producto> productosOrdenados = vendedor.getListaProductos();

        productosOrdenados.sort((p1, p2) -> Integer.compare(p2.getLikes(), p1.getLikes()));

        return productosOrdenados.stream().limit(10).toList();
    }

    public List<String> productosPorVendedor() {
        List<String> productosPorVendedor = new ArrayList<>();

        for (Vendedor vendedor : vendedores) {
            int cantidadProductosPublicados = 0;

            for (Producto producto : vendedor.getListaProductos()) {
                if (producto.getEstado() == Estado.PUBLICADO) {
                    cantidadProductosPublicados++;
                }
            }

            productosPorVendedor.add(vendedor.getNombre() + ": " + cantidadProductosPublicados + " productos publicados");
        }

        return productosPorVendedor;
    }

    // Método que retorna una lista con el nombre del vendedor y la cantidad de contactos
    public List<String> contactosCadaVendedor() {
        List<String> contactosPorVendedor = new ArrayList<>();

        for (Vendedor vendedor : vendedores) {
            int cantidadContactos = vendedor.getListaContactos().size();
            contactosPorVendedor.add(vendedor.getNombre() + ": " + cantidadContactos + " contactos");
        }

        return contactosPorVendedor;
    }

    public static void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public List<Producto> getListaProductos() {
        return productos;
    }
    public void setUsuarios(List<Usuario> usuarios) {this.usuarios = usuarios;
    }
    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    public List<Vendedor> getListaVendedores() {
        return vendedores;
    }
    public List<Categoria> getListaCategorias() {
        return categorias;
    }
    public void setVendedores(List<Vendedor> vendedores) {
        this.vendedores = vendedores;
    }
    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    public List<Admin> getListaAdministradores() {
        return administradores;
    }
    public void setAdministradores(List<Admin> administradores) {
        this.administradores= administradores;
    }
    public void aniadirVendedor(Vendedor vendedor) throws UsuarioYaExisteException {
        // Verifica que no exista un vendedor con la misma contraseña
        for (Vendedor v : vendedores) {
            if (v.getContrasenia().equals(vendedor.getContrasenia())) {
                logger.logWarning("Intento de creacion de cuenta con contraseña existente ");
                throw new UsuarioYaExisteException("Intente con otra contraseña");
                 // Salir del método sin añadir el vendedor
            }
        }
        // Si no existe un vendedor con la misma contraseña, lo añade
        vendedores.add(vendedor);
        usuarios.add(vendedor);
        logger.logInfo("Vendedor "+ vendedor.getNombre()+ " añadido satisfactoriamente");

    }

    public void aniadirAdmin(Admin admin){
        administradores.add(admin);
        usuarios.add(admin);
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public List<Admin> getAdministradores() {
        return administradores;
    }

    public List<Vendedor> getVendedores() {
        return vendedores;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public Estadisticas getEstadistica() {
        return estadistica;
    }

    public Vendedor getVendedorPorAgregar() {
        return vendedorPorAgregar;
    }

    public void setVendedorPorAgregar(Vendedor vendedorPorAgregar) {
        this.vendedorPorAgregar = vendedorPorAgregar;
    }


    public double obtenerTotalVentasMes(Vendedor vendedor) {
        List<Producto> listaProductos = vendedor.getListaProductos();

        LocalDate fechaActual = LocalDate.now();


        return listaProductos.stream()
                .filter(p -> p.getEstado().equals(Estado.VENDIDO))
                .filter(p -> p.getFechaPublicacion().getMonth().equals(fechaActual.getMonth()))
                .filter(p -> p.getFechaPublicacion().getYear() == fechaActual.getYear())
                .mapToDouble(p -> {
                    try {
                        return Double.parseDouble(p.getPrecio());
                    } catch (NumberFormatException e) {
                        System.err.println("Error al parsear el precio: " + p.getPrecio());
                        return 0.0; // En caso de error, retorna 0
                    }
                })
                .sum(); // Sumar los precios
    }
}
