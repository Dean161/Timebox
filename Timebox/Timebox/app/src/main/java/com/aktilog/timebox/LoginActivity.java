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

public class LoginActivity extends AppCompatActivity {

    Button sign_in,forgot_pin;
    EditText entered_pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences shared_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean enable_pin_flag = shared_preferences.getBoolean("EnablePin",false);

        if (!enable_pin_flag){
            Intent launch_MainActivity = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(launch_MainActivity);
            finish();
        }

        sign_in = findViewById(R.id.button_sign_in);
        entered_pin = findViewById(R.id.text_pin);
        final String set_pin = shared_preferences.getString("SetPin","");

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(entered_pin.getText().toString().equals(set_pin)){
                    Intent launch_MainActivity = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(launch_MainActivity);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),R.string.message_incorrect_pin,Toast.LENGTH_SHORT).show();
                }
            }
        });

        forgot_pin = findViewById(R.id.button_forgot_pin);

        forgot_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launch_ForgotPINActivity = new Intent(LoginActivity.this,ForgotPINActivity.class);
                startActivity(launch_ForgotPINActivity);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences shared_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        sign_in = findViewById(R.id.button_sign_in);
        entered_pin = findViewById(R.id.text_pin);
        final String set_pin = shared_preferences.getString("SetPin","");

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(entered_pin.getText().toString().equals(set_pin)){
                    Intent launch_MainActivity = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(launch_MainActivity);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),R.string.message_incorrect_pin,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
