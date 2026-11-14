
package co.edu.uniquindio.softwarecrud.service;

public class ContextoAplicacion {

    private static final EventoService eventoService =
            new EventoService();

    private static final ValidacionService validacionService =
            new ValidacionService();

    private static final VentaService ventaService =
            new VentaService(eventoService, validacionService);

    // Cargar datos iniciales una sola vez
    static {
        DatosIniciales.inicializar(eventoService, ventaService);
    }

    private ContextoAplicacion() {
    }

    public static EventoService getEventoService() {
        return eventoService;
    }

    public static ValidacionService getValidacionService() {
        return validacionService;
    }

    public static VentaService getVentaService() {
        return ventaService;
    }
}
