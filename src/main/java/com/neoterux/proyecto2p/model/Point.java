/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.model;

import com.neoterux.proyecto2p.App;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author neoterux
 */
public class Point {
    
    private double x;
    private double y;
    
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    
    
    public double getX() { return this.x; }
    
    public double getY() { return this.y; }
    
    
    public void setX(double x) { this.x = x; }
    
    public void setY(double y) { this.y = y; }
    
    /**
     * <p>Calcula la distancia euclidiana entre 2 puntos. </p>
     * 
     * @param p0 punto inicial
     * @param pf punto final
     * @return distancia entre 2 puntos.
     */
    public static double distancia(Point p0, Point pf) {
        return Math.sqrt(Math.pow((pf.x - p0.x), 2) + Math.pow((pf.y - p0.y), 2)); 
    }
    
    public static List<Point> loadPoints() {
        var plist = new ArrayList<Point>();
        File file;
        try {
            file = Paths.get(App.class.getResource("res/lugares.txt").toURI()).toFile();
            
            
            try (var reader = new BufferedReader(new FileReader(file))){

                reader.lines()
                        .map(it-> it.split("-"))
                        .map(par-> new Point(Double.parseDouble(par[0]), Double.parseDouble(par[1])))
                        .forEach(plist::add);
            }catch(IOException ioe){
                System.out.println("Error when loading lugares.txt file: " + ioe.getMessage());
            }
        } catch (URISyntaxException ex) {
            ex.getMessage();
        }
        return plist;
    }
}
