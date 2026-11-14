
package co.edu.uniquindio.softwarecrud.controller;

import co.edu.uniquindio.softwarecrud.model.Boleto;
import co.edu.uniquindio.softwarecrud.model.Cliente;
import co.edu.uniquindio.softwarecrud.model.Evento;
import co.edu.uniquindio.softwarecrud.model.EstadoCliente;

import co.edu.uniquindio.softwarecrud.service.ContextoAplicacion;
import co.edu.uniquindio.softwarecrud.service.EventoService;
import co.edu.uniquindio.softwarecrud.service.VentaService;
import co.edu.uniquindio.softwarecrud.service.ValidacionService;

import co.edu.uniquindio.softwarecrud.util.Navegacion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

public class VentaController {

    private final VentaService ventaService =
            ContextoAplicacion.getVentaService();

    private final EventoService eventoService =
            ContextoAplicacion.getEventoService();

    private final ValidacionService validacionService =
            ContextoAplicacion.getValidacionService();

    @FXML
    private TextField txtNombreCliente;

    @FXML
    private TextField txtDocumento;

    @FXML
    private TextField txtCorreo;

    @FXML
    private ComboBox<Cliente> cmbCliente;

    @FXML
    private ComboBox<Evento> cmbEvento;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtOperador;

    @FXML
    private Label lblTotal;

    @FXML
    private TableView<Boleto> tablaBoletos;

    @FXML
    private TableColumn<Boleto, String> colCodigo;

    @FXML
    private TableColumn<Boleto, String> colCliente;

    @FXML
    private TableColumn<Boleto, String> colEvento;

    @FXML
    private TableColumn<Boleto, Integer> colCantidad;

    @FXML
    private TableColumn<Boleto, Double> colTotal;

    @FXML
    private TableColumn<Boleto, LocalDateTime> colFecha;

    @FXML
    private TableColumn<Boleto, Object> colEstado;

    @FXML
    public void initialize() {

        configurarTabla();
        configurarCombos();

        cargarClientes();
        cargarEventos();
        cargarBoletos();

        cmbEvento.setOnAction(event -> actualizarTotal());
        cmbCliente.setOnAction(event -> actualizarTotal());
        txtCantidad.textProperty().addListener(
                (observable, anterior, nuevo) -> actualizarTotal()
        );
    }

    private void configurarTabla() {

        colCodigo.setCellValueFactory(
                new PropertyValueFactory<>("codigo")
        );

        colCliente.setCellValueFactory(
                celda -> new javafx.beans.property.SimpleStringProperty(
                        celda.getValue().getCliente().getNombre()
                )
        );

        colEvento.setCellValueFactory(
                celda -> new javafx.beans.property.SimpleStringProperty(
                        celda.getValue().getEvento().getNombre()
                )
        );

        colCantidad.setCellValueFactory(
                new PropertyValueFactory<>("cantidad")
        );

        colTotal.setCellValueFactory(
                new PropertyValueFactory<>("valorTotal")
        );

        colFecha.setCellValueFactory(
                new PropertyValueFactory<>("fechaCompra")
        );

        colEstado.setCellValueFactory(
                new PropertyValueFactory<>("estado")
        );
    }

    private void configurarCombos() {

        cmbCliente.setCellFactory(lista -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Cliente cliente, boolean vacio) {
                super.updateItem(cliente, vacio);

                if (vacio || cliente == null) {
                    setText(null);
                } else {
                    setText(cliente.getNombre() + " - " + cliente.getDocumento());
                }
            }
        });

        cmbCliente.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Cliente cliente, boolean vacio) {
                super.updateItem(cliente, vacio);

                if (vacio || cliente == null) {
                    setText("Seleccionar cliente");
                } else {
                    setText(cliente.getNombre());
                }
            }
        });

        cmbEvento.setCellFactory(lista -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Evento evento, boolean vacio) {
                super.updateItem(evento, vacio);

                if (vacio || evento == null) {
                    setText(null);
                } else {
                    setText(evento.getNombre());
                }
            }
        });

        cmbEvento.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Evento evento, boolean vacio) {
                super.updateItem(evento, vacio);

                if (vacio || evento == null) {
                    setText("Seleccionar evento");
                } else {
                    setText(evento.getNombre());
                }
            }
        });
    }

    private void cargarClientes() {

        ObservableList<Cliente> clientes =
                FXCollections.observableArrayList(
                        ventaService.obtenerClientes()
                );

        cmbCliente.setItems(clientes);
    }

    private void cargarEventos() {

        ObservableList<Evento> eventos =
                FXCollections.observableArrayList(
                        eventoService.obtenerEventos()
                );

        cmbEvento.setItems(eventos);
    }

    private void cargarBoletos() {

        ObservableList<Boleto> boletos =
                FXCollections.observableArrayList(
                        ventaService.obtenerBoletos()
                );

        tablaBoletos.setItems(boletos);
    }

    @FXML
    public void registrarCliente() {

        String nombre = txtNombreCliente.getText().trim();
        String documento = txtDocumento.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (nombre.isEmpty() || documento.isEmpty() || correo.isEmpty()) {

            mostrarMensaje(
                    "Campos incompletos",
                    "Completa todos los datos del cliente.",
                    Alert.AlertType.WARNING
            );

            return;
        }

        if (!correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {

            mostrarMensaje(
                    "Correo inválido",
                    "Introduce una dirección de correo válida.",
                    Alert.AlertType.WARNING
            );

            return;
        }

        try {

            String id = UUID.randomUUID().toString();

            Cliente cliente = new Cliente(
                    id,
                    nombre,
                    documento,
                    correo,
                    EstadoCliente.ACTIVO
            );

            ventaService.registrarCliente(cliente);

            cargarClientes();

            cmbCliente.getSelectionModel().select(cliente);

            txtNombreCliente.clear();
            txtDocumento.clear();
            txtCorreo.clear();

            mostrarMensaje(
                    "Cliente registrado",
                    "El cliente fue registrado correctamente.",
                    Alert.AlertType.INFORMATION
            );

        } catch (IllegalArgumentException e) {

            mostrarMensaje(
                    "No se pudo registrar",
                    e.getMessage(),
                    Alert.AlertType.WARNING
            );
        }
    }

    @FXML
    public void calcularTotal() {

        actualizarTotal();

        if (cmbEvento.getValue() == null || txtCantidad.getText().isBlank()) {
            mostrarMensaje(
                    "Datos incompletos",
                    "Selecciona un evento e introduce la cantidad.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        try {

            int cantidad = Integer.parseInt(txtCantidad.getText().trim());

            validacionService.validarCantidad(cantidad);

            double total = validacionService.calcularValorTotal(
                    cmbEvento.getValue(),
                    cantidad
            );

            lblTotal.setText(formatearDinero(total));

        } catch (NumberFormatException e) {

            mostrarMensaje(
                    "Cantidad inválida",
                    "La cantidad debe ser un número entero.",
                    Alert.AlertType.WARNING
            );

        } catch (IllegalArgumentException e) {

            mostrarMensaje(
                    "No se pudo calcular",
                    e.getMessage(),
                    Alert.AlertType.WARNING
            );
        }
    }

    private void actualizarTotal() {

        if (cmbEvento.getValue() == null || txtCantidad.getText().isBlank()) {
            lblTotal.setText("$ 0.00");
            return;
        }

        try {

            int cantidad = Integer.parseInt(txtCantidad.getText().trim());

            if (cantidad <= 0) {
                lblTotal.setText("$ 0.00");
                return;
            }

            double total = validacionService.calcularValorTotal(
                    cmbEvento.getValue(),
                    cantidad
            );

            lblTotal.setText(formatearDinero(total));

        } catch (NumberFormatException e) {
            lblTotal.setText("$ 0.00");
        }
    }

    @FXML
    public void registrarVenta() {

        Cliente cliente = cmbCliente.getValue();
        Evento evento = cmbEvento.getValue();

        String cantidadTexto = txtCantidad.getText().trim();
        String operador = txtOperador.getText().trim();

        if (cliente == null || evento == null
                || cantidadTexto.isEmpty() || operador.isEmpty()) {

            mostrarMensaje(
                    "Datos incompletos",
                    "Selecciona un cliente, un evento, la cantidad e indica el operador.",
                    Alert.AlertType.WARNING
            );

            return;
        }

        try {

            int cantidad = Integer.parseInt(cantidadTexto);

            Boleto boleto = ventaService.comprarBoletos(
                    cliente.getId(),
                    evento.getId(),
                    cantidad,
                    operador
            );

            cargarBoletos();
            cargarEventos();

            cmbEvento.getSelectionModel().select(evento);

            txtCantidad.clear();
            lblTotal.setText("$ 0.00");

            mostrarMensaje(
                    "Venta realizada",
                    "La compra se registró correctamente.\n\n"
                            + "Código: " + boleto.getCodigo()
                            + "\nCliente: " + cliente.getNombre()
                            + "\nEvento: " + evento.getNombre()
                            + "\nCantidad: " + boleto.getCantidad()
                            + "\nTotal: " + formatearDinero(boleto.getValorTotal()),
                    Alert.AlertType.INFORMATION
            );

        } catch (NumberFormatException e) {

            mostrarMensaje(
                    "Cantidad inválida",
                    "Introduce una cantidad válida de boletos.",
                    Alert.AlertType.WARNING
            );

        } catch (IllegalArgumentException e) {

            mostrarMensaje(
                    "Venta rechazada",
                    e.getMessage(),
                    Alert.AlertType.WARNING
            );
        }
    }

    private String formatearDinero(double valor) {

        NumberFormat formato = NumberFormat.getCurrencyInstance(
                Locale.forLanguageTag("es-CO")
        );

        return formato.format(valor);
    }

    @FXML
    public void irADashboard(ActionEvent event) {

        Navegacion.cambiarVista(
                "dashboard.fxml",
                (Node) event.getSource()
        );
    }

    @FXML
    public void irAEventos(ActionEvent event) {

        Navegacion.cambiarVista(
                "eventos.fxml",
                (Node) event.getSource()
        );
    }

    private void mostrarMensaje(
            String titulo,
            String mensaje,
            Alert.AlertType tipo
    ) {

        Alert alerta = new Alert(tipo);

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }
}
