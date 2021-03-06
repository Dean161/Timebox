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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;
import java.util.List;
import java.util.ArrayList;
import android.widget.Toast;
import android.arch.lifecycle.LiveData;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;


import javax.xml.transform.Templates;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    AppDatabase db;
    Category predefinedCat1 = new Category();
    Category predefinedCat2 = new Category();
    Category predefinedCat3 = new Category();
    Category predefinedCat4 = new Category();
    Category predefinedCat5 = new Category();
    Button button_quick_log;

    //List<LoggedActivities> recent_activity = new ArrayList<>();
    List<LoggedActivities> recent_activity;
    List<Category> category_list;
    ListView list_recent;
    TextView list_empty;
    static LoggedActivities loggedActivities;
    static String current_category_name;
    String myIdentity = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getAppDatabase(getApplicationContext());
        new DatabaseAsyncInsertPredefined().execute();
        new DatabaseAsyncGetCatColor().execute();

        list_recent = findViewById(R.id.list_view_recent_activities);
        list_empty = findViewById(R.id.list_view_empty);

        new DatabaseAsyncGetRecent().execute();

        button_quick_log=(Button)findViewById(R.id.button_quick_log);
        button_quick_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launch_LogActivity = new Intent(MainActivity.this,LogActivity.class);
                startActivity(launch_LogActivity);
                //Toast.makeText(MainActivity.this, "YOUR MESSAGE", Toast.LENGTH_LONG).show();
            }
        });

        list_recent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showActivityDetailsDialog = new Intent(MainActivity.this, DisplayActivity.class);
                loggedActivities = (LoggedActivities) parent.getAdapter().getItem(position);
                int cat_id = loggedActivities.getCid_fk();
                current_category_name = getCatNameByID(category_list,cat_id);
                showActivityDetailsDialog.putExtra("ActivityName",myIdentity);
                startActivityForResult(showActivityDetailsDialog,0);
            }
        });


/*
        list_recent.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = list_recent.getItemAtPosition(position);
                LoggedActivities loggedActivities = (LoggedActivities) o;
                Toast.makeText(MainActivity.this, "Selected :" + " " + loggedActivities, Toast.LENGTH_LONG).show();
            }*/
//        }

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
                        }else if (title.equals(getResources().getString(R.string.title_settings))){
                            Intent launch_SettingsActivity = new Intent(MainActivity.this,SettingsActivity.class);
                            startActivity(launch_SettingsActivity);
                        } else if (title.equals(getResources().getString(R.string.title_checkScheduled_activities))){
                            Intent launch_CheckScheduled = new Intent(MainActivity.this,CheckScheduled.class);
                            startActivity(launch_CheckScheduled);
                        }

                        return true;
                    }
                });

        Toolbar toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionbar.setTitle(R.string.title_home);

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
/*
    @Override
    protected void onResume() {
        super.onResume();
        //onDisplay();
        //new DatabaseAsyncGetRecent().execute();
    }
/*
    private void onDisplay() {
        new DatabaseAsyncGetRecent().execute();

        if (recent_activity.isEmpty()) {
            list_recent.setVisibility(View.GONE);
            list_empty.setVisibility(View.VISIBLE);
        }
        else {
            list_recent.setVisibility(View.VISIBLE);
            list_empty.setVisibility(View.GONE);
            list_recent.setAdapter(new CustomAdapter(this, recent_activity,category_list));
            //((BaseAdapter)list_recent.getAdapter()).
            //list_recent.refreshDrawableState();
            //list_recent.invalidate();
        }
    }
/*
    private  List<LoggedActivities> getListData() {
        List<LoggedActivities> list = new ArrayList<LoggedActivities>();

        LoggedActivities Programming = new LoggedActivities();
        Programming.setActivityName("Programming");
        Programming.setStartDateTime("2018-06-17 15:00");
        Programming.setEndDateTime("2018-06-17 17:00");
        Programming.setNotes("Programming TimeBox Application");

        LoggedActivities Football = new LoggedActivities();
        Football.setActivityName("Football");
        Football.setStartDateTime("2018-06-16 20:00");
        Football.setEndDateTime("2018-06-16 22:00");
        Football.setNotes("Watching Worldcup");

        LoggedActivities Dinner = new LoggedActivities();
        Dinner.setActivityName("Dinner");
        Dinner.setStartDateTime("2018-06-15 18:00");
        Dinner.setEndDateTime("2018-06-15 18:00");
        Dinner.setNotes("Having dinner");

        //list.add(Programming);
        //list.add(Football);
        //list.add(Dinner);

        return list;
    }
*/

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
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //insert predefined categories if there are no
    private class DatabaseAsyncInsertPredefined extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //perform pre-adding operation here
        }

        @Override
        protected Void doInBackground(Void... voids) {
            int NoCurrentCat = db.catDao().CountCats();


            if (NoCurrentCat == 0) {
                predefinedCat1.setCatName("Sport");
                predefinedCat1.setHexCode("-65536");

                predefinedCat2.setCatName("University");
                predefinedCat2.setHexCode("-37353");

                predefinedCat3.setCatName("Leisure");
                predefinedCat3.setHexCode("-16760577");

                predefinedCat4.setCatName("Work");
                predefinedCat4.setHexCode("-7657");

                predefinedCat5.setCatName("Holiday");
                predefinedCat5.setHexCode("-4259585");

                db.catDao().insertAll(predefinedCat1);
                db.catDao().insertAll(predefinedCat2);
                db.catDao().insertAll(predefinedCat3);
                db.catDao().insertAll(predefinedCat4);
                db.catDao().insertAll(predefinedCat5);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //perform post-adding operation here
        }
    }
    private class DatabaseAsyncGetRecent extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //perform pre-adding operation here
        }

        @Override
        protected Void doInBackground(Void... voids) {
            recent_activity = db.catDao().getRecentLoggedActivities();

            if (recent_activity.isEmpty()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list_recent.setVisibility(View.GONE);
                        list_empty.setVisibility(View.VISIBLE);
                    }
                });
            }
            else {
                //scheduled_activities_listView.setVisibility(View.GONE);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list_recent.setVisibility(View.VISIBLE);
                        list_empty.setVisibility(View.GONE);
                        list_recent.setAdapter(new CustomAdapter(getApplicationContext(), recent_activity, category_list));
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
            category_list = db.catDao().getAllCat();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK){
            new DatabaseAsyncGetRecent().execute();
        }
    }
}
