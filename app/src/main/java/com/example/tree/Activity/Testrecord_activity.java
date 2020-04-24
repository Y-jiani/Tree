package com.example.tree.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tree.Adapter.SentLetterAdapter;
import com.example.tree.Adapter.TestRecordAdapter;
import com.example.tree.Bean.SentLetter;
import com.example.tree.Bean.TestRecord;
import com.example.tree.Bean._User;
import com.example.tree.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Testrecord_activity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView testrecord_recyclerView;
    private TestRecordAdapter testRecordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.testrecord_activity_layout);
        initView();
        querytestrecord();
    }

    private void initView() {
        testrecord_recyclerView = findViewById( R.id.testrecord_recyclerView );
        testrecord_recyclerView.setOnClickListener( this );

        findViewById( R.id.testrecord_btn_back ).setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.testrecord_btn_back:
                finish();
                break;
        }
    }

    private void querytestrecord() {
        if (BmobUser.isLogin()) {
            BmobQuery<TestRecord> query = new BmobQuery<>();
            query.addWhereEqualTo("username", BmobUser.getCurrentUser( _User.class));
            query.include("testtype");
            query.order("-createdAt");
            query.findObjects(new FindListener<TestRecord>() {
                @Override
                public void done(List<TestRecord> object, BmobException e) {
                    if (e == null) {
                        if(object.size() == 0){
                            Toast.makeText( Testrecord_activity.this, "没有测试记录，快去测测吧吧~", Toast.LENGTH_SHORT).show();
                        }
                        testRecordAdapter = new TestRecordAdapter( Testrecord_activity.this, object );
                        initRV();
                    } else {
                        Log.e("BMOB", e.toString());
                        Toast.makeText( Testrecord_activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
        }
    }

    private void initRV() {
        //设置LayoutManager
        testrecord_recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        //设置动画效果
        testrecord_recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        testrecord_recyclerView.setAdapter(testRecordAdapter);
    }
}
