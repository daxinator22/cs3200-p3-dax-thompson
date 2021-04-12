package com.daxthompsonproject1.api.viewmodels;

import android.location.Location;

import androidx.annotation.NonNull;

import com.daxthompsonproject1.api.models.ManagerData;
import com.daxthompsonproject1.api.models.Reservation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ManagerViewModel extends ParentViewModel{

    public ManagerViewModel(){
        super();
    }

    public void signUp(String email, String password, String displayName, String company, String lat, String lon){

        super.signUp(email, password);
        this.userData.setValue(new ManagerData(email, displayName, company, lat, lon));
    }

    @Override
    protected void defineUserDataTable() {
        this.userDataTable = FirebaseDatabase.getInstance().getReference("/managers");
    }

    @Override
    protected void updateUserData(DataSnapshot snapshot) {
        ManagerData manager = snapshot.getValue(ManagerData.class);
        manager.uid = this.user.getValue().uid;
        this.userData.setValue(manager);

        this.reservationTable.get().addOnCompleteListener(task -> {
            updateWaitList(task.getResult());
        });

    }

    @Override
    protected void updateWaitList(DataSnapshot snapshot) {
        this.waitlist.setValue(new ArrayList<>());

        for(DataSnapshot child : snapshot.getChildren()){
            if(child.child("managerUid").getValue().equals(this.userData.getValue().uid)){
                Reservation r = child.getValue(Reservation.class);
                r.id = child.getKey();
                waitlist.getValue().add(r);
            }
        }

        this.waitlist.setValue(this.waitlist.getValue());
    }

}
