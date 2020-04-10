package com.example.charusatattendance.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class practisedownload extends AppCompatActivity {

    private StorageReference storageReference;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference formsRef=database.getReference("forms");

    private static final String CHANNEL_ID="learn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practisedownload);



    }
}
