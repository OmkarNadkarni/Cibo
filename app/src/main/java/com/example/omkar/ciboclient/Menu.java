package com.example.omkar.ciboclient;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {
    RecyclerView categoryrv;
    DatabaseReference categoryref;
    FirebaseRecyclerOptions<Categories> FROcategory;
    FirebaseRecyclerAdapter<Categories,CategoryVH> FRAcategory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        categoryrv = findViewById(R.id.menurv);
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
                        String id = getRef(position).getKey();
                        Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(),MenuItemActivity.class);
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
        categoryrv.setLayoutManager(gridLayoutManager);
        FRAcategory.startListening();
        categoryrv.setAdapter(FRAcategory);




    }
}
