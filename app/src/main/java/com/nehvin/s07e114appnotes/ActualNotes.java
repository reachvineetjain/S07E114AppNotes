package com.nehvin.s07e114appnotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.Serializable;

public class ActualNotes extends AppCompatActivity {

    TextView editableTextView;
    private SharedPreferences sharedPreferences;
    int postion = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_notes);

        sharedPreferences = this.getSharedPreferences("com.nehvin.s07e114appnotes", Context.MODE_PRIVATE);

        editableTextView = (EditText) findViewById(R.id.editText);
        Intent noteIntent = getIntent();
        postion = noteIntent.getIntExtra("note",0);
        if (postion != -1) {
            editableTextView.setText(MainActivity.notesList.get(postion));
        }
    }

    @Override
    public void onBackPressed() {
        Log.i("Back button pressed","Back button pressed");
        MainActivity.notesList.remove(postion);
        MainActivity.notesList.add(postion, editableTextView.getText().toString());
        try {
            sharedPreferences.edit().putString("notes", ObjectSerializer.serialize((Serializable) MainActivity.notesList)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainActivity.arrayAdapter.notifyDataSetChanged();
        super.onBackPressed();
    }
}