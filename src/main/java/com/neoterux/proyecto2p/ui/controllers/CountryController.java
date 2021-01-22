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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
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
    private VBox nombreCol;

    @FXML
    private VBox casosCol;

    @FXML
    private VBox muertesCol;

    @FXML
    private VBox imgCol;
    
    @FXML
    private Button btnConsulta;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cbxOrden.getItems().setAll(OrdenBusqueda.CASOS, OrdenBusqueda.MUERTES);

    }

    /**
     * Ordena la lista paises, obtiene los 10 primeros e invoca a la función que muestra la información.
     * @param event
     */
    @FXML
    public void mostrarOrden(ActionEvent event) {
        imgCol.getChildren().clear();
        muertesCol.getChildren().clear();
        casosCol.getChildren().clear();
        nombreCol.getChildren().clear();

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
                    mostrarPais(paises.get(i));
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
                    mostrarPais(paises.get(i));
                }
                break;

        }
    }

    /**
     * Método encargado de mostrar la información de cada país.
     * 
     * @param pais
     */
    public void mostrarPais(Pais pais) {
        ImageView imgView = null;

        try (FileInputStream input = new FileInputStream(App.FLAGS_PATH + "/" + pais.getNombre().toLowerCase() + ".png")) {
            Image image = new Image(input,50,34,false,false);
            imgView = new ImageView(image);
        } catch (IOException ex) {
            new Alert(Alert.AlertType.WARNING, "Existen problemas técnicos. Vuelva a intentarlo más tarde.").showAndWait();
        }

        nombreCol.getChildren()
                .add(new Label(pais.getNombre()));
        casosCol.getChildren()
                .add(new Label(String.valueOf(pais.getCasos())));
        muertesCol.getChildren()
                .add(new Label(String.valueOf(pais.getMuertes())));
        imgCol.getChildren()
                .add(imgView);

    }
    
    /**
     * Método que muestra la nueva ventana al momento de presionar el botón de consulta. 
     */
    @FXML
    public void consultaAction(){
        Stage contryStg = App.newWindow("ui/dashboard", 680, 550);
        contryStg.setTitle("Dashboard");
        contryStg.showAndWait();
    }
    
    
}
