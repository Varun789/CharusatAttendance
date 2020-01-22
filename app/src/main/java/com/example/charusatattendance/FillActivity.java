package com.example.charusatattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FillActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference studentRef = database.getReference("students");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final EditText name,id,status;
        final TextView t_name,t_id,t_status;
        final Button edit,submit;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill);

        name=findViewById(R.id.edt_name);
        id=findViewById(R.id.edt_id);
        status=findViewById(R.id.edt_status);


        t_name=findViewById(R.id.txt_name);
        t_id=findViewById(R.id.txt_id);
        t_status=findViewById(R.id.txt_status);



        edit=findViewById(R.id.btn_submit);
        submit=findViewById(R.id.btn_submit);

        Query query=studentRef.orderByChild("student_key").equalTo(mAuth.getInstance().getCurrentUser().getUid());

        //If do student is a new login
        if(query!=null)
        {

            Log.i("See","aa gaya!");

//            studentRef.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    student_pojjo std = dataSnapshot.getValue(student_pojjo.class);
//                    t_id.setText(std.getSid());
//                    t_status.setText(std.getStatus());
//                    t_name.setText(std.getName());
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {}
//
//            });
            edit.setVisibility(View.VISIBLE);
            t_name.setVisibility(View.VISIBLE);
            t_id.setVisibility(View.VISIBLE);
            t_status.setVisibility(View.VISIBLE);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    t_name.setVisibility(View.GONE);
                    t_id.setVisibility(View.GONE);
                    t_status.setVisibility(View.GONE);
                    name.setVisibility(View.VISIBLE);
                    id.setVisibility(View.VISIBLE);
                    status.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.VISIBLE);

                }
            });
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    student_pojjo student=new student_pojjo(mAuth.getInstance().getCurrentUser().getUid(),name.getText().toString(),id.getText().toString(),status.toString());
                    t_name.setText(student.getName());
                    t_id.setText(student.getSid());
                    t_status.setText(student.getStatus());
                    t_name.setVisibility(View.VISIBLE);
                    t_id.setVisibility(View.VISIBLE);
                    t_status.setVisibility(View.VISIBLE);
                    name.setVisibility(View.GONE);
                    id.setVisibility(View.GONE);
                    status.setVisibility(View.GONE);
                    submit.setVisibility(View.GONE);
                    edit.setVisibility(View.VISIBLE);
                    studentRef.child(mAuth.getInstance().getCurrentUser().getUid()).setValue(student);



                }
            });


        }
        else
        {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    student_pojjo student=new student_pojjo(mAuth.getInstance().getCurrentUser().getUid(),name.getText().toString(),id.getText().toString(),status.toString());
                    t_name.setText(student.getName());
                    t_id.setText(student.getSid());
                    t_status.setText(student.getStatus());
                    t_name.setVisibility(View.VISIBLE);
                    t_id.setVisibility(View.VISIBLE);
                    t_status.setVisibility(View.VISIBLE);
                    name.setVisibility(View.GONE);
                    id.setVisibility(View.GONE);
                    status.setVisibility(View.GONE);
                    submit.setVisibility(View.GONE);
                    edit.setVisibility(View.VISIBLE);
                    studentRef.child(mAuth.getInstance().getCurrentUser().getUid()).setValue(student);



                }
            });



            t_id.setVisibility(View.GONE);
            t_status.setVisibility(View.GONE);
            t_name.setVisibility(View.GONE);
            id.setVisibility(View.VISIBLE);
            status.setVisibility(View.VISIBLE);
            submit.setVisibility(View.VISIBLE);
            edit.setVisibility(View.GONE);
        }




    }

    }

