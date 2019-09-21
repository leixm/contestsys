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

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	        model.addObject("leftTime", 86400);
	        
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
	public String examSubmit(@RequestBody String paper,HttpServletResponse response) throws Exception{
	      
    	System.out.println(paper);
    	Map classMap = new HashMap(); 
		classMap.put("simp", OneSimproblem.class);
		classMap.put("prob", OneProblem.class);
		classMap.put("answer", Answer.class);
		classMap.put("option", Options.class);
		OnePaper onePaper = (OnePaper) JSONObject.toBean(JSONObject.fromObject(paper), OnePaper.class, classMap);
	   	//OnePaper onePaper = oneContest.getPaper();
    	//OnePaper onePaper = (OnePaper)request.getAttribute("onePaper");
    	//System.out.println(onePaper);
	   		
	   	List<OneProblem> oneProblems = onePaper.getProb(); 
	   
   		List<OneSimproblem> oneSimproblems = onePaper.getSimp();
	   		
	   	//添加编程题作答结果
  		if(oneProblems.size()>0){
   			for(OneProblem oneProblem : oneProblems){
   				SolutionWithBLOBs solution = oneProblem.getSolution();
   				solution.setStatus(new Integer(0));
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
		return "成功";
	
	}
   
    /**
	 * 根据搜索条件模糊查询出学生成绩表
	 * @param request
	 * @param response
	 * @return 返回查询到的学生成绩等相关信息
	 */
	@RequestMapping(value="Student/selOneStuScore",method={RequestMethod.POST},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String selStuScore(HttpServletRequest request,HttpServletResponse response) {
		//获取所登录用户的user对象
		HttpSession session = request.getSession(); 
		User user = (User)session.getAttribute("user");	
		String userId;
		//此处判断是暂时用，需要修改
		if(session.getAttribute("user")!=null) {
			userId = user.getUserId();
		}else {
			userId = "1614080902233";
		}
		
		
		LayResponse layResp = new LayResponse();//layui参数返回格式
		layResp.setCode(1); //默认设置为1
		
		String className = request.getParameter("classname"); 
		String stuId = userId;
		String stuName = request.getParameter("stuname");
		String contestName = request.getParameter("contestname");
		//判断是否非空后再来去掉前后空格，防止空指针
		if(className!=null||"".equals(className)) {
			className = className.trim();
		}
		if(stuId!=null||"".equals(stuId)) {
			stuId = stuId.trim();	
		}
		if(stuName!=null||"".equals(stuName)) {
			stuName = stuName.trim();
		}
		if(contestName!=null||"".equals(contestName)) {
			contestName = contestName.trim();
		}
		//获取分页所需相关数据
		String pageSize = request.getParameter("limit"); //一页多少个
		String pageNumber = request.getParameter("page");	//第几页
		List<Map<String,Object>> resultList = studentService.selOneStuScore(className, stuId, stuName, contestName,pageSize,pageNumber); //数据库查询返回的学生成绩结果集
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

	 /**
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
	
	/**
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
	
}

