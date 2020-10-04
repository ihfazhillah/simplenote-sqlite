package com.ihfazh.simplenote;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ihfazh.simplenote.database.NoteHelper;
import com.ihfazh.simplenote.models.NoteModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.ihfazh.simplenote.database.DatabaseContract.NoteColumns.DATE;
import static com.ihfazh.simplenote.database.DatabaseContract.NoteColumns.DESCRIPTION;
import static com.ihfazh.simplenote.database.DatabaseContract.NoteColumns.TITLE;

public class NoteAddEditActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_ADD = 100 ;
    public static final int RESPONSE_ADD = 101;

    public static final String EXTRA_NOTE = "extra_note";

    private EditText inpTitle, inpDescription;
    private Button btnSave;

    private NoteHelper noteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add_edit);

        noteHelper = NoteHelper.getInstance(this);
        noteHelper.open();

        btnSave = findViewById(R.id.btnSave);
        inpTitle = findViewById(R.id.et_title);
        inpDescription = findViewById(R.id.et_description);

        btnSave.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        noteHelper.close();
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

            ContentValues noteValues = new ContentValues();
            noteValues.put(TITLE, title);
            noteValues.put(DESCRIPTION, description);
            noteValues.put(DATE, date);

            long result = noteHelper.insert(noteValues);

            if (0 > result){
                Toast.makeText(this, "Gagal menambah data", Toast.LENGTH_LONG).show();
                return;
            }

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

    private String getDate() {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return format.format(date);
    }
}