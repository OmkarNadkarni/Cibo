package com.example.omkar.ciboclient;

import java.util.ArrayList;

public class firebasereqdata {

    String name,contact,type,datetime,address,totalprice,uid;

    ArrayList<items> itemlist;

    public firebasereqdata(String uid,String name, String contact, String type, String datetime, String address, String totalprice,  ArrayList<items> itemlist) {
        this.name = name;
        this.contact = contact;
        this.type = type;
        this.datetime = datetime;
        this.address = address;
        this.totalprice = totalprice;
        this.uid = uid;
        this.itemlist = itemlist;
    }

    public firebasereqdata()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ArrayList<items> getItemlist() {
        return itemlist;
    }

    public void setItemlist(ArrayList<items> itemlist) {
        this.itemlist = itemlist;
    }
}
