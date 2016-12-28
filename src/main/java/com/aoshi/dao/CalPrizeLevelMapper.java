package com.aoshi.dao;

import com.aoshi.domain.CalPrizeLevel;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CalPrizeLevelMapper {
    int deleteByPrimaryKey(Integer prizeLevelId);

    int insert(CalPrizeLevel record);

    int insertSelective(CalPrizeLevel record);

    CalPrizeLevel selectByPrimaryKey(Integer prizeLevelId);

    int updateByPrimaryKeySelective(CalPrizeLevel record);

    int updateByPrimaryKey(CalPrizeLevel record);

    @Select("select * from cal_prize_level")
    List<CalPrizeLevel> selectAll();
}