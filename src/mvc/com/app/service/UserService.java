/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 用户管理服务
 * */
package com.app.service;

import com.annotation.SystemServiceLog;
import com.app.dao.ClassMapper;
import com.app.dao.CourseMapper;
import com.app.dao.UserMapper;
import com.app.tools.MD5Util;
import com.app.tools.MD5Util2;
import com.app.tools.RandomString;
import com.code.model.User;
import com.code.model.UserExample;
import com.github.pagehelper.PageHelper;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


public interface UserService {

	public int LoginByUserName(String userName,String passWord) throws UnsupportedEncodingException, MessagingException, GeneralSecurityException;

	public int LoginByEmail(String email,String passWord) throws UnsupportedEncodingException, MessagingException, GeneralSecurityException;

	/**
	 * 注册接口
	 * @param user
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 * @throws GeneralSecurityException
	 */
	public String Register(User user) throws UnsupportedEncodingException, MessagingException, GeneralSecurityException;

	/**
	 * 登录主接口
	 * @param userName
	 * @param passWord
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 * @throws GeneralSecurityException
	 */
	public int Login(String userName,String passWord) throws UnsupportedEncodingException, MessagingException, GeneralSecurityException;

	public User GetUser(String username);

	public String ProcessActivate(String email,String validateCode) throws UnsupportedEncodingException;

	public void ApplyActivate(String email,String validateCode) throws UnsupportedEncodingException, MessagingException, GeneralSecurityException;

	public String ApplyResetPassword(String email) throws UnsupportedEncodingException, MessagingException, GeneralSecurityException;

	public String ResetPassword(String email , String newPassword ,String validateCode) throws UnsupportedEncodingException, MessagingException, GeneralSecurityException;

	public List GetAllStudents(String Keyword,String pageSize,String pageNumber);

	//此处为AOP拦截Service记录异常信息。方法不需要加try-catch
	public int AddUser(User user);

	public int UpdateUser(User user);

	public int DeleteUser(String id);

	public User getUser(String id);

	public List<User> getAllTeacher();

	public List<User> getTeacherById(String userId);

	public int DeleteAllUser(List<String> ids);

	public List<Map<String,Object>> selCourseNameByTeacherId(String teacherId,String keyword,String pageSize,String pageNumber);

	public List<Map<String,Object>> selCourseObjById(String courseId);

	public int addCourse(String courseName);

	public int addTeach(String courseId,String teacherId);

	public int deleteCourse(String courseId) ;

	public int deleteAllCourse(List<String> ids);

	public int deleteTeach(String courseId,String teacherId);

	public int updateCourse(int courseId,String courseName) ;
	
}
