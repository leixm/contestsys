package com.app.dao;

import com.code.model.Contest;
import com.code.model.ContestExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ContestMapper {
    int countByExample(ContestExample example);

    int deleteByExample(ContestExample example);

    int deleteByPrimaryKey(Integer contestId);

    int insert(Contest record);

    int insertSelective(Contest record);

    List<Contest> selectByExample(ContestExample example);

    Contest selectByPrimaryKey(Integer contestId);

    int updateByExampleSelective(@Param("record") Contest record, @Param("example") ContestExample example);

    int updateByExample(@Param("record") Contest record, @Param("example") ContestExample example);

    int updateByPrimaryKeySelective(Contest record);

    int updateByPrimaryKey(Contest record);
    
    List<Map<String,Object>> listAll();
    
    List<Map<String,Object>> listAllByKeyword(String keyword);
    
    List<Map<String,Object>> listAllByDate(String startTime,String endTime);
    
    List<Map<String,Object>> listAllByKeywordAndDate(String keyword,String startTime,String endTime);

    
}