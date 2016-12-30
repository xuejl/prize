package com.aoshi.dao;

import com.aoshi.domain.CalNumRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface CalNumRecordMapper {
    int deleteByPrimaryKey(Integer numRecordId);

    int insert(CalNumRecord record);

    int insertSelective(CalNumRecord record);

    CalNumRecord selectByPrimaryKey(Integer numRecordId);

    int updateByPrimaryKeySelective(CalNumRecord record);

    int updateByPrimaryKey(CalNumRecord record);

    @Select("select * from cal_num_record where num_set_id = #{calNumId}")
    List<CalNumRecord> selectAll(@Param(value = "calNumId") Integer calNumId);

    @Select("select * from cal_num_record nr " +
            "left join cal_prize p on p.prize_id = nr.prize_id " +
            "where nr.num_set_id != 0")
    List<Map<String,Object>> selectRecordsAll();

    @Delete("delete from cal_num_record")
    int cleanTable();

}