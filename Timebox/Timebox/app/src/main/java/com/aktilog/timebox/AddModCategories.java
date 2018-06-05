package com.aktilog.timebox;

import android.renderscript.Script;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class AddModCategories extends AppCompatActivity {

    EditText inputCat = (EditText) findViewById(R.id.inputCat);
    EditText inputHex = (EditText) findViewById(R.id.inputHex);
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mod_categories);

        final Switch s = (Switch) findViewById(R.id.switch2);
        s.setChecked(false);

        final Spinner mySpinner = (Spinner) findViewById(R.id.spinner2);

        Button buttonSave = (Button) findViewById(R.id.button);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String specCat = inputCat.getText().toString();
                String specHex = inputHex.getText().toString();
                String oldCat = mySpinner.getSelectedItem().toString();

                Boolean switchState = s.isChecked();

                private void populateData() {
                    Category category = new Category();
                    category.setCatName(specCat);
                    category.setHexCode(specHex);

                    if (switchState = false) {
                        db.catDao().insertAll(category);
                    } else {
                        db.catDao().update(specCat, specHex, oldCat);
                    }

                }
            }
        });


    }
}
