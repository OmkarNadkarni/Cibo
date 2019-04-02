package com.example.omkar.ciboclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class deliveryaddress extends AppCompatActivity {

    EditText flat,landmark,pin,area,saveaset;
    CheckBox saveadd;
    DatabaseReference addressref;
    FirebaseAuth mauth;
    LinearLayout saveasll;

    String DELIVERY_ADDRESS;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveryaddress);
        flat = findViewById(R.id.etflat);
        landmark = findViewById(R.id.etlandmark);
        pin = findViewById(R.id.etpincode);
        area = findViewById(R.id.etarea);
        saveadd = findViewById(R.id.saveaddcb);
        saveasll = findViewById(R.id.saveasll);
        saveaset = findViewById(R.id.saveaset);
        saveasll.setVisibility(View.GONE);


        mauth = FirebaseAuth.getInstance();
        addressref  = FirebaseDatabase.getInstance().getReference().child("delivery").child(mauth.getUid());




    }


    public boolean validation()
    {
        if(flat.getText().toString().trim().isEmpty())
        {
            flat.setError("Please fill in all the details");
            flat.requestFocus();
            return false;
        }
        if(landmark.getText().toString().trim().isEmpty())
        {
            landmark.setError("Please fill in all the details");
            landmark.requestFocus();
            return false;
        }
        if(pin.getText().toString().trim().isEmpty())
        {
            pin.setError("Please fill in all the details");
            pin.requestFocus();
            return false;
        }
        if(area.getText().toString().trim().isEmpty())
        {
            area.setError("Please fill in all the details");
            area.requestFocus();
            return false;
        }

        return true;
    }

    public void checked(View v)
    {
        if(saveadd.isChecked())
        {
            saveasll.setVisibility(View.VISIBLE);
        }
        else
            saveasll.setVisibility(View.GONE);
    }

    public void orderplaced(View v)
    {
        if(validation())
        {
           DELIVERY_ADDRESS = flat.getText().toString() +" "+landmark.getText().toString()+" "+
                   pin.getText().toString()+" "+area.getText().toString();
            if(saveadd.isChecked())
            {
                if(saveaset.getText().toString().isEmpty())
                {
                    saveaset.setError("please fill in all the details");
                    saveaset.requestFocus();
                    return;
                }

                addressref.child(saveaset.getText().toString()).setValue(new DeliveryAddressDetails(DELIVERY_ADDRESS,"CURRENT"));
                Intent orderSummary = new Intent(getApplicationContext(),ordersummary.class);
                orderSummary.putExtra("ADDRESS",DELIVERY_ADDRESS);
                orderSummary.putExtra("FROM","NEWADDRESS");
                startActivity(orderSummary);
                finish();
            }
            else
            {
                Intent orderSummary = new Intent(getApplicationContext(),ordersummary.class);
                orderSummary.putExtra("ADDRESS",DELIVERY_ADDRESS);
                orderSummary.putExtra("FROM","NEWADDRESS");
                startActivity(orderSummary);
                finish();
            }

        }
    }


}
