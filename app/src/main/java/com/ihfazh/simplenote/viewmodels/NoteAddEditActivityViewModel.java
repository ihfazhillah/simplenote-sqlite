package com.ihfazh.simplenote.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ihfazh.simplenote.database.NoteHelper;
import com.ihfazh.simplenote.models.NoteModel;

public class NoteAddEditActivityViewModel extends AndroidViewModel {

    private MutableLiveData<NoteModel> note = new MutableLiveData<>();
    private NoteHelper noteHelper;

    public NoteAddEditActivityViewModel(@NonNull Application application) {
        super(application);
        noteHelper = NoteHelper.getInstance(getApplication());
        noteHelper.open();

    }

    public void loadNote(int id){
        NoteModel mNote = noteHelper.getById(id);
        note.postValue(mNote);
    }

    public MutableLiveData<NoteModel> getNote() {
        return note;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        noteHelper.close();
    }
}
