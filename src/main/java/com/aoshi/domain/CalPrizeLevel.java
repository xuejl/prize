package com.aoshi.domain;

public class CalPrizeLevel {
    private Integer prizeLevelId;

    private String prizeLevelName;

    public Integer getPrizeLevelId() {
        return prizeLevelId;
    }

    public void setPrizeLevelId(Integer prizeLevelId) {
        this.prizeLevelId = prizeLevelId;
    }

    public String getPrizeLevelName() {
        return prizeLevelName;
    }

    public CalPrizeLevel setPrizeLevelName(String prizeLevelName) {
        this.prizeLevelName = prizeLevelName == null ? null : prizeLevelName.trim();
        return this;
    }
}