package com.app.dao;

import com.code.model.ContestStatus;
import com.code.model.ContestStatusExample;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ContestStatusMapper {
    int countByExample(ContestStatusExample example);

    int deleteByExample(ContestStatusExample example);

    int deleteByPrimaryKey(Integer contestStatusId);

    int insert(ContestStatus record);

    int insertSelective(ContestStatus record);

    List<ContestStatus> selectByExample(ContestStatusExample example);

    ContestStatus selectByPrimaryKey(Integer contestStatusId);

    int updateByExampleSelective(@Param("record") ContestStatus record, @Param("example") ContestStatusExample example);

    int updateByExample(@Param("record") ContestStatus record, @Param("example") ContestStatusExample example);

    int updateByPrimaryKeySelective(ContestStatus record);

    int updateByPrimaryKey(ContestStatus record);
    
    List<Map<String,Object>> GetAllContestStudent(String id);
    
    List<Map<String,Object>> selStuScoreBykeyword(@Param("classname") String className,@Param("stuid") String stuId,
                                                  @Param("stuname") String stuName,@Param("contestname") String contestName,
                                                  @Param("simCourseId") int simCourseId,@Param("status") List status,@Param("userId")String userId);
    
    List<Map<String,Object>> selAllStuScore( );
    
    int updateScore(@Param("cstatusid") int cStatusId,@Param("score") BigDecimal score); 
    
    List<Map<String, Object>> selOwnContestBykeyword(@Param("stuid") String stuId, @Param("keyword") String keyword);
    
    List<Map<String, Object>> selClassContestAVGbyKeyword(@Param("classname") List className,@Param("contestname") List contestName);
  
    List<Map<String, Object>> selOneStuScoreAVGbyKeyword(@Param("stuid") String stuId,@Param("contestname") List contestName);
    
    List<Map<String, Object>> selStuContestTitleByKeyword(@Param("stuid") String stuId);
    
    List<Map<String, Object>> selOneStuScore(@Param("stuid") String stuId);
}