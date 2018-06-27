package com.aktilog.timebox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SetPinActivity extends AppCompatActivity {

    Button set_pin_cancel;
    Button set_pin_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);

        set_pin_ok = findViewById(R.id.button_set_pin_ok);
        set_pin_cancel = findViewById(R.id.button_set_pin_cancel);
        set_pin_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SetPinActivity.this, "Clicked OK", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("EnablePin",true);
                editor.apply();
                Intent call_back = new Intent();
                setResult(RESULT_OK,call_back);
                SetPinActivity.this.finish();

            }
        });
        set_pin_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SetPinActivity.this, "Clicked Cancel", Toast.LENGTH_SHORT).show();
                SetPinActivity.this.finish();
            }
        });
    }
}
