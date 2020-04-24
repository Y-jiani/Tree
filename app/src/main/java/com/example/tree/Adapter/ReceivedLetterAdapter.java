package com.example.tree.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tree.Activity.Readletter_activity;
import com.example.tree.Activity.Readreceivedletter_activity;
import com.example.tree.Bean.ReceivedLetter;
import com.example.tree.R;

import java.util.List;

public class ReceivedLetterAdapter extends RecyclerView.Adapter<ReceivedLetterAdapter.ReceivedLetterHolder> {

    Context context;
    private List<ReceivedLetter> receivedLetterList;
    private ReceivedLetterHolder receivedLetterHolder;
    private static final String TAG ="Rong";
    private ReceivedLetterAdapter.OnItemClickListener onItemClickListener;

    public ReceivedLetterAdapter(Context context, List<ReceivedLetter> receivedLetterList) {
        this.context = context;
        this.receivedLetterList = receivedLetterList;
    }

    @NonNull
    @Override
    public ReceivedLetterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext()).inflate ( R.layout.letter_recycle_item ,parent,false);
        receivedLetterHolder = new ReceivedLetterHolder(view);
        return receivedLetterHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReceivedLetterHolder holder, int position) {
        ReceivedLetter item = receivedLetterList.get(position);
        holder.letter_tv_letterDate.setText(item.getUpdatedAt().substring( 0, 10 ));
        holder.letter_tv_title.setText(item.getReceivedLetter().getLetterTitle());
    }

    @Override
    public int getItemCount() {
        return receivedLetterList.size ();
    }

    class ReceivedLetterHolder extends RecyclerView.ViewHolder{
        RelativeLayout letter_recycle_item;
        TextView letter_tv_letterDate;
        TextView letter_tv_title;
        public ReceivedLetterHolder(View itemView) {
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
                    ReceivedLetter letter = receivedLetterList.get(getLayoutPosition());
                    Intent intent = new Intent(context, Readreceivedletter_activity.class);
                    intent.putExtra("objectId", letter.getObjectId());
//                    Log.i(TAG,"flag"+v.getId());
//                    if (v.getId() == R.id.repliedletter_recyclerView) {
//                        intent.putExtra("flag1", 1);
//                    }else if (v.getId() == R.id.receivedletter_recyclerView){
//                        intent.putExtra("flag1", 2);
//                    }
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
