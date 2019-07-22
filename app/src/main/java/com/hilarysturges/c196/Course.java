package com.hilarysturges.c196;

import java.util.ArrayList;
import java.util.Date;

public class Course  {
    private int _id;
    private String title;
    private Date startDate;
    private Date endDate;
    private String status;
    private String notes;
    private ArrayList<Assessment> assessments;
//    private Assessment assessment;
    private Instructor instructor;


    public Course(String title, Date startDate, Date endDate, String status) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Course(int _id, String title, Date startDate, Date endDate, String status) {
        this._id = _id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public Assessment getAssessment() {
//        return assessment;
//    }
//
//    public void setAssessment(Assessment assessment) {
//        this.assessment = assessment;
//    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ArrayList<Assessment> getCourseAssessments() {
        return assessments;
    }

    public void setCourseAssessments(ArrayList<Assessment> assessments) {
        this.assessments = assessments;
    }

    public Assessment getCourseAssessment(int i) {
        return assessments.get(i);
    }

    public void setCourseAssessment(Assessment assessment) {
        this.assessments.add(assessment);
    }
}

