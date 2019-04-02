package com.example.omkar.ciboclient;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DineInActivity extends AppCompatActivity {

    DatabaseReference dineinref, call_waiter, dine_in_item_ref;
    private String tableid;
    private String WAITERID;

    FirebaseRecyclerOptions<DineInItemDetails> FROmenuitem;
    FirebaseRecyclerAdapter<DineInItemDetails, DineInItemsVH> FRAmenuitem;
    RecyclerView dinein_RV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dine_in);
        dinein_RV = findViewById(R.id.dineinRV);
        tableid = getIntent().getStringExtra("TableId");
        dineinref = FirebaseDatabase.getInstance().getReference().child("Table").child(tableid);


        dine_in_item_ref = FirebaseDatabase.getInstance().getReference().child("DINE_IN").child(tableid);
        dineinref.child("customer").setValue(FirebaseAuth.getInstance().getUid());
        dineinref.child("status").setValue("UNAVAILABLE");
        getWaiterID();

        dineinref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String stat = (String) dataSnapshot.child("status").getValue();
                if(stat.matches("AVAILABLE") )
                {
                    finish();
                    Toast.makeText(getApplicationContext(),"Session expired",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        FROmenuitem = new FirebaseRecyclerOptions.Builder<DineInItemDetails>().setQuery(dine_in_item_ref, DineInItemDetails.class).build();

        FRAmenuitem = new FirebaseRecyclerAdapter<DineInItemDetails, DineInItemsVH>(FROmenuitem) {
            @Override
            protected void onBindViewHolder(@NonNull final DineInItemsVH holder, final int position, @NonNull final DineInItemDetails model) {

                holder.name.setText(model.getName());
                holder.price.setText(model.getPrice());
                holder.qty.setText(model.getQty());
                holder.total.setText(model.getTotalprice());

            }

            @NonNull
            @Override
            public DineInItemsVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.confirmorder_rowlayout, viewGroup, false);

                return new DineInItemsVH(view);
            }
        };

        LinearLayoutManager linearLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        dinein_RV.setLayoutManager(linearLayout);
        FRAmenuitem.startListening();
        dinein_RV.setAdapter(FRAmenuitem);

    }

    public void callwaiter(View v) {
        if (WAITERID != null) {
            call_waiter = FirebaseDatabase.getInstance().getReference().child("Staff").child(WAITERID).child("call");
            call_waiter.setValue(tableid);
        } else {
            Toast.makeText(getApplicationContext(), "wait till data Loads", Toast.LENGTH_SHORT).show();
        }

    }

    public void getWaiterID() {
        dineinref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                WAITERID = (String) dataSnapshot.child("waiter").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void splitBill(View v) {
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.dinein_alert, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AppFullScreenTheme));
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.editTexttablecustomer);
        TextView usertext = promptsView.findViewById(R.id.textViewtablenumber);
        usertext.setVisibility(View.GONE);
            userInput.setHint("Number Of people");
            userInput.setInputType(InputType.TYPE_CLASS_NUMBER);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                int TOTAL=validation(userInput.getText().toString());
                                finishalert(TOTAL);

                            }

                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public int validation(String text)
    {
        int number = Integer.parseInt(text);
        int TOTAL = 1000/number;
        return TOTAL;
    }

    public void finishalert(int total)
    {
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.dinein_alert, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AppFullScreenTheme));
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTexttablecustomer);
        TextView usertext = promptsView.findViewById(R.id.textViewtablenumber);
        userInput.setVisibility(View.GONE);
        usertext.setText("Your individual bill is "+ String.valueOf(total));
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id)
                            {

                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
