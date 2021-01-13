package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
//Autor: AndradeLuis

public class VentanaMapaController implements Initializable {

    @FXML
    private ImageView mapa;

    private final Image imagen = new Image(App.resourceFrom(App.class, "ui/res/mapaguayaquil.png").toExternalForm());

    @FXML
    private Button btn_registrar;

    @FXML
    private Button btn_cerrar;

    @FXML
    private Label texto;

    public void botonRegistrar(ActionEvent event) {
        texto.setText("Boton Oprimido");
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(App.loadFXML("ui/VentanaConfirmacion"), 506, 307));
            stage.show();
            
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void botonCerrar() {
        texto.setText("Otro boton Oprimido");
        Stage stage = (Stage) btn_cerrar.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        texto.setText(" ");
        mapa.setImage(imagen);
        coordenadas();
    }

    public void coordenadas() {
        mapa.setPickOnBounds(true); // Esto permite la deteccion de clicks en la imagen
        mapa.setOnMouseClicked(e -> {
            System.out.println("[" + e.getX() + " , " + e.getY() + "]");
        });
    }
}
