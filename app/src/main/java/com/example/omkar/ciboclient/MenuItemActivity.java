package com.example.omkar.ciboclient;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MenuItemActivity extends AppCompatActivity {

    RecyclerView menuitemrv;
    DatabaseReference menuitemref;
    FirebaseRecyclerOptions<MenuItems> FROmenuitem;
    FirebaseRecyclerAdapter<MenuItems,MenuItemVH> FRAmenuitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);
        Intent i = getIntent();
        String id = i.getStringExtra("categoryid");

        menuitemrv = findViewById(R.id.menuitemrv);
        menuitemref = FirebaseDatabase.getInstance().getReference().child("MenuItems");
        Query query = menuitemref.orderByChild("CategoryId").equalTo(id);
        FROmenuitem = new FirebaseRecyclerOptions.Builder<MenuItems>().setQuery(query,MenuItems.class).build();

        FRAmenuitem = new FirebaseRecyclerAdapter<MenuItems, MenuItemVH>(FROmenuitem) {
            @Override
            protected void onBindViewHolder(@NonNull MenuItemVH holder, int position, @NonNull MenuItems model) {
                Picasso.get().load(model.getImage()).into(holder.itemimage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

                holder.itemname.setText(model.getName());
                holder.itemprice.setText(model.getPrice());




            }

            @NonNull
            @Override
            public MenuItemVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menuitem_item_layout,viewGroup,false);

                return new MenuItemVH(view);
            }
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        menuitemrv.setLayoutManager(gridLayoutManager);
        FRAmenuitem.startListening();
        menuitemrv.setAdapter(FRAmenuitem);
    }
}
