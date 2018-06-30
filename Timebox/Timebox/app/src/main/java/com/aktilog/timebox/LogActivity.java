package com.aktilog.timebox;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class LogActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener{
    protected static TextView selected_start_date_time;
    protected static TextView selected_end_date_time;
    protected static TextView title_target_duration;
    protected static TextView selected_target_duration;

    Button buttonSave;
    private DrawerLayout mDrawerLayout;
    AppDatabase db;
    EditText specActivity;
    TextView start_date_time;
    TextView end_date_time;
    EditText inputNotes;
    Spinner categorySpinner;
    TextView target_duration;
    int categoryCid;
    String DEFAULT_CATEGORY_ITEM = "-- Please select category --";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        //instantiate button_save and Database
        buttonSave = findViewById(R.id.button_save_log);
        db = AppDatabase.getAppDatabase(getApplicationContext());

        categorySpinner = findViewById(R.id.spinner_category_select_log);
        //load existing categories into the spinner
        new DatabaseAsyncLoad().execute();

        //disable button_save
        //buttonSave.setEnabled(false);


        //instantiate TextViews for dates and times
        selected_start_date_time = findViewById(R.id.text_start_date_time);
        selected_end_date_time = findViewById(R.id.text_end_date_time);

        //2 times onClickListener for Date and Time Fields
        assert selected_start_date_time != null;
        selected_start_date_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragmentStart startDatePicker = new DatePickerFragmentStart();
                startDatePicker.show(getFragmentManager(), "Select Start Date!");

            }
        });

        assert  selected_end_date_time != null;
        selected_end_date_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragmentEnd endDatePicker = new DatePickerFragmentEnd();
                endDatePicker.show(getFragmentManager(), "Select End Date!");
            }
        });

        title_target_duration = findViewById(R.id.title_target_duration);
        selected_target_duration = findViewById(R.id.text_target_duration);

        //make TextView for target duration (instantiated above) invisible
        title_target_duration.setVisibility(View.GONE);
        selected_target_duration.setVisibility(View.GONE);

        //instantiate schedule_switch
        Switch schedule_switch = findViewById(R.id.switch_schedule_activity);

        mDrawerLayout = findViewById(R.id.drawer_navigation_log);

        NavigationView navigationView = findViewById(R.id.navigation_view_log);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        String title = (String) menuItem.getTitle();
                        if (title.equals(getResources().getString(R.string.title_home))){
                            Intent launch_MainActivity = new Intent(LogActivity.this,MainActivity.class);
                            startActivity(launch_MainActivity);
                        }else if (title.equals(getResources().getString(R.string.title_log_activity))){
                            //do nothing
                        }else if (title.equals(getResources().getString(R.string.title_review))){
                            Intent launch_ReviewActivity = new Intent(LogActivity.this,ReviewActivity.class);
                            startActivity(launch_ReviewActivity);
                        }else if (title.equals(getResources().getString(R.string.title_settings))){
                            Intent launch_SettingsActivity = new Intent(LogActivity.this,SettingsActivity.class);
                            startActivity(launch_SettingsActivity);
                        } else if (title.equals(getResources().getString(R.string.title_checkScheduled_activities))){
                            Intent launch_CheckScheduled = new Intent(LogActivity.this,CheckScheduled.class);
                            startActivity(launch_CheckScheduled);
                        }

                        return true;
                    }
                });

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_log);
        setSupportActionBar(toolbar);
        final ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionbar.setTitle(R.string.title_log_activity);

        //onClickListener for schedule switch
        schedule_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actionbar.getTitle().equals(getResources().getString(R.string.title_log_activity))){
                    actionbar.setTitle(R.string.title_schedule_activity);
                    selected_target_duration.setVisibility(View.VISIBLE);
                    title_target_duration.setVisibility(View.VISIBLE);
                }
                else{
                    actionbar.setTitle(R.string.title_log_activity);
                    selected_target_duration.setVisibility(View.GONE);
                    title_target_duration.setVisibility(View.GONE);
                }
            }
        });

        //onClickListener for add_category floatingActionButton
        FloatingActionButton add_cat = findViewById(R.id.button_add_category);
        add_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launch_AddModCategoriesActivity = new Intent(LogActivity.this,AddModCategories.class);
                startActivity(launch_AddModCategoriesActivity);
            }
        });

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

        //instantiate textViews
        specActivity = findViewById(R.id.text_activity_name);
        start_date_time = findViewById(R.id.text_start_date_time);
        end_date_time = findViewById(R.id.text_end_date_time);
        target_duration = findViewById(R.id.text_target_duration);
        inputNotes = findViewById(R.id.text_notes);

        //TODO: check that if statement with buttonSave.isEnabled
        //onClickListener for save_button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionbar.getTitle().equals(getResources().getString(R.string.title_log_activity))) {
                    if (buttonSave.isEnabled()) {
                        if (!categorySpinner.getSelectedItem().toString().equals(DEFAULT_CATEGORY_ITEM)) {
                            String act_name = specActivity.getText().toString();
                            String start_datetime = start_date_time.getText().toString();
                            String end_datetime = end_date_time.getText().toString();
                            if (!act_name.equals("") && !start_datetime.equals("") && !end_datetime.equals("")) {
                                new DatabaseAsyncGetCid().execute();

                                String startDateTimeToCheck = start_date_time.getText().toString();
                                String endDateTimeToCheck = end_date_time.getText().toString();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                                Date convertedStartDate = new Date();
                                Date convertedEndDate = new Date();
                                try {
                                    convertedStartDate = dateFormat.parse(startDateTimeToCheck);
                                    convertedEndDate = dateFormat.parse(endDateTimeToCheck);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                if (convertedEndDate.after(convertedStartDate)) {
                                    new DatabaseAsyncInsertLoggedActivity().execute();
                                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(LogActivity.this, "Chosen end date is earlier than start date. Please adjust.", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                Toast.makeText(LogActivity.this, "One or more fields are blank", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            //not working???
                            Toast.makeText(getApplicationContext(), "Please choose a category!", Toast.LENGTH_LONG).show();
                        }
                    }
                } else if (actionbar.getTitle().equals(getResources().getString(R.string.title_schedule_activity))) {
                    if (buttonSave.isEnabled()) {
                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                        new DatabaseAsyncGetCid().execute();

                        String startDateTimeToCheck = start_date_time.getText().toString();
                        String endDateTimeToCheck = end_date_time.getText().toString();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                        Date convertedStartDate = new Date();
                        Date convertedEndDate = new Date();
                        try {
                            convertedStartDate = dateFormat.parse(startDateTimeToCheck);
                            convertedEndDate = dateFormat.parse(endDateTimeToCheck);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (convertedEndDate.after(convertedStartDate)) {
                            new DatabaseAsyncInsertScheduledActivity().execute();
                            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(LogActivity.this, "Chosen end date is earlier than start date. Please adjust.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        //not working???
                        Toast.makeText(getApplicationContext(), "Please choose a category!", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        //onItemSelectedListener for category spinner
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                buttonSave.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });
    }

    private class DatabaseAsyncInsertScheduledActivity extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //perform pre-adding operation here
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String newActivity = specActivity.getText().toString();
            String startDateTime = start_date_time.getText().toString();
            String endDateTime = end_date_time.getText().toString();
            String targetDurationString = target_duration.getText().toString();
            String[] targetDurationStringSplitted = targetDurationString.split(" ");
            String[] targetDurationStringSplittedHours =targetDurationStringSplitted[0].split("h");
            String[] targetDurationStringSplittedMin =targetDurationStringSplitted[1].split("m");
            String targetHours = targetDurationStringSplittedHours[0];
            String targetMin = targetDurationStringSplittedMin[0];

            int targetDuration = Integer.parseInt(targetHours)*60 + Integer.parseInt(targetMin);
            String notes = inputNotes.getText().toString();

            ScheduledActivities newAct = new ScheduledActivities();
            newAct.setActivityName(newActivity);
            newAct.setCid_fk(categoryCid);
            newAct.setStartDateTime(startDateTime);
            newAct.setEndDateTime(endDateTime);
            newAct.setTargetDurationInMin(targetDuration);
            newAct.setNotes(notes);
            newAct.setLoggedHours(0);

            db.catDao().insertScheduledActivity(newAct);

            Intent goHome = new Intent(LogActivity.this, MainActivity.class);
            startActivity(goHome);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //perform post-adding operation here
        }
    }
    //opens thread for inserting new activity and calls the insert function from CatDao
    private class DatabaseAsyncInsertLoggedActivity extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //perform pre-adding operation here
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String newActivity = specActivity.getText().toString();
            String startDateTime = start_date_time.getText().toString();
            String endDateTime = end_date_time.getText().toString();
            String notes = inputNotes.getText().toString();

            LoggedActivities newAct = new LoggedActivities();
            newAct.setActivityName(newActivity);
            newAct.setCid_fk(categoryCid);
            newAct.setStartDateTime(startDateTime);
            newAct.setEndDateTime(endDateTime);
            newAct.setNotes(notes);

            db.catDao().insertActivity(newAct);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    categorySpinner.setSelection(0);
                    specActivity.getText().clear();
                    start_date_time.setText(R.string.hint_start_date_time);
                    end_date_time.setText(R.string.hint_end_date_time);
                    inputNotes.getText().clear();
                }
            });

            Intent goHome = new Intent(LogActivity.this, MainActivity.class);
            startActivity(goHome);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //perform post-adding operation here
        }
    }

    //new thread for getting the category id via sql query in CatDao
    private class DatabaseAsyncGetCid extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //perform pre-adding operation here
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String CategoryToGetCIdFrom = categorySpinner.getSelectedItem().toString();
            categoryCid = db.catDao().getCidActivities(CategoryToGetCIdFrom);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //perform post-adding operation here
        }
    }

    //new thread for loading data in the spinner
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

    //method to load data into spinner, called from thread above
    public void loadSpinnerData() {
        //Spinner drop down elements
        List<String> labels = db.catDao().getCatNames();
        labels.add(DEFAULT_CATEGORY_ITEM);
        Collections.sort(labels);

        //creating adapter from spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, labels);

        //drop down layout style
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                //attaching data adapter to spinner
                categorySpinner.setAdapter(dataAdapter);

            }
        });
    }

    //method to get values from the number picker (target_duration)
    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        //Toast.makeText(this, "selected number " + numberPicker.getValue(), Toast.LENGTH_SHORT).show();
        selected_target_duration.setText(String.valueOf(i) + "hr " + String.valueOf(i1) + "min");
    }

    //method to show the numberPicker
    public void showNumberPicker(View view) {
        NumberPickerDialog newFragment = new NumberPickerDialog();
        newFragment.setValueChangeListener(this);
        newFragment.show(getSupportFragmentManager(), "time picker");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
            String start_date = selected_start_date_time.getText().toString();
            selected_start_date_time.setText(start_date + " " + String.format("%02d",hourOfDay) + ":" + String.format("%02d",minute));
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
            selected_start_date_time.setText(String.valueOf(year) + "-" + String.format("%02d",month+1) + "-" + String.format("%02d",day));
            TimePickerStart startTimePicker = new TimePickerStart();
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
            String end_date = selected_end_date_time.getText().toString();
            selected_end_date_time.setText(end_date + " " + String.format("%02d",hourOfDay) + ":" + String.format("%02d",minute));
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
            selected_end_date_time.setText(String.valueOf(year) + "-" + String.format("%02d",month+1) + "-" + String.format("%02d",day));
            TimePickerEnd endTimePicker = new TimePickerEnd();
            endTimePicker.show(getFragmentManager(), "Select End Time!");
        }
    }

    @Override
    protected void onResume() {
        new DatabaseAsyncLoad().execute();
        super.onResume();
    }
}
