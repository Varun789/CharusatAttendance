package com.example.charusatattendance;

import java.io.Serializable;
import java.util.Date;

public class Form implements Serializable {
//    public String getYear() {
//        return year;
//    }
//
//    public void setYear(String year) {
//        this.year = year;
//    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStudent_key() {
        return student_key;
    }

    public void setStudent_key(String student_key) {
        this.student_key = student_key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getForm_key() {
        return form_key;
    }

    public void setForm_key(String form_key) {
        this.form_key = form_key;
    }

    //String year;
    String sid;
    String semester;
    String event_name;
    String start_date;
    String end_date;
    String student_key;
    String status;
    String form_key;

    Form(){}

    Form(String sid,String semester,String event_name,String start_date,String end_date,String student_key,String status,String form_key){
        //this.year=year;
        this.sid=sid;
        this.semester=semester;
        this.event_name=event_name;
        this.start_date=start_date;
        this.end_date=end_date;
        this.student_key=student_key;
        this.status=status;
        this.form_key=form_key;
    }
    void updatestatus(String status)
    {
        this.status=status;
    }
}
