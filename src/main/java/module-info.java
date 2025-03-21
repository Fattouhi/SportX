module com.example.sportmarket {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.sportmarket to javafx.fxml;
    opens com.example.sportx to javafx.graphics;
    opens com.example.sportx.controller.abir to javafx.fxml;

    exports com.example.chatboot to javafx.fxml;
    opens com.example.chatboot to javafx.fxml;

    exports com.example.sportmarket.DAO;
    opens com.example.sportmarket.DAO to javafx.fxml;
    exports com.example.sportmarket;

    opens com.example.demo.controllers to javafx.fxml, javafx.base; // Ouvre le package aux modules nécessaires
    exports com.example.demo; // Exporte le package principal si nécessaire

    requires org.json;
    //requires mysql.connector.java;
    requires org.jsoup;
    requires org.slf4j;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens com.example.sportxnews to javafx.fxml;
    exports com.example.sportxnews;
    exports com.example.sportxnews.controller;
    opens com.example.sportxnews.controller to javafx.fxml;
    exports com.example.sportxnews.dao;
    opens com.example.sportxnews.dao to javafx.fxml;
    exports com.example.sportxnews.models;
    opens com.example.sportxnews.models to javafx.fxml;



    // Open if reflection access is needed
    opens com.example.demo to javafx.fxml;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Added for SQL access

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.demo1 to javafx.fxml;
    exports com.example.demo1;

}