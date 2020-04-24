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
import com.example.tree.Bean.SentLetter;
import com.example.tree.Bean._User;
import com.example.tree.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class  ReceivedLetter_activity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView receivedletter_recyclerView;
    private ReceivedLetterAdapter receivedLetterAdapter;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.receivedletter_activity_layout);
        initView();
        editor.putInt("flag",1);
        editor.apply();
        queryReceivedLetter();
    }

    private void initView() {
        receivedletter_recyclerView = findViewById( R.id.receivedletter_recyclerView );
        receivedletter_recyclerView.setOnClickListener( this );
        findViewById( R.id.receivedletter_btn_back ).setOnClickListener( this );
        editor = getSharedPreferences("data", MODE_PRIVATE).edit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.receivedletter_btn_back:
                finish();
                break;
        }
    }

    private void queryReceivedLetter() {
            BmobQuery<ReceivedLetter> query = new BmobQuery<>();
            BmobQuery<SentLetter> innerQuery = new BmobQuery<>();
            innerQuery.addWhereEqualTo("sentUser", BmobUser.getCurrentUser( _User.class));
            query.addWhereMatchesQuery("receivedLetter", "SentLetter", innerQuery);
            query.order("-updatedAt");
            query.include("receivedLetter");
            query.findObjects(new FindListener<ReceivedLetter>() {
                @Override
                public void done(List<ReceivedLetter> object, BmobException e) {
                    if(e==null){
                        Log.i("bmob","成功");
                        if(object.size() == 0){
                            Toast.makeText( ReceivedLetter_activity.this, "还没有已收到的回复，挚友马上就来咯~", Toast.LENGTH_SHORT).show();
                        }
                        receivedLetterAdapter = new ReceivedLetterAdapter( ReceivedLetter_activity.this, object );
                        initRV();
                    }else{
                        Log.i("bmob","失败："+e.getMessage());
                        Toast.makeText( ReceivedLetter_activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    private void initRV() {
        //设置LayoutManager
        receivedletter_recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        //设置动画效果
        receivedletter_recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        receivedletter_recyclerView.setAdapter(receivedLetterAdapter);
    }
}

