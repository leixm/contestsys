/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 班级管理 
 * */

package com.app.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.app.service.impl.ClassService;
import com.app.service.impl.UserService;
import com.code.model.LayResponse;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class ClassController {

	@Resource
	private ClassService classService;
	
	@Resource
	private UserService userService;
	
	/**
	 * @author lxm
	 * @param Keyword: 过滤条件
	 * @description 查询班级
	 * @return 班级信息
	 */
	@RequestMapping(value = "Class/GetAllClass", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String GetAllClass(HttpServletRequest request, HttpServletResponse response, String Keyword)
			throws Exception {
		//获取分页所需相关数据
		String pageSize = request.getParameter("limit"); //一页多少个
		String pageNumber = request.getParameter("page");	//第几页
		
		List resultList =  classService.GetAllClass(Keyword,pageSize,pageNumber);
		//获取分页插件的数据只能通过PageInfo来获取
		PageInfo pInfo = new PageInfo(resultList);
		Long total = pInfo.getTotal();
		
		JSONObject obj = new JSONObject();
		JSONArray arr = JSONArray.fromObject(resultList);
		
		obj.put("code", 0);
		obj.put("msg", "返回成功");
		obj.put("count", total.intValue());
		obj.put("data", arr);
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
		mav.addObject("teachers", userService.getAllTeacher() );
		mav.setViewName("class-add.jsp");
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
	public String AddClass(HttpServletRequest request, com.code.model.Class cla) {
		System.out.println(JSONObject.fromObject(cla).toString());
		LayResponse response = new LayResponse();
		response.setCode(1);
		if (classService.AddClass(cla) > 0) {
			response.setCode(0);
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
		System.out.println(JSONObject.fromObject(classService.GetClass(id)).toString());
		mav.addObject("classes", classService.GetClass(id));
		mav.addObject("teachers", userService.getAllTeacher());
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
	public String UpdateClass(HttpServletRequest request,com.code.model.Class cla) {
		System.out.println(JSONObject.fromObject(cla).toString());
		LayResponse response = new LayResponse();
		response.setCode(1);
		if (classService.UpdateClass(cla) > 0) {
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
	public String DeleteClass(HttpServletRequest request, String id) {

		LayResponse response = new LayResponse();
		response.setCode(1);
		if (classService.DeleteClass(id) > 0) {
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
	public String DelAll(HttpServletRequest request, @RequestBody List<String> ids) {

		System.out.println(JSONArray.fromObject(ids).toString());
		  LayResponse response = new LayResponse(); response.setCode(1);
		  if(classService.DeleteAllClass(ids)>0){
		   response.setCode(0); 
		   System.out.println(JSONObject.fromObject(response).toString());
		   return JSONObject.fromObject(response).toString(); 
		   }
		  else{
		  response.setMsg("删除失败"); 
		  return JSONObject.fromObject(response).toString(); }
		 
	}
}
