/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.App;
import com.neoterux.proyecto2p.model.Pais;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 *
 * @author danae
 */
public class GlobalDataController implements Initializable{
    
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
        App.setRoot("countryData", "Ventana Países Ordenar", 550, 620);
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {     
        Pais.cargarPaises();
        numGlobalCases.setText(""+Pais.casosTotales);
        numGlobalDeath.setText(""+Pais.muertesTotales);
             
    }
   
  
    
    
}
