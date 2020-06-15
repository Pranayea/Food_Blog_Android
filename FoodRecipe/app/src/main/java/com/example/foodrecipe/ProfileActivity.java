package com.example.foodrecipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    //Declaring variables
    TextView userValue,emailValue;
    DatabaseReference nodePath;
    FirebaseAuth userAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Initializing variables
        userValue = findViewById(R.id.userValue);
        emailValue = findViewById(R.id.emailValue);
        nodePath = FirebaseDatabase.getInstance().getReference("users");
        userAuth = FirebaseAuth.getInstance();

        //getting values of current users
        nodePath.child(userAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userNameDB = dataSnapshot.child("name").getValue(String.class);
                String emailDB = dataSnapshot.child("email").getValue(String.class);

                userValue.setText(userNameDB);
                emailValue.setText(emailDB);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //logout function
    public void logout(View v){
        userAuth.signOut();
        startActivity(new Intent(ProfileActivity.this,MainActivity.class));
    }
}
