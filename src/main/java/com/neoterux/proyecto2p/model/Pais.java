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
import java.util.ArrayList;
import javafx.scene.control.Alert;

/**
 *
 * @author danae
 */
public class Pais {

    private String nombre;
    private int casos;
    private int muertes;


    public Pais(String nombre, int casos, int muertes) {
        this.nombre = nombre;
        this.casos = casos;
        this.muertes = muertes;

    }

    @Override
    public String toString() {
        return nombre + " " + casos + " " + muertes;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCasos() {
        return casos;
    }

    public int getMuertes() {
        return muertes;
    }


    public static ArrayList<Pais> cargarPaises() {

        ArrayList<Pais> paises = new ArrayList();

        try (BufferedReader bf = new BufferedReader(new FileReader(App.FILES_PATH+"/globales.csv"))) {
            String linea;
            String nombre;
            int muertes;
            int casos;
            bf.readLine();
            while ((linea = bf.readLine()) != null) {
                    String d[] = linea.split("\\|");
                    if (d.length == 3) {
                        if (d[1] != null) {
                            nombre = d[0];
                            casos = Integer.parseInt(d[1]);
                            muertes = Integer.parseInt(d[2]);
                            paises.add(new Pais(nombre, casos, muertes));
                        } else {
                            nombre = d[0];
                            casos = 0;
                            muertes = Integer.parseInt(d[2]);
                            paises.add(new Pais(nombre, casos, muertes));
                        }
                    } else {
                        nombre = d[0];
                        casos = Integer.parseInt(d[1]);
                        muertes = 0;
                        paises.add(new Pais(nombre, casos, muertes));
                    }
                }
          
        } catch (FileNotFoundException ex) {
            new Alert(Alert.AlertType.WARNING, "Existen problemas para encontrar el archivo globales.csv. Estamos solucionando.").showAndWait();
        } catch (IOException ex) {
            new Alert(Alert.AlertType.WARNING, "Existen problemas de lectura del archivo globales.csv. Estamos solucionando.").showAndWait();
        }

        return paises;

    }

}
