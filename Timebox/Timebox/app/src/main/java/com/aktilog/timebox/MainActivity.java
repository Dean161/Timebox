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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int i;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView last_ten_list = findViewById(R.id.list_last_ten_activities);

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
                        String home = "Home";
                        String log = "Log Activity";
                        String review = "Review Activities";
                        if (title.equals(home)){
                            //do nothing
                        }else if (title.equals(log)){
                            Intent launch_LogActivity = new Intent(MainActivity.this,LogActivity.class);
                            startActivity(launch_LogActivity);
                        }else if (title.equals(review)){
                            //Toast.makeText(getApplicationContext(),"Review",Toast.LENGTH_SHORT).show();
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
