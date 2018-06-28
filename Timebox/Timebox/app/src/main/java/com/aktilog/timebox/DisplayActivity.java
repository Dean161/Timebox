package com.aktilog.timebox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    TextView display_activity_name;
    TextView display_category_name;
    TextView display_start_date_time;
    TextView display_end_date_time;
    TextView display_notes;
    Button display_edit_activity;
    LoggedActivities clicked_logged_activity_entry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        display_activity_name = findViewById(R.id.text_display_activity_name);
        display_category_name = findViewById(R.id.text_display_category_name);
        display_start_date_time = findViewById(R.id.text_display_start_datetime);
        display_end_date_time = findViewById(R.id.text_display_end_datetime);
        display_notes = findViewById(R.id.text_display_notes);
        display_edit_activity = findViewById(R.id.button_edit_activity);

        clicked_logged_activity_entry = ReviewText.loggedActivities;
        display_activity_name.setText(clicked_logged_activity_entry.getActivityName());
        display_category_name.setText(ReviewText.current_category_name);
        display_start_date_time.setText(clicked_logged_activity_entry.getStartDateTime());
        display_end_date_time.setText(clicked_logged_activity_entry.getEndDateTime());
        display_notes.setText(clicked_logged_activity_entry.getNotes());
    }
}
