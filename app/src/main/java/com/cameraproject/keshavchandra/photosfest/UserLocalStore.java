package com.cameraproject.keshavchandra.photosfest;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Keshav Chandra on 20-09-2015.
 */
public class UserLocalStore {
    public static final String SP_name = "User Details";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_name,0);
    }

    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();

        spEditor.putString("name",user.Name);
        spEditor.putString("email",user.Email);
        spEditor.putString("username",user.Username);
        spEditor.putString("password",user.Password);
        spEditor.commit();


    }

    public User getLoggedinUser(){
        String name = userLocalDatabase.getString("name", "");
        String email = userLocalDatabase.getString("email","");
        String username = userLocalDatabase.getString("username","");
        String password = userLocalDatabase.getString("password","");

        User storedUser = new User(name,email,username,password);
        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn",loggedIn);
        spEditor.commit();

    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
