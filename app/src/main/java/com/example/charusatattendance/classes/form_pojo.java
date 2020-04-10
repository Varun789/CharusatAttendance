package com.example.charusatattendance.classes;

public class form_pojo{

    private String student_key;
    private String form_key;
    private String image_key;
    private  String email_id;
    private String form_status;
    private String year;
    private String sid;
    private String student_name;
    private String semester;
    private String event_name;
    private String start_date;
    private String end_date;
    private String counselor;
    private String participation;
    private String type_of_event;
    private String organization;
    private String topic_of_work;
    private String team;
    private String image_name;
    private String end_time;
    private String support;
    private String information;

    public String getFaculty_feedback() {
        return faculty_feedback;
    }

    public void setFaculty_feedback(String faculty_feedback) {
        this.faculty_feedback = faculty_feedback;
    }

    private String faculty_feedback;


    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }


    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    private String start_time;

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }


    public String getStudent_key() {
        return student_key;
    }

    public void setStudent_key(String student_key) {
        this.student_key = student_key;
    }

    public String getForm_key() {
        return form_key;
    }

    public void setForm_key(String form_key) {
        this.form_key = form_key;
    }

    public String getImage_key() {
        return image_key;
    }

    public void setImage_key(String image_key) {
        this.image_key = image_key;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getForm_status() {
        return form_status;
    }

    public void setForm_status(String form_status) {
        this.form_status = form_status;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
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

    public String getCounselor() {
        return counselor;
    }

    public void setCounselor(String counselor) {
        this.counselor = counselor;
    }

    public String getParticipation() {
        return participation;
    }

    public void setParticipation(String participation) {
        this.participation = participation;
    }

    public String getType_of_event() {
        return type_of_event;
    }

    public void setType_of_event(String type_of_event) {
        this.type_of_event = type_of_event;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getTopic_of_work() {
        return topic_of_work;
    }

    public void setTopic_of_work(String topic_of_work) {
        this.topic_of_work = topic_of_work;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }



    public form_pojo()
    {

    }

    public form_pojo(String image_name,String student_key, String form_key, String image_key, String email_id, String form_status, String year, String sid, String student_name, String semester, String event_name, String start_date, String end_date, String counselor, String participation, String type_of_event, String organization, String topic_of_work, String team, String support, String information,String start_time,String end_time) {
        this.student_key = student_key;
        this.form_key = form_key;
        this.image_key = image_key;
        this.email_id = email_id;
        this.form_status = form_status;
        this.year = year;
        this.sid = sid;
        this.student_name = student_name;
        this.semester = semester;
        this.event_name = event_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.counselor = counselor;
        this.participation = participation;
        this.type_of_event = type_of_event;
        this.organization = organization;
        this.topic_of_work = topic_of_work;
        this.team = team;
        this.support = support;
        this.information = information;
        this.start_time=start_time;
        this.end_time=end_time;
        this.image_name=image_name;
    }
}
