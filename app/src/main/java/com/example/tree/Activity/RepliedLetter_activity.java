package com.example.tree.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tree.Adapter.ReceivedLetterAdapter;
import com.example.tree.Bean.ReceivedLetter;
import com.example.tree.Bean._User;
import com.example.tree.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class RepliedLetter_activity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView repliedletter_recyclerView;
    private ReceivedLetterAdapter repliedLetterAdapter;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.repliedletter_activity_layout);
        initView();
        editor.putInt("flag",2);
        editor.apply();
        queryRepliedLetter();
    }

    private void initView() {
        repliedletter_recyclerView = findViewById( R.id.repliedletter_recyclerView );
        repliedletter_recyclerView.setOnClickListener( this );
        findViewById( R.id.repliedletter_btn_back ).setOnClickListener( this );
        editor = getSharedPreferences("data", MODE_PRIVATE).edit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.repliedletter_btn_back:
                finish();
                break;
        }
    }

    private void queryRepliedLetter() {
        if (BmobUser.isLogin()) {
            BmobQuery<ReceivedLetter> query = new BmobQuery<>();
            query.addWhereEqualTo("receivedUser", BmobUser.getCurrentUser( _User.class));
            query.order("-updateddAt");
            query.include("receivedLetter");
            query.findObjects(new FindListener<ReceivedLetter>() {
                @Override
                public void done(List<ReceivedLetter> object, BmobException e) {
                    if (e == null) {
                        if(object.size() == 0){
                            Toast.makeText( RepliedLetter_activity.this, "没有已回复信件，快去找挚友说说话吧~", Toast.LENGTH_SHORT).show();
                        }
                        repliedLetterAdapter = new ReceivedLetterAdapter( RepliedLetter_activity.this, object );
                        initRV();
                    } else {
                        Log.e("BMOB", e.toString());
                        Toast.makeText( RepliedLetter_activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
        }
    }

    private void initRV() {
        //设置LayoutManager
        repliedletter_recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        //设置动画效果
        repliedletter_recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        repliedletter_recyclerView.setAdapter(repliedLetterAdapter);
    }
}


