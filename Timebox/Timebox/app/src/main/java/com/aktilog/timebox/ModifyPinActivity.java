package com.aktilog.timebox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyPinActivity extends AppCompatActivity {

    Button modify_pin_cancel;
    Button modify_pin_ok;
    EditText entered_pin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pin);

        modify_pin_ok = findViewById(R.id.button_modify_pin_ok);
        modify_pin_cancel = findViewById(R.id.button_modify_pin_cancel);
        entered_pin = findViewById(R.id.text_modify_pin);
        modify_pin_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entered_pin_value = entered_pin.getText().toString();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String set_pin = sharedPreferences.getString("SetPin","");
                if (entered_pin_value.equals(set_pin)){
                    Intent showModifyDialog = new Intent(ModifyPinActivity.this,SetPinActivity.class);
                    startActivity(showModifyDialog);
                    ModifyPinActivity.this.finish();
                } else {
                    Toast.makeText(ModifyPinActivity.this, "Incorrect Pin", Toast.LENGTH_LONG).show();
                }

            }
        });

        modify_pin_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPinActivity.this.finish();
            }
        });

    }
}
