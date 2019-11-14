package com.shahid.medialocker.models;

public class Image {
    public String imagePath;
    public String fileName;

    public Image(String imagePath, String fileName) {
        this.imagePath = imagePath;
        this.fileName = fileName;

    }

    public String fileSize;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }




}
