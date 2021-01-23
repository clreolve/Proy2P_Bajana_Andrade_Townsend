/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.App;
import com.neoterux.proyecto2p.model.OrdenBusqueda;
import com.neoterux.proyecto2p.model.Pais;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;
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

    ArrayList<Pais> paises = Pais.cargarPaises();

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
     * @param event
     */
    @FXML
    public void mostrarOrden(ActionEvent event) {

        gpInfo.getChildren().clear();
        OrdenBusqueda orden = cbxOrden.getValue();

        switch (orden) {
            case CASOS:
                Collections.sort(paises, new Comparator<Pais>() {
                    @Override
                    public int compare(Pais p1, Pais p2) {
                        return ((Integer) p1.getCasos()).compareTo(p2.getCasos());
                    }
                }.reversed());

                for (int i = 1; i < 11; i++) {
                    mostrarPais(paises.get(i), i);
                }
                break;

            case MUERTES:
                Collections.sort(paises, new Comparator<Pais>() {
                    @Override
                    public int compare(Pais p1, Pais p2) {
                        return ((Integer) p1.getMuertes()).compareTo(p2.getMuertes());
                    }
                }.reversed());

                for (int i = 1; i < 11; i++) {
                    mostrarPais(paises.get(i), i);
                }
                break;

        }
    }

    /**
     * Método encargado de mostrar la información de cada país.
     *
     * @param pais
     * @param i
     */
    public void mostrarPais(Pais pais, int i) {
        ImageView imgView = null;

        try (FileInputStream input = new FileInputStream(App.FLAGS_PATH + "/" + pais.getNombre().toLowerCase() + ".png")) {
            Image image = new Image(input, 50, 34, false, false);
            imgView = new ImageView(image);
        } catch (IOException ex) {
            new Alert(Alert.AlertType.WARNING, "Existen problemas técnicos. Vuelva a intentarlo más tarde.").showAndWait();
        }

        Label lblnombre = new Label(pais.getNombre());
        Label lblCasos = new Label(String.valueOf(pais.getCasos()));
        Label lblMuertes = new Label(String.valueOf(pais.getMuertes()));

        gpInfo.addRow(i);
        gpInfo.add(imgView, 0, i);
        gpInfo.add(lblnombre, 1, i);
        gpInfo.add(lblMuertes, 2, i);
        gpInfo.add(lblCasos, 3, i);

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
