package com.example.omkar.ciboclient;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ViewDetailedHistoryVH extends RecyclerView.ViewHolder {
    TextView itemname,itemprice,qty,totalprice;


    public ViewDetailedHistoryVH(@NonNull View itemView) {
        super(itemView);
        itemname = itemView.findViewById(R.id.name);
        itemprice = itemView.findViewById(R.id.price);
        qty= itemView.findViewById(R.id.qty);
        totalprice = itemView.findViewById(R.id.totalprice);
    }
}
