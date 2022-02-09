package com.atr.dserver.dclasses;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Method;
import java.util.HashMap;

public class DFirebase extends DBClass{

    private static HashMap<String, ValueEventListener> observer = new HashMap<String, ValueEventListener>();

    public interface Getter{
        public void onGet(DataSnapshot value);
        public void onCancel(DatabaseError error);
    }


    public static void init(){
        log("firebase initializing....");
        FirebaseApp.initializeApp(DBContext.getAppContext());
    }

    public static void set(String dpath, Object value){

        FirebaseDatabase.getInstance().getReference().child(dpath).setValue(value);
    }

    public static void get(String dpath, DFirebase.Getter getter){
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference(dpath);
        db1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                getter.onGet(dataSnapshot);
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                getter.onCancel(databaseError);
            }});
    }

    public static void get(String dpath){
        get(dpath, new Getter() {
            @Override
            public void onGet(DataSnapshot value) {
                log(value.toString());
            }

            @Override
            public void onCancel(DatabaseError error) {
                log("ERROW WHEN TRYING TO GET DATA! \n"+error.getMessage()+"\n"+error.getDetails());

            }
        });

    }

    public static void login(String email, String password){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(
                            Task<AuthResult> task){
                        if (task.isSuccessful())
                           log("Login success");
                        else
                            log("LOGIN FAILED: \n"+task.getException().getMessage());
                    }
                });
    }

    public static boolean isLogged(){
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public static boolean delListener(String dpath) {
        if(!observer.containsKey(dpath)) return false;
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(dpath);
        connectedRef.removeEventListener(observer.get(dpath));
        observer.remove(dpath);
        return true;
    }

    public static boolean addListener(String dpath, DFirebase.Getter getter) {
        if(observer.containsKey(dpath)) return false;
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(dpath);
        observer.put(dpath, connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                getter.onGet(snapshot);
            }

            @Override
            public void onCancelled(DatabaseError err) {
              getter.onCancel(err);
            }
        }));
        return true;
    }
}
