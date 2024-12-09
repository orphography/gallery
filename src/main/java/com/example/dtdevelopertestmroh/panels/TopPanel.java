package com.example.dtdevelopertestmroh.panels;

import com.example.dtdevelopertestmroh.panels.GalleryPanel;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import java.io.File;

public class TopPanel extends HBox {
    private boolean isSortedByName = false;
    private boolean isSortedByDate = false;
    public TopPanel(GalleryPanel galleryPanel) {

        TextField searchField = new TextField();
        searchField.setPromptText("Поиск изображений");
        searchField.textProperty().addListener((ov, oldV, newV) -> galleryPanel.filterImages(newV));

        Button loadButton = new Button(" Загрузить в галерею");
        loadButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));
            File file = fileChooser.showOpenDialog(loadButton.getScene().getWindow());
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

        getChildren().addAll(searchField, loadButton, sortByName, sortByDate);
    }
}
