package com.aktilog.timebox;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ReviewText extends Fragment {

    //claas variables

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_review_text, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ListView review_activity_text = getView().findViewById(R.id.list_activities_text);
        List<LoggedActivities> list_activity = getListData();
        review_activity_text.setAdapter(new CustomAdapterReview(getContext(),list_activity));

        //database and spinner assignment

        //new DatabaseAsyncLoad.execute

        super.onViewCreated(view, savedInstanceState);
    }

    private List<LoggedActivities> getListData() {
        List<LoggedActivities> list = new ArrayList<LoggedActivities>();

        LoggedActivities Programming = new LoggedActivities();
        Programming.setActivityName("Programming");
        Programming.setStartDate("2018-06-17");
        Programming.setEndDate("2018-06-17");
        Programming.setStartTime("15h00");
        Programming.setEndTime("17h00");
        Programming.setNotes("Programming TimeBox Application");

        LoggedActivities Football = new LoggedActivities();
        Football.setActivityName("Football");
        Football.setStartDate("2018-06-16");
        Football.setStartTime("20h00");
        Football.setEndTime("22h00");
        Football.setNotes("Watching Worldcup");

        LoggedActivities Dinner = new LoggedActivities();
        Dinner.setActivityName("Dinner");
        Dinner.setStartDate("2018-06-15");
        Dinner.setStartTime("18h00");
        Dinner.setEndTime("19h00");
        Dinner.setNotes("Having dinner");

        list.add(Programming);
        list.add(Football);
        list.add(Dinner);
        list.add(Dinner);

        return list;

    }

    //databaseasync

    //loadspinnerdata
}
