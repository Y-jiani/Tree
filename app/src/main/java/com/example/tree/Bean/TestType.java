package com.example.tree.Bean;

import cn.bmob.v3.BmobObject;

public class TestType extends BmobObject {
    private Integer testId;
    private String testName;

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }


}
