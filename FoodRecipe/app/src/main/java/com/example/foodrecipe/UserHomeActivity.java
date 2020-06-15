package com.example.foodrecipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
    }

    //When Clicked used goes to profile
    public void profile(View v){startActivity(new Intent(UserHomeActivity.this,ClientProfileActivity.class));}
    //When Clicked used goes to recipe
    public void recipes(View v){
        startActivity(new Intent(UserHomeActivity.this,ClientSideRecipeActivity.class));
    }
    //When Clicked used goes to request
    public void request(View v){
        startActivity(new Intent(UserHomeActivity.this,RequestActivity.class));
    }

}
