package com.neoterux.proyecto2p.ui.controllers;

import com.neoterux.proyecto2p.App;
import com.neoterux.proyecto2p.model.Point;
import com.neoterux.proyecto2p.model.shape.Mark;
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
    File archivo_deserealizado;

    FileWriter fw = null;
    BufferedWriter bw = null;
    PrintWriter pw = null;

    private final Image imagen = new Image(App.resourceFrom(App.class, "ui/res/mapaguayaquil.png").toExternalForm());
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

    @FXML
    public void mouseClick(MouseEvent evt) {

    }

    public void coordenadas() {
        // image scale wxh-> 1278:599
        // orig: 685 344  / 642 350
        //area_interactiva.setScaleX(1278 / area_interactiva.getWidth());
        //area_interactiva.setScaleY(599 / area_interactiva.getHeight());
        double x_offset = Math.ceil(area_interactiva.getPrefWidth()/2)*0 +170d;
        double y_offset = Math.floor(area_interactiva.getPrefHeight()/2) + 15d;
        System.out.println("Area interactiva x: " + area_interactiva.getPrefWidth() + " y: " + area_interactiva.getPrefHeight());

        mapa.setPickOnBounds(true); // Esto permite la deteccion de clicks en la imagen
        mapa.setOnMouseClicked(e -> {
            try {
                area_interactiva.getChildren().remove(1, area_interactiva.getChildren().size());
            }catch (IllegalArgumentException iae){
                System.out.println("error at removing");
            }

            //area_interactiva.getChildren().remove(area_interactiva.lookup(":not(image-view)"));
            var c_pos = new Point(e.getX(), e.getY());
            //System.out.println("[" + e.getX() + " , " + e.getY() + "]");
            //punto_principal = new Point(e.getX(), e.getY());
            //var p = new Point(e.getX(), e.getY());
            //var img = App.class.getResource("ui/res/marcador_persona.png");
            //System.out.println("Img url: " + img);
            //var x = new ImageView(new Image(img.toString()));
            //x.setPreserveRatio(true);
            //x.setFitHeight(10);
            //var path = new Mark("#eb1a1a");
            // translateX is for
            //path.getPath().setTranslateX(p.getX()-342);
            //path.getPath().setTranslateY(p.getY()-172);
            //x.setX(p.getX() + 5);
            //x.setY(p.getY() + 10);
            //System.out.println("image pos: x:" + path.getPath().getTranslateX() + " y: " + path.getPath().getTranslateY());
            //area_interactiva.getChildren().add(x);
            //area_interactiva.getChildren().add(rg);
            //area_interactiva.getChildren().add(path.getPath());
            //marcador.setImage(usuario);
            //marcador.setVisible(true);
            /*
            marcador = new ImageView(usuario);
            marcador.setFitHeight(5);
            marcador.setFitWidth(5);
            marcador.setLayoutX(e.getX());
            marcador.setLayoutY(e.getY());
             */
            var user_mark  = new Mark("rgb(200,50,0)").getPath();
            user_mark.setTranslateX(c_pos.getX() - x_offset);
            user_mark.setTranslateY(c_pos.getY() - y_offset);
            this.area_interactiva.getChildren().add(user_mark);
            var matches = Point.loadPoints().stream()
                    .filter(point -> Point.distancia(point, c_pos) <= 100)
                    .peek(point -> {
                        var infected = new Mark("rgb(0,50,200)").getPath();
                        infected.setTranslateX( point.getX() - x_offset);
                        infected.setTranslateY(point.getY() - y_offset);
                        area_interactiva.getChildren().add(infected);
                    }).count();
            //int counter = 0;
            //for (Point points : Point.loadPoints()) {
            //    if (Point.distancia(punto_principal, points) <= 100) {
            //        counter++;
            //    }
            //}
            //System.out.println(counter);
            //texto.setText("Se han encontrado " + counter + " personas cercanas a tu ubicacion que han registrado positivos");
            texto.setText("Se han encontrado " + matches + " personas cercanas a tu ubicacion que han registrado positivos");
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
