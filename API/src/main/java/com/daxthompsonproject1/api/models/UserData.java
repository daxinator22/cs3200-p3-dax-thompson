package com.daxthompsonproject1.api.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public abstract class UserData {

    public String email;
    public String displayName;

    @Exclude
    public String uid;

    public UserData() {


    }

    public UserData(String email, String displayName){

        this.email = email;
        this.displayName = displayName;

    }

    public abstract String toString();



}
