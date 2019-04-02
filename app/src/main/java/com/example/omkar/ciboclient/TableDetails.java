package com.example.omkar.ciboclient;

public class TableDetails {

    String status,id;

    public TableDetails(String status, String id) {
        this.status = status;
        this.id = id;
    }
    public TableDetails() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
