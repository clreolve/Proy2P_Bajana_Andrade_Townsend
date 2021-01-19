package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.App;
import com.neoterux.proyecto2p.model.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
//Autor: AndradeLuis

public class VentanaMapaController implements Initializable, Serializable {

    File archivo = Paths.get(App.FILES_PATH.toString(), "lugares.txt").toFile();
    File archivo_deserealizado;

    FileWriter fw = null;
    BufferedWriter bw = null;
    PrintWriter pw = null;

    @FXML
    private ImageView mapa;

    private final Image imagen = new Image(App.resourceFrom(App.class, "ui/res/mapaguayaquil.png").toExternalForm());
    private Point punto_principal;

    @FXML
    private Button btn_registrar;

    @FXML
    private Button btn_cerrar;

    @FXML
    private Label texto;

    public void botonRegistrar(ActionEvent event) {
        if (punto_principal != null) {
            btn_registrar.setOnAction(e -> {
                boolean answer = VentanaConfirmacion.display();
                if (answer == true) {
                    texto.setText("Registro realizado exitosamente");
                    texto.setStyle("-fx-text-fill:green;");
                    RegistroDatos();
                    Serializar();
         
                }
            });
        } else {
            texto.setText("Seleccione primero una ubicación en el mapa");
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
        mapa.setImage(imagen);
        coordenadas();
    }

    public void coordenadas() {

        mapa.setPickOnBounds(true); // Esto permite la deteccion de clicks en la imagen
        mapa.setOnMouseClicked(e -> {
            System.out.println("[" + e.getX() + " , " + e.getY() + "]");
            punto_principal = new Point(e.getX(), e.getY());
            int counter = 0;

            for (Point points : Point.loadPoints()) {
                if (Point.distancia(punto_principal, points) <= 100) {
                    counter++;
                }
            }
            System.out.println(counter);
            texto.setText("Se han encontrado " + counter + " personas cercanas a tu ubicacion que han registrado positivos");
        }
        );

    }

    public void RegistroDatos() {
        try {
            fw = new FileWriter(archivo, true);
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
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
