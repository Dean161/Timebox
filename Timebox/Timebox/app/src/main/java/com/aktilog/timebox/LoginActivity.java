package com.aktilog.timebox;

import android.content.Intent;
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

        sign_in = findViewById(R.id.sign_in);
        entered_pin = findViewById(R.id.pin_box);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(entered_pin.getText().toString().equals("1234")){
                    Intent launch_MainActivity = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(launch_MainActivity);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Incorrect PIN.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        forgot_pin = findViewById(R.id.forgot_pin);

        forgot_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launch_ForgotPINActivity = new Intent(LoginActivity.this,ForgotPINActivity.class);
                startActivity(launch_ForgotPINActivity);
            }
        });
    }

}
