package com.xuanluan.myapplication.model;

import java.io.Serializable;

public class ViewAll implements Serializable {
    private String name;
    private String description;
    private String rating;
    private String type;
    private String image;
    private int price;

    public ViewAll() {
    }

    public ViewAll(String name, String description, String rating, String type, String image, int price) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.type = type;
        this.image = image;
        this.price = price;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
