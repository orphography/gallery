package com.example.dtdevelopertestmroh;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

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
            getChildren().add(imageView);
        }
    }
}
