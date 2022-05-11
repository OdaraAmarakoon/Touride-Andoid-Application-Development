package com.example1.openapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    EditText mEmail,mPassword;
    Button mlogin;
    TextView mRegister;

    FirebaseAuth fAuth;
    FirebaseFirestore fstore;

    String email,password;


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth     = FirebaseAuth.getInstance();
        fstore    = FirebaseFirestore.getInstance();

        // get email and pw
        mEmail    = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);

        mlogin    = findViewById(R.id.btn_login);
        mRegister = findViewById(R.id.btn_signup);

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //login validations

                email    = mEmail.getText().toString();
                password = mPassword.getText().toString();

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

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                            System.out.println(fAuth.getCurrentUser().getUid());
                            //checkUserAccessLevel(fAuth.getUid());
                            checkUserAccessLevel(fAuth.getCurrentUser().getUid());




                        }else{
                            Toast.makeText(Login.this,"Invalid Email Or Password" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });

    }

    private void checkUserAccessLevel(String uid){
        DocumentReference df = fstore.collection("Users").document(uid);

        //extract the data from the document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess: " + documentSnapshot.getData());
                System.out.println(documentSnapshot.getData());

                //identify the user access level(admin or user)

                if(documentSnapshot.getString("isAdmin") != null){
                    System.out.println("rrrrrrrrr");
                    //current user is an admin
                    startActivity(new Intent(getApplicationContext(),AdminHome.class));
                }

                if(documentSnapshot.getString("isUser") != null){
                    //current user is an customer
                    startActivity(new Intent(getApplicationContext(),Home.class));
                }else{
                    System.out.println("User not loging");
                    Toast.makeText(getApplicationContext(),"user not logged",Toast.LENGTH_SHORT).show();
                }
            }


        });



    }

}