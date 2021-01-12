/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.App;
import com.neoterux.proyecto2p.utils.Counter;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author neoterux
 */
public class DownloadController {
    
    @FXML
    private VBox root;
    
    @FXML
    private Label dLabel;

    @FXML
    private ProgressBar progress;
    
    private int total;
    private Counter counter;
    private String target;
    
    
    public DownloadController(){
        this.total = -1;
        target = "";
          
    }
    
    
    /**
     * Coloca el objetivo de la descarga.
     * Ej. Descargado [--/--] {target}
     * 
     * @param message 
     */
    public void target(String message){
        this.dLabel.setText(message);
    }
    
    public int getCurrentValue() {
        return this.counter.getCurrentValue();
    }
    
    public void setMaxProgress(int max) {
        if (counter == null) {
            counter = new Counter(1);
        }
        this.total = max;
    }
            
    public void updateProgress(){
        Platform.runLater(() -> {
            //System.out.println("label: " + dLabel + " progress: " + progress);
            this.dLabel.setText(String.format("Descargando  [%d/%d] %s", counter.getCurrentValue(), total, target));
            this.progress.setProgress((double)counter.getCurrentValue() / (double)total);
            //System.out.println((double)counter.getCurrentValue() / (double)total);

        });
        
        counter.step();
    }
    
    
}
