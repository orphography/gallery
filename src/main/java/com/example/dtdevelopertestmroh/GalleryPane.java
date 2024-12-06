package com.example.dtdevelopertestmroh;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class GalleryPane  extends TilePane {
    private List<File> imageFiles;
    private ImageLoader imageLoader;
    public GalleryPane() {
        imageLoader = new ImageLoader("src/main/resources/assets");
        imageFiles = imageLoader.loadImages();
        displayImages(imageFiles);
    }

    public void displayImages(List<File> images) {
        getChildren().clear();
        for (File file : images) {
            Image image = new Image("file:" + file.getAbsolutePath(), 70, 70, false, true);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(70);
            imageView.setFitHeight(70);
            imageView.setPreserveRatio(true);
            imageView.setOnMouseClicked(event -> showImage(file));
            getChildren().add(imageView);
        }
    }
    private void showImage(File file) {
        Stage stage = new Stage();
        Image image = new Image("file:" + file.getAbsolutePath());
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);

        StackPane pane = new StackPane(imageView);
        Scene scene = new Scene(pane, image.getWidth(), image.getHeight());
        stage.setScene(scene);
        stage.setTitle(file.getName());
        stage.show();
    }
}
