package com.example.omkar.ciboclient;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TableVH extends RecyclerView.ViewHolder {

    ImageView tableimg;
    TextView status;


    public TableVH(@NonNull View itemView) {
        super(itemView);
        tableimg= itemView.findViewById(R.id.tableiv);
        status = itemView.findViewById(R.id.tablestatustv);
    }

    public ImageView getTableimg() {
        return tableimg;
    }

    public void setTableimg(ImageView tableimg) {
        this.tableimg = tableimg;
    }

    public TextView getStatus() {
        return status;
    }

    public void setStatus(TextView status) {
        this.status = status;
    }

    public boolean isavailable(String stat)
    {
        if(stat.matches("AVAILABLE"))
            return true;
        return false;
    }
}


