package com.ihfazh.consumerapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ihfazh.consumerapp.models.NoteModel;

import java.util.ArrayList;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<ArrayList<NoteModel>> notes = new MutableLiveData<>();

    public MutableLiveData<ArrayList<NoteModel>> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<NoteModel> notes) {
//        this.notes = new MutableLiveData<>(notes);
        this.notes.postValue(notes);
    }

}
