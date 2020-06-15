package com.example.foodrecipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class RecipeProfileActivity extends AppCompatActivity {
    //Declaration of activity
    TextView recipeTitle, recipeIngredients, recipeProcedure, recipeCategories;
    ImageView recipeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_profile);
        //Initializing Variables
        recipeTitle = findViewById(R.id.recipeTitle);
        recipeIngredients =findViewById(R.id.recipeIngredients);
        recipeProcedure = findViewById(R.id.recipeProcedure);
        recipeCategories = findViewById(R.id.recipeCategory);
        recipeImage = findViewById(R.id.recipeImage);

        Intent details = getIntent();
        //Values sent from intent
        recipeTitle.setText(details.getStringExtra("title"));
        recipeIngredients.setText(details.getStringExtra("ingredients"));
        recipeProcedure.setText(details.getStringExtra("procedure"));
        recipeCategories.setText(details.getStringExtra("category"));
        Picasso.get().load(details.getStringExtra("image")).fit().centerCrop().into(recipeImage);
    }
}
