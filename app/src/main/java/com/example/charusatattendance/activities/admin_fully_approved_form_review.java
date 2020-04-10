package com.example.charusatattendance.activities;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.charusatattendance.classes.form_pojo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import static android.os.Environment.DIRECTORY_DOWNLOADS;


public class admin_fully_approved_form_review extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference studentformsRef = database.getReference("studentforms");
    DatabaseReference formRef = database.getReference("forms");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    TextView image_name,email_id,id,name,counsellor,semester,academic_year,type_of_event,event_name,start_date,end_date,start_time,end_time,participated_in,organization,topic_of_work,type_of_participation,info,financial_support;
    StorageReference storageRef = storage.getReference();
    StorageReference storageReference = storageRef.child("uploads");
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseUser user=mAuth.getCurrentUser();
    private StorageTask uploadTask;
    form_pojo formpojo;
    ImageView download_symbol;
    String form_key;
    StorageReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_fully_approved_form_review);

        id=findViewById(R.id.txt_id);
        counsellor=findViewById(R.id.txt_counselor);
        email_id=findViewById(R.id.txt_emailid);
        name=findViewById(R.id.txt_name);
        semester=findViewById(R.id.txt_sem);
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
        financial_support=findViewById(R.id.txt_financial);
        info=findViewById(R.id.txt_otherinfo);
        image_name=findViewById(R.id.txt_image_name);
        download_symbol=findViewById(R.id.img_download);

        download_symbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });

        Intent i=getIntent();
        form_key=i.getStringExtra("form_key");
        Log.d("formKey:",form_key);

        formRef.child(form_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                formpojo = dataSnapshot.getValue(form_pojo.class);
                email_id.setText(formpojo.getEmail_id());
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
                image_name.setText(formpojo.getImage_name());



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("studentfullyapproved", "Failed to read value.", error.toException());
            }
        });

    }
    public void download()
    {
     formRef.child(form_key).child("image_name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final String imageName = dataSnapshot.getValue(String.class);
                Log.i("image",imageName);


                storageReference = FirebaseStorage.getInstance().getReference().child("uploads");
                //Bring thje name from database
                ref = storageReference.child(imageName);
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url = uri.toString();
                        downloadFiles(admin_fully_approved_form_review.this,imageName,DIRECTORY_DOWNLOADS,url);
                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Tag apna hai", "Failed to read value.", error.toException());
            }
        });


    }
    public void downloadFiles(Context context, String filename, String destinationDirectory, String url){
        DownloadManager downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destinationDirectory,filename);
        downloadManager.enqueue(request);
    }

}
