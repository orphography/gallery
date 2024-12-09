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
    private boolean isSortedByName = false;
    private boolean isSortedByDate = false;
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Галерея изображений");

        BorderPane borderPane = new BorderPane();
        galleryPanel = new GalleryPanel();
        galleryPanel.setPrefTileWidth(85);
        galleryPanel.setPrefTileHeight(85);
        HBox topPanel = new HBox(2);

        TextField searchField = new TextField();
        searchField.setPromptText("Поиск изображений");
        searchField.textProperty().addListener((ov, oldV, newV) -> {
            galleryPanel.filterImages(newV);
        });

        Button loadButton = new Button(" Загрузить в галерею");
        loadButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                galleryPanel.saveImage(file);
            }
        });

        Button sortByName = new Button(" Сортировать по имени ↑");
        sortByName.setOnAction(event -> {
            galleryPanel.sortByName(isSortedByName);
            isSortedByName = !isSortedByName;
            sortByName.setText(isSortedByName ? " Сортировать по имени ↓" : " Сортировать по имени ↑");
        });

        Button sortByDate = new Button(" Сортировать по дате ↑");
        sortByDate.setOnAction(event -> {
            galleryPanel.sortByDate(isSortedByDate);
            isSortedByDate = !isSortedByDate;
            sortByDate.setText(isSortedByDate ? " Сортировать по дате ↓" : " Сортировать по дате ↑");
        });

        topPanel.getChildren().addAll(searchField, loadButton, sortByName, sortByDate);
        borderPane.setTop(topPanel);

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