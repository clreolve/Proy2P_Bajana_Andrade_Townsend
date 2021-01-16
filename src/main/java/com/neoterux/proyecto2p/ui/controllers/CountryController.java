/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.model.Pais;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 *
 * @author danae
 */
public class CountryController implements Initializable{
    
    ArrayList<Pais> paises = Pais.cargarPaises();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
}
