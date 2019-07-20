package com.hilarysturges.c196;

public class Assessment {
    private int _id;
    private String name;
    private String type;

    public Assessment(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Assessment(int _id, String name, String type) {
        this._id = _id;
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
