package com.example.tree.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tree.Adapter.StoryAdapter;
import com.example.tree.Bean.Story;
import com.example.tree.R;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Story_fragment extends Fragment {
    private View view;
    private RecyclerView story_recyclerView;
    private StoryAdapter storyAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //相当于activity的onCreate里的setcontentView,给它一个布局文件返回一个视图文件
        view = inflater.inflate(R.layout.story_fragment_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //当View创建完成后，这里有一个回调方法
        super.onViewCreated(view, savedInstanceState);
        initView();
        queryStory();
    }

    private void queryStory() {
        BmobQuery<Story> query = new BmobQuery<>();
        query.order("-createdAt");
        query.findObjects(new FindListener<Story>() {
            @Override
            public void done(List<Story> object, BmobException e) {
                if (e == null) {
                    if(object.size() == 0){
                        Toast.makeText( getContext(), "还没有写好我们的故事，再等等吧~", Toast.LENGTH_SHORT).show();
                    }
                    storyAdapter = new StoryAdapter( getContext(), object );
                    initRV();
                } else {
                    Log.e("BMOB", e.toString());
                    Toast.makeText( getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        story_recyclerView = view.findViewById(R.id.story_recyclerView);
    }

    private void initRV() {
        //设置LayoutManager
        story_recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        //设置动画效果
        story_recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        story_recyclerView.setAdapter(storyAdapter);
    }
}