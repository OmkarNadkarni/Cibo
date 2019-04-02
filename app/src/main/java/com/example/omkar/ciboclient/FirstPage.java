package com.example.omkar.ciboclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class FirstPage extends AppCompatActivity {

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        next = findViewById(R.id.buttonf);

        final SharedPreferences sp = this.getSharedPreferences("chibo",MODE_PRIVATE);
        //SharedPreferences.Editor ed = sp.edit();
        boolean first = sp.getBoolean("first",true);
        String user = sp.getString("Login","logout");
        if(!first  )
        {

                    Intent loginpage = new Intent(getApplicationContext(),Login.class);
                    startActivity(loginpage);
                    finish();

        }

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);


        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.3, 30);
        myAnim.setInterpolator(interpolator);

        next.startAnimation(myAnim);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor ed = sp.edit();
                ed.putBoolean("first",false);
                ed.apply();
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
