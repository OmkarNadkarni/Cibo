package com.example.omkar.ciboclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button  login;
    EditText username,password;
    TextView forgotpassword,signup;
    private FirebaseAuth mAuth;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.loginc);
        username=(EditText)findViewById(R.id.usernamec);
        password=(EditText)findViewById(R.id.passwordc);
        login=(Button)findViewById(R.id.loginc);
        forgotpassword=(TextView)findViewById(R.id.forgotpasswordc);
        signup=(TextView)findViewById(R.id.signupc);
        mAuth = FirebaseAuth.getInstance();
        pb = findViewById(R.id.signinpb);
        pb.setVisibility(View.INVISIBLE);


         SharedPreferences sp = this.getSharedPreferences("login",MODE_PRIVATE);
        //SharedPreferences.Editor ed = sp.edit();
        String user = sp.getString("loginid",null);
        String uid = mAuth.getUid();
        if(uid !=null)
        {

            startActivity(new Intent(getApplicationContext(),MainScreen.class));
            finish();
        }




        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ForgotPassword.class);
                startActivity(i);



            }
        });
        String text="Create an account.Sign up";

        SpannableString ss=new SpannableString(text);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent signupintent=new Intent(getApplicationContext(),SignUp.class);
                startActivity(signupintent);
            }
        };
        ss.setSpan(clickableSpan1,18,25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signup.setText(ss);
        signup.setMovementMethod(LinkMovementMethod.getInstance());

    }

    public void signin(View view)
    {
        String usernamestr = username.getText().toString().trim();
        String passwordstr = password.getText().toString().trim();
        if(usernamestr.isEmpty())
        {
            username.setError("Please input a valid username");
            username.requestFocus();
            return;
        }
        if(passwordstr.isEmpty())
        {
            password.setError("Please input a valid password");
            password.requestFocus();
            return;
        }
        pb.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(usernamestr, passwordstr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Intent mainscreenintent = new Intent(getApplicationContext(),MainScreen.class);
                            String user = mAuth.getCurrentUser().getUid();
                            startActivity(mainscreenintent);
                            pb.setVisibility(View.GONE);
                            finish();

                        } else {

                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            pb.setVisibility(View.GONE);

                        }


                    }
                });
    }


}
