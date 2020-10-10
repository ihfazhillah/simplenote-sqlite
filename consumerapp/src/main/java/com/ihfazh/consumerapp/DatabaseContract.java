package com.ihfazh.consumerapp;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String TABLE_NAME = "notes_note";
    public static final String AUTHORITY = "com.ihfazh.simplenote";
    private static final String SCHEME = "content";

    public static class NoteColumns implements BaseColumns{
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String DATE = "date";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
