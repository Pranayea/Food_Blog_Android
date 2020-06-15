package com.example.foodrecipe;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    //Declaring variables
    private Context mContext;
    private List<Post> mPosts;
    private OnClickListener mListener;

    //Initializing variables
    public PostAdapter(Context context, List<Post> posts){
        this.mContext = context;
        this.mPosts = posts;
    }

    //setting values to recipe item layout
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recipe_items,parent,false);
        return new PostViewHolder(v);
    }

    //Binding values to the layout
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, final int position) {
        final Post currentPost = mPosts.get(position);
        holder.recipeTitle.setText(currentPost.getTitle());
        Picasso.get().load(currentPost.getImageURL()).fit().centerCrop().into(holder.recipeImage);
        //Sending all values of post to new adapter
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

    //getting value count
    @Override
    public int getItemCount() {
        return mPosts.size();
    }


    public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        //Declaring variables
        public TextView recipeTitle,recipeIngredients,recipeProcedure,recipeCategory;
        public ImageView recipeImage;
        public CardView ParentLayout;

        //Initializing variables
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeTitle = itemView.findViewById(R.id.recipeTitle);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            recipeIngredients = itemView.findViewById(R.id.recipeIngredients);
            recipeProcedure = itemView.findViewById(R.id.recipeProcedure);
            recipeCategory = itemView.findViewById(R.id.recipeCategory);
            ParentLayout = itemView.findViewById(R.id.ParentLayout);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        //Onclick action to show other action
        @Override
        public void onClick(View v) {
            if(mListener!=null){
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION){
                    mListener.OnItemClick(pos);
                }
            }
        }

        //creating menu after long press on item
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Delete = menu.add(Menu.NONE,1,1,"Delete");

            Delete.setOnMenuItemClickListener(this);
        }

        //checking the position of item
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(mListener!=null){
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION){
                    switch (item.getItemId()){
                        case 1:
                            mListener.onDeleteClick(pos);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    //interface to act on for extra actions
    public interface OnClickListener{
        void OnItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnClickListener(OnClickListener listener){
        this.mListener = listener;
    }
}
