package com.daxthompsonproject1.api.viewmodels;

import com.daxthompsonproject1.api.models.ManagerData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class ClientViewModel extends ParentViewModel{

    public ClientViewModel(){
        super();
    }

    @Override
    protected void defineUserDataTable() {
        this.userDataTable = FirebaseDatabase.getInstance().getReference("clients");
    }

    @Override
    protected void updateUserData(DataSnapshot snapshot) {
        ManagerData manager = snapshot.getValue(ManagerData.class);
        manager.uid = this.user.getValue().uid;
        this.userData.setValue(manager);
    }
}