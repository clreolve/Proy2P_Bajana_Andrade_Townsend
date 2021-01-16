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

/**
 *
 * @author danae
 */
public class Pais {
    
    private String nombre;
    private int casos;
    private int muertes;
    public static int casosTotales;
    public static int muertesTotales;
    
    
    public Pais(String nombre, int casos, int muertes){
        this.nombre = nombre;
        this.casos = casos;
        this.muertes = muertes;
        casosTotales+= casos;
        muertesTotales+=muertes;
    }
    
    @Override
    public String toString(){
        return nombre+" "+casos+" "+muertes;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public int getCasos(){
        return casos;
    }
    
    public int getMuertes(){
        return muertes;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public void setCasos(int casos){
        this.casos = casos;
    }
    
    public void setMuertes(int muertes){
        this.muertes = muertes;
    }
    
    
    
    public static ArrayList<Pais> cargarPaises() {
        
       ArrayList<Pais> paises = new ArrayList();
    
        try (BufferedReader bf = new BufferedReader(new FileReader("globales.csv"))) {
            String linea;
            while ((linea = bf.readLine()) != null) {
                System.out.println(linea);
                String d[] = linea.split("|");
                System.out.println(d);
                paises.add(new Pais(d[0].trim(), Integer.valueOf(d[1].trim()),Integer.valueOf(d[2].trim())));
                
            }

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getStackTrace());
            System.out.println(ex.getLocalizedMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }


        return paises;
        
        
    }
    
}
