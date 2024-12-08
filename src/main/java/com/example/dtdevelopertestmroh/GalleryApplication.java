package com.example.dtdevelopertestmroh;

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

        BorderPane borderPane = new BorderPane();
        galleryPanel = new GalleryPanel();
        galleryPanel.setPrefTileWidth(80);
        galleryPanel.setPrefTileHeight(80);
        HBox topPanel = new HBox(2);

        TextField searchField = new TextField();
        searchField.setPromptText("Поиск изображений");
        searchField.textProperty().addListener((ov, oldV, newV) -> {
            galleryPanel.filterImages(newV);
        });

        Button loadButton = new Button("Загрузить в галерею");
        loadButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                galleryPanel.saveImage(file);
            }
        });

        Button sortByName = new Button("Сортировать по имени");
        sortByName.setOnAction(event -> galleryPanel.sortByName());

        Button sortByDate = new Button("Сортировать по дате");
        sortByDate.setOnAction(event -> galleryPanel.sortByDate());

        topPanel.getChildren().addAll(searchField, loadButton, sortByName, sortByDate);
        borderPane.setTop(topPanel);
        borderPane.setCenter(galleryPanel);
        borderPane.setBottom(galleryPanel.createPaginationControls());

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}