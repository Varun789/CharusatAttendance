package com.example.charusatattendance.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.charusatattendance.adapters.StudentSubmittedAdapter;
import com.example.charusatattendance.classes.form_pojo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class student_submitted extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth;
    DatabaseReference studentformsRef = database.getReference("studentforms");
    private ArrayList<form_pojo>forms;
    private RecyclerView requested_recycler_view;
    private StudentSubmittedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_submitted);
        requested_recycler_view=findViewById(R.id.requested_recyclerview);
        requested_recycler_view.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        requested_recycler_view.setLayoutManager(manager);
        forms=new ArrayList<form_pojo>();

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        String uid = user.getUid();
        adapter=new StudentSubmittedAdapter(getApplicationContext(),forms);


        requested_recycler_view.setAdapter(adapter);

        studentformsRef.child(uid).orderByChild("form_status").equalTo("submitted").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                        form_pojo l = npsnapshot.getValue(form_pojo.class);
                        Log.i("Form submitted", l.getEvent_name());
                        forms.add(l);
//
                        adapter.notifyDataSetChanged();
                    }
                    adapter = new StudentSubmittedAdapter(getApplicationContext(), forms);
                    requested_recycler_view.setAdapter(adapter);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
