/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.App;
import com.neoterux.proyecto2p.model.Country;
import com.neoterux.proyecto2p.model.OrdenBusqueda;
import com.neoterux.proyecto2p.model.Pais;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

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
            mostrarPais(paises.get(i), i);
        }
    }

    /**
     * Método encargado de mostrar la información de cada país.
     *  @param pais país a agregar al table view
     *  @param i indice de la fila.
     */
    public void mostrarPais(Country pais, int i) {
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

        gpInfo.addRow(i);
        gpInfo.add(imgView, 0, i);
        gpInfo.add(lblnombre, 1, i);
        gpInfo.add(lblMuertes, 3, i);
        gpInfo.add(lblCasos, 2, i);

    }

    /**
     * Método que muestra la nueva ventana al momento de presionar el botón de
     * consulta.
     */
    @FXML
    public void consultaAction() {
        var windowStage = (Stage) principal.getScene().getWindow();
        App.setRoot(windowStage, "dashboard", "Dashboard", 600, 800);
    }

}
