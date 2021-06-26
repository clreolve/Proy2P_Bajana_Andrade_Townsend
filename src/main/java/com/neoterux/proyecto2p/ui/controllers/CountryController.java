/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.App;
import com.neoterux.proyecto2p.model.Country;
import com.neoterux.proyecto2p.model.OrdenBusqueda;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * CountryController - Controlador de la ventana que muestra datos por paises.
 *
 * @author danae
 */
public class CountryController implements Initializable {

    private List<Country> paises = Country.get();

    @FXML
    private ComboBox<OrdenBusqueda> cbxOrden;

    @FXML
    private GridPane gpInfo;

    @FXML
    private Button btnConsulta;

    @FXML
    private Node principal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cbxOrden.getItems().setAll(OrdenBusqueda.CASOS, OrdenBusqueda.MUERTES);

    }

    /**
     * Ordena la lista paises, obtiene los 10 primeros e invoca a la función que
     * muestra la información.
     *
     * @param event action event
     */
    @FXML
    public void mostrarOrden(ActionEvent event) {

        gpInfo.getChildren().clear();
        OrdenBusqueda orden = cbxOrden.getValue();
        Country.setOrder(orden);
        Collections.sort(paises);

        for (int i = 0; i < 10; i++) {
            ShowCountryController sc = new ShowCountryController(paises.get(i));
            sc.show(gpInfo, i);
        }
    }

    /**
     * Método que muestra la nueva ventana al momento de presionar el botón de
     * consulta.
     */
    @FXML
    public void consultaAction() {
        var windowStage = (Stage) principal.getScene().getWindow();
        var nw = App.newWindow("ui/dashboard", 683, 450);
        nw.setTitle("Dashboard");
        nw.show();
        windowStage.close();
    }

}
