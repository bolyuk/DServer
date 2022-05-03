package org.bolyuk;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DFirebase extends Baser {

    private HashMap<String, ValueEventListener> observer = new HashMap<String, ValueEventListener>();
    private FirebaseApp app;
    public interface Getter{
        public void onGet(DataSnapshot value);
        public void onCancel(DatabaseError error);
    }


    public void init(String projectID, String appId, String apiKey, String referenceName){
        try {
            kovalski("firebase initializing....", 2);
             FirebaseOptions options = new FirebaseOptions.Builder()
                    .setProjectId(projectID)
                    .setApplicationId(appId)
                    .setApiKey(apiKey)
                    .build();
           app = FirebaseApp.initializeApp(App.getContext(), options,referenceName);
        }catch (Exception e){ kovalski(e);}
    }

    public void set(String dpath, Object value){

        FirebaseDatabase.getInstance(app).getReference().child(dpath).setValue(value);
    }

    public void get(String dpath, Getter getter){
        DatabaseReference db1 = FirebaseDatabase.getInstance(app).getReference(dpath);
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

    public void get(String dpath){
        get(dpath, new Getter() {
            @Override
            public void onGet(DataSnapshot value) {
                kovalski(value.toString(),3);
            }

            @Override
            public void onCancel(DatabaseError error) {
                kovalski("ERROW WHEN TRYING TO GET DATA! \n"+error.getMessage()+"\n"+error.getDetails(),0);

            }
        });

    }

    public void login(String email, String password){
        FirebaseAuth.getInstance(app).signInWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(
                            Task<AuthResult> task){
                        if (task.isSuccessful())
                           kovalski("Login success",3);
                        else
                            kovalski("LOGIN FAILED: \n"+task.getException().getMessage(),0);
                    }
                });
    }

    public boolean isLogged(){
        return FirebaseAuth.getInstance(app).getCurrentUser() != null;
    }

    public boolean delListener(String dpath) {
        if(!observer.containsKey(dpath)) return false;
        DatabaseReference connectedRef = FirebaseDatabase.getInstance(app).getReference(dpath);
        connectedRef.removeEventListener(observer.get(dpath));
        observer.remove(dpath);
        return true;
    }

    public boolean addListener(String dpath, Getter getter) {
        if(observer.containsKey(dpath)) return false;
        DatabaseReference connectedRef = FirebaseDatabase.getInstance(app).getReference(dpath);
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
