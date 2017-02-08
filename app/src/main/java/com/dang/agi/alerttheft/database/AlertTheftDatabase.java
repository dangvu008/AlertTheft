package com.dang.agi.alerttheft.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.dang.agi.alerttheft.Accounts;

/**
 * Created by DANG on 2/8/2017.
 */

public class AlertTheftDatabase extends SQLiteOpenHelper {
    private Context context;
    private SQLiteDatabase database;
    private Cursor cursor;
    public static final String DATABASE_NAME = "ALERT_THEFT";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_ALERT = "Alert";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_PASSWORD = "passwords";
    public static final String COLUMN_QUESTION ="question";
    public static final String COLUMN_ANSWER ="answer";
    public static final String CREATE_TABLE = " CREATE TABLE " + TABLE_ALERT + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + COLUMN_EMAIL + " TEXT ,"
            + COLUMN_PHONE + " TEXT,"
            + COLUMN_PASSWORD + " TEXT,"
            +COLUMN_QUESTION +" TEXT ,"
            +COLUMN_ANSWER +" TEXT )";

    public AlertTheftDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ALERT);
        onCreate(sqLiteDatabase);
    }

    public boolean createAccount(Accounts account) {
        boolean result = false;
      try {
          database = this.getWritableDatabase();
          ContentValues values = new ContentValues();
          values.put(COLUMN_EMAIL, account.getEmail());
          values.put(COLUMN_PHONE, account.getPhone());
          values.put(COLUMN_PASSWORD, account.getPassword());
          values.put(COLUMN_QUESTION,account.getQuestion());
          values.put(COLUMN_ANSWER,account.getAnswer());
          int i = (int) database.insert(TABLE_ALERT, null, values);
          if (i != -1)
              result = true;
      }catch (SQLiteException ex){
          ex.printStackTrace();
      }finally {
          database.close();
      }
        return result;
    }
    public boolean updateAccount(Accounts account){
        boolean result = false;
        try {
            database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_EMAIL, account.getEmail());
            values.put(COLUMN_PHONE, account.getPhone());
            values.put(COLUMN_PASSWORD, account.getPassword());
            values.put(COLUMN_QUESTION,account.getQuestion());
            values.put(COLUMN_ANSWER,account.getAnswer());
            int i = (int) database.update(TABLE_ALERT,values,"id = ?",new String[]{String.valueOf(account.getId())});
            if (i != -1)
                result = true;
        }catch (SQLiteException ex)
        {
            ex.printStackTrace();
        }finally {
            database.close();
        }
        return result;
    }
    public boolean updatePassword(String password,int id){
        boolean result = false;
        try {
            database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_PASSWORD, password);
            int i = (int) database.update(TABLE_ALERT,values,"id = ?",new String[]{String.valueOf(id)});
            if (i != -1)
                result = true;
        }catch (SQLiteException ex)
        {
            ex.printStackTrace();
        }finally {
            database.close();
        }
        return result;
    }
    public boolean login(String password){
        boolean result = false;
        database = this.getReadableDatabase();
        try {
            cursor = database.query(TABLE_ALERT,new String[]{COLUMN_ID},COLUMN_PASSWORD+" = ?",
                    new String[]{password},null,null,null);
            if (cursor.moveToFirst())
                result = true;
            cursor.close();
        }catch (SQLiteException ex){
            ex.printStackTrace();
        }finally {
            database.close();
        }
        return result;
    }
    public String getPasswordForgot(String question,String answer){
        String pass = "";
        database = this.getReadableDatabase();
        try {
            cursor = database.query(TABLE_ALERT,new String[]{COLUMN_PASSWORD},COLUMN_QUESTION+" = ? AND "
                    +COLUMN_ANSWER+ " =? ",
                    new String[]{question,answer},null,null,null);
            if (cursor.moveToFirst())
                pass = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            cursor.close();
        }catch (SQLiteException ex){
            ex.printStackTrace();
        }finally {
            database.close();
        }
        return pass;
    }
}
