package com.example.sunmeter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class setting extends AppCompatActivity {
    DatabaseReference firedatabase ;
    MainAdapter mainAdapter;
    ArrayList<User> list;
    private User user;
List<String> key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Button update = findViewById(R.id.update_btn);
        EditText firstName = findViewById(R.id.firstName_update);
       // EditText image = findViewById(R.id.img_update);
        list = new ArrayList<>();
        mainAdapter = new MainAdapter(this,list);
        key = new ArrayList<String>();


        firedatabase =  FirebaseDatabase.getInstance().getReference("Users");
        firedatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    list.add(user);
                  Log.i("22", snapshot.getValue().toString());

               //   key.add( snapshot.child("Abhay").getKey());


                }
                mainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_name = firstName.getText().toString();
              //  String img = image.getText().toString();
                user = list.get(0);

               // Log.i("11111",userName);
                Log.i("222","222");
                updateData(user,first_name);
            }
        });
    }

    private void updateData(User user, String first_name) {
        HashMap hashMap = new HashMap();

        hashMap.put("firstName",first_name);
        //hashMap.put("img",img);
        Log.i("11111",first_name);

       // firedatabase.child(username).child("firstName").setValue(name);

        firedatabase.child(user.getUserName()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
            if(task.isSuccessful()){
                Toast.makeText(setting.this,"successfully udpated",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(setting.this,"failed",Toast.LENGTH_SHORT).show();
            }
            }
        });
    }
}