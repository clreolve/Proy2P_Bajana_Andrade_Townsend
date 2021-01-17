/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.App;
import com.neoterux.proyecto2p.net.DownloadTask;
import com.neoterux.proyecto2p.utils.LocaleUtils;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * <h1>Main Controller</h1>
 * <p>Controlador de la interfaz principal.</p>
 *
 * @author neoterux
 */
public class MainController extends DownloadTask implements Initializable {

    private static final Logger logger = LogManager.getLogger(MainController.class);

    private volatile DownloadController download;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Thread(App.appThreadGroup, this, "DownloadFlagThread").start();
    }

    /**
     * Cambia la interfaz principal a la interfaz de datos globales.
     * @param event action event
     */
    @FXML
    void globalAction(ActionEvent event) {
        // !-----------------CAMBIAR EL STRING DE APP.SETROOT PARA IR A TU FXML. YAPS done
        App.setRoot("globalData", "Datos globales", 550, 620);
    }

    /**
     * Cambia la interfaz principal a la interfáz de datos locales.
     * @param event action event
     */
    @FXML
    void zoneAction(ActionEvent event){
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

    /**
     * Operación que se realiza durante la descarga
     * @throws IOException si ocurre algún error al escribir las imágenes.
     */
    @Override
    public void onDownload() throws IOException {
        var file = Paths.get(App.class.getResource("res/owid-covid-data_.csv").getFile()).toFile();
        var reader = new BufferedReader(new FileReader(file));
        var country_info = reader.lines().skip(1)
                .map(it -> it.split("[|]"))
                //first: iso_code, second: country_name
                .filter(it -> it[0].length() == 3)
                .map(it -> new Pair<>(LocaleUtils.iso3toIso2(it[0]), it[2].toLowerCase()))
                .distinct()
                .map(ci -> new Pair<>(Paths.get(App.FLAGS_PATH.toString(), ci.getValue() + ".png").toFile(), ci.getKey()))
                .filter(it -> !it.getKey().exists())
                .collect(Collectors.toList());

        reader.close();
        var total = country_info.size();
        if (total > 0) {
            logger.info("Downloading flags");
            Platform.runLater(() -> {
                download = (DownloadController) App.showAndGetController("download_message", 253, 90, false);
                download.setMaxProgress(total);
            });

            while (download == null) {
                Thread.onSpinWait();
                // do nothing, only pass time
            }
            country_info
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
        if(download != null)
            Platform.runLater(() -> download.close());
    }

}
