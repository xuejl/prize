package com.aoshi.domain;

public class CalPrize {
    private Integer prizeId;

    private String prizeName;

    private Integer prizeLevelId;

    private Integer numSetId;

    private Integer remainTime;

    public Integer getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(Integer prizeId) {
        this.prizeId = prizeId;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName == null ? null : prizeName.trim();
    }

    public Integer getPrizeLevelId() {
        return prizeLevelId;
    }

    public void setPrizeLevelId(Integer prizeLevelId) {
        this.prizeLevelId = prizeLevelId;
    }

    public Integer getNumSetId() {
        return numSetId;
    }

    public void setNumSetId(Integer numSetId) {
        this.numSetId = numSetId;
    }

    public Integer getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(Integer remainTime) {
        this.remainTime = remainTime;
    }
}