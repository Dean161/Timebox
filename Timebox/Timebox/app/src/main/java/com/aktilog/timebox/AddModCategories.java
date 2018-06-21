package com.aktilog.timebox;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
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

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.Collections;
import java.util.List;

public class AddModCategories extends AppCompatActivity {

    AppDatabase app_database;
    EditText input_category;
    Button input_color;
    Button save_category_button;
    Spinner category_sel_spinner;
    TextView category_sel_title;
    ActionBar actionbar_categories;
    Switch add_mod_switch;
    int default_color;
    String input_hex;
    private int CURRENT_BACKGROUND_COLOR = 0xFFFFFFFF;
    private String OK = "ok";
    private String CANCEL = "cancel";
    Boolean item_selected;
    String DEFAULT_CATEGORY_ITEM = "----------- Please select category -----------";
    String COLOR_TEXT = "#000000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mod_categories);

        //build database
        app_database = AppDatabase.getAppDatabase(getApplicationContext());

        //on page load do not display the spinner and category title
        category_sel_title = findViewById(R.id.title_category_select);
        category_sel_spinner = findViewById(R.id.spinner_category_select);
        category_sel_spinner.setVisibility(View.GONE);
        category_sel_title.setVisibility(View.GONE);
        item_selected = false;

        //setting up custom toolbar for the activity
        Toolbar toolbar_add_mod_cat = findViewById(R.id.toolbar_add_mod_cat);
        setSupportActionBar(toolbar_add_mod_cat);
        actionbar_categories = getSupportActionBar();
        actionbar_categories.setDisplayHomeAsUpEnabled(true);
        actionbar_categories.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionbar_categories.setTitle(R.string.title_add_category);

        input_category = findViewById(R.id.text_category_name);
        input_color = findViewById(R.id.button_category_color);
        save_category_button = findViewById(R.id.button_category_save);
        add_mod_switch = findViewById(R.id.switch_modify_category);

        /*
            click listener to check when user switches between add and modify category.
            display the spinner and category title while switching to modify category.
        */
        add_mod_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actionbar_categories.getTitle().equals(getResources().getString(R.string.title_add_category))){
                    actionbar_categories.setTitle(R.string.title_modify_category);
                    category_sel_title.setVisibility(View.VISIBLE);
                    category_sel_spinner.setVisibility(View.VISIBLE);
                    input_category.getText().clear();
                    input_color.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorBlack));
                    input_color.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite));
                    input_color.setText(COLOR_TEXT);
                    new DatabaseAsyncLoad().execute();
                }else{
                    actionbar_categories.setTitle(R.string.title_add_category);
                    category_sel_spinner.setVisibility(View.GONE);
                    category_sel_title.setVisibility(View.GONE);
                    input_category.getText().clear();
                    input_color.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorBlack));
                    input_color.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite));
                    input_color.setText(COLOR_TEXT);
                }
            }
        });

        default_color = ContextCompat.getColor(this,R.color.colorBlack);

        input_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = AddModCategories.this;
                ColorPickerDialogBuilder
                        .with(context,R.style.ColorPickerDialogTheme)
                        .setTitle("Choose Color")
                        .initialColor(CURRENT_BACKGROUND_COLOR)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                                //toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
                                //Toast.makeText(context,String.format("#%06X", (0xFFFFFF & selectedColor)), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton(OK, new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                input_color.setBackgroundColor(selectedColor);
                                input_color.setText(String.format("#%06X", (0xFFFFFF & selectedColor)));
                                input_hex = String.valueOf(selectedColor);
                                getTextColor(selectedColor);
                            }
                        })
                        .setNegativeButton(CANCEL, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .showColorEdit(true)
                        .setColorEditTextColor(ContextCompat.getColor(AddModCategories.this, android.R.color.holo_blue_bright))
                        .build()
                        .show();
            }
        });

        category_sel_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //run sql queries here
                    item_selected = true;
                    new DatabaseAsyncLoad().execute();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //do nothing
                }
        });


        /*String sel_category = category_sel_spinner.getSelectedItem().toString();
                Category category_data = app_database.catDao().findByName(sel_category);
                input_category.setText(category_data.getCatName());
                input_hex.setText(category_data.getHexCode());*/

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
            String specCat = input_category.getText().toString();
            String specHex = input_hex;

            Category category = new Category();
            category.setCatName(specCat);
            category.setHexCode(specHex);

            if (actionbar_categories.getTitle().equals(getResources().getString(R.string.title_add_category))) {
                app_database.catDao().insertAll(category);
                //TODO resolve error msg: Can't toast on a thread that has not called Looper.prepare()
                //Toast.makeText(getApplicationContext(),"Category saved",Toast.LENGTH_SHORT).show();
                //was: clearComposingText();
                input_category.getText().clear();
                input_color.setBackgroundColor(ContextCompat.getColor(AddModCategories.this,R.color.colorBlack));
                input_color.setText(R.string.hint_category_color);
                input_color.setTextColor(ContextCompat.getColor(AddModCategories.this, R.color.colorWhite));

            } else {
                String oldCat = category_sel_spinner.getSelectedItem().toString();
                app_database.catDao().update(specCat, specHex, oldCat);
                input_category.getText().clear();
                input_color.setBackgroundColor(ContextCompat.getColor(AddModCategories.this,R.color.colorBlack));
                input_color.setText(R.string.hint_category_color);
                input_color.setTextColor(ContextCompat.getColor(AddModCategories.this, R.color.colorWhite));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        category_sel_spinner.setSelection(0);
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

    private class DatabaseAsyncLoad extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //perform pre-adding operation here
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if(!item_selected) {
                loadSpinnerData();
            } else {
                loadCategoryDetails();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //perform post-adding operation here
        }
    }

    private void loadCategoryDetails() {

        //Get the selected category item name
        final String category_name = category_sel_spinner.getSelectedItem().toString();

        //Fetch the corresponding color from the database
        final String category_color = app_database.catDao().getCatColor(category_name);

        //Load the category name and color to corresponding views
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (category_name != DEFAULT_CATEGORY_ITEM) {
                    input_category.setText(category_name);
                    int cat_color = Integer.parseInt(category_color);
                    input_color.setText(String.format("#%06X", (0xFFFFFF & cat_color)));
                    input_color.setBackgroundColor(cat_color);
                    getTextColor(cat_color);
                    input_hex = category_color;
                }
            }
        });
        item_selected = false;
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
        List<String> labels = app_database.catDao().getCatNames();

        labels.add(DEFAULT_CATEGORY_ITEM);
        Collections.sort(labels);

        //creating adapter from spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, labels);

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

    //to change text color for better contrast
    public void getTextColor(int selectedColor){
        int colorRed = Color.red(selectedColor);
        int colorGreen = Color.green(selectedColor);
        int colorBlue = Color.blue(selectedColor);
        if((colorRed*0.299 + colorGreen*0.587 + colorBlue*0.114)>186){
            input_color.setTextColor(ContextCompat.getColor(AddModCategories.this,R.color.colorBlack));
        } else {
            input_color.setTextColor(ContextCompat.getColor(AddModCategories.this,R.color.colorWhite));
        }
    }

    @Override
    protected void onResume() {
        item_selected = false;
        new DatabaseAsyncLoad().execute();
        super.onResume();
    }
}
