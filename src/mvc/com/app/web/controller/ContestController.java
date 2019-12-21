/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 考试管理 
 * */
package com.app.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.app.service.impl.ClassService;
import com.app.service.impl.ContestPaperService;
import com.app.service.impl.ContestService;
import com.app.service.impl.UserService;
import com.code.model.Contest;
import com.code.model.LayResponse;
import com.code.model.User;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class ContestController {

	@Resource
	private ContestService contestService;
	 
	@Resource
	private ContestPaperService contestpaperService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private ClassService classService;
	
	/**
	 * @author lxm
	 * @param keyWord:查询关键字 
	 * @param startTime: 开始时间
	 * @param endTime: 结束时间 
	 * @description 查询考试
	 * @return 考试实例列表
	 */
	@RequestMapping(value = "Contest/GetAllContest", method = { RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String GetAllContest(HttpServletRequest request, HttpServletResponse response, String Keyword,String startTime,String endTime)
			throws Exception {
		//获取分页所需相关数据
		String pageSize = request.getParameter("limit"); //一页多少个
		String pageNumber = request.getParameter("page");	//第几页
		List resultList = contestService.GetAllContest(Keyword,startTime,endTime,pageSize,pageNumber); //数据库查询返回的学生成绩结果集
		//获取分页插件的数据只能通过PageInfo来获取
		PageInfo pInfo = new PageInfo(resultList);
		Long total = pInfo.getTotal();
		
		JSONObject obj = new JSONObject();
		JSONArray arr = JSONArray.fromObject(resultList);
		System.out.println("count---"+arr.size());
		obj.put("code", 0);
		obj.put("msg", "返回成功");
		obj.put("count",total.intValue());
		obj.put("data", arr);
		return obj.toString();
	}
	
	/**
	 * @author lxm
	 * @description 跳转添加考试页面
	 * @return 视图、考试信息、教师信息
	 */
	@RequestMapping(value = "addcontest.do", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView PrepareAddContest(HttpServletRequest request) {
		//获取登录对象，判断其角色
		User user = (User)request.getSession().getAttribute("user");
		int level = user.getLevel(); //学生： 0  老师：1  管理员：2
		String userId = user.getUserId();
		if(level == 2) {	//管理员角色
			ModelAndView mav = new ModelAndView();
	        mav.addObject("paper", JSONArray.fromObject(contestpaperService.GetAllContestPaper(null,null,null,null,null)));
	        mav.addObject("teachers",userService.getAllTeacher());
			mav.setViewName("contest-add.jsp");
			return mav;
		}
		//	教师角色
		ModelAndView mav = new ModelAndView();
        mav.addObject("paper", JSONArray.fromObject(contestpaperService.GetAllContestPaper(null,null,null,null,null)));
        mav.addObject("teachers",userService.getTeacherById(userId));
		mav.setViewName("contest-add.jsp");
		return mav;
	}
	
	/**
	 * @author lxm
	 * @param contest: 考试实例
	 * @description 添加考试
	 * @return 响应状态
	 */
	@RequestMapping(value = "Contest/AddContest", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String AddContest(HttpServletRequest request, Contest contest) throws ParseException {
		System.out.println(JSONObject.fromObject(contest).toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		contest.setStarttime(sdf.parse(contest.getStartTimeS())); 
		contest.setEndtime(sdf.parse(contest.getEndTimeS()));
		LayResponse response = new LayResponse();
		response.setCode(1);
		if (contestService.AddContest(contest) > 0) {
			response.setCode(0);
			return JSONObject.fromObject(response).toString();
		}
		response.setMsg("添加失败");
		return JSONObject.fromObject(response).toString();
	}
	
	/**
	 * @author lxm
	 * @param id: 考试id
	 * @description 跳转更新考试页面
	 * @return 考试视图、试卷信息、教师信息、考试信息
	 */
	@RequestMapping(value = "editcontest.do", method = { RequestMethod.POST,RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView EditContest(HttpServletRequest request, String id) {
		//获取登录对象，判断其角色
		User user = (User)request.getSession().getAttribute("user");
		int level = user.getLevel(); //学生： 0  老师：1  管理员：2
		String userId = user.getUserId();
		if(level == 2) {	//管理员角色
			ModelAndView mav = new ModelAndView();
			mav.addObject("paper",JSONArray.fromObject(contestpaperService.GetAllContestPaper(null,null,null,null,null)));
			mav.addObject("teachers", userService.getAllTeacher());
			JSONArray arr = JSONArray.fromObject(contestService.GetAllContest(id,null,null,null,null));
			for(int i=0;i<arr.size();i++){
				if(arr.getJSONObject(i).getInt("contest_id") == Integer.parseInt(id)){
						mav.addObject("contest",arr.getJSONObject(i));
						break;
				}
			} 
			 
			mav.setViewName("contest-edit.jsp"); 
			return mav;
		}
		ModelAndView mav = new ModelAndView();			//教师角色
		
		mav.addObject("paper", JSONArray.fromObject(contestpaperService.GetAllContestPaper(null,null,null,null,null)));
		mav.addObject("teachers",userService.getTeacherById(userId));
		JSONArray arr = JSONArray.fromObject(contestService.GetAllContest(id,null,null,null,null));
		for(int i=0;i<arr.size();i++)
		{
		if(arr.getJSONObject(i).getInt("contest_id") == Integer.parseInt(id))
		{
			mav.addObject("contest",arr.getJSONObject(i));
			break;
		}
		} 
		 
		mav.setViewName("contest-edit.jsp"); 
		return mav;
		
	}
	
	/**
	 * @author lxm
	 * @param contest: 考试实例
	 * @description 更新考试
	 * @return 响应状态
	 */
	@RequestMapping(value = "Contest/UpdateContest", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String UpdateContest(HttpServletRequest request,Contest contest) throws ParseException {
		System.out.println(JSONObject.fromObject(contest).toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		contest.setStarttime(sdf.parse(contest.getStartTimeS())); 
		contest.setEndtime(sdf.parse(contest.getEndTimeS()));
		LayResponse response = new LayResponse();
		response.setCode(1);
		if (contestService.UpdateContest(contest) > 0) {
			response.setCode(0);
			return JSONObject.fromObject(response).toString();
		} else {
			response.setMsg("更新失败");
			return JSONObject.fromObject(response).toString();
		}
	}
	
	/**
	 * @author lxm
	 * @param id: 考试id
	 * @description 删除单个考试
	 * @return 响应状态
	 */
	@RequestMapping(value = "Contest/DeleteContest", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String DeleteContest(HttpServletRequest request, String id) {

		LayResponse response = new LayResponse();
		response.setCode(1);
		if (contestService.DeleteContest(id) > 0) {
			response.setCode(0);
			return JSONObject.fromObject(response).toString();
		} else {
			response.setMsg("更新失败");
			return JSONObject.fromObject(response).toString();
		}
	}
	
	/**
	 * @author lxm
	 * @param ids: 考试id列表
	 * @description 批量删除考试
	 * @return 响应状态
	 */
	@RequestMapping(value = "Contest/DelAll", consumes = "application/json", produces = "text/html;charset=UTF-8", method = {RequestMethod.POST })
	@ResponseBody
	public String DelAll(HttpServletRequest request, @RequestBody List<String> ids) {
		  //规定返回对象，默认设置Code为1表示失败
		  LayResponse response = new LayResponse(); 
		  response.setCode(1);
		  
		//获取登录对象，判断其角色
		User user = (User)request.getSession().getAttribute("user");
		int level = user.getLevel(); //学生： 0  老师：1  管理员：2
		String userId = user.getUserId();
		System.out.println("userid+++"+userId);
		if(level>0) {
			//权限校验：
			/*用用户的userId查contest表，获取所有的contestId集合，校验用户选中删除的考试id和数据库表的考试id有没有不同，有则退出，不给予删除*/
			List<Map<String,Object>> selList = contestService.selAllContestByUserId(userId);	//获取userId对应下的contest集合
			System.out.println("selList----"+selList);
			System.out.println("idsSize-----"+ids.size());
			
			int i = 0; //记录ids是否全部与数据库的contestId对应
			if(selList.size()>0 ) {
				for(Map map : selList) {
					for(String idStr : ids) {
						if(map.get("contest_id").toString().equals(idStr)) {
							i++;
						}
					}
				}
				System.out.println("iSize------"+i);
				if(i != ids.size()) {	//校验用户选中删除的考试id和数据库表的考试id有不同
					response.setMsg("删除失败,只能选择自己负责的考试进行删除"); 
					return JSONObject.fromObject(response).toString();
				}
				
				//进行删除操作
				if(contestService.DeleteAllContest(ids)>0){
				    response.setCode(0); 
				    System.out.println(JSONObject.fromObject(response).toString());
				    return JSONObject.fromObject(response).toString(); 
				}else{
				   response.setMsg("删除失败"); 
				   return JSONObject.fromObject(response).toString(); 
				}
			}else if(level == 2) { //管理员
				//进行删除操作
				if(contestService.DeleteAllContest(ids)>0){
				    response.setCode(0); 
				    System.out.println(JSONObject.fromObject(response).toString());
				    return JSONObject.fromObject(response).toString(); 
				}else{
				   response.setMsg("删除失败"); 
				   return JSONObject.fromObject(response).toString(); 
				}
			}
			//查询不到userId下的contest对象，故返回失败
			response.setMsg("无权限，删除失败"); 
			return JSONObject.fromObject(response).toString();
			
		}else {	
			response.setMsg("无权限，删除失败"); 
			return JSONObject.fromObject(response).toString();
		}
		 
	}
	
	
	
	/**
	 * @author lxm
	 * @param id: 考试id
	 * @description 跳转至查看单场考试学生名单页面
	 * @return 视图、考试id
	 */
	@RequestMapping(value = "viewContestStudent.do", method = { RequestMethod.POST,RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView viewContestStudent(HttpServletRequest request, String id) {

		ModelAndView mav = new ModelAndView();
		mav.addObject("contest_id", id);
		mav.setViewName("conteststudent.jsp"); 
		return mav;
	} 
	
	/**
	 * @author lxm
	 * @param id: 考试id
	 * @description 跳转至添加单场考试学生页面
	 * @return 视图、班级信息、考试id
	 */
	@RequestMapping(value = "addContestStudent.do", method = { RequestMethod.POST,RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView addContestStudent(HttpServletRequest request, String id) {

		ModelAndView mav = new ModelAndView();
		//获取登录对象，判断其角色
		User user = (User)request.getSession().getAttribute("user");
		String userId = user.getUserId().toString();
		mav.addObject("classes", classService.GetAllClass(userId,null,null,null));
		mav.addObject("contest_id", id); 
		mav.setViewName("conteststudent-add.jsp"); 
		return mav;
	}
	
	/**
	 * @author lxm
	 * @param id: 考试id
	 * @description 查看单场考试的学生名单
	 * @return 响应状态
	 */
	@RequestMapping(value = "Contest/ViewContestStudent", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String ViewContestStudent(HttpServletRequest request, String id)
	{
		LayResponse response = new LayResponse();
		List<Map<String,Object>> list = contestService.GetAllContestStudent(id);
		response.setCode(0);
		response.setCount(list.size());
		response.setMsg("返回成功");
		response.setData(list);
		
		return JSONObject.fromObject(response).toString();
	}
	
	/**
	 * @author lxm&zzs
	 * @description 添加考试班级(教师只能给负责的考试加考试班级)
	 * @return 响应状态
	 */
	@RequestMapping(value = "Contest/AddContestClass", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String AddContestClass(HttpServletRequest request) throws ParseException {
		//规定返回对象，默认设置Code为1表示失败
		LayResponse response = new LayResponse();	
		response.setCode(1); 
		
		//获取登录对象，判断其角色
		User user = (User)request.getSession().getAttribute("user");
		int level = user.getLevel(); //学生： 0  老师：1  管理员：2
		String userId = user.getUserId();
		
		if(level>0) {
			String contestId = request.getParameter("contestId");
			String classId = request.getParameter("classId");		//classId：1,2,3
			
			if(contestId==null || classId==null || classId.equals(""))
			{	
				response.setMsg("请选并择添加考试班级");
				JSONObject.fromObject(response).toString();
				return JSONObject.fromObject(response).toString();
			}
			//判断操作用户的id是否和contest表中的teacher一样
			List<Map<String,Object>> selList = contestService.selOneContestById(contestId);
			String selUserId = selList.get(0).get("teacher").toString();	//获取某个conetstId对应下的userId
			System.out.println("selUserId-----"+selUserId);
			if(selUserId.equals(userId) || level>1) {	//用户的id是否和contest表中的teacher一样  或者  是管理员
				//分割classId 组成集合
				String[] classIdStrArr = classId.split(",");
				List<String> classIdList = new ArrayList<>();
				for(String classIdStr : classIdStrArr) {	//遍历classIdStr并添加其为考试班级
					contestService.AddContestClass(Integer.parseInt(contestId), Integer.parseInt(classIdStr));
				}
				response.setCode(0);
				response.setMsg("添加成功"); 
				return JSONObject.fromObject(response).toString();
			}else {	//无权限添加试卷
				response.setMsg("无法添加考试班级，请对你自己负责的考试进行操作"); 
				return JSONObject.fromObject(response).toString();
			}
			
			
			
		}else {	
			response.setMsg("无权限，添加失败"); 
			return JSONObject.fromObject(response).toString();
		}
	}
	
	
	/**
	 * @author zzs
	 * @description 添加考试班级(教师只能给负责的考试加考试班级)
	 * @return 响应状态
	 */
	@RequestMapping(value = "Contest/judgeUserPower", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String judgeUserPower(HttpServletRequest request) throws ParseException {
		//规定返回对象，默认设置Code为1表示失败
		LayResponse response = new LayResponse();	
		response.setCode(1); 
		
		//获取登录对象，判断其角色
		User user = (User)request.getSession().getAttribute("user");
		int level = user.getLevel(); //学生： 0  老师：1  管理员：2
		String userId = user.getUserId();
		
		if(level>0) {
			String contestId = request.getParameter("contestId");
			
			//判断操作用户的id是否和contest表中的teacher一样
			List<Map<String,Object>> selList = contestService.selOneContestById(contestId);
			String selUserId = selList.get(0).get("teacher").toString();	//获取某个conetstId对应下的userId
			System.out.println("selUserId-----"+selUserId);
			if(selUserId.equals(userId) || level>1) {	//用户的id是否和contest表中的teacher一样  或者  是管理员
				response.setCode(0);
				response.setMsg("操作权限验证通过"); 
				return JSONObject.fromObject(response).toString();
			}else {	//无权限添加试卷
				response.setMsg("无法执行操作，请选择自己负责的考试进行操作"); 
				return JSONObject.fromObject(response).toString();
			}
			
		}else {	
			response.setMsg("无权限，添加失败"); 
			return JSONObject.fromObject(response).toString();
		}
	}
	
	
	
	
	 
	/*@RequestMapping(value = "viewcontest.do", method = { RequestMethod.POST,RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView viewcontest(HttpServletRequest request, String id) throws IOException {

		
		String path = request.getServletContext().getRealPath("\\测试数据\\text.json");
		Map classMap = new HashMap();
		classMap.put("simp", OneSimproblem.class);
		classMap.put("prob", OneProblem.class);
		classMap.put("answer", Answer.class);
		classMap.put("option", Options.class);
		
	    OnePaper onePaper = (OnePaper) JSONObject.toBean(JSONObject.fromObject(path),OnePaper.class,classMap);
	    
	    System.out.println(JSONArray.fromObject(onePaper.getSimp()).toString());
		FileHelper fh = new FileHelper();
		ModelAndView mav = new ModelAndView();
		System.out.println(fh.getFileContent(path));
		
		
		User user = new User();
		user.setUserId("1614080902217");
		user.setRealname("陈柏俊");
		 

        Contest contest = new Contest(); 
        contest.setTitle("C语言程序设计期末考试");
          
        mav.addObject("contest", contest);
		mav.addObject("contestStatusId", 1);
		mav.addObject("student",user);
		mav.addObject("leftTime", 600); 
		mav.addObject("paper", fh.getFileContent(path)); 
		
		mav.setViewName("contestpage.jsp"); 
		return mav;
		
	}*/

	
}
