package com.app.dao;

import com.code.model.ContestStatus;
import com.code.model.ContestStatusExample;
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
    
}