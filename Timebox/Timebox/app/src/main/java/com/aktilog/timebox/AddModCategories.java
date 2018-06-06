package com.aktilog.timebox;

import android.content.Intent;
import android.os.AsyncTask;
import android.renderscript.Script;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

//TODO check if valid values are entered before enabeling save button1

public class AddModCategories extends AppCompatActivity{
    public EditText inputCat;
    public EditText inputHex;
    public String specCat;
    public String specHex;
    public AppDatabase db;
    public Switch s;
    public Spinner mySpinner;
    public Button buttonSave;
    public String oldCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mod_categories);
        //text fields
        inputCat = (EditText) findViewById(R.id.editText);
        inputHex = (EditText) findViewById(R.id.editText2);
        specCat = inputCat.getText().toString();
        specHex = inputHex.getText().toString();

        //switch
        s = (Switch) findViewById(R.id.switch2);
        //spinner
        mySpinner = (Spinner) findViewById(R.id.spinner2);
        //final Boolean switchState = null;
        //button
        buttonSave = (Button) findViewById(R.id.button);
        //chosen value from spinner
        oldCat = mySpinner.getSelectedItem().toString();



        //disable button
        buttonSave.setEnabled(false);
        //for spinner
        loadSpinnerData();

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                buttonSave.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //AUTO_GENERATED method
                //do nothing
            }
        });

        //for button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Category category = new Category();
                        category.setCatName(specCat);
                        category.setHexCode(specHex);

                        final Boolean switchState = getSwitchState();

                        if(switchState == false) {
                            add(category);
                        } else {
                            update(specCat, specHex, oldCat);
                        }
                    }
                });
            }
        });
    }

    //gets switch state
    public boolean getSwitchState() {
        Boolean switchState = s.isChecked();
        return switchState;
    }

    //equal to INSERT INTO
    public void add(Category category) {
        db.catDao().insertAll(category);
    }

    //equal to UPDATE
    public void update(String specCat, String specHex, String oldCat) {
        db.catDao().update(specCat, specHex, oldCat);
    }

    //load spinner data
    public void loadSpinnerData() {
        //Spinner drop down elements
        List<String> labels = db.catDao().getCatNames();

        //creating adapter from spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, labels);

        //drop down layout style
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //attaching data adapter to spinner
        mySpinner.setAdapter(dataAdapter);
    }
}

