package com.example.foodrecipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    //Declaration of variables
    private EditText fullName, email, password;
    private Button register;
    private FirebaseDatabase rootNode;
    private DatabaseReference nodePath;
    private FirebaseAuth userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Initializing variables
        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        userAuth = FirebaseAuth.getInstance();
    }

    //called when register button is clicked
    public void register(View v){
        //Setting values to pass to firebase authentication and realtime database
        final String fullNameEntered = fullName.getText().toString();
        final String emailEntered = email.getText().toString();
        String passwordEntered = password.getText().toString();
        final boolean isAdmin = false;

        //if structure to avoid null values
        if(fullNameEntered.isEmpty()){
            fullName.setError("Please Enter Your Name");
            fullName.requestFocus();
        }
        else if(emailEntered.isEmpty()){
            email.setError("Please Enter Your Email");
            email.requestFocus();
        }
        else if(passwordEntered.isEmpty()){
            password.setError("Please Enter Your Password");
            password.requestFocus();
        }
        else if(passwordEntered.length()<8){
            password.setError("Your Password Must be atleast 8 characters");
            password.requestFocus();
        }
        else {
            //creating user with inbuilt firebase auth function
            userAuth.createUserWithEmailAndPassword(emailEntered, passwordEntered).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Users user = new Users(emailEntered, fullNameEntered, isAdmin);//passing values to model to pass to realtime database

                        rootNode = FirebaseDatabase.getInstance();
                        nodePath = rootNode.getReference("users");//getting reference to users node in realtime database

                        //adding values
                        nodePath.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Register.this, MainActivity.class));
                                }
                            }
                        });
                    } else {
                        Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    //Runs when login button is used to go through activities
    public void login(View v){
        Intent login = new Intent(Register.this,MainActivity.class);
        startActivity(login);
    }
}
