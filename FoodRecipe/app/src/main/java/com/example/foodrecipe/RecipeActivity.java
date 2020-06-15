package com.example.foodrecipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity implements PostAdapter.OnClickListener {
    //Declaring variables
    private RecyclerView postRecycler;
    private PostAdapter pAdapter;
    private DatabaseReference nodePath;
    private FirebaseStorage storage;
    private List<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        //Initializing variables
        postRecycler = findViewById(R.id.recyclerView);
        postRecycler.setHasFixedSize(true);
        postRecycler.setLayoutManager(new LinearLayoutManager(this));

        posts = new ArrayList<>();
        storage = FirebaseStorage.getInstance();
        nodePath = FirebaseDatabase.getInstance().getReference("posts"); //getting reference of node in the database
        //getting all posts in the database
        nodePath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.clear();//needed so that the recycler view has no duplicate list
                for(DataSnapshot postsSnapshot : dataSnapshot.getChildren()){
                    Post recipePost = postsSnapshot.getValue(Post.class);
                    recipePost.setMKey(postsSnapshot.getKey());
                    posts.add(recipePost);
                }
                pAdapter = new PostAdapter(RecipeActivity.this,posts);
                postRecycler.setAdapter(pAdapter);
                pAdapter.setOnClickListener(RecipeActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RecipeActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void OnItemClick(int position) {
        Toast.makeText(RecipeActivity.this,"Position at"+position,Toast.LENGTH_SHORT).show();
    }

    //Deletion of data from database from recycler view
    @Override
    public void onDeleteClick(int position) {
        Post selectedItem = posts.get(position);//getting position
        final String selectedKey = selectedItem.getMKey();

        StorageReference imageRef = storage.getReferenceFromUrl(selectedItem.getImageURL());//Getting image position from database
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                nodePath.child(selectedKey).removeValue();
                Toast.makeText(RecipeActivity.this,"Delete Success",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RecipeActivity.this,AdminActivity.class));
            }
        });
    }
}
