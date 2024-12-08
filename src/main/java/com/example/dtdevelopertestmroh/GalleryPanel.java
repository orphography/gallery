package com.example.dtdevelopertestmroh;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.List;
import java.util.stream.Collectors;

public class GalleryPanel extends TilePane {
    private static final String PARENT_PATH = "src/main/resources/assets/";
    private List<File> imageFiles;
    private ImageLoader imageLoader;
    private Pagination pagination;
    private int imagesOnPage = 35;
    private boolean isSortedByName = false;
    private boolean isSortedByDate = false;
    public GalleryPanel() {
        imageLoader = new ImageLoader(PARENT_PATH);
        imageFiles = imageLoader.loadImages();
        pagination = new Pagination(this, imageFiles, imagesOnPage);
        pagination.updateGallery();
    }
    public final void displayImages(List<File> images) {
        getChildren().clear();
        for (File file : images) {
            Image image = new Image("file:" + file.getAbsolutePath(), 70, 70, false, true);
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

        Scene scene = new Scene(borderPane, 800, 600);
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
        File dest = new File(PARENT_PATH+file.getName());
        try {
            Files.copy(file.toPath(), dest.toPath());
            imageFiles.add(dest);
            pagination.updateGallery();
        }
        catch (FileAlreadyExistsException e){
            showErrorDialog("Файл с таким именем уже существует.");
        }
        catch (IOException e){
            showErrorDialog(e.getMessage());
        }
    }
    private void deleteImage(File file){
        file.delete();
        imageFiles.remove(file);
        pagination.updateGallery();
    }
    public void sortByName(){
        if(isSortedByName){
            imageFiles.sort((file1, file2) -> file1.getName().compareToIgnoreCase(file2.getName()));
        }
        else
        {
            imageFiles.sort((file1, file2) -> file2.getName().compareToIgnoreCase(file1.getName()));
        }
        isSortedByName = !isSortedByName;
        pagination = new Pagination(this, imageFiles, imagesOnPage);
        pagination.updateGallery();
    }
    public void sortByDate(){
        if(isSortedByDate){
            imageFiles.sort((file1, file2) -> Long.compare(getCreationTimeImage(file1), getCreationTimeImage(file2)));
        }
        else
        {
            imageFiles.sort((file1, file2) -> Long.compare(getCreationTimeImage(file2), getCreationTimeImage(file1)));
        }
        isSortedByDate = !isSortedByDate;
        pagination = new Pagination(this, imageFiles, imagesOnPage);
        pagination.updateGallery();
    }
    private Long getCreationTimeImage(File file){
        try {
            FileTime creationTime = (FileTime) Files.getAttribute(file.toPath(), "creationTime");
            return creationTime.toMillis();
        } catch (IOException ex) {
            showErrorDialog(ex.getMessage());
            return 0L;
        }
    }
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
