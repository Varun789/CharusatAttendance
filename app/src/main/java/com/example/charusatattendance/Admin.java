package com.example.charusatattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference requested=database.getReference("requested");
    DatabaseReference accepted=database.getReference("accepted");
    DatabaseReference rejected=database.getReference("rejected");
    Button accept,reject;
    TextView info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        accept=(Button) findViewById(R.id.btn_accept);
        reject=(Button) findViewById(R.id.btn_reject);
        info=(TextView) findViewById(R.id.txt_info);
        requested.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String str_info=dataSnapshot.child("17ce049").child("-LyJp_187CtDn25e1vDn").child("Information").getValue(String.class);
                info.setText(str_info);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Tag", "Failed to read value.");
            }
            });

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requested.child("17ce049").child("-LyJp_187CtDn25e1vDn").child("status").setValue("approved");


                }
            });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requested.child("17ce049").child("-LyJp_187CtDn25e1vDn").child("status").setValue("rejected");
               

            }
        });

        }

    }

