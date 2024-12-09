package com.example.dtdevelopertestmroh.panels;

import com.example.dtdevelopertestmroh.utils.ImageManager;
import com.example.dtdevelopertestmroh.utils.Pagination;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class GalleryPanel extends TilePane {
    private ImageManager imageManager;
    private Pagination pagination;
    private static final int IMAGES_ON_PAGE = 35;
    private static final int THUMBNAIL_WIDTH = 65;
    private static final int THUMBNAIL_HEIGHT = 65;
    private EventHandler<Event> onImagesLoaded;
    private static final String PARENT_PATH = "src/main/resources/assets/";

    public GalleryPanel() {
        imageManager = new ImageManager(PARENT_PATH);
        loadImages();
    }
    private void loadImages(){
        Task<List<File>> loadTask = imageManager.loadImagesAsync();
        loadTask.setOnSucceeded(event -> {
            List<File> img = imageManager.getImages();
            pagination = new Pagination(this, img, IMAGES_ON_PAGE);
            pagination.updateGallery();
            if(onImagesLoaded !=null){
                onImagesLoaded.handle(null);
            }
        });

        loadTask.setOnFailed(event -> {
            Platform.runLater(() -> {
                System.err.println("Ошибка загрузки" + loadTask.getException());
            });
        });

        new Thread(loadTask).start();
    }
    public void setOnImagesLoaded(EventHandler<Event> onImagesLoaded){
        this.onImagesLoaded = onImagesLoaded;
    }
    public final void displayImages(List<File> images) {
        if(images == null) return;
        getChildren().clear();
        for (File file : images) {
            Image image = new Image("file:" + file.getAbsolutePath(), THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, false, true, true);
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

        imageView.fitWidthProperty().bind(stage.widthProperty());
        imageView.fitHeightProperty().bind(stage.heightProperty().subtract(100));

        Button deleteButton = new Button("Удалить из галереи");
        deleteButton.setOnAction(event -> {
            stage.close();
            deleteImage(file);
        });
        deleteButton.setMaxWidth(Double.MAX_VALUE);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(imageView);
        borderPane.setBottom(deleteButton);
        BorderPane.setAlignment(deleteButton, Pos.CENTER);

        Scene scene = new Scene(borderPane, 800, 500);
        stage.setScene(scene);
        stage.setTitle(file.getName());
        stage.show();
    }

    public HBox createPaginationControls() {
        return pagination.createPaginationControls();
    }
    public void filterImages(String query) {
        List<File> filteredImages = imageManager.filterImages(query);
        pagination = new Pagination(this, filteredImages, IMAGES_ON_PAGE);
        pagination.updateGallery();
    }
    public void saveImage(File file) {
        imageManager.saveImage(file);
        pagination.updateGallery();
    }
    private void deleteImage(File file){
        imageManager.deleteImage(file);
        pagination.updateGallery();
    }
    public void sortByName(boolean isSortedByName){
        imageManager.sortByName(isSortedByName);
        pagination.updateGallery();
    }
    public void sortByDate(boolean isSortedByDate){
        imageManager.sortByDate(isSortedByDate);
        pagination.updateGallery();
    }
}
