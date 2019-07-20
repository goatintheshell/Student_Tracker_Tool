package com.hilarysturges.c196;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.sql.Date;
import java.util.ArrayList;


public class DBManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "trackerTool.db";
    public static final String TABLE_TERMS = "terms";
    public static final String COLUMN_ID_T = "idT";
    public static final String COLUMN_TITLE_T = "titleT";
    public static final String COLUMN_START_T = "startT";
    public static final String COLUMN_END_T = "endT";
    public static final String TABLE_COURSES = "courses";
    public static final String COLUMN_ID_C = "idC";
    public static final String COLUMN_TITLE_C = "titleC";
    public static final String COLUMN_START_C = "startC";
    public static final String COLUMN_END_C = "endC";
    public static final String COLUMN_STATUS_C = "statusC";
    public static final String TABLE_ASSESSMENTS = "assessments";
    public static final String COLUMN_ID_A = "idA";
    public static final String COLUMN_NAME_A = "nameA";
    public static final String COLUMN_TYPE_A = "typeA";
    public static final String COLUMN_COURSE_ID_A = "courseIdA";
    public static final String TABLE_INSTRUCTORS = "instructors";
    public static final String COLUMN_ID_I = "idI";
    public static final String COLUMN_NAME_I = "nameI";
    public static final String COLUMN_PHONE_I = "phoneI";
    public static final String COLUMN_EMAIL_I = "emailI";
    public static final String COLUMN_COURSE_ID_I = "courseIdI";


    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        System.out.println("database created");
        String termQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_TERMS + "(" + COLUMN_ID_T + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE_T + " TEXT, "
                + COLUMN_START_T + " DATE, " + COLUMN_END_T + " DATE);";
        sqLiteDatabase.execSQL(termQuery);

        String courseQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_COURSES + "(" + COLUMN_ID_C + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE_C + " TEXT, " + COLUMN_START_C + " DATE, "
                + COLUMN_END_C + " DATE, " + COLUMN_STATUS_C + " TEXT);";
        sqLiteDatabase.execSQL(courseQuery);

        String assessmentQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_ASSESSMENTS + "(" + COLUMN_ID_A + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME_A + " TEXT, " + COLUMN_TYPE_A + " TEXT, " + COLUMN_COURSE_ID_A + " INTEGER, "
                + "FOREIGN KEY (" + COLUMN_COURSE_ID_A + ") REFERENCES " + TABLE_INSTRUCTORS + "(" + COLUMN_ID_C + "));";
        sqLiteDatabase.execSQL(assessmentQuery);

        String instructorQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_INSTRUCTORS + "(" + COLUMN_ID_I + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME_I + " TEXT, "
                + COLUMN_PHONE_I + " TEXT, " + COLUMN_EMAIL_I+ " TEXT, "
                + "FOREIGN KEY (" + COLUMN_COURSE_ID_I + ") REFERENCES " + TABLE_INSTRUCTORS + "(" + COLUMN_ID_C +"));";
        sqLiteDatabase.execSQL(instructorQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TERMS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSESSMENTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTRUCTORS);
        onCreate(sqLiteDatabase);
    }

    public void addTerm(Term term) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TITLE_T, term.getTitle());
        values.put(COLUMN_START_T, String.valueOf(term.getStartDate()));
        values.put(COLUMN_END_T, String.valueOf(term.getEndDate()));

        db.insert(TABLE_TERMS,null, values);
        db.close();

    }

    public void addCourse(Course course) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TITLE_C, course.getTitle());
        values.put(COLUMN_START_C, String.valueOf(course.getStartDate()));
        values.put(COLUMN_END_C, String.valueOf(course.getEndDate()));
        values.put(COLUMN_STATUS_C, course.getStatus());

        db.insert(TABLE_COURSES,null, values);
        db.close();
    }

    public void addAssessment(Assessment assessment) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME_A, assessment.getName());
        values.put(COLUMN_TYPE_A, assessment.getType());

        db.insert(TABLE_ASSESSMENTS,null, values);
        db.close();
    }

    public void addInstructor(Instructor instructor) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME_I, instructor.getName());
        values.put(COLUMN_PHONE_I, instructor.getPhoneNumber());
        values.put(COLUMN_EMAIL_I, instructor.getEmail());

        db.insert(TABLE_INSTRUCTORS,null, values);
        db.close();
    }

    public void removeTerm (int ID) {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList listOfIds = new ArrayList();
        String query = "SELECT idT FROM " + TABLE_TERMS + ";";

        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_ID_T))!=null) {
                listOfIds.add(c.getInt(c.getColumnIndex(COLUMN_ID_T)));

            } c.moveToNext();
        }
        if (listOfIds.contains(ID)) {
            db.execSQL("DELETE FROM " + TABLE_TERMS + " WHERE " + COLUMN_ID_T + " = " + ID + ";");
        } else {System.out.println("ID not found");}
        db.close();
    }

    public void removeCourse (int ID) {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList listOfIds = new ArrayList();
        String query = "SELECT idC FROM " + TABLE_COURSES + ";";

        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_ID_C))!=null) {
                listOfIds.add(c.getInt(c.getColumnIndex(COLUMN_ID_C)));

            } c.moveToNext();
        }
        if (listOfIds.contains(ID)) {
            db.execSQL("DELETE FROM " + TABLE_COURSES + " WHERE " + COLUMN_ID_C + " = " + ID + ";");
        } else {System.out.println("ID not found");}
        db.close();
    }

    public void removeAssessment (int ID) {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList listOfIds = new ArrayList();
        String query = "SELECT idA FROM " + TABLE_ASSESSMENTS + ";";

        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_ID_A))!=null) {
                listOfIds.add(c.getInt(c.getColumnIndex(COLUMN_ID_A)));

            } c.moveToNext();
        }
        if (listOfIds.contains(ID)) {
            db.execSQL("DELETE FROM " + TABLE_ASSESSMENTS + " WHERE " + COLUMN_ID_A + " = " + ID + ";");
        } else {System.out.println("ID not found");}
        db.close();
    }

    public void removeInstructor (int ID) {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList listOfIds = new ArrayList();
        String query = "SELECT idI FROM " + TABLE_INSTRUCTORS + ";";

        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_ID_I))!=null) {
                listOfIds.add(c.getInt(c.getColumnIndex(COLUMN_ID_I)));

            } c.moveToNext();
        }
        if (listOfIds.contains(ID)) {
            db.execSQL("DELETE FROM " + TABLE_INSTRUCTORS + " WHERE " + COLUMN_ID_I + " = " + ID + ";");
        } else {System.out.println("ID not found");}
        db.close();
    }

    public ArrayList<Term> termsToArray() {
        ArrayList<Term> terms = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TERMS + ";";

        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_ID_T))!=null) {
                String startDate = c.getString(c.getColumnIndex(COLUMN_START_T));
                java.sql.Date startDateNew = Date.valueOf(startDate);
                String endDate = c.getString(c.getColumnIndex(COLUMN_END_T));
                java.sql.Date endDateNew = Date.valueOf(endDate);
                Term term = new Term(c.getInt(c.getColumnIndex(COLUMN_ID_T)), c.getString(c.getColumnIndex(COLUMN_TITLE_T)), startDateNew, endDateNew);
                terms.add(term);

            } c.moveToNext();
        }
        db.close();
        return terms;
    }

    public ArrayList<Course> coursesToArray() {
        ArrayList<Course> courses = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_COURSES + ";";

        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_ID_C))!=null) {
                String startDate = c.getString(c.getColumnIndex(COLUMN_START_C));
                java.sql.Date startDateNew = Date.valueOf(startDate);
                String endDate = c.getString(c.getColumnIndex(COLUMN_END_C));
                java.sql.Date endDateNew = Date.valueOf(endDate);
                Course course = new Course(c.getInt(c.getColumnIndex(COLUMN_ID_C)), c.getString(c.getColumnIndex(COLUMN_TITLE_C)), startDateNew, endDateNew, c.getString(c.getColumnIndex(COLUMN_STATUS_C)));
                courses.add(course);

            } c.moveToNext();
        }
        db.close();
        return courses;
    }

    public ArrayList<Assessment> assessmentsToArray() {
        ArrayList<Assessment> assessments = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ASSESSMENTS + ";";

        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_ID_A))!=null) {
               Assessment assessment = new Assessment(c.getInt(c.getColumnIndex(COLUMN_ID_A)), c.getString(c.getColumnIndex(COLUMN_NAME_A)), c.getString(c.getColumnIndex(COLUMN_TYPE_A)));
               assessments.add(assessment);

            } c.moveToNext();
        }
        db.close();
        return assessments;
    }

    public ArrayList<Instructor> instructorsToArray() {
        ArrayList<Instructor> instructors = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_INSTRUCTORS + ";";

        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_ID_I))!=null) {
                Instructor instructor = new Instructor(c.getInt(c.getColumnIndex(COLUMN_ID_I)), c.getString(c.getColumnIndex(COLUMN_NAME_I)), c.getString(c.getColumnIndex(COLUMN_PHONE_I)), c.getString(c.getColumnIndex(COLUMN_EMAIL_I)));
                instructors.add(instructor);

            } c.moveToNext();
        }
        db.close();
        return instructors;
    }

    public Term getLastTerm() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TERMS + " ORDER BY idT DESC LIMIT 1;";

        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        String startDate = c.getString(c.getColumnIndex(COLUMN_START_T));
        java.sql.Date startDateNew = Date.valueOf(startDate);
        String endDate = c.getString(c.getColumnIndex(COLUMN_END_T));
        java.sql.Date endDateNew = Date.valueOf(endDate);
        Term term = new Term(c.getInt(c.getColumnIndex(COLUMN_ID_T)), c.getString(c.getColumnIndex(COLUMN_TITLE_T)), startDateNew, endDateNew);

        return term;

    }

    public Course getLastCourse() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_COURSES + " ORDER BY idC DESC LIMIT 1;";

        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        String startDate = c.getString(c.getColumnIndex(COLUMN_START_C));
        java.sql.Date startDateNew = Date.valueOf(startDate);
        String endDate = c.getString(c.getColumnIndex(COLUMN_END_C));
        java.sql.Date endDateNew = Date.valueOf(endDate);
        Course course = new Course(c.getInt(c.getColumnIndex(COLUMN_ID_C)), c.getString(c.getColumnIndex(COLUMN_TITLE_C)), startDateNew, endDateNew, c.getString(c.getColumnIndex(COLUMN_STATUS_C)));

        return course;

    }

    public Assessment getLastAsessment() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ASSESSMENTS+ " ORDER BY idA DESC LIMIT 1;";

        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        Assessment assessment = new Assessment(c.getInt(c.getColumnIndex(COLUMN_ID_A)), c.getString(c.getColumnIndex(COLUMN_NAME_A)), c.getString(c.getColumnIndex(COLUMN_TYPE_A)));

        return assessment;

    }

    public Instructor getLastInstructor() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_INSTRUCTORS+ " ORDER BY idI DESC LIMIT 1;";

        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        Instructor instructor = new Instructor(c.getInt(c.getColumnIndex(COLUMN_ID_I)), c.getString(c.getColumnIndex(COLUMN_NAME_I)), c.getString(c.getColumnIndex(COLUMN_PHONE_I)), c.getString(c.getColumnIndex(COLUMN_EMAIL_I)));

        return instructor;

    }

    public void updateTerm(int ID, String title, String start, String end) {
        SQLiteDatabase db = getWritableDatabase();
        Date startDate = Date.valueOf(start);
        Date endDate = Date.valueOf(end);
        String query = "UPDATE " + TABLE_TERMS + " SET " + COLUMN_TITLE_T + " = " + "\'" + title + "\'" + ", "
                + COLUMN_START_T + " = " + "\'" + startDate + "\'" + ", " + COLUMN_END_T + " = " + "\'" +  endDate + "\'" + " "
                + "WHERE idT = " + ID + ";";
        db.execSQL(query);
        db.close();
    }

    public void updateCourse(int ID, String title, String start, String end, String status) {
        SQLiteDatabase db = getWritableDatabase();
        Date startDate = Date.valueOf(start);
        Date endDate = Date.valueOf(end);
        String query = "UPDATE " + TABLE_COURSES + " SET " + COLUMN_TITLE_C + " = " + "\'" + title + "\'" + ", "
                + COLUMN_START_C + " = " + "\'" + startDate + "\'" + ", " + COLUMN_END_C + " = " + "\'" +  endDate + "\'" + ","
                + COLUMN_STATUS_C + " = " + "\'" + status + "\'" + " WHERE idC = " + ID + ";";
        db.execSQL(query);
        db.close();
    }

    public void updateAssessment(int ID, String name, String type) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_ASSESSMENTS + " SET " + COLUMN_NAME_A + " = " + "\'" + name + "\'" + ", "
                + COLUMN_TYPE_A + " = " + "\'" + type + "\'" + " WHERE idA = " + ID + ";";
        db.execSQL(query);
        db.close();
    }

    public void updateInstructor(int ID, String name, String phone, String email) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_INSTRUCTORS + " SET " + COLUMN_NAME_I + " = " + "\'" + name + "\'" + ", "
                + COLUMN_PHONE_I + " = " + "\'" + phone + "\'" + ","
                + COLUMN_EMAIL_I + " = " + "\'" + email + "\'"+ " WHERE idA = " + ID + ";";
        db.execSQL(query);
        db.close();
    }

    public void linkAssessmentToCourse(int course_id, int assessment_id) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_ASSESSMENTS + " SET " + COLUMN_COURSE_ID_A + " = "  + course_id
                + " WHERE idA = " + assessment_id + ";";
        db.execSQL(query);
        db.close();
    }
}
