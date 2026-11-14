
package co.edu.uniquindio.softwarecrud.model;

public class Cliente {

    private String id;
    private String nombre;
    private String documento;
    private String correo;
    private EstadoCliente estado;

    public Cliente(String id, String nombre, String documento,
                   String correo, EstadoCliente estado) {

        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.correo = correo;
        this.estado = estado;
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public EstadoCliente getEstado() {
        return estado;
    }

    public void setEstado(EstadoCliente estado) {
        this.estado = estado;
    }
}
