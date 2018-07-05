package com.aktilog.timebox;

import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.ListView;

import java.util.List;

//TODO: update view after closing dialog (show updated amount of logged hours)
public class CheckScheduled extends AppCompatActivity {

    AppDatabase app_database;
    private DrawerLayout mDrawerLayout;
    ListView scheduled_activities_listView;
    List<Category> category_list;
    static public String clickedItem;
    static public ScheduledActivities clickedActivity;
    public static final int REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_scheduled);

        //build database
        app_database = AppDatabase.getAppDatabase(getApplicationContext());

        //get category list
        new DatabaseAsyncGetCatColor().execute();

        //setting up custom toolbar for the activity
        Toolbar toolbar_checkScheduled = findViewById(R.id.toolbar_checkScheduled);
        setSupportActionBar(toolbar_checkScheduled);
        final ActionBar actionbar_categories = getSupportActionBar();
        actionbar_categories.setDisplayHomeAsUpEnabled(true);
        actionbar_categories.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionbar_categories.setTitle(R.string.title_checkScheduled_activities);

        //Navigation
        mDrawerLayout = findViewById(R.id.drawer_navigation_checkScheduled);

        NavigationView navigationView = findViewById(R.id.navigation_view_checkScheduled);
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
                            Intent launch_MainActivity = new Intent(CheckScheduled.this,MainActivity.class);
                            startActivity(launch_MainActivity);
                        }else if (title.equals(getResources().getString(R.string.title_log_activity))){
                            Intent launch_LogActivity = new Intent(CheckScheduled.this,LogActivity.class);
                            startActivity(launch_LogActivity);
                        }else if (title.equals(getResources().getString(R.string.title_review))){
                            Intent launch_ReviewActivity = new Intent(CheckScheduled.this,ReviewActivity.class);
                            startActivity(launch_ReviewActivity);
                        }else if (title.equals(getResources().getString(R.string.title_settings))){
                            Intent launch_SettingsActivity = new Intent(CheckScheduled.this,SettingsActivity.class);
                            startActivity(launch_SettingsActivity);
                        } else if (title.equals(getResources().getString(R.string.title_checkScheduled_activities))){
                            //do nothing
                        }

                        return true;
                    }
                });

        //ListView
        scheduled_activities_listView = findViewById(R.id.list_scheduled_activities);

        new DatabaseAsyncGetActivity().execute();

        scheduled_activities_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedActivity = (ScheduledActivities) parent.getAdapter().getItem(position);
                clickedItem = clickedActivity.getActivityName();
                //clickedItem = parent.getItemAtPosition(position).toString();
                Intent showDetailDialog = new Intent(CheckScheduled.this, ScheduledActivitiesStatus.class);
                //startActivity(showDetailDialog);
                startActivityForResult(showDetailDialog, REQUEST_CODE);
            }
        });

    }

    private class DatabaseAsyncGetActivity extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //perform pre-adding operation here
        }

        @Override
        protected Void doInBackground(Void... voids) {
            final List<ScheduledActivities> scheduled_activities_list = app_database.catDao().getScheduledActivities();

            if (scheduled_activities_list.isEmpty()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scheduled_activities_listView.setVisibility(View.GONE);
                    }
                });

            }
            else {
                //scheduled_activities_listView.setVisibility(View.GONE);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scheduled_activities_listView.setAdapter(new CustomAdapterCheckScheduled(getApplicationContext(), scheduled_activities_list, category_list));
                    }
                });
            }
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
            category_list = app_database.catDao().getAllCat();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //perform post-adding operation here
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            new DatabaseAsyncGetActivity().execute();
        }
    }
}
