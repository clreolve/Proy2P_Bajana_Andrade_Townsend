/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.App;
import com.neoterux.proyecto2p.utils.Counter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author neoterux
 */
public class DashboardController implements Initializable{
    
    private final Logger logger = LogManager.getLogger(getClass());
    
    @FXML
    private Label cLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logger.info("Initializing Dashboard Scene");
        
        var counter = new Counter(0);
        
        var counterThread = new Thread(App.appThreadGroup, ()->{
            
            while(true){
                
                try {
                    Thread.currentThread().sleep(1000);
                    Platform.runLater(()->cLabel.setText(String.format("Tiempo en la aplicación: %d segundos", counter.getCurrentValue())));
                    counter.step();
                } catch (InterruptedException ex) {
                    logger.error(ex);
                }
            }
        
        }, "Counter Thread");
        
        counterThread.start();
        
    }
    
}
