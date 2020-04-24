package com.example.tree.Bean;

import cn.bmob.v3.BmobObject;


public class SentLetter extends BmobObject {
    private int sentId;
    private _User sentUser;
    private String letterTitle;
    private String letterBody;
    private String sentType;
//    private java.util.Date createdAt;

//    @Override
//    public String getCreatedAt() {
//        return createdAt.toString();
//    }
//
//    public void setCreatedAt(java.util.Date createdAt) {
//        this.createdAt = createdAt;
//    }

    public int getSentId() {
        return sentId;
    }

    public void setSentId(int sentId) {
        this.sentId = sentId;
    }

    public _User getSentUser() {
        return sentUser;
    }

    public void setSentUser(_User sentUser) {
        this.sentUser = sentUser;
    }

    public String getLetterTitle() {
        return letterTitle;
    }

    public void setLetterTitle(String letterTitle) {
        this.letterTitle = letterTitle;
    }

    public String getLetterBody() {
        return letterBody;
    }

    public void setLetterBody(String letterBody) {
        this.letterBody = letterBody;
    }

    public String getSentType() {
        return sentType;
    }

    public void setSentType(String sentType) {
        this.sentType = sentType;
    }
}
