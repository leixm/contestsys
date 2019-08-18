/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 用户管理服务
 * */
package com.app.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.*;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.ClassMapper;
import com.app.dao.UserMapper;
import com.app.tools.MD5Util;
import com.app.tools.RandomString;
import com.app.tools.SendEmail;
import com.code.model.ClassExample;
import com.code.model.User;
import com.code.model.UserExample;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service 
public class UserService {

	@Autowired
	private UserMapper userDao;
	
	@Autowired
	private ClassMapper classDao;
	
	public int LoginByUserName(String userName,String passWord) throws UnsupportedEncodingException, MessagingException, GeneralSecurityException
	{
		UserExample userExample = new UserExample();
		UserExample.Criteria criteria= userExample.createCriteria();
		criteria.andUserIdEqualTo(userName);
		
		
		List<User> list = userDao.selectByExample(userExample);
		System.out.println("size=" + list.size()); 
	    if(list.size()==1)
	    {
	    	User user = list.get(0);
	    	if(user.getState()==0 )  //待激活
    		{
	    		if(user.getValidatetime().before(new Date()))
	    		{
	    			User record = new User();
	        		Calendar ca = Calendar.getInstance();
	        		ca.setTime(new Date());
	        		ca.add(Calendar.MINUTE, 15);
	        		record.setValidatetime(ca.getTime());
	        		record.setValidatecode(MD5Util.encryption(RandomString.getRandomString(32)));
	        		userDao.updateByExampleSelective(record, userExample);
	        		ApplyActivate(user.getEmail(),user.getValidatecode());
	    		}
    		
    		return -1;
    		}
	    	
	    	criteria.andPasswordEqualTo(passWord);
	    	list = userDao.selectByExample(userExample);
	    	if(list.size()==1)
	    	{
	    		user = list.get(0);
	    		if(user.getState()<0)
	    			return -1; //状态异常（封禁状态）
	    		else if(user.getLevel()==2)
		    		return 4;  //管理员
		    	else if(user.getLevel()==1)
		    		return 3;   //教师用户
		    	else return 2; //普通用户
	    	}
	    	else return 1; //密码错误
	    		
	    }
	    else return 0;  //用户名不存在
	    
	}
	
	public int LoginByEmail(String email,String passWord) throws UnsupportedEncodingException, MessagingException, GeneralSecurityException
	{
		UserExample userExample = new UserExample();
		UserExample.Criteria criteria= userExample.createCriteria();
		criteria.andEmailEqualTo(email);
		
		
		List<User> list = userDao.selectByExample(userExample);
	    if(list.size()==1)
	    {
	    	User user = list.get(0);
	    	if(user.getState()==0  )
	    	{
	    		if(user.getValidatetime().before(new Date()))
	    		{
	    			User record = new User();
		    		Calendar ca = Calendar.getInstance();
		    		ca.setTime(new Date());
		    		ca.add(Calendar.MINUTE, 15);
		    		record.setValidatetime(ca.getTime());
		    		record.setValidatecode(MD5Util.encryption(RandomString.getRandomString(32)));
		    		userDao.updateByExampleSelective(record, userExample);
		    		ApplyActivate(user.getEmail(),user.getValidatecode());
		    		
	    		}
	    		
	    		return -1;
	    	}
	    	
	    	criteria.andPasswordEqualTo(passWord);
	    	list = userDao.selectByExample(userExample);
	    	if(list.size()==1)
	    	{
	    		user = list.get(0);
	    		if(user.getState()<0)
	    			return -1; //状态异常（封禁状态）
	    		else if(user.getLevel()==2)
		    		return 4;  //管理员
		    	else if(user.getLevel()==1)
		    		return 3;   //教师用户
		    	else return 2; //普通用户
	    	}
	    	else return 1; //密码错误
	    		
	    }
	    else return 0;  //用户名不存在
	    
	}
	
	public String Register(User user) throws UnsupportedEncodingException, MessagingException, GeneralSecurityException
	{
		String userNameRegex = "^[0-9]*$";
		String passWordRegex = "^[A-Za-z0-9]+$";
		String emailRegex= "^[0-9A-Za-z][\\.-_0-9A-Za-z]*@[0-9A-Za-z]+(\\.[0-9A-Za-z]+)+$";
		
		if(user.getUserId()==null || user.getUserId().length()!=13 || !Pattern.matches(userNameRegex, user.getUserId()))
			return "用户名不符合规范（要求13位且全部为数字）";
		if(user.getPassword()==null || !(user.getPassword().length()>=8 && user.getPassword().length()<=16) || !Pattern.matches(passWordRegex, user.getPassword()))
			return "密码不符合规范（要求8-16位且由数字或字母组成）";
		if(user.getEmail()==null || !Pattern.matches(emailRegex, user.getEmail()))
			return "邮箱格式错误";
		if(LoginByUserName(user.getUserId(),user.getPassword())>0)
			return "用户名已经存在";
		if(LoginByEmail(user.getEmail(),user.getPassword())>0)
			return "邮箱地址已经被使用";
		
		user.setRegistertime(new Date());
		String validateCode = MD5Util.encryption(RandomString.getRandomString(32));
		user.setValidatecode(validateCode);
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.add(Calendar.MINUTE, 15);
		user.setValidatetime(ca.getTime());
		userDao.insertSelective(user);
		ApplyActivate(user.getEmail(),user.getValidatecode());
		
		return "注册成功";
	}
	
	public int Login(String userName,String passWord) throws UnsupportedEncodingException, MessagingException, GeneralSecurityException
	{
		int state = LoginByEmail(userName,passWord);
		if(state==0)
			return LoginByUserName(userName,passWord);
		else return state; 

	}
	
	public User GetUser(String username){
		return userDao.selectByPrimaryKey(username);
	}
	
	public String ProcessActivate(String email,String validateCode) throws UnsupportedEncodingException
	{
		UserExample userExample = new UserExample();
        UserExample.Criteria criteria= userExample.createCriteria();
        criteria.andEmailEqualTo(email);
        List<User> list = userDao.selectByExample(userExample);
        if(list.size()==1)
        {
        	User user = list.get(0);
        	if(user.getValidatecode().equals(validateCode))
        	{
        		Date date = user.getValidatetime();
        		System.out.println(date.toString());
        		if(date.after(new Date()))
        		{
        			user = new User();
        			user.setState(new Integer(1));
        			userDao.updateByExampleSelective(user, userExample);
        			return "激活成功";
        		}
        		else return "激活码过期";
        	}
        	else return "激活码错误";
        }
        else return "邮箱地址错误";
		
	}
	
	public void ApplyActivate(String email,String validateCode) throws UnsupportedEncodingException, MessagingException, GeneralSecurityException
	{
		
		StringBuilder sb = new StringBuilder();
		sb.append("您好:\n");
		sb.append("以下为您的邮箱激活地址：\n");
		
		sb.append("<a href=\"http://localhost:8080/rustshop/User/Validate?email=");
        sb.append(email); 
        sb.append("&validatecode=");  
        sb.append(validateCode);
        sb.append("\">请在15分钟内点击此链接激活您的账号");  
        sb.append("</a>");
        
		SendEmail.sendMessage("考试系统", email, "注册激活", sb.toString(), "text/html;charset=gb2312");
	}
	
	public String ApplyResetPassword(String email) throws UnsupportedEncodingException, MessagingException, GeneralSecurityException
	{
	    UserExample userExample = new UserExample();
	    UserExample.Criteria criteria = userExample.createCriteria();
	    criteria.andEmailEqualTo(email);
	    List<User> list = userDao.selectByExample(userExample);
	    if(list.size()==1)
	    {
	    	if(list.get(0).getState()<=0)  //状态异常
	    	return "账号状态异常";
	    	String ran = RandomString.getRandomNum(6);
	    	
	    	Calendar ca  = Calendar.getInstance();
	    	ca.setTime(new Date());
	    	ca.add(Calendar.MINUTE, 10);
	    	User user = new User();
	    	user.setValidatecode(ran);
	    	user.setValidatetime(ca.getTime());
	    	userDao.updateByExampleSelective(user, userExample);
	    	
	    	StringBuilder sb = new StringBuilder();
			sb.append("您好:\n");
			sb.append("以下为您的密码重置验证码：");
	        sb.append(ran);
	        sb.append("\n如果不是您本人操作，请忽略此邮件");
	        
			SendEmail.sendMessage("考试系统", email, "密码重置", sb.toString(), "text/html;charset=gb2312");
	    	
	    }
	    else return "邮箱地址不存在";
	      
		return "申请成功";
	}
	
	public String ResetPassword(String email , String newPassword ,String validateCode) throws UnsupportedEncodingException, MessagingException, GeneralSecurityException
	{
		String passWordRegex = "^[A-Za-z0-9]+$";
		int state = LoginByEmail(email,"");
	    if(state==-1) return "邮箱地址不存在";
	    else if(state==0) return "邮箱地址尚未激活，请到您的邮箱进行激活";
	    
	    UserExample userExample = new UserExample();
	    UserExample.Criteria criteria = userExample.createCriteria();
	    criteria.andEmailEqualTo(email);
	    List<User> list = userDao.selectByExample(userExample);
	    User user = list.get(0);
	    if(user.getValidatecode().equals(validateCode))
	    {
	    	if(user.getValidatetime().after(new Date()))
	    	{
	    		user = new User();
	    		if(Pattern.matches(passWordRegex, newPassword))
	    		{
	    			user.setPassword(newPassword);
		    		userDao.updateByExampleSelective(user, userExample);
		    		return "密码重置成功";
	    		}
	    		else return "密码不符合规范（要求8-16位且由数字或字母组成）";
	    	}
	    		else return "验证码过期";	
	    }
	    else return "验证码错误";
	    
	}
	
	public JSONArray GetAllStudents(String Keyword){
        System.out.println("key=" + Keyword);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if(Keyword==null || Keyword.trim().isEmpty())
			list = userDao.listAllStudents();

		else list = userDao.listAllStudentsByKeyword(Keyword);
	    
	    for(Map<String,Object> map : list){
	    	map.put("registerTime", sdf.format((Date)map.get("registerTime")));
	    }
	    System.out.println(JSONArray.fromObject(list).toString());
	    return JSONArray.fromObject(list);
	}
	
	public JSONArray GetAllTeachers(String Keyword){
        System.out.println("key=" + Keyword);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if(Keyword==null || Keyword.trim().isEmpty())
			list = userDao.listAllTeachers();

		else list = userDao.listAllTeachersByKeyword(Keyword);
	    
	    for(Map<String,Object> map : list){
	    	map.put("registerTime", sdf.format((Date)map.get("registerTime")));
	    }
	    System.out.println(JSONArray.fromObject(list).toString());
	    return JSONArray.fromObject(list);
	}
	
	
	public int AddUser(User user){
		user.setState(new Integer(1));
		user.setRegistertime(new Date());
		return userDao.insertSelective(user);
	}
	
	public int UpdateUser(User user){
		return userDao.updateByPrimaryKeySelective(user);
	}
	
	public int DeleteUser(String id){
		return userDao.deleteByPrimaryKey(id);
	}
	

	
	public User getUser(String id){
		return userDao.selectByPrimaryKey(id);
	}
	
	public List<User> getAllTeacher()
	{
		UserExample userExample = new UserExample();
		UserExample.Criteria criteria = userExample.createCriteria();
		criteria.andLevelEqualTo(new Integer(1));
		return userDao.selectByExample(userExample);
	}
	
	public int DeleteAllUser(List<String> ids)
	{  
	   int count = 0;
	   for(String id : ids){
		 count += userDao.deleteByPrimaryKey(id);
		 System.out.println("count=" + count);
	   } 
	   return count;
	} 

}
