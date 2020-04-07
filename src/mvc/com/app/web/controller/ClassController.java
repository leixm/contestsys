/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 班级管理 
 * */

package com.app.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.annotation.SystemControllerLog;
import com.code.model.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.app.service.impl.ClassServiceImpl;
import com.app.service.impl.UserServiceImpl;
import com.code.model.LayResponse;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class ClassController {

	@Resource
	private ClassServiceImpl classService;
	
	@Resource
	private UserServiceImpl userService;
	
	/**
	 * @author lxm、zzs
	 * @param Keyword: 班级或教师姓名
	 * @description 查询班级
	 * @return 班级信息
	 */
	@RequestMapping(value = "Class/GetAllClass", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String GetAllClass(HttpServletRequest request, HttpServletResponse response, String Keyword)
			throws Exception {
		JSONObject obj = new JSONObject();
		//获取分页所需相关数据
		String pageSize = request.getParameter("limit"); //一页多少个
		String pageNumber = request.getParameter("page");	//第几页

		//获取所登录用户的user对象
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int level = user.getLevel();
		String teacherId = user.getUserId().toString();
		if(level > 0) {		//管理员或教师
			if(level == 2) {
				teacherId = null;
			}
			//userId为参，null的时候默认查全部的课程，即管理员角色
			String className = Keyword == null ? null : Keyword.trim();
			List resultList =  classService.GetAllClass(teacherId,className,pageSize,pageNumber);
			JSONArray arr = JSONArray.fromObject(resultList);
			//获取分页插件的数据只能通过PageInfo来获取
			PageInfo pInfo = new PageInfo(resultList);
			Long total = pInfo.getTotal();
			obj.put("code", 0);
			obj.put("msg", "返回成功");
			obj.put("count", total.intValue());
			obj.put("data", arr);
		}else {
			obj.put("code", 0);
			obj.put("msg", "抱歉，权限不足！");
			return obj.toString();
		}
		return obj.toString();
	}
	
	/**
	 * @author lxm
	 * @description 跳转添加班级页面
	 * @return 视图及教师实例列表
	 */
	@RequestMapping(value = "addclass.do", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView PrepareAddClass(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("class-add.jsp");
		return mav;
	}
	
	/**
	 * @author zzs
	 * @description 跳转添加任课教师页面
	 * @return 视图
	 */
	@RequestMapping(value = "addClassTeach.do", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView preAddTeach(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		//获取登录对象，判断其角色
		User user = (User)request.getSession().getAttribute("user");
		String userId = user.getUserId().toString();
		int level = user.getLevel();
		
		if(level == 2) {
			 mav.addObject("teachers",userService.getAllTeacher());
		}else {
			 mav.addObject("teachers",userService.getTeacherById(userId));
		}
		mav.addObject("classes", classService.GetAllClass("root",null,null,null));
		mav.setViewName("class-addTeach.jsp");
		return mav;
	}
	
	/**
	 * @author lxm
	 * @param cla: 班级信息
	 * @description 添加班级
	 * @return 响应状态
	 */
	@RequestMapping(value = "Class/AddClass", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@SystemControllerLog(description = "添加班级")
	public String AddClass(HttpServletRequest request, com.code.model.Class cla) {
		LayResponse response = new LayResponse();
		response.setCode(1);
		//获取所登录用户的user对象
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int level = user.getLevel();
		if(level < 2) {
			response.setMsg("无操作权限");
			return JSONObject.fromObject(response).toString();
		}
		
		int result = classService.AddClass(cla);
		if ( result == 1) {		//返回0表示插入失败，返回1成功，返回2表示班级已存在无法
			response.setCode(0);
			response.setMsg("添加成功!");
			return JSONObject.fromObject(response).toString();
		}else if(result == 2) {
			response.setMsg("班级已存在");
			return JSONObject.fromObject(response).toString();
		}
		response.setMsg("添加失败");
		return JSONObject.fromObject(response).toString();
	}
	
	/**
	 * @author lxm
	 * @param id: 班级id
	 * @description 跳转更新班级页面
	 * @return 视图、班级信息、教师信息
	 */
	@RequestMapping(value = "editclass.do", method = { RequestMethod.POST,RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView EditClass(HttpServletRequest request, String id) {

		ModelAndView mav = new ModelAndView();
		//System.out.println("id---"+id);
		//System.out.println(JSONObject.fromObject(classService.GetClass(id)).toString());
		mav.addObject("classes", classService.GetClass(id));
		mav.setViewName("class-edit.jsp");
		return mav;
	}
	
	/**
	 * @author lxm
	 * @param cla: 班级id
	 * @description 更新班级
	 * @return 响应状态
	 */
	@RequestMapping(value = "Class/UpdateClass", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@SystemControllerLog(description = "更新班级信息")
	public String UpdateClass(HttpServletRequest request,com.code.model.Class cla) {
		LayResponse response = new LayResponse();
		response.setCode(1);
		
		//获取所登录用户的user对象
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int level = user.getLevel();
		if(level < 2) {
			response.setMsg("无操作权限");
			return JSONObject.fromObject(response).toString();
		}
		if (classService.UpdateClass(cla) > 0) {
			response.setMsg("更新成功");
			response.setCode(0);
			return JSONObject.fromObject(response).toString();
		} else {
			response.setMsg("更新失败");
			return JSONObject.fromObject(response).toString();
		}
	}
	
	/**
	 * @author lxm
	 * @param id: 班级id
	 * @description 删除单个班级
	 * @return 响应状态
	 */
	@RequestMapping(value = "Class/DeleteClass", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@SystemControllerLog(description = "删除班级")
	public String DeleteClass(HttpServletRequest request, String id) {
		
		LayResponse response = new LayResponse();
		response.setCode(1);
		
		//获取所登录用户的user对象
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int level = user.getLevel();
		if(level < 2) {
			response.setMsg("无操作权限");
			return JSONObject.fromObject(response).toString();
		}
		
		if (classService.DeleteClass(id) > 0) {
			response.setCode(0);
			return JSONObject.fromObject(response).toString();
		} else {
			response.setMsg("更新失败");
			return JSONObject.fromObject(response).toString();
		}
	}
	
	/**
	 * @author zzs
	 * @param classId,teacherId
	 * @description 移除任课关系
	 * @return 响应状态
	 */
	@RequestMapping(value = "Class/deleteTeach", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@SystemControllerLog(description = "移除班级任课关系")
	public String deleteTeach(HttpServletRequest request, String classId, String teacherId) {
		LayResponse response = new LayResponse();
		response.setCode(1);
		//获取所登录用户的user对象
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int level = user.getLevel();
		if(level < 2) {
			response.setMsg("无操作权限");
			return JSONObject.fromObject(response).toString();
		}
				
		if (classService.deleteTeach(classId,teacherId) > 0) {
			response.setCode(0);
			return JSONObject.fromObject(response).toString();
		} else {
			response.setMsg("更新失败");
			return JSONObject.fromObject(response).toString();
		}
	}
	
	/**
	 * @author lxm
	 * @param ids: 班级id列表
	 * @description 删除多个班级
	 * @return 响应状态
	 */
	@RequestMapping(value = "Class/DelAll", consumes = "application/json", produces = "text/html;charset=UTF-8", method = {RequestMethod.POST })
	@ResponseBody
	@SystemControllerLog(description = "批量删除班级")
	public String DelAll(HttpServletRequest request, @RequestBody List<String> ids) {
		  LayResponse response = new LayResponse(); 
		  response.setCode(1);
		//获取所登录用户的user对象
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int level = user.getLevel();
		if(level < 2) {
			response.setMsg("无操作权限");
			return JSONObject.fromObject(response).toString();
		}
			
		  if(classService.DeleteAllClass(ids)>0){
		   response.setCode(0); 
		   System.out.println(JSONObject.fromObject(response).toString());
		   return JSONObject.fromObject(response).toString(); 
		   }
		  else{
		  response.setMsg("删除失败"); 
		  return JSONObject.fromObject(response).toString(); }
		 
	}
	
	/**
	 * @author zzs
	 * @param cla: 班级信息
	 * @description 添加任课关系
	 * @return 响应状态
	 */
	@RequestMapping(value = "Class/AddTeach", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@SystemControllerLog(description = "添加班级任课关系")
	public String AddTeach(HttpServletRequest request) {
		LayResponse response = new LayResponse();
		response.setCode(1);
		//获取所登录用户的user对象
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int level = user.getLevel();
		if(level < 1) {
			response.setMsg("无操作权限");
			return JSONObject.fromObject(response).toString();
		}
		
		String classId = request.getParameter("classId"); 
		String teacherId = request.getParameter("teacherId");
		if(classId != null && !"".equals(classId) && teacherId != null && !"".equals(teacherId)) {
			int resultNum = classService.AddTeach(classId,teacherId);
			if(resultNum > 0) {
				response.setCode(0);
				response.setMsg("添加成功!成功增加"+ resultNum +"条记录");
				return JSONObject.fromObject(response).toString();
			}else {
				response.setMsg("添加失败,所选任课教师可能已存在");
				return JSONObject.fromObject(response).toString();
			}
		}
		response.setMsg("添加失败,请选择班级/任课教师");
		return JSONObject.fromObject(response).toString();
	}
	
	
	/**
	 * @author zzs
	 * @description 获取所有班级下拉
	 * @return 
	 */
	@RequestMapping(value = "User/getSelectClass", method = {
			RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getSelectCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LayResponse layResp = new LayResponse();//layui参数返回格式
		layResp.setCode(1); //默认设置为1
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		resultList = classService.GetAllClass("root",null,null,null);
		layResp.setCode(0);
		layResp.setMsg("请求成功！");
		layResp.setData(resultList);
		return JSONObject.fromObject(layResp).toString();
	}
}
