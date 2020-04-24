package com.example.tree.Bean;

public class Answerstate {
    private String selectitem;//选择的内容
    private int item_id;//选择的按钮id
    private Boolean finishState;//题目完成标志
    public String getSelectitem() {
        return selectitem;
    }

    public void setSelectitem(String selectitem) {
        this.selectitem = selectitem;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public Boolean getFinishState() {
        return finishState;
    }

    public void setFinishState(Boolean finishState) {
        this.finishState = finishState;
    }

}

