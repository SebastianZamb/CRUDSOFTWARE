
package co.edu.uniquindio.softwarecrud.service;

import co.edu.uniquindio.softwarecrud.model.Boleto;
import co.edu.uniquindio.softwarecrud.model.Cliente;
import co.edu.uniquindio.softwarecrud.model.Evento;
import co.edu.uniquindio.softwarecrud.model.EstadoBoleto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class VentaService {

    private final ArrayList<Cliente> clientes;
    private final ArrayList<Boleto> boletos;

    private final EventoService eventoService;
    private final ValidacionService validacionService;

    public VentaService(EventoService eventoService,
                        ValidacionService validacionService) {

        this.clientes = new ArrayList<>();
        this.boletos = new ArrayList<>();

        this.eventoService = eventoService;
        this.validacionService = validacionService;
    }

    // Registrar cliente

    public void registrarCliente(Cliente cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }

        if (buscarCliente(cliente.getId()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un cliente con ese ID"
            );
        }

        for (Cliente existente : clientes) {

            if (existente.getDocumento().equals(cliente.getDocumento())) {
                throw new IllegalArgumentException(
                        "Ya existe un cliente con ese documento"
                );
            }
        }

        clientes.add(cliente);
    }

    // Buscar cliente por ID

    public Cliente buscarCliente(String id) {

        for (Cliente cliente : clientes) {

            if (cliente.getId().equals(id)) {
                return cliente;
            }
        }

        return null;
    }

    // Buscar cliente por documento

    public Cliente buscarClientePorDocumento(String documento) {

        for (Cliente cliente : clientes) {

            if (cliente.getDocumento().equals(documento)) {
                return cliente;
            }
        }

        return null;
    }

    // Obtener clientes

    public ArrayList<Cliente> obtenerClientes() {
        return new ArrayList<>(clientes);
    }

    // Registrar compra

    public Boleto comprarBoletos(String clienteId, String eventoId,
                                 int cantidad, String usuarioOperador) {

        Cliente cliente = buscarCliente(clienteId);
        Evento evento = eventoService.buscarEvento(eventoId);

        if (usuarioOperador == null || usuarioOperador.isBlank()) {
            throw new IllegalArgumentException(
                    "Debe indicar el usuario operador"
            );
        }

        // Validar las reglas de negocio
        validacionService.validarVenta(
                evento, cliente, cantidad, boletos
        );

        // Calcular el valor total
        double valorTotal = validacionService.calcularValorTotal(
                evento, cantidad
        );

        // Generar identificadores
        String id = UUID.randomUUID().toString();
        String codigo = "BOL-" + UUID.randomUUID()
                .toString().substring(0, 8).toUpperCase();

        // Registrar fecha y operador
        LocalDateTime fechaCompra = LocalDateTime.now();

        // Crear boleto
        Boleto boleto = new Boleto(
                id,
                codigo,
                cantidad,
                valorTotal,
                fechaCompra,
                usuarioOperador,
                EstadoBoleto.ACTIVO,
                cliente,
                evento
        );

        // Registrar compra
        boletos.add(boleto);

        // Actualizar aforo vendido
        evento.setBoletosVendidos(
                evento.getBoletosVendidos() + cantidad
        );

        return boleto;
    }

    // Obtener boletos

    public ArrayList<Boleto> obtenerBoletos() {
        return new ArrayList<>(boletos);
    }
}
