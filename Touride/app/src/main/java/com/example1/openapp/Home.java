package com.example1.openapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    TextView mguide, missues,mprofile, mlogout;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fAuth = FirebaseAuth.getInstance();

        mguide = findViewById(R.id.btn_Guide);
        mprofile = findViewById(R.id.btn_profile);
        missues = findViewById(R.id.btn_Issues);
        mlogout = findViewById(R.id.btn_logout);


        mlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();

                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

      mprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),UserProfile.class));
            }
        });
    }





}