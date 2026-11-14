
package co.edu.uniquindio.softwarecrud.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Navegacion {

    public static void cambiarVista(String archivo, Node componente) {

        try {
            FXMLLoader loader = new FXMLLoader(
                    Navegacion.class.getResource(
                            "/co/edu/uniquindio/softwarecrud/" + archivo
                    )
            );

            Parent root = loader.load();

            Stage escenario = (Stage) componente.getScene().getWindow();

            Scene escena = new Scene(root);

            escena.getStylesheets().add(
                    Navegacion.class.getResource("/css/estilos.css").toExternalForm()
            );

            escenario.setScene(escena);
            escenario.setMaximized(true);
            escenario.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
