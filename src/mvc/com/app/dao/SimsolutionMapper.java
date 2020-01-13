package com.app.dao;

import com.code.model.Simsolution;
import com.code.model.SimsolutionExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SimsolutionMapper {
    int countByExample(SimsolutionExample example);

    int deleteByExample(SimsolutionExample example);

    int deleteByPrimaryKey(Integer simsolutionId);

    int insert(Simsolution record);

    int insertSelective(Simsolution record);

    List<Simsolution> selectByExampleWithBLOBs(SimsolutionExample example);

    List<Simsolution> selectByExample(SimsolutionExample example);

    Simsolution selectByPrimaryKey(Integer simsolutionId);

    int updateByExampleSelective(@Param("record") Simsolution record, @Param("example") SimsolutionExample example);

    int updateByExampleWithBLOBs(@Param("record") Simsolution record, @Param("example") SimsolutionExample example);

    int updateByExample(@Param("record") Simsolution record, @Param("example") SimsolutionExample example);

    int updateByPrimaryKeySelective(Simsolution record);

    int updateByPrimaryKeyWithBLOBs(Simsolution record);

    int updateByPrimaryKey(Simsolution record);

    List<Map<String, Object>> selSolutionSimproblemByTypeAndcStatusId(@Param("type") int type,@Param("cStatusId") int cStatusId);

}