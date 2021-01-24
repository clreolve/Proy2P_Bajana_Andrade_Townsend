/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.utils;

import com.neoterux.proyecto2p.model.Point;
import com.neoterux.proyecto2p.model.shape.Mark;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.util.stream.Collectors;

/**
 * Thread que se encarga de llenar el StackPane con marcas.
 *
 * @author luis_
 */
public class ThreadMapa implements Runnable {

    Thread t;
    /**
     * Label que contiene el texto informativo
     */
    Label texto;
    /**
     * Stackpane donde se colocarán las marcas
     */
    StackPane area_interactiva;
    /**
     * Posición del puntero
     */
    Point c_pos;
    /**
     * valor para corregir la posición de las marcas en x
     */
    double x_offset;
    /**
     * valor para corregir la posición de las marcas en y
     */
    double y_offset;
    /**
     * Boton para registrar el punto seleccionado.
     */
    Button btn_registrar;

    /**
     * Crea un nuevo ThreadMapa
     * @param texto Label que contiene el texto informativo
     * @param area_interactiva Stackpane donde se colocarán las marcas
     * @param c_pos Posición del puntero
     * @param x_offset valor para corregir en x
     * @param y_offset valor para corregir en y
     * @param btn_registrar boton para registar punto seleccionado.
     */
    public ThreadMapa(Label texto, StackPane area_interactiva, Point c_pos, double x_offset, double y_offset, Button btn_registrar) {
        this.texto = texto;
        this.area_interactiva = area_interactiva;
        this.c_pos = c_pos;
        this.x_offset = x_offset;
        this.y_offset = y_offset;
        this.btn_registrar = btn_registrar;
        btn_registrar.setDisable(true);
        t = new Thread(this);
        t.start();
    }

    /**
     * Verifica que el thread siga corriendo.
     *
     * @return true si el thread esta corriendo.
     */
    public boolean isActive() {
        return t.isAlive();
    }

    /**
     * fuerza la finalización del thread.
     */
    public void terminate(){
        this.t.interrupt();
    }

    /**
     * Lógica donde se colocan las marcas y se calcula el delay entra cada una
     */
    @Override
    public void run() {
        Counter contador = new Counter(0);
        double rsec = 0;
        do{
            rsec = Math.random() * 10000;
        }while (rsec == 0);
        Platform.runLater(() -> {
            texto.setStyle("-fx-text-fill: rgb(256, 150, 50);" +
                            "-fx-font-size: 13px;");
            texto.setText("Iniciando búsqueda...");
        });
        var matches = Point.loadPoints().stream()
                .filter(point -> Point.distancia(point, c_pos) <= 100)
                .collect(Collectors.toList());
        var sec = rsec / (double)matches.size();
        matches.forEach(point -> {
                    try {
                        Thread.sleep((long) sec);
                        var infected = new Mark("rgb(0,50,200)").getPath();
                        infected.setTranslateX(point.getX() - x_offset);
                        infected.setTranslateY(point.getY() - y_offset);
                        Platform.runLater(() -> {
                            area_interactiva.getChildren().add(infected);
                            contador.step();
                            texto.setText("Se han encontrado " + contador.toString() + " personas cercanas a tu ubicacion que han registrado positivos");

                        });
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                });
        Platform.runLater(() -> {
            btn_registrar.setDisable(false);
        });
    }
}
