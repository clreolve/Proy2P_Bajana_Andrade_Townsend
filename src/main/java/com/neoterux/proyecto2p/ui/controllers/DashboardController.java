/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.App;
import com.neoterux.proyecto2p.model.Continent;
import com.neoterux.proyecto2p.model.Country;
import com.neoterux.proyecto2p.utils.Counter;
import com.neoterux.proyecto2p.utils.LocaleUtils;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <h1>DahsboardController</h1>
 * <p>Controlador de la interfaz del dashboard</p>
 *
 * @author neoterux
 */
public class DashboardController implements Initializable, Runnable{
    
    private final Logger logger = LogManager.getLogger(getClass());
    
    /**
     * Es el ThreadPool que contendrá a las tareas de búsqueda repetitivas, 
     * corriendo en el fondo.
     */
    private final ExecutorService searchPool;
    private final File dataFile;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String[] lastData;
    
    @FXML private ComboBox<Country> cbxCountry;
    
    @FXML private ImageView countryImg;

    @FXML private ComboBox<Continent> cbxContinent;

    @FXML private DatePicker dpTargetDate;

    @FXML private GridPane dataContainer;

    @FXML private Label lblTotales;

    @FXML private Label lblMuertes;

    @FXML private Label lblDiario;

    @FXML private Label lblMuertesDiario;

    @FXML private Label lblPoblacion;

    @FXML private Label cLabel;
    
    /**
     * Configura el controlador.
     */
    public DashboardController() {
        searchPool = Executors.newFixedThreadPool(1);
        dataFile = Paths.get(App.FILES_PATH.toString(), "owid-covid-data_.csv").toFile();
    }
    
    /**
     * Este método se encarga de configurar o realizar procesos antes de cargar el gui.
     * 
     * @param url url
     * @param rb resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logger.info("Initializing Dashboard Scene");
        
        if (cbxContinent.getItems().setAll(Continent.loadContinents())) {
            logger.debug("Added continents correctly");
        } else {
            logger.debug("Cannot add continents correctly");
        }
        
        var counter = new Counter(0);
        
        var counterThread = new Thread(App.appThreadGroup, ()->{
            
            while(true){
                
                try {
                    //noinspection BusyWait
                    Thread.sleep(1000);
                    Platform.runLater(()->cLabel.setText(String.format("Tiempo en la aplicación: %d segundos", counter.getCurrentValue())));
                    counter.step();
                } catch (InterruptedException ex) {
                    logger.error("Thread interrumped", ex);
                }
            }
        
        }, "Counter Thread");
        
        counterThread.start();
        
    }
    
    /**
     * Función que se ejecuta al presional el boton cerrar.
     * 
     * @param event action event 
     */
    @FXML
    void exitAction(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Método que se realiza al momento de presionar el boton de consultar.
     * @param event action event
     */
    @FXML
    void searchAction(ActionEvent event) {
        if (cbxCountry.getSelectionModel().getSelectedItem() == null || dpTargetDate.getValue() == null ) {
            new Alert(Alert.AlertType.ERROR, "Verifique que ha seleccionado todos los criterios de búsqueda").showAndWait();
        }else {
            if (((ThreadPoolExecutor)searchPool).getActiveCount() == 0){
                searchPool.submit(this);
            }else {
                new Alert(Alert.AlertType.WARNING, "Ya se está realizando una búsqueda, espere porfavor.").showAndWait();
            }
        }
        
    }
    
    /**
     * Método que se ejecuta cuando se clickea el vbox de la poblacion, muestra la 
     * ventana con información extra del país.
     * 
     * @param event mouse event 
     */
    @FXML
    void onPoblationClick(MouseEvent event) {
        logger.debug("Poblation clicked");
        
        var dlgController = App.showAndGetController("poblation_data", 340, 300, false);
        
        ((PoblationDataController)dlgController).sendData(lastData);
        
    }
    
    /**
     * Método que se ejecuta cuando se elige un continente del combobox {@link #cbxContinent}
     * cargando los paises correspondientes en el combobox {@link #cbxCountry}.
     * 
     * @param event action event
     */
    @FXML
    void continentSelected(ActionEvent event) {
        var selected_continent = cbxContinent.getSelectionModel().getSelectedItem();
        
        this.cbxCountry.getItems().setAll(selected_continent.getCountries());
    }
    
    /**
     * Realiza la búsqueda de los infectados, muertos, etc del archivo owid-covid-data_.csv
     */
    @Override
    public void run() {
        var country = cbxCountry.getSelectionModel().getSelectedItem();
        var date = formatter.format(dpTargetDate.getValue());
        var start = System.nanoTime();
        // Read dataset with 10MiB Buffer
        try (var reader = new BufferedReader(new FileReader(dataFile), 10240)){
            var result = reader.lines()
                    .skip(1) // Skip csv header
                    //.parallel()
                    .map(line -> line.split("[|]"))
                    //.parallel()
                    .filter(data -> data[2].equals(country.getName()) && data[3].equals(date))
                    .peek(it -> System.out.println(Arrays.toString(it)))
                    .findFirst()
                    .orElse(null);
            
            Platform.runLater(() -> {
                if (result == null){
                
                    new Alert(Alert.AlertType.INFORMATION, "No se han encontrado datos.").showAndWait();
                
                }else{
                    lastData = result;
                    //this.dataContainer.setVisible(true);
                    this.lblTotales.setText(result[4]);
                    this.lblDiario.setText(result[5]);
                    this.lblMuertes.setText(result[6]);
                    this.lblMuertesDiario.setText(result[7]);
                    this.lblPoblacion.setText(result[8]);
                    var url = getImgUrl(result[0]); 
                    logger.debug("img url: " + url);
                    this.countryImg.setImage(new Image(url));
                    if (!this.dataContainer.isVisible()){
                        var transition = new FadeTransition(Duration.millis(240), dataContainer);
                                transition.setFromValue(0);
                                transition.setToValue(2);
                                transition.setInterpolator(Interpolator.EASE_IN);
                        this.dataContainer.setVisible(true);
                    }
                    
                    
                }
            
            });
            
        }catch (FileNotFoundException fnf){
            logger.error("Covid dataset not found", fnf);
        } catch (IOException ioe){
            logger.error("IOException ocurred when trying to load dataset.", ioe);
        }catch (Exception e){
            logger.error("Unknown exception ocurred when trying to load dataset", e);
        }
        
        var end = System.nanoTime();
        
        logger.debug("Execution time: " + ((end-start)*1e-6) + " [ms]");
       
    }
    
   
    /**
     * Genera el url de la imagen del territorio de un país específico.
     * 
     * @param iso3 codigo iso3 del país objetivo
     * @return url de la imagen del territorio
     */
    private synchronized String getImgUrl(String iso3){
        var isocode2 = LocaleUtils.iso3toIso2(iso3).toLowerCase();
        return "https://raw.githubusercontent.com/djaiss/mapsicon/master/all/" + isocode2+  "/256.png";
    }
    
    
    
}
