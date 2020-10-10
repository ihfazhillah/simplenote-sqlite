package com.ihfazh.consumerapp.helpers;

import android.database.Cursor;

import com.ihfazh.consumerapp.models.NoteModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.ihfazh.consumerapp.DatabaseContract.NoteColumns.DATE;
import static com.ihfazh.consumerapp.DatabaseContract.NoteColumns.DESCRIPTION;
import static com.ihfazh.consumerapp.DatabaseContract.NoteColumns.TITLE;

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
