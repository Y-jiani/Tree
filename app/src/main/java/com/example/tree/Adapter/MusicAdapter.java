package com.example.tree.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tree.Bean.Music;
import com.example.tree.R;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicHolder>{
    Context context;
    private List<Music> musicList;
    private MusicAdapter.MusicHolder musicHolder;
    private MusicAdapter.OnItemClickListener onItemClickListener;

    public MusicAdapter(Context context, List<Music> musicList) {
        this.context = context;
        this.musicList = musicList;
    }

    @NonNull
    @Override
    public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext ()).inflate ( R.layout.music_recycle_item,parent,false);
        MusicHolder musicHolder = new MusicHolder(view);
        return musicHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicHolder holder, final int position) {
        Music item = musicList.get (position);
        holder.music_item_tv_num.setText (item.getMusicNum());
        holder.music_item_tv_name.setText (item.getMusicName());
        holder.music_item_tv_time.setText( item.getMusicDuration());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return musicList.size ();
    }

    class MusicHolder extends RecyclerView.ViewHolder{
        RelativeLayout music_recycle_item;
        TextView music_item_tv_num;
        TextView music_item_tv_name;
        TextView music_item_tv_time;
        public MusicHolder(View itemView) {
            super (itemView);
            music_recycle_item = itemView.findViewById (R.id.music_recycle_item);
            music_item_tv_num = itemView.findViewById(R.id.music_item_tv_num);
            music_item_tv_name=itemView.findViewById(R.id.music_item_tv_name);
            music_item_tv_time=itemView.findViewById(R.id.music_item_tv_time);
        }
    }

    //    点击 RecyclerView 某条的监听
    public interface OnItemClickListener{
        //        当RecyclerView某个被点击的时候回调, view 点击item的视图, data 点击得到的数据
        void onItemClick(View view, int position);
    }
    //    设置RecyclerView某个的监听onItemClickListener
    public void setOnItemClickListener(MusicAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
