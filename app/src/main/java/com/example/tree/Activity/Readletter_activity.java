package com.example.tree.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tree.Bean.SentLetter;
import com.example.tree.Bean.Story;
import com.example.tree.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Readletter_activity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG ="Rong";
    private Intent intent;
    private SentLetter mSentLetter;
    private Story mStory;
    private TextView readletter_tv_titlebar;
    private TextView readletter_tv_title;
    private TextView readletter_tv_date;
    private TextView readletter_tv_letterbody;
    private Context context= Readletter_activity.this;
    List<SentLetter> mSentLetters =new ArrayList<>();
    List<Story> mStories =new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readletter_activity_layout);
        initView();
        intent = getIntent();
        int flag = intent.getIntExtra("flag", 0);
        Log.i(TAG, "flag: "+ flag);
        if (flag == 1){
            queryLetter();
        }else if (flag == 2){
            queryStory();
        }
    }
    private void initView() {
        readletter_tv_titlebar = findViewById(R.id.readletter_tv_titlebar);
        readletter_tv_title = findViewById(R.id.readletter_tv_title);
        readletter_tv_date = findViewById(R.id.readletter_tv_date);
        readletter_tv_letterbody = findViewById(R.id.readletter_tv_letterbody);
        findViewById(R.id.readletter_btn_back).setOnClickListener(this);
    }
    private void queryStory(){
//        查询信件
        Log.i(TAG,"进入查询");
        Intent intent = getIntent();
        String storyId = intent.getStringExtra( "objectId" );
        Log.i( TAG, "onClick: " + storyId);
        BmobQuery<Story> storyBmobQueryBmobQuery = new BmobQuery<Story>();
        storyBmobQueryBmobQuery.setLimit(1);
        storyBmobQueryBmobQuery.addWhereEqualTo("objectId",storyId);
        storyBmobQueryBmobQuery.findObjects(new FindListener<Story>() {
            @Override
            public void done(List<Story> object, BmobException e) {
                Log.i(TAG,"进入查询222");
                if (e == null) {
                    mStories.addAll(object);
                    mStory = mStories.get(0);
                    showStory();
                    Log.i(TAG,"查询成功"+ mStories.size());
                } else {
                    Log.e("Question:", e.toString());
                    Toast.makeText( context, "查询失败"+e.toString(), Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }
    private void queryLetter(){
//        查询信件
        Log.i(TAG,"进入查询");
        Intent intent = getIntent();
        String sentLetterId = intent.getStringExtra( "objectId" );
        Log.i( TAG, "onClick: " + sentLetterId);
        BmobQuery<SentLetter> letterBmobQueryBmobQuery = new BmobQuery<SentLetter>();
        letterBmobQueryBmobQuery.setLimit(1);
        letterBmobQueryBmobQuery.addWhereEqualTo("objectId",sentLetterId);
        letterBmobQueryBmobQuery.findObjects(new FindListener<SentLetter>() {
            @Override
            public void done(List<SentLetter> object, BmobException e) {
                Log.i(TAG,"进入查询222");
                if (e == null) {
                    mSentLetters.addAll(object);
                    mSentLetter = mSentLetters.get(0);
                    showLetter();
                    Log.i(TAG,"查询成功"+ mSentLetters.size());
                } else {
                    Log.e("Question:", e.toString());
                    Toast.makeText( context, "查询失败"+e.toString(), Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }
    private void showLetter(){
        readletter_tv_titlebar.setText( mSentLetter.getLetterTitle());
        readletter_tv_title.setText( mSentLetter.getLetterTitle());
        readletter_tv_date.setText( mSentLetter.getUpdatedAt().substring( 0, 10 ) );
        readletter_tv_letterbody.setText(mSentLetter.getLetterBody());

    }
    private void showStory(){
        readletter_tv_titlebar.setText( mStory.getStoryTitle());
        readletter_tv_title.setText(mStory.getStoryTitle());
        readletter_tv_date.setText( mStory.getUpdatedAt().substring( 0, 10 ) );
        readletter_tv_letterbody.setText(mStory.getStoryBody());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.readletter_btn_back:
                finish();
                break;
        }
    }
}
