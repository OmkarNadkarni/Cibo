package com.example.omkar.ciboclient;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainScreen extends AppCompatActivity {

    ImageView demoImage;
    EditText tablenumber;
    FirebaseAuth mauth;
    DatabaseReference tableref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        demoImage =  findViewById(R.id.imgmain);
        mauth = FirebaseAuth.getInstance();
        tableref = FirebaseDatabase.getInstance().getReference("Table");






        int imagesToShow[] = { R.drawable.newof1, R.drawable.newof2,R.drawable.newof3 };
      // animate(demoImage, imagesToShow, 0,true);

        Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //add(int groupId, int itemId, int order, CharSequence title)
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.add(1,2,1,"View history");
        menu.add(3,4,2,"Logout");

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == 4 )
        {
            mauth.signOut();
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }
        if(id==2)
        {
            startActivity(new Intent(getApplicationContext(),ViewHistoryActivity.class));
        }
        return true;
    }


    public void startactivity(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.btbooktable:
                startActivity(new Intent(getApplicationContext(),BookTable.class));
                break;
            case R.id.btlocation:
                startActivity(new Intent(getApplicationContext(),maplocation.class));
                break;
            case R.id.btmenu:
                startActivity(new Intent(MainScreen.this, com.example.omkar.ciboclient.Menu.class));
                break;

                //IMPLEMENT DINE IN IF CODE TOO BIG IMPLEMENT A LISTENER
//            case R.id.btdinein:
//               startActivity(new Intent(getApplicationContext(),.class));
//                break;
            case R.id.bttakeaway:
                startActivity(new Intent(getApplicationContext(),TakeAway.class));
                break;
            case R.id.btoffers:
                startActivity(new Intent(getApplicationContext(),Offers.class));
                break;
        }
    }


    public void DineInActivity(View v)
    {
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.dinein_alert, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AppFullScreenTheme));
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTexttablecustomer);


        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                validation(userInput.getText().toString());

                            }

                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public void validation(final String text)
    {
        tableref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren())
                {
                    if(snap.child("password").getValue().toString().matches(text) )
                    {
                        String TABLEID = snap.getKey();
                        String uid = (String) snap.child("customer").getValue();
                        if(uid.matches("NONE") || uid.matches(mauth.getUid()))
                        {
                            tableref.child(TABLEID).child("customer").setValue(mauth.getUid());
                            tableref.child(TABLEID).child("status").setValue("UNAVAILABLE");
                            Intent i = new Intent(getApplicationContext(),DineInActivity.class);
                            i.putExtra("TableId",TABLEID);
                            startActivity(i);
                            finish();
                            return;
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"INVALID USER",Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }
                }
                Toast.makeText(getApplicationContext(),"Invalid id",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




    private void animate(final ImageView imageView, final int images[], final int imageIndex, final boolean forever) {

        //imageView <-- The View which displays the images
        //images[] <-- Holds R references to the images to display
        //imageIndex <-- index of the first image to show in images[]
        //forever <-- If equals true then after the last image it starts all over again with the first image resulting in an infinite loop. You have been warned.

        int fadeInDuration = 500; // Configure time values here
        int timeBetween = 3000;
        int fadeOutDuration = 1000;

        imageView.setVisibility(View.INVISIBLE);    //Visible or invisible by default - this will apply when the animation ends
        imageView.setImageResource(images[imageIndex]);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setDuration(fadeInDuration);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
        fadeOut.setStartOffset(fadeInDuration + timeBetween);
        fadeOut.setDuration(fadeOutDuration);

        AnimationSet animation = new AnimationSet(false); // change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        animation.setRepeatCount(1);
        imageView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (images.length - 1 > imageIndex) {
                    animate(imageView, images, imageIndex + 1,forever); //Calls itself until it gets to the end of the array
                }
                else {
                    if (forever == true){
                        animate(imageView, images, 0,forever);  //Calls itself to start the animation all over again in a loop if forever = true
                    }
                }
            }
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }
        });



    }
}






