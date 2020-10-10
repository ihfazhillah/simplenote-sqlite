package com.ihfazh.consumerapp.viewmodels;

import android.app.Application;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ihfazh.consumerapp.helpers.NoteCursorHelper;
import com.ihfazh.consumerapp.models.NoteModel;

import static com.ihfazh.consumerapp.DatabaseContract.NoteColumns.CONTENT_URI;

public class NoteAddEditActivityViewModel extends AndroidViewModel {

    private MutableLiveData<NoteModel> note = new MutableLiveData<>();

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
