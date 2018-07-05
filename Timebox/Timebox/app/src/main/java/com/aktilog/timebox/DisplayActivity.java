package com.aktilog.timebox;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    //TODO: Handle clicks from other Activities

    TextView display_activity_name;
    TextView display_category_name;
    TextView display_start_date_time;
    TextView display_end_date_time;
    TextView display_notes;
    Button display_edit_activity;
    EditText edit_activity_name;
    Spinner edit_category_name;
    public static TextView edit_start_date_time;
    public static TextView edit_end_date_time;
    EditText edit_notes;
    Button edit_save_activity;
    LoggedActivities clicked_logged_activity_entry;
    AppDatabase app_database;
    LoggedActivities updated_logged_activity = new LoggedActivities();
    int categoryCid;
    List<Category> categoryList;
    Intent calledActivity;
    String activity;

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
        calledActivity = getIntent();
        final String activityName = calledActivity.getStringExtra("ActivityName");

        if (activityName.equals("MainActivity")) {
            clicked_logged_activity_entry = MainActivity.loggedActivities;
            display_category_name.setText(MainActivity.current_category_name);
        } else if(activityName.equals("ReviewText")){
            clicked_logged_activity_entry = ReviewText.loggedActivities;
            display_category_name.setText(ReviewText.current_category_name);
        }

        //clicked_logged_activity_entry = ReviewText.loggedActivities;
        display_activity_name.setText(clicked_logged_activity_entry.getActivityName());
        //display_category_name.setText(ReviewText.current_category_name);
        display_start_date_time.setText(clicked_logged_activity_entry.getStartDateTime());
        display_end_date_time.setText(clicked_logged_activity_entry.getEndDateTime());
        display_notes.setText(clicked_logged_activity_entry.getNotes());

        display_edit_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_save_activity.getVisibility() == View.GONE){
                    edit_activity_name.setText(clicked_logged_activity_entry.getActivityName());
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

                    activity = activityName;
                    new DatabaseAsyncLoad().execute();

                    //2 times onClickListener for Date and Time Fields
                    assert edit_start_date_time != null;
                    edit_start_date_time.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DisplayActivity.DatePickerFragmentStart startDatePicker = new DisplayActivity.DatePickerFragmentStart();
                            startDatePicker.show(getFragmentManager(), "Select Start Date!");

                        }
                    });

                    assert  edit_end_date_time != null;
                    edit_end_date_time.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DisplayActivity.DatePickerFragmentEnd endDatePicker = new DisplayActivity.DatePickerFragmentEnd();
                            endDatePicker.show(getFragmentManager(), "Select End Date!");
                        }
                    });

                    new DatabaseAsyncGetCategories().execute();
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
                if (activityName.equals("ReviewText")){
                    if (!(entered_activity_name.equals(ReviewText.loggedActivities.getActivityName()) && entered_category_name.equals(ReviewText.current_category_name) && entered_start_date_time.equals(ReviewText.loggedActivities.getStartDateTime()) && entered_end_date_time.equals(ReviewText.loggedActivities.getEndDateTime()) && entered_notes.equals(ReviewText.loggedActivities.getNotes()))){
                        categoryCid = getCatIDByName(categoryList,entered_category_name);
                        updated_logged_activity.setLaID(ReviewText.loggedActivities.getLaID());
                        updated_logged_activity.setActivityName(entered_activity_name);
                        updated_logged_activity.setCid_fk(categoryCid);
                        updated_logged_activity.setStartDateTime(entered_start_date_time);
                        updated_logged_activity.setEndDateTime(entered_end_date_time);
                        updated_logged_activity.setNotes(entered_notes);
                        activity = activityName;
                        new DatabaseAsyncUpdateActivity().execute();
                    } else {
                        toggleFromEditToView();
                    }
                } else if (activityName.equals("MainActivity")){
                    if (!(entered_activity_name.equals(MainActivity.loggedActivities.getActivityName()) && entered_category_name.equals(MainActivity.current_category_name) && entered_start_date_time.equals(MainActivity.loggedActivities.getStartDateTime()) && entered_end_date_time.equals(MainActivity.loggedActivities.getEndDateTime()) && entered_notes.equals(MainActivity.loggedActivities.getNotes()))){
                        categoryCid = getCatIDByName(categoryList,entered_category_name);
                        updated_logged_activity.setLaID(MainActivity.loggedActivities.getLaID());
                        updated_logged_activity.setActivityName(entered_activity_name);
                        updated_logged_activity.setCid_fk(categoryCid);
                        updated_logged_activity.setStartDateTime(entered_start_date_time);
                        updated_logged_activity.setEndDateTime(entered_end_date_time);
                        updated_logged_activity.setNotes(entered_notes);
                        activity = activityName;
                        new DatabaseAsyncUpdateActivity().execute();
                    } else {
                        toggleFromEditToView();
                    }
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

            loadSpinnerData(activity);
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //perform post-adding operation here
        }
    }

    //load spinner data
    public void loadSpinnerData(String actName) {
        //Spinner drop down elements
        List<String> labels = app_database.catDao().getCatNames();

        if (actName.equals("MainActivity")){
            labels.remove(MainActivity.current_category_name);
            labels.add(0,MainActivity.current_category_name);
        } else if (actName.equals("ReviewActivity")){
            labels.remove(ReviewText.current_category_name);
            labels.add(0,ReviewText.current_category_name);
        }


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

    private class DatabaseAsyncUpdateActivity extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //perform pre-adding operation here
        }

        @Override
        protected Void doInBackground(Void... voids) {

            updateActivityData();
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //perform post-adding operation here
            if (activity.equals("ReviewText")){
                ReviewText.loggedActivities.setCid_fk(updated_logged_activity.getCid_fk());
                ReviewText.loggedActivities.setActivityName(updated_logged_activity.getActivityName());
                ReviewText.loggedActivities.setStartDateTime(updated_logged_activity.getStartDateTime());
                ReviewText.loggedActivities.setEndDateTime(updated_logged_activity.getEndDateTime());
                ReviewText.loggedActivities.setNotes(updated_logged_activity.getNotes());
            } else if (activity.equals("MainActivity")){
                MainActivity.loggedActivities.setCid_fk(updated_logged_activity.getCid_fk());
                MainActivity.loggedActivities.setActivityName(updated_logged_activity.getActivityName());
                MainActivity.loggedActivities.setStartDateTime(updated_logged_activity.getStartDateTime());
                MainActivity.loggedActivities.setEndDateTime(updated_logged_activity.getEndDateTime());
                MainActivity.loggedActivities.setNotes(updated_logged_activity.getNotes());
            }
            Intent returnBack = new Intent();
            returnBack.putExtra("Result",RESULT_OK);
            setResult(Activity.RESULT_OK,returnBack);
            finish();
        }
    }

    //update the logged activity
    public void updateActivityData() {
        //execute query to update the values

        app_database.catDao().updateLoggedActivity(updated_logged_activity);
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                display_activity_name.setText(updated_logged_activity.getActivityName());
                display_category_name.setText(edit_category_name.getSelectedItem().toString());
                display_start_date_time.setText(updated_logged_activity.getStartDateTime());
                display_end_date_time.setText(updated_logged_activity.getEndDateTime());
                display_notes.setText(updated_logged_activity.getNotes());
                toggleFromEditToView();
                Toast.makeText(DisplayActivity.this, "Updated", Toast.LENGTH_LONG).show();
            }
        });
    }

    //for start time
    public static class TimePickerStart extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
            String start_date = edit_start_date_time.getText().toString();
            edit_start_date_time.setText(start_date + " " + String.format("%02d",hourOfDay) + ":" + String.format("%02d",minute));
            //displayCurrentEndTime.setText("Selected end time: " + String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
        }
    }

    //for start date
    public static class DatePickerFragmentStart extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            edit_start_date_time.setText(String.valueOf(year) + "-" + String.format("%02d",month+1) + "-" + String.format("%02d",day));
            DisplayActivity.TimePickerStart startTimePicker = new DisplayActivity.TimePickerStart();
            startTimePicker.show(getFragmentManager(), "Select Start Time!");
        }
    }

    //for end time
    public static class TimePickerEnd extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
            String end_date = edit_end_date_time.getText().toString();
            edit_end_date_time.setText(end_date + " " + String.format("%02d",hourOfDay) + ":" + String.format("%02d",minute));
        }
    }

    //for end date
    public static class DatePickerFragmentEnd extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            edit_end_date_time.setText(String.valueOf(year) + "-" + String.format("%02d",month+1) + "-" + String.format("%02d",day));
            DisplayActivity.TimePickerEnd endTimePicker = new DisplayActivity.TimePickerEnd();
            endTimePicker.show(getFragmentManager(), "Select End Time!");
        }
    }

    //new thread for getting the category id via sql query in CatDao
    private class DatabaseAsyncGetCategories extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //perform pre-adding operation here
        }

        @Override
        protected Void doInBackground(Void... voids) {
            categoryList = app_database.catDao().getAllCat();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //perform post-adding operation here
        }
    }

    public void toggleFromEditToView(){

        edit_activity_name.setVisibility(View.GONE);
        edit_category_name.setVisibility(View.GONE);
        edit_start_date_time.setVisibility(View.GONE);
        edit_end_date_time.setVisibility(View.GONE);
        edit_notes.setVisibility(View.GONE);
        edit_save_activity.setVisibility(View.GONE);

        display_activity_name.setVisibility(View.VISIBLE);
        display_category_name.setVisibility(View.VISIBLE);
        display_start_date_time.setVisibility(View.VISIBLE);
        display_end_date_time.setVisibility(View.VISIBLE);
        display_notes.setVisibility(View.VISIBLE);
        display_edit_activity.setVisibility(View.VISIBLE);
    }

    private int getCatIDByName(List<Category> category, String category_name){
        for(int i=0;i<category.size();i++){
            if (category.get(i).getCatName().equals(category_name)){
                return category.get(i).getCid();
            }
        }
        return 0;
    }
}
