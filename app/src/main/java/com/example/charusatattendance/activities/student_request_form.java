package com.example.charusatattendance.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.charusatattendance.classes.form_pojo;
import com.example.charusatattendance.classes.student_pojjo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class student_request_form extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    EditText btn_start_date_calender,btn_end_date_calender, eventTitle, orgName, workTopic, info,start_time,end_time;
    DatePickerDialog datePickerDialog;
    Spinner acd_year, semester,start_time_amORpm,end_time_amORpm;
    RadioGroup participation_in, type_event, singleORgroup, supportORnot;
    RadioButton selected_participation_in, selected_type_of_event,number_of_participant, selected_support_option;
    Button submit,cancel;
    Dialog mydialog;

    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference formRef = database.getReference("forms");
    DatabaseReference studentRef = database.getReference("students");
    DatabaseReference studentformsRef=database.getReference("studentforms");
    student_pojjo student;
    String academic_year,str_start_time_amORpm,str_end_time_amORpm,str_semester,start_date,end_date,str_start_time,str_end_time,participated_in,type_of_event,title_of_event,name_of_organization,topic_of_work,type_of_participation,support,information;
    Boolean startDateCheck=false;
    Boolean endDateCheck=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_request_form);


        mydialog=new Dialog(this);
        ArrayList<String> am= new ArrayList<String>();
        am.add(" AM ");
        am.add(" PM " );
        ArrayList<String> year= new ArrayList<String>();
        year.add("Choose");
        year.add("2019-20");
        ArrayList<String> semester_list= new ArrayList<String>();
        semester_list.add("Choose");
        semester_list.add("1");
        semester_list.add("2");
        semester_list.add("3");
        semester_list.add("4");
        semester_list.add("5");
        semester_list.add("6");
        semester_list.add("7");
        semester_list.add("8");

        btn_start_date_calender = findViewById(R.id.start_date_calender);
        btn_end_date_calender=findViewById(R.id.end_date_calender);
        eventTitle = findViewById(R.id.edt_event_title);
        orgName = findViewById(R.id.edt_name_of_organization);
        workTopic = findViewById(R.id.edt_topic);
        acd_year = findViewById(R.id.year);
        semester = findViewById(R.id.sem);
        participation_in = findViewById(R.id.participation_in);
        type_event = findViewById(R.id.type_of_event);
        singleORgroup =findViewById(R.id.group);
        supportORnot = findViewById(R.id.support);
        info = findViewById(R.id.edt_info);
        start_time=findViewById(R.id.edt_start_time);
        start_time_amORpm=findViewById(R.id.am_pm_start_time);
        end_time_amORpm=findViewById(R.id.am_pm_end_time);
        end_time=findViewById(R.id.edt_end_time);
        submit = findViewById(R.id.btn_submit);
        cancel=findViewById(R.id.btn_cancel);



        btn_end_date_calender.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                datePickerDialog= new DatePickerDialog(student_request_form.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        btn_end_date_calender.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },2020,3,20);
                endDateCheck=true;
                datePickerDialog.show();

            }
        });
        btn_start_date_calender.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                datePickerDialog= new DatePickerDialog(student_request_form.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        btn_start_date_calender.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },2020,3,20);
                startDateCheck=true;
                datePickerDialog.show();

            }
        });
        participation_in.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selected_participation_in = findViewById(participation_in.getCheckedRadioButtonId());

            }
        });
        type_event.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selected_type_of_event = findViewById(type_event.getCheckedRadioButtonId());
            }
        });
        singleORgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                number_of_participant = findViewById(singleORgroup.getCheckedRadioButtonId());
            }
        });
        supportORnot.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selected_support_option = findViewById(supportORnot.getCheckedRadioButtonId());
            }
        });

        // Read from the database
        studentRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                student = dataSnapshot.getValue(student_pojjo.class);
//                Log.d("CHECK", "Value of name is: " + student.getName());


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("CHECK", "Failed to read value.", error.toException());
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check year
                if(acd_year.getSelectedItem().toString()=="Choose"){
                    Toast.makeText(student_request_form.this, "please enter your academic year ", Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    academic_year = acd_year.getSelectedItem().toString();
                }
                //Check semester
                if(semester.getSelectedItem().toString()=="Choose"){
                    Toast.makeText(student_request_form.this, "please enter semester ", Toast.LENGTH_LONG).show();
                    return;

                }
                else{
                    str_semester = semester.getSelectedItem().toString();
                }

                if(startDateCheck==false)
                {

                    Toast.makeText(student_request_form.this, "please enter start date ", Toast.LENGTH_LONG).show();
                    return;

                }
                else {
                    start_date = btn_start_date_calender.getText().toString();
                }

                if(endDateCheck==false)
                {
                    Toast.makeText(student_request_form.this, "please enter end date ", Toast.LENGTH_LONG).show();
                    return;

                }
                else {
                    end_date = btn_end_date_calender.getText().toString();
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date sdf_start_Date = null;
                try {
                    sdf_start_Date = sdf.parse(start_date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date sdf_end_Date = null;
                try {
                    sdf_end_Date = sdf.parse(end_date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (sdf_start_Date!=null&& sdf_end_Date!=null ) {
                    if (sdf_start_Date.after(sdf_end_Date)) {
                        Toast.makeText(student_request_form.this, "please enter valid dates. ", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                if(orgName.getText().toString().equals(""))
                {
                    Toast.makeText(student_request_form.this, "please enter organization name ", Toast.LENGTH_LONG).show();
                    return;

                }
                else {
                    name_of_organization = orgName.getText().toString();
                }

                if(orgName.getText().toString().equals(""))
                {
                    Toast.makeText(student_request_form.this, "please enter topic of work. ", Toast.LENGTH_LONG).show();
                    return;

                }
                else {
                    topic_of_work = workTopic.getText().toString();
                }

                if(info.getText().toString().equals(""))
                {
                    Toast.makeText(student_request_form.this, "please enter some other information. ", Toast.LENGTH_LONG).show();
                    return;

                }
                else {
                    information = info.getText().toString();
                }

                if (participation_in.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(student_request_form.this, "please select participation. ", Toast.LENGTH_LONG).show();
                    return;

                }
                else
                {
                    participated_in =selected_participation_in.getText().toString();
                }

                if (eventTitle.getText().toString().equals(""))
                {
                    Toast.makeText(student_request_form.this, "please select Event Title. ", Toast.LENGTH_LONG).show();
                    return;

                }
                else
                {
                    title_of_event=eventTitle.getText().toString();
                }

                if (type_event.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(student_request_form.this, "please select type of Event. ", Toast.LENGTH_LONG).show();
                    return;

                }
                else
                {
                type_of_event=selected_type_of_event.getText().toString();
                }

                if (singleORgroup.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(student_request_form.this, "Please select no of participants. ", Toast.LENGTH_LONG).show();
                    return;

                }
                else
                {
                   type_of_participation=number_of_participant.getText().toString();
                }

                if (supportORnot.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(student_request_form.this, "Please select no of participants. ", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                     support=selected_support_option.getText().toString();
                }

                if (start_time.getText().toString().equals(""))
                {
                    Toast.makeText(student_request_form.this, "Please select start time ", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                   str_start_time=start_time.getText().toString();
                }

                if (end_time.getText().toString().isEmpty())
                {
                    Toast.makeText(student_request_form.this, "Please select end time ", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                   str_end_time=end_time.getText().toString();
                }
                if(start_time_amORpm.getSelectedItem().toString()==null){
                    Toast.makeText(student_request_form.this, "please enter your time properly", Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    str_start_time_amORpm = start_time_amORpm.getSelectedItem().toString();
                }
                if(end_time_amORpm.getSelectedItem().toString()==null){
                    Toast.makeText(student_request_form.this, "please enter your time properly", Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    str_end_time_amORpm = end_time_amORpm.getSelectedItem().toString();
                }


                showPopup();
            }
        });



        ArrayAdapter timeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, am){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }

            }

            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.BLACK);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        ArrayAdapter yearAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, year){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }

            };
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };





        ArrayAdapter semesterAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, semester_list){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }

            }      @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        semester.setAdapter(semesterAdapter);
        acd_year.setAdapter(yearAdapter);
        start_time_amORpm.setAdapter(timeAdapter);
        end_time_amORpm.setAdapter(timeAdapter);



    }

    public void showPopup(){


        mydialog.setContentView(R.layout.popup_yes_no);

        Button btn_submit = mydialog.findViewById(R.id.btn_submit);
        Button btn_cancel = mydialog.findViewById(R.id.btn_cancel);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String form_key = formRef.push().getKey();
                Log.i("user_id", user.getEmail());
                form_pojo form = new form_pojo("",user.getUid(), form_key, "", user.getEmail(), "submitted", academic_year, student.getSid(), student.getName(), str_semester, title_of_event, start_date, end_date, student.getCouncellor(), participated_in, type_of_event, name_of_organization, topic_of_work, type_of_participation, support, information, str_start_time+str_start_time_amORpm, str_end_time+str_end_time_amORpm);
                Log.d("TEST", user.getEmail());
                studentformsRef.child(user.getUid()).child(form_key).setValue(form);
                formRef.child(form_key).setValue(form);

                Intent i = new Intent(student_request_form.this, Main_student_layout.class);
                startActivity(i);

                mydialog.dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.show();
    }

    public void setStartTime(View view)
    {
        Calendar calander=Calendar.getInstance();
        int hour=calander.get(Calendar.HOUR);
        int minute=calander.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog;
        timePickerDialog=new TimePickerDialog(student_request_form.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                start_time.setText(hourOfDay+":"+minute);
            }
        },hour,minute,true);
        timePickerDialog.show();
    }
    public void setEndTime(View view)
    {
        Calendar calander=Calendar.getInstance();
        int hour=calander.get(Calendar.HOUR);
        int minute=calander.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog;
        timePickerDialog=new TimePickerDialog(student_request_form.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                end_time.setText(hourOfDay+":"+minute);
            }
        },hour,minute,true);
        timePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }


}
