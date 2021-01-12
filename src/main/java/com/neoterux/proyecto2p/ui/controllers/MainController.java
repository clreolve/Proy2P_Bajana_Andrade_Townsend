/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.App;
import com.neoterux.proyecto2p.utils.Counter;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author neoterux
 */
public class MainController implements Initializable{
    
    private static Logger logger = LogManager.getLogger(MainController.class);
    
    private volatile Stage downloadStage;
    private volatile Label infoLabel;
    private ProgressBar progress;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        downloadStage = new Stage();
        var root = new VBox();
        root.setSpacing(20);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);
        root.setFillWidth(true);
        root.setMinWidth(225);
        downloadStage.setMinWidth(225);
        downloadStage.setMinHeight(75);
        downloadStage.setAlwaysOnTop(true);
        infoLabel = new Label("Descargando imágenes --/--");
        
        progress = new ProgressBar();
        root.getChildren().addAll(infoLabel, progress);
        
        downloadStage.setResizable(false);
        downloadStage.setScene(new Scene(root));
        //downloadStage.centerOnScreen();
        downloadStage.initModality(Modality.APPLICATION_MODAL);
        //downloadStage.toFront();
        //downloadStage.show();
       
        startDownloadThread();
    }
    
    @FXML
    void globalAction(ActionEvent event) {
        
        try{
            // !-----------------CAMBIAR EL STRING DE APP.SETROOT PARA IR A TU FXML
            App.setRoot("ui/test_imgs", "");
        } catch(IOException | IllegalStateException ioe) {
            new Alert(Alert.AlertType.ERROR, "Error al cargar FXML, revisa la consola").show();
            logger.error(ioe);
            //ioe.printStackTrace();
        }

    }

    @FXML
    void zoneAction(ActionEvent event) {
        
        try{
            App.setRoot("ui/dashboard", "");
        } catch(IOException | IllegalStateException ioe) {
            logger.error(ioe);
            new Alert(Alert.AlertType.ERROR, "Error al cargar FXML, revisa la consola").show();
            
            //ioe.printStackTrace();
        }

    }
    
    private void startDownloadThread(){
        var downloadImgThread = new Thread(App.appThreadGroup,  () ->{
            //Open covid csv
            
            try {
                var file = Paths.get(App.class.getResource("res/owid-covid-data_.csv").toURI()).toFile();
                
                var reader = new BufferedReader(new FileReader(file)); 
                
                
                var country_info = reader.lines().skip(1)
                                .map(it -> it.split("[|]"))
                                //first: iso_code, second: country_name
                                .filter(it -> it[0].length() == 3)
                                .map(it -> new Pair<>(iso3ToIso2(it[0]), it[2].toLowerCase())) 
                                .distinct()
                                .map(ci -> new Pair<>(Paths.get(App.FLAGS_PATH.toString(), ci.getValue()+ ".png").toFile(), ci.getKey()) )
                                .filter(it -> !it.getKey().exists())
                                .collect(Collectors.toList());
                
           
                reader.close();
                var total = country_info.size();
                if (total > 0){
                    logger.info("Downloading flags");
                    var counter = new Counter(1); 
                    Platform.runLater(() -> {
                        this.downloadStage.show();
                    });
                    
                    country_info
                            .stream()
                            .forEach(ci -> {
                                
                                Platform.runLater(() -> {
                                    infoLabel.setText(String.format("Descargando imágenes %d/%d", counter.getCurrentValue(), total));
                                    this.progress.setProgress(counter.getCurrentValue()/total);
                                });
                                var flag_url = getFlagUrl(ci.getValue());
                                var flagimg = new Image(flag_url);
                                logger.debug("Image URL: " + flag_url + " Image[" + counter.getCurrentValue() +"/" + total+ "] : " + flagimg);
                                saveImage(flagimg, ci.getKey());
                                
                                counter.step();

                            });
                    logger.info("Finish download images");
                }else{
                    System.gc();
                }
                
                
            
            }catch(URISyntaxException | IOException use){
                logger.error(use);
            }catch(NullPointerException npe){
                npe.printStackTrace();
                logger.error(npe, npe.getCause());
                Platform.runLater(()->{
                    new Alert(Alert.AlertType.ERROR, "Error, owid-covid-data_.csv not found.").showAndWait();
                    Platform.exit();
                });
            }finally{
                Platform.runLater(()-> downloadStage.close());
                
            }
        }, "Download thread");
        
        downloadImgThread.start();
    }
    
    
    private synchronized static String getFlagUrl(String iso_code) {
        
        return "https://www.countryflags.io/" + iso_code + "/flat/64.png";
    }
    
    private synchronized static void saveImage(Image target, File imgFile) {
        try{
            
            ImageIO.write(SwingFXUtils.fromFXImage(target, null), "png", imgFile);
        }catch(IOException | IllegalArgumentException ioe){
            logger.error(ioe, ioe.getCause());
            logger.debug(imgFile.toString());
        }
        
    }
    
    private synchronized static String iso3ToIso2(String iso3){
        final var countries = Locale.getISOCountries();
        final var localeMap = new HashMap<String, Locale>(countries.length);
        Arrays.stream(countries).forEach((t) -> {
            var loc = new Locale("", t);
            localeMap.put(loc.getISO3Country().toUpperCase(), loc);
        });     
        //System.out.println(localeMap);
        var y = localeMap.get(iso3);
        if (y==null){
            System.out.println("xd " + iso3);
            return "";
        }
        
        return y.getCountry();
    }
    

    
    
}
