package com.example.omkar.ciboclient;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    EditText emailid;
    Button resetpass;
    FirebaseAuth userAuth;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailid = findViewById(R.id.fpemailet);
        resetpass = findViewById(R.id.fpresetbt);
         userAuth = FirebaseAuth.getInstance();


         resetpass.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                  email = emailid.getText().toString().trim();
                  if(email.isEmpty())
                  {
                      emailid.setError("Enter valid Email");
                  }
                  else
                  {
                      userAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {
                              if(task.isSuccessful())
                              {

                                  Toast.makeText(getApplicationContext(),"Password reset email sent",Toast.LENGTH_LONG).show();
                                  finish();
                              }
                              else
                              {
                                  Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                              }
                          }
                      });
                  }

             }
         });

    }


}
