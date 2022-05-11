package com.example1.openapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends AppCompatActivity {

    EditText mName,mEmail,mPassword,mPhone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    DocumentReference reference;

    @Override
    protected void onStart() {
        super.onStart();

        mName = findViewById(R.id.ptxt_name);
        mEmail = findViewById(R.id.ptxt_email);
        mPassword = findViewById(R.id.ptxt_password);
        mPhone = findViewById(R.id.ptxt_phone);

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        String userid = user.getUid();
        System.out.println(userid);
        reference = fStore.collection("Users").document(user.getUid());
        System.out.println(reference.get().toString());
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                System.out.println("kkkkkkkkkkkkk");
                if(task.getResult().exists()){
                    System.out.println("aaaaaaaaaaaaaaaaaaa");
                    String name = task.getResult().getString("Name");
                    String email = task.getResult().getString("Email");
                    String phone = task.getResult().getString("Phone");


                    System.out.println(name);
                    mName.setText(name);
                    mEmail.setText(email);
                    mPhone.setText(phone);

                }else{
                    Toast.makeText(getApplicationContext(),"Error loading ",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
}