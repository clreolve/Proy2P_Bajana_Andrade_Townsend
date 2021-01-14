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
import javafx.scene.layout.Pane;
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
    public static volatile Path FILES_PATH = Paths.get("data");
    
    
    private static Logger logger = LogManager.getLogger(App.class);
    
    public static ThreadGroup appThreadGroup = new ThreadGroup("Proyect2"); 
    
    private static int WIDTH = 680;
    private static int HEIGHT = 600;

    private static Scene scene;
    public static Stage mainStage;

    @Override
    public void init() throws Exception {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        
        FLAGS_PATH.toFile().mkdirs();
        COUNTRIES_PATH.toFile().mkdirs();
        FILES_PATH.toFile().mkdirs();
    }
    
    

    @Override
    public void start(Stage stage) {
        logger.info("Starting application");
        
        //scene = new Scene(loadFXML("ui/main"));
        mainStage = stage;
        setRoot("main");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setMinWidth(WIDTH);
        stage.setMinHeight(HEIGHT);

        stage.show();
        
        
    }
    
    @Override
    public void stop() throws Exception {
        logger.info("exitting...");
        
        System.exit(0);//close all posible threads
    }
    
    
    /**
     * Coloca en la ventana principal una nueva interfáz a partir del nombre fxml 
     * que se provea.
     * 
     * @param fxml nombre del fxml, o la dirección relativa de un fxml desde la carpeta
     * ui.
     * @param title título que va a tener la ventana principal
     *  
     */
    public static void setRoot(String fxml, String title) {
        try {
        var loader = new FXMLLoader(App.class.getResource( "ui/" + fxml + ".fxml")); 
        var root  = (Parent)loader.load();
        
        if (scene == null) scene = new Scene(root);
        else scene.setRoot(root);
        //var sroot = scene.getRoot();
        
        //logger.debug(String.format("Parent size = height[%f] x widht[%f]", ((Pane)sroot).getHeight(), ((Pane)sroot).getWidth() ));
        
        }catch (IOException ioe){
            logger.error("IOException ocurred when reading fxml: ", ioe);
        }catch (IllegalStateException ise){
            logger.error("fxml name/location is bad, or cannot locate fxml in ui folder: ", ise);
        }finally{
            mainStage.setTitle(title);
        }
        
    }
    
    public static void setRoot(String fxml, String title, int minheight, int minwidth){
        setRoot(fxml, title);
        mainStage.setMinHeight(minheight);
        mainStage.setMinWidth(minwidth);
    }
    
    /**
     * Coloca en la ventana principal una nueva interfáz a partir del nombre fxml 
     * que se provea.
     * 
     * @param fxml nombre del fxml, o la dirección relativa de un fxml desde la carpeta
     * ui.
     * 
     */
    public static void setRoot(String fxml) {
        setRoot(fxml, "");
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