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
import javax.swing.JPanel;
import vista.Dashboard;
import vista.Agregar;
import vista.Editar;
import vista.Eliminar;
import vista.Facturas;
import vista.Inventario;
import vista.UsuarioView;
import vista.Venta;

public class ControladorDashboard implements ActionListener {

    private Dashboard dashboard;

    public ControladorDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;

        // Vincular eventos a los botones del menú
        this.dashboard.btnVenta.addActionListener(this);
        this.dashboard.btnInventario.addActionListener(this);
        this.dashboard.btnAgregarProducto.addActionListener(this);
        this.dashboard.btnEditarProducto.addActionListener(this);
        this.dashboard.btnEliminarProducto.addActionListener(this);
        this.dashboard.btnFacturas.addActionListener(this);
        this.dashboard.btnUsuario.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dashboard.btnVenta) {
            abrirVenta();
        } else if (e.getSource() == dashboard.btnInventario) {
            abrirInventario();
        } else if (e.getSource() == dashboard.btnAgregarProducto) {
            abrirAgregarProducto();
        } else if (e.getSource() == dashboard.btnEditarProducto) {
            abrirEditarProducto();
        } else if (e.getSource() == dashboard.btnEliminarProducto) {
            abrirEliminarProducto();
        } else if (e.getSource() == dashboard.btnFacturas) {
            abrirFacturas();
        } else if (e.getSource() == dashboard.btnUsuario) {
            abrirUsuario();
        }
    }

    private void abrirVenta() {
        Venta venta = new Venta();
        setContenido(venta);
    }

    private void abrirInventario() {
        Inventario inventario = new Inventario();
        setContenido(inventario);
    }

    private void abrirAgregarProducto() {
        Agregar agregar = new Agregar();
        ControladorAgregarProducto cap = new ControladorAgregarProducto(agregar);
        setContenido(agregar);
    }

    private void abrirEditarProducto() {
        Editar editar = new Editar();
        setContenido(editar);
    }

    private void abrirEliminarProducto() {
        Eliminar eliminar = new Eliminar();
        setContenido(eliminar);
    }

    private void abrirFacturas() {
        Facturas facturas = new Facturas();
        setContenido(facturas);
    }

    private void abrirUsuario() {
        UsuarioView usuarioView = new UsuarioView();
        setContenido(usuarioView);
    }

    private void setContenido(JPanel panel) {
        panel.setSize(1045, 638);
        panel.setLocation(0, 0);

        dashboard.contenido.removeAll();
        dashboard.contenido.add(panel);
        dashboard.contenido.revalidate();
        dashboard.contenido.repaint();
    }
}
