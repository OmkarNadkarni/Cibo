package com.example.omkar.ciboclient;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ViewRecordsCardViewVH extends RecyclerView.ViewHolder {

    TextView datetime,customername,type,grandtotal;
    CardView cardView;

    public ViewRecordsCardViewVH(@NonNull View itemView) {
        super(itemView);
        datetime = itemView.findViewById(R.id.VR_datetime);
        type= itemView.findViewById(R.id.VR_type);
        grandtotal = itemView.findViewById(R.id.VR_grandtotal);
        cardView = itemView.findViewById(R.id.viewhistory_CV);
    }
}
