package ru.itis.testwork2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.itis.testwork2.service.ChatService;
import ru.itis.testwork2.util.LoadersUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    private final ChatService chatService = new ChatService();

    @FXML
    TextArea answerTextArea;
    @FXML
    Button answerButton;
    @FXML
    TextField commandTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        commandTextField.setOnAction(e -> {
            try {
                getAnswer(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        answerTextArea.setEditable(false);
    }

    public void getAnswer(ActionEvent event) throws IOException {
        String[] words = commandTextField.getText().split(" ");
        switch (words[0]) {
            case "list":
                answerTextArea.setText(chatService.getCommands());
                break;
            case "weather":
                try {
                    answerTextArea.setText(chatService.getWeather(words[1]));
                } catch (ArrayIndexOutOfBoundsException e) {
                    answerTextArea.setText("Введите город");
                }
                break;
            case "exchange":
                try {
                    answerTextArea.setText(chatService.getExchangeRate(words[1]));
                } catch (ArrayIndexOutOfBoundsException e) {
                    answerTextArea.setText("Введите валюту");
                }
                break;
            case "quit":
                LoadersUtil.loadMainMenu();
                break;
            default:
                answerTextArea.setText("Такой команды нет. Чтобы узнать команды, вы можете ввести команду list.");
                break;
        }
    }
}
