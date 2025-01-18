package ru.itis.testwork2.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoadersUtil {
    public static final Map<String, FXMLLoader> loaders = new HashMap<>();
    public static final Map<String, Scene> scenes = new HashMap<>();
    @Setter
    public static Stage primaryStage;

    public static void loadFxmlLoaders() throws IOException {
        FXMLLoader loader = new FXMLLoader(LoadersUtil.class.getResource("/ru/itis/testwork2/menu.fxml"));
        loaders.put("menu", loader);
        scenes.put("menu", new Scene(loader.load()));

        loader = new FXMLLoader(LoadersUtil.class.getResource("/ru/itis/testwork2/chat.fxml"));
        loaders.put("chat", loader);
        scenes.put("chat", new Scene(loader.load()));
    }

    public static void loadMainMenu() throws IOException {
        Scene scene = scenes.get("menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void loadChat() {
        Scene scene = scenes.get("chat");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
