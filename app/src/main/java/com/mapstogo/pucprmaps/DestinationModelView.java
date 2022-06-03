package com.mapstogo.pucprmaps;

public class DestinationModelView {

    private String name;
    private String description;
    private String img;
    private int idImg;

    public DestinationModelView(String name, String description, String img, int id) {
        this.name = name;
        this.description = description;
        this.img = img;
        this.idImg = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getIdImg() {
        return idImg;
    }

    public void setIdImg(int idImg) {
        this.idImg = idImg;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
