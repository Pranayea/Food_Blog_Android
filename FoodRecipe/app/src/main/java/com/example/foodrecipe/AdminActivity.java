package com.example.foodrecipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

    }

//    onclick action from adminactivity file
    public void profile(View v){
//        getting values from Mainactivity
        Intent userData = getIntent();
        String userDB = userData.getStringExtra("name");
        String emailDB = userData.getStringExtra("email");

//        Going to new Activity
        Intent profileData = new Intent(AdminActivity.this,ProfileActivity.class);
        profileData.putExtra("name",userDB);
        profileData.putExtra("email",emailDB);
        startActivity(profileData);
    }

    //        Going to new Activity
    public void post(View v){
        startActivity(new Intent(AdminActivity.this,PostActivity.class));
    }

    //        Going to new Activity
    public void recipes(View v){startActivity(new Intent(AdminActivity.this,RecipeActivity.class));}

    //        Going to new Activity
    public void request(View v){startActivity(new Intent(AdminActivity.this,RequestList.class));}
}
