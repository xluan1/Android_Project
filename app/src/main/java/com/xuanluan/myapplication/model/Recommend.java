package com.xuanluan.myapplication.model;

import java.io.Serializable;

public class Recommend implements Serializable {
    private String name;
    private String description;
    private String rating;
    private String discount;
    private String image;
    private int price;

    public Recommend(){}

    public Recommend(String name, String description, String rating, String discount, String image, int price) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.discount = discount;
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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
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
