package com.example1.openapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class A extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("NNNNNNNNNNNNNNNNNN");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
    }
}