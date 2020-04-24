package com.example.tree.Bean;

import cn.bmob.v3.BmobObject;

public class TestRecord extends BmobObject {

    private _User username;//用户名
    private TestType testtype;
    private String testscore;//答题分数

    public TestType getTesttype() {
        return testtype;
    }

    public void setTesttype(TestType testtype) {
        this.testtype = testtype;
    }
    public _User getUsername() {
        return username;
    }

    public void setUsername(_User username) {
        this.username = username;
    }


    public String getTestscore() {
        return testscore;
    }

    public void setTestscore(String testscore) {
        this.testscore = testscore;
    }

}
