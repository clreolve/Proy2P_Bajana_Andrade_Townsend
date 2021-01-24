/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.ui.controllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author AndradeLuis
 */
public class VentanaConfirmacion {

    static boolean answer;

    public static boolean display() {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("VentanaConfirmación");
        window.setMinWidth(506.0);
        window.setMaxWidth(506.0);
        window.setMinHeight(307.0);
        window.setMaxHeight(307.0);

        Label titulo = new Label();
        titulo.setText("Confirmación");
        titulo.setFont(Font.font("System Bold", FontWeight.BOLD, FontPosture.REGULAR, 32));
        titulo.setStyle("-fx-text-fill: #0018b2;");

        Label texto = new Label();

        texto.setText("Si en este momento tienes un diagnóstico de COVID 19 positivo, registra tu ubicación y así otras personas podrán identificar si has estado en la misma zona y podrán tomar las medidas adecuadas como aislamiento voluntario. No se registraran tus datos personales.");
        texto.setTextAlignment(TextAlignment.JUSTIFY);
        texto.setWrapText(true);

        Label aviso = new Label();
        aviso.setText("¿Deseas registrar tu ubicación?");
        aviso.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 13));
        Button registrar = new Button("Registrar");
        registrar.setStyle("-fx-background-color:blue;-fx-text-fill:white;");
        registrar.setOnAction(e -> {
            answer = true;
            window.close();
        });
        Button closeButton = new Button("Cancelar");
        closeButton.setStyle("-fx-background-color:blue;-fx-text-fill:white;");
        closeButton.setOnAction(e -> {
            answer = false;
            window.close();
        }
        );

        HBox layout_btn = new HBox(10);
        layout_btn.getChildren().addAll(registrar, closeButton);
        layout_btn.setAlignment(Pos.CENTER);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(titulo, texto, aviso, layout_btn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(0, 10, 0, 10));
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
