package com.example.sunmeter;

import android.media.Image;

public class User {
    String firstName;
    String age;
    String lastName;
    String userName;
    // this is profile image, i don't know why I change it to img it will return null;

    private User(String firstName,String age,String lastName,String userName){
        this.firstName = firstName;
        this.age = age;
        this.lastName = lastName;
        this.userName = userName;
    }
    public User(){}

    public String getFirstName() {
        return firstName;
    }

    public String getUserName(){return userName; }

    public String getAge() {
        return age;
    }

    public String getLastName(){return lastName;}
}
