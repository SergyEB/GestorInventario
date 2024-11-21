/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;
import modelo.ProductoDAO;
import vista.Eliminar;

/**
 *
 * @author sergi
 */
public class ControladorEliminarProducto implements ActionListener {

    Producto productoSeleccionado;
    private Eliminar vistaEliminar; // Vista reutilizada
    private ProductoDAO productoDAO; // DAO para acceder a los productos
    private DefaultTableModel modeloTabla; // Modelo para la tabla

    public ControladorEliminarProducto(Eliminar vistaEliminar) {
        this.vistaEliminar = vistaEliminar;
        this.productoDAO = new ProductoDAO();
        this.modeloTabla = (DefaultTableModel) vistaEliminar.tblProductos.getModel();

        // Agregar listeners a los botones
        this.vistaEliminar.btnBuscar.addActionListener(this);
        this.vistaEliminar.btnSeleccionar.addActionListener(this);
        this.vistaEliminar.btnEliminar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaEliminar.btnBuscar) {
            buscarProducto();
        } else if (e.getSource() == vistaEliminar.btnSeleccionar) {
            seleccionarProducto();
        } else if (e.getSource() == vistaEliminar.btnEliminar) {
            eliminarProducto();
        }
    }
 private void buscarProducto() {
        // Lógica de búsqueda (igual que en el controlador anterior)
        String textoBusqueda = vistaEliminar.txtBuscar.getText().trim();
        if (textoBusqueda.isEmpty()) {
            JOptionPane.showMessageDialog(vistaEliminar, "Por favor, ingrese un texto para buscar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Limpiar la tabla
        modeloTabla.setRowCount(0);

        // Buscar productos
        List<Producto> productos = productoDAO.buscarProductosPorNombre(textoBusqueda);
        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(vistaEliminar, "No se encontraron productos con ese nombre.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Agregar los productos a la tabla
        for (Producto producto : productos) {
            modeloTabla.addRow(new Object[]{
                producto.getIdProducto(),
                producto.getNombreProducto(),
                producto.getCantidadProducto(),
                producto.getPrecio()
            });
        }
    }

    private void seleccionarProducto() {
        // Obtener la fila seleccionada
        int filaSeleccionada = vistaEliminar.tblProductos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vistaEliminar, "Por favor, seleccione un producto de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener datos del producto seleccionado
        int idProducto = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        productoSeleccionado = productoDAO.obtenerProductoPorId(idProducto);
        if (productoSeleccionado == null) {
            JOptionPane.showMessageDialog(vistaEliminar, "Error al obtener el producto seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Cargar los datos del producto en los campos de texto
        cargarDatosProducto(productoSeleccionado);
    }

    private void cargarDatosProducto(Producto producto) {
        // Cargar las categorías para mostrar la categoría del producto
        cargarCategorias();
        String nombreCategoria = productoDAO.obtenerNombreCategoria(producto.getIdCategoria());
        vistaEliminar.cbxCategoria.setSelectedItem(nombreCategoria);

        // Llenar campos de texto con los datos del producto
        vistaEliminar.txtNombreAEliminar.setText(producto.getNombreProducto());
        vistaEliminar.txtaDescripcionAEliminar.setText(producto.getDescripcionProducto());
        vistaEliminar.txtCantidadAEliminar.setText(String.valueOf(producto.getCantidadProducto()));
        vistaEliminar.txtPrecioAEliminar.setText(String.valueOf(producto.getPrecio()));
        if (producto.getFechaVencimiento() != null) {
            vistaEliminar.dateChooserAEliminar.setDate(producto.getFechaVencimiento());
        } else {
            vistaEliminar.dateChooserAEliminar.setDate(null);
        }

        
    }

 

    private void eliminarProducto() {
        // Verificar que haya un producto seleccionado
        if (productoSeleccionado == null) {
            JOptionPane.showMessageDialog(vistaEliminar, "Por favor, seleccione un producto antes de eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Confirmar la eliminación
        int confirmacion = JOptionPane.showConfirmDialog(vistaEliminar, "¿Está seguro de que desea eliminar este producto?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        // Eliminar producto de la base de datos
        boolean eliminado = productoDAO.eliminarProducto(productoSeleccionado.getIdProducto());
        if (eliminado) {
            JOptionPane.showMessageDialog(vistaEliminar, "Producto eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Limpiar campos y tabla
            limpiarCampos();
            modeloTabla.setRowCount(0);
        } else {
            JOptionPane.showMessageDialog(vistaEliminar, "Error al eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        vistaEliminar.txtNombreAEliminar.setText("");
        vistaEliminar.txtaDescripcionAEliminar.setText("");
        vistaEliminar.txtCantidadAEliminar.setText("");
        vistaEliminar.txtPrecioAEliminar.setText("");
        vistaEliminar.dateChooserAEliminar.setDate(null);
        vistaEliminar.cbxCategoria.setSelectedIndex(0);

        // Resetear producto seleccionado
        productoSeleccionado = null;
    }

    private void cargarCategorias() {
        List<String> categorias = productoDAO.obtenerCategorias();
        vistaEliminar.cbxCategoria.removeAllItems();
        for (String categoria : categorias) {
            vistaEliminar.cbxCategoria.addItem(categoria);
        }
    }
}


