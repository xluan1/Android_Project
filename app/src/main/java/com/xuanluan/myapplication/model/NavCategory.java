package com.xuanluan.myapplication.model;

public class NavCategory {
    private String name;
    private String description;
    private String image;
    private String discount;

    public NavCategory(){}

    public NavCategory(String name, String description, String image, String discount) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.discount = discount;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
