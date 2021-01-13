package com.neoterux.proyecto2p;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <h1>JavaFX App</h1><p>
 * Proyecto 2do Parcial POO
 * </p>
 * 
 * @author Neoterux
 * @version 0.0.1
 */
public class App extends Application {
    /*
    Images APIs 
    
    iso_code : ex. Ecuador -> ec / EC
    size : depends of api regular is width x height. but also can be only one parameter
            ex. ' 64x64' or '64'
    
    site: https://flagcdn.com/[size{width x height}]/[iso_code].png
    
    
    Images for Countries
    
    site: https://github.com/djaiss/mapsicon
    isocode2: lowercase, 2-digit
    size: availables [16, 32, 48, 64, 80, 96, 128, 256, 1024 ]
    img_link: https://github.com/djaiss/mapsicon/tree/master/all/{isocode2}/{size}.png
    
    */
    
    public static volatile Path FLAGS_PATH = Paths.get("imgs", "flags");
    public static volatile Path COUNTRIES_PATH = Paths.get("imgs", "countries");
    
    
    private static Logger logger = LogManager.getLogger(App.class);
    
    public static ThreadGroup appThreadGroup = new ThreadGroup("Proyect2"); 
    
    private static int WIDTH = 640;
    private static int HEIGHT = 400;

    private static Scene scene;
    public static Stage mainStage;

    @Override
    public void init() throws Exception {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        
        FLAGS_PATH.toFile().mkdirs();
        COUNTRIES_PATH.toFile().mkdirs();
    }
    
    

    @Override
    public void start(Stage stage) {
        logger.info("Starting application");
        /*
            BASE FX APPLICATION
        
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();*/
        try {
            scene = new Scene(loadFXML("ui/main"));
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setMinWidth(WIDTH);
            stage.setMinHeight(HEIGHT);

            stage.show();
        } catch (IOException ex) {
            logger.error(ex, ex.getCause());
        }
        
    }

    @Override
    public void stop() throws Exception {
        logger.info("exitting...");
        
        System.exit(0);//close all posible threads
    }
    
    

    public static void setRoot(String fxml, String title) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        logger.debug("loading fxml at: " + fxml); 
        var fxml_url = App.class.getResource(fxml + ".fxml"); 
        FXMLLoader fxmlLoader = new FXMLLoader(fxml_url);
        logger.debug("fxml url: " + fxml_url);
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
    
    public synchronized static URL resourceFrom(Class from, String relativePath) {
        return from.getResource(relativePath);
//        try { 
//            var res = Paths.get(from.getResource(relativePath).toURI());
//        }catch(URISyntaxException use) {
//            System.out.println("Error when loading resource: " + use.getMessage());
//        }
    }

}