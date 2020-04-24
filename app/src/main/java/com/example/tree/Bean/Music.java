package com.example.tree.Bean;

public class Music {
    private String musicNum;//白噪音序号
    private String musicName;//白噪音曲名
    private String musicDuration;//白噪音时长
    private String musicPath;//白噪音路径

    public Music(String musicNum, String musicName, String musicDuration, String musicPath){
        this.musicNum = musicNum;
        this.musicName = musicName;
        this.musicDuration = musicDuration;
        this.musicPath = musicPath;
    }

    public String getMusicNum() {
        return musicNum;
    }

    public void setMusicNum(String musicNum) {
        this.musicNum = musicNum;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getMusicDuration() {
        return musicDuration;
    }

    public void setMusicDuration(String musicDuration) {
        this.musicDuration = musicDuration;
    }

    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicDuration) {
        this.musicPath = musicPath;
    }
}
