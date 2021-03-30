package com.daxthompsonproject1.api.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Reservation {

    public String managerUid;
    public String clientUid;
    public long timestamp;

    public Reservation(){



    }

    public Reservation(String managerUid, String clientUid, long timestamp){
        this.managerUid = managerUid;
        this.clientUid = clientUid;
        this.timestamp = timestamp;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("Manager Uid: %s\n", this.managerUid));
        builder.append(String.format("Client Uid: %s\n", this.clientUid));
        builder.append(String.format("Timestamp: %s\n", this.timestamp));

        return builder.toString();
    }

}
