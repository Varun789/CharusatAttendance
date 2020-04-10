package com.example.charusatattendance.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    CardView teacher,student;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    //1.Notification Channnel
    //2.Notification Builder
    //3.Notification Manger

    public static final String CHANNEL_ID = "varun notification id";
    private static final String CHANNEL_NAME = "varunnotification channel";
    private static final String CHANNEL_DESC = "varun notifications";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        teacher=findViewById(R.id.admin);
        student=findViewById(R.id.student);
        firebaseAuth = FirebaseAuth.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }



        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,admin_login_page.class);
                startActivity(i);
                finish();

            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,GoogleLoginStudent.class);
                startActivity(i);
                finish();

            }
        });

    }

    protected void onStart() {
        super.onStart();

        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()!=null) {
                    if (mAuth.getCurrentUser().getProviders().get(0).equals("google.com")) {

                        Intent intent = new Intent(MainActivity.this, Main_student_layout.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(MainActivity.this, admin_main_page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
                }

            };

        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

