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

public class BookTable extends AppCompatActivity {

    DatabaseReference tableref;
    FirebaseRecyclerOptions<TableDetails> FROtable;
    FirebaseRecyclerAdapter<TableDetails,TableVH> FRAtable;
    RecyclerView tablerv;
    boolean TB = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_table);
        tablerv = findViewById(R.id.tablerv);

        tableref = FirebaseDatabase.getInstance().getReference().child("Table");
        FROtable = new FirebaseRecyclerOptions.Builder<TableDetails>().setQuery(tableref,TableDetails.class).build();
        FRAtable = new FirebaseRecyclerAdapter<TableDetails, TableVH>(FROtable) {
            @Override
            protected void onBindViewHolder(@NonNull final TableVH holder, final int position, @NonNull final TableDetails model) {

                if(model.getId().contains("TFT"))
                {
                    if(model.getStatus().matches("AVAILABLE"))
                    {
                        holder.tableimg.setImageResource(R.drawable.tfta);
                    }
                    else {
                        holder.tableimg.setImageResource(R.drawable.tftu);

                    }
                }

                if(model.getId().contains("TFF"))
                {
                    if(model.getStatus().matches("AVAILABLE"))
                    {
                        holder.tableimg.setImageResource(R.drawable.tffa);
                    }
                    else {
                        holder.tableimg.setImageResource(R.drawable.tffu);

                    }
                }

                holder.status.setText(model.getStatus());

                holder.tableimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent tablebook = new Intent(getApplicationContext(),BookTableClient.class);
                        tablebook.putExtra("tableid",model.getId());
                        startActivity(tablebook);

                    }
                });

            }

            @NonNull
            @Override
            public TableVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book_table_layout,viewGroup,false);

                return new TableVH(view);
            }
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        tablerv.setLayoutManager(gridLayoutManager);
        FRAtable.startListening();
        tablerv.setAdapter(FRAtable);



    }
}
