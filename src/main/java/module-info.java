module org.example.projectjava {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;
    requires jbcrypt;
    requires java.desktop;

    opens org.example.projectjava to javafx.fxml;
    exports org.example.projectjava;
    exports org.example.projectjava.controller;
    opens org.example.projectjava.controller to javafx.fxml;
}