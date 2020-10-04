package com.ihfazh.simplenote.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.ihfazh.simplenote.database.DatabaseContract.TABLE_NAME;

public class NoteHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static NoteHelper instance;
    private static SQLiteDatabase database;

    public NoteHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static NoteHelper getInstance(Context context) {
        if (instance == null){
            synchronized (SQLiteOpenHelper.class){
                if (instance == null){
                    instance = new NoteHelper(context);
                }
            }
        }
        return instance;
    }

    public void open(){
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();
        if (database.isOpen()){
            database.close();
        }
    }

    public long insert(ContentValues values){
        return database.insert(DATABASE_TABLE, null, values);
    }
}
