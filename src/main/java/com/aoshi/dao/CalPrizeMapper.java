package com.aoshi.dao;

import com.aoshi.domain.CalPrize;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CalPrizeMapper {
    int deleteByPrimaryKey(Integer prizeId);

    int insert(CalPrize record);

    int insertSelective(CalPrize record);

    CalPrize selectByPrimaryKey(Integer prizeId);

    int updateByPrimaryKeySelective(CalPrize record);

    int updateByPrimaryKey(CalPrize record);

    @Select("select * from cal_prize where prize_level_id = #{prizeLevelId}")
    List<CalPrize> selectByLevelId(@Param(value = "prizeLevelId") Integer prizeLevelId);

    int updateAllData();

    @Select("select * from cal_prize p left join cal_prize_level l on l.prize_level_id = p.prize_level_id left join cal_num_set ns on ns.num_set_id = p.num_set_id ")
    @ResultMap("WithLevel")
    List<CalPrize> selectAll();

}