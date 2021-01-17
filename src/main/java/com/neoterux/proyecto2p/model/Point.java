/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.model;

import com.neoterux.proyecto2p.App;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <h1> Point </h1>
 * <p>Clase que representa un punto 2D </p>
 *
 * @author neoterux
 */
public class Point {
    
    private static Logger logger = LogManager.getLogger(Point.class);

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * <p>
     * Calcula la distancia euclidiana entre 2 puntos. </p>
     *
     * @param p0 punto inicial
     * @param pf punto final
     * @return distancia entre 2 puntos.
     */
    public static double distancia(Point p0, Point pf) {
        return Math.sqrt(Math.pow((pf.x - p0.x), 2) + Math.pow((pf.y - p0.y), 2));
    }
    
    /**
     * Carga los puntos del archivo lugares.txt
     * 
     * @return lista con los puntos parseados del archivo.
     */
    public static List<Point> loadPoints() {
        var plist = new ArrayList<Point>();
        logger.info("reading lugares.txt");
        var file = Paths.get(App.FILES_PATH.toString(), "lugares.txt").toFile();

        try (var reader = new BufferedReader(new FileReader(file))) {

            reader.lines()
                    .map(it -> it.split("-"))
                    .map(par -> new Point(Double.parseDouble(par[0]), Double.parseDouble(par[1])))
                    .forEach(plist::add);
            logger.info("lugares.txt successfully readed");
        }catch (FileNotFoundException fnf){
            logger.error("archivo lugares.txt no se encuentra en la capeta data");
            new Alert(Alert.AlertType.ERROR, "Archivo lugares.txt no se encuentra en la carpeta data.").showAndWait();
        }catch (IOException ioe) {
            logger.error("IOException ocured when trying to read lugares.txt", ioe);
        } 
        return plist;
    }
}
