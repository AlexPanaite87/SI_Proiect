package com.si.proiect;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        DatabaseManager.initialize();

        Label label = new Label("Ready");
        StackPane root = new StackPane(label);
        Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("App");
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(event -> {
           DatabaseManager.shutdown();
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}