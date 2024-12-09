module com.example.dtdevelopertestmroh {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    exports com.example.dtdevelopertestmroh.app;
    opens com.example.dtdevelopertestmroh.app to javafx.fxml;

    exports com.example.dtdevelopertestmroh.panels;
    opens com.example.dtdevelopertestmroh.panels to javafx.fxml;

    exports com.example.dtdevelopertestmroh.utils;
    opens com.example.dtdevelopertestmroh.utils to javafx.fxml;
}
