package com.example.omkar.ciboclient;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class DineInItemsVH extends RecyclerView.ViewHolder {
    TextView name,price,qty,total;

    public DineInItemsVH(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.itemnametv);
        price = itemView.findViewById(R.id.itempricetv);
        qty = itemView.findViewById(R.id.qtytv);
        total = itemView.findViewById(R.id.totalitempricetv);


    }
}
