package com.example.omkar.ciboclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class TakeAway extends AppCompatActivity {

    ToggleButton takeawaytb;
    RecyclerView takeawayrv;
    Button cart;
    DatabaseReference categoryref;
    FirebaseRecyclerOptions<Categories> FROcategory;
    FirebaseRecyclerAdapter<Categories,CategoryVH> FRAcategory;
    cartdatabase mydb;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_away);
        takeawaytb = findViewById(R.id.takeawaytb);
        takeawayrv = findViewById(R.id.takeawayrv);
        cart = findViewById(R.id.takeawaybt);

         mydb = new cartdatabase(this);
        sp = getSharedPreferences("TAKEAWAY",MODE_PRIVATE);


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor ed = sp.edit();
                if(takeawaytb.isChecked())
                {
                    ed.putString("DELIVERY","Take Away");
                    ed.apply();

                }
                else
                    ed.putString("DELIVERY","Home Delivery");
                    ed.apply();
                    startActivity(new Intent(getApplicationContext(),TakeAwayOrderView.class));
            }
        });

        setlayout();
        setcartbt();






    }

    @Override
    protected void onResume() {
        setlayout();
        setcartbt();
        super.onResume();
    }

    public void setcartbt()
    {
        Cursor cartcursor= mydb.showdata();
       int count = cartcursor.getCount();
       if(count==0)
       {
           cart.setText("CART ("+count+")");
           cart.setEnabled(false);
       }
       else
       {
           cart.setEnabled(true);
           cart.setText("CART ("+count+")");
       }
    }

    public void setlayout()
    {
        categoryref = FirebaseDatabase.getInstance().getReference().child("Categories");
        FROcategory = new FirebaseRecyclerOptions.Builder<Categories>().setQuery(categoryref,Categories.class).build();
        FRAcategory = new FirebaseRecyclerAdapter<Categories, CategoryVH>(FROcategory) {
            @Override
            protected void onBindViewHolder(@NonNull final CategoryVH holder, final int position, @NonNull Categories model) {


                Picasso.get().load(model.getImage()).into(holder.categoryimage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

                holder.type.setText(model.getName());
                holder.categoryimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences.Editor ed = sp.edit();

                        if(takeawaytb.isChecked())
                        {
                            ed.putString("DELIVERY","Take Away");
                            ed.apply();

                        }
                        else
                        {
                            ed.putString("DELIVERY","Home Delivery");
                            ed.apply();
                        }

                        String id = getRef(position).getKey();
                        Intent i = new Intent(getApplicationContext(),TakeAwayMenuItem.class);
                        i.putExtra("categoryid",id);
                        startActivity(i);
                    }
                });

            }

            @NonNull
            @Override
            public CategoryVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item_layout,viewGroup,false);

                return new CategoryVH(view);

            }
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        takeawayrv.setLayoutManager(gridLayoutManager);
        FRAcategory.startListening();
        takeawayrv.setAdapter(FRAcategory);
    }



}
