package com.example.foodrecipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClientSideRecipeActivity extends AppCompatActivity {
    //Declaring variables
    private RecyclerView postRecycler;
    private ClientSideRecipeAdapter cAdapter;
    private DatabaseReference nodePath;
    private List<Post> posts;
    SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_side_recipe);

        //Initializing variables
        postRecycler = findViewById(R.id.recyclerViewClient);
        postRecycler.setHasFixedSize(true);
        postRecycler.setLayoutManager(new LinearLayoutManager(this));
        search = findViewById(R.id.search);
        posts = new ArrayList<>();

        nodePath = FirebaseDatabase.getInstance().getReference("posts");

    }

    @Override
    protected void onStart() {
        super.onStart();

        //getting all values of recipes
        nodePath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.clear();
                for(DataSnapshot postsSnapshot : dataSnapshot.getChildren()){
                    Post recipePost = postsSnapshot.getValue(Post.class);
                    posts.add(recipePost);
                }
                cAdapter = new ClientSideRecipeAdapter(ClientSideRecipeActivity.this,posts);
                postRecycler.setAdapter(cAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ClientSideRecipeActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

        //searching titles from database
        if(search!=null){
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    searchRecipe(newText);
                    return true;
                }
            });
        }
        else{
            Toast.makeText(ClientSideRecipeActivity.this,"Object NUll re",Toast.LENGTH_SHORT).show();
        }

    }

    //searching keywords and adding to recycler view
    public void searchRecipe(String keyword){
        ArrayList<Post> recipeList = new ArrayList<>();
        for(Post object : posts){
            if(object!=null){
                if(object.getTitle().toLowerCase().contains(keyword.toLowerCase())){
                    recipeList.add(object);
                }
            }else{
                Toast.makeText(ClientSideRecipeActivity.this,"Object NUll re",Toast.LENGTH_SHORT).show();
            }

        }
        ClientSideRecipeAdapter adapter = new ClientSideRecipeAdapter(ClientSideRecipeActivity.this,recipeList);
        postRecycler.setAdapter(adapter);
    }
}
