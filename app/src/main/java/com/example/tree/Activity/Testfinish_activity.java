package com.example.tree.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tree.Fragment.Test_fragment;
import com.example.tree.R;

public class Testfinish_activity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG ="Rong";
    TextView testfinish_tv_grade;
    TextView testfinish_tv_sentence;
    ImageView testfinish_iv_icon;
    Button testfinish_btn_end;
    Button testfinish_btn_back;

    int grade;
    private Context context=Testfinish_activity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.testfinish_activity_layout);
        initView();
        showResult();
    }

    private void showResult() {
        Intent intent = getIntent();
        grade = intent.getIntExtra("grade",0);
        Log.i(TAG,"传过来的成绩是"+grade);
        testfinish_tv_grade.setText(String.valueOf(grade)+"分");
        switch(grade/10){
            case 0:
            case 1:
            case 2:
                testfinish_iv_icon.setBackgroundResource(R.drawable.testrecord_testresult_superunhappy);
                testfinish_tv_sentence.setText(R.string.testfinish_tv_superunhappy);
                break;
            case 3:
            case 4:
                testfinish_iv_icon.setBackgroundResource(R.drawable.testrecord_testresult_unhappy);
                testfinish_tv_sentence.setText(R.string.testfinish_tv_unhappy);
                break;
            case 5:
            case 6:
                testfinish_iv_icon.setBackgroundResource(R.drawable.testrecord_testresult_normal);
                testfinish_tv_sentence.setText(R.string.testfinish_tv_normal);
                break;
            case 7:
            case 8:
                testfinish_iv_icon.setBackgroundResource(R.drawable.testrecord_testresult_happy);
                testfinish_tv_sentence.setText(R.string.testfinish_tv_happy);
                break;
            case 9:
            case 10:
                testfinish_iv_icon.setBackgroundResource(R.drawable.testrecord_testresult_superhappy);
                testfinish_tv_sentence.setText(R.string.testfinish_tv_superhappy);
                break;
        }
    }
    private void initView() {
        testfinish_tv_grade =findViewById(R.id.testfinish_tv_grade);
        testfinish_iv_icon =findViewById(R.id.testfinish_iv_icon);
        testfinish_tv_sentence = findViewById(R.id.testfinish_tv_sentence);

        findViewById(R.id.testfinish_btn_end).setOnClickListener(this);
        findViewById(R.id.testfinish_btn_back).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.testfinish_btn_back:
                finish();
                break;
            case R.id.testfinish_btn_end:
                finish();
                break;
        }
    }
}
