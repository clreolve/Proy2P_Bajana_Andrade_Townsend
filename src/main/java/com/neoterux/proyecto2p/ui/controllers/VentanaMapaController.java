package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.App;
import com.neoterux.proyecto2p.model.Point;
import com.neoterux.proyecto2p.model.shape.Mark;
import com.neoterux.proyecto2p.utils.ThreadMapa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
//Autor: AndradeLuis

public class VentanaMapaController implements Initializable, Serializable {

    File archivo = Paths.get(App.FILES_PATH.toString(), "lugares.txt").toFile();

    FileWriter fw = null;
    BufferedWriter bw = null;
    PrintWriter pw = null;

    private final Image usuario = new Image(App.resourceFrom(App.class, "ui/res/marcador_persona.png").toExternalForm());
    private final Image virus = new Image(App.resourceFrom(App.class, "ui/res/marcador_virus.png").toExternalForm());

    private Point punto_principal;

    private ImageView marcador;

    @FXML
    private StackPane area_interactiva;

    @FXML
    private ImageView mapa;

    @FXML
    private Button btn_registrar;

    @FXML
    private Button btn_cerrar;

    @FXML
    private Label texto;

    public void botonRegistrar(ActionEvent event) {
        if (punto_principal != null) {
            boolean answer = VentanaConfirmacion.display();
            if (answer) {
                texto.setText("Registro realizado exitosamente");
                texto.setStyle("-fx-text-fill:green;");
                RegistroDatos();
                Serializar();
            }
        } else {
            texto.setText("Seleccione primero una ubicación en el mapa");
            texto.setStyle("-fx-text-fill:red;");
        }
    }

    public void botonCerrar() {
        texto.setText("Otro boton Oprimido");
        Stage stage = (Stage) btn_cerrar.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        texto.setText(" ");
        coordenadas();

    }

    public void coordenadas() {

        double x_offset = Math.ceil(area_interactiva.getPrefWidth() / 2) * 0 + 170d;
        double y_offset = Math.floor(area_interactiva.getPrefHeight() / 2) + 15d;
        System.out.println("Area interactiva x: " + area_interactiva.getPrefWidth() + " y: " + area_interactiva.getPrefHeight());

        mapa.setPickOnBounds(true); // Esto permite la deteccion de clicks en la imagen
        mapa.setOnMouseClicked(e -> {
            try {
                area_interactiva.getChildren().remove(1, area_interactiva.getChildren().size());
            } catch (IllegalArgumentException iae) {
                System.out.println("error at removing");
            }

            var c_pos = new Point(e.getX(), e.getY());
            punto_principal = c_pos;
            var user_mark = new Mark("rgb(200,50,0)").getPath();
            user_mark.setTranslateX(c_pos.getX() - x_offset);
            user_mark.setTranslateY(c_pos.getY() - y_offset);
            this.area_interactiva.getChildren().add(user_mark);
            ThreadMapa t = new ThreadMapa(texto, area_interactiva, c_pos, x_offset, y_offset, btn_registrar);
        }
        );
    }

    public void RegistroDatos() {
        try {
            fw = new FileWriter(archivo,true);
            bw = new BufferedWriter(fw);
            bw.append("\n" + punto_principal.getX() + "-" + punto_principal.getY());
            bw.close();
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void Serializar() {
        try {
            FileOutputStream fileOut = new FileOutputStream("data/lugares.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(archivo);
            out.close();
            fileOut.close();
            System.out.println("Archivo guardado");
        } catch (FileNotFoundException ex) {
            System.out.println("Archivo no encontrado");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
