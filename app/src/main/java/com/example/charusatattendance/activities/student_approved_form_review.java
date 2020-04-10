package com.example.charusatattendance.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.charusatattendance.classes.form_pojo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class student_approved_form_review extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    Button upload,choose;
    TextView image_name,email_id,id,name,counsellor,semester,academic_year,type_of_event,event_name,start_date,end_date,start_time,end_time,participated_in,organization,topic_of_work,type_of_participation,info,financial_support;

    String image_url;
    Uri mImageUri;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference studentformsRef = database.getReference("studentforms");
    DatabaseReference formRef = database.getReference("forms");
    FirebaseStorage storage = FirebaseStorage.getInstance();

    StorageReference storageRef = storage.getReference();
    StorageReference storageReference = storageRef.child("uploads");
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseUser user=mAuth.getCurrentUser();
    private StorageTask uploadTask;
    form_pojo formpojo;
    String form_key;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_approved_form_review);
        id=findViewById(R.id.txt_sid);
        counsellor=findViewById(R.id.txt_counselor);
        email_id=findViewById(R.id.txt_emailid);
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
        financial_support=findViewById(R.id.txt_financial_support);
        info=findViewById(R.id.txt_otherinfo);
        upload=findViewById(R.id.btn_upload);
        image_name=findViewById(R.id.txt_image_name);
        choose=findViewById(R.id.btn_choosefile);
        upload=findViewById(R.id.btn_upload);




        Intent i=getIntent();
        form_key=i.getStringExtra("form_key");
        Log.d("formKey:",form_key);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfilechooser();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadfile();

            }
        });

        //Read from database
        studentformsRef.child(user.getUid()).child(form_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                formpojo = dataSnapshot.getValue(form_pojo.class);
                Log.d("Tag apna hai", "Value is: " + formpojo.getSid());
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



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Tag apna hai", "Failed to read value.", error.toException());
            }
        });


    }
    private void openfilechooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent , PICK_IMAGE_REQUEST);

    }
    private String getFileExtension(Uri uri)
    {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData()!=null){
            mImageUri = data.getData();
            //Toast.makeText(this, ""+mImageUri, Toast.LENGTH_SHORT).show();
            image_name.setText(id.getText().toString()+event_name.getText().toString()+"."+getFileExtension(mImageUri));
            //Picasso.with(this).load(mImageUri).into(imageView);

        }
    }

    private void uploadfile()
    {
        Toast.makeText(getApplicationContext(),"uploading",Toast.LENGTH_LONG).show();
        StorageReference fileref = storageReference.child(id.getText().toString()+event_name.getText().toString()+"."+getFileExtension(mImageUri));
        fileref.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                String name = taskSnapshot.getStorage().getName();
                Log.i("namesuccess",name);
                while (!uri.isComplete());
                Uri url = uri.getResult();

                //Upload upload = new Upload(url.toString());
                form_pojo formpojo2 = new form_pojo();
                String image_key=url.toString();
                formpojo2.setImage_name(name);
                formpojo2.setImage_key(url.toString());
//              Upload upload = new Upload(url.toString(),name);

                Log.i("successtag", "onSuccess:");
                formRef.child(form_key).child("image_key").setValue(image_key);
                studentformsRef.child(user.getUid()).child(form_key).child("image_key").setValue(image_key);
                formRef.child(form_key).child("image_name").setValue(name);
                studentformsRef.child(user.getUid()).child(form_key).child("image_name").setValue(name);
                Toast.makeText(student_approved_form_review.this, "successfully uploaded", Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });


    }

}
