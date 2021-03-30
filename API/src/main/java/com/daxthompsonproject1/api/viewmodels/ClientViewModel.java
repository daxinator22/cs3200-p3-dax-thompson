package com.daxthompsonproject1.api.viewmodels;

import com.daxthompsonproject1.api.models.ClientData;
import com.daxthompsonproject1.api.models.ManagerData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class ClientViewModel extends ParentViewModel{

    public ClientViewModel(){
        super();
    }

    public void signUp(String email, String password, String displayName){
        super.signUp(email, password);
        this.userData.setValue(new ClientData(email, displayName));
    }

    @Override
    protected void defineUserDataTable() {
        this.userDataTable = FirebaseDatabase.getInstance().getReference("clients");
    }

    @Override
    protected void updateUserData(DataSnapshot snapshot) {
        ClientData client = snapshot.getValue(ClientData.class);
        client.uid = this.user.getValue().uid;
        this.userData.setValue(client);
    }

}
