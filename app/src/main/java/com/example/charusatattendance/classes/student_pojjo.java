package com.example.charusatattendance.classes;

public class  student_pojjo {

   private String name;
   private String sid;
   private String councellor;
   private String student_email_id;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

   student_pojjo(){

   }
    public student_pojjo( String name, String sid, String councellor, String student_email_id)
    {

        this.name=name;
        this.sid=sid;
        this.councellor=councellor;
        this.student_email_id=student_email_id;

    }
    public String getCouncellor() {
        return councellor;
    }

    public void setCouncellor(String councellor) {
        this.councellor = councellor;
    }

    public String getStudent_email_id() {
        return student_email_id;
    }

    public void setStudent_email_id(String student_email_id) {
        this.student_email_id = student_email_id;
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

}
