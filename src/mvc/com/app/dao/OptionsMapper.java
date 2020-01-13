package com.app.dao;

import com.code.model.Options;
import com.code.model.OptionsExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface OptionsMapper {
    int countByExample(OptionsExample example);

    int deleteByExample(OptionsExample example);

    int deleteByPrimaryKey(Integer optionId);

    int insert(Options record);

    int insertSelective(Options record);

    List<Options> selectByExampleWithBLOBs(OptionsExample example);

    List<Options> selectByExample(OptionsExample example);

    Options selectByPrimaryKey(Integer optionId);

    int updateByExampleSelective(@Param("record") Options record, @Param("example") OptionsExample example);

    int updateByExampleWithBLOBs(@Param("record") Options record, @Param("example") OptionsExample example);

    int updateByExample(@Param("record") Options record, @Param("example") OptionsExample example);

    int updateByPrimaryKeySelective(Options record);

    int updateByPrimaryKeyWithBLOBs(Options record);

    int updateByPrimaryKey(Options record);

    @Delete("delete from options t where t.simproblem_id = #{0}")
    int deleteOptionBySimId(int SimId);

    @Select("select t.content, t.pos from options t where t.simproblem_id = #{0}")
    List<Map<String,Object>> selOpBySimId(int simId);
}