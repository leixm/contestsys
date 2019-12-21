package com.app.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface CourseMapper {
	//搜索和展示列表用
	List<Map<String, Object>> selCourseNameByTeacherId(@Param("teacherId") String teacherId,@Param("keyword") String keyword);
	
	@Select("select * from course where course_name = #{0}")
	List<Map<String, Object>> selCourseByName(String courseName);
	
	@Select("select * from course where course_id = #{0}")
	List<Map<String, Object>> selCourseObjById(String courseId);
	
	@Select("select * from configuration_teacher_course u where u.fk_course_id = #{0} and u.fk_teacher_id = #{1}")
	List<Map<String, Object>> selCourseConfByKeyword(String courseId,String teacherId);
	
	@Insert("insert into course (course_name) values (#{0})")
	int insCourseByName(String courseName);

	@Insert(" insert into configuration_teacher_course (fk_course_id,fk_teacher_id) values (#{0},#{1})")
	int insertTeachByKeyword(String courseId,String teacherId);
	
	@Update("update course t set t.course_name = #{1} where t.course_id = #{0}")
	int updateCourseById(int courseId,String courseName);
	
	@Delete("delete from course t where t.course_id = #{0}")
	int delCourseById(int courseId);
	
	@Delete(" delete from configuration_teacher_course where fk_course_id = #{0} and fk_teacher_id = #{1}")
	int deleteTeachById(int courseId,String teacherId);

}