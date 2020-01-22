package com.example.charusatattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Formreview extends AppCompatActivity {
    TextView id,semester,event_name,start_date,end_date;
    Button approve,reject;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference submittedRef = database.getReference("submitted");
    DatabaseReference approvedRef = database.getReference("approved");
    DatabaseReference  rejectedRef=database.getReference("rejected");
    DatabaseReference formsRef=database.getReference("forms");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formreview);
        id=findViewById(R.id.txt_id);
        semester=findViewById(R.id.txt_semester);
        event_name=findViewById(R.id.txt_event);
        start_date=findViewById(R.id.txt_start_date);
        end_date=findViewById(R.id.txt_end_date);
        approve=findViewById(R.id.btn_approve);
        reject=findViewById(R.id.btn_reject);

        Intent i=getIntent();
        final Form form = (Form) i.getSerializableExtra("form");
        id.setText(form.getSid());
        semester.setText(form.getSemester());
        event_name.setText(form.event_name);
        start_date.setText(form.getStart_date());
        end_date.setText(form.end_date);


        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approvedRef.child(form.getForm_key()).setValue(form);
                formsRef.child(form.getForm_key()).child("status").setValue("approved");
                submittedRef.child(form.getForm_key()).removeValue();

            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectedRef.child(form.getForm_key()).setValue(form);
                formsRef.child(form.getForm_key()).child("status").setValue("rejected");
                submittedRef.child(form.getForm_key()).removeValue();


            }
        });



    }
}
