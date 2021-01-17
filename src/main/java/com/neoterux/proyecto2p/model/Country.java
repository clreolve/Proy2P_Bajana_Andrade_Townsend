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
import java.nio.file.Paths;
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
    private int cases;
    private int totalDeaths;
    
    public Country (String name){
        this.name = name;
    }
    
    public Country (String name, int cases, int totalDeaths) {
        this(name);
        this.cases = cases;
        this.totalDeaths = totalDeaths;
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
    
    /**
     * Carga los datos del archivo globales.csv
     * 
     * @return lista con los datos parseados del csv a objeto Country. 
     */
    public static List<Country> get() {
        if (countries == null){
            var con = Continent.loadContinents();
            
            // This is for complete country data 
            var cmap = new HashMap<String, Country>();
            // this is the list with full data of countries
            var lst = new ArrayList<Country>();
            con.stream()
                    .map(it -> it.getCountries())
                    .forEach(it ->{ // this is a list of countries by continent
                        it.forEach(country ->{
                            lst.add(country);
                            cmap.put(country.name, country);
                        });
                    });
            
            // Now lets complete countries data
            var file = Paths.get(App.FILES_PATH.toString(), "globales.csv").toFile();
            // Read with a buffer of 10 MiB
            try(var reader = new BufferedReader(new FileReader(file), 10240)){
                
                reader.lines()
                        .skip(1)
                        .map(it -> it.split("[|]"))
                        .forEach(data ->{
                            /*
                            * data[0]: Country name
                            * data[1]: Total cases
                            * data[2]: Total deaths
                            */
                            // This can cause stackOverflow
                            // but with the max countries number is 196
                            // I think that this doesnt represent high memory usage
                            var c = cmap.get(data[0]);
                            c.cases = Integer.parseInt(data[1]);
                            c.totalDeaths = Integer.parseInt(data[2]);
                            
                        });
                
            }catch (FileNotFoundException fnf){
                logger.error("globales.csv not found in data folder");
            }catch (IOException ioe) {
                logger.error("An IOException ocurred when trying to read globales.csv file ", ioe); 
            }
            catch (Exception e) {
                logger.error("Unknown exception when trying to read globale.csv file", e);
            }
            countries = lst; 
        }
        
        return countries;
    }


    /**
     * @return the cases
     */
    public int getCases() {
        return cases;
    }

    /**
     * @param cases the cases to set
     */
    public void setCases(int cases) {
        this.cases = cases;
    }

    /**
     * @return the totalDeaths
     */
    public int getTotalDeaths() {
        return totalDeaths;
    }

    /**
     * @param totalDeaths the totalDeaths to set
     */
    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }
}
