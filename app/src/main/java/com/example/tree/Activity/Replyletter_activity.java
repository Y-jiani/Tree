package com.example.tree.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tree.Bean.ReceivedLetter;
import com.example.tree.Utils.StackManager;
import com.example.tree.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class Replyletter_activity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Replyletter_activity";
    private TextView writeletter_tv_title;
    private EditText writeletter_et_letterbody;
    private Intent intent;
    public static StackManager mStackManager;
    private ReceivedLetter receivedletter;
    private View dialogView;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replyletter_activity_layout);
        initView();
    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        receivedletter = (ReceivedLetter) bundle.get("receivedletter");
        String title = getIntent().getStringExtra("title") ;
        writeletter_tv_title = findViewById(R.id.writeletter_tv_title);
        writeletter_et_letterbody = findViewById(R.id.writeletter_et_letterbody);
        findViewById(R.id.writeletter_btn_back).setOnClickListener(this);
        findViewById(R.id.writeletter_btn_sent).setOnClickListener(this);
        writeletter_tv_title.setText(title);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.writeletter_btn_back:
                showDialog();
                break;
            case R.id.writeletter_btn_sent:
                if(writeletter_et_letterbody.getText().toString().trim().equals("")){
                    Toast.makeText(Replyletter_activity.this, "内容为空!", Toast.LENGTH_SHORT).show();
                }
                else{
                    sentLetter();
                    intent = new Intent(Replyletter_activity.this, Menu_activity.class);
                    startActivity(intent);
                    finish();
                    mStackManager.getStackManager().popTopActivity(Getletter_activity.class);
                }
                break;
        }
    }

    private void showDialog(){
        dialogView = LayoutInflater.from(Replyletter_activity.this).inflate(R.layout.reply_dialog_layout,null,false);
        final AlertDialog replyDialog = new AlertDialog.Builder(Replyletter_activity.this).setView(dialogView).create();
        replyDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Button replydialog_btn_out = dialogView.findViewById(R.id.replydialog_btn_out);
        Button replydialog_btn_cancel = dialogView.findViewById(R.id.replydialog_btn_cancel);

        replydialog_btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Replyletter_activity.this, Menu_activity.class);
                startActivity(intent);
                finish();
                replyDialog.dismiss();

            }
        });

        replydialog_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replyDialog.dismiss();
            }
        });
        replyDialog.show();
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = replyDialog.getWindow().getAttributes();
        lp.width = (int)(display.getWidth() * 0.7); //设置宽度
        replyDialog.getWindow().setAttributes(lp);
    }

    private void sentLetter() {
        receivedletter.setReplyBody(writeletter_et_letterbody.getText().toString());
        receivedletter.setLetterType("回信");
        receivedletter.update(receivedletter.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(Replyletter_activity.this, "回信成功!", Toast.LENGTH_SHORT).show();
                    Log.i(TAG,"上传成功!");

                }else{
                    Toast.makeText(Replyletter_activity.this, "回信失败!"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
