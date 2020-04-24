package com.example.tree.Bean;

import cn.bmob.v3.BmobObject;

public class Story extends BmobObject {
    String storyTitle;
    String storyBody;
    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    public String getStoryBody() {
        return storyBody;
    }

    public void setStoryBody(String storyBody) {
        this.storyBody = storyBody;
    }

}
