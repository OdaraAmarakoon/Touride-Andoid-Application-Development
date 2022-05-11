package com.example1.openapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText mName,mEmail,mPassword,mPhone;
    Button mRegister;
    TextView mLogin;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mName       = findViewById(R.id.Name);
        mEmail      = findViewById(R.id.Email);
        mPassword   = findViewById(R.id.Password);
        mPhone      = findViewById(R.id.Phone);
        mRegister   = findViewById(R.id.btn_register);
        mLogin      = findViewById(R.id.btn_login);

        fAuth       = FirebaseAuth.getInstance();
        fStore      = FirebaseFirestore.getInstance();


        checkField(mName);
        checkField(mEmail);
        checkField(mPassword);
        checkField(mPhone);



        //validate if user Already logged in
/*
        if(fAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }

        */


        mRegister.setOnClickListener(new View.OnClickListener() {

            // email , password validation
            @Override
            public void onClick(View view) {
                String email    = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required");
                    return;
                }


                if(password.length() < 6){
                    mPassword.setError("Password must be >= 6 characters");
                    return;

                }



                //Register User in firebase database
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            FirebaseUser user = fAuth.getCurrentUser();
                            //Toast.makeText(Register.this,"User Created Successfully",Toast.LENGTH_SHORT).show();
                            //get the name and phone to fire store
                            DocumentReference df = fStore.collection("Users").document(user.getUid());
                            Map<String,Object> userInfo = new HashMap<>();
                            userInfo.put("Name",mName.getText().toString());
                            userInfo.put("Email",mEmail.getText().toString());
                            userInfo.put("Phone",mPhone.getText().toString());

                            //specify if the user is an admin

                            userInfo.put("isUser","1");
                            df.set(userInfo);
                            Toast.makeText(Register.this,"User Created Successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Home.class));
                        } else{
                            Toast.makeText(Register.this,"Error !!!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();


                        }

                    }
                });

            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }

    private void checkField(EditText mName) {
    }

}