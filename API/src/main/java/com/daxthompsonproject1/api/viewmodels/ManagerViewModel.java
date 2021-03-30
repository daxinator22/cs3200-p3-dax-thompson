package com.daxthompsonproject1.api.viewmodels;

import androidx.annotation.NonNull;

import com.daxthompsonproject1.api.models.ManagerData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class ManagerViewModel extends ParentViewModel{

    public ManagerViewModel(){
        super();
    }

    public void signUp(String email, String password, String displayName, String company){
        super.signUp(email, password);

        this.userData.setValue(new ManagerData(email, displayName, company));
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

    }

    @Override
    protected void updateWaitList(DataSnapshot snapshot) {

    }

}
