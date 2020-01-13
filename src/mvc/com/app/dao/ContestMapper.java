package com.app.dao;

import com.code.model.Contest;
import com.code.model.ContestExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    
    List<Map<String,Object>> listAll(@Param("fkCourseId") int fkCourseId);
    
    List<Map<String,Object>> listAllByKeyword(String keyword, @Param("fkCourseId") int fkCourseId);
    
    List<Map<String,Object>> listAllByDate(@Param("fkCourseId") int fkCourseId,String startTime,String endTime);
    
    List<Map<String,Object>> listAllByKeywordAndDate(@Param("keyword")String keyword,@Param("fkCourseId")int fkCourseId,@Param("startTime")String startTime,@Param("endTime")String endTime);
    
    @Select("select * from contest where teacher = #{id}")
    List<Contest> selAllContest(String id);
    
    List<Map<String,Object>> selAllByUserId(String keyword);
    
    
}