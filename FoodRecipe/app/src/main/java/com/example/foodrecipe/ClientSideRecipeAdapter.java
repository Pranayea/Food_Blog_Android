package com.example.foodrecipe;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ClientSideRecipeAdapter extends RecyclerView.Adapter<ClientSideRecipeAdapter.ClientSideHolder>{
    //Declaring variables
    private Context mContext;
    private List<Post> mPosts;


    //Creating constructor to initialize and use this class
    public  ClientSideRecipeAdapter(Context context,List<Post> posts){
        this.mContext = context;
        this.mPosts = posts;
    }

    //Using a model Layout
    @NonNull
    @Override
    public ClientSideHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recipe_items,parent,false);
        return new ClientSideHolder(v);
    }

    //Binding Values to the layout
    @Override
    public void onBindViewHolder(@NonNull ClientSideHolder holder, int position) {
        final Post currentPost = mPosts.get(position);
        holder.recipeTitle.setText(currentPost.getTitle());
        Picasso.get().load(currentPost.getImageURL()).fit().centerCrop().into(holder.recipeImage);
        holder.ParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recipeDetails = new Intent(mContext,RecipeProfileActivity.class);
                recipeDetails.putExtra("title",currentPost.getTitle());
                recipeDetails.putExtra("image",currentPost.getImageURL());
                recipeDetails.putExtra("ingredients",currentPost.getIngredients());
                recipeDetails.putExtra("procedure",currentPost.getProcedure());
                recipeDetails.putExtra("category",currentPost.getCategories());
                mContext.startActivity(recipeDetails);
            }
        });
    }

    //Getting total count of layout
    @Override
    public int getItemCount() {
        return mPosts.size();
    }


    //Initializing the variables
    public class ClientSideHolder extends RecyclerView.ViewHolder{
            public TextView recipeTitle,recipeIngredients,recipeProcedure,recipeCategory;
            public ImageView recipeImage;
            public CardView ParentLayout;
            public ClientSideHolder(@NonNull View itemView) {
                super(itemView);


                recipeTitle = itemView.findViewById(R.id.recipeTitle);
                recipeImage = itemView.findViewById(R.id.recipeImage);
                recipeIngredients = itemView.findViewById(R.id.recipeIngredients);
                recipeProcedure = itemView.findViewById(R.id.recipeProcedure);
                recipeCategory = itemView.findViewById(R.id.recipeCategory);
                ParentLayout = itemView.findViewById(R.id.ParentLayout);
            }
        }
}
