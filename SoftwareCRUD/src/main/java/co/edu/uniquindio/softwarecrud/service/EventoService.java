
package co.edu.uniquindio.softwarecrud.service;

import co.edu.uniquindio.softwarecrud.model.Evento;
import co.edu.uniquindio.softwarecrud.model.EstadoEvento;

import java.util.ArrayList;

public class EventoService {

    private final ArrayList<Evento> eventos;

    public EventoService() {
        eventos = new ArrayList<>();
    }

    public void registrarEvento(Evento evento) {

        if (evento == null) {
            throw new IllegalArgumentException("El evento no puede ser nulo");
        }

        if (buscarEvento(evento.getId()) != null) {
            throw new IllegalArgumentException("Ya existe un evento con ese ID");
        }

        eventos.add(evento);
    }

    public Evento buscarEvento(String id) {

        for (Evento evento : eventos) {
            if (evento.getId().equals(id)) {
                return evento;
            }
        }

        return null;
    }

    public ArrayList<Evento> obtenerEventos() {
        return new ArrayList<>(eventos);
    }

    public void actualizarEvento(Evento eventoActualizado) {

        if (eventoActualizado == null) {
            throw new IllegalArgumentException("El evento no puede ser nulo");
        }

        Evento eventoExistente = buscarEvento(eventoActualizado.getId());

        if (eventoExistente == null) {
            throw new IllegalArgumentException("El evento no existe");
        }

        if (eventoActualizado.getAforoTotal()
                < eventoExistente.getBoletosVendidos()) {
            throw new IllegalArgumentException(
                    "El aforo no puede ser menor que los boletos vendidos"
            );
        }

        int posicion = eventos.indexOf(eventoExistente);

        eventos.set(posicion, eventoActualizado);
    }

    public void eliminarEvento(String id) {

        Evento evento = buscarEvento(id);

        if (evento == null) {
            throw new IllegalArgumentException("El evento no existe");
        }

        if (evento.getBoletosVendidos() > 0) {
            throw new IllegalArgumentException(
                    "El evento tiene boletos vendidos, debe cancelarlo"
            );
        }

        eventos.remove(evento);
    }

    public void cancelarEvento(String id) {

        Evento evento = buscarEvento(id);

        if (evento == null) {
            throw new IllegalArgumentException("El evento no existe");
        }

        evento.setEstado(EstadoEvento.CANCELADO);
    }
}
