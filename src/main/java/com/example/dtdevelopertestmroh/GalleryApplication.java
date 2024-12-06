package com.example.dtdevelopertestmroh;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GalleryApplication extends Application {
    private GalleryPane galleryPane;
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Галерея изображений");
        BorderPane borderPane = new BorderPane();
        galleryPane = new GalleryPane();
        HBox topPanel = new HBox(2);
        TextField searchField = new TextField();
        searchField.setPromptText("Поиск изображений");
        searchField.textProperty().addListener((ov, oldV, newV) -> {
            galleryPane.filterImages(newV);
        });
        topPanel.getChildren().add(searchField);
        borderPane.setTop(topPanel);
        borderPane.setCenter(galleryPane);
        borderPane.setBottom(galleryPane.createPaginationControls());

        Scene scene = new Scene(borderPane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}