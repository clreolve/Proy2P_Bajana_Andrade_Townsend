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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <h1>Continent</h1>
 * Esta clase representa a los datos de los continentes.
 *
 * @author neoterux
 */
public class Continent {
    
    private static List<Continent> continentes;
    private static Logger logger = LogManager.getLogger(Continent.class);
    
    private String name;
    private List<Country> countries;

    public Continent(String name) {
        countries = new ArrayList<>();
        this.name = name;
    }
    
    
    
    
    
    /**
     * Carga los continentes a partir del archivo continentes.txt.
     * 
     * @return lista con los continentes.
     */
    public static List<Continent> loadContinents() {
                
        if (continentes == null){
            var lst = new ArrayList<Continent>();
        
            var file = Paths.get(App.FILES_PATH.toString(), "continentes.txt").toFile();
            
            try (var reader = new BufferedReader(new FileReader(file), 1024)) {
                
                reader.lines().forEach(it -> {
                    lst.add(new Continent(it));
                });
                
                
                
            }catch (IOException ioe) {
                logger.error("Error at reading file continentes.txt ", ioe);
            } catch (Exception e){
                logger.error("Unknow Exception when trying to read continentes.txt", e);
            }
                        
            continentes = lst;
            loadCountries();

        }
        
        return continentes;
    }
    
    
    @Override 
    public String toString(){
        return this.getName();
    }
    
    @Override 
    public boolean equals(Object o){
        if ( o == this ) return true;
        
        if (o instanceof Continent){
            return this.getName().equals(((Continent) o).getName());
        }
        return false;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the countries
     */
    public List<Country> getCountries() {
        return countries;
    }

    
    /**
     * Carga los paises del archivo paises.csv
     */
    private static void loadCountries() {
            var file = Path.of(App.FILES_PATH.toString(), "paises.csv").toFile();
            
            try (var reader = new BufferedReader(new FileReader(file))) {
                
                var continentMap = new HashMap<String,Continent>();
                continentes.forEach(cont->{
                    continentMap.put(cont.getName().toLowerCase(), cont);
                });
                
                reader.lines()
                        .skip(1) // Skip header of .csv file
                        .map(line -> line.split("[|]"))// Split 
                        .forEach(data ->{
                            /*
                            * data[0]: Continent name
                            * data[1]: Country name
                            */
                            var c = continentMap.getOrDefault(data[0].toLowerCase(), null);
                            
                            if (c != null) {
                                c.getCountries().add(new Country(data[1]));
                            }
                            
                        });
                
            }catch (FileNotFoundException fnf) {
                logger.error("File paises.csv not found in folder data", fnf);
            }
            catch(IOException ioe) {
                logger.error("Error when trying to read paises.csv ", ioe);
            }catch (Exception e){
                logger.error("Unknow error when reading paises.csv", e);
            }
    }
    
    
    
    
}
