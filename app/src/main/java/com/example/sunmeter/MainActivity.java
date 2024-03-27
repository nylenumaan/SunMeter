package com.example.sunmeter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView progressText;
    ImageButton refresh;
    private CalculateSunTime calculateSunTime;
    int i = 10;
    DatabaseReference firedatabase ;
    MainAdapter mainAdapter;
    ArrayList<User>list;
    private User user;
    boolean getData = true;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);
        progressText = findViewById(R.id.progres_text);
        list = new ArrayList<>();
        mainAdapter = new MainAdapter(this,list);


        calculateSunTime = new CalculateSunTime();

        if(BuildConfig.DEBUG) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        firedatabase = FirebaseDatabase.getInstance().getReference("Users");
        firedatabase.addValueEventListener(new ValueEventListener() {
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
        FloatingActionButton countdown = findViewById(R.id.countdown_btn);

        countdown.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                user = list.get(0);


                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(i >= 0){
                            //android.os.SystemClock.sleep(5000);
                            progressText.setText(""+i+"sec");
                            progressBar.setProgress(i);
                            i--;
                            handler.postDelayed(this,1000);


                        }else{
                            handler.removeCallbacks(this);
                            if(getData) {
                                updateData(user);
                                getData = false;
                            }

                        }

                    }
                },1000);


            }
        });



        refresh = findViewById(R.id.refresh_btn);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    user = list.get(0);
                    final Handler handler = new Handler();
                i =10;
                getData = true;
                progressText.setText(""+i+"sec");

                try {
                    i = calculateSunTime.weatherData(43.073929,-89.385239);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.i("i value","" + i);

            }
        });

       Button button = findViewById(R.id.recycle_btn);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(view.getContext(),ranklist.class);
               startActivity(intent);

           }
       });
       Button button1 = findViewById(R.id.setting_btn);
       button1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(view.getContext(),setting.class);
               startActivity(intent);
           }
       });

    }
    private void updateData(User user){
        HashMap User = new HashMap();
         String count = user.age;

        int temp = Integer.valueOf(count);
        temp++;
        user.age = String.valueOf(temp);

        User.put("age",String.valueOf(temp));




        firedatabase.child(user.getUserName()).updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"You absorb your daily sunlight!",Toast.LENGTH_SHORT).show();
                }else{
                    Log.i(":22","failed");
                }
            }
        });
    }

}