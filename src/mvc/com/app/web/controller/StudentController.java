/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 学生管理 
 * */
package com.app.web.controller;

import com.app.service.impl.*;
import com.app.tools.FileHelper;
import com.code.model.*;

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
	        String path = request.getServletContext().getRealPath("");
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
   

}

