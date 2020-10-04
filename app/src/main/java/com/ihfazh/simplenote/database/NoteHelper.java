package com.ihfazh.simplenote.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ihfazh.simplenote.helpers.NoteCursorHelper;
import com.ihfazh.simplenote.models.NoteModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
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

    public ArrayList<NoteModel> loadNotes(){
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, _ID + " DESC");
        ArrayList<NoteModel> notes = NoteCursorHelper.cursorToArraylist(cursor);
        return notes;
    }

    public NoteModel getById(int id) {
        Cursor cursor = database.query(DATABASE_TABLE, null, _ID + " = ?", new String[]{String.valueOf(id)}, null, null, null );
        return NoteCursorHelper.cursorToArraylist(cursor).get(0);
    }

    public long update(int noteId, ContentValues noteValues) {
        return database.update(DATABASE_TABLE, noteValues, _ID + " = ?", new String[]{String.valueOf(noteId)});
    }
}
