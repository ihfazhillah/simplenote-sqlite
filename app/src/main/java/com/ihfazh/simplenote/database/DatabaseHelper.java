package com.ihfazh.simplenote.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static android.provider.BaseColumns._ID;
import static com.ihfazh.simplenote.database.DatabaseContract.NoteColumns.DATE;
import static com.ihfazh.simplenote.database.DatabaseContract.NoteColumns.DESCRIPTION;
import static com.ihfazh.simplenote.database.DatabaseContract.NoteColumns.TITLE;
import static com.ihfazh.simplenote.database.DatabaseContract.TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "simplenote";
    public static final int DB_VERSION = 1;

    public static final String SQL_CREATE_TABLE = String.format(
            "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %S TEXT NOT NULL, %S TEXT NOT NULL);",
            TABLE_NAME,
            _ID,
            TITLE,
            DESCRIPTION,
            DATE
    );

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
        onCreate(sqLiteDatabase);
    }
}
