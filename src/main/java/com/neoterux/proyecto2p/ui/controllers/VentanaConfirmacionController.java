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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author luis_
 */
public class VentanaConfirmacionController implements Initializable {
    
    private static final Logger logger = LogManager.getLogger(VentanaConfirmacionController.class);

    File archivo;
    BufferedWriter bufferedWriter;
    

    @FXML
    private Button btn_registrar;

    @FXML
    private Button btn_Cancelar;

    
    @FXML
    public void botonCancelar(ActionEvent aev) {
        Stage stage = (Stage) btn_Cancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void botonRegistrar(ActionEvent aev) {
        VentanaMapaController datos = new VentanaMapaController();
        try {
            
            archivo = Paths.get(App.class.getResource("res/lugares.txt").toURI()).toFile();
            bufferedWriter = new BufferedWriter(new FileWriter(archivo));
            bufferedWriter.write(datos.getPunto_principal().getX() + "-" + datos.getPunto_principal().getY());
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            logger.error("An IOException ocurrend when trying to write file", e);
        } catch (URISyntaxException ex) {
            logger.error("An error ocurred in file uri syntax", ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
