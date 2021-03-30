package com.daxthompsonproject1.api.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.daxthompsonproject1.api.models.ClientData;
import com.daxthompsonproject1.api.models.ManagerData;
import com.daxthompsonproject1.api.models.Reservation;
import com.google.android.gms.common.api.Api;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ClientViewModel extends ParentViewModel{

    private DatabaseReference managerTable;
    private MutableLiveData<ArrayList<ManagerData>> managers = new MutableLiveData<>();

    public ClientViewModel(){
        super();

        this.managerTable = FirebaseDatabase.getInstance().getReference("/managers");
        managerTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                managers.setValue(new ArrayList<>());

                for(DataSnapshot s : snapshot.getChildren()){
                    ManagerData managerData = s.getValue(ManagerData.class);
                    managerData.uid = s.getKey();
                    managers.getValue().add(managerData);
                }

                managers.setValue(managers.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public MutableLiveData<ArrayList<ManagerData>> getManagers(){return this.managers;}

    public void signUp(String email, String password, String displayName){
        super.signUp(email, password);
        this.userData.setValue(new ClientData(email, displayName));
        Log.d("ClientViewModel-signUp", userData.getValue().toString());
    }

    @Override
    protected void defineUserDataTable() {
        this.userDataTable = FirebaseDatabase.getInstance().getReference("/clients");
    }

    @Override
    protected void updateUserData(DataSnapshot snapshot) {
        ClientData client = snapshot.getValue(ClientData.class);
        client.uid = this.user.getValue().uid;
        this.userData.setValue(client);

        this.reservationTable.get().addOnCompleteListener(task -> {
           updateWaitList(task.getResult());
        });
    }

    @Override
    protected void updateWaitList(DataSnapshot snapshot) {
        this.getWaitList().setValue(new ArrayList<>());

        for(DataSnapshot child : snapshot.getChildren()){
            if(child.child("clientUid").getValue().equals(this.userData.getValue().uid)) {
                Reservation r = child.getValue(Reservation.class);
                r.id = child.getKey();
                this.waitlist.getValue().add(r);
            }
        }

        Collections.sort(this.waitlist.getValue());

        this.getWaitList().setValue(this.waitlist.getValue());
    }

    public void makeReservation(String company){
        ManagerData manager = null;
        for(ManagerData m : this.getManagers().getValue()){
            if(company.equals(m.company)){
                manager = m;
                break;
            }
        }


        Reservation reservation = new Reservation(manager.uid, this.user.getValue().uid, System.currentTimeMillis());
        this.reservationTable.push().setValue(reservation);
    }

}
