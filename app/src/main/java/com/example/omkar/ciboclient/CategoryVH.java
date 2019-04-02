package com.example.omkar.ciboclient;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryVH extends RecyclerView.ViewHolder {
    TextView type;
    ImageView categoryimage;

    public CategoryVH(@NonNull View itemView) {
        super(itemView);
        type = itemView.findViewById(R.id.typetv);
        categoryimage = itemView.findViewById(R.id.categoryiv);
    }

    public TextView getType() {
        return type;
    }

    public void setType(TextView type) {
        this.type = type;
    }

    public ImageView getCategoryimage() {
        return categoryimage;
    }

    public void setCategoryimage(ImageView categoryimage) {
        this.categoryimage = categoryimage;
    }
}
