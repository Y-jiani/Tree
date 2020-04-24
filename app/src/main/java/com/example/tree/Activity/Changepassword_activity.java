package com.example.tree.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tree.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class Changepassword_activity extends AppCompatActivity implements View.OnClickListener {

    private EditText changepassword_et_oldpassword, changepassword_et_newpassword, changepassword_et_confirm_newpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword_activity_layout);
        initview();
    }

    private void initview() {
        changepassword_et_oldpassword = (EditText) findViewById(R.id.changepassword_et_oldpassword);
        changepassword_et_newpassword = (EditText) findViewById(R.id.changepassword_et_newpassword);
        changepassword_et_confirm_newpassword = (EditText) findViewById(R.id.changepassword_et_confirm_newpassword);
        findViewById(R.id.changepassword_btn_changepassword).setOnClickListener(this);
        findViewById(R.id.changepassword_btn_back).setOnClickListener(this);
    }

    /*
    点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changepassword_btn_back:
                finish();
                break;
            case R.id.changepassword_btn_changepassword:
                if (!changepassword_et_newpassword.getText().toString().equals(changepassword_et_confirm_newpassword.getText().toString())) {
                    Toast.makeText(Changepassword_activity.this, "新密码与确认密码不一致!", Toast.LENGTH_SHORT).show();
                } else {
                    BmobUser.updateCurrentUserPassword(changepassword_et_oldpassword.getText().toString(), changepassword_et_newpassword.getText().toString(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(Changepassword_activity.this, "修改密码成功!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Changepassword_activity.this, "修改密码失败!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;

        }
    }
}
