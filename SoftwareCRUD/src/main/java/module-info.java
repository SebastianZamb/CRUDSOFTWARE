
module co.edu.uniquindio.softwarecrud {

    requires javafx.controls;
    requires javafx.fxml;

    exports co.edu.uniquindio.softwarecrud;

    opens co.edu.uniquindio.softwarecrud to javafx.fxml;
    opens co.edu.uniquindio.softwarecrud.controller to javafx.fxml;
    opens co.edu.uniquindio.softwarecrud.model to javafx.base;
}
