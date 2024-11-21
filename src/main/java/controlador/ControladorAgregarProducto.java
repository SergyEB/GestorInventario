/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Producto;
import modelo.ProductoDAO;
import vista.Agregar;

/**
 *
 * @author sergi
 */
public class ControladorAgregarProducto implements ActionListener {

    private Agregar vistaAgregar;
    private ProductoDAO productoDAO;

    public ControladorAgregarProducto(Agregar vistaAgregar) {
        this.vistaAgregar = vistaAgregar;
        this.productoDAO = new ProductoDAO();

        // Vincular evento al botón
        this.vistaAgregar.btnAgregarProducto.addActionListener(this);

        // Cargar categorías en el JComboBox
        cargarCategorias();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaAgregar.btnAgregarProducto) {
            agregarProducto();
        }
    }

    private void cargarCategorias() {
        List<String> categorias = productoDAO.obtenerCategorias();
        vistaAgregar.cbxCategoriaProducto.removeAllItems();
        for (String categoria : categorias) {
            vistaAgregar.cbxCategoriaProducto.addItem(categoria);
        }
    }

    private void agregarProducto() {
        try {
            // Validar y obtener valores
            String nombreProducto = vistaAgregar.txtNombreProducto.getText().trim();
            String descripcion = vistaAgregar.txtaDescripcionProducto.getText().trim();
            String precioText = vistaAgregar.txtPrecioProducto.getText().trim();
            String cantidadText = vistaAgregar.txtCantidadProducto.getText().trim();
            Date fechaVencimiento = vistaAgregar.dateChooserVencimiento.getDate();
            String categoriaSeleccionada = (String) vistaAgregar.cbxCategoriaProducto.getSelectedItem();

            // Validaciones básicas
            if (nombreProducto.isEmpty() || precioText.isEmpty() || cantidadText.isEmpty() || categoriaSeleccionada == null) {
                JOptionPane.showMessageDialog(vistaAgregar, "Por favor, complete todos los campos obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!precioText.matches("\\d+(\\.\\d{1,2})?") && !precioText.matches("\\d+")) {
                JOptionPane.showMessageDialog(vistaAgregar, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!cantidadText.matches("\\d+")) {
                JOptionPane.showMessageDialog(vistaAgregar, "La cantidad debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener ID de la categoría
            int idCategoria = productoDAO.obtenerIdCategoria(categoriaSeleccionada);
            if (idCategoria == -1) {
                JOptionPane.showMessageDialog(vistaAgregar, "La categoría seleccionada no es válida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Conversión de valores
            double precio = Double.parseDouble(precioText);
            int cantidad = Integer.parseInt(cantidadText);

            // Crear objeto producto
            Producto producto = new Producto();
            producto.setNombreProducto(nombreProducto);
            producto.setDescripcionProducto(descripcion);
            producto.setPrecio(precio);
            producto.setCantidadProducto(cantidad);
            producto.setFechaVencimiento(fechaVencimiento);
            producto.setIdCategoria(idCategoria); // Usar el ID de la categoría

            // Guardar en la base de datos
            boolean resultado = productoDAO.agregarProducto(producto);
            if (resultado) {
                JOptionPane.showMessageDialog(vistaAgregar, "Producto agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vistaAgregar, "Error al agregar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaAgregar, "Ocurrió un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        vistaAgregar.txtNombreProducto.setText("");
        vistaAgregar.txtaDescripcionProducto.setText("");
        vistaAgregar.txtPrecioProducto.setText("");
        vistaAgregar.txtCantidadProducto.setText("");
        vistaAgregar.dateChooserVencimiento.setDate(null);
        vistaAgregar.cbxCategoriaProducto.setSelectedIndex(0);
    }
}
