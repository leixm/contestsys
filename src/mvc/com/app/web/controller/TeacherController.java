/** 
 * @author: lxm
 * @create_date: 2019.5.3
 * @description 教师管理 
 * */
package com.app.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.service.TeacherService;
import com.code.model.Answer;
import com.code.model.Contest;
import com.code.model.Contestpaper;
import com.code.model.OnePaper;
import com.code.model.OneProblem;
import com.code.model.OneSimproblem;
import com.code.model.Options;
import com.code.model.Problem;
import com.code.model.ProblemWithBLOBs;
import com.code.model.Response;
import com.code.model.Simproblem;
import com.code.model.User;

import net.sf.json.JSONObject;



@Controller
public class TeacherController {
	@Resource
	private TeacherService teacherService;
	
	
	@RequestMapping(value="Teacher/selAllpaper",method={RequestMethod.POST},produces="text/html;charset=UTF-8")
	/**
	 * 查老师所有试题(reObj封装的是List<OnePaper>对象)
	 * @param request
	 * @param response
	 * @return 属于同个userId的所有的卷子
	 */
	@ResponseBody
	public String selAllpaper(HttpServletRequest request,HttpServletResponse response) {
		
		User user = (User)request.getSession().getAttribute("user");
		String userId = user.getUserId();
		//测试数据
		///User user = new User();
	//	String userId = "123";
		
		//测试
		user.setLevel(1);
		user.setUserId(userId);
		
		System.out.println(JSONObject.fromObject(teacherService.selAllpaper(user)).toString());
		return JSONObject.fromObject(teacherService.selAllpaper(user)).toString();
	}

	
	
	@RequestMapping(value="Teacher/addNewpaper",method={RequestMethod.POST},produces="text/html;charset=UTF-8")
	/**
	 * 添加新试卷
	 * @param request
	 * @param response
	 * @return 1添加成功     0添加失败
	 */
	//@RequestBody Response resp,
	@ResponseBody
	public String addNewpaper(@RequestBody String resp ,HttpServletRequest request,HttpServletResponse response) {
	
		
		User user = new User();
		String userId = "123";
		
		//测试
		user.setLevel(1);
		user.setUserId(userId);
		
		 //1、使用JSONObject(把json字符串转Java对象)
        JSONObject jsonObject=JSONObject.fromObject(resp);
        //复杂对象需要借助Map(所有容器对象都得put进去)
        Map classMap = new HashMap();
        classMap.put("simp",OneSimproblem.class);
        classMap.put("prob", OneProblem.class);
        classMap.put("option", Options.class);
        classMap.put("answer", Answer.class);
        OnePaper newpaper=(OnePaper)JSONObject.toBean(jsonObject,OnePaper.class,classMap);
        System.out.println(resp);
        List<OneSimproblem> oneSimps = newpaper.getSimp();
		return JSONObject.fromObject(teacherService.addNewpaper(newpaper,user,oneSimps)).toString();
	}
	
	
	@RequestMapping(value="Teacher/addContest",method={RequestMethod.POST},produces="text/html;charset=UTF-8")
	/**
	 * 添加新考试
	 * @param request
	 * @param response
	 * @return 0添加考试失败  1添加考试成功  -1权限不足
	 */
	@ResponseBody
	public String addContest(HttpServletRequest request,HttpServletResponse response) throws ParseException {
		
		String startTimeStr = request.getParameter("starttime");
		String endTimeStr = request.getParameter("endtime");
		Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTimeStr);
		Date endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTimeStr);
		int paperId = Integer.parseInt(request.getParameter("paperid"));
		String title = request.getParameter("title");
		
		
//		User user = (User)request.getSession().getAttribute("user");
//		String userId = user.getUserId();
		
		//测试
		User user = new User();
		String userId = "123";
		user.setLevel(1);
		user.setUserId(userId);
		
		//存数据进实体
		Contest contest = new Contest();
		contest.setContestId(6);
		contest.setEndtime(endTime);
		contest.setStarttime(startTime);
		contest.setPaperId(paperId);
		contest.setTeacher(userId);
		contest.setTitle(title);
		//System.out.println(contest.getEndtime());
	//	System.out.println(contest.getTitle());
		//String className = request.getParameter("classname");
		String className = "软件一班";
		return JSONObject.fromObject(teacherService.addContest(contest, className)).toString();
		
	}
	
	
	
	@RequestMapping(value="Teacher/selContestStatus",method={RequestMethod.POST},produces="text/html;charset=UTF-8")
	
	/**
	 * 教师查询所有考试Status
	 * @return Response
	 * 
	 */
	@ResponseBody
	public String selContestStatus(HttpServletRequest request,HttpServletResponse response) {
		//User user = (User)request.getSession().getAttribute("user");
		//测试
		User user = new User();
		user.setUserId("123");
		user.setLevel(1);

		return JSONObject.fromObject(teacherService.selContestStatus(user)).toString();
	}
}
