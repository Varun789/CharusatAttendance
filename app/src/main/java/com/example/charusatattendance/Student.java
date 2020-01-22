package com.example.charusatattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Student extends AppCompatActivity {
    EditText id,name,event_name,start_date,end_date;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference formRef = database.getReference("forms");
    DatabaseReference submittedRef = database.getReference("submitted");
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        id=findViewById(R.id.edt_id);
        name=findViewById(R.id.edt_name);
        event_name=findViewById(R.id.edt_event_name);
        start_date=findViewById(R.id.edt_start_date);
        end_date=findViewById(R.id.edt_end_date);
        submit=findViewById(R.id.btn_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key=formRef.push().getKey();
                String sid=id.getText().toString();
                String semester="6";
                String event=event_name.getText().toString();
                String start_d=start_date.getText().toString();
                String end_d=end_date.getText().toString();
                submittedRef.child(key).setValue(new Form(sid,semester,event, start_d,end_d,"17ce049","submitted",key));
                formRef.child(key).setValue(new Form(sid,semester,event, start_d,end_d,"17ce049","submitted",key));
            }
        });
    }
}
