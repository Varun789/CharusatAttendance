package com.example.charusatattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference studentRef = database.getReference("student");
    DatabaseReference adminRef= database.getReference("requested");
    DatabaseReference requested=database.getReference("requested");
    EditText info;
    Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submit=(Button) findViewById(R.id.btn_submit);
        info=(EditText) findViewById(R.id.edt_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strinfo = info.getText().toString();

                if(TextUtils.isEmpty(strinfo)) {
                    info.setError("Please request something");
                    return;
                }
                else {
                        String Key=requested.child("17ce049").push().getKey();
                        requested.child("17ce049").child(Key).child("Information").setValue(strinfo);
                        requested.child("17ce049").child(Key).child("Id").setValue(Key);
                         requested.child("17ce049").child(Key).child("Status").setValue("requested");
                    }
                Intent i=new Intent(MainActivity.this,Admin.class);
                startActivity(i);
            }
        });

    }

}
