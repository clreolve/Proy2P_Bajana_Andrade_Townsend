/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.App;
import com.neoterux.proyecto2p.model.Country;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * GlobalDataController - Controlador de la ventana que muestra datos globales.
 * @author danae
 */
public class GlobalDataController implements Initializable{

    /**
     * Contiene el recuento internacional de contagios
     */
    private static Country globalData;
    
    @FXML
    private ImageView imgViewMap;
    
    @FXML
    private Label numGlobalCases;
    
    @FXML 
    private Label numGlobalDeath;
    
    @FXML 
    private Button btnConsultaPaises;
    
    /**
     * Método que muestra la nueva ventana al momento de presionar el botón de consulta.
     */
    @FXML
    public void consultaPais(){
        App.setRoot((Stage)numGlobalCases.getScene().getWindow(), "countryData", "Ordenar paises",550, 620);
        
    }
    
    /**
     * Método que inicializa la ventana mediante la carga del contenido de etiquetas definidas. 
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {     

        if ( globalData == null) {
            List<Country> paises = Country.get();
            for(Country p: paises){
                if ( p.getName().equals("World")){
                    globalData = p;
                    break;
                }
            }
            paises.remove(globalData);
        }
        numGlobalCases.setText(String.valueOf(globalData.getCases()));
        numGlobalDeath.setText(String.valueOf(globalData.getTotalDeaths()));
    }
    

   
  
    
    
}
