package com.example.tree.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tree.Activity.Readletter_activity;
import com.example.tree.Bean.Story;
import com.example.tree.R;
import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryHolder>{
    Context context;
    private List<Story> storyList;
    private StoryHolder storyHolder;
    private StoryAdapter.OnItemClickListener onItemClickListener;

    public StoryAdapter(Context context, List<Story> storyList) {
        this.context = context;
        this.storyList = storyList;
    }

    @NonNull
    @Override
    public StoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext ()).inflate ( R.layout.story_recycle_item,parent,false);
        StoryHolder storyHolder = new StoryHolder(view);
        return storyHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StoryHolder holder, final int position) {
        Story item = storyList.get (position);
        holder.story_item_tv_date.setText (item.getUpdatedAt().substring(0, 10));
        holder.story_item_tv_storyTitle.setText (item.getStoryTitle());
    }

    @Override
    public int getItemCount() {
        return storyList.size ();
    }

    class StoryHolder extends RecyclerView.ViewHolder{
        RelativeLayout story_recycle_item;
        TextView story_item_tv_date;
        TextView story_item_tv_storyTitle;
        public StoryHolder(View itemView) {
            super (itemView);
            story_recycle_item = itemView.findViewById (R.id.story_recycle_item);
            story_item_tv_date = itemView.findViewById(R.id.story_item_tv_date);
            story_item_tv_storyTitle=itemView.findViewById(R.id.story_item_tv_storyTitle);

            //添加点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(v, getLayoutPosition());
                    }
                    Story story = storyList.get(getLayoutPosition());
                    Intent intent = new Intent(context, Readletter_activity.class);
                    intent.putExtra("objectId", story.getObjectId());
                    intent.putExtra("flag", 2);
                    context.startActivity(intent);
                }
            });
        }
    }

    //    点击 RecyclerView 某条的监听
    public interface OnItemClickListener{
        //        当RecyclerView某个被点击的时候回调, view 点击item的视图, data 点击得到的数据
        void onItemClick(View view, int position);
    }
    //    设置RecyclerView某个的监听onItemClickListener
    public void setOnItemClickListener(StoryAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
