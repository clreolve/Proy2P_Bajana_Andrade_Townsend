/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Clase que permite realizar operaciones con Locales, isocode, etc.
 *
 * @author neoterux
 */
public class LocaleUtils {
    private LocaleUtils(){}
    
    private static class Iso2NotFoundException extends RuntimeException{

        public Iso2NotFoundException(String message, Throwable throwable) {
            super(message, throwable);
        }
    }
    
    private static final Map<String, Locale> LOCALEMAP;
    
    static {
        //init localemap when classloader reference this class.
        String[] countries = Locale.getISOCountries();
        LOCALEMAP = new HashMap<>(countries.length);
        Arrays.stream(countries).forEach((t) -> {
            var loc = new Locale("", t);
            LOCALEMAP.put(loc.getISO3Country().toUpperCase(), loc);
        });
        
    }   
    
    
    /**
     * Transforma un string que contenga un codigo iso3 de un país y lo transforma 
     * a su respectivo codigo ISO2.
     * 
     * @param iso3 codigo ISO3.
     * @return codigo ISO2
     * 
     * @throws Iso2NotFoundException si el iso3 no es válido.
     */
    public static String iso3toIso2(String iso3){
        try {
            return LOCALEMAP.get(iso3.toUpperCase()).getCountry();
        }catch (NullPointerException npe){
            throw new Iso2NotFoundException("ISO3 " + iso3 + " isn't a valid iso3", npe);
            
        }
    }
    
}
