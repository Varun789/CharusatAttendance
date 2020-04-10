package com.example.charusatattendance.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.charusatattendance.classes.form_pojo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin_side_submitted_form_review extends AppCompatActivity {
    private TextView emailid,id,name,counsellor,academic_year,semester,start_date,end_date,start_time,end_time,participated_in,type_of_event,event_name,organization,topic_of_work,type_of_participation,info,financial_support;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private form_pojo formpojo;
    private DatabaseReference formsRef=database.getReference("forms");
    private DatabaseReference studentRef = database.getReference("students");
    private DatabaseReference studentformsRef = database.getReference("studentforms");
    private  FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private  FirebaseUser user=mAuth.getCurrentUser();
    private Button approve,reject;
    Dialog mydialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_side_submitted_form_review);
        emailid=findViewById(R.id.txt_emailid);
        counsellor=findViewById(R.id.txt_counselor);
        id=findViewById(R.id.txt_sid);
        name=findViewById(R.id.txt_name);
        semester=findViewById(R.id.txt_semester);
        academic_year=findViewById(R.id.txt_year);
        type_of_event=findViewById(R.id.txt_typeofevent);
        event_name=findViewById(R.id.titleofevent);
        start_date=findViewById(R.id.txt_startdate);
        end_date=findViewById(R.id.txt_enddate);
        start_time=findViewById(R.id.txt_starttime);
        end_time=findViewById(R.id.txt_endtime);
        participated_in=findViewById(R.id.txt_participate);
        organization=findViewById(R.id.txt_organization);
        topic_of_work=findViewById(R.id.txt_topic);
        type_of_participation=findViewById(R.id.txt_type_of_participation);
        info=findViewById(R.id.txt_otherinfo);
        financial_support=findViewById(R.id.txt_financial);
        approve=findViewById(R.id.btn_approve);
        reject=findViewById(R.id.btn_reject);
        mydialog=new Dialog(this);




        Intent i=getIntent();
        final String form_key=i.getStringExtra("form_key");

        formsRef.child(form_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                formpojo = dataSnapshot.getValue(form_pojo.class);
                Log.d("Tag apna hai", "Value is: " + formpojo.getSid());
                emailid.setText(formpojo.getEmail_id());
                id.setText(formpojo.getSid());
                counsellor.setText(formpojo.getCounselor());
                name.setText(formpojo.getStudent_name());
                semester.setText(formpojo.getSemester());
                academic_year.setText(formpojo.getYear());
                event_name.setText(formpojo.getEvent_name());
                start_date.setText(formpojo.getStart_date());
                end_date.setText(formpojo.getEnd_date());
                start_time.setText(formpojo.getStart_time());
                end_time.setText(formpojo.getEnd_time());
                participated_in.setText(formpojo.getParticipation());
                type_of_event.setText(formpojo.getType_of_event());
                topic_of_work.setText(formpojo.getTopic_of_work());
                organization.setText(formpojo.getOrganization());
                info.setText(formpojo.getInformation());
                type_of_participation.setText(formpojo.getTeam());
                financial_support.setText(formpojo.getSupport());

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Tag apna hai", "Failed to read value.", error.toException());
            }
        });
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"approved",Toast.LENGTH_LONG).show();
                formsRef.child(formpojo.getForm_key()).child("form_status").setValue("approved");
                studentformsRef.child(formpojo.getStudent_key()).child(formpojo.getForm_key()).child("form_status").setValue("approved");
                //formsRef.child(formpojo.getForm_key()).child("faculty_feedback").setValue(faculty_feedback.getText().toString());
                //studentformsRef.child(formpojo.getStudent_key()).child(formpojo.getForm_key()).child("faculty_feedback").setValue(faculty_feedback.getText().toString());
                Log.d("Executing approve","Yes!");
                // submittedRef.child(formpojo.getForm_key()).removeValue();

            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rejectedRef.child(formpojo.getForm_key()).setValue(formpojo);
                formsRef.child(formpojo.getForm_key()).child("form_status").setValue("rejected");
                studentformsRef.child(formpojo.getStudent_key()).child(formpojo.getForm_key()).child("form_status").setValue("rejected");

                // submittedRef.child(formpojo.getForm_key()).removeValue();

                showPopup();

            }
        });

    }
    public void showPopup(){
        mydialog.setContentView(R.layout.popup_feedback);
        Button btn_ok = mydialog.findViewById(R.id.btn_ok);
        final EditText faculty_feedback=mydialog.findViewById(R.id.edt_feedback);


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(faculty_feedback.getText().toString()==null){
                    Toast.makeText(admin_side_submitted_form_review.this, "please enter your feedback ", Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                 String facultyFeedback=faculty_feedback.getText().toString();
                    //Put feedback in feedback_key

                    studentformsRef.child(formpojo.getStudent_key()).child(formpojo.getForm_key()).child("faculty_feedback").setValue(facultyFeedback);
                    formsRef.child(formpojo.getForm_key()).child("faculty_feedback").setValue(facultyFeedback);

                }

                Intent i = new Intent(admin_side_submitted_form_review.this, admin_main_page.class);
                startActivity(i);

                mydialog.dismiss();
            }
        });

        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.show();
    }

    }


