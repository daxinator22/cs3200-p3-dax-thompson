package com.daxthompsonproject1.api.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ManagerData extends UserData{

    public String company;
    public String lat;
    public String lon;

    public ManagerData(){

    }

    public ManagerData(String email, String displayName, String company, long lat, long lon){
        super(email, displayName);
        this.company = company;
        this.lat = lat + "";
        this.lon = lon + "";
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Uid: " + uid + "\n");
        builder.append("Email: " + email + "\n");
        builder.append("Display Name: " + displayName + "\n");
        builder.append("Company: " + company + "\n");
        builder.append("Latitude: " + lat + "\n");
        builder.append("Longitude: " + lon + "\n");

        return builder.toString();
    }
}
