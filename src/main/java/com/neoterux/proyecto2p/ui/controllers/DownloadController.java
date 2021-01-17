/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.utils.Counter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
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

    /**
     * La cantidad total de archivos a descargar
     */
    private int total;
    /**
     * Contador usado durante la descarga.
     */
    private Counter counter;

    /**
     * Configura el controlador
     */
    public DownloadController(){
        this.total = -1;
    }

    /**
     * Obtiene el valor actual de archivos descargados.
     * @return valor actual de archivos descargados.
     */
    public int getCurrentValue() {
        return this.counter.getCurrentValue();
    }

    /**
     * Coloca el valor máximo de archivos adescargar
     *
     * @param max valor máximo de archivos.
     */
    public void setMaxProgress(int max) {
        if (counter == null) {
            counter = new Counter(1);
        }
        this.total = max;
    }

    /**
     * Actualiza el label con el progreso actual de descarga.
     */
    public void updateProgress(){
        Platform.runLater(() -> {
            this.dLabel.setText(String.format("Descargando  [%d/%d] ", counter.getCurrentValue(), total));
            this.progress.setProgress((double)counter.getCurrentValue() / (double)total);
        });
        
        counter.step();
    }

    /**
     * Cierra la ventana del la interfaz de descarga
     */
    public void close() {
        ((Stage)root.getScene().getWindow()).close();
    }
}
