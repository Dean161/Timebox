package com.aktilog.timebox;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class scheduledActivitiesStatus extends AppCompatActivity {

    Button increase_button;
    Button decrease_button;
    Button save_button;
    TextView start_date_time;
    TextView end_date_time;
    TextView target_duration;
    TextView logged_hours;
    TextView activity_name;
    AppDatabase db;
    String activityname = CheckScheduled.clickedItem;
    ScheduledActivities updatedScheduledActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_activities_status);

        //assign buttons and TextViews to variables
        increase_button = findViewById(R.id.increase_hours);
        decrease_button = findViewById(R.id.decrease_hours);
        save_button = findViewById(R.id.check_scheduled_save);
        start_date_time = findViewById(R.id.display_start_date_time);
        end_date_time = findViewById(R.id.display_end_date_time);
        target_duration = findViewById(R.id.display_target_duration);
        logged_hours = findViewById(R.id.display_logged_hours);
        activity_name = findViewById(R.id.display_activity_name);

        //get all values
        activity_name.setText(CheckScheduled.clickedItem);
        //activityname = CheckScheduled.clickedItem;
        //activityname = activity_name.getText().toString();

        db = AppDatabase.getAppDatabase(getApplicationContext());
        new DatabaseAsyncTaskGetChosenScheduled().execute();

        //onClickListener increase, decrease and save button
        increase_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int add = 1;

                logged_hours.setText(""+add);

                //String loggedHoursString = logged_hours.getText().toString();
                //int loggedHours = Integer.parseInt(loggedHoursString);
                //loggedHours = loggedHours + 1;
                //loggedHoursString = String
                //logged_hours.setText(loggedHoursString);

            }
        });

        decrease_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int subtract = -1;

                logged_hours.setText(""+subtract);
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(scheduledActivitiesStatus.this, "Updated", Toast.LENGTH_LONG).show();
                new DatabaseAsyncTaskUpdateLoggedHours().execute();
                //close dialog
            }
        });

    }

    private class DatabaseAsyncTaskGetChosenScheduled extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //perform pre-adding operation here
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<ScheduledActivities> scheduledActivitiesCheckScheduled = db.catDao().getChosenScheduled(activityname);
            updatedScheduledActivity = scheduledActivitiesCheckScheduled.get(0);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //perform post-adding operation here
        }
    }

    //Update logged Hours
    private class DatabaseAsyncTaskUpdateLoggedHours extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //perform pre-adding operation here
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String loggedHoursString = logged_hours.getText().toString();
            int loggedHours = Integer.parseInt(loggedHoursString);

            updatedScheduledActivity.setLoggedHours(loggedHours);
            db.catDao().updateLoggedHours(updatedScheduledActivity);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //perform post-adding operation here
        }
    }
}
