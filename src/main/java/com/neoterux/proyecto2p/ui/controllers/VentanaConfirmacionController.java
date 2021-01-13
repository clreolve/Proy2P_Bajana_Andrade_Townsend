/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 *
 * @author luis_
 */
public class VentanaConfirmacionController implements Initializable {

    @FXML
    private Button btn_registrar;

    @FXML
    private Button btn_Cancelare;

    public void botonCancelar() {
        try {
           App.setRoot("ui/VentanaMapa", " ");
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
