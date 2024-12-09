package com.example.dtdevelopertestmroh;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.File;
import java.util.List;

public class Pagination {
    private final GalleryPanel galleryPanel;
    private final List<File> images;
    private final int imagesOnPage;
    private int currentPage;
    private int numberPage;
    public Pagination(GalleryPanel galleryPanel, List<File> images, int imagesOnPage) {
        this.galleryPanel = galleryPanel;
        this.images = images;
        this.imagesOnPage = imagesOnPage;
        this.currentPage = 0;
        this.numberPage = 1;
    }
    public HBox createPaginationControls() {
        HBox paginationControls = new HBox(10);
        paginationControls.setStyle("-fx-background-color: #ADD8E6;");
        Button previousButton = new Button("Назад");
        Label label = new Label("Стр. " + numberPage);
        label.setStyle("-fx-background-color: #ADD8E6;");
        Button nextButton = new Button("Вперед");

        previousButton.setOnAction(event -> {
            if (currentPage > 0) {
                currentPage--;
                numberPage--;
                label.setText("Стр. " + numberPage);
                updateGallery();
            }
        });

        nextButton.setOnAction(event -> {
            if ((currentPage + 1) * imagesOnPage < images.size()) {
                currentPage++;
                numberPage++;
                label.setText("Стр. " + numberPage);
                updateGallery();
            }
        });

        HBox.setHgrow(previousButton, Priority.ALWAYS);
        HBox.setHgrow(nextButton, Priority.ALWAYS);
        previousButton.setMaxWidth(Double.MAX_VALUE);
        nextButton.setMaxWidth(Double.MAX_VALUE);
        paginationControls.getChildren().addAll(previousButton, label, nextButton);
        return paginationControls;
    }
    public void updateGallery() {
        int startIndex = currentPage * imagesOnPage;
        int endIndex = Math.min(startIndex + imagesOnPage, images.size());
        List<File> subbedList = images.subList(startIndex, endIndex);
        galleryPanel.displayImages(subbedList);
    }
}
