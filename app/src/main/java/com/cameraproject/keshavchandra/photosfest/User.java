package com.cameraproject.keshavchandra.photosfest;

/**
 * Created by Keshav Chandra on 20-09-2015.
 */
public class User {
    String Name,Email,Username,Password;

    public User(String name,String email,String username,String password){
        this.Name = name;
        this.Email = email;
        this.Username = username;
        this.Password = password;
    }

    public User(String username,String password){
        this.Name = "";
        this.Email = "abc@gmail.com";
        this.Username = username;
        this.Password = password;
    }


}
