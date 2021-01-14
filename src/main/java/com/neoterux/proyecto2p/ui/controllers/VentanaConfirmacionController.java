/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.App;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author luis_
 */
public class VentanaConfirmacionController implements Initializable {

    File archivo;
    BufferedWriter bufferedWriter;

    @FXML
    private Button btn_registrar;

    @FXML
    private Button btn_Cancelar;

    public void botonCancelar() {
        Stage stage = (Stage) btn_Cancelar.getScene().getWindow();
        stage.close();
    }

    public void botonRegistrar() {
        VentanaMapaController datos = new VentanaMapaController();
        try {
            archivo = Paths.get(App.class.getResource("res/lugares.txt").toURI()).toFile();
            bufferedWriter = new BufferedWriter(new FileWriter(archivo));
            bufferedWriter.write(datos.getPunto_principal().getX() + "-" + datos.getPunto_principal().getY());
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.getMessage();
        } catch (URISyntaxException ex) {
            ex.getMessage();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
