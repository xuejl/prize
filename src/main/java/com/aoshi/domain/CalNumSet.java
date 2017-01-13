package com.aoshi.domain;

public class CalNumSet {
    private Integer numSetId;

    private Integer maxNum;

    private Integer minNum;

    private Integer time;

    private String isreject;

    private String lotteryType;

    //非数据库字段，用于页面显示
    private String str;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Integer getNumSetId() {
        return numSetId;
    }

    public void setNumSetId(Integer numSetId) {
        this.numSetId = numSetId;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public Integer getMinNum() {
        return minNum;
    }

    public void setMinNum(Integer minNum) {
        this.minNum = minNum;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getIsreject() {
        return isreject;
    }

    public void setIsreject(String isreject) {
        this.isreject = isreject;
    }

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }
}