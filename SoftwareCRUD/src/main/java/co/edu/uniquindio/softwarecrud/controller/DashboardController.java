
package co.edu.uniquindio.softwarecrud.controller;

import co.edu.uniquindio.softwarecrud.service.ContextoAplicacion;
import co.edu.uniquindio.softwarecrud.util.Navegacion;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class DashboardController {

    @FXML
    private Label lblTotalEventos;

    @FXML
    private Label lblBoletosVendidos;

    @FXML
    private Label lblTotalClientes;

    @FXML
    private Label lblTotalVentas;

    @FXML
    public void initialize() {

        lblTotalEventos.setText(
                String.valueOf(ContextoAplicacion.getEventoService()
                        .obtenerEventos().size())
        );

        lblBoletosVendidos.setText(
                String.valueOf(ContextoAplicacion.getVentaService()
                        .obtenerBoletos().stream()
                        .filter(b -> b.getEstado().name().equals("ACTIVO"))
                        .mapToInt(b -> b.getCantidad())
                        .sum())
        );

        lblTotalClientes.setText(
                String.valueOf(ContextoAplicacion.getVentaService()
                        .obtenerClientes().size())
        );

        lblTotalVentas.setText(
                String.valueOf(ContextoAplicacion.getVentaService()
                        .obtenerBoletos().size())
        );
    }

    @FXML
    public void irAEventos(ActionEvent event) {
        Navegacion.cambiarVista(
                "eventos.fxml",
                (Node) event.getSource()
        );
    }

    @FXML
    public void irAVentas(ActionEvent event) {
        Navegacion.cambiarVista(
                "venta.fxml",
                (Node) event.getSource()
        );
    }
}
