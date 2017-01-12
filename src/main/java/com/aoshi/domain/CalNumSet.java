package com.aoshi.domain;

public class CalNumSet {
    private Integer numSetId;

    private Integer maxNum;

    private Integer minNum;

    private Integer time;

    private String isReject;

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

    public String getIsReject() {
        return isReject;
    }

    public void setIsReject(String isReject) {
        this.isReject = isReject;
    }
}