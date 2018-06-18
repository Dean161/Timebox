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

import java.util.Calendar;
import java.util.List;

//TODO: add titles to layout file
public class LogActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener{
    protected static TextView displayCurrentStartTime;
    protected static TextView displayCurrentEndTime;
    protected static TextView displayCurrentStartDate;
    protected static TextView displayCurrentEndDate;
    protected static TextView target_duration;

    Button buttonSave;
    private DrawerLayout mDrawerLayout;
    AppDatabase db;
    EditText specActivity;
    TextView start_date;
    TextView end_date;
    TextView start_time;
    TextView end_time;
    EditText inputNotes;
    Spinner categorySpinner;
    int categoryCid;

    //TODO: run sql query (to get all categories) every time the screen is called (not only onCreate) - probably same in AddModCategories
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        //instantiate button_save and Database
        buttonSave = findViewById(R.id.button_save_log);
        db = AppDatabase.getAppDatabase(getApplicationContext());

        //load existing categories into the spinner
        new DatabaseAsyncLoad().execute();

        //disable button_save
        buttonSave.setEnabled(false);


        //instatiate TextViews for dates and times
        displayCurrentStartTime = findViewById(R.id.text_start_time);
        displayCurrentEndTime = findViewById(R.id.text_end_time);
        displayCurrentStartDate = findViewById(R.id.text_start_date);
        displayCurrentEndDate = findViewById(R.id.text_end_date);

        //4 times onClickListener for Date and Time Fields
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
        //final TextView target_duration_title = findViewById(R.id.target_duration_title);
        target_duration = findViewById(R.id.text_target_duration);

        //make TextView for target duration (instantiated above) invisible
        target_duration.setVisibility(View.GONE);
        //target_duration_title.setVisibility(View.GONE);

        //instantiate schedule_swtich
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

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_log);
        setSupportActionBar(toolbar);
        final ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionbar.setTitle(R.string.title_log_activity);

        //onClickListener for schedule swtich
        schedule_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actionbar.getTitle().equals(getResources().getString(R.string.title_log_activity))){
                    actionbar.setTitle(R.string.title_schedule_activity);
                    target_duration.setVisibility(View.VISIBLE);
                    //target_duration_title.setVisibility(View.VISIBLE);
                }
                else{
                    actionbar.setTitle(R.string.title_log_activity);
                    target_duration.setVisibility(View.GONE);
                    //target_duration_title.setVisibility(View.GONE);
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
        start_date = findViewById(R.id.text_start_date);
        end_date = findViewById(R.id.text_end_date);
        start_time = findViewById(R.id.text_start_time);
        end_time = findViewById(R.id.text_end_time);
        inputNotes = findViewById(R.id.text_notes);
        categorySpinner = findViewById(R.id.spinner_category_select_log);

        //onClickListener for save_button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonSave.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                    new DatabaseAsyncGetCid().execute();
                    new DatabaseAsyncInsertLoggedActivity().execute();
                } else {
                    Toast.makeText(getApplicationContext(), "Please choose a category!", Toast.LENGTH_LONG).show();
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
            String startDate = start_date.getText().toString();
            String endDate = end_date.getText().toString();
            String startTime = start_time.getText().toString();
            String endTime = end_time.getText().toString();
            String notes = inputNotes.getText().toString();



            LoggedActivities newAct = new LoggedActivities();
            newAct.setActivityName(newActivity);
            newAct.setCid_fk(categoryCid);
            newAct.setStartDate(startDate);
            newAct.setEndDate(endDate);
            newAct.setStartTime(startTime);
            newAct.setEndTime(endTime);
            newAct.setNotes(notes);

            db.catDao().insertActivity(newAct);

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
            categoryCid = db.catDao().getCidActivites(CategoryToGetCIdFrom);

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
        target_duration.setText("Selected amount: " + String.valueOf(i) + ":" + String.valueOf(i1));
    }

    //method to show the numberPicker
    public void showNumberPicker(View view) {
        NumberPickerDialog newFragment = new NumberPickerDialog();
        newFragment.setValueChangeListener(this);
        newFragment.show(getSupportFragmentManager(), "time picker");
    }

    //TODO: @Dean please specify what this method does :D
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
            displayCurrentStartTime.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
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
            displayCurrentStartDate.setText(String.valueOf(year) + " - " + String.valueOf(month) + " - " + String.valueOf(day));
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
            displayCurrentEndTime.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
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
            displayCurrentEndDate.setText(String.valueOf(year) + " - " + String.valueOf(month) + " - " + String.valueOf(day));
        }
    }
}
