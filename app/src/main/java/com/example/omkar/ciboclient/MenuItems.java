package com.example.omkar.ciboclient;

public class MenuItems {

    private String Name,Image,Price,CategoryId;

    public MenuItems(String name, String image, String price, String categoryId) {
        Name = name;
        Image = image;
        Price = price;
        CategoryId = categoryId;
    }
    public MenuItems() {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }
}
