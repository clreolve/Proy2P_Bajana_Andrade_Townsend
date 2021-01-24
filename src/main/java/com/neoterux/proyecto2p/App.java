package com.neoterux.proyecto2p;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <h1>JavaFX App</h1><p>
 * Proyecto 2do Parcial POO
 * </p>
 * 
 * @author Neoterux
 * @version 0.5.0
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
    
    /**
     * Objeto que contiene la direción de la carpeta de las banderas
     */
    public static final Path FLAGS_PATH = Paths.get("imgs", "flags");
    
    /**
     * Objeto que contiene la direción de la carpeta de los paises
     */
    @Deprecated
    public static volatile Path COUNTRIES_PATH = Paths.get("imgs", "countries");
    
    /**
     * Objeto que contiene la direción de la carpeta de los datos a utilizar
     */
    public static final Path FILES_PATH = Paths.get("data");
    
    private static final Logger logger = LogManager.getLogger(App.class);
    
    /**
     * Grupo de threads que pertenezcan a esta app.
     */
    public static final ThreadGroup appThreadGroup = new ThreadGroup("Proyect2");
    
    /**
     * Escena principal de la aplicación.
     */
    private static Scene scene;
    /**
     * Stage o ventana principal de la aplicacion.
     */
    public static Stage mainStage;

    /**
     * Crea las carpetas necesarias antes de iniciar la aplicación.
     */
    @Override
    public void init() {
        var x = FLAGS_PATH.toFile().mkdirs() &&
                //COUNTRIES_PATH.toFile().mkdirs() &&
                FILES_PATH.toFile().mkdirs();
        if (x){
            logger.info("Folders creates successfully");
        }
    }
    
    
    /**
     * Punto de partida de la aplicacion.
     * @param stage stage o ventana principal.
     */
    @Override
    public void start(Stage stage) {
        logger.info("Starting application");
        
        //scene = new Scene(loadFXML("ui/main"));
        mainStage = stage;
        setRoot("main", "Ventana principal", 400, 640);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
    
    /**
     * Método que se ejecuta al finalizar el Thread de JavaFX
     */
    @Override
    public void stop() {
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
        }catch (LoadException le){
            logger.error("Hay un error en el fxml, por favor revisa el archivo", le);
        }
        catch (IOException ioe){
            logger.error("IOException ocurred when reading fxml: ", ioe);
        }catch (IllegalStateException ise){
            logger.error("fxml name/location is bad, or cannot locate fxml in ui folder: ", ise);
        }finally{
            mainStage.setTitle(title);
        }
        
    }

    /**
     * Cambia el scene de la ventana objetivo.
     * Ejemplo:<pre>{@code
     *  public class FooController {
     *      // code...
     *      @FXML
     *      private Node nodeInWindow;
     *      //code...
     *
     *      private void doSomething() {
     *          // code
     *          var windowStage = (Stage) nodeInWindow.getScene().getWindow();
     *          App.setRoot(windowStage, "example_fxml", "test window", 600, 800);
     *      }
     *  }
     *
     * }</pre>
     * @param targetWindow ventana objetivo a la cual se le va a cambiar el scene.
     * @param fxml nombre o ubicación relativa del fxml en el paquete ui.
     * @param title nuevo título de la ventana.
     * @param minHeight altura mínima a sobreescribir de la ventana. -1 si se quiere conservar
     *                  el valor actual de la ventana.
     * @param minWidth  ancho mínimo a sobreescribir de la ventana. -1 si se quiere conservar
     *                  el valor actual.
     */
    public static void setRoot(Stage targetWindow, String fxml, String title, int minHeight, int minWidth){
        try {
            var loader = new FXMLLoader(App.class.getResource( "ui/" + fxml + ".fxml"));
            var root  = (Parent)loader.load();
            targetWindow.setScene(new Scene(root, minWidth, minHeight));
        }catch (LoadException le){
            logger.error("Hay un error en el fxml, por favor revisa el archivo", le);
        }catch (IllegalStateException ise){
            logger.error("fxml name/location is bad, or cannot locate fxml in ui folder: ", ise);
        }catch (IOException ioe){
            logger.error("IOException ocurred when reading fxml: ", ioe);
        }finally{
            targetWindow.setTitle(title);
        }
    }
    
    /**
     * Cambia el stage principal, por otra escena a partir de un fxml.
     * 
     * @param fxml dirección relativa del fxml con respecto a la clase App.
     * @param title nuevo título de la ventana.
     * @param minheight altura mínima nueva de la ventana.
     * @param minwidth ancho mínimo nuevo de la ventana
     */
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
    
    /**
     * Obtiene el objeto parent/root del fxml luego de haberlo cargado.
     * 
     * @param fxml dirección del fxml con respecto a la clase App.
     * @return el objeto parent del archivo fxml.
     * @throws IOException si el archivo no existe, u ocurre algun error de I/O al leer el fxml.
     * @throws LoadException si hay error en la sintaxis del fxml.
     */
    public static Parent loadFXML(String fxml) throws IOException, LoadException {
        logger.debug("loading fxml at: " + fxml); 
        var fxml_url = App.class.getResource(fxml + ".fxml"); 
        FXMLLoader fxmlLoader = new FXMLLoader(fxml_url);
        logger.debug("fxml url: " + fxml_url);
        return fxmlLoader.load();
    }
    
    /**
     * Obtiene el cagador fxml de un archivo específico.
     * Inicializar el cargador fxml:
     * {@code 
     * var loader = App.getLoader("path/to/fxml.fxml");
     * loader.load(); // Esto cargará al FXMLLoader
     * }
     * 
     * @param fxml dirección del fxml con respecto a la clase App.
     * @return FXMLoader sin cargar del archivo fxml
     */
    public static FXMLLoader getLoader(String fxml){
        FXMLLoader loader;
        loader = new FXMLLoader(App.class.getResource(fxml));
        //loader.load();// with this initialize the fxmlload

        return loader;
    }
    
    /**
     * Entry point of JVM
     * @param args args from the cli
     */
    public static void main(String[] args) {
        launch();
    }
    
    /**
     * Muestra una nueva ventana.
     * 
     * @param fxml nombre o dirección del fxml relativa desde la carpeta ui.
     * @param minwidth ancho minimo
     * @param minheight alto minimo 
     * @param superpuesto true si la ventana se muestra en el stage principal
     * @return controlador de la nueva ventana.
     */
    public static Object showAndGetController(String fxml, int minwidth, int minheight, boolean superpuesto) {
        
        var loader = new FXMLLoader(App.class.getResource("ui/" + fxml + ".fxml"));
        try{
            var root = (Parent) loader.load();
            if (!superpuesto){
                var stage = new Stage();
                stage.initOwner(mainStage);
                stage.setScene(new Scene(root, minwidth, minheight));
                stage.sizeToScene();
                stage.show();
            }else {
                scene.setRoot(root);
                mainStage.setMinHeight(minheight);
                mainStage.setMinWidth(minwidth);
            }
        }catch (LoadException le){
            logger.error("Error al leer fxml, revisa el archivo", le);
        }
        catch (IOException ioe){
            logger.error("Error al cargar el fxml", ioe);
        }
        
        return loader.getController();
    }
    
    /**
     * Crea una nueva ventana a partir del fxml indicado.Todos estos stages son
     * hijas del stage principal.
     * 
     * @param fxml nombre o dirección del fxml relativa desde la capeta ui.
     * @param minwidth ancho mínimo de la nueva ventana.
     * @param minheight alto mínimo de la nueva ventana
     * 
     * @return el stage de la nueva ventana
     */
    public static Stage newWindow(String fxml, int minwidth, int minheight) {
        var nstage = new Stage();
        try {
            nstage.setScene(new Scene(loadFXML(fxml), minwidth, minheight));
            nstage.setMinWidth(minwidth);
            nstage.setMinHeight(minheight);
            nstage.initOwner(mainStage);
            nstage.initModality(Modality.WINDOW_MODAL);
            return nstage;
        }catch (LoadException le){
            logger.error("Hay un error en el archivo fxml ", le);
            throw new RuntimeException("Error al crear nueva ventana", le);
        }
        catch (IOException ioe){
            logger.error("Error al leer fxml: ", ioe);
            throw new RuntimeException("Error al crear nueva ventana", ioe);
        }
        
    }
    
    /**
     * Obtiene la URL de un recurso específico.
     * 
     * @param from clase que sirve de base(ubicacion) para localizar el recurso.
     * @param relativePath dirección relativa, con respecto a la clase, donde se
     * encuentre el recurso
     * @return URL del recurso especificado.
     */
    public synchronized static URL resourceFrom(Class<?> from, String relativePath) {
        return from.getResource(relativePath);
    }

}