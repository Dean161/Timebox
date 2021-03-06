package com.aktilog.timebox;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.LauncherActivity;
import android.app.TimePickerDialog;
import android.graphics.Color;
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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ReviewGraph extends Fragment {

    TextView title_start_datetime_review_graph;
    TextView title_end_datetime_review_graph;
    protected static TextView input_start_datetime_review_graph;
    protected static TextView input_end_datetime_review_graph;
    TextView title_category_review_graph;
    MultiSelectSpinner spinner_category_review_graph;
    Button button_search_review_graph;
    Button button_clear_review_graph;
    Button button_switch_filter_review_graph;
    AppDatabase app_db_review_graph;
    ArrayAdapter<String> dataAdapter;
    String[] selection;
    List<Category> category_list;
    List<LoggedActivities> logged_activities_graph;
    PieChart pieChart;
    ArrayList<PieEntry> yvalues;
    List<Integer> piechart_colors = new ArrayList<>();
    int REQUEST_NAME = 1;
    int REQUEST_COLOR = 2;
    List<String> category_names = new ArrayList<>();
    List<Float> category_duration = new ArrayList<>();
    List<Integer> category_color = new ArrayList<>();
    List<LegendEntry> legend_entries = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_review_graph, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        app_db_review_graph = AppDatabase.getAppDatabase(getContext().getApplicationContext());
        title_start_datetime_review_graph = getView().findViewById(R.id.title_start_datetime_review_graph);
        title_end_datetime_review_graph = getView().findViewById(R.id.title_end_datetime_review_graph);
        input_start_datetime_review_graph = getView().findViewById(R.id.text_start_datetime_review_graph);
        input_end_datetime_review_graph = getView().findViewById(R.id.text_end_datetime_review_graph);
        title_category_review_graph = getView().findViewById(R.id.title_category_review_graph);
        spinner_category_review_graph = getView().findViewById(R.id.spinner_category_select_review_graph);
        button_search_review_graph = getView().findViewById(R.id.button_search_review_graph);
        button_clear_review_graph = getView().findViewById(R.id.button_clear_review_graph);
        button_switch_filter_review_graph = getView().findViewById(R.id.button_switch_filter_review_graph);
        pieChart = getView().findViewById(R.id.piechart_review_graph);
        pieChart.setUsePercentValues(true);
        yvalues = new ArrayList<>();

        new DatabaseAsyncLoad().execute();
        new DatabaseAsyncGetCatColor().execute();



        button_search_review_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatabaseAsyncGetActivity().execute();
            }

        });

        button_clear_review_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_start_datetime_review_graph.setText(R.string.hint_start_date_time);
                input_end_datetime_review_graph.setText(R.string.hint_end_date_time);
                spinner_category_review_graph.clearText();
            }
        });

        button_switch_filter_review_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button_switch_filter_review_graph.getText().toString().equals(getResources().getString(R.string.action_collapse))) {
                    collapseFilterOptions();
                } else {
                    expandFilterOptions();
                }
            }
        });

        //2 times onClickListener for Date and Time Fields
        assert input_start_datetime_review_graph != null;
        input_start_datetime_review_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewGraph.DatePickerFragmentStart startDatePicker = new ReviewGraph.DatePickerFragmentStart();
                startDatePicker.show(getActivity().getFragmentManager(), "Select Start Date!");

            }
        });

        assert  input_end_datetime_review_graph != null;
        input_end_datetime_review_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewGraph.DatePickerFragmentEnd endDatePicker = new ReviewGraph.DatePickerFragmentEnd();
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
        List<String> labels = app_db_review_graph.catDao().getCatNames();

        //creating adapter from spinner
        dataAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, labels);

        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {

                //attaching data adapter to spinner
                spinner_category_review_graph.setAdapter(dataAdapter,false,onSelectedListener);
                spinner_category_review_graph.setAllText(getResources().getString(R.string.title_all_categories));
                spinner_category_review_graph.setDefaultText(getResources().getString(R.string.hint_select_category));

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
            String start_date = input_start_datetime_review_graph.getText().toString();
            input_start_datetime_review_graph.setText(start_date + " " + String.format("%02d",hourOfDay) + ":" + String.format("%02d",minute));
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
            input_start_datetime_review_graph.setText(String.valueOf(year) + "-" + String.format("%02d",month+1) + "-" + String.format("%02d",day));
            ReviewGraph.TimePickerStart startTimePicker = new ReviewGraph.TimePickerStart();
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
            String end_date = input_end_datetime_review_graph.getText().toString();
            input_end_datetime_review_graph.setText(end_date + " " + String.format("%02d",hourOfDay) + ":" + String.format("%02d",minute));
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
            input_end_datetime_review_graph.setText(String.valueOf(year) + "-" + String.format("%02d",month+1) + "-" + String.format("%02d",day));
            ReviewGraph.TimePickerEnd endTimePicker = new ReviewGraph.TimePickerEnd();
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
            String review_start_datetime = input_start_datetime_review_graph.getText().toString();
            String review_end_datetime = input_end_datetime_review_graph.getText().toString();
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
                logged_activities_graph = app_db_review_graph.catDao().getLoggedActivitiesWithAllFiltersOrderCat(selection,input_start_datetime_review_graph.getText().toString(),input_end_datetime_review_graph.getText().toString());
            } else if (!category_spinner_is_null && start_datetime_is_null && end_datetime_is_null){
                logged_activities_graph = app_db_review_graph.catDao().getLoggedActivitiesWithCategoriesOrderCat(selection);
            } else if (category_spinner_is_null && !start_datetime_is_null && !end_datetime_is_null){
                logged_activities_graph = app_db_review_graph.catDao().getLoggedActivitiesWithDatesOrderCat(input_start_datetime_review_graph.getText().toString(),input_end_datetime_review_graph.getText().toString());
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Please try one of the following:\n 1. Select one or more Categories\n 2. Select both Start and End Date Time\n 3. Select all the above", Toast.LENGTH_LONG).show();
                    }
                });
                return null;
            }
            yvalues.clear();
            piechart_colors.clear();
            int j = -1;
            for(int i=0; i<logged_activities_graph.size();i++){
                SimpleDateFormat start_date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                SimpleDateFormat end_date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date start_date = new Date();
                Date end_date = new Date();
                try{
                    start_date = start_date_format.parse(logged_activities_graph.get(i).getStartDateTime());
                    end_date = end_date_format.parse(logged_activities_graph.get(i).getEndDateTime());
                } catch (ParseException e){
                    e.printStackTrace();
                }
                int cat_id = logged_activities_graph.get(i).getCid_fk();
                String cat_name = getCatDetailsByID(category_list, cat_id,REQUEST_NAME);
                int cat_color = Integer.parseInt(getCatDetailsByID(category_list, cat_id,REQUEST_COLOR));
                float duration = ((float)(end_date.getTime() - start_date.getTime())/3600000);
                if(i == 0 || !category_names.get(j).equals(cat_name)){
                    j = j + 1;
                    category_names.add(cat_name);
                    category_duration.add(duration);
                    category_color.add(cat_color);
                } else {
                    float new_duration = category_duration.get(j) + duration;
                    category_duration.set(j,new_duration);
                }
            }

            for (int k=0;k<category_names.size();k++){
                yvalues.add(new PieEntry(category_duration.get(k),category_names.get(k)));
                piechart_colors.add(category_color.get(k));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //perform post-adding operation here
            LegendEntry legendEntry = new LegendEntry();
            legendEntry.label = "";
            legendEntry.formColor = 0;
            legend_entries.add(legendEntry);

            PieDataSet dataSet = new PieDataSet(yvalues,"");
            PieData data = new PieData(dataSet);
            data.setValueFormatter(new PercentFormatter());
            pieChart.setData(data);
            pieChart.invalidate();

            dataSet.setColors(piechart_colors);
            pieChart.setDrawHoleEnabled(true);
            pieChart.setTransparentCircleRadius(30f);
            pieChart.setHoleRadius(30f);
            pieChart.setEntryLabelTextSize(0f);
            Description description = new Description();
            description.setText("");
            pieChart.setDescription(description);

            //data.setValueTextSize(26f);
            data.setValueTextColor(Color.BLACK);
            int colorBlack = Color.parseColor("#000000");
            pieChart.setEntryLabelColor(colorBlack);
            dataSet.setValueTextSize(13f);
            pieChart.getLegendRenderer().computeLegend(data);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    collapseFilterOptions();
                }
            });
        }
    }

    private void collapseFilterOptions(){

        title_start_datetime_review_graph.setVisibility(View.GONE);
        title_end_datetime_review_graph.setVisibility(View.GONE);
        input_start_datetime_review_graph.setVisibility(View.GONE);
        input_end_datetime_review_graph.setVisibility(View.GONE);
        title_category_review_graph.setVisibility(View.GONE);
        spinner_category_review_graph.setVisibility(View.GONE);
        button_search_review_graph.setVisibility(View.GONE);
        button_clear_review_graph.setVisibility(View.GONE);
        button_switch_filter_review_graph.setText(R.string.action_expand);

    }

    private void expandFilterOptions(){

        title_start_datetime_review_graph.setVisibility(View.VISIBLE);
        title_end_datetime_review_graph.setVisibility(View.VISIBLE);
        input_start_datetime_review_graph.setVisibility(View.VISIBLE);
        input_end_datetime_review_graph.setVisibility(View.VISIBLE);
        title_category_review_graph.setVisibility(View.VISIBLE);
        spinner_category_review_graph.setVisibility(View.VISIBLE);
        button_search_review_graph.setVisibility(View.VISIBLE);
        button_clear_review_graph.setVisibility(View.VISIBLE);
        button_switch_filter_review_graph.setText(R.string.action_collapse);

    }

    private class DatabaseAsyncGetCatColor extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //perform pre-adding operation here
        }

        @Override
        protected Void doInBackground(Void... voids) {
            category_list = app_db_review_graph.catDao().getAllCat();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //perform post-adding operation here
        }
    }

    private String getCatDetailsByID(List<Category> category, int cat_id, int request_code){
        for(int i=0;i<category.size();i++){
            if (category.get(i).getCid() == cat_id){
                if (request_code == REQUEST_NAME) {
                    return category.get(i).getCatName();
                } else {
                    return category.get(i).getHexCode();
                }
            }
        }
        return "N/A";
    }

}
