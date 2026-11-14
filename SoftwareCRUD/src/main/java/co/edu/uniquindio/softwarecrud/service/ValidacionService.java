
package co.edu.uniquindio.softwarecrud.service;

import co.edu.uniquindio.softwarecrud.model.Boleto;
import co.edu.uniquindio.softwarecrud.model.Cliente;
import co.edu.uniquindio.softwarecrud.model.Evento;
import co.edu.uniquindio.softwarecrud.model.EstadoCliente;
import co.edu.uniquindio.softwarecrud.model.EstadoEvento;
import co.edu.uniquindio.softwarecrud.model.EstadoBoleto;

import java.time.LocalDate;
import java.util.ArrayList;

public class ValidacionService {

    public void validarVenta(Evento evento, Cliente cliente,
                             int cantidad, ArrayList<Boleto> boletos) {

        if (evento == null) {
            throw new IllegalArgumentException("El evento no existe");
        }

        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no existe");
        }

        validarEventoAbierto(evento);
        validarFechaEvento(evento);
        validarClienteActivo(cliente);
        validarCantidad(cantidad);
        validarAforo(evento, cantidad);
        validarLimiteCliente(cliente, evento, cantidad, boletos);
    }

    // R1: El evento debe estar abierto

    public void validarEventoAbierto(Evento evento) {

        if (evento.getEstado() != EstadoEvento.ABIERTO) {
            throw new IllegalArgumentException(
                    "El evento no está abierto para la venta"
            );
        }
    }

    // R2: Debe existir aforo suficiente

    public void validarAforo(Evento evento, int cantidad) {

        if (evento.aforoDisponible() < cantidad) {
            throw new IllegalArgumentException(
                    "No hay suficientes boletos disponibles"
            );
        }
    }

    // R3: El cliente debe estar activo

    public void validarClienteActivo(Cliente cliente) {

        if (cliente.getEstado() != EstadoCliente.ACTIVO) {
            throw new IllegalArgumentException(
                    "El cliente está inactivo"
            );
        }
    }

    // R4: Máximo cinco boletos por cliente y evento

    public void validarLimiteCliente(Cliente cliente, Evento evento,
                                     int cantidad, ArrayList<Boleto> boletos) {

        int boletosCliente = 0;

        for (Boleto boleto : boletos) {

            if (boleto.getEstado() == EstadoBoleto.ACTIVO
                    && boleto.getCliente().getId().equals(cliente.getId())
                    && boleto.getEvento().getId().equals(evento.getId())) {

                boletosCliente += boleto.getCantidad();
            }
        }

        if (boletosCliente + cantidad > 5) {
            throw new IllegalArgumentException(
                    "El máximo permitido es de 5 boletos por cliente y evento"
            );
        }
    }

    // Validación de cantidad

    public void validarCantidad(int cantidad) {

        if (cantidad <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor que cero"
            );
        }
    }

    // R5: Calcular valor total

    public double calcularValorTotal(Evento evento, int cantidad) {

        return evento.getPrecio() * cantidad;
    }

    // R6: El evento no debe haber pasado

    public void validarFechaEvento(Evento evento) {

        if (evento.getFecha().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "El evento ya pasó, no se pueden vender boletos"
            );
        }
    }
}
