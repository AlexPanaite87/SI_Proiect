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

        //DatabaseTester.runCRUDTests();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 650);

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