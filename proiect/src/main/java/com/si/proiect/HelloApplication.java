package com.si.proiect;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        DatabaseManager.initialize();

        DatabaseTester.ruleazaTesteCRUD();

        /*Label label = new Label("Baza de date functioneaza! Verifica Consola IntelliJ.");
        StackPane root = new StackPane(label);*/
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);

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