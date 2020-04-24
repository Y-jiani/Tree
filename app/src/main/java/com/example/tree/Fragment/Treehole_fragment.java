package com.example.tree.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tree.Activity.Getletter_activity;
import com.example.tree.Activity.ReceivedLetter_activity;
import com.example.tree.Activity.RepliedLetter_activity;
import com.example.tree.Activity.SentLetter_activity;
import com.example.tree.Activity.Writeletter_activity;
import com.example.tree.R;

public class Treehole_fragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "Rong";
    private View view;
    private Intent intent;
    private Button treehole_btn_writtedLetter;
    private Button treehole_btn_repliedLetter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //相当于activity的onCreate里的setcontentView,给它一个布局文件返回一个视图文件
        view=inflater.inflate( R.layout.treehole_fragment_layout,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //当View创建完成后，这里有一个回调方法
        super.onViewCreated(view, savedInstanceState);
        initView();
    }
    //    初始化View
    private void initView() {
        treehole_btn_writtedLetter = view.findViewById( R.id.treehole_btn_writtedLetter);
        treehole_btn_repliedLetter = view.findViewById( R.id.treehole_btn_repliedLetter);
        treehole_btn_writtedLetter.setOnClickListener(this);
        treehole_btn_repliedLetter.setOnClickListener(this);

        view.findViewById( R.id.treehole_rl_sending).setOnClickListener(this);
        view.findViewById( R.id.treehole_btn_moreSend).setOnClickListener(this);
        view.findViewById( R.id.treehole_rl_receiving).setOnClickListener(this);
        view.findViewById( R.id.treehole_btn_moreReceive).setOnClickListener(this);
        view.findViewById( R.id.treehole_btn_writeletter).setOnClickListener(this);
        view.findViewById( R.id.treehole_btn_getletter).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.treehole_rl_sending:
            case R.id.treehole_btn_moreSend:
                Log.i(TAG,"点击了");
                if (treehole_btn_writtedLetter.getVisibility() == View.GONE && treehole_btn_repliedLetter.getVisibility() == View.GONE){
                    treehole_btn_writtedLetter.setVisibility( View.VISIBLE );
                    treehole_btn_repliedLetter.setVisibility( View.VISIBLE );
                }else {
                    treehole_btn_writtedLetter.setVisibility( View.GONE );
                    treehole_btn_repliedLetter.setVisibility( View.GONE );
                }
                break;
            case R.id.treehole_btn_writtedLetter:
                Log.i(TAG,"点击了");
                intent = new Intent(getContext(), SentLetter_activity.class);
                startActivity(intent);
                break;
            case R.id.treehole_btn_repliedLetter:
                Log.i(TAG,"点击了");
                intent = new Intent(getContext(), RepliedLetter_activity.class);
                startActivity(intent);
                break;
            case R.id.treehole_rl_receiving:
            case R.id.treehole_btn_moreReceive:
                Log.i(TAG,"点击了");
                intent = new Intent(getContext(), ReceivedLetter_activity.class);
                startActivity(intent);
                break;
            case R.id.treehole_btn_writeletter:
                Log.i(TAG,"点击了");
                intent = new Intent(getContext(), Writeletter_activity.class);
                startActivity(intent);
                break;
            case R.id.treehole_btn_getletter:
                intent = new Intent(getContext(), Getletter_activity.class);
                startActivity(intent);
                break;

        }
    }
}
