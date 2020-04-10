package com.example.charusatattendance.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.charusatattendance.classes.student_pojjo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class Main_student_layout extends AppCompatActivity {


    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference studentRef = database.getReference("students");
    DatabaseReference studentlistRef = database.getReference("studentlist");
    CardView request_form, submitted_form, approved_form, fully_approved_form, rejected_form;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_student_layout);


        checkNewUser();

        request_form = findViewById(R.id.request_form);
        submitted_form = findViewById(R.id.submitted_form);
        approved_form = findViewById(R.id.approved_form);
        fully_approved_form = findViewById(R.id.fully_approved_form);
        rejected_form = findViewById(R.id.rejected_form);


        studentRef.child("student-key").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                        String studentkey = npsnapshot.getValue(String.class);
                        if (studentkey == user.getUid()) {
                            return;
                        } else {
                            pop_up_student_info_form();
                        }
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        request_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main_student_layout.this, student_request_form.class);
                startActivity(i);
            }
        });

        submitted_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main_student_layout.this, student_submitted.class);
                startActivity(i);
            }
        });
        approved_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main_student_layout.this, student_approved.class);
                startActivity(i);
            }
        });
        fully_approved_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main_student_layout.this, fully_approved_form.class);
                startActivity(i);
            }
        });

        rejected_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Main_student_layout.this,student_rejected.class);
                startActivity(i);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.student_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_profile:
                Log.i("check", "In the profile");
                startActivity(new Intent(this, student_info.class));
                return true;


            case R.id.menu_logout:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
   public void pop_up_student_info_form() {
        final EditText id, name, councellor;
        Button submit;


        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.student_info_pop_up);
        id = dialog.findViewById(R.id.edt_id);
        name = dialog.findViewById(R.id.edt_name);
        councellor = dialog.findViewById(R.id.edt_counsellor);
        submit = dialog.findViewById(R.id.btn_submit);
        dialog.show();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (id.getText().toString()=="") {
                    id.setError("Enter id");
                } else if (name.getText().toString() == "") {
                    name.setError("Enter name");
                } else if (councellor.getText().toString() == "") {
                    name.setError("Enter counsellor name");
                } else {
                    //Enter his detailes in the firebase.
                    student_pojjo student = new student_pojjo(name.getText().toString(), id.getText().toString(), councellor.getText().toString(), user.getEmail());
                    studentRef.child(user.getUid()).setValue(student);
                    dialog.dismiss();
                }
            }
        });



    }
    //Createtoken
    void createToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    if (task.isSuccessful()) {
                       String token = task.getResult().getToken();
                       saveToken(token);
                    } else {

                    }
                }
            });
}
    //Save token
    private void saveToken(String token) {


        DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference("students");

        dbUsers.child(mAuth.getCurrentUser().getUid()).child("token")
                .setValue(token).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.i("token","Token Created");
                }
            }
        });


    }
    void enterDetails() {

        studentlistRef.child(user.getUid()).child("student-key").setValue(user.getUid());
       createToken();

    }

    void checkNewUser() {

        studentlistRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()) {

                    Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_LONG).show();
                    enterDetails();
                    pop_up_student_info_form();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                enterDetails();
                pop_up_student_info_form();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}


