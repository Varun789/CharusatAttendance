package com.example.charusatattendance.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.charusatattendance.activities.R;
import com.example.charusatattendance.activities.student_submitted_form_review;
import com.example.charusatattendance.classes.form_pojo;
import com.example.charusatattendance.holders.RequestedHolder;
import com.example.charusatattendance.interfaces.ItemClickListener;

import java.util.ArrayList;

public class StudentSubmittedAdapter extends RecyclerView.Adapter<RequestedHolder> {
    Context c;
    ArrayList<form_pojo> request;


    public StudentSubmittedAdapter(Context c,ArrayList<form_pojo>request)
    {
        this.c=c;
        this.request=request;
    }

    @NonNull
    @Override
    public RequestedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.requested_card, parent, false);
        Log.i("Form", "onCreateView! called");
        return new RequestedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestedHolder holder, final int position) {
        //object of Requestedholder
        //Pass Student Id and Semester
        holder.sid.setText(request.get(position).getSid());
        holder.sem.setText(request.get(position).getSemester());
        holder.event.setText(request.get(position).getEvent_name());

        holder.setItemClickListener(new ItemClickListener() {

            //form_pojo f=request.get(position);

            //Send Object to the Form review.

            @Override
            public void onItemClickListener(View view, int position) {
                Intent i =new Intent(c, student_submitted_form_review.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("form_key",request.get(position).getForm_key());
                c.startActivity(i);

            }
        });



    }

    @Override
    public int getItemCount() {
        return request.size();
    }
}

