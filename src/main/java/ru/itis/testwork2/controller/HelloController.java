package ru.itis.testwork2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ru.itis.testwork2.util.LoadersUtil;

import java.io.IOException;

public class HelloController {
    @FXML
    Button beginButton;

    public void beginChat(ActionEvent event) throws IOException {
        LoadersUtil.loadChat();
    }
}