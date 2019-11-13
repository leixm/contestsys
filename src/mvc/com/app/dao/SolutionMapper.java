package com.app.dao;

import com.code.model.Solution;
import com.code.model.SolutionExample;
import com.code.model.SolutionWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SolutionMapper {
    int countByExample(SolutionExample example);

    int deleteByExample(SolutionExample example);

    int deleteByPrimaryKey(Integer solutionId);

    int insert(SolutionWithBLOBs record);

    int insertSelective(SolutionWithBLOBs record);

    List<SolutionWithBLOBs> selectByExampleWithBLOBs(SolutionExample example);

    List<Solution> selectByExample(SolutionExample example);

    SolutionWithBLOBs selectByPrimaryKey(Integer solutionId);

    int updateByExampleSelective(@Param("record") SolutionWithBLOBs record, @Param("example") SolutionExample example);

    int updateByExampleWithBLOBs(@Param("record") SolutionWithBLOBs record, @Param("example") SolutionExample example);

    int updateByExample(@Param("record") Solution record, @Param("example") SolutionExample example);

    int updateByPrimaryKeySelective(SolutionWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SolutionWithBLOBs record);

    int updateByPrimaryKey(Solution record);
}