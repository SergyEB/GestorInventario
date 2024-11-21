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
import javax.swing.table.DefaultTableModel;
import modelo.Producto;
import modelo.ProductoDAO;
import vista.Editar;

/**
 *
 * @author sergi
 */
public class ControladorEditarProducto implements ActionListener {

    private Editar vistaEditar; // Vista vinculada al controlador
    private ProductoDAO productoDAO; // DAO para acceder a los productos
    private DefaultTableModel modeloTabla; // Modelo para la tabla

    public ControladorEditarProducto(Editar vistaEditar) {
        this.vistaEditar = vistaEditar;
        this.productoDAO = new ProductoDAO();
        this.modeloTabla = (DefaultTableModel) vistaEditar.tblProductos.getModel();

        // Agregar listeners a los botones
        this.vistaEditar.btnBuscar.addActionListener(this);
        this.vistaEditar.btnSeleccionar.addActionListener(this);
        this.vistaEditar.btnModificar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaEditar.btnBuscar) {
            buscarProducto();
        } else if (e.getSource() == vistaEditar.btnSeleccionar) {
            seleccionarProducto();
        }
        if (e.getSource() == vistaEditar.btnModificar) {
            modificarProducto();
        }
    }

    private void buscarProducto() {
        // Obtener texto de búsqueda
        String textoBusqueda = vistaEditar.txtBuscar.getText().trim();
        if (textoBusqueda.isEmpty()) {
            JOptionPane.showMessageDialog(vistaEditar, "Por favor, ingrese un texto para buscar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Limpiar la tabla
        modeloTabla.setRowCount(0);

        // Buscar productos
        List<Producto> productos = productoDAO.buscarProductosPorNombre(textoBusqueda);
        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(vistaEditar, "No se encontraron productos con ese nombre.", "Información", JOptionPane.INFORMATION_MESSAGE);
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
        int filaSeleccionada = vistaEditar.tblProductos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vistaEditar, "Por favor, seleccione un producto de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener datos del producto seleccionado
        int idProducto = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        Producto producto = productoDAO.obtenerProductoPorId(idProducto);
        if (producto == null) {
            JOptionPane.showMessageDialog(vistaEditar, "Error al obtener el producto seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        cargarCategorias();
        String nombreCategoria = productoDAO.obtenerNombreCategoria(producto.getIdCategoria());
        // Cargar los datos del producto en los campos de edición
        vistaEditar.cbxCategoria.setSelectedItem(nombreCategoria);
        vistaEditar.txtNombreAEditar.setText(producto.getNombreProducto());
        vistaEditar.txtaDescripcionAEditar.setText(producto.getDescripcionProducto());
        vistaEditar.txtCantidadAEditar.setText(String.valueOf(producto.getCantidadProducto()));
        vistaEditar.txtPrecioAEditar.setText(String.valueOf(producto.getPrecio()));
        if (producto.getFechaVencimiento() != null) {
            vistaEditar.dateChooserAEditar.setDate(producto.getFechaVencimiento());
        } else {
            vistaEditar.dateChooserAEditar.setDate(null);
        }
    }

    private void cargarCategorias() {
        List<String> categorias = productoDAO.obtenerCategorias(); // Obtener las categorías
        vistaEditar.cbxCategoria.removeAllItems(); // Limpiar el ComboBox
        for (String categoria : categorias) {
            vistaEditar.cbxCategoria.addItem(categoria); // Agregar cada categoría
        }
    }

    private void modificarProducto() {
        // Validar que se haya seleccionado un producto
        int filaSeleccionada = vistaEditar.tblProductos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vistaEditar, "Por favor, seleccione un producto de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID del producto seleccionado
        int idProducto = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

        // Validar los campos editados
        String nombreProducto = vistaEditar.txtNombreAEditar.getText().trim();
        String descripcionProducto = vistaEditar.txtaDescripcionAEditar.getText().trim();
        String cantidadProductoStr = vistaEditar.txtCantidadAEditar.getText().trim();
        String precioProductoStr = vistaEditar.txtPrecioAEditar.getText().trim();
        Date fechaVencimiento = vistaEditar.dateChooserAEditar.getDate();

        if (nombreProducto.isEmpty() || cantidadProductoStr.isEmpty() || precioProductoStr.isEmpty()) {
            JOptionPane.showMessageDialog(vistaEditar, "Por favor, complete todos los campos requeridos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar datos numéricos
        int cantidadProducto;
        double precioProducto;
        try {
            cantidadProducto = Integer.parseInt(cantidadProductoStr);
            precioProducto = Double.parseDouble(precioProductoStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaEditar, "Cantidad y precio deben ser valores numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener la categoría seleccionada
        String nombreCategoria = (String) vistaEditar.cbxCategoria.getSelectedItem();
        if (nombreCategoria == null || nombreCategoria.isEmpty()) {
            JOptionPane.showMessageDialog(vistaEditar, "Por favor, seleccione una categoría.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCategoria = productoDAO.obtenerIdCategoria(nombreCategoria);
        if (idCategoria == -1) {
            JOptionPane.showMessageDialog(vistaEditar, "Error al obtener el ID de la categoría seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear el objeto Producto con los datos editados
        Producto productoEditado = new Producto();
        productoEditado.setIdProducto(idProducto);
        productoEditado.setIdCategoria(idCategoria);
        productoEditado.setNombreProducto(nombreProducto);
        productoEditado.setDescripcionProducto(descripcionProducto);
        productoEditado.setCantidadProducto(cantidadProducto);
        productoEditado.setPrecio(precioProducto);
        productoEditado.setFechaVencimiento(fechaVencimiento);

        // Actualizar el producto en la base de datos
        boolean actualizado = productoDAO.actualizarProducto(productoEditado);
        if (actualizado) {
            JOptionPane.showMessageDialog(vistaEditar, "Producto actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            buscarProducto(); // Refrescar la tabla después de actualizar
        } else {
            JOptionPane.showMessageDialog(vistaEditar, "Error al actualizar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
