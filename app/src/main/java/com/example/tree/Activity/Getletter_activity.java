package com.example.tree.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tree.Bean.ReceivedLetter;
import com.example.tree.Bean.SentLetter;
import com.example.tree.Utils.StackManager;
import com.example.tree.Bean._User;
import com.example.tree.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class Getletter_activity extends AppCompatActivity implements View.OnClickListener {


    public static StackManager mStackManager;
    private String title,titlebody;
    private static final String TAG ="yu";
    private TextView readletter_tv_title,readletter_tv_date, readletter_tv_letterbody;
    private Button readletter_btn_reply;
    private Intent intent;

    public SentLetter sentletter;
    ReceivedLetter receivedletter;
    _User sentuser,receiveduser;
    List<SentLetter> mSentLetter=new ArrayList<>();
    List<ReceivedLetter>mReceivedLetter=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readletter_activity_layout);
        initquery();
        initview();
    }


    private void initquery() {
        Log.i(TAG,"开始查询");
        receiveduser= BmobUser.getCurrentUser(_User.class);
        BmobQuery<SentLetter> SendletterBmobQuery = new BmobQuery<SentLetter>();
        SendletterBmobQuery.setLimit(500);
        SendletterBmobQuery.addWhereNotEqualTo("sentUser", BmobUser.getCurrentUser(_User.class));
        SendletterBmobQuery.include("sentUser");
        SendletterBmobQuery.findObjects(new FindListener<SentLetter>() {
            @Override
            public void done(List<SentLetter> object, BmobException e) {
                if (e == null) {
                    mSentLetter.addAll(object);
                    Random random = new Random();
                    int num = random.nextInt(mSentLetter.size()-1)%(mSentLetter.size()-1) + 0;
                    sentletter = mSentLetter.get(num);
                    sentuser = sentletter.getSentUser();
                    receivedletter = new ReceivedLetter();
                    initviewContent();
                    queryReceivedLetter();
                } else {
                    Toast.makeText(Getletter_activity.this, "获取信件失败!"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void queryReceivedLetter() {
        BmobQuery<ReceivedLetter> eq1 = new BmobQuery<ReceivedLetter>();
        eq1.include("receivedLetter");
        eq1.addWhereEqualTo("receivedLetter", sentletter);
        BmobQuery<ReceivedLetter> eq2 = new BmobQuery<ReceivedLetter>();//--and条件2
        eq2.include("receivedUser");
        eq2.addWhereGreaterThanOrEqualTo("receivedUser", BmobUser.getCurrentUser(_User.class));
        List<BmobQuery<ReceivedLetter>> andQuerys = new ArrayList<BmobQuery<ReceivedLetter>>();//最后组装完整的and条件
        andQuerys.add(eq1);
        andQuerys.add(eq2);
        BmobQuery<ReceivedLetter> query = new BmobQuery<ReceivedLetter>();//查询符合整个and条件的
        query.and(andQuerys);
        query.findObjects(new FindListener<ReceivedLetter>() {
            @Override
            public void done(List<ReceivedLetter> object, BmobException e) {
                if(e==null){
                    mReceivedLetter.addAll(object);
                    receivedletter = mReceivedLetter.get(0);
                }else{
                    Log.i("bmob","查询mReceivedLetter失败："+e.getMessage()+","+e.getErrorCode());
                    receivedletter.setReceivedLetter(sentletter);
                    receivedletter.setReceivedUser(receiveduser);
                    receivedletter.setSentUser(sentuser);
                    receivedletter.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                Log.i(TAG,"receivedletter+创建数据成功");
                            }else{
                                Log.i(TAG,"receivedletter+创建数据失败");
                            }
                        }
                    });
                }
            }
        });
    }

    private void initviewContent() {
        readletter_tv_date.setText(sentletter.getCreatedAt());
        readletter_tv_title.setText(sentletter.getLetterTitle());
        readletter_tv_letterbody.setText(sentletter.getLetterBody());
    }

    private void initview() {
        readletter_tv_title = (TextView) findViewById(R.id.readletter_tv_title);
        readletter_tv_date = (TextView)findViewById(R.id.readletter_tv_date);
        readletter_tv_letterbody = (TextView)findViewById(R.id.readletter_tv_letterbody);
        readletter_btn_reply = (Button) findViewById(R.id.readletter_btn_reply);
        readletter_btn_reply.setVisibility(View.VISIBLE);
        findViewById(R.id.readletter_btn_back).setOnClickListener(this);
        findViewById(R.id.readletter_btn_reply).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.readletter_btn_back:
                intent = new Intent(Getletter_activity.this, Menu_activity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.readletter_btn_reply:
                Intent intent = new Intent();
                intent.putExtra("receivedletter",receivedletter);
                intent.putExtra("title",sentletter.getLetterTitle());
                intent.setClass(Getletter_activity.this,Replyletter_activity.class);
                startActivity(intent);
                mStackManager.getStackManager().pushActivity(this);
                break;
        }
    }
}
