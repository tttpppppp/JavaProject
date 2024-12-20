module org.example.testgiaodienfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;


    opens org.example.testgiaodienfx to javafx.fxml;
    opens org.example.testgiaodienfx.model to javafx.base;
    exports org.example.testgiaodienfx;
    exports org.example.testgiaodienfx.config;
    opens org.example.testgiaodienfx.config to javafx.fxml;
    exports org.example.testgiaodienfx.controller;
    opens org.example.testgiaodienfx.controller to javafx.fxml;
}