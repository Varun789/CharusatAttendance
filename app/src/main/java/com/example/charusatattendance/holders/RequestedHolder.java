package com.example.charusatattendance.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.charusatattendance.activities.R;
import com.example.charusatattendance.interfaces.ItemClickListener;


public class RequestedHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView sid;
    public TextView sem;
    public TextView event;
    ItemClickListener itemClickListener;
      public RequestedHolder(@NonNull View itemView)
    {
        super(itemView);

        this.sid=itemView.findViewById(R.id.txt_student_id);
        this.sem= itemView.findViewById(R.id.txt_semester);
        this.event=itemView.findViewById(R.id.txt_event);
        itemView.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
          this.itemClickListener.onItemClickListener(v,getLayoutPosition());

    }
    public void setItemClickListener(ItemClickListener il)
    {
        this.itemClickListener=il;
    }
}
