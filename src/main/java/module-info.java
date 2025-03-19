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

    opens com.example.sportmarket to javafx.fxml;

    exports com.example.sportmarket.DAO;
    opens com.example.sportmarket.DAO to javafx.fxml;
    exports com.example.sportmarket;


}