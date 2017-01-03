package com.aoshi.domain;

public class CalPrize {
    private Integer prizeId;

    private String prizeName;

    private Integer prizeLevelId;

    private Integer numSetId;

    private Integer remainTime;

    private String prizeimg;

    private CalPrizeLevel level;

    private CalNumSet numSet;


    public CalNumSet getNumSet() {
        return numSet;
    }

    public void setNumSet(CalNumSet numSet) {
        this.numSet = numSet;
    }

    public CalPrizeLevel getLevel() {
        return level;
    }

    public void setLevel(CalPrizeLevel level) {
        this.level = level;
    }

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

    public String getPrizeimg() {
        return prizeimg;
    }

    public void setPrizeimg(String prizeimg) {
        this.prizeimg = prizeimg == null ? null : prizeimg.trim();
    }
}