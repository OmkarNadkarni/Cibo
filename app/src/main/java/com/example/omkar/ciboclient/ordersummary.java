package com.example.omkar.ciboclient;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ordersummary extends AppCompatActivity {
    ListView itemslv;
    TextView totalprice, type, address;
    Button placeorder;
    cartdatabase cartdb;
    Cursor cartcursor;

    DatabaseReference requests,addref,user_data;
    FirebaseAuth mauth;
    String delivery_type, date_time;
    Date date;
    ArrayList<items> ITEMS;
    String CURRENT_ADDRESS;
    DeliveryAddressDetails DAD;
    String username, contact, UID;

    private String TOTAL_PRICE, DELIVERY_TYPE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordersummary);
        itemslv = findViewById(R.id.itemslv);
        totalprice = findViewById(R.id.totalpricetv);
        type = findViewById(R.id.deltypetv);
        address = findViewById(R.id.deladdtv);
        placeorder = findViewById(R.id.placeorderbt);
        ITEMS = new ArrayList<>();              //saving items that are ordered
        cartdb = new cartdatabase(this);
        mauth = FirebaseAuth.getInstance();
        user_data = FirebaseDatabase.getInstance().getReference().child("users").child(mauth.getUid());
        user_data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username = (String) dataSnapshot.child("firstname").getValue();
                username = username+ " " + (String) dataSnapshot.child("lastname").getValue();
                contact = (String) dataSnapshot.child("contact").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        SharedPreferences takeawaypref = getSharedPreferences("TAKEAWAY",MODE_PRIVATE);
        DELIVERY_TYPE = takeawaypref.getString("DELIVERY",null);
        TOTAL_PRICE = takeawaypref.getString("TOTALPRICE","0");


        SharedPreferences userinfo = getSharedPreferences("user", MODE_PRIVATE);
        String username = userinfo.getString("name", null);         //GETTING USERNAME
        String contact = userinfo.getString("contact", null);      //GETTING USER CONTACT INFO
        UID = mauth.getUid();                                   //GETTING UID

        cartcursor = cartdb.showdata();
        ListAdapter myadapter = new OrderViewAdapter(this, cartcursor, 0);
        itemslv.setAdapter(myadapter);                          //SETTING UP LISTVIEW OF ITEMS


        totalprice.setText(TOTAL_PRICE);
        type.setText(DELIVERY_TYPE);


        if(DELIVERY_TYPE.matches("Take Away"))
        {
            address.setVisibility(View.INVISIBLE);
        }
        else
        {
            String from = getIntent().getStringExtra("FROM");

            if(from.matches("NEWADDRESS"))
            {
                CURRENT_ADDRESS = getIntent().getStringExtra("ADDRESS");
                address.setText("Address: "+CURRENT_ADDRESS);
            }
            if(from.matches("ORDERVIEW") )
            {
                getFirebaseAddress();
            }
        }


        totalprice.setText(TOTAL_PRICE);
        type.setText(DELIVERY_TYPE);
        getItems();

    }

    public void getItems() {
        if ( cartcursor.moveToFirst()) {
            do {
                items singleitem = new items(cartcursor.getString(0), cartcursor.getString(1), cartcursor.getString(3), cartcursor.getString(2));
                ITEMS.add(singleitem);

            } while (cartcursor.moveToNext());

        }
    }


    public void getFirebaseAddress()
    {
        addref = FirebaseDatabase.getInstance().getReference().child("delivery").child(UID);

        addref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap: dataSnapshot.getChildren())
                {
                    String Type =(String) snap.child("type").getValue();
                    if(Type.matches("CURRENT"))
                    {
                        CURRENT_ADDRESS = (String) snap.child("address").getValue();
                        address.setText("Address: "+CURRENT_ADDRESS);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




    public void placeordermethod(View v) {


        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        date = new Date();
        date_time = dateFormat.format(date).toString();               //GETTING DATE

        firebasereqdata sendData = new firebasereqdata(UID,username,contact,DELIVERY_TYPE,date_time,address.getText().toString(),TOTAL_PRICE,ITEMS);
        Toast.makeText(getApplicationContext(),username+contact,Toast.LENGTH_SHORT).show();
        requests = FirebaseDatabase.getInstance().getReference().child("requests");
        requests.push().setValue(sendData);

        cartdb.deleteall();

        Toast.makeText(getApplicationContext(),"Order Placed",Toast.LENGTH_LONG).show();
        finish();

    }





}