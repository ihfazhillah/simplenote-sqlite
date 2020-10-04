package com.ihfazh.simplenote.viewmodels;

import android.app.Application;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ihfazh.simplenote.database.NoteHelper;
import com.ihfazh.simplenote.helpers.NoteCursorHelper;
import com.ihfazh.simplenote.models.NoteModel;

import static com.ihfazh.simplenote.database.DatabaseContract.NoteColumns.CONTENT_URI;

public class NoteAddEditActivityViewModel extends AndroidViewModel {

    private MutableLiveData<NoteModel> note = new MutableLiveData<>();
    private NoteHelper noteHelper;

    public NoteAddEditActivityViewModel(@NonNull Application application) {
        super(application);
//        noteHelper = NoteHelper.getInstance(getApplication());
//        noteHelper.open();

    }

    public void loadNote(int id){
        Uri uriWithId = Uri.parse(CONTENT_URI + "/" + String.valueOf(id));
        Cursor cursor = getApplication().getApplicationContext().getContentResolver().query(uriWithId, null, null, null);
        NoteModel mNote = NoteCursorHelper.cursorToArraylist(cursor).get(0);
        note.postValue(mNote);
    }

    public MutableLiveData<NoteModel> getNote() {
        return note;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
//        noteHelper.close();
    }
}
