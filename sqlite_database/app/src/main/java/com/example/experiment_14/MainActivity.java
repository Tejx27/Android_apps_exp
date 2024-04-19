package com.example.experiment_14;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNote;
    private Button buttonAddNote;
    private ListView listViewNotes;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNote = findViewById(R.id.editTextNote);
        buttonAddNote = findViewById(R.id.buttonAddNote);
        listViewNotes = findViewById(R.id.listViewNotes);

        // Open or create the database
        database = openOrCreateDatabase("NotesDB", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS Notes (id INTEGER PRIMARY KEY AUTOINCREMENT, note TEXT)");

        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });

        displayNotes();
    }

    private void addNote() {
        String noteText = editTextNote.getText().toString().trim();
        if (!noteText.isEmpty()) {
            String query = "INSERT INTO Notes (note) VALUES ('" + noteText + "')";
            database.execSQL(query);
            editTextNote.setText("");
            displayNotes();
        } else {
            Toast.makeText(this, "Please enter a note", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayNotes() {
        ArrayList<String> notesList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM Notes", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String note = cursor.getString(cursor.getColumnIndex("note"));
                notesList.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        listViewNotes.setAdapter(adapter);
    }
}