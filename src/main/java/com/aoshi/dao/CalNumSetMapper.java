package com.aoshi.dao;

import com.aoshi.domain.CalNumSet;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CalNumSetMapper {
    int deleteByPrimaryKey(Integer numSetId);

    int insert(CalNumSet record);

    int insertSelective(CalNumSet record);

    CalNumSet selectByPrimaryKey(Integer numSetId);

    int updateByPrimaryKeySelective(CalNumSet record);

    int updateByPrimaryKey(CalNumSet record);

    @Select("select * from cal_num_set")
    List<CalNumSet> selectAll();
}