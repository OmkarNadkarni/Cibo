package com.example.omkar.ciboclient;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ViewDetailedHistoryActivity extends AppCompatActivity {

    RecyclerView rv;
    DatabaseReference dinein_ref;
    FirebaseRecyclerOptions<ViewHistoryItemDetails> FROmenuitem;
    FirebaseRecyclerAdapter<ViewHistoryItemDetails,ViewDetailedHistoryVH> FRAmenuitem;
    TextView grandtotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detailed_history);


        grandtotal = findViewById(R.id.grandtotal);
        String datetime = getIntent().getStringExtra("datetime");
        String totalprice = getIntent().getStringExtra("total");
        String orderid = getIntent().getStringExtra("orderid");
        rv = findViewById(R.id.RV_viewrecorditemactivity);
        dinein_ref = FirebaseDatabase.getInstance().getReference().child("requests").child(orderid).child("itemlist");
        Query query = dinein_ref.orderByChild("id");
        grandtotal.setText(totalprice);

        FROmenuitem = new FirebaseRecyclerOptions.Builder<ViewHistoryItemDetails>().setQuery(query,ViewHistoryItemDetails.class).build();

        FRAmenuitem = new FirebaseRecyclerAdapter<ViewHistoryItemDetails, ViewDetailedHistoryVH>(FROmenuitem) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewDetailedHistoryVH holder, final int position, @NonNull final ViewHistoryItemDetails model) {

                holder.itemname.setText(model.getName());
                holder.itemprice.setText(model.getPrice());
                holder.qty.setText(model.getQty());
                holder.totalprice.setText(model.getPrice());

            }

            @NonNull
            @Override
            public ViewDetailedHistoryVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewdetailedhistory_item_layout,viewGroup,false);

                return new ViewDetailedHistoryVH(view);
            }
        };

        LinearLayoutManager linearLayout = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(linearLayout);
        FRAmenuitem.startListening();
        rv.setAdapter(FRAmenuitem);
    }
}
