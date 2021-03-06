package com.aktilog.timebox;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReviewCalendar extends Fragment {

    CalendarView review_calendar;
    ListView review_activity_calendar;
    String selected_date;
    List<LoggedActivities> logged_activities_calendar;
    AppDatabase app_db_review_calendar;
    List<Category> category_list_calendar;
    public static LoggedActivities loggedActivities;
    public static String current_category_name;
    String myIdentity = "ReviewCalendar";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_review_calendar, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        app_db_review_calendar = AppDatabase.getAppDatabase(getContext().getApplicationContext());
        review_calendar = getView().findViewById(R.id.cview_review_calendar);
        review_activity_calendar = getView().findViewById(R.id.list_activities_calendar);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        selected_date = dateFormat.format(date);
        new DatabaseAsyncGetActivity().execute();

        new DatabaseAsyncGetCatColor().execute();
        review_calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                selected_date = (String.valueOf(i) + "-" + String.format("%02d",i1+1) + "-" + String.format("%02d",i2));
                new DatabaseAsyncGetActivity().execute();
            }
        });

        review_activity_calendar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loggedActivities = (LoggedActivities) parent.getAdapter().getItem(position);
                int cat_id = loggedActivities.getCid_fk();
                current_category_name = getCatNameByID(category_list_calendar,cat_id);
                Intent showActivityDetailsDialog = new Intent(getActivity(),DisplayActivity.class);
                showActivityDetailsDialog.putExtra("ActivityName",myIdentity);
                startActivityForResult(showActivityDetailsDialog,0);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private class DatabaseAsyncGetActivity extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //perform pre-adding operation here
        }

        @Override
        protected Void doInBackground(Void... voids) {
            logged_activities_calendar = app_db_review_calendar.catDao().getLoggedActivitiesCalendar(selected_date);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //header_activity_review_text.setVisibility(View.VISIBLE);
                    review_activity_calendar.setAdapter(new CustomAdapterReview(getContext(),logged_activities_calendar,category_list_calendar));
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

    private class DatabaseAsyncGetCatColor extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //perform pre-adding operation here
        }

        @Override
        protected Void doInBackground(Void... voids) {
            category_list_calendar = app_db_review_calendar.catDao().getAllCat();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //perform post-adding operation here
        }
    }

    private String getCatNameByID(List<Category> category, int cat_id){
        for(int i=0;i<category.size();i++){
            if (category.get(i).getCid() == cat_id){
                return category.get(i).getCatName();
            }
        }
        return "N/A";
    }
}
