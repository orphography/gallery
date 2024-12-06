package com.example.dtdevelopertestmroh;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.File;
import java.util.List;

public class Pagination {
    private final GalleryPane galleryPane;
    private final List<File> images;
    private final int imagesOnPage;
    private int currentPage;
    public Pagination(GalleryPane galleryPane, List<File> images, int imagesOnPage) {
        this.galleryPane = galleryPane;
        this.images = images;
        this.imagesOnPage = imagesOnPage;
        this.currentPage = 0;
    }
    public HBox createPaginationControls() {
        HBox paginationControls = new HBox(1);
        Button previousButton = new Button("Предыдущая");
        Button nextButton = new Button("Следующая");

        previousButton.setOnAction(event -> {
            if (currentPage > 0) {
                currentPage--;
                updateGallery();
            }
        });

        nextButton.setOnAction(event -> {
            if ((currentPage + 1) * imagesOnPage < images.size()) {
                currentPage++;
                updateGallery();
            }
        });
        HBox.setHgrow(previousButton, Priority.ALWAYS);
        HBox.setHgrow(nextButton, Priority.ALWAYS);
        previousButton.setMaxWidth(Double.MAX_VALUE);
        nextButton.setMaxWidth(Double.MAX_VALUE);
        paginationControls.getChildren().addAll(previousButton, nextButton);
        return paginationControls;
    }
    public void updateGallery() {
        int startIndex = currentPage * imagesOnPage;
        int endIndex = Math.min(startIndex + imagesOnPage, images.size());
        List<File> subbedList = images.subList(startIndex, endIndex);
        galleryPane.displayImages(subbedList);
    }
}
