package com.example.foodrecipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RequestActivity extends AppCompatActivity {
    //Declaration of variables
    private EditText name,origin;
    private Button button;
    long idNumber;
    DatabaseReference nodePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        //Initializing variables
        name = findViewById(R.id.RecipeName);
        origin = findViewById(R.id.OriginName);
        button = findViewById(R.id.requestPost);
        nodePath = FirebaseDatabase.getInstance().getReference("requests");

        //adding request to realtime database
        nodePath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    idNumber = dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void request(View v){
        //Setting variables to pass values into the database
        final String rName = name.getText().toString();
        final String originName = origin.getText().toString();
        //if structure to avoid null values
        if(rName.isEmpty()){
            name.setError("Please Provide Recipe Name");
            name.requestFocus();
        }
        else if(originName.isEmpty()){
            origin.setError("Please Provide Origin");
            origin.requestFocus();
        }
        else{
            //sending values through Request model class
            Request request = new Request(rName,originName);
            nodePath.child(String.valueOf(idNumber+1)).setValue(request);
            startActivity(new Intent(RequestActivity.this,UserHomeActivity.class));
            Toast.makeText(RequestActivity.this,"Your Request Has Been Submitted",Toast.LENGTH_SHORT).show();
        }

    }
}
