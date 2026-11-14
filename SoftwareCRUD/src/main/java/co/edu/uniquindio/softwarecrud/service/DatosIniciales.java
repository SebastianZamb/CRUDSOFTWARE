
package co.edu.uniquindio.softwarecrud.service;

import co.edu.uniquindio.softwarecrud.model.Cliente;
import co.edu.uniquindio.softwarecrud.model.Evento;
import co.edu.uniquindio.softwarecrud.model.EstadoCliente;
import co.edu.uniquindio.softwarecrud.model.EstadoEvento;

import java.time.LocalDate;

public class DatosIniciales {

    private DatosIniciales() {
    }

    public static void inicializar(EventoService eventoService,
                                   VentaService ventaService) {

        // EVENTO 1
        eventoService.registrarEvento(new Evento(
                "EVT-001",
                "Concierto de prueba",
                LocalDate.of(2026, 12, 20),
                "Auditorio principal",
                20,
                0,
                50000,
                EstadoEvento.ABIERTO
        ));

        // EVENTO 2
        eventoService.registrarEvento(new Evento(
                "EVT-002",
                "Festival universitario",
                LocalDate.of(2026, 12, 15),
                "Universidad",
                50,
                0,
                30000,
                EstadoEvento.ABIERTO
        ));

        // EVENTO 3
        eventoService.registrarEvento(new Evento(
                "EVT-003",
                "Evento cancelado",
                LocalDate.of(2026, 12, 18),
                "Centro de eventos",
                30,
                0,
                25000,
                EstadoEvento.CANCELADO
        ));

        // CLIENTE 1
        ventaService.registrarCliente(new Cliente(
                "CLI-001",
                "Juan Perez",
                "1001",
                "juan@test.com",
                EstadoCliente.ACTIVO
        ));

        // CLIENTE 2
        ventaService.registrarCliente(new Cliente(
                "CLI-002",
                "Maria Lopez",
                "1002",
                "maria@test.com",
                EstadoCliente.ACTIVO
        ));

        // CLIENTE 3
        ventaService.registrarCliente(new Cliente(
                "CLI-003",
                "Carlos Gomez",
                "1003",
                "carlos@test.com",
                EstadoCliente.ACTIVO
        ));

        // CLIENTE 4
        ventaService.registrarCliente(new Cliente(
                "CLI-004",
                "Ana Rodriguez",
                "1004",
                "ana@test.com",
                EstadoCliente.ACTIVO
        ));
    }
}
