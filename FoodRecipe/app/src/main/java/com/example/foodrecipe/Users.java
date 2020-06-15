package com.example.foodrecipe;

//Users Model class to pass user values to realtime database
public class Users {
    //Declaration of variables
    private String email, name;
    private boolean isAdmin;

    public Users() {
    }

    //Initialization of variables
    public Users(String email, String name, boolean isAdmin) {
        this.email = email;
        this.name = name;
        this.isAdmin = isAdmin;
    }

    //getter and setter methods
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
