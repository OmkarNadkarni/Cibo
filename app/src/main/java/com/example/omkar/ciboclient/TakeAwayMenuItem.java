package com.example.omkar.ciboclient;

import android.content.Intent;
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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class TakeAwayMenuItem extends AppCompatActivity {

    RecyclerView menuitems;
    Button cart;
    DatabaseReference menuitemref;
    FirebaseRecyclerOptions<MenuItems> FROmenuitem;
    FirebaseRecyclerAdapter<MenuItems,TakeAwayVH> FRAmenuitem;
    cartdatabase mydb;
    Cursor cartcursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takeaway_menuitem_layout);
        menuitems = findViewById(R.id.menuitemrv);
        cart = findViewById(R.id.cartbt);

        mydb = new cartdatabase(this);






        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),TakeAwayOrderView.class));

            }
        });
        menuitems = findViewById(R.id.menuitemrv);
        setlayout();
        setbttext();


    }

    @Override
    protected void onResume() {
        setlayout();
        setbttext();
        super.onResume();
    }

    public void setbttext()
    {
        cartcursor= mydb.showdata();
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
        Intent i = getIntent();
        String id = i.getStringExtra("categoryid");
        menuitemref = FirebaseDatabase.getInstance().getReference().child("MenuItems");
        Query query = menuitemref.orderByChild("CategoryId").equalTo(id);
        FROmenuitem = new FirebaseRecyclerOptions.Builder<MenuItems>().setQuery(query,MenuItems.class).build();

        FRAmenuitem = new FirebaseRecyclerAdapter<MenuItems, TakeAwayVH>(FROmenuitem) {
            @Override
            protected void onBindViewHolder(@NonNull final TakeAwayVH holder, int position, @NonNull final MenuItems model) {
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
                final String id = getRef(position).getKey();
                if(mydb.showselected(id).getCount()>0)
                {
                    holder.addtocart.setEnabled(false);
                    holder.addtocart.setText("Added");
                }
                else {
                    holder.addtocart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mydb.additem(id, model.getName(), 1, model.getPrice());
                            setbttext();
                            holder.addtocart.setText("Added");
                            holder.addtocart.setEnabled(false);

                        }
                    });
                }

            }

            @NonNull
            @Override
            public TakeAwayVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_take_away_menu_item,viewGroup,false);

                return new TakeAwayVH(view);
            }
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        menuitems.setLayoutManager(gridLayoutManager);
        FRAmenuitem.startListening();
        menuitems.setAdapter(FRAmenuitem);
    }


}
