package com.example.omkar.ciboclient;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CartAdapter extends CursorAdapter {
    Context mycontext;
    LayoutInflater layoutInflater;
    Cursor mycursor;
    cartdatabase db;
    TextView nametv,pricetv,qtytv,totaltv;
    int qtyint,priceint,totalint;
    ImageView addiv,subiv;



    public CartAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = LayoutInflater.from(context);
        mycontext = context;
        mycursor = c;
        db = new cartdatabase(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        View view = layoutInflater.inflate(R.layout.orderview_rowlayout,null);
        return view;


    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        nametv = view.findViewById(R.id.ovitemnametv);
        pricetv = view.findViewById(R.id.ovitempricetv);
        qtytv = view.findViewById(R.id.ovqtytv);
        addiv = view.findViewById(R.id.ovaddiv);
        subiv = view.findViewById(R.id.ovsubiv);
        totaltv = view.findViewById(R.id.ovtotalitempricetv);

        //setting values

        nametv.setText(cursor.getString(1));
        qtytv.setText(cursor.getString(2));
        pricetv.setText(cursor.getString(3));
        qtyint = Integer.parseInt(cursor.getString(2));
        priceint = Integer.parseInt(cursor.getString(3).replace("₹",""));
        totalint = qtyint*priceint;
        totaltv.setText(String.valueOf(totalint));


//        addiv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try
//                {
//                    db.updateme(mycursor.getString(0),++qtyint);
//                    mycursor = db.showselected(mycursor.getString(0));
//                    mycursor.moveToFirst();
//                    qtytv.setText(mycursor.getString(mycursor.getColumnIndex("ITEM_QTY")));
//                    totalint = qtyint*priceint;
//                    totaltv.setText(String.valueOf(totalint));
//                }
//                catch (Exception e)
//                {
//                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
//                    Log.d("THOR",String.valueOf(mycursor.getColumnIndex("ITEM_QTY")));
//                }
//
//            }
//        });
//
//        subiv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(qtyint == 1)
//                {
//                    db.deleteitem(mycursor.getString(0));
//
//
//                }
//                else
//                {
//
//                    db.updateme(mycursor.getString(0),--qtyint);
//                    mycursor = db.showselected(mycursor.getString(0));
//                    mycursor.moveToFirst();
//                    qtytv.setText(mycursor.getString(2));
//                    totalint = qtyint*priceint;
//                    totaltv.setText(String.valueOf(totalint));
//
//                }
//            }
//        });


    }

}
