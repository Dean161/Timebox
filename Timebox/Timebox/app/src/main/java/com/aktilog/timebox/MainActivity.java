package com.aktilog.timebox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView last_ten_list = findViewById(R.id.last_ten_list);

        String[] activities = new String[]{
                "Football",
                "Basketball",
                "Work",
                "Presentation",
                "Timepass",
                "Google",
                "Games",
                "Assignments",
                "Cooking",
                "Sleep"
        };

        final ArrayList<String> activity_list = new ArrayList<String>();
        for (i = 0; i < activities.length; ++i) {
            activity_list.add(activities[i]);
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activity_list);

        last_ten_list.setAdapter(adapter);

    }
}
