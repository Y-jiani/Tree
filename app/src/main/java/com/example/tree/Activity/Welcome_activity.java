package com.example.tree.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.tree.R;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.Bmob;
import cn.jpush.im.android.api.JMessageClient;

public class Welcome_activity extends AppCompatActivity {

    private int recLen = 2;//跳过倒计时提示5秒
    private Button mBtn;
    Timer timer = new Timer();
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity_layout);
        //初始化bmob
        Bmob.initialize(this, "72906826fef7f7cc5f15b752ce12774d");
//        查看是否已获取权限
        if (ContextCompat.checkSelfPermission( this, Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1 );
        }
        else {
            init();
        }
//        极光IM
//        调试模式会输出信息,方便找错
        JMessageClient.setDebugMode(true);
        JMessageClient.init(getApplicationContext(),true);
    }

//    申请获取权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //flag是我们必须要设置的变量
                    init();
                }
                else {
                    Toast.makeText( this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT ).show();
                }
                break;
            default:
        }
    }

    public void init() {
        //flag是我们必须要设置的变量
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags( flag, flag );
        timer.schedule( task, 1000, 1000 );//等待时间一秒，停顿时间一秒
        /**
         * 正常情况下不点击跳过
         */
        handler = new Handler();
        handler.postDelayed( runnable = new Runnable() {
            @Override
            public void run() {
                //跳转到首界面
                Intent intent = new Intent( Welcome_activity.this, Login_activity.class );
                startActivity( intent );
                finish();
            }
        }, 2000 );//延迟5S后发送handler信息
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    recLen--;
                    if (recLen < 0) {
                        timer.cancel();
                    }
                }
            });
        }
    };
}
