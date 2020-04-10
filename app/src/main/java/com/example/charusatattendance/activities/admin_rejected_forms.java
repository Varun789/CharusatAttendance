package com.example.charusatattendance.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.charusatattendance.adapters.admin_rejected_adapter;
import com.example.charusatattendance.classes.form_pojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin_rejected_forms extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference formRef = database.getReference("forms");
    private ArrayList<form_pojo> forms;
    private RecyclerView requested_recycler_view;
    private admin_rejected_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_rejected_forms);

        requested_recycler_view=findViewById(R.id.requested_recyclerview);
        requested_recycler_view.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        requested_recycler_view.setLayoutManager(manager);
        forms=new ArrayList<form_pojo>();
        adapter=new admin_rejected_adapter(getApplicationContext(),forms);


        requested_recycler_view.setAdapter(adapter);


        formRef.orderByChild("form_status").equalTo("rejected").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){
                        form_pojo l = npsnapshot.getValue(form_pojo.class);
                        forms.add(l);

                        adapter.notifyDataSetChanged();
                    }
                    adapter=new admin_rejected_adapter(getApplicationContext(),forms);
                    requested_recycler_view.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}