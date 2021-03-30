package com.daxthompsonproject1.api.models;

public class ClientData extends UserData{

    public ClientData(){

    }

    public ClientData(String email, String displayName){
        this.email = email;
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Uid: " + this.uid + "\n");
        builder.append("Email: " + this.email + "\n");
        builder.append("Display Name: " + this.displayName + "\n");

        return builder.toString();
    }
}
