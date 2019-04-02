package com.example.omkar.ciboclient;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class OrderViewAdapter  extends CursorAdapter {
    TextView nametv,pricetv,qtytv,totaltv;
    LayoutInflater layoutInflater;
    int qtyint,priceint,totalint;

    public OrderViewAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.confirmorder_rowlayout,null);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        nametv = view.findViewById(R.id.itemnametv);
        pricetv = view.findViewById(R.id.itempricetv);
        qtytv = view.findViewById(R.id.qtytv);

        totaltv = view.findViewById(R.id.totalitempricetv);

        nametv.setText(cursor.getString(1));
        qtytv.setText(cursor.getString(2));
        pricetv.setText(cursor.getString(3));
        qtyint = Integer.parseInt(cursor.getString(2));
        priceint = Integer.parseInt(cursor.getString(3).replace("₹",""));
        totalint = qtyint*priceint;
        totaltv.setText(String.valueOf(totalint));

    }
}
