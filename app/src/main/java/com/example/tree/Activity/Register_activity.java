package com.example.tree.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tree.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Register_activity extends AppCompatActivity implements View.OnClickListener {

    private EditText register_et_name,register_et_email,register_et_password,register_et_confirmpassword;
    private CheckBox register_cb_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_layout);
        initviews();

        register_cb_display.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    register_et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    register_et_confirmpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    register_et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    register_et_confirmpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        register_et_confirmpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                } else {
                    if (!register_et_password.getText().toString().equals(register_et_confirmpassword.getText().toString())) {
                        Toast.makeText(Register_activity.this, "密码与确认密码不一致!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void initviews() {
        register_et_name = (EditText) findViewById(R.id.register_et_name);
        register_et_email = (EditText) findViewById(R.id.register_et_email);
        register_et_password = (EditText) findViewById(R.id.register_et_password);
        register_et_confirmpassword = (EditText) findViewById(R.id.register_et_confirmpassword);
        register_cb_display = (CheckBox) findViewById(R.id.register_cb_display);
        findViewById(R.id.register_btn_back).setOnClickListener(this);
        findViewById(R.id.register_btn_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //TODO Auto-generated method stub
        Intent intent;
        int flag = 1;
        final EditText register_et_name = (EditText) findViewById(R.id.register_et_name);
        final EditText register_et_email = (EditText) findViewById(R.id.register_et_email);
        final EditText register_et_password = (EditText) findViewById(R.id.register_et_password);
        final EditText register_et_confirmpassword = (EditText) findViewById(R.id.register_et_confirmpassword);
        switch (v.getId()){
            case R.id.register_btn_back:
                intent = new Intent(Register_activity.this,Login_activity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.register_btn_register:
                if (!register_et_password.getText().toString().equals(register_et_confirmpassword.getText().toString())) {
                    Toast.makeText(Register_activity.this, "密码与确认密码不一致!", Toast.LENGTH_SHORT).show();
                    flag = 0;
                }
                if(flag == 1){
                    BmobUser user = new BmobUser();
                    user.setUsername(register_et_name.getText().toString());
                    user.setEmail(register_et_email.getText().toString());
                    user.setPassword(register_et_password.getText().toString());
                    user.signUp(new SaveListener<BmobUser>() {
                        @Override
                        public void done(BmobUser bmobUser, BmobException e) {
                            if(e==null){
                                Toast.makeText(Register_activity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Register_activity.this,Login_activity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(Register_activity.this, "用户名或邮箱已存在！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
        }
    }
}
