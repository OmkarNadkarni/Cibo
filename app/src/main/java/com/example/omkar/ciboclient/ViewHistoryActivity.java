package com.example.omkar.ciboclient;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ViewHistoryActivity extends AppCompatActivity {

    RecyclerView viewhistory;
    FirebaseRecyclerOptions<firebasereqdata> FROmenuitem;
    FirebaseRecyclerAdapter<firebasereqdata,ViewRecordsCardViewVH> FRAmenuitem;
    FirebaseAuth user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);
        viewhistory = findViewById(R.id.viewhistory_RV);
        user = FirebaseAuth.getInstance();
        final String UID = user.getUid();

        Query query = FirebaseDatabase.getInstance().getReference().child("requests").orderByChild("uid").equalTo(UID);

        FROmenuitem = new FirebaseRecyclerOptions.Builder<firebasereqdata>().setQuery(query,firebasereqdata.class).build();

        FRAmenuitem = new FirebaseRecyclerAdapter<firebasereqdata, ViewRecordsCardViewVH>(FROmenuitem) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewRecordsCardViewVH holder, final int position, @NonNull final firebasereqdata model) {

                holder.datetime.setText(model.getDatetime());
                holder.type.setText(model.getType());
                holder.grandtotal.setText(model.getTotalprice());

                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getApplicationContext(),ViewDetailedHistoryActivity.class);
                        String orderid = getRef(position).getKey();
                        String datetime = model.getDatetime();
                        String total = model.getTotalprice();
                        i.putExtra("datetime",datetime);
                        i.putExtra("total",total);
                        i.putExtra("orderid",orderid);
                        startActivity(i);
                    }
                });



            }

            @NonNull
            @Override
            public ViewRecordsCardViewVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewhistory_cardview_layout,viewGroup,false);

                return new ViewRecordsCardViewVH(view);
            }
        };

        LinearLayoutManager linearLayout = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        viewhistory.setLayoutManager(linearLayout);
        FRAmenuitem.startListening();
        viewhistory.setAdapter(FRAmenuitem);
    }
}
