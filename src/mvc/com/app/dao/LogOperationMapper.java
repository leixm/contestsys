package com.app.dao;

import com.code.model.LogOperation;
import com.code.model.LogOperationExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface LogOperationMapper {
    int countByExample(LogOperationExample example);

    int deleteByExample(LogOperationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LogOperation record);

    int insertSelective(LogOperation record);

    List<LogOperation> selectByExample(LogOperationExample example);

    LogOperation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LogOperation record, @Param("example") LogOperationExample example);

    int updateByExample(@Param("record") LogOperation record, @Param("example") LogOperationExample example);

    int updateByPrimaryKeySelective(LogOperation record);

    int updateByPrimaryKey(LogOperation record);

    @Select("select * from log_operation t order by t.type")
    List<Map<String, Object>> listAllLog();

    List<Map<String,Object>> listAllByDate(@Param("startTime") String startTime, @Param("endTime") String endTime);

}