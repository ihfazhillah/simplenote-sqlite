package com.ihfazh.simplenote.helpers;

import android.database.Cursor;

import com.ihfazh.simplenote.models.NoteModel;

import java.util.ArrayList;
import java.util.Collection;

import static android.provider.BaseColumns._ID;
import static com.ihfazh.simplenote.database.DatabaseContract.NoteColumns.DATE;
import static com.ihfazh.simplenote.database.DatabaseContract.NoteColumns.DESCRIPTION;
import static com.ihfazh.simplenote.database.DatabaseContract.NoteColumns.TITLE;

public class NoteCursorHelper {
    public static ArrayList<NoteModel> cursorToArraylist(Cursor cursor) {
        ArrayList<NoteModel> notes = new ArrayList<>();
        NoteModel model;

        while (cursor.moveToNext()){
            model = new NoteModel();
            model.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
            model.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
            model.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
            model.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
            notes.add(model);
        }
        return notes;
    }
}
