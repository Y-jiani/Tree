package com.example.tree.Bean;

import cn.bmob.v3.BmobObject;

public class ReceivedLetter extends BmobObject {
    private SentLetter receivedLetter;
    private _User receivedUser;
    private _User sentUser;
    private String replyBody;
    private String letterType;

    public String getLetterType() {
        return letterType;
    }

    public void setLetterType(String letterType) {
        this.letterType = letterType;
    }


    public SentLetter getReceivedLetter() {
        return receivedLetter;
    }

    public void setReceivedLetter(SentLetter receivedLetter) {
        this.receivedLetter = receivedLetter;
    }

    public _User getReceivedUser() {
        return receivedUser;
    }

    public void setReceivedUser(_User receivedUser) {
        this.receivedUser = receivedUser;
    }

    public String getReplyBody() {
        return replyBody;
    }

    public void setReplyBody(String replyBody) {
        this.replyBody = replyBody;
    }

    public _User getSentUser() {
        return sentUser;
    }

    public void setSentUser(_User sentUser) {
        this.sentUser = sentUser;
    }
}
