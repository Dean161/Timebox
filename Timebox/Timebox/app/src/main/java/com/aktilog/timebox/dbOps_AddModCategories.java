package com.aktilog.timebox;

import android.widget.ArrayAdapter;

import java.util.List;

//TODO find a way to use variables from protected method here
public class dbOps_AddModCategories extends AddModCategories {

    //gets switch state
    public static boolean getSwitchState() {
        Boolean switchState = s.isChecked();
        return switchState;
    }

    //equal to INSERT INTO
    public static void add(Category category) {
        db.catDao().insertAll(category);
    }

    //equal to UPDATE
    public static void update(String specCat, String specHex, String oldCat) {
        db.catDao().update(specCat, specHex, oldCat);
    }

    //load spinner data
    public static void loadSpinnerData() {
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
