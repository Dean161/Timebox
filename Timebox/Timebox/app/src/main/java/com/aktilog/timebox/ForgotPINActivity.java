package com.aktilog.timebox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ForgotPINActivity extends AppCompatActivity {

    Button validate, reset;
    EditText sec_ans;
    TextView sec_ques;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pin);

        validate = findViewById(R.id.validate_answer);
        reset = findViewById(R.id.reset_button);
        sec_ans = findViewById(R.id.sec_ans);
        sec_ques = findViewById(R.id.sec_ques);

        final ArrayList<SecurityQuesAns> security_list = new ArrayList<>();
        final SecurityQuesAns sec_ques_ans1 = new SecurityQuesAns();
        final SecurityQuesAns sec_ques_ans2 = new SecurityQuesAns();
        final SecurityQuesAns sec_ques_ans3 = new SecurityQuesAns();

        sec_ques_ans1.setSet_sec_ques("What is the name of your first pet?");
        sec_ques_ans1.setSet_sec_ans("Doggy");
        security_list.add(sec_ques_ans1);
        sec_ques_ans2.setSet_sec_ques("What is the name of your second pet?");
        sec_ques_ans2.setSet_sec_ans("Piggy");
        security_list.add(sec_ques_ans2);
        sec_ques_ans3.setSet_sec_ques("What is the name of your third pet?");
        sec_ques_ans3.setSet_sec_ans("Monkey");
        security_list.add(sec_ques_ans3);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i;
                for (i = 0; i < 3; i++) {
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
}
