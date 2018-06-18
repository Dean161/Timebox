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
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.List;

public class AddModCategories extends AppCompatActivity {

    AppDatabase db;
    EditText inputCat;
    Button inputColor;
    Button save_category_button;
    Spinner category_sel_spinner;
    TextView category_sel_title;
    ActionBar actionbar;
    Switch add_mod_switch;
    int defaultColor;
    String inputHex;
    private int currentBackgroundColor = 0xFFFFFFFF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mod_categories);

        //build database
        //db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "timeboxDatabase").build();
        db = AppDatabase.getAppDatabase(getApplicationContext());

        category_sel_title = findViewById(R.id.title_category_select);
        category_sel_spinner = findViewById(R.id.spinner_category_select);

        category_sel_spinner.setVisibility(View.GONE);
        category_sel_title.setVisibility(View.GONE);

        Toolbar toolbar_add_mod_cat = findViewById(R.id.toolbar_add_mod_cat);
        setSupportActionBar(toolbar_add_mod_cat);
        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionbar.setTitle(R.string.title_add_category);

        inputCat = findViewById(R.id.text_category_name);
        inputColor = findViewById(R.id.button_category_color);
        save_category_button = findViewById(R.id.button_category_save);

        add_mod_switch = findViewById(R.id.switch_modify_category);

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

        defaultColor = ContextCompat.getColor(this,R.color.colorBlack);

        inputColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = AddModCategories.this;
                ColorPickerDialogBuilder
                        .with(context,R.style.ColorPickerDialogTheme)
                        .setTitle("Choose Color")
                        .initialColor(currentBackgroundColor)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                                //toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
                                //Toast.makeText(context,String.format("#%06X", (0xFFFFFF & selectedColor)), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                inputColor.setBackgroundColor(selectedColor);
                                inputColor.setText(String.format("#%06X", (0xFFFFFF & selectedColor)));
                                getTextColor(selectedColor);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
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
            String specHex = inputHex;

            Category category = new Category();
            category.setCatName(specCat);
            category.setHexCode(specHex);

            if (actionbar.getTitle().equals(getResources().getString(R.string.title_add_category))) {
                db.catDao().insertAll(category);
                //TODO resolve error msg: Can't toast on a thread that has not called Looper.prepare()
                //Toast.makeText(getApplicationContext(),"Category saved",Toast.LENGTH_SHORT).show();

                //was: clearComposingText();
                inputCat.setText("");
                inputColor.setBackgroundColor(ContextCompat.getColor(AddModCategories.this,R.color.colorBlack));
                inputColor.setText(R.string.hint_category_color);
                inputColor.setTextColor(ContextCompat.getColor(AddModCategories.this, R.color.colorWhite));

            } else {
                String oldCat = category_sel_spinner.getSelectedItem().toString();
                db.catDao().update(specCat, specHex, oldCat);
                //Toast.makeText(getApplicationContext(),"Category updated",Toast.LENGTH_SHORT).show();
                //inputCat.clearComposingText();
                //inputHex.clearComposingText();
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
            inputColor.setTextColor(ContextCompat.getColor(AddModCategories.this,R.color.colorBlack));
        } else {
            inputColor.setTextColor(ContextCompat.getColor(AddModCategories.this,R.color.colorWhite));
        }
    }
}
