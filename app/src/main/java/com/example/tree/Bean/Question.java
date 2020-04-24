package com.example.tree.Bean;

import cn.bmob.v3.BmobObject;

public class Question extends BmobObject{
    private String questionID;
    private Integer questionType;
    private String question;
    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
