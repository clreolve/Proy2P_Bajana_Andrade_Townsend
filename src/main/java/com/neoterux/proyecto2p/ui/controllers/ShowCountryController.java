package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.App;
import com.neoterux.proyecto2p.model.Country;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.io.FileInputStream;
import java.io.IOException;


public class ShowCountryController {
    Country pais;
    ShowCountryController(Country pais){
        this.pais = pais;
    }

    /**
     * @param gpInfo GridPane a Agregar
     * @param index indice del Grip Pane
     */
    public void show(GridPane gpInfo, int index){
        ImageView imgView = null;

        try (FileInputStream input = new FileInputStream(App.FLAGS_PATH + "/" + pais.getName().toLowerCase() + ".png")) {
            Image image = new Image(input, 50, 34, false, false);
            imgView = new ImageView(image);
        } catch (IOException ex) {
            new Alert(Alert.AlertType.WARNING, "Existen problemas técnicos. Vuelva a intentarlo más tarde.").showAndWait();
        }

        Label lblnombre = new Label(pais.getName());
        Label lblCasos = new Label(String.valueOf(pais.getCases()));
        Label lblMuertes = new Label(String.valueOf(pais.getTotalDeaths()));

        gpInfo.addRow(index);
        gpInfo.add(imgView, 0, index);
        gpInfo.add(lblnombre, 1, index);
        gpInfo.add(lblMuertes, 3, index);
        gpInfo.add(lblCasos, 2, index);
    }
}
