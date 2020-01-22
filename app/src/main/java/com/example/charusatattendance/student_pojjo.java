package com.example.charusatattendance;

public class student_pojjo {


   private String student_key;
   private String name;
   private String sid;
   private String status;
   student_pojjo(){}

    student_pojjo(String student_key,String name,String sid,String status)
    {
        this.student_key=student_key;
        this.name=name;
        this.sid=sid;
        this.status=status;
    }
    public String getStudent_key() {
        return student_key;
    }

    public void setStudent_key(String student_key) {
        this.student_key = student_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
