/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 学生管理 
 * */
package com.app.web.controller;

import com.app.service.impl.*;
import com.app.tools.FileHelper;
import com.app.tools.PathHelper;
import com.code.model.*;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class StudentController {

    @Resource
    private StudentService studentService;

    

    /**
	 * @author lxm
	 * @param 
	 * @description 请求开始考试
	 * @return 视图、学生信息、考试状态信息、剩余时间、试卷信息、考试信息
	 */
    @RequestMapping(value = "begin.do",produces = "text/html;charset=UTF-8", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody  
    public ModelAndView beginExam(
            @RequestParam("contest_status_id") Integer contest_status_id,
            HttpSession session,HttpServletRequest request) throws Exception{
    	ModelAndView model = new ModelAndView();
        
        if(session.getAttribute("user") == null){
        	model.addObject("error", "请先登录后再操作");
			model.setViewName("error");
			return model;
        }

        Date date = new Date();
        
        ContestStatus contestStatus = studentService.selectContestStatusByPrimaryKey(contest_status_id);
        Contest contest = studentService.selectContestByPrimaryKey(contestStatus.getContestId());
        if(contest != null){
	        int result1 = date.compareTo(contest.getStarttime());
	        int result2 = date.compareTo(contest.getEndtime());
	        if(result1 < 0 || result2 > 0){
	        	model.addObject("error","当前非考试时间，不能开始考试");
				model.setViewName("error");
	        }
	
	        User user = (User)session.getAttribute("user");
	        if(!user.getUserId().equals(contestStatus.getStudent())){
	        	model.addObject("error","没有该场考试的权限");
				model.setViewName("error");
	        }
	        //获取指定试卷的所有编程题
	        int paper_id = contest.getPaperId();
	        List<OneProblem> ProblemList = studentService.getProblemAndSolutionByPaperId(paper_id);
	       
	        //获取指定试卷的所有选择题和选项、填空题
	        List<OneSimproblem> SimproblemList = studentService.getSimproblemAndOptionByPaperId(paper_id);
	        //封装一张试题
	        OnePaper onePaper = new OnePaper();
	
	        Contestpaper contestpaper = studentService.selectContestpaperByPrimaryKey(paper_id);
	          
	        onePaper.setContestpaper(contestpaper);
	        onePaper.setProb(ProblemList); 
	        onePaper.setSimp(SimproblemList);  
	        System.out.println(JSONObject.fromObject(onePaper).toString());
	        model.addObject("paper",JSONObject.fromObject(onePaper).toString());
	        FileHelper fh = new FileHelper();
	        String path = PathHelper.getNormativePath(request);
	        model.addObject("student", user);
	        model.addObject("contestStatusId", contest_status_id);
	        model.addObject("contest", contest);
	        model.addObject("leftTime", (contest.getEndtime().getTime() - (new Date()).getTime())/1000);
	        
	        model.setViewName("contestpage.jsp");
        }
		return model;
        
    }
    
    /**
	 * @author lxm
	 * @param id: 考试id
	 * @description 跳转至考试页面
	 * @return
	 */
    @RequestMapping(value = "viewpaper.do", method = { RequestMethod.POST,RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView viewcontest(HttpServletRequest request, Integer id) throws IOException {

    	 HttpSession session = request.getSession();
    	 ModelAndView model = new ModelAndView();
    	 
    	if(session.getAttribute("user") == null){
        	model.addObject("error", "请先登录后再操作");
			model.setViewName("error");
			return model;
        } 
        System.out.println("$$$$$$id=" + id);
       
        Contestpaper contestPaper = studentService.selectContestpaperByPrimaryKey(id);
        
        if(contestPaper != null){
	 
	        //获取指定试卷的所有编程题
	        int paper_id = contestPaper.getPaperId();
	        List<OneProblem> ProblemList = studentService.getProblemAndSolutionByPaperId(paper_id);
	       
	        //获取指定试卷的所有选择题和选项、填空题
	        List<OneSimproblem> SimproblemList = studentService.getSimproblemAndOptionByPaperId(paper_id);
	        //封装一张试题
	        OnePaper onePaper = new OnePaper();
	
	        Contestpaper contestpaper = studentService.selectContestpaperByPrimaryKey(paper_id);
	          
	        onePaper.setContestpaper(contestpaper);
	        onePaper.setProb(ProblemList); 
	        onePaper.setSimp(SimproblemList);     
	        System.out.println(JSONObject.fromObject(onePaper).toString());
	        model.addObject("paper",JSONObject.fromObject(onePaper).toString());    

	  //      model.addObject("paper",fh.getFileContent(path + "\\测试数据\\text.json"));
	        Contest contest = new Contest();
	        contest.setTitle(contestPaper.getTitle());
	        User user = new User();
	        user.setRealname("测试用户");
	        model.addObject("student", user);
	        model.addObject("contestStatusId", id);
	        model.addObject("contest", contest);
	        model.addObject("leftTime", 10);
	        
	        model.setViewName("contestpage.jsp");
        }
		return model;
		
	}


	/**
	 * @author lxm
	 * @param contestStatusId: 考试状态id
	 * @description 跳转至考试页面
	 * @return
	 */
	@RequestMapping(value = "contest.do", method = { RequestMethod.POST,RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView startContest(HttpServletRequest request, Integer contestStatusId) throws IOException {

		HttpSession session = request.getSession();
		ModelAndView model = new ModelAndView();

		if(session.getAttribute("user") == null){
			model.addObject("error", "请先登录后再操作");
			model.setViewName("error");
			return model;
		}
		User user = (User)session.getAttribute("user");

		ContestStatus contestStatus = studentService.selectContestStatusByPrimaryKey(contestStatusId);
		Contest contest = studentService.selectContestByPrimaryKey(contestStatus.getContestId());
		Contestpaper contestPaper = studentService.selectContestpaperByPrimaryKey(contest.getPaperId());

		if(contestPaper != null){

			//获取指定试卷的所有编程题
			int paper_id = contestPaper.getPaperId();
			List<OneProblem> ProblemList = studentService.getProblemAndSolutionByPaperId(paper_id);

			//获取指定试卷的所有选择题和选项、填空题
			List<OneSimproblem> SimproblemList = studentService.getSimproblemAndOptionByPaperId(paper_id);
			//封装一张试题
			OnePaper onePaper = new OnePaper();

			onePaper.setContestpaper(contestPaper);
			onePaper.setProb(ProblemList);
			onePaper.setSimp(SimproblemList);
			System.out.println(JSONObject.fromObject(onePaper).toString());
			model.addObject("paper",JSONObject.fromObject(onePaper).toString());
			Date now_time = new Date();
			Date start_time = contest.getStarttime();
			Date end_time = contest.getEndtime();	
			long time_span = end_time.getTime() - now_time.getTime();	//计算考试剩余时间
			System.out.println("剩余时间----"+time_span);
			model.addObject("student", user);
			model.addObject("contestStatusId", contestStatusId);
			model.addObject("contest", contestPaper);
			model.addObject("leftTime", time_span/1000);

			model.setViewName("contestpage.jsp");
		}
		return model;

	}
	   
    	  
    /**
	 * @author lxm
	 * @param paper: 答卷内容
	 * @description 交卷
	 * @return 响应状态信息
	 */
    @RequestMapping(value = "submit.do", consumes = "application/json" ,produces = "text/html;charset=UTF-8", method = {RequestMethod.POST })
    @ResponseBody
	public String examSubmit(@RequestBody String paper,HttpServletRequest request,HttpServletResponse response) throws Exception{
	      
    	System.out.println(paper);
    	Map classMap = new HashMap(); 
		classMap.put("simp", OneSimproblem.class);
		classMap.put("prob", OneProblem.class);
		classMap.put("answer", Answer.class);
		classMap.put("option", Options.class);
		OnePaper onePaper = (OnePaper) JSONObject.toBean(JSONObject.fromObject(paper), OnePaper.class, classMap);
	   		
	   	List<OneProblem> oneProblems = onePaper.getProb(); 
	   
   		List<OneSimproblem> oneSimproblems = onePaper.getSimp();

	   	//添加编程题作答结果
  		if(oneProblems.size()>0){
   			for(OneProblem oneProblem : oneProblems){
   				SolutionWithBLOBs solution = oneProblem.getSolution();
   				solution.setStatus(new Integer(0));
				solution.setResult((short)0);
   				studentService.insert(solution);
   			}
   		}	   		
	   		
	   	//添加选择填空题作答结果
  		if(oneSimproblems.size()>0){
   			for(OneSimproblem oneSimproblem : oneSimproblems){
	   			Simsolution simsolution = oneSimproblem.getSimsolution();
	   			simsolution.setStatus(new Integer(0));
	   			studentService.insert(simsolution); 
   			} 
   		}

		Integer contestStatusId = Integer.parseInt(request.getParameter("contestStatusId"));
		ContestStatus contestStatus = new ContestStatus();
		contestStatus.setContestStatusId(contestStatusId);
		contestStatus.setStatus(new Integer(1));
		studentService.updateContestStatus(contestStatus);

		return "成功";
	
	}
   
    /**zzs
	 * 根据搜索条件模糊查询出学生成绩表
	 * @param request
	 * @param response
	 * @return 返回查询到的学生成绩等相关信息
	 */
	@RequestMapping(value="Student/selOneStuScore",method={RequestMethod.POST},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String selStuScore(HttpServletRequest request,HttpServletResponse response) {
		//获取所登录用户的user对象
		LayResponse layResp = new LayResponse();//layui参数返回格式
		HttpSession session = request.getSession(); 
		User user = (User)session.getAttribute("user");	
		String userId;
		//此处判断是暂时用，需要修改
		if(session.getAttribute("user")!=null) {
			userId = user.getUserId();
		}else {
			layResp.setMsg("无数据");
			return JSONObject.fromObject(layResp).toString();
		}
		System.out.println("userId==="+userId);
		
		
		layResp.setCode(1); //默认设置为1
		
		String stuId = userId;
		
		//获取分页所需相关数据
		String pageSize = request.getParameter("limit"); //一页多少个
		String pageNumber = request.getParameter("page");	//第几页
		List<Map<String,Object>> resultList = studentService.selOneStuScore(stuId,pageSize,pageNumber); //数据库查询返回的学生成绩结果集
		//获取分页插件的数据只能通过PageInfo来获取
		PageInfo pInfo = new PageInfo(resultList);
		Long total = pInfo.getTotal();
		
		if(resultList.size() > 0) {
			//返回规定格式给前端
			layResp.setCode(0);
			layResp.setCount(total.intValue());
			layResp.setData(resultList);
			
			return JSONObject.fromObject(layResp).toString();
		}
		layResp.setMsg("无数据");
		return JSONObject.fromObject(layResp).toString();
	}

	 /**zzs
		 * 根据条件更新学生用户的个人信息
		 * @param request
		 * @param response
		 * @return 更新是否成功
	 */
	@RequestMapping(value="Student/updateStuInfo",method={RequestMethod.POST},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updateStuInfo(HttpServletRequest request,HttpServletResponse response) {
		LayResponse layResp = new LayResponse();//layui参数返回格式
		layResp.setCode(1); //默认设置为1,1为失败
		
		String stuId = request.getParameter("stuid");
		String stuName = request.getParameter("stuname");
		String stuEmail = request.getParameter("stuemail");
		String newPwd = request.getParameter("newpwd1");
		
		int updateResult = studentService.updateStuInfo(stuId, stuName, stuEmail, newPwd);
		if(updateResult>0) {
			layResp.setCode(0);
			layResp.setMsg("修改成功");
		}
			layResp.setMsg("修改失败");
			
		return JSONObject.fromObject(layResp).toString();
	}
	
	/**zzs
	 * 校验前端填写的旧密码是否和session保存 的一致
	 * @param request
	 * @param response
	 * @return -1不一致  1一致
	 */
	@RequestMapping(value="Student/selOldPwd",method={RequestMethod.POST},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String selOldPwd(HttpServletRequest request,HttpServletResponse response) {
		
		String oldPwd = request.getParameter("oldpwd");
		
		User user = (User) request.getSession().getAttribute("user");
		String sessionPwd = user.getPassword();
		
		if(sessionPwd.equals(oldPwd)) {
			return "1";
		}
		
		return "-1"; 
	}
	
	/**zzs
	 * 根据条件查询学生个人的考试列表
	 * @param stuId	用户Id	
	 * @param keyword	搜索条件
	 * @return
	 */
	@RequestMapping(value="Student/selOwnContest",method={RequestMethod.POST},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String selOwnContest(HttpServletRequest request,HttpServletResponse response) {
		try {
			//获取user对象
			HttpSession session = request.getSession(); 
			User user = (User)session.getAttribute("user");		
			String stuId = user.getUserId();

			LayResponse layResp = new LayResponse();//layui参数返回格式
			layResp.setCode(1); //默认设置为1
			
			String keyword = request.getParameter("keyword"); 
			System.out.println(keyword);
			//判断是否非空后再来去掉前后空格，防止空指针
			if(keyword!=null||"".equals(keyword)) {
				keyword = keyword.trim();
			}
			
			//获取分页所需相关数据
			String pageSize = request.getParameter("limit"); //一页多少个
			String pageNumber = request.getParameter("page");	//第几页
			List<Map<String,Object>> resultList = studentService.selOwnContest(stuId, keyword, pageSize, pageNumber);
			
			//计算考试的状态：未开始，正在进行，已结束
			if(resultList.size()>0) {
				for (Map<String, Object> map : resultList) {
					String cstatus = ""; //中文字描述考试状态
					String status = map.get("status").toString();	// 0、1、2数据库表考试状态;	
					String firstStatus = "";
					
					Date nowDate = new Date();
					String start = map.get("starttime").toString();
					String end = map.get("endtime").toString();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					Date startDate = sdf.parse(start);
					Date endDate = sdf.parse(end);
					String contestStatusId = map.get("cstatusid").toString();
					map.put("cstatusid", contestStatusId);
					map.put("status", status);
					//结合时间和数据库status状态来给与cstatus中文描述考试状态
					switch(status) {
						case "0":
							firstStatus = "（未作答）";
							break;
						case "1":
							firstStatus = "（正在批改）";
							break;
						case "2":
							firstStatus = "（已作答）";
							break;
					}
					if(nowDate.getTime()<startDate.getTime()) {
						cstatus = "未开始"+firstStatus;
					}else if(endDate.getTime() >= nowDate.getTime() && nowDate.getTime() >= startDate.getTime()) {
						cstatus = "正在进行"+firstStatus;
					}else {
						cstatus = "已结束"+firstStatus;
					}
					map.put("cstatus", cstatus);
				}
			}
			
			//获取分页插件的数据只能通过PageInfo来获取
			PageInfo pInfo = new PageInfo(resultList);
			Long total = pInfo.getTotal();
			
			if(resultList.size() > 0) {
				//返回规定格式给前端
				layResp.setCode(0);
				layResp.setCount(total.intValue());
				layResp.setData(resultList);
				
				return JSONObject.fromObject(layResp).toString();
			}
			layResp.setMsg("无数据");
			return JSONObject.fromObject(layResp).toString();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**zzs
	 * 根据条件查询已经结束了的考试列表（不能展现考试完成的，不然发生作弊bug）
	 * @param stuId	用户Id	
	 * @param keyword	搜索条件
	 * @return
	 */
	@RequestMapping(value="Student/selOwnContestFinished",method={RequestMethod.POST},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String selOwnContestFinished(HttpServletRequest request,HttpServletResponse response) {
		try {
			//获取user对象
			HttpSession session = request.getSession(); 
			User user = (User)session.getAttribute("user");		
			String stuId = user.getUserId();
			System.out.println("userId:----"+user.getUserId());
			
			LayResponse layResp = new LayResponse();//layui参数返回格式
			layResp.setCode(1); //默认设置为1
			
			String keyword = request.getParameter("keyword"); 
			System.out.println(keyword);
			//判断是否非空后再来去掉前后空格，防止空指针
			if(keyword!=null||"".equals(keyword)) {
				keyword = keyword.trim();
			}
			
			//获取分页所需相关数据
			String pageSize = request.getParameter("limit"); //一页多少个
			String pageNumber = request.getParameter("page");	//第几页
			List<Map<String,Object>> resultList = studentService.selOwnContest(stuId, keyword, pageSize, pageNumber);
			List<Map<String,Object>> overContestList = new ArrayList<Map<String,Object>>(); //挑选已结束的考试进集合
			//计算考试的状态：未开始，正在进行，已结束
			if(resultList.size()>0) {
				List<Integer> leftList = new ArrayList<>();	//记录需要留下来的结果集resultList的下标，
				int i = 0;//结果集初始下标
				for (Map<String, Object> map : resultList) {
					String cstatus = ""; //中文字描述考试状态
					String status = map.get("status").toString();	// 0、1、2数据库表考试状态;	
					String firstStatus = "";
					
					Date nowDate = new Date();
					String start = map.get("starttime").toString();
					String end = map.get("endtime").toString();
					String paperId = map.get("paper_id").toString();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					Date startDate = sdf.parse(start);
					Date endDate = sdf.parse(end);
					String contestStatusId = map.get("cstatusid").toString();
					map.put("cstatusid", contestStatusId);
					map.put("status", status);
					map.put("paper_id", paperId);
					//结合时间和数据库status状态来给与cstatus中文描述考试状态
					switch(status) {
						case "0":
							firstStatus = "（未作答）";
							break;
						case "1":
							leftList.add(i);
							firstStatus = "（正在批改）";
							break;
						case "2":
							firstStatus = "（已作答）";
							break;
					}
					if(nowDate.getTime()<startDate.getTime()) {
						cstatus = "未开始"+firstStatus;
					}else if(endDate.getTime() >= nowDate.getTime() && nowDate.getTime() >= startDate.getTime()) {
						cstatus = "正在进行"+firstStatus;
					}else {
						leftList.add(i);
						cstatus = "已结束"+firstStatus;
					}
					i++;
					map.put("cstatus", cstatus);
				}
				System.out.println("leftList+st0----"+leftList);
				if(!leftList.isEmpty()) {		//挑选已结束的考试进集合
					for(int j : leftList) {
						overContestList.add(resultList.get(j));						
					}
				}
			}
			
			//获取分页插件的数据只能通过PageInfo来获取
			PageInfo pInfo = new PageInfo(overContestList);
			Long total = pInfo.getTotal();
			
			if(overContestList.size() > 0) {
				//返回规定格式给前端
				layResp.setCode(0);
				layResp.setCount(total.intValue());
				layResp.setData(overContestList);
				
				return JSONObject.fromObject(layResp).toString();
			}
			
			layResp.setMsg("无数据");
			return JSONObject.fromObject(layResp).toString();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * zzs
	 * 查询所有的班级的所有考试的平均分
	 * @return 按照Echars所需要的格式返回JSON格式的参数 
	 */
	@RequestMapping(value = "Student/selOneStuScoreAVG", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String selOneStuScoreAVG(HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<String> seriesList = new ArrayList<String>(); //存series供图表用
		Set set = new HashSet(); //利用set特性来去重
		List<String> titleList = new ArrayList<String>();	//格式为：['titleList', '个人成绩', '所在班级平均分'],
		titleList.add("titleList");		//------+1
		titleList.add("个人成绩");	
		titleList.add("所在班级平均分");	
		List<String> contestNameList = new ArrayList<String>();	//格式为：['C语言测试',''C++语言测试''。。。。。],
		List<List<String>> allScoreList = new ArrayList<>(); //封装多条数据集，部分格式为： ['C语言测试', '43.3', 85.8],
		allScoreList.add(titleList);  //添加固定集合头头  echars规定格式
		
		//创建供sql in 语句查询的对象，下面对前端参数进行分割拼接成list
		List conList = new ArrayList();
		
		if(request.getParameter("selcontest") != null && !("null").equals(request.getParameter("selcontest"))) {
			String contest = request.getParameter("selcontest").toString();
			contest = contest.substring(2,contest.length()-2); //去掉首尾["  "]
			contest = contest.replaceAll("\",\"",",");
			String str[] = contest.split(",");
			for (String string : str) {
				conList.add(string);
			}
		}else {
			conList = null;
		}
		//通过session获取userId
		User user = (User)request.getSession().getAttribute("user");
		String stuId = user.getUserId();
		
		//获取某个学生考试的成绩和班级平均分
		List<Map<String,Object>> OneStuScoreList = studentService.selOneStuScoreAVG(stuId, conList);
		if(OneStuScoreList.size() > 0) {
			for (Map<String, Object> map : OneStuScoreList) {
				List<String> OneStuScore = new ArrayList<String>();  //封装一条数据集['C语言测试', '43.3', 85.8],
				String title = map.get("title").toString();
				String score = map.get("score").toString();
				String avg = map.get("class_avg").toString();
				
				contestNameList.add(title);
				OneStuScore.add(title);
				OneStuScore.add(score);
				OneStuScore.add(avg);
				allScoreList.add(OneStuScore);
			}
			
			
			//计算并拼接 series: [ {type: 'bar'},{type: 'bar'}] 
			int size = 2;	//因为只有两个['titleList', '个人成绩', '所在班级平均分']所以size=2
			String series = "{type: 'bar'}";
			for(int i=0;i<size;i++)  {
				seriesList.add(series);
			}
		}
		resultMap.put("series", seriesList);
		resultMap.put("dataset", allScoreList);
		resultMap.put("msg", "请求成功");
		resultMap.put("status", 1);
		return JSONObject.fromObject(resultMap).toString();
	}
	
	/**
	 * @description 查所有的考试名称
	 * @return 响应状态
	 */
	@RequestMapping(value = "Student/selStuContestTitle", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String selStuContestTitle(HttpServletRequest request) {
		//通过session获取userId
		User user = (User)request.getSession().getAttribute("user");
		String stuId = user.getUserId();
				
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<Map<String,Object>> contestList = studentService.selStuContestTitle(stuId);
		List<String> contestNameList = new ArrayList<String>();
		
		if(contestList.size() > 0) {
			for (Map<String, Object> contestObj : contestList) {
				contestNameList.add(contestObj.get("title").toString());
			}
		}
		
		resultMap.put("contestname", contestNameList);
		resultMap.put("msg", "查询成功");
		return JSONObject.fromObject(resultMap).toString();
	}
}

