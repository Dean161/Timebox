package com.aktilog.timebox;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;
import java.util.ArrayList;
import android.widget.AdapterView;
import android.widget.Toast;

import javax.xml.transform.Templates;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView list_recent = findViewById(R.id.list_view_recent_activities);
        final TextView list_empty = findViewById(R.id.list_view_empty);

        List<LoggedActivities> recent_activity = getListData();
        if (recent_activity.isEmpty())  list_recent.setVisibility(View.GONE);
        else  {
            list_empty.setVisibility(View.GONE);
            list_recent.setAdapter(new CustomAdapter(this, recent_activity));
/*
        list_recent.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = list_recent.getItemAtPosition(position);
                LoggedActivities loggedActivities = (LoggedActivities) o;
                Toast.makeText(MainActivity.this, "Selected :" + " " + loggedActivities, Toast.LENGTH_LONG).show();
            } */
        }

        mDrawerLayout = findViewById(R.id.drawer_navigation_home);

        NavigationView navigationView = findViewById(R.id.navigation_view_home);
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
                            //do nothing
                        }else if (title.equals(getResources().getString(R.string.title_log_activity))){
                            Intent launch_LogActivity = new Intent(MainActivity.this,LogActivity.class);
                            startActivity(launch_LogActivity);
                        }else if (title.equals(getResources().getString(R.string.title_review))){
                            Intent launch_ReviewActivity = new Intent(MainActivity.this,ReviewActivity.class);
                            startActivity(launch_ReviewActivity);
                        }else{
                            Intent launch_SettingsActivity = new Intent(MainActivity.this,SettingsActivity.class);
                            startActivity(launch_SettingsActivity);
                        }

                        return true;
                    }
                });

        Toolbar toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

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

    private  List<LoggedActivities> getListData() {
        List<LoggedActivities> list = new ArrayList<LoggedActivities>();

        LoggedActivities Programming = new LoggedActivities();
        Programming.setActivityName("Programming");
        Programming.setStartDate("2018-06-17");
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
