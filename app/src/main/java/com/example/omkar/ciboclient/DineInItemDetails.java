package com.example.omkar.ciboclient;

public class DineInItemDetails {

    String name,price,qty,totalprice;

    public DineInItemDetails(String name, String price, String qty, String totalprice) {
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.totalprice = totalprice;
    }

    public DineInItemDetails() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }
}
