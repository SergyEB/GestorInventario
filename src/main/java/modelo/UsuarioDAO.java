/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author sergi
 */
public class UsuarioDAO {

    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public int agregar(Usuario u) {
        String sql = "INSERT INTO Usuarios (nombre, apellido1, apellido2, correo, contraseña, rolUsuario) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            con = conectar.obtenerConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido1());
            ps.setString(3, u.getApellido2());
            ps.setString(4, u.getCorreo());
            ps.setString(5, u.getContraseña());
            ps.setString(6, u.getRolUsuario());
            ps.executeUpdate();

            return 1; // Devuelve 1 si la inserción fue exitosa
        } catch (Exception e) {
            System.out.println("Error al agregar usuario: " + e.getMessage()); // Opcional: imprime el error para depuración
            return 0; // Devuelve 0 si ocurre un error
        }
    }

    public List<String> obtenerClavesAdministradores() {
        List<String> claves = new ArrayList<>();
        String sql = "SELECT contraseña FROM Usuarios WHERE rolUsuario = 'Administrador'";
        try {
            con = conectar.obtenerConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                claves.add(rs.getString("contraseña")); // Agrega cada clave a la lista
            }
        } catch (Exception e) {
            System.out.println("Error al obtener claves de administradores: " + e.getMessage());
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
        return claves;
    }

    public Usuario validarCredenciales(String correo, String contraseña) {
        Usuario usuario = null;
        String sql = "SELECT * FROM Usuarios WHERE correo = ? AND contraseña = ?";
        try {
            con = conectar.obtenerConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, contraseña);
            rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("ID_Usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido1(rs.getString("apellido1"));
                usuario.setApellido2(rs.getString("apellido2"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setRolUsuario(rs.getString("rolUsuario"));
            }
        } catch (Exception e) {
            System.out.println("Error al validar credenciales: " + e.getMessage());
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
        return usuario;
    }

    public boolean correoExiste(String correo) {
        String sql = "SELECT 1 FROM Usuarios WHERE correo = ?";
        boolean existe = false;

        try (Connection con = conectar.obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true; // Retorno inmediato si el correo existe
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // O puedes usar un Logger aquí
        }

        return existe; // Retorna false si no se encontró el correo
    }

    public boolean actualizarContraseña(String correo, String nuevaContraseña) {
        String sql = "UPDATE Usuarios SET contraseña = ? WHERE correo = ?";
        try (Connection con = conectar.obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevaContraseña);
            ps.setString(2, correo);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
