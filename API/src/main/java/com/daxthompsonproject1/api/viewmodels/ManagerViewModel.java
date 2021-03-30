package com.daxthompsonproject1.api.viewmodels;

import com.daxthompsonproject1.api.models.ManagerData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class ManagerViewModel extends ParentViewModel{

    public ManagerViewModel(){
        super();
    }

    @Override
    protected void defineUserDataTable() {
        this.userDataTable = FirebaseDatabase.getInstance().getReference("/managers");
    }

    @Override
    protected void updateUserData(DataSnapshot snapshot) {

    }

    public void signUp(String email, String password, String displayName, String company){
        super.signUp(email, password);
        this.userData.setValue(new ManagerData(email, displayName, company));
    }

}
