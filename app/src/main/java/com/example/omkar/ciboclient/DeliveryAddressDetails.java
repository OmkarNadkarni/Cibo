package com.example.omkar.ciboclient;

import android.os.Parcel;
import android.os.Parcelable;

public class DeliveryAddressDetails {
    String address,type;

    public DeliveryAddressDetails(String address, String type) {
        this.address = address;
        this.type = type;
    }
    public DeliveryAddressDetails() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
