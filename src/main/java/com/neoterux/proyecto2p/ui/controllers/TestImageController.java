/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.App;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author neoterux
 */
public class TestImageController implements Initializable, Runnable{
    
    private static Logger logger = LogManager.getLogger(TestImageController.class);
    
    private class Counter {
        private int value;
        private int step;
        
        Counter(int value) {
            this.value = value;
            step = 1;
        }
        
        
        public void next() { this.value += step; }
        
        @Override
        public String toString(){
            return "" + value;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logger.info("starting Image controller");
        
        var loader = new Thread(this, "LoaderThread");
        
        loader.start();
        System.out.println("Hola??");
        
    }
    
    @FXML
    volatile private Label ncountries;

    @FXML
    volatile private Label nflags;

    @FXML
    volatile private FlowPane img_container;

    @Override
    public void run() {
        File file;
        try {
            file = Paths.get(App.resourceFrom(App.class, "res/owid-covid-data_.csv").toURI()).toFile();
            
            try (var reader = new BufferedReader(new FileReader(file))){

                var i = new Counter(0);

                reader.lines().skip(1).map(it -> { 
                            var iso = ""; 
                            var full_iso = it.split("[|]")[0];
                            try {
                                iso = full_iso.substring(0, 2).toLowerCase(); 
                            }catch (StringIndexOutOfBoundsException iob){
                                iso = full_iso;
                            }
                            return iso;
                            })
                        .distinct()
                        .forEach(iso_code ->{
    //                                var iso_code = it.split("[|]")[0].substring(0, 2).toLowerCase(); 
                            var flag_url = "https://www.countryflags.io/" + iso_code + "/flat/64.png";
                            logger.debug("img url: " + flag_url);
                            i.next();
                            Platform.runLater(() -> {
                                var imv = new ImageView(new Image(flag_url));
                                var transition = new FadeTransition(Duration.millis(240), imv);
                                transition.setFromValue(0);
                                transition.setToValue(1);
                                transition.setInterpolator(Interpolator.EASE_IN);
                                img_container.getChildren().add(imv);
                                transition.play();
                                nflags.setText(i +" loaded flags");

                            });
                    try {
                        Thread.currentThread().sleep(400);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                        });
                
                }catch (IOException ioe){ // BufferedReader
                logger.error("Error when reading file: "
                        + ioe.getMessage());
            }
            System.out.println("End loading");
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
                
        

        
    }

    
}
