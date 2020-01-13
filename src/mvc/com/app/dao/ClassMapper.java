package com.app.dao;

import com.code.model.Class;
import com.code.model.ClassExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ClassMapper {
    int countByExample(ClassExample example);

    int deleteByExample(ClassExample example);

    int deleteByPrimaryKey(Integer classId);
    
    int deleteTeachById(int classId,String teacherId);

    int insert(Class record);

    int insertSelective(Class record);
    
    int insertSelectiveToConfiguration(Class record);
    
    int insertTeachByKeyword(String classId,String teacherId);

    List<Class> selectByExample(ClassExample example);

    Class selectByPrimaryKey(Integer classId);

    int updateByExampleSelective(@Param("record") Class record, @Param("example") ClassExample example);

    int updateByExample(@Param("record") Class record, @Param("example") ClassExample example);

    int updateByPrimaryKeySelective(Class record);

    int updateByPrimaryKey(Class record);

    List<Map<String,Object>> listAllByKeyword(@Param("teacherId") String teacherId,@Param("className") String className);
    
    List<Map<String,Object>> selClassConfByKeyword(String classId,String teacherId);
    
    @Select("select class_id from class where name = #{classname}")
    List<Map<String,Object>> selByClassname(String classname);

    @Select("select t.class_id,t.name,v.fk_teacher_id from class t " +
            "left join configuration_teacher_class v on t.class_id = v.fk_class_id where fk_teacher_id = #{0}")
    List<Map<String,Object>> selClassObjByUserId(String userId);
    
}