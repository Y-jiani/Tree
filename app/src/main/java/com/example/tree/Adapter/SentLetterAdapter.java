package com.example.tree.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tree.Activity.Readletter_activity;
import com.example.tree.Bean.SentLetter;
import com.example.tree.R;

import java.util.List;

public class SentLetterAdapter extends RecyclerView.Adapter<SentLetterAdapter.SentLetterHolder>{

    Context context;
    private List<SentLetter> sentLetterList;
    private SentLetterHolder sentLetterHolder;
    private OnItemClickListener onItemClickListener;
    private String TAG = "xhy";

    public SentLetterAdapter(Context context, List<SentLetter> sentLetterList) {
        this.context = context;
        this.sentLetterList = sentLetterList;
    }

    @NonNull
    @Override
    public SentLetterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext()).inflate ( R.layout.letter_recycle_item ,parent,false);
        sentLetterHolder = new SentLetterHolder(view);
        return sentLetterHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SentLetterHolder holder, int position) {
        SentLetter item = sentLetterList.get(position);
        holder.letter_tv_letterDate.setText( item.getUpdatedAt().substring( 0, 10 ) );
        holder.letter_tv_title.setText (item.getLetterTitle());
    }

    @Override
    public int getItemCount() {
        return sentLetterList.size ();
    }

    class SentLetterHolder extends RecyclerView.ViewHolder{
        RelativeLayout letter_recycle_item;
        TextView letter_tv_letterDate;
        TextView letter_tv_title;
        public SentLetterHolder(View itemView) {
            super (itemView);
            letter_recycle_item = itemView.findViewById ( R.id.letter_recycle_item);
            letter_tv_letterDate  = itemView.findViewById( R.id.letter_tv_letterDate);
            letter_tv_title = itemView.findViewById( R.id.letter_tv_title);

            //添加点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(v, getLayoutPosition());
                    }
                    SentLetter sentLetter = sentLetterList.get(getLayoutPosition());
                    Intent intent = new Intent(context, Readletter_activity.class);
                    intent.putExtra("objectId", sentLetter.getObjectId());
                    intent.putExtra("flag", 1);
                    Log.i( TAG, "onClick: " + sentLetter.getObjectId());
                    context.startActivity(intent);
                }
            });

        }
    }

    //    点击 RecyclerView 某条的监听
    public interface OnItemClickListener{
        //        当RecyclerView某个被点击的时候回调, view 点击item的视图, data 点击得到的数据
        void onItemClick(View view, Integer data);
    }
    //    设置RecyclerView某个的监听onItemClickListener
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

}
