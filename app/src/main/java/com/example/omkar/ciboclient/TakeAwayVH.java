package com.example.omkar.ciboclient;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TakeAwayVH extends RecyclerView.ViewHolder {

    TextView itemname,itemprice;
    ImageView itemimage;
    Button addtocart;

    public TakeAwayVH(@NonNull View itemView) {
        super(itemView);
        itemname = itemView.findViewById(R.id.itemnametv);
        itemprice = itemView.findViewById(R.id.itempricetv);
        itemimage = itemView.findViewById(R.id.menuitemiv);
        addtocart = itemView.findViewById(R.id.takeawayaddbt);
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

    public Button getAddtocart() {
        return addtocart;
    }

    public void setAddtocart(Button addtocart) {
        this.addtocart = addtocart;
    }
}
