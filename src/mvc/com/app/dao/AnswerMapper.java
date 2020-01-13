package com.app.dao;

import com.code.model.Answer;
import com.code.model.AnswerExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;
import java.util.List;

public interface AnswerMapper {
    int countByExample(AnswerExample example);

    int deleteByExample(AnswerExample example);

    int deleteByPrimaryKey(Integer answerId);

    int insert(Answer record);

    int insertSelective(Answer record);

    List<Answer> selectByExampleWithBLOBs(AnswerExample example);

    List<Answer> selectByExample(AnswerExample example);

    Answer selectByPrimaryKey(Integer answerId);

    int updateByExampleSelective(@Param("record") Answer record, @Param("example") AnswerExample example);

    int updateByExampleWithBLOBs(@Param("record") Answer record, @Param("example") AnswerExample example);

    int updateByExample(@Param("record") Answer record, @Param("example") AnswerExample example);

    int updateByPrimaryKeySelective(Answer record);

    int updateByPrimaryKeyWithBLOBs(Answer record);

    int updateByPrimaryKey(Answer record);

    @Delete("delete from answer t where t.simproblem_id = #{0}")
    int deleteAnswerBySimId(int SimId);

    @Select("select t.content, t.pos from answer t where t.simproblem_id = #{0}")
    List<Map<String,Object>> selAnBySimId(int simId);
}