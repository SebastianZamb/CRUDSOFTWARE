
package co.edu.uniquindio.softwarecrud.model;

import java.time.LocalDateTime;

public class Boleto {

    private String id;
    private String codigo;
    private int cantidad;
    private double valorTotal;
    private LocalDateTime fechaCompra;
    private String usuarioOperador;

    private EstadoBoleto estado;

    private Cliente cliente;
    private Evento evento;

    public Boleto(String id, String codigo, int cantidad,
                  double valorTotal, LocalDateTime fechaCompra,
                  String usuarioOperador, EstadoBoleto estado,
                  Cliente cliente, Evento evento) {

        this.id = id;
        this.codigo = codigo;
        this.cantidad = cantidad;
        this.valorTotal = valorTotal;
        this.fechaCompra = fechaCompra;
        this.usuarioOperador = usuarioOperador;
        this.estado = estado;
        this.cliente = cliente;
        this.evento = evento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getUsuarioOperador() {
        return usuarioOperador;
    }

    public void setUsuarioOperador(String usuarioOperador) {
        this.usuarioOperador = usuarioOperador;
    }

    public EstadoBoleto getEstado() {
        return estado;
    }

    public void setEstado(EstadoBoleto estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}
