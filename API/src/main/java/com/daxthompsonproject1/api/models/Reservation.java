package com.daxthompsonproject1.api.models;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Reservation implements Comparable<Reservation> {

    public String managerUid;
    public String clientUid;
    public long timestamp;

    @Exclude
    public String id;

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

    public LinearLayout render(Activity activity){
        LinearLayout container = new LinearLayout(activity);

        AppCompatTextView reservation = new AppCompatTextView(activity);
        reservation.setText(this.toString());

        container.addView(reservation);
        return container;

    }

    @Override
    public int compareTo(Reservation o) {
        return (int) (this.timestamp - o.timestamp);
    }
}
