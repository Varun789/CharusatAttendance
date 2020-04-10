package com.example.charusatattendance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin_login_page extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    EditText password,email;
    Button admin_signIn;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_page);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("admin");
        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.edt_email);
        password = findViewById(R.id.edt_password);
        admin_signIn = findViewById(R.id.btn_admin_signin);
        firebaseAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(admin_login_page.this, admin_main_page.class));
                }

            }
        };
        if (email.getText() != null && password != null) {
            admin_signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String semail = email.getText().toString();
                    String spassword = password.getText().toString();
                    //Toast.makeText(MainActivity.this, semail +" "+spassword, Toast.LENGTH_SHORT).show();
                    firebaseAuth.signInWithEmailAndPassword(semail, spassword).addOnCompleteListener(admin_login_page.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                //Toast.makeText(MainActivity.this, user.getUid(), Toast.LENGTH_SHORT).show();
                                final String userid = user.getUid();
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot dns : dataSnapshot.getChildren()) {
//                                            dns.getKey();
                                            // Toast.makeText(MainActivity.this,dns.getKey(), Toast.LENGTH_SHORT).show();
                                            if (userid.equals(dns.getKey())) {
                                                Intent intent = new Intent(admin_login_page.this, admin_main_page.class);
                                                startActivity(intent);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
//
                            } else {
                                Toast.makeText(admin_login_page.this, "failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }
        else {
            Toast.makeText(admin_login_page.this, "Give credentials", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth .addAuthStateListener(mAuthListener);
    }
}
