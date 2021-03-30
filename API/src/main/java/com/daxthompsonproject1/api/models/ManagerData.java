package com.daxthompsonproject1.api.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ManagerData extends UserData{

    public String company;

    public ManagerData(){

    }

    public ManagerData(String email, String displayName, String company){
        super(email, displayName);
        this.company = company;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Uid: " + uid + "\n");
        builder.append("Email: " + email + "\n");
        builder.append("Display Name: " + displayName + "\n");
        builder.append("Company: " + company + "\n");

        return builder.toString();
    }
}
