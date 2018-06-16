package com.aktilog.timebox;

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
        int random_num;

        number = new Random();
        random_num = number.nextInt(3);

        validate = findViewById(R.id.button_validate_answer);
        reset = findViewById(R.id.button_reset);
        sec_ans = findViewById(R.id.text_sec_answer);
        sec_ques = findViewById(R.id.text_sec_question);

        switch(random_num){
            case 0:
                sec_ques.setText(R.string.sec_ques1);
                break;
            case 1:
                sec_ques.setText(R.string.sec_ques2);
                break;
            case 2:
                sec_ques.setText(R.string.sec_ques3);
                break;
            default:
                sec_ques.setText(R.string.title_sec_question);
        }


        final ArrayList<SecurityQuesAns> security_list = new ArrayList<>();
        final SecurityQuesAns sec_ques_ans1 = new SecurityQuesAns();
        final SecurityQuesAns sec_ques_ans2 = new SecurityQuesAns();
        final SecurityQuesAns sec_ques_ans3 = new SecurityQuesAns();

        sec_ques_ans1.setSet_sec_ques(getString(R.string.sec_ques1));
        sec_ques_ans1.setSet_sec_ans("Doggy");
        security_list.add(sec_ques_ans1);
        sec_ques_ans2.setSet_sec_ques(getString(R.string.sec_ques2));
        sec_ques_ans2.setSet_sec_ans("Piggy");
        security_list.add(sec_ques_ans2);
        sec_ques_ans3.setSet_sec_ques(getString(R.string.sec_ques3));
        sec_ques_ans3.setSet_sec_ans("Monkey");
        security_list.add(sec_ques_ans3);

        Toolbar toolbar_forgot_pin = findViewById(R.id.toolbar_forgot_pin);
        setSupportActionBar(toolbar_forgot_pin);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionbar.setTitle(R.string.title_forgot_pin);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i;
                for (i = 0; i < security_list.size(); i++) {
                    //Toast.makeText(getApplicationContext(),security_list.get(0).getSet_sec_ques(), Toast.LENGTH_SHORT).show();
                    //Log.d("Value",security_list.get(i).getSet_sec_ques());
                    //Log.d("Answer",security_list.get(i).getSet_sec_ans());
                    if (security_list.get(i).getSet_sec_ques().equals(sec_ques.getText().toString())) {
                        if (security_list.get(i).getSet_sec_ans().equals(sec_ans.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Correct Answer.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Incorrect Answer.", Toast.LENGTH_SHORT).show();
                        }
                    }
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
