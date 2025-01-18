module ru.itis.testwork2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires com.fasterxml.jackson.databind;
    requires org.json;


    opens ru.itis.testwork2 to javafx.fxml;
    exports ru.itis.testwork2;
    exports ru.itis.testwork2.controller;
    opens ru.itis.testwork2.controller to javafx.fxml;
}