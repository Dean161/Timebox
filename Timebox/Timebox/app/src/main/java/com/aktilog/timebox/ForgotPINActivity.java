package com.aktilog.timebox;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class ForgotPINActivity extends AppCompatActivity {

    Button validate, reset;
    EditText sec_ans;
    TextView sec_ques;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pin);

        Random number;
        final int random_num;

        number = new Random();
        random_num = number.nextInt(6);

        validate = findViewById(R.id.button_validate_answer);
        reset = findViewById(R.id.button_reset);
        sec_ans = findViewById(R.id.text_sec_answer);
        sec_ques = findViewById(R.id.text_sec_question);

        switch(random_num){
            case 0:
                sec_ques.setText(R.string.pref_sec_ques1);
                break;
            case 1:
                sec_ques.setText(R.string.pref_sec_ques2);
                break;
            case 2:
                sec_ques.setText(R.string.pref_sec_ques3);
                break;
            case 3:
                sec_ques.setText(R.string.pref_sec_ques4);
                break;
            case 4:
                sec_ques.setText(R.string.pref_sec_ques5);
                break;
            case 5:
                sec_ques.setText(R.string.pref_sec_ques6);
                break;
            default:
                sec_ques.setText(R.string.title_sec_question);
        }


        Toolbar toolbar_forgot_pin = findViewById(R.id.toolbar_forgot_pin);
        setSupportActionBar(toolbar_forgot_pin);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionbar.setTitle(R.string.title_forgot_pin);

        final SharedPreferences shared_preferences = PreferenceManager.getDefaultSharedPreferences(this);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(random_num){
                    case 0:
                        if(sec_ans.getText().toString().equals(shared_preferences.getString("Answer1",""))){
                            Toast.makeText(ForgotPINActivity.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ForgotPINActivity.this, "Incorrect Answer", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        if(sec_ans.getText().toString().equals(shared_preferences.getString("Answer2",""))){
                            Toast.makeText(ForgotPINActivity.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ForgotPINActivity.this, "Incorrect Answer", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        if(sec_ans.getText().toString().equals(shared_preferences.getString("Answer3",""))){
                            Toast.makeText(ForgotPINActivity.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ForgotPINActivity.this, "Incorrect Answer", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        if(sec_ans.getText().toString().equals(shared_preferences.getString("Answer4",""))){
                            Toast.makeText(ForgotPINActivity.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ForgotPINActivity.this, "Incorrect Answer", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 4:
                        if(sec_ans.getText().toString().equals(shared_preferences.getString("Answer5",""))){
                            Toast.makeText(ForgotPINActivity.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ForgotPINActivity.this, "Incorrect Answer", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 5:
                        if(sec_ans.getText().toString().equals(shared_preferences.getString("Answer6",""))){
                            Toast.makeText(ForgotPINActivity.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ForgotPINActivity.this, "Incorrect Answer", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        Toast.makeText(ForgotPINActivity.this, "Wrong option", Toast.LENGTH_SHORT).show();
                }
            }

        });

        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sec_ans.getText().clear();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
       return super.onOptionsItemSelected(item);
    }
}
