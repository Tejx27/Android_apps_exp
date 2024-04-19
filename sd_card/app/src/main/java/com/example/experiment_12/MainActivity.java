package com.example.experiment_12;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button saveButton, loadButton;
    private TextView textView;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize views
        editText = findViewById(R.id.editText);
        saveButton = findViewById(R.id.saveButton);
        loadButton = findViewById(R.id.loadButton);
        textView = findViewById(R.id.textView);

        // Create or open the database
        database = openOrCreateDatabase("MyDatabase", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS MyTable (id INTEGER PRIMARY KEY AUTOINCREMENT, data TEXT)");

        // Save button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = editText.getText().toString().trim();
                if (!data.isEmpty()) {
                    ContentValues values = new ContentValues();
                    values.put("data", data);
                    long result = database.insert("MyTable", null, values);
                    if (result != -1) {
                        Toast.makeText(MainActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                        editText.setText("");
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter some text", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Load button click listener
        loadButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View v) {
                Cursor cursor = database.rawQuery("SELECT * FROM MyTable", null);
                StringBuilder stringBuilder = new StringBuilder();
                if (cursor.moveToFirst()) {
                    do {
                        stringBuilder.append(cursor.getString(cursor.getColumnIndex("data"))).append("\n");
                    } while (cursor.moveToNext());
                    textView.setText(stringBuilder.toString());
                } else {
                    textView.setText("No data found");
                }
                cursor.close();
            }
        });
    }
}
