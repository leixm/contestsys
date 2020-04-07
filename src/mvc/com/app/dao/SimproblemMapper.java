package com.app.dao;

import com.code.model.OneSimproblem;
import com.code.model.Simproblem;
import com.code.model.SimproblemExample;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    @Delete("DELETE a.*,b.*,c.* FROM simproblem a LEFT JOIN `options` b on a.simproblem_id = b.simproblem_id LEFT JOIN answer c on a.simproblem_id = c.simproblem_id "
    		+"WHERE a.simproblem_id = #{0}")
    int delSimproblemByIdBak(int simId);
    
    @Delete("DELETE a.* FROM simproblem a WHERE a.simproblem_id = #{0}")
    int delSimproblemById(int simId);


    List<OneSimproblem> getSimproblemAndOptionByPaperId(Integer paperId);
    
    @Select("select count(*) from contestpaper")
    int selSimpCount();
    
    @Select("select ifnull(max(simproblem_id),0) from simproblem")
    int selMaxSimpId();

    List<Map<String,Object>> selSimproblemList(@Param("simCourseId")int simCourseId,@Param("simPaperTitle")String simPaperTitle,@Param("simType")int simType);

    @Select("select * from simproblem s where s.content = #{0} and s.type = #{1}")
    List<Map<String,Object>> selSimByContent(String content,int type);

    @Update("update simproblem set content = #{0},score = #{1} where simproblem_id = #{2}")
    int updateSimContentAndScore(String simContent, BigDecimal simScore, int simId);

    @Select("SELECT simproblem_id simId FROM simproblem WHERE paper_id = #{0} ORDER BY type")
    List<Map<String,Object>> selSimIdByPaperId(int paperId);

    @Update("UPDATE simproblem t SET t.pos = #{1} WHERE t.simproblem_id = #{0}")
    int updatePosBySimId(int simId, int pos);
    
    /**
     * 查询未批改完的题目类型
     * @param cStatusId
     * @return
     */
    List<Map<String,Object>> selTypeByStatusIdandStatus(@Param("cStatusId") int cStatusId);
    
    
    
    
}