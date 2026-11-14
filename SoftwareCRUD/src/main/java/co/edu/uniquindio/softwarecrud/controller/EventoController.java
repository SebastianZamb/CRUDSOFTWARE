
package co.edu.uniquindio.softwarecrud.controller;

import co.edu.uniquindio.softwarecrud.model.Evento;
import co.edu.uniquindio.softwarecrud.service.ContextoAplicacion;
import co.edu.uniquindio.softwarecrud.service.EventoService;
import co.edu.uniquindio.softwarecrud.util.Navegacion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.Optional;

public class EventoController {

    private final EventoService eventoService =
            ContextoAplicacion.getEventoService();

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombre;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private TextField txtLugar;

    @FXML
    private TextField txtAforo;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TableView<Evento> tablaEventos;

    @FXML
    private TableColumn<Evento, String> colId;

    @FXML
    private TableColumn<Evento, String> colNombre;

    @FXML
    private TableColumn<Evento, LocalDate> colFecha;

    @FXML
    private TableColumn<Evento, String> colLugar;

    @FXML
    private TableColumn<Evento, Integer> colAforo;

    @FXML
    private TableColumn<Evento, Integer> colVendidos;

    @FXML
    private TableColumn<Evento, Double> colPrecio;

    @FXML
    private TableColumn<Evento, Object> colEstado;

    @FXML
    public void initialize() {

        configurarTabla();
        cargarEventos();

        tablaEventos.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> {

                    if (seleccionado != null) {
                        cargarFormulario(seleccionado);
                    }

                });
    }

    private void configurarTabla() {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );

        colFecha.setCellValueFactory(
                new PropertyValueFactory<>("fecha")
        );

        colLugar.setCellValueFactory(
                new PropertyValueFactory<>("lugar")
        );

        colAforo.setCellValueFactory(
                new PropertyValueFactory<>("aforoTotal")
        );

        colVendidos.setCellValueFactory(
                new PropertyValueFactory<>("boletosVendidos")
        );

        colPrecio.setCellValueFactory(
                new PropertyValueFactory<>("precio")
        );

        colEstado.setCellValueFactory(
                new PropertyValueFactory<>("estado")
        );
    }

    private void cargarEventos() {

        ObservableList<Evento> lista =
                FXCollections.observableArrayList(
                        eventoService.obtenerEventos()
                );

        tablaEventos.setItems(lista);
    }

    private void cargarFormulario(Evento evento) {

        txtId.setText(evento.getId());
        txtNombre.setText(evento.getNombre());
        dpFecha.setValue(evento.getFecha());
        txtLugar.setText(evento.getLugar());

        txtAforo.setText(
                String.valueOf(evento.getAforoTotal())
        );

        txtPrecio.setText(
                String.valueOf(evento.getPrecio())
        );
    }

    @FXML
    public void registrarEvento() {

        try {

            String id = txtId.getText().trim();
            String nombre = txtNombre.getText().trim();
            String lugar = txtLugar.getText().trim();

            LocalDate fecha = dpFecha.getValue();

            if (id.isEmpty() || nombre.isEmpty() || lugar.isEmpty()
                    || fecha == null || txtAforo.getText().isBlank()
                    || txtPrecio.getText().isBlank()) {

                mostrarMensaje(
                        "Campos incompletos",
                        "Debes completar todos los campos.",
                        Alert.AlertType.WARNING
                );

                return;
            }

            int aforo = Integer.parseInt(txtAforo.getText().trim());
            double precio = Double.parseDouble(txtPrecio.getText().trim());

            if (aforo <= 0 || precio <= 0) {
                throw new IllegalArgumentException(
                        "El aforo y el precio deben ser mayores que cero."
                );
            }

            Evento evento = new Evento(
                    id,
                    nombre,
                    fecha,
                    lugar,
                    aforo,
                    0,
                    precio,
                    co.edu.uniquindio.softwarecrud.model.EstadoEvento.ABIERTO
            );

            eventoService.registrarEvento(evento);

            cargarEventos();
            limpiarCampos();

            mostrarMensaje(
                    "Registro exitoso",
                    "El evento se registró correctamente.",
                    Alert.AlertType.INFORMATION
            );

        } catch (NumberFormatException e) {

            mostrarMensaje(
                    "Datos incorrectos",
                    "El aforo debe ser un número entero y el precio debe ser numérico.",
                    Alert.AlertType.WARNING
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
    public void actualizarEvento() {

        Evento seleccionado =
                tablaEventos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarMensaje(
                    "Selección requerida",
                    "Selecciona un evento de la tabla.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        try {

            String nombre = txtNombre.getText().trim();
            String lugar = txtLugar.getText().trim();
            LocalDate fecha = dpFecha.getValue();

            if (nombre.isEmpty() || lugar.isEmpty()
                    || fecha == null || txtAforo.getText().isBlank()
                    || txtPrecio.getText().isBlank()) {

                mostrarMensaje(
                        "Campos incompletos",
                        "Debes completar todos los campos.",
                        Alert.AlertType.WARNING
                );

                return;
            }

            int aforo = Integer.parseInt(txtAforo.getText().trim());
            double precio = Double.parseDouble(txtPrecio.getText().trim());

            if (aforo <= 0 || precio <= 0) {
                throw new IllegalArgumentException(
                        "El aforo y el precio deben ser mayores que cero."
                );
            }

            Evento actualizado = new Evento(
                    seleccionado.getId(),
                    nombre,
                    fecha,
                    lugar,
                    aforo,
                    seleccionado.getBoletosVendidos(),
                    precio,
                    seleccionado.getEstado()
            );

            eventoService.actualizarEvento(actualizado);

            cargarEventos();
            tablaEventos.getSelectionModel().select(actualizado);

            mostrarMensaje(
                    "Actualización exitosa",
                    "El evento se actualizó correctamente.",
                    Alert.AlertType.INFORMATION
            );

        } catch (NumberFormatException e) {

            mostrarMensaje(
                    "Datos incorrectos",
                    "El aforo y el precio deben ser valores numéricos válidos.",
                    Alert.AlertType.WARNING
            );

        } catch (IllegalArgumentException e) {

            mostrarMensaje(
                    "No se pudo actualizar",
                    e.getMessage(),
                    Alert.AlertType.WARNING
            );
        }
    }

    @FXML
    public void eliminarEvento() {

        Evento seleccionado =
                tablaEventos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarMensaje(
                    "Selección requerida",
                    "Selecciona un evento para eliminarlo.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        Optional<ButtonType> respuesta = confirmar(
                "¿Eliminar el evento " + seleccionado.getNombre() + "?"
        );

        if (respuesta.isEmpty()
                || respuesta.get() != ButtonType.OK) {
            return;
        }

        try {

            eventoService.eliminarEvento(seleccionado.getId());

            cargarEventos();
            limpiarCampos();

            mostrarMensaje(
                    "Eliminación exitosa",
                    "El evento fue eliminado.",
                    Alert.AlertType.INFORMATION
            );

        } catch (IllegalArgumentException e) {

            mostrarMensaje(
                    "No se pudo eliminar",
                    e.getMessage(),
                    Alert.AlertType.WARNING
            );
        }
    }

    @FXML
    public void cancelarEvento() {

        Evento seleccionado =
                tablaEventos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarMensaje(
                    "Selección requerida",
                    "Selecciona un evento para cancelarlo.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        Optional<ButtonType> respuesta = confirmar(
                "¿Cancelar el evento " + seleccionado.getNombre() + "?"
        );

        if (respuesta.isEmpty()
                || respuesta.get() != ButtonType.OK) {
            return;
        }

        try {

            eventoService.cancelarEvento(seleccionado.getId());

            cargarEventos();
            tablaEventos.refresh();
            limpiarCampos();

            mostrarMensaje(
                    "Evento cancelado",
                    "El evento fue cancelado correctamente.",
                    Alert.AlertType.INFORMATION
            );

        } catch (IllegalArgumentException e) {

            mostrarMensaje(
                    "No se pudo cancelar",
                    e.getMessage(),
                    Alert.AlertType.WARNING
            );
        }
    }

    @FXML
    public void limpiarCampos() {

        txtId.clear();
        txtNombre.clear();
        dpFecha.setValue(null);
        txtLugar.clear();
        txtAforo.clear();
        txtPrecio.clear();

        tablaEventos.getSelectionModel().clearSelection();
    }

    @FXML
    public void irADashboard(ActionEvent event) {

        Navegacion.cambiarVista(
                "dashboard.fxml",
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

    private Optional<ButtonType> confirmar(String mensaje) {

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);

        alerta.setTitle("Confirmar operación");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        return alerta.showAndWait();
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
