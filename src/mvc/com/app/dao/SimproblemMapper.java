package com.app.dao;

import com.code.model.OneSimproblem;
import com.code.model.Simproblem;
import com.code.model.SimproblemExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SimproblemMapper {
    int countByExample(SimproblemExample example);

    int deleteByExample(SimproblemExample example);

    int deleteByPrimaryKey(Integer simproblemId);
    
    @Delete("DELETE a.*,b.*,c.* FROM simproblem a LEFT JOIN `options` b on a.simproblem_id = b.simproblem_id LEFT JOIN answer c on a.simproblem_id = c.simproblem_id "
    		+"WHERE a.simproblem_id = #{0}")
    int delSimproblemById(int simId);

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

    List<Map<String,Object>> selSimproblemList(@Param("simCourseId")int simCourseId,@Param("simPaperTitle")String simPaperTitle,@Param("simType")int simType);

    @Select("select * from simproblem s where s.content = #{0}")
    List<Map<String,Object>> selSimByContent(String content);
}