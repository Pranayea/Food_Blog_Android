package com.example.foodrecipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClientProfileActivity extends AppCompatActivity {
    //Declaring variables
    TextView userValue,emailValue;
    DatabaseReference nodePath;
    FirebaseAuth userAuth;
    String userNameDB;
    String emailDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);

        //Initializing variables
        userValue = findViewById(R.id.userValue);
        emailValue = findViewById(R.id.emailValue);
        nodePath = FirebaseDatabase.getInstance().getReference("users");
        userAuth = FirebaseAuth.getInstance();

        //Getting values from current User
        nodePath.child(userAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userNameDB = dataSnapshot.child("name").getValue(String.class);
                emailDB = dataSnapshot.child("email").getValue(String.class);

                userValue.setText(userNameDB);
                emailValue.setText(emailDB);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    //Sets values for next Activity
    public void update(View v){
        Intent edit = new Intent(ClientProfileActivity.this,ClientEdit.class);
        edit.putExtra("name",userNameDB);
        edit.putExtra("email",emailDB);
        startActivity(edit);

    }

    //Delete Users from firebase authentication and firebase Realtime Database
    public void delete(View v){
        final FirebaseUser userId = userAuth.getCurrentUser(); //finding user
        final String selectedUser =  userId.getUid(); // getting user id
        userId.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    nodePath.child(selectedUser).removeValue();
                    Toast.makeText(ClientProfileActivity.this,"UserDeleted",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ClientProfileActivity.this,MainActivity.class));
                }
            }
        });
    }

    //Logout from the system
    public void logout(View v){
        userAuth.signOut();
        startActivity(new Intent(ClientProfileActivity.this,MainActivity.class));
    }
}
