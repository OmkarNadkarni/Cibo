package com.example.omkar.ciboclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TakeAwayOrderView extends AppCompatActivity {

    ListView orderview;
    Button confirm, newadd;
    cartdatabase cartdb;
    Cursor cartcursor;
    TextView totalprice;
    DatabaseReference addref;
    FirebaseAuth mauth;
    SharedPreferences sp;
    String DeliveryAddress;
    int TOTALPRICE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_away_order_view);
        orderview  =findViewById(R.id.vieworderlv);
        confirm  = findViewById(R.id.confirmorderbt);
        totalprice = findViewById(R.id.totalpricetv);
        newadd = findViewById(R.id.newaddbt);
        cartdb = new cartdatabase(this);
         sp = getSharedPreferences("TAKEAWAY",MODE_PRIVATE);
        String type = sp.getString("DELIVERY","self");

        mauth = FirebaseAuth.getInstance();

        resetdata();
        switch (type)
        {
            case "Take Away":
                newadd.setEnabled(false);
                break;
            case "Home Delivery":
                HomeDelivery();
                break;
             default:
                 Toast.makeText(getApplicationContext(),"INVALID",Toast.LENGTH_SHORT).show();
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ordersummaryIntent = new Intent(getApplicationContext(),ordersummary.class);
                ordersummaryIntent.putExtra("FROM","ORDERVIEW");
                if(DeliveryAddress!=null)
                {
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("ADDRESS",DeliveryAddress);
                    ed.putString("TOTALPRICE",String.valueOf(TOTALPRICE));
                    ed.apply();

                    startActivity(ordersummaryIntent);
                    finish();

                }
                else
                {
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("TOTALPRICE",String.valueOf(TOTALPRICE));
                    ed.apply();
                    startActivity(ordersummaryIntent);
                    finish();
                }
            }
        });






    }


    public void addme(View v)
    {
        LinearLayout ll = (LinearLayout) v.getParent();
        TextView qty = (TextView) ll.getChildAt(1);

        LinearLayout pl = (LinearLayout) ll.getParent();

        TextView name = (TextView) pl.getChildAt(0);
        String namestr = name.getText().toString();
        int qtyint = Integer.parseInt(qty.getText().toString());
        cartdb.updateme(namestr,++qtyint);
        resetdata();

    }
    public void subme(View v)
    {
        LinearLayout ll = (LinearLayout) v.getParent();
        TextView qty = (TextView) ll.getChildAt(1);

        LinearLayout pl = (LinearLayout) ll.getParent();

        TextView name = (TextView) pl.getChildAt(0);
        String namestr = name.getText().toString();
        int qtyint = Integer.parseInt(qty.getText().toString());
        if(qtyint==1)
        {
            cartdb.deleteitem(namestr);
            resetdata();

        }
        else
        {
            cartdb.updateme(namestr,--qtyint);
            resetdata();

        }
    }
    public void resetdata()
    {
        cartcursor = cartdb.showdata();
        ListAdapter myadapter = new CartAdapter(this,cartcursor,0);
        orderview.setAdapter(myadapter);
        TOTALPRICE = 0;

        if(cartcursor.moveToFirst())
        {
            do
            {
                int qty = Integer.valueOf(cartcursor.getString(2) );
                int price = Integer.valueOf(cartcursor.getString(3).replace("₹","") );
                TOTALPRICE = TOTALPRICE + (qty*price);
            }while (cartcursor.moveToNext());
            totalprice.setText("TOTAL PRICE: "+String.valueOf(TOTALPRICE));
        }
        else
        {
            totalprice.setText("TOTAL PRICE: "+String.valueOf(TOTALPRICE));
        }


    }

    public void newaddact(View v)
    {
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("TOTALPRICE",totalprice.getText().toString());
        ed.apply();
        startActivity(new Intent(getApplicationContext(),deliveryaddress.class));
        finish();
    }


    public void HomeDelivery()
    {
        String uid = mauth.getUid();
        addref = FirebaseDatabase.getInstance().getReference().child("delivery").child(uid);

        addref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()==0)
                {
                    confirm.setEnabled(false);
                }
                else
                {
                    for (DataSnapshot snap: dataSnapshot.getChildren())
                    {
                        String CurrentAddress = (String)snap.child("type").getValue();
                        if(CurrentAddress.matches("CURRENT"))
                        {
                            DeliveryAddress = (String) snap.child("address").getValue();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
