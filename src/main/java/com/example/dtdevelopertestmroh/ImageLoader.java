package com.example.dtdevelopertestmroh;

import javafx.concurrent.Task;
import javafx.scene.control.Alert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageLoader {
    private final File folder;

    public ImageLoader(String assetsPath) {
        this.folder = new File(assetsPath);
        if (!folder.exists() || !folder.isDirectory()) {
            showErrorDialog("Директория assets отсутствует.");
        }
    }
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public Task<List<File>> loadImagesAsync() {
        return new Task<>() {
            @Override
            protected List<File> call() {
                File[] files = folder.listFiles((dir, name) -> name.endsWith(".jpg") || name.endsWith(".png"));
                List<File> imageFiles = new ArrayList<>();
                if (files != null) {
                    for (File file : files) {
                        imageFiles.add(file);
                    }
                }
                return imageFiles;
            }
        };
    }
}
