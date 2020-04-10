package com.example.charusatattendance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class admin_main_page extends AppCompatActivity {
    CardView submitted_form,approved_form,fully_approved,rejected;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_page);
        submitted_form=findViewById(R.id.card_admin_submitted_form);
        approved_form=findViewById(R.id.card_admin_approved);
        fully_approved=findViewById(R.id.card_admin_fully_approved);
        rejected=findViewById(R.id.card_admin_rejected);

        mAuth=FirebaseAuth.getInstance();

        FirebaseMessaging.getInstance().subscribeToTopic("adminNotification");


//Here we create a token and save it.
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            String token = task.getResult().getToken();
                            saveToken(token);
                        }
                    }
                });

        submitted_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_main_page.this, admin_side_submitted_forms.class));
                finish();
            }
        });

        approved_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_main_page.this,admin_aprroved_forms.class));
                finish();
            }
        });

        fully_approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_main_page.this, admin_fully_approved_forms.class));
                finish();
            }
        });

        rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_main_page.this, admin_rejected_forms.class));
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.menu_logout:
                FirebaseAuth.getInstance().signOut();
                Intent i=new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void saveToken(String token) {


        DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference("admin");

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
}
