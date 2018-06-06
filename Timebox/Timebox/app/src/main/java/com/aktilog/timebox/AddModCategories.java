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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //text fields
        EditText inputCat = (EditText) findViewById(R.id.editText);
        EditText inputHex = (EditText) findViewById(R.id.editText2);
        final String specCat = inputCat.getText().toString();
        final String specHex = inputHex.getText().toString();
        //Database
        AppDatabase db;
        //switch
        Switch s = (Switch) findViewById(R.id.switch2);
        //spinner
        final Spinner mySpinner = (Spinner) findViewById(R.id.spinner2);
        //final Boolean switchState = null;
        //button
        final Button buttonSave = (Button) findViewById(R.id.button);
        //chosen value from spinner
        final String oldCat = mySpinner.getSelectedItem().toString();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mod_categories);

        //disable button
        buttonSave.setEnabled(false);
        //for spinner
        dbOps_AddModCategories.loadSpinnerData();

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

                        final Boolean switchState = dbOps_AddModCategories.getSwitchState();

                        if(switchState == false) {
                            dbOps_AddModCategories.add(category);
                        } else {
                            dbOps_AddModCategories.update(specCat, specHex, oldCat);
                        }
                    }
                });
            }
        });
    }

}
