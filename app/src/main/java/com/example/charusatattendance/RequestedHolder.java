package com.example.charusatattendance;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RequestedHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView sid,sem;
    ItemClickListener itemClickListener;
      RequestedHolder(@NonNull View itemView )
    {
        super(itemView);

        this.sid=itemView.findViewById(R.id.txt_student_id);
        this.sem= itemView.findViewById(R.id.txt_semester);
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
