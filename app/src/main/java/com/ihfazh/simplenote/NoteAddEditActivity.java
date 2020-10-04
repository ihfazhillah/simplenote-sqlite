package com.ihfazh.simplenote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ihfazh.simplenote.database.NoteHelper;
import com.ihfazh.simplenote.models.NoteModel;
import com.ihfazh.simplenote.viewmodels.NoteAddEditActivityViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.ihfazh.simplenote.database.DatabaseContract.NoteColumns.CONTENT_URI;
import static com.ihfazh.simplenote.database.DatabaseContract.NoteColumns.DATE;
import static com.ihfazh.simplenote.database.DatabaseContract.NoteColumns.DESCRIPTION;
import static com.ihfazh.simplenote.database.DatabaseContract.NoteColumns.TITLE;

public class NoteAddEditActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_ADD = 100 ;
    public static final int RESPONSE_ADD = 101;

    public static final String EXTRA_NOTE = "extra_note";
    public static final String EDIT_MODE = "edit_mode";
    public static final String EXTRA_NOTE_ID = "extra_note_id";

    public static final int REQUEST_UPDATE = 200;
    public static final int RESPONSE_UPDATE = 201;
    public static final int RESPONSE_DELETE = 301;

    private EditText inpTitle, inpDescription;
    private Button btnSave;

    private NoteHelper noteHelper;
    private NoteAddEditActivityViewModel viewModel;
    boolean editMode = false;
    int noteId;
    Uri uriWithId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add_edit);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(NoteAddEditActivityViewModel.class);

        Intent intent = getIntent();
        editMode = intent.getBooleanExtra(EDIT_MODE, false);
        noteId = intent.getIntExtra(EXTRA_NOTE_ID, 0);
        uriWithId = Uri.parse(CONTENT_URI + "/" + noteId);

        String title, btnText;

        if (editMode){
            title = "Update";
            btnText = "Update";
            viewModel.loadNote(noteId);
        } else {
            title = "Create new";
            btnText = "Create new";
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
//            getSupportActionBar().setHomeButtonEnabled(true);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

//        noteHelper = NoteHelper.getInstance(this);
//        noteHelper.open();

        btnSave = findViewById(R.id.btnSave);
        inpTitle = findViewById(R.id.et_title);
        inpDescription = findViewById(R.id.et_description);

        btnSave.setOnClickListener(this);
        btnSave.setText(btnText);

        viewModel.getNote().observe(this, new Observer<NoteModel>() {
            @Override
            public void onChanged(NoteModel noteModel) {
                inpTitle.setText(noteModel.getTitle());
                inpDescription.setText(noteModel.getDescription());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        noteHelper.close();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSave){
            String title = inpTitle.getText().toString().trim();
            String description = inpDescription.getText().toString().trim();
            String date = getDate();

            if (description.isEmpty()){
                inpTitle.setError("Title tidak boleh kosong.");
                return;
            }

            if (editMode){
                ContentValues noteValues = new ContentValues();
                noteValues.put(TITLE, title);
                noteValues.put(DESCRIPTION, description);
                getContentResolver().update(uriWithId, noteValues, null, null);
//                long result = noteHelper.update(noteId, noteValues);

//                if (0 > result){
//                    Toast.makeText(this, "Gagal mengubah data", Toast.LENGTH_LONG).show();
//                    return;
//                }

                NoteModel note = new NoteModel();
//                note.setDate(date);
                note.setTitle(title);
                note.setDescription(description);

                Intent resultIntent = new Intent(NoteAddEditActivity.this, MainActivity.class);
                resultIntent.putExtra(EXTRA_NOTE, note);
                setResult(RESPONSE_UPDATE, resultIntent);
                finish();

            } else {

                ContentValues noteValues = new ContentValues();
                noteValues.put(TITLE, title);
                noteValues.put(DESCRIPTION, description);
                noteValues.put(DATE, date);

//                long result = noteHelper.insert(noteValues);
//
//                if (0 > result){
//                    Toast.makeText(this, "Gagal menambah data", Toast.LENGTH_LONG).show();
//                    return;
//                }

                getContentResolver().insert(CONTENT_URI, noteValues);

                NoteModel note = new NoteModel();
                note.setDate(date);
                note.setTitle(title);
                note.setDescription(description);

                Intent resultIntent = new Intent(NoteAddEditActivity.this, MainActivity.class);
                resultIntent.putExtra(EXTRA_NOTE, note);
                setResult(RESPONSE_ADD, resultIntent);
                finish();

            }
        }

    }

    private String getDate() {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return format.format(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (editMode) {
            getMenuInflater().inflate(R.menu.edit_menu, menu);
            return true;
        } return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_close){
            // create alert dialog
            // if yes, delete and back to root screen
            // if no, do nothing
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Hapus Note")
                    .setMessage("Kamu yakin akan menghapus: "+ inpTitle.getText().toString())
                    .setCancelable(true)
                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            return;
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // hapus
//                            long res = noteHelper.remove(noteId);
                            // back to root screen
//                            if (res > 0) {
                            getContentResolver().delete(uriWithId, null, null);
                                Intent deleteIntent = new Intent(NoteAddEditActivity.this, MainActivity.class);
                                setResult(RESPONSE_DELETE, deleteIntent);
                                finish();
//                            } else {
//                                Toast.makeText(NoteAddEditActivity.this, "Hapus gagal", Toast.LENGTH_LONG).show();
//                            }
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
        } else if (item.getItemId() == android.R.id.home){
            onBackClicked();
        }
        return true;
    }

    private void onBackClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Cancel")
                .setMessage("Kamu yakin gak jadi isi data?")
                .setCancelable(true)
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        return;
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent deleteIntent = new Intent(NoteAddEditActivity.this, MainActivity.class);
//                            setResult(RESPONSE_DELETE, deleteIntent);
                        finish();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        onBackClicked();
    }
}