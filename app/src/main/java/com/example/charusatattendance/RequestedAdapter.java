package com.example.charusatattendance;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;



public class RequestedAdapter extends RecyclerView.Adapter<RequestedHolder> {
    Context c;
    ArrayList<Form>request;


    public RequestedAdapter(Context c,ArrayList<Form>request)
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

        holder.setItemClickListener(new ItemClickListener() {

            Form f=request.get(position);

            @Override
            public void onItemClickListener(View view, int position) {
              Intent i =new Intent(c,Formreview.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("form",f);
                c.startActivity(i);

            }
        });



    }

    @Override
    public int getItemCount() {
        return request.size();
    }
}
