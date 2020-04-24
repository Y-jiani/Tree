package com.example.tree.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tree.Activity.Test_activity;
import com.example.tree.R;

public class Test_fragment extends Fragment implements View.OnClickListener{
    private Button test_item_btn_test1;
    private Button test_item_btn_test2;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //相当于activity的onCreate里的setcontentView,给它一个布局文件返回一个视图文件
        view=inflater.inflate(R.layout.test_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //当View创建完成后，这里有一个回调方法
        super.onViewCreated(view, savedInstanceState);
        initView();
    }
    private void initView() {
        test_item_btn_test1 = view.findViewById(R.id.test_item_btn_test1);
        test_item_btn_test2 = view.findViewById(R.id.test_item_btn_test2);
        test_item_btn_test1.setOnClickListener(this);
        test_item_btn_test2.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_item_btn_test1:
                Intent intent1 = new Intent(getContext(), Test_activity.class);
                intent1.putExtra("testType", 1);
                startActivity(intent1);
                break;
            case R.id.test_item_btn_test2:
                Intent intent2 = new Intent(getContext(), Test_activity.class);
                intent2.putExtra("testType", 2);
                startActivity(intent2);
                break;
        }
    }
}