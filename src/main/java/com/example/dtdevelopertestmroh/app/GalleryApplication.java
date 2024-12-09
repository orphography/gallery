package com.example.dtdevelopertestmroh.app;

import com.example.dtdevelopertestmroh.panels.GalleryPanel;
import com.example.dtdevelopertestmroh.panels.TopPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class GalleryApplication extends javafx.application.Application {
    private GalleryPanel galleryPanel;
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Галерея изображений");

        galleryPanel = new GalleryPanel();
        galleryPanel.setPrefTileWidth(85);
        galleryPanel.setPrefTileHeight(85);
        galleryPanel.setStyle("-fx-background-color: #ADD8E6;");
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(new TopPanel(galleryPanel));

        galleryPanel.setOnImagesLoaded(event -> {
            borderPane.setCenter(galleryPanel);
            borderPane.setBottom(galleryPanel.createPaginationControls());
        });
        Scene scene = new Scene(borderPane, 595, 480);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}