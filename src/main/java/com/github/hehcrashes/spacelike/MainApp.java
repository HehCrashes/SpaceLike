package com.github.hehcrashes.spacelike;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("main.fxml")
        );

        Scene scene = new Scene(loader.load(), 1920, 1040);

        stage.setTitle("Elite-style Rotating Dodecahedron");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
