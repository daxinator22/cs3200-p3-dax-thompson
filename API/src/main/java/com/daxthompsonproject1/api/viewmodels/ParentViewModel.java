package com.daxthompsonproject1.api.viewmodels;

import android.renderscript.Sampler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.daxthompsonproject1.api.models.ClientData;
import com.daxthompsonproject1.api.models.ManagerData;
import com.daxthompsonproject1.api.models.Reservation;
import com.daxthompsonproject1.api.models.User;
import com.daxthompsonproject1.api.models.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public abstract class ParentViewModel extends ViewModel {
    protected FirebaseAuth auth;
    protected MutableLiveData<User> user = new MutableLiveData<>();

    protected DatabaseReference userDataTable;
    protected MutableLiveData<UserData> userData = new MutableLiveData<>();

    protected MutableLiveData<ArrayList<Reservation>> waitlist = new MutableLiveData<>();
    protected DatabaseReference reservationTable = FirebaseDatabase.getInstance().getReference("/reservations");
    //    MutableLiveData<RuntimeException> loginError = new MutableLiveData<>();
    public ParentViewModel() {
        this.auth = FirebaseAuth.getInstance();
        this.auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser fbUser = auth.getCurrentUser();
//                loginError.setValue(null);
                if (fbUser == null) {
                    user.setValue(null);
                } else {
                    user.setValue(new User(fbUser));
                    Log.d("ParentViewModel-AuthChange", "Taking snapshot");
                    userDataTable.get().addOnCompleteListener(task -> {
                        DataSnapshot snapshot = task.getResult();
                        if(snapshot.child(user.getValue().uid).getValue() != null) {
                            updateUserData(snapshot);
                        }
                    });

                }
            }
        });

        defineUserDataTable();

        userDataTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(user.getValue() != null){
                    updateUserData(snapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reservationTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(user.getValue() != null && userData.getValue() != null) {
                    waitlist.setValue(new ArrayList<>());
                    updateWaitList(snapshot);
                    waitlist.setValue(waitlist.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public MutableLiveData<User> getUser() {return user;}
    public MutableLiveData<UserData> getUserData(){return userData;}
    public MutableLiveData<ArrayList<Reservation>> getWaitList(){return waitlist;}

    public void signUp(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task ->{
            String uid = user.getValue().uid;
            userData.getValue().uid = uid;
            userData.setValue(userData.getValue());
            userDataTable.child(uid).setValue(userData.getValue());
        });
//        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                AuthResult result = task.getResult();
//                if (result.getUser() == null) {
//                    loginError.setValue(new RuntimeException("Signup failed"));
//                }
//            }
//        });
    }

    public void signIn(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.getResult().getUser() == null){
                signOut();
            }
        });
    }

    public void signOut() {
        auth.signOut();
    }

    protected abstract void defineUserDataTable();
    protected abstract void updateUserData(DataSnapshot snapshot);
    protected abstract void updateWaitList(DataSnapshot snapshot);

}

