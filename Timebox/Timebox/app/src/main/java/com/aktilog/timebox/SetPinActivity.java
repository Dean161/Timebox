package com.aktilog.timebox;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SetPinActivity extends AppCompatActivity {

    Button set_pin_cancel;
    Button set_pin_ok;
    EditText specified_pin;
    EditText confirmed_pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);

        set_pin_ok = findViewById(R.id.button_set_pin_ok);
        set_pin_cancel = findViewById(R.id.button_set_pin_cancel);
        specified_pin = findViewById(R.id.text_set_pin);
        confirmed_pin = findViewById(R.id.text_confirm_pin);
        set_pin_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String specified_pin_value = specified_pin.getText().toString();
                String confirmed_pin_value = confirmed_pin.getText().toString();
                if (confirmed_pin_value.equals(specified_pin_value)){
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("SetPin",specified_pin_value);
                    editor.putBoolean("EnablePin",true);
                    editor.apply();
                    if (SettingsActivity.switchPreference != null){
                        SettingsActivity.switchPreference.setChecked(true);
                    }
                    SetPinActivity.this.finish();
                } else {
                    Toast.makeText(SetPinActivity.this, "Entered PINs do not match", Toast.LENGTH_LONG).show();
                }
            }
        });
        set_pin_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetPinActivity.this.finish();
            }
        });
    }
}
