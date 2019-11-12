package com.app.dao;

import com.code.model.OneSimproblem;
import com.code.model.Simproblem;
import com.code.model.SimproblemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SimproblemMapper {
    int countByExample(SimproblemExample example);

    int deleteByExample(SimproblemExample example);

    int deleteByPrimaryKey(Integer simproblemId);

    int insert(Simproblem record);

    int insertSelective(Simproblem record);

    List<Simproblem> selectByExampleWithBLOBs(SimproblemExample example);

    List<Simproblem> selectByExample(SimproblemExample example);

    Simproblem selectByPrimaryKey(Integer simproblemId);

    int updateByExampleSelective(@Param("record") Simproblem record, @Param("example") SimproblemExample example);

    int updateByExampleWithBLOBs(@Param("record") Simproblem record, @Param("example") SimproblemExample example);

    int updateByExample(@Param("record") Simproblem record, @Param("example") SimproblemExample example);

    int updateByPrimaryKeySelective(Simproblem record);

    int updateByPrimaryKeyWithBLOBs(Simproblem record);

    int updateByPrimaryKey(Simproblem record);
    
    List<OneSimproblem> getSimproblemAndOptionByPaperId(Integer paperId);
    
    @Select("select count(*) from contestpaper")
    int selSimpCount();
    
    @Select("select ifnull(max(simproblem_id),0) from simproblem")
    int selMaxSimpId();
}