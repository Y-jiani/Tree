package com.example.tree.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tree.Bean.ReceivedLetter;
import com.example.tree.Bean.SentLetter;
import com.example.tree.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Readreceivedletter_activity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG ="Rong";
    private ReceivedLetter mReceivedLetter;
    private SharedPreferences pref;
    private TextView readreceivedletter_tv_titlebar;
    private TextView readreceivedletter_tv_title;
    private TextView readreceivedletter_tv_date;
    private TextView readreceivedletter_tv_letterbody;
    private TextView readreceivedletter_tv_replybody;
    private TextView readreceivedletter_tv_name1;
    private TextView readreceivedletter_tv_name2;
    private String receivedLetterId;
    Intent intent;
    private int flag;
    private Context context= Readreceivedletter_activity.this;
    List<ReceivedLetter> mReceivedLetters =new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readreceivedletter_activity_layout);
        initView();
        queryLetter();
        changeText();

    }

    private void changeText() {
         flag = pref.getInt("flag",0);
         Log.i(TAG,"flag"+flag);
        if (flag == 1){
            Log.i(TAG,"我是小一");
            readreceivedletter_tv_name1.setText("我");
            readreceivedletter_tv_name2.setText("挚友");
        }else if (flag == 2){
             readreceivedletter_tv_name2.setText("我");
            readreceivedletter_tv_name1.setText("小一");
        }
    }

    private void initView() {
        readreceivedletter_tv_titlebar = findViewById(R.id.readreceivedletter_tv_titlebar);
        readreceivedletter_tv_title = findViewById(R.id.readreceivedletter_tv_title);
        readreceivedletter_tv_date = findViewById(R.id.readreceivedletter_tv_date);
        readreceivedletter_tv_letterbody = findViewById(R.id.readreceivedletter_tv_letterbody);
        readreceivedletter_tv_replybody = findViewById(R.id.readreceivedletter_tv_replybody);
        readreceivedletter_tv_name1=findViewById(R.id.readreceivedletter_tv_name1);
        readreceivedletter_tv_name2=findViewById(R.id.readreceivedletter_tv_name2);
        findViewById(R.id.readreceivedletter_btn_back).setOnClickListener(this);
        pref= getSharedPreferences("data", MODE_PRIVATE);
        intent = getIntent();
    }
    private void queryLetter(){//查询题目
        Log.i(TAG,"进入查询");
        receivedLetterId = intent.getStringExtra( "objectId" );
        Log.i( TAG, "onClick: " + receivedLetterId);
        BmobQuery<ReceivedLetter> QuestionBmobQuery = new BmobQuery<ReceivedLetter>();
        QuestionBmobQuery.setLimit(1);
        QuestionBmobQuery.include("receivedLetter");
        QuestionBmobQuery.addWhereEqualTo("objectId", receivedLetterId);
        QuestionBmobQuery.findObjects(new FindListener<ReceivedLetter>() {
            @Override
            public void done(List<ReceivedLetter> object, BmobException e) {

                Log.i(TAG,"进入查询222");
                if (e == null) {
                    mReceivedLetters.addAll(object);
                    mReceivedLetter = mReceivedLetters.get(0);
                    showLetter();
                    Log.i(TAG,"查询成功"+ mReceivedLetters.size());
                } else {
                    Log.e("Question:", e.toString());
                    Toast.makeText( context, "查询失败"+e.toString(), Toast.LENGTH_SHORT ).show();
                }
            }
        });

    }
    private void showLetter(){
        readreceivedletter_tv_titlebar.setText( mReceivedLetter.getReceivedLetter().getLetterTitle());
        readreceivedletter_tv_title.setText( mReceivedLetter.getReceivedLetter().getLetterTitle());
        readreceivedletter_tv_date.setText( mReceivedLetter.getUpdatedAt().substring( 0, 10 ) );
        readreceivedletter_tv_letterbody.setText(mReceivedLetter.getReceivedLetter().getLetterBody());
        readreceivedletter_tv_replybody.setText(mReceivedLetter.getReplyBody());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.readreceivedletter_btn_back:
                finish();
                break;
        }
    }
}
