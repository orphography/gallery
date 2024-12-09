package com.example.dtdevelopertestmroh;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class GalleryApplication extends javafx.application.Application {
    private GalleryPanel galleryPanel;
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Галерея изображений");

        galleryPanel = new GalleryPanel();
        galleryPanel.setPrefTileWidth(85);
        galleryPanel.setPrefTileHeight(85);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(new TopPanel(stage, galleryPanel));

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