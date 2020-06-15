package com.example.foodrecipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClientEdit extends AppCompatActivity {
//    Declaring variables
    EditText name,email;
    DatabaseReference nodePath;
    FirebaseAuth userAuth;
    String nameDB,emailDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_edit);

        //Initializing variables
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        nodePath = FirebaseDatabase.getInstance().getReference("users");
        userAuth = FirebaseAuth.getInstance();
//        /getting values from client profile
        Intent editInfo = getIntent();
        nameDB = editInfo.getStringExtra("name");
        emailDB = editInfo.getStringExtra("email");

        //Setting values to edit text
        name.setText(nameDB);
        email.setText(emailDB);
    }

    //update action by checking if the values are changed or not
    public void update(View v){
        if(isNameChanged() || isEmailChanged()){
            Toast.makeText(ClientEdit.this,"Value Updated",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ClientEdit.this,ClientProfileActivity.class));
        }
        else{
            Toast.makeText(ClientEdit.this,"Value Is Same Update Request Denied",Toast.LENGTH_SHORT).show();
        }

    }

    //To check if the name was changed or not
    public boolean isNameChanged(){
        if(!nameDB.equals(name.getText().toString())){
            nodePath.child(userAuth.getCurrentUser().getUid()).child("name").setValue(name.getText().toString());
            nameDB = name.getText().toString();
            return true;
        }else{
            return false;
        }
    }

    //To check if the name was changed or not
    public boolean isEmailChanged(){
        if(!emailDB.equals(email.getText().toString())){
            nodePath.child(userAuth.getCurrentUser().getUid()).child("email").setValue(email.getText().toString());
            emailDB = email.getText().toString();
            return true;
        }else{
            return false;
        }
    }
}
