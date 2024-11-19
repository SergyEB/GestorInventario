/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author sergi
 */
public class Conexion {

    Connection connection = null;

    String user = "Gustavo";
    String pass = "12345678";
    String db = "GestionInventario";
    String ip = "localhost";
    String port = "1433";

    public Connection obtenerConexion() {
        try {
            //String cadena = "jdbc:sqlserver://localhost:"+puerto+";"+"databaseName="+db;
            //conexion = DriverManager.getConnection(cadena, user, pass);

            // jdbc:sqlserver://localhost:1433;databaseName=tuBaseDeDatos;user=tuUsuario;password=tuContraseña;encrypt=true;trustServerCertificate=true;
            String cadena = "jdbc:sqlserver://" + ip + ":" + port + ";databaseName=" + db
                    + ";user=" + user + ";password=" + pass + ";encrypt=true;trustServerCertificate=true";
            connection = DriverManager.getConnection(cadena);
            JOptionPane.showMessageDialog(null, "Conexion Exitosa a la Base de Datos");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
        return connection;
    }
}
