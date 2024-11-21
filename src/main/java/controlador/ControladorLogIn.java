/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author sergi
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Usuario;
import modelo.UsuarioDAO;
import vista.Dashboard;
import vista.LogIn;

public class ControladorLogIn implements ActionListener {

    private LogIn logIn;
    private UsuarioDAO usuarioDAO;

    public ControladorLogIn(LogIn logIn) {
        this.logIn = logIn;
        this.usuarioDAO = new UsuarioDAO();

        // Vincular eventos de botones
        this.logIn.btnAceptar.addActionListener(this);
        this.logIn.btnCerrar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logIn.btnAceptar) {
            iniciarSesion();
        } else if (e.getSource() == logIn.btnCerrar) {
            cerrarAplicacion();
        }
    }

    private void iniciarSesion() {
        String correo = logIn.txtCorreo.getText().trim();
        String contraseña = new String(logIn.txtContraseña.getPassword()).trim();

        // Validar campos vacíos
        if (correo.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(logIn, "Por favor, complete todos los campos.");
            return;
        }

        // Validar credenciales
        Usuario usuario = usuarioDAO.validarCredenciales(correo, contraseña);
        if (usuario != null) {
            JOptionPane.showMessageDialog(logIn, "Bienvenido, " + usuario.getNombre() + "!");
            abrirDashboard(usuario);
        } else {
            JOptionPane.showMessageDialog(logIn, "Correo o contraseña incorrectos.");
        }
    }

    private void cerrarAplicacion() {
        System.exit(0);
    }

    private void abrirDashboard(Usuario usuario) {
        // Lógica para abrir la ventana principal o el dashboard
        // Aquí puedes pasar datos del usuario si es necesario
        JOptionPane.showMessageDialog(logIn, "Accediendo al sistema como " + usuario.getRolUsuario());
        
        Dashboard dash = new Dashboard(usuario);
        ControladorDashboard cd = new ControladorDashboard(dash);
        dash.setVisible(true);
        
        
        logIn.setVisible(false); // Ocultar la ventana de LogIn
        // Crear e inicializar el dashboard
    }
}
