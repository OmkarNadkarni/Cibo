package com.example.omkar.ciboclient;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuItemVH extends RecyclerView.ViewHolder {

    TextView itemname,itemprice;
    ImageView itemimage;

    public MenuItemVH(@NonNull View itemView) {
        super(itemView);
        itemname = itemView.findViewById(R.id.itemnametv);
        itemprice = itemView.findViewById(R.id.itempricetv);
        itemimage = itemView.findViewById(R.id.menuitemiv);
    }

    public TextView getItemname() {
        return itemname;
    }

    public void setItemname(TextView itemname) {
        this.itemname = itemname;
    }

    public TextView getItemprice() {
        return itemprice;
    }

    public void setItemprice(TextView itemprice) {
        this.itemprice = itemprice;
    }

    public ImageView getItemimage() {
        return itemimage;
    }

    public void setItemimage(ImageView itemimage) {
        this.itemimage = itemimage;
    }
}
