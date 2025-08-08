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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    MazeHandler mazeHandler;

    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();

        Scene scene = new Scene(root, 640, 640);

        Canvas canvas  = new Canvas(600,600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Label selectWidth = new Label("SelectMaze width");
        Label selectHeight = new Label("SelectMaze height");

        TextField textFieldWidth = new TextField();
        TextField textFieldHeight = new TextField();

        Button submitButton = new Button("Enter");

        Button solveButton = new Button("Solve");

        Label validation = new Label("");

        mazeHandler = new MazeHandler(gc);

        solveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                mazeHandler.solve();
                
            }
        });

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int x = Integer.parseInt(textFieldWidth.getText());
                int y = Integer.parseInt(textFieldHeight.getText());
                //validation.setText("Making a "+ textFieldHeight.getText()+ "x"+ textFieldWidth.getText()+ " grid");
                mazeHandler.createAdjacencyMatrix(x,y);
                //AdjMatrix adj = new AdjMatrix(x,y);
                //MazeGraphics mg = new MazeGraphics();
                mazeHandler.genMaze();
                //MazeGenerator mazeGen = new MazeGenerator(adj);
                //mazeGen.createMaze();
                //mg.drawMaze(gc, mazeGen.createMaze());
                mazeHandler.drawMaze();
                
            }
        });


        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 
        new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.PRIMARY){
                    mazeHandler.setMazeStart(e.getX(), e.getY());
                }
                if (e.getButton() == MouseButton.SECONDARY){
                    mazeHandler.setMazeEnd(e.getX(), e.getY());
                }   

            }
        });
         
      
        //HBox hb = new HBox();
        VBox vb = new VBox();
        vb.getChildren().addAll(selectWidth, textFieldWidth,selectHeight ,textFieldHeight, submitButton, solveButton);
        //hb.getChildren().addAll(selectWidth, textFieldWidth,selectHeight ,textFieldHeight, submitButton);

        
        root.setCenter(canvas);
        root.setRight(vb);
        root.setLeft(validation);

        stage.setScene(scene);


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}