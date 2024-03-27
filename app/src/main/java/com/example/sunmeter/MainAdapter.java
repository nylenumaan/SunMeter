package com.example.sunmeter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    Context context;
    ArrayList<User> rankList;
    public MainAdapter(Context context,ArrayList<User> rankList){
        this.context = context;
        this.rankList = rankList;
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        User  user = rankList.get(position);
        holder.setFirstName(String.valueOf(user.getFirstName()));
        holder.setAge(String.valueOf(user.getAge()));

        holder.setImage(String.valueOf(user.getLastName()));
        Random rnd = new Random();







    }

    @Override
    public int getItemCount() {
        return rankList.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setFirstName(String firstName){
            TextView textview = (TextView) mView.findViewById(R.id.tvfirstName);
            textview.setText(firstName);
        }
        public void setAge(String age){
            TextView textview = (TextView) mView.findViewById(R.id.tvAge);
            textview.setText(age);
        }
        public void setImage( String img){
            ImageView imageView = (ImageView)mView.findViewById(R.id.tvimage);
            Log.i("1",img);

            Picasso.get().load(img).resize(200,200).noFade().into(imageView);


        }



        }
    }

