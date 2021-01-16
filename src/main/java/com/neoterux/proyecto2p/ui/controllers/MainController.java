/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.App;
import com.neoterux.proyecto2p.net.DownloadTask;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
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
public class MainController extends DownloadTask implements Initializable {

    private static Logger logger = LogManager.getLogger(MainController.class);

    private DownloadController download;
    private Stage downloadStage;
    private final Map<String, Locale> localeMap;

    /**
     * Configura la escena del Main
     *
     */
    public MainController() {
        try {
            var loader = new FXMLLoader(App.class.getResource("ui/download_message.fxml"));
            this.downloadStage = new Stage();
            this.downloadStage.setAlwaysOnTop(true);
            this.downloadStage.initModality(Modality.APPLICATION_MODAL);
            //this.downloadStage.setResizable(false);
            this.downloadStage.setScene(new Scene(loader.load()));
            download = loader.getController();

        } catch (IOException ex) {
            logger.error(ex);
        }
        //Configure locale translations
        final String[] countries = Locale.getISOCountries();
        localeMap = new HashMap<>(countries.length);
        Arrays.stream(countries).forEach((t) -> {
            var loc = new Locale("", t);
            localeMap.put(loc.getISO3Country().toUpperCase(), loc);
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Thread(App.appThreadGroup, this, "DownloadFlagThread").start();

    }

    @FXML
    void globalAction(ActionEvent event) {

        
        // !-----------------CAMBIAR EL STRING DE APP.SETROOT PARA IR A TU FXML. YAPS done
        App.setRoot("globalData", "Datos globales", 550, 620);
        

    }

    @FXML
    void zoneAction(ActionEvent event) throws IOException {

        //Stage stage = new Stage();
        //stage.setScene(new Scene(App.loadFXML("ui/VentanaMapa"), 680, 600));
        //stage.show();
        App.setRoot("VentanaMapa", "Datos locales", 600, 680);

    }

    private synchronized static String getFlagUrl(String iso_code) {

        return "https://www.countryflags.io/" + iso_code + "/flat/64.png";
    }

    private synchronized static void saveImage(Image target, File imgFile) {
        try {

            ImageIO.write(SwingFXUtils.fromFXImage(target, null), "png", imgFile);
        } catch (IOException | IllegalArgumentException ioe) {
            logger.error(ioe, ioe.getCause());
            logger.debug(imgFile.toString());
        }

    }

    private synchronized String iso3ToIso2(String iso3) {
        var y = localeMap.get(iso3);
        if (y == null) {
            //System.out.println("xd " + iso3);
            return "";
        }
        return y.getCountry();
    }
  
    @Override
    public void onDownload() throws IOException {
        var file = Paths.get(App.class.getResource("res/owid-covid-data_.csv").getFile()).toFile();
        var reader = new BufferedReader(new FileReader(file));
        var country_info = reader.lines().skip(1)
                .map(it -> it.split("[|]"))
                //first: iso_code, second: country_name
                .filter(it -> it[0].length() == 3)
                .map(it -> new Pair<>(iso3ToIso2(it[0]), it[2].toLowerCase()))
                .distinct()
                .map(ci -> new Pair<>(Paths.get(App.FLAGS_PATH.toString(), ci.getValue() + ".png").toFile(), ci.getKey()))
                .filter(it -> !it.getKey().exists())
                .collect(Collectors.toList());

        reader.close();
        var total = country_info.size();
        if (total > 0) {
            logger.info("Downloading flags");
            download.setMaxProgress(total);
            Platform.runLater(() -> {
                downloadStage.show();
            });

            country_info
                    .stream()
                    .forEach(ci -> {

                        download.updateProgress();
                        var flag_url = getFlagUrl(ci.getValue());
                        var flagimg = new Image(flag_url);
                        logger.debug("Image URL: " + flag_url + " Image[" + download.getCurrentValue() + "/" + total + "] : " + flagimg);
                        saveImage(flagimg, ci.getKey());

                    });
            logger.info("Finish download images");
        } else {
            System.gc();
        }
    }

    @Override
    public void onError(IOException ex) {
        logger.error(ex);
    }

    @Override
    public void onCompleted() {
        System.gc();
        Platform.runLater(() -> downloadStage.close());
    }

}
