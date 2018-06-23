package com.aktilog.timebox;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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
    Spinner spinner_category_review_text;
    Button button_search_review_text;
    AppDatabase app_db_review_text;
    String DEFAULT_VALUE = "---- Please select category ----";
    ListView review_activity_text;


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

        new DatabaseAsyncLoad().execute();

        button_search_review_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatabaseAsyncGetActivity().execute();
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
        labels.add(DEFAULT_VALUE);
        Collections.sort(labels);

        //creating adapter from spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item, labels);

        //drop down layout style
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {

                //attaching data adapter to spinner
                spinner_category_review_text.setAdapter(dataAdapter);

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
        final List<LoggedActivities> logged_activities_list = app_db_review_text.catDao().getLoggedActivities(input_start_datetime_review_text.getText().toString(),input_end_datetime_review_text.getText().toString());

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                review_activity_text.setAdapter(new CustomAdapterReview(getContext(),logged_activities_list));
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

}
