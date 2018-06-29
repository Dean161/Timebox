package com.aktilog.timebox;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    TextView display_activity_name;
    TextView display_category_name;
    TextView display_start_date_time;
    TextView display_end_date_time;
    TextView display_notes;
    Button display_edit_activity;
    EditText edit_activity_name;
    Spinner edit_category_name;
    EditText edit_start_date_time;
    EditText edit_end_date_time;
    EditText edit_notes;
    Button edit_save_activity;
    LoggedActivities clicked_logged_activity_entry;
    AppDatabase app_database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        //build database
        app_database = AppDatabase.getAppDatabase(getApplicationContext());

        display_activity_name = findViewById(R.id.text_display_activity_name);
        display_category_name = findViewById(R.id.text_display_category_name);
        display_start_date_time = findViewById(R.id.text_display_start_datetime);
        display_end_date_time = findViewById(R.id.text_display_end_datetime);
        display_notes = findViewById(R.id.text_display_notes);
        display_edit_activity = findViewById(R.id.button_edit_activity);

        edit_activity_name = findViewById(R.id.text_edit_activity_name);
        edit_category_name = findViewById(R.id.text_edit_category_name);
        edit_start_date_time = findViewById(R.id.text_edit_start_datetime);
        edit_end_date_time = findViewById(R.id.text_edit_end_datetime);
        edit_notes = findViewById(R.id.text_edit_notes);
        edit_save_activity = findViewById(R.id.button_save_activity);

        edit_activity_name.setVisibility(View.GONE);
        edit_category_name.setVisibility(View.GONE);
        edit_start_date_time.setVisibility(View.GONE);
        edit_end_date_time.setVisibility(View.GONE);
        edit_notes.setVisibility(View.GONE);
        edit_save_activity.setVisibility(View.GONE);

        clicked_logged_activity_entry = ReviewText.loggedActivities;
        display_activity_name.setText(clicked_logged_activity_entry.getActivityName());
        display_category_name.setText(ReviewText.current_category_name);
        display_start_date_time.setText(clicked_logged_activity_entry.getStartDateTime());
        display_end_date_time.setText(clicked_logged_activity_entry.getEndDateTime());
        display_notes.setText(clicked_logged_activity_entry.getNotes());

        display_edit_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_save_activity.getVisibility() == View.GONE){
                    edit_activity_name.setText(clicked_logged_activity_entry.getActivityName());
                    //edit_category_name.setText(ReviewText.current_category_name);
                    edit_start_date_time.setText(clicked_logged_activity_entry.getStartDateTime());
                    edit_end_date_time.setText(clicked_logged_activity_entry.getEndDateTime());
                    edit_notes.setText(clicked_logged_activity_entry.getNotes());

                    edit_activity_name.setVisibility(View.VISIBLE);
                    edit_category_name.setVisibility(View.VISIBLE);
                    edit_start_date_time.setVisibility(View.VISIBLE);
                    edit_end_date_time.setVisibility(View.VISIBLE);
                    edit_notes.setVisibility(View.VISIBLE);
                    edit_save_activity.setVisibility(View.VISIBLE);

                    display_activity_name.setVisibility(View.GONE);
                    display_category_name.setVisibility(View.GONE);
                    display_start_date_time.setVisibility(View.GONE);
                    display_end_date_time.setVisibility(View.GONE);
                    display_notes.setVisibility(View.GONE);
                    display_edit_activity.setVisibility(View.GONE);

                    new DatabaseAsyncLoad().execute();
                }
            }
        });

        edit_save_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entered_activity_name = edit_activity_name.getText().toString();
                String entered_category_name = edit_category_name.getSelectedItem().toString();
                String entered_start_date_time = edit_start_date_time.getText().toString();
                String entered_end_date_time = edit_end_date_time.getText().toString();
                String entered_notes = edit_notes.getText().toString();
                int activity_id = clicked_logged_activity_entry.getLaID();

                if (!(entered_activity_name.equals(ReviewText.loggedActivities.getActivityName()) && entered_category_name.equals(ReviewText.current_category_name) && entered_start_date_time.equals(ReviewText.loggedActivities.getStartDateTime()) && entered_end_date_time.equals(ReviewText.loggedActivities.getEndDateTime()) && entered_notes.equals(ReviewText.loggedActivities.getNotes()))){

                }
            }
        });
    }

    private class DatabaseAsyncLoad extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //perform pre-adding operation here
        }

        @Override
        protected Void doInBackground(Void... voids) {

            loadSpinnerData();
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //perform post-adding operation here
        }
    }

    //load spinner data
    public void loadSpinnerData() {
        //Spinner drop down elements
        List<String> labels = app_database.catDao().getCatNames();

        labels.remove(ReviewText.current_category_name);
        labels.add(0,ReviewText.current_category_name);

        //creating adapter from spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, labels);

        //drop down layout style
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                //attaching data adapter to spinner
                edit_category_name.setAdapter(dataAdapter);

            }
        });
    }
}
