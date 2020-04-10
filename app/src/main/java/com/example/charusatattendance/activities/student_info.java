package com.example.charusatattendance.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.charusatattendance.classes.student_pojjo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class student_info extends AppCompatActivity {
    private FirebaseAuth mAuth =FirebaseAuth.getInstance();
    FirebaseUser user=mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference studentRef = database.getReference("students");
    TextView sid,student_name,counsellor_name;
    Button edit_button;

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        sid=findViewById(R.id.txt_sid);
        student_name=findViewById(R.id.txt_student_name);
        counsellor_name=findViewById(R.id.txt_consellor);
        // Read from the database
        studentRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                student_pojjo value = dataSnapshot.getValue(student_pojjo.class);
                sid.setText(value.getSid());
                student_name.setText(value.getName());
                counsellor_name.setText(value.getCouncellor());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("CHECK", "Failed to read value.", error.toException());
            }
        });

        edit_button=findViewById(R.id.btn_edit_profile);
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText id,name,councellor;
                Button  submit;

                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.student_info_pop_up);
                id=dialog.findViewById(R.id.edt_id);
                name=dialog.findViewById(R.id.edt_name);
                councellor=dialog.findViewById(R.id.edt_counsellor);
                submit=dialog.findViewById(R.id.btn_submit);
                id.setText(sid.getText());
                name.setText(student_name.getText());
                councellor.setText(counsellor_name.getText());
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(id.getText().toString()=="" )
                        {
                            id.setError("Enter id");
                        }
                        else if(name.getText().toString().toString()=="")
                        {
                            name.setError("Enter name");
                        }
                        else if (councellor.getText().toString()=="")
                        {
                            name.setError("Enter counsellor name");
                        }
                        else  {
                            student_pojjo student = new student_pojjo(name.getText().toString(), id.getText().toString(), councellor.getText().toString(), user.getEmail());
                            studentRef.child(user.getUid()).setValue(student);
                            dialog.dismiss();
                        }

                    }

                });
                dialog.show();
            }
        });
    }
}

