package com.app.dao;

import com.code.model.OneProblem;
import com.code.model.Problem;
import com.code.model.ProblemExample;
import com.code.model.ProblemWithBLOBs;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ProblemMapper {
    int countByExample(ProblemExample example);

    int deleteByExample(ProblemExample example);

    int deleteByPrimaryKey(Integer problemId);

    int insert(ProblemWithBLOBs record);

    int insertSelective(ProblemWithBLOBs record);

    List<ProblemWithBLOBs> selectByExampleWithBLOBs(ProblemExample example);

    List<Problem> selectByExample(ProblemExample example);

    ProblemWithBLOBs selectByPrimaryKey(Integer problemId);

    int updateByExampleSelective(@Param("record") ProblemWithBLOBs record, @Param("example") ProblemExample example);

    int updateByExampleWithBLOBs(@Param("record") ProblemWithBLOBs record, @Param("example") ProblemExample example);

    int updateByExample(@Param("record") Problem record, @Param("example") ProblemExample example);

    int updateByPrimaryKeySelective(ProblemWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ProblemWithBLOBs record);

    int updateByPrimaryKey(Problem record);
    
    List<OneProblem> getProblemAndSolutionByPaperId(Integer paperId);
    
    @Select("select ifnull(max(problem_id),0) from problem")
    int selMaxProbId();

    @Select("select count(*) from problem")
    int selProbCount();

    List<Map<String,Object>> selProblemList(@Param("courseId")int courseId,@Param("paperTitle")String paperTitle);

    @Delete("DELETE a.* FROM problem a WHERE a.problem_id = #{0}")
    int delProblemById(int proId);

    @Select("select t.problem_id from problem t where t.paper_id = #{0}")
    List<Map<String,Object>> selProbByPaperId(int paperId);

    @Update("UPDATE problem t SET t.pos = #{1} WHERE t.problem_id = #{0}")
    int updatePosByProbId(int probId, int pos);
}