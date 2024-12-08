package com.example.dtdevelopertestmroh;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageLoader {
    private final File folder;

    public ImageLoader(String assetsPath) {
        this.folder = new File(assetsPath);
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("Неверный каталог ресурсов: " + assetsPath);
        }
    }

    public final List<File> loadImages() {
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg") || name.endsWith(".PNG"));
        List<File> imageFiles = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                imageFiles.add(file);
            }
        }
        return imageFiles;
    }
}
