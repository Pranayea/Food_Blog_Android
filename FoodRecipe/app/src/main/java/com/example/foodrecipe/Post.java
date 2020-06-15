package com.example.foodrecipe;

import com.google.firebase.database.Exclude;

//Model class to add post elements in Firebase realtime database
public class Post {
    //Declaring variables
    String title,procedure,ingredients,categories,imageURL,mKey;

    public Post() {
    }

    //initializing variables
    public Post(String title, String ingredients, String procedure, String categories, String imageURL) {
        this.title = title;
        this.procedure = procedure;
        this.ingredients = ingredients;
        this.categories = categories;
        this.imageURL = imageURL;
    }

    //Getter and setter methods
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


    //to get random keys of realtime database assigned to the post
    @Exclude
    public String getMKey() {
        return mKey;
    }
    @Exclude
    public void setMKey(String mKey) {
        this.mKey = mKey;
    }
}
