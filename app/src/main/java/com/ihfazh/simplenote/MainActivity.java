package com.ihfazh.simplenote;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ihfazh.simplenote.adapters.NoteListAdapter;
import com.ihfazh.simplenote.database.NoteHelper;
import com.ihfazh.simplenote.helpers.NoteCursorHelper;
import com.ihfazh.simplenote.models.NoteModel;
import com.ihfazh.simplenote.viewmodels.MainActivityViewModel;

import java.util.ArrayList;

import static com.ihfazh.simplenote.database.DatabaseContract.NoteColumns.CONTENT_URI;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fabBtn;
    private RecyclerView rvNotes;
    private NoteListAdapter adapter;
    private MainActivityViewModel mainActivityViewModel;
    NoteHelper databaseHelper;

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (databaseHelper != null){
//            databaseHelper.close();
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        databaseHelper = NoteHelper.getInstance(this);
//        databaseHelper.open();


        fabBtn = findViewById(R.id.fabBtn);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteAddEditActivity.class);
                startActivityForResult(intent, NoteAddEditActivity.REQUEST_ADD);

            }
        });

        rvNotes = findViewById(R.id.notes_list);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NoteListAdapter();
        adapter.setCallback(new NoteListAdapter.NoteListClicked() {
            @Override
            public void onClick(int id) {
                Intent intent = new Intent(MainActivity.this, NoteAddEditActivity.class);
                intent.putExtra(NoteAddEditActivity.EDIT_MODE, true);
                intent.putExtra(NoteAddEditActivity.EXTRA_NOTE_ID, id);
                startActivityForResult(intent, NoteAddEditActivity.REQUEST_UPDATE);
            }
        });
        rvNotes.setAdapter(adapter);

        mainActivityViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainActivityViewModel.class);
        mainActivityViewModel.getNotes().observe(this, new Observer<ArrayList<NoteModel>>() {
            @Override
            public void onChanged(ArrayList<NoteModel> noteModels) {
                adapter.setNotes(noteModels);
            }
        });

//        mainActivityViewModel.setNotes(databaseHelper.loadNotes());
        loadNotes();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }

        if (requestCode == NoteAddEditActivity.REQUEST_ADD){
            if (resultCode == NoteAddEditActivity.RESPONSE_ADD){
                snackbarMessage("Note baru telah ditambahkan.");
            }
        } else if (requestCode == NoteAddEditActivity.REQUEST_UPDATE){
            if (resultCode == NoteAddEditActivity.RESPONSE_UPDATE){
                snackbarMessage("Note telah diupdate...");
            } else if (resultCode == NoteAddEditActivity.RESPONSE_DELETE){
                snackbarMessage("Satu Note telah dihapus...");

            }
        }

//        mainActivityViewModel.setNotes(databaseHelper.loadNotes());
        loadNotes();
    }

    private void snackbarMessage(String message) {
        Snackbar.make(rvNotes, message, Snackbar.LENGTH_SHORT).show();
    }

    private void loadNotes(){
        Cursor dataCursor = getApplicationContext().getContentResolver().query(CONTENT_URI, null, null, null);
        ArrayList<NoteModel> notes = NoteCursorHelper.cursorToArraylist(dataCursor);
        mainActivityViewModel.setNotes(notes);
    }
}