package com.example.omkar.ciboclient;

public class Customer {

    private String firstname,lastname,phone,role;

    public Customer(String firstname, String lastname, String phone, String role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }
}
