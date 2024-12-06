package com.example.dtdevelopertestmroh;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GalleryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Галерея изображений!");
        TilePane tilePane = new TilePane();
        ImageLoader imageLoader = new ImageLoader("src/main/resources/assets");
        List<File> fileList = imageLoader.loadImages();
        if(fileList!= null){
            for(File file : fileList){
                Image image = new Image("file:"+file.getAbsolutePath(), 100, 100, false, true);
                ImageView imageView = new ImageView(image);
                imageView.setPreserveRatio(true);
                tilePane.getChildren().add(imageView);
            }
        }
        Scene scene = new Scene(tilePane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}