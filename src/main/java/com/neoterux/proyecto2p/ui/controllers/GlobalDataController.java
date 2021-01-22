/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.App;
import com.neoterux.proyecto2p.model.Pais;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * GlobalDataController - Controlador de la ventana que muestra datos globales.
 * @author danae
 */
public class GlobalDataController implements Initializable{
    
    private static Scene scene;
    
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
        Stage contryStg = App.newWindow("ui/countryData", 620, 550);
        contryStg.setTitle("Ordenar paises");
        contryStg.showAndWait();
        
    }
    
    /**
     * Método que inicializa la ventana mediante la carga del contenido de etiquetas definidas. 
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {     
        ArrayList<Pais> paises = Pais.cargarPaises();
        
        for(Pais p: paises){
            if ( p.getNombre().equals("World")){
                numGlobalCases.setText(String.valueOf(p.getCasos()));
                numGlobalDeath.setText(String.valueOf(p.getMuertes()));          
            }
        }
        
             
    }
    

   
  
    
    
}
