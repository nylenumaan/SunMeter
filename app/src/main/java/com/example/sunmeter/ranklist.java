package com.example.sunmeter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class ranklist extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    MainAdapter mainAdapter;
    ArrayList<User>list;
    //DatabaseReference userref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranklist);

        recyclerView = findViewById(R.id.ranklist);
        Query query =FirebaseDatabase.getInstance().getReference("Users").orderByChild("age");
        database = FirebaseDatabase.getInstance().getReference("Users");
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ranklist.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        list = new ArrayList<>();
        mainAdapter = new MainAdapter(this,list);
        recyclerView.setAdapter(mainAdapter);





        database.orderByChild("age").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    list.add(user);
                }
                mainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}