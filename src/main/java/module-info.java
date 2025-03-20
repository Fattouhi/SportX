module com.example.sportmarket {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    requires org.json;
    requires java.desktop;
    requires mysql.connector.java;
    requires org.jsoup;
    requires org.slf4j;

    opens com.example.sportmarket to javafx.fxml;
    exports com.example.sportmarket.DAO;
    opens com.example.sportmarket.DAO to javafx.fxml;
    exports com.example.sportmarket;

    opens com.example.sportxnews to javafx.fxml;
    exports com.example.sportxnews;
    exports com.example.sportxnews.controller;
    opens com.example.sportxnews.controller to javafx.fxml;
    exports com.example.sportxnews.dao;
    opens com.example.sportxnews.dao to javafx.fxml;
    exports com.example.sportxnews.models;
    opens com.example.sportxnews.models to javafx.fxml;

    // Open the package containing NavbarController to javafx.fxml
    opens com.example.sportx.controller.abir to javafx.fxml;
}