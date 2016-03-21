package com.tp.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="contact.db";
    private static final String TABLE_NAME="contact";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_NAME="name";
    private static final String COLUMN_UNAME="Uname";
    private static final String COLUMN_PASS="pass";
    private static final String COLUMN_EMAIL="email";
    
    SQLiteDatabase db;
    private static final String TABLE_CREATE= "create table login (id integer primary key not null  , " +
            "uname text not null" + "pass text not null);";



    public DatabaseHelper(Context context)
    {
        super(context , DATABASE_NAME,null,DATABASE_VERSION);
    }

    public void insertContact(Contact c)
    {
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        String query="Select * from"+ TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        int count=cursor.getCount();

        values.put(COLUMN_ID,count);
        values.put(COLUMN_NAME,c.getName());
        values.put(COLUMN_UNAME,c.getUname());
        values.put(COLUMN_PASS,c.getPass());
        values.put(COLUMN_EMAIL,c.getEmail());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public String searchpass(String pwd)
    {
        db=this.getReadableDatabase();
        String query="select pass from" +TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);//cursor is a temporary buffer used to store data from a sql query
        String p;
        p="not found!";
        if (cursor.moveToFirst())
        {
            do{
                p=cursor.getString(0);

                if(p.equals(pwd))
                {
                    break;
                }
            }
            while(cursor.moveToNext());
        }
        return p;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db=db;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query="DROP TABLE IF EXISTS"+ TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }
}
