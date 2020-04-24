package com.example.tree.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tree.Bean.SentLetter;
import com.example.tree.Bean._User;
import com.example.tree.R;
import com.example.tree.Utils.ToastUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Writeletter_activity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG ="Rong";
    private EditText writeletter_et_title;
    private EditText writeletter_et_letterbody;
    private Context context= Writeletter_activity.this;
    private Intent intent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writeletter_activity_layout);
        initView();
    }
    private void initView() {
        writeletter_et_title = findViewById(R.id.writeletter_et_title);
        writeletter_et_letterbody= findViewById(R.id.writeletter_et_letterbody);
        findViewById(R.id.writeletter_btn_back).setOnClickListener(this);
        findViewById(R.id.writeletter_btn_sent).setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.writeletter_btn_back:
                if(!(writeletter_et_title.getText().toString().trim().equals("")&&writeletter_et_letterbody.getText().toString().trim().equals(""))){
                    showDialog("小一，你要放弃这次的信件吗？");
                }else{
                    finish();
                }
                break;
            case R.id.writeletter_btn_sent:
                if(writeletter_et_title.getText().toString().trim().equals("")&&writeletter_et_letterbody.getText().toString().trim().equals("")){
                    showDialog("小一，不能上传空信哟！");
                }else if(writeletter_et_title.getText().toString().trim().equals("")){
                    showDialog("小一，别忘了写标题哟！");
                }else if(writeletter_et_letterbody.getText().toString().trim().equals("")){
                    showDialog("小一，别忘了写信的内容哟！");}
                else{
                    sentLetter();
                    finish();
                }
                break;
        }
    }
    private void sentLetter(){
        //创建发信的信息
        SentLetter sentletter=new SentLetter();
        //获取当前用户
        _User user= BmobUser.getCurrentUser(_User.class);
        sentletter.setLetterTitle(writeletter_et_title.getText().toString());
        sentletter.setLetterBody(writeletter_et_letterbody.getText().toString());
        sentletter.setSentUser(user);
        sentletter.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    ToastUtils.show(context,"添加数据成功，返回objectId为："+s);
                }else{
                    ToastUtils.show(context,"创建数据失败：" + e.getMessage());
                }

            }
        });
    }

    public void showDialog(String message){
        AlertDialog.Builder dialog=new AlertDialog.Builder(Writeletter_activity.this);
        dialog.setTitle("提醒");
        dialog.setMessage(message);
        dialog.setPositiveButton("下次写", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.setNegativeButton("现在写", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
        dialog.show();
    }
//    private void isEditTextNull() {
//        writeletter_et_title.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (!"".equals(editable.toString())){
//                    writeletter_btn_sent.setEnabled(true);
//                }else{
//                    writeletter_btn_sent.setEnabled(false);
//                }
//            }
//        });
//
//    }

}
