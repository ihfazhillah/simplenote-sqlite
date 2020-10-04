package com.ihfazh.simplenote;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ihfazh.simplenote.models.NoteModel;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fabBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabBtn = findViewById(R.id.fabBtn);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteAddEditActivity.class);
                startActivityForResult(intent, NoteAddEditActivity.REQUEST_ADD);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }

        if (requestCode == NoteAddEditActivity.REQUEST_ADD){
            if (resultCode == NoteAddEditActivity.RESPONSE_ADD){
                NoteModel note = data.getParcelableExtra(NoteAddEditActivity.EXTRA_NOTE);
                snackbarMessage("Ini adalah note: " + note.getTitle());
                return;
            }
        }
    }

    private void snackbarMessage(String message) {
//        Snackbar.make(this, message, Snackbar.LENGTH_SHORT);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}