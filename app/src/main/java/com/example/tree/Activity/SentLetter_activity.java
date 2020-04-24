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
import com.example.tree.Bean.SentLetter;
import com.example.tree.Bean._User;
import com.example.tree.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SentLetter_activity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView sentletter_recyclerView;
    private SentLetterAdapter sentLetterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.sentletter_activity_layout);
        initView();
        querySentLetter();
    }

    private void initView() {
        sentletter_recyclerView = findViewById( R.id.sentletter_recyclerView );
        sentletter_recyclerView.setOnClickListener( this );

        findViewById( R.id.sentletter_btn_back ).setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sentletter_btn_back:
                finish();
                break;
        }
    }

    private void querySentLetter() {
        if (BmobUser.isLogin()) {
            BmobQuery<SentLetter> query = new BmobQuery<>();
            query.addWhereEqualTo("sentUser", BmobUser.getCurrentUser( _User.class));
            query.order("-createdAt");
            query.findObjects(new FindListener<SentLetter>() {
                @Override
                public void done(List<SentLetter> object, BmobException e) {
                    if (e == null) {
                        if(object.size() == 0){
                            Toast.makeText( SentLetter_activity.this, "没有写好的信件，快去找挚友说说话吧~", Toast.LENGTH_SHORT).show();
                        }
                        sentLetterAdapter = new SentLetterAdapter( SentLetter_activity.this, object );
                        initRV();
                    } else {
                        Log.e("BMOB", e.toString());
                        Toast.makeText( SentLetter_activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
        }
    }

    private void initRV() {
        //设置LayoutManager
        sentletter_recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        //设置动画效果
        sentletter_recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        sentletter_recyclerView.setAdapter(sentLetterAdapter);
    }
}
