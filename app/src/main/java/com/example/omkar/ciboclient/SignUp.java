package com.example.omkar.ciboclient;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    private final String CUSTOMER = "CUSTOMER";

    EditText firstname,lastname,email,phoneno,pass,cpass;
    Button signup;
    ProgressBar pb;
    private FirebaseAuth mAuth;
    Customer customer;
    String firstnamestr, lastnamestr , emailstr, phonestr , passstr , cpassstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        firstname = findViewById(R.id.etfirstname);
        lastname = findViewById(R.id.etlastname);
        email = findViewById(R.id.etemail);
        phoneno = findViewById(R.id.etphone);
        pass = findViewById(R.id.etpass);
        cpass = findViewById(R.id.etcpass);
        signup = findViewById(R.id.btsignup);
        pb = findViewById(R.id.signuppb);
        mAuth = FirebaseAuth.getInstance();
        pb.setVisibility(View.INVISIBLE);



    }

    public void signup(View view)
    {
        firstnamestr = firstname.getText().toString().trim();
        lastnamestr = lastname.getText().toString().trim();
        emailstr = email.getText().toString().trim();
        phonestr = phoneno.getText().toString().trim();
        passstr = pass.getText().toString().trim();
        cpassstr = cpass.getText().toString().trim();
        if(validate(firstnamestr,lastnamestr,emailstr,phonestr,passstr,cpassstr)==false)
        {
            return;
        }
        pb.setVisibility(View.VISIBLE);




        mAuth.createUserWithEmailAndPassword(emailstr, passstr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        task.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TAG",e.toString());
                            }
                        });

                        if (task.isSuccessful()) {

                            String user = mAuth.getCurrentUser().getUid();
                            customer = new Customer(firstnamestr,lastnamestr,phonestr,CUSTOMER);
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("users");
                            myRef.child(user).setValue(customer);
                            pb.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"Sign-Up Successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Login.class));

                        } else {

                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            pb.setVisibility(View.GONE);

                        }

                        // ...
                    }
                });

    }


    //VALIDATION
    public boolean validate(String firstnames,String lastnames,String emails,String phones,String passs,String cpasss)
    {
        Boolean flag = false;
        if(firstnames.isEmpty())
        {
            firstname.setError("Insert Firstname");
            firstname.requestFocus();
            return flag;
        }
        if(lastnames.isEmpty())
        {
            lastname.setError("Insert Lastname");
            lastname.requestFocus();
            return flag;
        }
        if(emails.isEmpty())
        {
            email.setError("Insert valid Email");
            email.requestFocus();
            return flag;
        }
        if(phones.isEmpty() || phones.length()<10)
        {
            phoneno.setError("Insert valid Phone Number");
            phoneno.requestFocus();
            return flag;
        }
        if(passs.isEmpty() )
        {
            pass.setError("Insert Password");
            pass.requestFocus();
            return flag;
        }

        if(!passs.matches(cpasss))
        {
            cpass.setError("Passwords Do Not Match");
            cpass.requestFocus();
            return flag;
        }
        flag = true;

        return flag;
    }


}
