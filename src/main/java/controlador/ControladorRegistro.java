/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Usuario;
import modelo.UsuarioDAO;
import vista.SignUp;

/**
 *
 * @author sergi
 */
public class ControladorRegistro implements ActionListener {

    UsuarioDAO dao = new UsuarioDAO();
    Usuario u = new Usuario();
    SignUp signUp = new SignUp();

    public ControladorRegistro(SignUp s) {
        this.signUp = s; // Vincula la ventana con el controlador
        this.signUp.btnAceptar.addActionListener(this); // Agrega el evento al botón
        System.out.println("Controlador inicializado correctamente.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUp.btnAceptar) {
            agregarUsuario();
        }
    }

    public void agregarUsuario() {
        String nombreCompleto = signUp.txtNombreCompleto.getText().trim();
        if (nombreCompleto.isEmpty()) {
            JOptionPane.showMessageDialog(signUp, "Por favor, ingrese el nombre completo.");
            return;
        }

        String[] partesNombre = nombreCompleto.split("\\s+");
        if (partesNombre.length < 3) {
            JOptionPane.showMessageDialog(signUp, "Por favor, ingrese el nombre completo (nombre, primer apellido y segundo apellido).");
            return;
        }

        String correo = signUp.txtCorreoUsuario.getText().trim();
        String contraseña = new String(signUp.txtContraseña.getPassword()).trim();
        String claveAdministrador = new String(signUp.txtContraseña1.getPassword()).trim(); // Clave administrador

        if (correo.isEmpty()) {
            JOptionPane.showMessageDialog(signUp, "Por favor, ingrese un correo electrónico.");
            return;
        }

        if (!correo.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            JOptionPane.showMessageDialog(signUp, "El correo ingresado no es válido.");
            return;
        }

        if (contraseña.length() < 6 || contraseña.length() > 10) {
            JOptionPane.showMessageDialog(signUp, "La contraseña debe tener entre 6 y 10 caracteres.");
            return;
        }

        // Validar clave administrador
        List<String> clavesAdministradores = dao.obtenerClavesAdministradores();
        if (!clavesAdministradores.contains(claveAdministrador)) {
            JOptionPane.showMessageDialog(signUp, "Clave de administrador incorrecta.");
            return;
        }

        // Si todo es válido, guardar usuario
        String nombre = partesNombre[0];
        String apellido1 = partesNombre[1];
        String apellido2 = partesNombre[2];
        String rolUsuario = "Usuario";

        u.setNombre(nombre);
        u.setApellido1(apellido1);
        u.setApellido2(apellido2);
        u.setCorreo(correo);
        u.setContraseña(contraseña);
        u.setRolUsuario(rolUsuario);

        int r = dao.agregar(u);

        if (r == 1) {
            JOptionPane.showMessageDialog(signUp, "Usuario agregado con éxito.");
        } else {
            JOptionPane.showMessageDialog(signUp, "Error al agregar el usuario.");
        }
    }

}
