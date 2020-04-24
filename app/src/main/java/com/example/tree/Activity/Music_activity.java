package com.example.tree.Activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tree.Adapter.MusicAdapter;
import com.example.tree.Bean.Music;
import com.example.tree.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Music_activity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView musicRv;
    private TextView tv_name;
    private ImageView iv_last;
    private ImageView iv_play;
    private ImageView iv_next;
    //    数据源
    List<Music> mMusicList;
    private MusicAdapter musicAdapter;
    //    记录当前正在播放的音乐的位置
    int currentPlayPosition = -1;
    //    记录暂停音乐时进度条的位置
    int currentPausePositionInSong = 0;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_activity_layout);
        initView();
        mediaPlayer = new MediaPlayer();
        mMusicList = new ArrayList<>();
//        创建适配器对象
        musicAdapter = new MusicAdapter(this, mMusicList);
        musicRv.setAdapter(musicAdapter);
//        设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        musicRv.setLayoutManager(layoutManager);
        loadAessetsMusic();//        加载aessets文件夹里的音频
        setEventListener();//        设置每一项的点击事件
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Menu_activity.class);
        startActivity(intent);
    }

    private void setEventListener() {
        /*设置每一项的点击事件*/
        musicAdapter.setOnItemClickListener(new MusicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                currentPlayPosition = position;
                Music music = mMusicList.get(position);
                playMusicInMusic(music);
            }
        });
    }

    public void playMusicInMusic(Music music) {
        /*根据传入对象播放音乐*/
        tv_name.setText(music.getMusicName());
        stopMusic();
//                重置多媒体播放器
        mediaPlayer.reset();
//                设置新的播放路径
        try {
            mediaPlayer.setDataSource(music.getMusicPath());
            playMusic();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playMusic() {
        /*播放音乐*/
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            if (currentPausePositionInSong == 0) {
                try {
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
//                从暂停到播放
                mediaPlayer.seekTo(currentPausePositionInSong);
                mediaPlayer.start();
            }
            iv_play.setImageResource(R.drawable.music_iv_pause);
        }
    }

    private void pauseMusic() {
        /*暂停音乐*/
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            currentPausePositionInSong = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
            iv_play.setImageResource(R.drawable.music_iv_play);
        }
    }

    private void stopMusic() {
        /*停止音乐*/
        if(mediaPlayer != null){
            currentPausePositionInSong = 0;
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
            mediaPlayer.stop();
            iv_play.setImageResource(R.drawable.music_iv_play);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMusic();
    }

    /*
    1.调用list方法获取assets/music目录下的全部音乐名称；
    2.将音乐拷贝到某个具体目录下（newPath）；
    3.调用MediaMetadataRetriever类的setDataSource方法时传入新的路径；
    4.调用MediaMetadataRetriever类的extractMetadata(int keyCode)方法，检索与键值相关联的信息。
    */
    private void loadAessetsMusic(){
        try {
            String[] musicFileName = getResources().getAssets().list("music");
            if (musicFileName != null){
                for(int i = 0; i < musicFileName.length ; i++){
                    String packageName = this.getPackageName();
                    //定义存放音乐的内存路径
                    String path="/data/data/"+packageName;
                    copyMusic(this,musicFileName[i],path+"/"+musicFileName[i]);
                    File file=new File(path);
                    File[] subFile=file.listFiles();
                    getMusicInfo(path+"/"+musicFileName[i], i);
                }
            }
            musicAdapter.notifyDataSetChanged();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 将assets目录下的音乐复制到指定目录下
     */
    private void copyMusic(Context context, String oldPath, String newPath) {
        try {
            File f = new File(newPath);
            if(!f.exists()) {
                InputStream is = context.getAssets().open("music/"+oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount = -1;
                while((byteCount = is.read(buffer)) != -1) {//循环从输入流读取 buffer字节
                    fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
                }
                fos.flush();//刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取歌曲信息
     */
    private void getMusicInfo(String musicFileName, int i) {
        MediaMetadataRetriever myRetriever = new MediaMetadataRetriever();
        myRetriever.setDataSource(musicFileName);
        String name = musicFileName.substring(musicFileName.lastIndexOf("/")+1, musicFileName.lastIndexOf("."));//获得音乐标题
        String strDuration = myRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);//获取音乐时长信息
        long duration = Long.valueOf(strDuration);
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String time = sdf.format(new Date(duration));
        //创建music对象并赋值
        Music music=new Music(String.valueOf(i+1), name , time, musicFileName);
        mMusicList.add(music);
    }

    //    初始化View
    private void initView() {
        musicRv = findViewById(R.id.music_recyclerView);
        tv_name = findViewById(R.id.music_tv_name);
        iv_last = findViewById(R.id.music_iv_last);
        iv_play = findViewById(R.id.music_iv_play);
        iv_next = findViewById(R.id.music_iv_next);
        iv_last.setOnClickListener(this);
        iv_play.setOnClickListener(this);
        iv_next.setOnClickListener(this);
        findViewById(R.id.music_btn_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.music_iv_last:
                if (currentPlayPosition == -1) {
//                    并没有选中要播放的音乐
                    Toast.makeText(this, "请选择想要播放的音乐", Toast.LENGTH_SHORT).show();
                    return;
                }else if (currentPlayPosition == 0) {
                    Toast.makeText(this, "已经是第一首了，没有上一曲！", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentPlayPosition = currentPlayPosition - 1;
                Music lastMusic = mMusicList.get(currentPlayPosition);
                playMusicInMusic(lastMusic);
                break;
            case R.id.music_iv_play:
                if (currentPlayPosition == -1) {
//                    并没有选中要播放的音乐
                    Toast.makeText(this, "请选择想要播放的音乐", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mediaPlayer.isPlaying()) {
//                    此时处于播放状态，需要暂停音乐
                    pauseMusic();
                }else {
//                    此时没有播放音乐，点击开始播放音乐
                    playMusic();
                }
                break;
            case R.id.music_iv_next:
                if (currentPlayPosition == -1) {
//                    并没有选中要播放的音乐
                    Toast.makeText(this, "请选择想要播放的音乐", Toast.LENGTH_SHORT).show();
                    return;
                }else if (currentPlayPosition == mMusicList.size()-1) {
                    Toast.makeText(this, "已经是最后一首了，没有下一曲！", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentPlayPosition = currentPlayPosition + 1;
                Music nextMusic = mMusicList.get(currentPlayPosition);
                playMusicInMusic(nextMusic);
                break;
            case R.id.music_btn_back:
                Intent intent = new Intent(this, Menu_activity.class);
                startActivity(intent);
        }
    }
}
