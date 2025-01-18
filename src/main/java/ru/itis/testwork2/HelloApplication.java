package ru.itis.testwork2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.itis.testwork2.util.LoadersUtil;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        LoadersUtil.loadFxmlLoaders();
        LoadersUtil.setPrimaryStage(stage);

        LoadersUtil.loadMainMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}