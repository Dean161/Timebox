/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aktilog.timebox;

import android.content.Intent;
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

import java.util.Random;

public class ForgotPINActivity extends AppCompatActivity {

    protected Button reset;
    protected Button validate;
    protected EditText sec_ans1;
    protected TextView sec_ques1;
    protected EditText sec_ans2;
    protected TextView sec_ques2;
    private String KEY_ANSWER1 = "Answer1";
    private String KEY_ANSWER2 = "Answer2";
    private String KEY_ANSWER3 = "Answer3";
    private String KEY_ANSWER4 = "Answer4";
    private String KEY_ANSWER5 = "Answer5";
    private String KEY_ANSWER6 = "Answer6";
    private String INCORRECT_ANSWER = "One or both answers are incorrect";

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
        sec_ans1 = findViewById(R.id.text_sec_answer1);
        sec_ques1 = findViewById(R.id.text_sec_question1);
        sec_ans2 = findViewById(R.id.text_sec_answer2);
        sec_ques2 = findViewById(R.id.text_sec_question2);

        switch(random_num){
            case 0:
                sec_ques1.setText(R.string.pref_sec_ques1);
                sec_ques2.setText(R.string.pref_sec_ques3);
                break;
            case 1:
                sec_ques1.setText(R.string.pref_sec_ques2);
                sec_ques2.setText(R.string.pref_sec_ques6);
                break;
            case 2:
                sec_ques1.setText(R.string.pref_sec_ques3);
                sec_ques2.setText(R.string.pref_sec_ques4);
                break;
            case 3:
                sec_ques1.setText(R.string.pref_sec_ques4);
                sec_ques2.setText(R.string.pref_sec_ques2);
                break;
            case 4:
                sec_ques1.setText(R.string.pref_sec_ques5);
                sec_ques2.setText(R.string.pref_sec_ques1);
                break;
            case 5:
                sec_ques1.setText(R.string.pref_sec_ques6);
                sec_ques2.setText(R.string.pref_sec_ques5);
                break;
            default:
                sec_ques1.setText(R.string.title_sec_question);
                sec_ques2.setText(R.string.title_sec_question);
        }

        try {
            Toolbar toolbar_forgot_pin = findViewById(R.id.toolbar_forgot_pin);
            setSupportActionBar(toolbar_forgot_pin);
            ActionBar actionbar = getSupportActionBar();
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionbar.setTitle(R.string.title_forgot_pin);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        final SharedPreferences shared_preferences = PreferenceManager.getDefaultSharedPreferences(this);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(random_num){
                    case 0:
                        if((sec_ans1.getText().toString().equals(shared_preferences.getString(KEY_ANSWER1,""))) && (sec_ans2.getText().toString().equals(shared_preferences.getString(KEY_ANSWER3,"")))){
                            showSetPinDialog();
                        }else{
                            Toast.makeText(ForgotPINActivity.this, INCORRECT_ANSWER, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        if((sec_ans1.getText().toString().equals(shared_preferences.getString(KEY_ANSWER2,""))) && (sec_ans2.getText().toString().equals(shared_preferences.getString(KEY_ANSWER6,"")))){
                            showSetPinDialog();
                        }else{
                            Toast.makeText(ForgotPINActivity.this, INCORRECT_ANSWER, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        if((sec_ans1.getText().toString().equals(shared_preferences.getString(KEY_ANSWER3,""))) && (sec_ans2.getText().toString().equals(shared_preferences.getString(KEY_ANSWER4,"")))){
                            showSetPinDialog();
                        }else{
                            Toast.makeText(ForgotPINActivity.this, INCORRECT_ANSWER, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        if((sec_ans1.getText().toString().equals(shared_preferences.getString(KEY_ANSWER4,""))) && (sec_ans2.getText().toString().equals(shared_preferences.getString(KEY_ANSWER2,"")))){
                            showSetPinDialog();
                        }else{
                            Toast.makeText(ForgotPINActivity.this, INCORRECT_ANSWER, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 4:
                        if((sec_ans1.getText().toString().equals(shared_preferences.getString(KEY_ANSWER5,""))) && (sec_ans2.getText().toString().equals(shared_preferences.getString(KEY_ANSWER1,"")))){
                            showSetPinDialog();
                        }else{
                            Toast.makeText(ForgotPINActivity.this, INCORRECT_ANSWER, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 5:
                        if((sec_ans1.getText().toString().equals(shared_preferences.getString(KEY_ANSWER6,""))) && (sec_ans2.getText().toString().equals(shared_preferences.getString(KEY_ANSWER5,"")))){
                            showSetPinDialog();
                        }else{
                            Toast.makeText(ForgotPINActivity.this, INCORRECT_ANSWER, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        //do nothing
                }
            }

        });

        /*
            Clears the content of the answer field
        */
        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sec_ans1.getText().clear();
                sec_ans2.getText().clear();
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

    public void showSetPinDialog(){
        Intent showSetPinDialog = new Intent(ForgotPINActivity.this,SetPinActivity.class);
        startActivity(showSetPinDialog);
        ForgotPINActivity.this.finish();
    }
}
