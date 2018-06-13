package com.aktilog.timebox;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AddModCategories extends AppCompatActivity {

    AppDatabase db;
    EditText inputCat;
    EditText inputHex;
    Button save_category_button;
    Spinner category_sel_spinner;
    TextView category_sel_title;
    ActionBar actionbar;
    Switch add_mod_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mod_categories);

        //build database
        //db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "timeboxDatabase").build();
        db = AppDatabase.getAppDatabase(getApplicationContext());

        category_sel_title = findViewById(R.id.category_select_title);
        category_sel_spinner = findViewById(R.id.category_selector);

        category_sel_spinner.setVisibility(View.GONE);
        category_sel_title.setVisibility(View.GONE);

        Toolbar toolbar_add_mod_cat = findViewById(R.id.toolbar_add_mod_cat);
        setSupportActionBar(toolbar_add_mod_cat);
        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionbar.setTitle(R.string.title_add_category);

        inputCat = findViewById(R.id.category_name);
        inputHex = findViewById(R.id.category_color);
        save_category_button = findViewById(R.id.button_category_save);

        add_mod_switch = findViewById(R.id.modify_switch);

        add_mod_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actionbar.getTitle().equals(getResources().getString(R.string.title_add_category))){
                    actionbar.setTitle(R.string.title_modify_category);
                    category_sel_title.setVisibility(View.VISIBLE);
                    category_sel_spinner.setVisibility(View.VISIBLE);
                    new DatabaseAsyncLoad().execute();
                }else{
                    actionbar.setTitle(R.string.title_add_category);
                    category_sel_spinner.setVisibility(View.GONE);
                    category_sel_title.setVisibility(View.GONE);
                }
            }
        });

        category_sel_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //run sql queries here
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //do nothing
                }
        });


        /*String sel_category = category_sel_spinner.getSelectedItem().toString();
                Category category_data = db.catDao().findByName(sel_category);
                inputCat.setText(category_data.getCatName());
                inputHex.setText(category_data.getHexCode());*/

        save_category_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatabaseAsyncInsert().execute();
            }
        });
    }

    private class DatabaseAsyncInsert extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //perform pre-adding operation here
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String specCat = inputCat.getText().toString();
            String specHex = inputHex.getText().toString();

            Category category = new Category();
            category.setCatName(specCat);
            category.setHexCode(specHex);

            if (actionbar.getTitle().equals(getResources().getString(R.string.title_add_category))) {
                db.catDao().insertAll(category);
                //TODO resolve error msg: Can't toast on a thread that has not called Looper.prepare()
                //Toast.makeText(getApplicationContext(),"Category saved",Toast.LENGTH_SHORT).show();

                //was: clearComposingText();
                inputCat.setText("");
                inputHex.setText("");
            } else {
                String oldCat = category_sel_spinner.getSelectedItem().toString();
                db.catDao().update(specCat, specHex, oldCat);
                //Toast.makeText(getApplicationContext(),"Category updated",Toast.LENGTH_SHORT).show();
                inputCat.clearComposingText();
                inputHex.clearComposingText();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //perform post-adding operation here
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //load spinner data
    public void loadSpinnerData() {
        //Spinner drop down elements
        List<String> labels = db.catDao().getCatNames();

        //creating adapter from spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, labels);

        //drop down layout style
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                //attaching data adapter to spinner
                category_sel_spinner.setAdapter(dataAdapter);

            }
        });
    }
}
