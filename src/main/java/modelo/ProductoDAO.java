/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author sergi
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Conexion;
import modelo.Producto;

public class ProductoDAO {

    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // Método para insertar un producto en la base de datos
    public boolean agregarProducto(Producto producto) {
        String sql = "INSERT INTO Productos (ID_Categoria, nombre_producto, descripciónProducto, cantidadProducto, precio, fechaVencimiento) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            con = conectar.obtenerConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, producto.getIdCategoria());
            ps.setString(2, producto.getNombreProducto());
            ps.setString(3, producto.getDescripcionProducto());
            ps.setInt(4, producto.getCantidadProducto());
            ps.setDouble(5, producto.getPrecio());
            if (producto.getFechaVencimiento() != null) {
                java.sql.Date sqlDate = new java.sql.Date(producto.getFechaVencimiento().getTime());
                ps.setDate(6, sqlDate);
            } else {
                ps.setNull(6, java.sql.Types.DATE);
            }

            return ps.executeUpdate() > 0; // Retorna true si se inserta el producto correctamente
        } catch (Exception e) {
            System.out.println("Error al agregar producto: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                System.out.println("Error al cerrar conexiones: " + e.getMessage());
            }
        }
    }

    // Método para obtener todas las categorías
    public List<String> obtenerCategorias() {
        List<String> categorias = new ArrayList<>();
        String sql = "SELECT nombreCategoria FROM Categorias";
        try {
            con = conectar.obtenerConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                categorias.add(rs.getString("nombreCategoria"));
            }
        } catch (Exception e) {
            System.out.println("Error al obtener categorías: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                System.out.println("Error al cerrar conexiones: " + e.getMessage());
            }
        }
        return categorias;
    }

    // Método para obtener el ID de una categoría por su nombre
    public int obtenerIdCategoria(String nombreCategoria) {
        String sql = "SELECT ID_Categoria FROM Categorias WHERE nombreCategoria = ?";
        try {
            con = conectar.obtenerConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombreCategoria);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID_Categoria");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener ID de la categoría: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                System.out.println("Error al cerrar conexiones: " + e.getMessage());
            }
        }
        return -1; // Retorna -1 si no encuentra la categoría
    }

    // Método para buscar productos por nombre
    public List<Producto> buscarProductosPorNombre(String nombre) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM Productos WHERE nombre_producto LIKE ?";

        try {
            con = conectar.obtenerConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + nombre + "%"); // Permite buscar productos que contengan el texto
            rs = ps.executeQuery();

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("ID_Producto"));
                producto.setIdCategoria(rs.getInt("ID_Categoria"));
                producto.setNombreProducto(rs.getString("nombre_producto"));
                producto.setDescripcionProducto(rs.getString("descripciónProducto"));
                producto.setCantidadProducto(rs.getInt("cantidadProducto"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setFechaVencimiento(rs.getDate("fechaVencimiento"));

                productos.add(producto);
            }
        } catch (Exception e) {
            System.out.println("Error al buscar productos: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                System.out.println("Error al cerrar conexiones: " + e.getMessage());
            }
        }

        return productos;
    }

    public Producto obtenerProductoPorId(int idProducto) {
        Producto producto = null; // Inicializamos el producto como null
        String sql = "SELECT * FROM Productos WHERE ID_Producto = ?";

        try {
            con = conectar.obtenerConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idProducto); // Asignamos el ID del producto al parámetro de la consulta
            rs = ps.executeQuery();

            if (rs.next()) {
                producto = new Producto();
                producto.setIdProducto(rs.getInt("ID_Producto"));
                producto.setIdCategoria(rs.getInt("ID_Categoria"));
                producto.setNombreProducto(rs.getString("nombre_producto"));
                producto.setDescripcionProducto(rs.getString("descripciónProducto"));
                producto.setCantidadProducto(rs.getInt("cantidadProducto"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setFechaVencimiento(rs.getDate("fechaVencimiento")); // Convertimos la fecha
            }
        } catch (Exception e) {
            System.out.println("Error al obtener producto por ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                System.out.println("Error al cerrar conexiones: " + e.getMessage());
            }
        }

        return producto;
    }

    public String obtenerNombreCategoria(int idCategoria) {
        String sql = "SELECT nombreCategoria FROM Categorias WHERE ID_Categoria = ?";
        String nombreCategoria = null;

        try {
            con = conectar.obtenerConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idCategoria);
            rs = ps.executeQuery();

            if (rs.next()) {
                nombreCategoria = rs.getString("nombreCategoria");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el nombre de la categoría: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                System.out.println("Error al cerrar conexiones: " + e.getMessage());
            }
        }

        return nombreCategoria;
    }

    public boolean actualizarProducto(Producto producto) {
        String sql = "UPDATE Productos SET ID_Categoria = ?, nombre_producto = ?, descripciónProducto = ?, cantidadProducto = ?, precio = ?, fechaVencimiento = ? WHERE ID_Producto = ?";
        try {
            con = conectar.obtenerConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, producto.getIdCategoria());
            ps.setString(2, producto.getNombreProducto());
            ps.setString(3, producto.getDescripcionProducto());
            ps.setInt(4, producto.getCantidadProducto());
            ps.setDouble(5, producto.getPrecio());
            if (producto.getFechaVencimiento() != null) {
                ps.setDate(6, new java.sql.Date(producto.getFechaVencimiento().getTime()));
            } else {
                ps.setNull(6, java.sql.Types.DATE);
            }
            ps.setInt(7, producto.getIdProducto());

            return ps.executeUpdate() > 0; // Retorna true si se actualizó el producto
        } catch (Exception e) {
            System.out.println("Error al actualizar el producto: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                System.out.println("Error al cerrar conexiones: " + e.getMessage());
            }
        }
        

}

public boolean eliminarProducto(int idProducto) {
    String sql = "DELETE FROM Productos WHERE ID_Producto = ?";
    try {
        con = conectar.obtenerConexion();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idProducto);

        return ps.executeUpdate() > 0; // Retorna true si se elimina correctamente
    } catch (Exception e) {
        System.out.println("Error al eliminar producto: " + e.getMessage());
        return false;
    } finally {
        try {
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (Exception e) {
            System.out.println("Error al cerrar conexiones: " + e.getMessage());
        }
    }
}
 
    
}
