package com.mazegen;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

        BorderPane root = new BorderPane();

        Scene scene = new Scene(root, 640, 640);
        root.setTop(l);

        Canvas canvas  = new Canvas(600,600);

        GraphicsContext gc = canvas.getGraphicsContext2D();


        Label selectSize = new Label("SelectMaze Size");

        TextField textField = new TextField();

        Button submitButton = new Button("Enter");
        Label validation = new Label("");


        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int x = Integer.parseInt(textField.getText());
                validation.setText("Making a "+ textField.getText()+ "x"+ textField.getText()+ " grid");
                AdjMatrix adj = new AdjMatrix(x,x);
                MazeGraphics mg = new MazeGraphics();
                mg.drawMaze(gc, adj);
            }
        });

      
        HBox hb = new HBox();

        hb.getChildren().addAll(selectSize, textField, submitButton);

        root.setCenter(canvas);
        root.setRight(hb);
        root.setLeft(validation);

        stage.setScene(scene);


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}