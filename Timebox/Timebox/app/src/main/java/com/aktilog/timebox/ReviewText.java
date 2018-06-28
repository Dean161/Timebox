package com.aktilog.timebox;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.LauncherActivity;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ReviewText extends Fragment {

    TextView title_start_datetime_review_text;
    TextView title_end_datetime_review_text;
    protected static TextView input_start_datetime_review_text;
    protected static TextView input_end_datetime_review_text;
    TextView title_category_review_text;
    MultiSelectSpinner spinner_category_review_text;
    Button button_search_review_text;
    Button button_clear_review_text;
    Button button_switch_filter_review_text;
    AppDatabase app_db_review_text;
    ListView review_activity_text;
    ArrayAdapter<String> dataAdapter;
    String[] selection;
    List<LoggedActivities> logged_activities_list;
    LinearLayout header_activity_review_text;
    List<Category> category_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_review_text, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        app_db_review_text = AppDatabase.getAppDatabase(getContext().getApplicationContext());
        title_start_datetime_review_text = getView().findViewById(R.id.title_start_datetime_review_text);
        title_end_datetime_review_text = getView().findViewById(R.id.title_end_datetime_review_text);
        input_start_datetime_review_text = getView().findViewById(R.id.text_start_datetime_review_text);
        input_end_datetime_review_text = getView().findViewById(R.id.text_end_datetime_review_text);
        title_category_review_text = getView().findViewById(R.id.title_category_review_text);
        spinner_category_review_text = getView().findViewById(R.id.spinner_category_select_review_text);
        button_search_review_text = getView().findViewById(R.id.button_search_review_text);
        review_activity_text = getView().findViewById(R.id.list_activities_text);
        button_clear_review_text = getView().findViewById(R.id.button_clear_review_text);
        button_switch_filter_review_text = getView().findViewById(R.id.button_switch_filter_review_text);
        header_activity_review_text = getView().findViewById(R.id.header_activity_review_text);

        header_activity_review_text.setVisibility(View.GONE);

        new DatabaseAsyncLoad().execute();
        new DatabaseAsyncGetCatColor().execute();

        button_search_review_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatabaseAsyncGetActivity().execute();
            }
        });

        button_clear_review_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_start_datetime_review_text.setText(R.string.hint_start_date_time);
                input_end_datetime_review_text.setText(R.string.hint_end_date_time);
                spinner_category_review_text.clearText();
            }
        });

        button_switch_filter_review_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button_switch_filter_review_text.getText().toString().equals(getResources().getString(R.string.action_collapse))){
                    collapseFilterOptions();
                } else {
                    expandFilterOptions();
                }
            }
        });

        review_activity_text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LoggedActivities loggedActivities = (LoggedActivities) parent.getAdapter().getItem(position);
                String name = loggedActivities.getActivityName();
                Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
            }
        });

        //2 times onClickListener for Date and Time Fields
        assert input_start_datetime_review_text != null;
        input_start_datetime_review_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewText.DatePickerFragmentStart startDatePicker = new ReviewText.DatePickerFragmentStart();
                startDatePicker.show(getActivity().getFragmentManager(), "Select Start Date!");

            }
        });

        assert  input_end_datetime_review_text != null;
        input_end_datetime_review_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewText.DatePickerFragmentEnd endDatePicker = new ReviewText.DatePickerFragmentEnd();
                endDatePicker.show(getActivity().getFragmentManager(), "Select End Date!");
            }
        });

        super.onViewCreated(view, savedInstanceState);
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

    //method to load data into spinner, called from thread above
    public void loadSpinnerData() {
        //Spinner drop down elements
        List<String> labels = app_db_review_text.catDao().getCatNames();

        //creating adapter from spinner
        dataAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, labels);

        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {

                //attaching data adapter to spinner
                spinner_category_review_text.setAdapter(dataAdapter,false,onSelectedListener);
                spinner_category_review_text.setAllText(getResources().getString(R.string.title_all_categories));
                spinner_category_review_text.setDefaultText(getResources().getString(R.string.hint_select_category));

            }
        });
    }

    private MultiSelectSpinner.MultiSpinnerListener onSelectedListener = new MultiSelectSpinner.MultiSpinnerListener() {
        public void onItemsSelected(boolean[] selected) {
            // Do something here with the selected items
            int i;
            int j = 0;
            selection = new String[selected.length];
            for(i=0;i<selected.length;i++){
                if(selected[i]){
                    selection[j] = dataAdapter.getItem(i);
                    j += 1;
                }
            }
        }
    };

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
            String start_date = input_start_datetime_review_text.getText().toString();
            input_start_datetime_review_text.setText(start_date + " " + String.format("%02d",hourOfDay) + ":" + String.format("%02d",minute));
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
            input_start_datetime_review_text.setText(String.valueOf(year) + "-" + String.format("%02d",month) + "-" + String.format("%02d",day));
            ReviewText.TimePickerStart startTimePicker = new ReviewText.TimePickerStart();
            startTimePicker.show(getActivity().getFragmentManager(), "Select start time!");
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
            String end_date = input_end_datetime_review_text.getText().toString();
            input_end_datetime_review_text.setText(end_date + " " + String.format("%02d",hourOfDay) + ":" + String.format("%02d",minute));
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
            input_end_datetime_review_text.setText(String.valueOf(year) + "-" + String.format("%02d",month) + "-" + String.format("%02d",day));
            ReviewText.TimePickerEnd endTimePicker = new ReviewText.TimePickerEnd();
            endTimePicker.show(getActivity().getFragmentManager(), "Select end time!");
        }
    }

    private class DatabaseAsyncGetActivity extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //perform pre-adding operation here
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String review_start_datetime = input_start_datetime_review_text.getText().toString();
            String review_end_datetime = input_end_datetime_review_text.getText().toString();
            boolean start_datetime_is_null = false;
            boolean end_datetime_is_null = false;
            boolean category_spinner_is_null = false;

            if (review_start_datetime.equals(getResources().getString(R.string.hint_start_date_time))){
                start_datetime_is_null = true;
            }

            if (review_end_datetime.equals(getResources().getString(R.string.hint_end_date_time))){
                end_datetime_is_null = true;
            }

            if (selection == null){
                category_spinner_is_null = true;
            }

            if (!category_spinner_is_null && !start_datetime_is_null && !end_datetime_is_null){
                logged_activities_list = app_db_review_text.catDao().getLoggedActivitiesWithAllFilters(selection,input_start_datetime_review_text.getText().toString(),input_end_datetime_review_text.getText().toString());
            } else if (!category_spinner_is_null && start_datetime_is_null && end_datetime_is_null){
                logged_activities_list = app_db_review_text.catDao().getLoggedActivitiesWithCategories(selection);
            } else if (category_spinner_is_null && !start_datetime_is_null && !end_datetime_is_null){
                logged_activities_list = app_db_review_text.catDao().getLoggedActivitiesWithDates(input_start_datetime_review_text.getText().toString(),input_end_datetime_review_text.getText().toString());
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Please try one of the following:\n 1. Select one or more Categories\n 2. Select both Start and End Date Time\n 3. Select all the above", Toast.LENGTH_LONG).show();
                    }
                });
                return null;
            }


            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    header_activity_review_text.setVisibility(View.VISIBLE);
                    review_activity_text.setAdapter(new CustomAdapterReview(getContext(),logged_activities_list,category_list));
                    collapseFilterOptions();
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //perform post-adding operation here
        }
    }

    private void collapseFilterOptions(){

        title_start_datetime_review_text.setVisibility(View.GONE);
        title_end_datetime_review_text.setVisibility(View.GONE);
        input_start_datetime_review_text.setVisibility(View.GONE);
        input_end_datetime_review_text.setVisibility(View.GONE);
        title_category_review_text.setVisibility(View.GONE);
        spinner_category_review_text.setVisibility(View.GONE);
        button_search_review_text.setVisibility(View.GONE);
        button_clear_review_text.setVisibility(View.GONE);
        button_switch_filter_review_text.setText(R.string.action_expand);

    }

    private void expandFilterOptions(){

        title_start_datetime_review_text.setVisibility(View.VISIBLE);
        title_end_datetime_review_text.setVisibility(View.VISIBLE);
        input_start_datetime_review_text.setVisibility(View.VISIBLE);
        input_end_datetime_review_text.setVisibility(View.VISIBLE);
        title_category_review_text.setVisibility(View.VISIBLE);
        spinner_category_review_text.setVisibility(View.VISIBLE);
        button_search_review_text.setVisibility(View.VISIBLE);
        button_clear_review_text.setVisibility(View.VISIBLE);
        button_switch_filter_review_text.setText(R.string.action_collapse);

    }

    private class DatabaseAsyncGetCatColor extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //perform pre-adding operation here
        }

        @Override
        protected Void doInBackground(Void... voids) {
            category_list = app_db_review_text.catDao().getAllCat();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //perform post-adding operation here
        }
    }

    public class ListEntry{

        LinearLayout linearLayout;
        TextView categoryColor;
        TextView activityName;
        TextView duration;
    }

}
