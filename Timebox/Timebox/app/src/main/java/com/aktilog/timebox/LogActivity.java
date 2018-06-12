package com.aktilog.timebox;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.TypedArray;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class LogActivity extends AppCompatActivity {
    protected static TextView displayCurrentStartTime;
    protected static TextView displayCurrentEndTime;
    protected static TextView displayCurrentStartDate;
    protected static TextView displayCurrentEndDate;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        displayCurrentStartTime = findViewById(R.id.start_time);
        displayCurrentEndTime = findViewById(R.id.end_time);
        displayCurrentStartDate = findViewById(R.id.start_date);
        displayCurrentEndDate = findViewById(R.id.end_date);

        //Button displayStartTimeButton = findViewById(R.id.select_start_time);
        //Button displayEndTimeButton = findViewById(R.id.select_end_time);
        //Button displayStartDateButton = findViewById(R.id.select_start_date);
        //Button displayEndDateButton = findViewById(R.id.select_end_date);

        //TODO look up if it is possible to assign a onClickListener to TextView
        assert displayCurrentStartTime != null;
        displayCurrentStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerStart startTimePicker = new TimePickerStart();
                startTimePicker.show(getFragmentManager(), "Select start time!");
            }
        });

        assert displayCurrentEndTime != null;
        displayCurrentEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerEnd endTimePicker = new TimePickerEnd();
                endTimePicker.show(getFragmentManager(), "Select end time!");
            }
        });

        assert  displayCurrentStartDate != null;
        displayCurrentStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragmentStart startDatePicker = new DatePickerFragmentStart();
                startDatePicker.show(getFragmentManager(), "Select start Date!");
            }
        });

        assert displayCurrentEndDate != null;
        displayCurrentEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragmentEnd endDatePicker = new DatePickerFragmentEnd();
                endDatePicker.show(getFragmentManager(), "Select end date!");
            }
        });
        final TextView target_duration_title = findViewById(R.id.target_duration_title);
        final EditText target_duration = findViewById(R.id.target_duration);

        target_duration.setVisibility(View.GONE);
        target_duration_title.setVisibility(View.GONE);

        Switch schedule_switch = findViewById(R.id.schedule_switch);

        mDrawerLayout = findViewById(R.id.nav_drawer_log);

        NavigationView navigationView = findViewById(R.id.nav_view_log);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        String title = (String) menuItem.getTitle();
                        String home = "Home";
                        String log = "Log Activity";
                        String review = "Review Activities";
                        if (title.equals(home)){
                            Intent launch_MainActivity = new Intent(LogActivity.this,MainActivity.class);
                            startActivity(launch_MainActivity);
                        }else if (title.equals(log)){
                            //do nothing
                        }else if (title.equals(review)){
                            Intent launch_ReviewActivity = new Intent(LogActivity.this,ReviewActivity.class);
                            startActivity(launch_ReviewActivity);
                        }else{
                            Intent launch_SettingsActivity = new Intent(LogActivity.this,SettingsActivity.class);
                            startActivity(launch_SettingsActivity);
                        }

                        return true;
                    }
                });

        Toolbar toolbar = findViewById(R.id.toolbar_log);
        setSupportActionBar(toolbar);
        final ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionbar.setTitle(R.string.log_activity_title);

        schedule_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actionbar.getTitle().equals(getResources().getString(R.string.log_activity_title))){
                    actionbar.setTitle(R.string.schedule_activity_title);
                    target_duration.setVisibility(View.VISIBLE);
                    target_duration_title.setVisibility(View.VISIBLE);
                }
                else{
                    actionbar.setTitle(R.string.log_activity_title);
                    target_duration.setVisibility(View.GONE);
                    target_duration_title.setVisibility(View.GONE);
                }
            }
        });

        FloatingActionButton add_cat = findViewById(R.id.add_category);
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
            displayCurrentStartTime.setText("Selected start time: " + String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
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
            displayCurrentStartDate.setText("Selected start date: " + String.valueOf(year) + " - " + String.valueOf(month) + " - " + String.valueOf(day));
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
            displayCurrentEndTime.setText("Selected start time: " + String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
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
            displayCurrentEndDate.setText("Selected start date: " + String.valueOf(year) + " - " + String.valueOf(month) + " - " + String.valueOf(day));
        }
    }
}
