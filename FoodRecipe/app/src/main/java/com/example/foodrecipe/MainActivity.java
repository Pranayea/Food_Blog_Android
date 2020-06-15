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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    //Declaring variables
    private EditText email, password;
    private Button login;
    private FirebaseDatabase rootNode;
    private DatabaseReference nodePath;
    private FirebaseAuth userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing variables
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        rootNode = FirebaseDatabase.getInstance();
        nodePath = rootNode.getReference("users");
        userAuth = FirebaseAuth.getInstance();
    }

    //Action after login is clicked
    public void login(View v){
        String emailEntered = email.getText().toString();
        String passwordEntered = password.getText().toString();

        //To check if the the forms are filled or not
        if(emailEntered.isEmpty()){
            email.setError("Please Enter Your Email");
            email.requestFocus();
        }
        else if(passwordEntered.isEmpty()){
            password.setError("Please Enter Your Password");
            password.requestFocus();
        }
        else {
            userAuth.signInWithEmailAndPassword(emailEntered, passwordEntered).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        nodePath.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                boolean admin = dataSnapshot.child("admin").getValue(Boolean.class);
                                if(admin == true){
                                    String userNameDB = dataSnapshot.child("name").getValue(String.class); //Getting userName values from fire database
                                    String emailDB = dataSnapshot.child("email").getValue(String.class); //Getting email values from fire database

                                    Intent userData = new Intent(MainActivity.this, AdminActivity.class);
                                    Toast.makeText(MainActivity.this, "Login Complete", Toast.LENGTH_SHORT).show();
                                    startActivity(userData);
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Login Complete", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, UserHomeActivity.class));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } else {
                        Toast.makeText(MainActivity.this,  task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    //Going to register activity
    public void register(View v){
        Intent register = new Intent(MainActivity.this, Register.class);
        startActivity(register);
    }
}
