package com.daxthompsonproject1.api.viewmodels;

import android.util.Log;

import com.daxthompsonproject1.api.models.ClientData;
import com.daxthompsonproject1.api.models.ManagerData;
import com.daxthompsonproject1.api.models.Reservation;
import com.google.android.gms.common.api.Api;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class ClientViewModel extends ParentViewModel{

    public ClientViewModel(){
        super();
    }

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
        ClientData client = (ClientData) this.userData.getValue();
        String uid = this.user.getValue().uid;
        client = snapshot.child(uid).getValue(ClientData.class);
        client.uid = uid;
        this.userData.setValue(client);

        this.reservationTable.get().addOnCompleteListener(task -> {
           updateWaitList(task.getResult());
        });
    }

    @Override
    protected void updateWaitList(DataSnapshot snapshot) {
        for(DataSnapshot child : snapshot.getChildren()){
            if(child.child("clientUid").getValue().equals(this.userData.getValue().uid)) {
                this.waitlist.getValue().add(child.getValue(Reservation.class));
            }
        }
    }

    public void makeReservation(String company){
        Reservation reservation = new Reservation(company, this.user.getValue().uid, System.currentTimeMillis());
        this.reservationTable.push().setValue(reservation);
    }

}
