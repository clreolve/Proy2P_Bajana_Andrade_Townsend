module com.neoterux.proyecto2p {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.neoterux.proyecto2p to javafx.fxml;
    opens com.neoterux.proyecto2p.ui.controllers to javafx.fxml;
    exports com.neoterux.proyecto2p;
}
