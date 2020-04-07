package com.app.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.code.model.User;
import com.code.model.UserExample;

public interface UserMapper {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(String userId);

    int insert(User record);

    int insertStuByExcel(User record); 
    
    int insertTeaByExcel(User record);
    
    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(String userId);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<Map<String,Object>> listAllStudents();
    
    List<Map<String,Object>> listAllStudentsByKeyword(String keyword);
    
	List<Map<String, Object>> listAllTeachers();

	List<Map<String, Object>> listAllTeachersByKeyword(String keyword);
	@Select("select * from user where user_id = #{userid}")
	List<Map<String, Object>> selUserById(String userid);
	
	@Select("select * from user where email = #{email}")
	List<Map<String, Object>> selUserByEmail(String email);
	
	int updateStuInfoByStuId(@Param("stuid") String stuId, @Param("stuname") String stuName,
			@Param("stuemail") String stuEmail, @Param("newpwd") String newPwd);
	
	
} 