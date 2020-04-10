package com.example.charusatattendance.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.charusatattendance.activities.R;
import com.example.charusatattendance.activities.admin_approved_form_review;
import com.example.charusatattendance.activities.admin_approved_with_certificateupload;
import com.example.charusatattendance.classes.form_pojo;
import com.example.charusatattendance.holders.admin_approved_holder;
import com.example.charusatattendance.interfaces.ItemClickListener;

import java.util.ArrayList;



public class admin_approved_adapter extends RecyclerView.Adapter<admin_approved_holder> {
    Context c;
    ArrayList<form_pojo> request;


    public admin_approved_adapter(Context c,ArrayList<form_pojo>request)
    {
        this.c=c;
        this.request=request;
    }

    @NonNull
    @Override
    public admin_approved_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_approved_card_view, parent, false);
        return new admin_approved_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final admin_approved_holder holder, final int position) {
        //object of Requestedholder
        //Pass Student Id and Semester
        holder.sid.setText(request.get(position).getSid());
        holder.sem.setText(request.get(position).getSemester());
        holder.event.setText(request.get(position).getEvent_name());

        if(request.get(position).getImage_key().isEmpty())
        {
            holder.dot.setColorFilter(Color.YELLOW);
        }
        else {
            holder.dot.setColorFilter(Color.GREEN);
        }


        holder.setItemClickListener(new ItemClickListener() {

            //form_pojo f=request.get(position);

            //Send Object to the Form review.

            @Override
            public void onItemClickListener(View view, int position) {
                if(holder.dot.getColorFilter().equals("YELLOW")) {
                    Log.d("Colour check","Aa gaya if me!!");
                    Intent i = new Intent(c, admin_approved_form_review.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("form_key", request.get(position).getForm_key());
                    c.startActivity(i);
                }
                else
                {
                    Log.d("Colour check","Aa gaya else me!!");
                    Intent i = new Intent(c, admin_approved_with_certificateupload.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("form_key", request.get(position).getForm_key());
                    c.startActivity(i);
                }

            }
        });



    }

    @Override
    public int getItemCount() {
        return request.size();
    }
}


