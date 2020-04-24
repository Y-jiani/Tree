package com.example.tree.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tree.Utils.Constant;
import com.example.tree.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Login_activity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Login";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText login_et_name, login_btn_password;
    private CheckBox login_cb_display, login_cb_remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);
        initviews();

        //静态方法获取SharePreferences对象
        pref = getSharedPreferences("data", MODE_PRIVATE);

        boolean isRemeber = pref.getBoolean("cb_remember", false);
        //判断是否要记住账户密码
        if (isRemeber) {
            //从SharedPreference中获取保存的账户记录
            String name = pref.getString("edt_name", "");
            String password = pref.getString("edt_userpas", "");
            login_et_name.setText(name);
            login_et_name.setSelection(name.length());
            login_btn_password.setText(password);
            login_btn_password.setSelection(password.length());
            login_cb_remember.setChecked(true);
        }

        login_cb_display.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    login_btn_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    login_btn_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


    }

    private void initviews() {
        login_et_name = (EditText) findViewById(R.id.login_et_name);
        login_btn_password = (EditText) findViewById(R.id.login_btn_password);
        login_cb_display = (CheckBox) findViewById(R.id.login_cb_display);
        login_cb_remember = (CheckBox) findViewById(R.id.login_cb_remember);
        findViewById(R.id.login_btn_login).setOnClickListener(this);
        findViewById(R.id.login_btn_register).setOnClickListener(this);

    }

    public void onClick(View v) {
        //TODO Auto-generated method stub
        Intent intent;
        final EditText login_et_name = (EditText) findViewById(R.id.login_et_name);
        final EditText login_btn_password = (EditText) findViewById(R.id.login_btn_password);
        final CheckBox login_cb_remember = (CheckBox) findViewById(R.id.login_cb_remember);
        final String name = this.login_et_name.getText().toString();
        final String password = login_btn_password.getText().toString();
        switch (v.getId()) {
            case R.id.login_btn_register:
                intent = new Intent(Login_activity.this, Register_activity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.login_btn_login:
                final BmobUser userlogin = new BmobUser();
                userlogin.setUsername(login_et_name.getText().toString());
                userlogin.setPassword(login_btn_password.getText().toString());
                userlogin.login(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if (e == null) {
                            String token = userlogin.getSessionToken();
                            String id = userlogin.getObjectId();
                            Constant.id = id;
                            Constant.token = token;
                            Toast.makeText(Login_activity.this, bmobUser.getUsername() + "登陆成功!", Toast.LENGTH_SHORT).show();
                            editor = pref.edit();
                            if (login_cb_remember.isChecked()) {
                                editor.putBoolean("cb_remember", true);
                                editor.putString("edt_name", name);
                                editor.putString("edt_userpas", password);
                            } else {
                                editor.clear();
                            }
                            editor.apply();
                            Intent intent = new Intent(Login_activity.this, Menu_activity.class);
                            Constant.name = name;
                            Constant.password = password;
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Login_activity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }
}
