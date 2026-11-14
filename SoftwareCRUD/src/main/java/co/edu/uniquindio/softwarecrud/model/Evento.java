
package co.edu.uniquindio.softwarecrud.model;

import java.time.LocalDate;

public class Evento {

    private String id;
    private String nombre;
    private LocalDate fecha;
    private String lugar;
    private int aforoTotal;
    private int boletosVendidos;
    private double precio;
    private EstadoEvento estado;

    public Evento(String id, String nombre, LocalDate fecha,
                  String lugar, int aforoTotal,
                  int boletosVendidos, double precio,
                  EstadoEvento estado) {

        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.lugar = lugar;
        this.aforoTotal = aforoTotal;
        this.boletosVendidos = boletosVendidos;
        this.precio = precio;
        this.estado = estado;
    }

    public int aforoDisponible() {
        return aforoTotal - boletosVendidos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public int getAforoTotal() {
        return aforoTotal;
    }

    public void setAforoTotal(int aforoTotal) {
        this.aforoTotal = aforoTotal;
    }

    public int getBoletosVendidos() {
        return boletosVendidos;
    }

    public void setBoletosVendidos(int boletosVendidos) {
        this.boletosVendidos = boletosVendidos;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public EstadoEvento getEstado() {
        return estado;
    }

    public void setEstado(EstadoEvento estado) {
        this.estado = estado;
    }
}
