package com.example.dtdevelopertestmroh.utils;

import javafx.concurrent.Task;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ImageManager {
    private final File folder;
    private List<File> images = new ArrayList<>();

    public ImageManager(String assetsPath) {
        folder = new File(assetsPath);
        if (!folder.exists() || !folder.isDirectory()) {
            showErrorDialog("Директория assets отсутствует.");
        }
    }

    public Task<List<File>> loadImagesAsync() {
        return new Task<>() {
            @Override
            protected List<File> call() {
                File[] files = folder.listFiles((dir, name) -> name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg"));
                if (files != null) {
                    images.addAll(Arrays.asList(files));
                }
                return images;
            }
        };
    }
    public List<File> getImages() {
        return images;
    }
    public List<File> filterImages(String query) {
        List<File> filteredImages = images.stream()
                .filter(file -> file.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
        return filteredImages;
    }
    public void saveImage(File file) {
        File dest = new File(folder.toPath()+"/"+file.getName());
        try {
            Files.copy(file.toPath(), dest.toPath());
            images.add(dest);
        }
        catch (FileAlreadyExistsException e){
            showErrorDialog("Файл с таким именем уже существует.");
        }
        catch (IOException e){
            showErrorDialog(e.getMessage());
        }
    }
    public void deleteImage(File file) {
        try{
            file.delete();
            images.remove(file);
        }catch (RuntimeException e){
            showErrorDialog("Не удалось удалить файл: " + e.getMessage());
        }

    }
    public void sortByName(boolean isSortedByName) {
        images.sort((file1, file2) -> isSortedByName ?
                file1.getName().compareToIgnoreCase(file2.getName()) :
                file2.getName().compareToIgnoreCase(file1.getName()));
    }

    public void sortByDate(boolean isSortedByDate) {
        images.sort((file1, file2) -> isSortedByDate ?
                Long.compare(getCreationTimeImage(file1), getCreationTimeImage(file2)) :
                Long.compare(getCreationTimeImage(file2), getCreationTimeImage(file1)));
    }

    private Long getCreationTimeImage(File file) {
        try {
            FileTime creationTime = (FileTime) Files.getAttribute(file.toPath(), "creationTime");
            return creationTime.toMillis();
        } catch (IOException e) {
            showErrorDialog("Ошибка получения времени создания файла: " + e.getMessage());
        }
        return null;
    }
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

