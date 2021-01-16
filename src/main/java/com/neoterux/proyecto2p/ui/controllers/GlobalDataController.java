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
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 *
 * @author danae
 */
public class GlobalDataController {
    
    @FXML 
    private ImageView imgViewMap;
    
    @FXML
    private Label numGlobalCases;
    
    @FXML 
    private Label numGlobalDeath;
    
    @FXML 
    private Button btnConsultaPaises;
    
    @FXML
    public void consultaPais(){
        
    }
    
    public void initialize(URL url, ResourceBundle rb) {     
        ArrayList<Pais> paises = Pais.cargarPaises();
        System.out.println(paises);
        
        
    }
    
    
}
