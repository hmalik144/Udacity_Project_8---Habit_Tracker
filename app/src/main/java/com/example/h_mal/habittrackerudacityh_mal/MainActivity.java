package com.example.h_mal.habittrackerudacityh_mal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.h_mal.habittrackerudacityh_mal.HabitContract.HabitEntry;

public class MainActivity extends AppCompatActivity {

    private EditText mNameEditText;

    private EditText mFrequencyEditText;

    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNameEditText = (EditText) findViewById(R.id.autoCompleteTextView);
        mFrequencyEditText = (EditText) findViewById(R.id.editText3);

        mDbHelper = new HabitDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void insertHabit() {
        String nameString = mNameEditText.getText().toString().trim();
        String frequencyString = mFrequencyEditText.getText().toString().trim();

        if (
                TextUtils.isEmpty(nameString) || TextUtils.isEmpty(frequencyString)) {
            Toast.makeText(MainActivity.this, "please insert all data", Toast.LENGTH_SHORT).show();
            return;
        }

        int frequency = Integer.parseInt(frequencyString);

        HabitDbHelper mDbHelper = new HabitDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(HabitEntry.COLUMN_HABIT_NAME, nameString);

        values.put(HabitEntry.COLUMN_HABIT_FREQUENCY, frequency);

        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "New row created", Toast.LENGTH_SHORT).show();
        }
    }



    private void displayDatabaseInfo() {

        HabitDbHelper habitDbHelper = new HabitDbHelper(this);
        Cursor cursor = habitDbHelper.readAllHabits();

        TextView displayView = (TextView) findViewById(R.id.textbox);

        try {
            displayView.setText(cursor.getCount() + " Habits in habits table.\n\n");
            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_HABIT_NAME + " - " +
                    HabitEntry.COLUMN_HABIT_FREQUENCY + "\n");

            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
            int frequencyColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_FREQUENCY);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentFrequency = cursor.getInt(frequencyColumnIndex);
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentFrequency));
            }
        } finally {
            cursor.close();
        }
    }


    public void onClick(View view){
        insertHabit();
        displayDatabaseInfo();
    }


}
