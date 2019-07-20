package com.hilarysturges.c196;

import java.util.Date;
import java.util.Locale;

public class Term {
    private int _id;
    private String title;
    private java.sql.Date startDate;
    private java.sql.Date endDate;

    public Term(String title, java.sql.Date startDate, java.sql.Date endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Term(int _id, String title, java.sql.Date startDate, java.sql.Date endDate) {
        this._id = _id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public void setStartDate(java.sql.Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(java.sql.Date endDate) {
        this.endDate = endDate;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
