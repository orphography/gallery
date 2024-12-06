package com.example.dtdevelopertestmroh;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class GalleryPanel extends TilePane {
    private List<File> imageFiles;
    private ImageLoader imageLoader;
    private Pagination pagination;
    private int imagesOnPage = 10;
    public GalleryPanel() {
        imageLoader = new ImageLoader("src/main/resources/assets");
        imageFiles = imageLoader.loadImages();
        pagination = new Pagination(this, imageFiles, imagesOnPage);
        pagination.updateGallery();
    }

    public final void displayImages(List<File> images) {
        getChildren().clear();
        for (File file : images) {
            Image image = new Image("file:" + file.getAbsolutePath(), 100, 100, false, true);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(image.getWidth());
            imageView.setFitHeight(image.getHeight());
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
        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.setTitle(file.getName());
        stage.show();
    }
    public HBox createPaginationControls() {
        return pagination.createPaginationControls();
    }
    public void filterImages(String query) {
        List<File> filteredImages = imageFiles.stream()
                .filter(file -> file.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
        pagination = new Pagination(this, filteredImages, imagesOnPage);
        pagination.updateGallery();
    }
    public void saveImage(File file) {
        File dest = new File("src/main/resources/assets/"+file.getName());
        try {
            Files.copy(file.toPath(), dest.toPath());
        }
        catch (IOException e){
            throw  new RuntimeException("dafafaf");
        }
    }
}
