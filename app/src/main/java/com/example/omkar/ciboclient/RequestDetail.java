package com.example.omkar.ciboclient;

public class RequestDetail {

    String name,datetime,type,totalprice,uid;

    public RequestDetail(String name, String datetime, String type, String totalprice,String uid) {
        this.name = name;
        this.datetime = datetime;
        this.type = type;
        this.totalprice = totalprice;
        this.uid = uid;
    }
    public RequestDetail() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }
}
