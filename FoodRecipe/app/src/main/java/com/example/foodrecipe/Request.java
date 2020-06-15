package com.example.foodrecipe;

import com.google.firebase.database.Exclude;

//Request model to send request values
public class Request {
    //Declaration of Variables
    private String id,rName,origin;

    public Request() {
    }

    //Initializing variables
    public Request(String rName, String origin) {
        this.rName = rName;
        this.origin = origin;
    }

    //setter and getter methods
    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
