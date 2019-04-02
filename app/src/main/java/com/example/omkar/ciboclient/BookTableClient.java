package com.example.omkar.ciboclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookTableClient extends AppCompatActivity {
    Button confirmbt;
    RadioGroup lunchrg, dinnerrg;
    ToggleButton timetb;
    TextView no_of_diners;
    private int DINERS,LIMIT;
    private String TABLE_ID;
    EditText nameOfCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_table_client);
        timetb = findViewById(R.id.timetb);
        confirmbt = findViewById(R.id.booktablebt);
        lunchrg = findViewById(R.id.lunchRG);
        dinnerrg = findViewById(R.id.dinnerRG);
        no_of_diners = findViewById(R.id.dinerstv);
        nameOfCustomer = findViewById(R.id.booktableclient_name_et);
        setTime(null);
         TABLE_ID = getIntent().getStringExtra("tableid");
        if(TABLE_ID.contains("TFT"))
        {
            LIMIT= 2;
            DINERS= 2;
        }
        else
        {
            LIMIT = 4;
            DINERS = 4;
        }
        no_of_diners.setText(String.valueOf(DINERS));
    }

    public void setTime(View v)
    {
        if(timetb.getText().toString().matches("Lunch"))
        {
            lunchrg.setVisibility(View.VISIBLE);
            dinnerrg.setVisibility(View.GONE);
        }
        else
        {
            lunchrg.setVisibility(View.GONE);
            dinnerrg.setVisibility(View.VISIBLE);
        }
    }


    public void addDiner(View v)
    {
        no_of_diners.setText(String.valueOf(++DINERS));
    }
    public void subDiner(View v)
    {
        if(DINERS>LIMIT)
            no_of_diners.setText(String.valueOf(--DINERS));
    }
    public void confirmTable(View v)
    {

        if(lunchrg.getVisibility() == View.VISIBLE)
        {
           int id = lunchrg.getCheckedRadioButtonId();
           RadioButton checked = findViewById(id);
           String time = checked.getText().toString();
           String CustomerName = nameOfCustomer.getText().toString();
            String no_of_diners = String.valueOf(DINERS);
            DatabaseReference book_ref =FirebaseDatabase.getInstance().getReference().child("TABLE_BOOKINGS").child(TABLE_ID).child(time);
            book_ref.child("Customer_name").setValue(CustomerName);
            book_ref.child("diners").setValue(no_of_diners);
        }
        else
        {
            int id = dinnerrg.getCheckedRadioButtonId();
            RadioButton checked = findViewById(id);
            String time = checked.getText().toString();
            String CustomerName = nameOfCustomer.getText().toString();
            String no_of_diners = String.valueOf(DINERS);
            DatabaseReference book_ref =FirebaseDatabase.getInstance().getReference().child("TABLE_BOOKINGS").child(TABLE_ID).child(time);
            book_ref.child("Customer_name").setValue(CustomerName);
            book_ref.child("diners").setValue(no_of_diners);
        }

        Toast.makeText(getApplicationContext(),"Table Booked",Toast.LENGTH_SHORT).show();
        finish();
    }
}
