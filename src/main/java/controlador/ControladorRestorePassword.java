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
import modelo.UsuarioDAO;
import vista.RestorePassword;

public class ControladorRestorePassword implements ActionListener {

    private RestorePassword vista; // Vista vinculada al controlador
    private UsuarioDAO dao; // Clase DAO para las operaciones con la base de datos

    public ControladorRestorePassword(RestorePassword vista) {
        this.vista = vista;
        this.dao = new UsuarioDAO(); // Instancia del DAO
        this.vista.btnAceptar.addActionListener(this); // Escucha el botón "Aceptar"
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnAceptar) {
            recuperarContraseña();
        }
    }

    private void recuperarContraseña() {
        // Obtener datos de la interfaz
        String correo = vista.txtCorreo.getText().trim();
        String nuevaContraseña = new String(vista.txtNuevaContraseña.getPassword()).trim();
        String claveAdmin = new String(vista.txtContraseñaAdmin.getPassword()).trim();

        // Validaciones iniciales
        if (correo.isEmpty() || nuevaContraseña.isEmpty() || claveAdmin.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar que el correo exista en la base de datos
        if (!dao.correoExiste(correo)) {
            JOptionPane.showMessageDialog(vista, "El correo no está registrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que la clave del administrador sea válida
        boolean esClaveAdminValida = dao.obtenerClavesAdministradores().contains(claveAdmin);
        if (!esClaveAdminValida) {
            JOptionPane.showMessageDialog(vista, "Clave de administrador incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Actualizar la contraseña
        boolean actualizada = dao.actualizarContraseña(correo, nuevaContraseña);
        if (actualizada) {
            JOptionPane.showMessageDialog(vista, "Contraseña actualizada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos(); // Limpiar los campos después de la actualización
        } else {
            JOptionPane.showMessageDialog(vista, "Error al actualizar la contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        vista.txtCorreo.setText("");
        vista.txtNuevaContraseña.setText("");
        vista.txtContraseñaAdmin.setText("");
    }
}
