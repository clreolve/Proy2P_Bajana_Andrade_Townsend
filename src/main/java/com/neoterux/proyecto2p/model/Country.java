/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.model;

import com.neoterux.proyecto2p.App;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <h1>Country</h1>
 * Esta clase representa los datos de los paises.
 *
 * @author neoterux
 */
public class Country implements Comparable {

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    
    private static Logger logger = LogManager.getLogger(Country.class);
    private static List<Country> countries;
    
    
    private String name;
    
    public Country (String name){
        this.name = name;
    }
        

    @Override
    public int compareTo(Object t) {
        // Impelementar para ....
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return 0;
    }
    
    
    @Override 
    public String toString() {
        return this.getName();
    }
}
