/*
 * Copyright 2021 neoterux.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.App;
import com.neoterux.proyecto2p.utils.Counter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * <h1>PoblationDataController</h1>
 * <p>Controlador de la interfaz de datos adicionales de 
 * la población</p>
 *
 * @author neoterux
 */
public class PoblationDataController implements Initializable, Runnable{
    
    private static final Logger logger = LogManager.getLogger(PoblationDataController.class);
    
    @FXML private Label lblPromAge;

    @FXML private Label lblLifespe;

    @FXML private Label lblIdx;

    @FXML private Label lblDensity;

    @FXML private Label lblCounter;

    /**
     * Envía información a sus respectivos Labels
     * @param data información a enviar.
     */
    public void sendData(String[] data){
        this.lblPromAge.setText(data[10]);
        this.lblLifespe.setText(data[12]);
        this.lblIdx.setText(data[11]);
        this.lblDensity.setText(data[9]);
    }
    
    /**
     * Este método configura el contorlador antes de cagar los objetos de FXML
     * 
     * @param url url
     * @param rb resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Thread(App.appThreadGroup, this, "Timer thread").start();
    }
    
    @FXML
    void exitAction(ActionEvent event) {
        ((Stage)lblCounter.getScene().getWindow()).close();
    }
    
    /**
     * Se encarga de poner en marcha el temporizador.
     */
    @Override
    public void run() {
        
        try {
            var counter = new Counter(6, -1);
            while (counter.getCurrentValue() > 0) {
                Platform.runLater(() -> lblCounter.setText("Ventana cerrando en " + counter.getCurrentValue()+ " segundos"));
                counter.step();
                //noinspection BusyWait
                Thread.sleep(1000);
            }

        }catch (InterruptedException ie){
            logger.info("Thread interrumped.");
        }finally {
            Platform.runLater(() -> ((Stage)lblCounter.getScene().getWindow()).close());
            
        }
        
    }
    
}
