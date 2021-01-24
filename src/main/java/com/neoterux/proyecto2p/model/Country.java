/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.model;

import com.neoterux.proyecto2p.App;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * <h1>Country</h1>
 * Esta clase representa los datos de los paises.
 *
 * @author neoterux
 */
public class Country implements Comparable<Country> {


    
    
    private static final Logger logger = LogManager.getLogger(Country.class);
    /**
     * lista de los paises cargados desde los archivos
     */
    private static List<Country> countries;
    /**
     * Orden de búsqueda para el método {@link #compareTo(Country), por defecto tiene el valor {@Value CASOS}
     */
    private static OrdenBusqueda orden = OrdenBusqueda.CASOS;
    
    private final String name;
    private int cases;
    private int totalDeaths;

    /**
     * Crea un nuevo objeto Country únicamente con su nombre.
     *
     * @param name nombre del país
     */
    public Country (String name){
        this.name = name;
    }

    /**
     * Crea un nuevo objeto Country con todos los datos del país.
     *
     * @param name nombre del pais
     * @param cases total de casos del pais
     * @param totalDeaths total de muertes del pais
     */
    public Country (String name, int cases, int totalDeaths) {
        this(name);
        this.cases = cases;
        this.totalDeaths = totalDeaths;
    }

    /**
     * Campara con el pais objetivo el total de muertes o total de casos, según se especifique
     * con el objeto {@link Country#orden}.
     *
     * @param t país objetivo
     * @return -1 si el valor del objetivo es menor al objeto actual,
     * 0 si el valor es igual al del objeto actual,
     * 1 si el valor es mayor al del objeto actual.
     */
    @Override
    public int compareTo(Country t) {
        switch (orden){
            case CASOS:
                if (this.cases == t.cases){
                    return 0;
                }else if (this.cases < t.cases){
                    return 1;
                }
                return -1;

            case MUERTES:
                if (this.totalDeaths == t.totalDeaths){
                    return 0;
                }else if (this.totalDeaths < t.totalDeaths){
                    return 1;
                }
                return -1;
            default:
                return -2;
        }
    }

    /**
     * Coloca el valor de de orden de búsqueda para el {@link #compareTo(Country)}.
     *
     * @param ordenBusqueda nuevo orden de búsqueda a realizar
     */
    public static void setOrder(OrdenBusqueda ordenBusqueda){
        orden = ordenBusqueda;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
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
                    .map(Continent::getCountries)
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

                            var c = cmap.get(data[0]);
                            if (c != null ) {
                                c.cases = Integer.parseInt(data[1]);
                                if (data.length < 3) {
                                    c.totalDeaths = 0;
                                } else {
                                    c.totalDeaths = Integer.parseInt(data[2]);
                                }
                            }
                        });
                
            }catch (FileNotFoundException fnf){
                logger.error("globales.csv not found in data folder");
                new Alert(Alert.AlertType.ERROR, "Archivo globales.csv no se encuentra en la carpeta data, cerrando.").showAndWait();
                Platform.exit();
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
