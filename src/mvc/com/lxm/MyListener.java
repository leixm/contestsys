/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 监听器类，随项目运行而启动，自动批改简单题目
 * */
package com.lxm;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.app.dao.AnswerMapper;
import com.app.dao.ContestMapper;
import com.app.dao.ContestStatusMapper;
import com.app.dao.ContestpaperMapper;
import com.app.dao.OptionsMapper;
import com.app.dao.ProblemMapper;
import com.app.dao.SimproblemMapper;
import com.app.dao.SimsolutionMapper;
import com.app.dao.SolutionMapper;
import com.app.dao.UserMapper;
import com.app.tools.PdfHelper;
import com.app.tools.SendEmail;
import com.code.model.Answer;
import com.code.model.AnswerExample;
import com.code.model.Contest;
import com.code.model.ContestStatus;
import com.code.model.ContestStatusExample;
import com.code.model.Contestpaper;
import com.code.model.OneContest;
import com.code.model.OnePaper;
import com.code.model.OneProblem;
import com.code.model.OneSimproblem;
import com.code.model.Options;
import com.code.model.OptionsExample;
import com.code.model.Problem;
import com.code.model.ProblemExample;
import com.code.model.ProblemWithBLOBs;
import com.code.model.Simproblem;
import com.code.model.SimproblemExample;
import com.code.model.Simsolution;
import com.code.model.SimsolutionExample;
import com.code.model.Solution;
import com.code.model.SolutionExample;
import com.code.model.SolutionWithBLOBs;
import com.code.model.User;

import net.sf.json.JSONArray;


public class MyListener implements ServletContextListener{

	private MyThread mythread;
 
	@Override
	public void contextDestroyed(ServletContextEvent e) {
		// TODO Auto-generated method stub
		if(mythread!=null && mythread.isInterrupted())
		{
			mythread.interrupt();
		}
	}
	@Override
	public void contextInitialized(ServletContextEvent e) {
		// TODO Auto-generated method stub
		System.out.println("[Listener] contextInitialized!");
		
	    String path = e.getServletContext().getRealPath("");
		
		mythread = new MyThread();

		//mythread.setPath(path);
		//mythread.start();
	}
}

class MyThread extends Thread{
	
	@Autowired
	private SimsolutionMapper simsolutionDao;
	
	@Autowired
	private SimproblemMapper simproblemDao;
	
    @Autowired
    private OptionsMapper optionDao;
    
    @Autowired
    private AnswerMapper answerDao;
    
    @Autowired
    private ContestStatusMapper contestStatusDao;
    
    @Autowired
    private SolutionMapper solutionDao;
	
    @Autowired
    private ProblemMapper problemDao;
    
    @Autowired
    private ContestMapper contestDao;
    
    @Autowired
    private ContestpaperMapper contestpaperDao;
    
    @Autowired
    private UserMapper userDao;
    
	private String path;
	
	
	public String getPath() {
		return path;
	}



	public void setPath(String path) {
		this.path = path;
	}






	public void run()
	{
		while(true)
		{
			
			SimsolutionExample simsolutionExample = new SimsolutionExample(); 
			SimsolutionExample.Criteria simsolutionCriteria = simsolutionExample.createCriteria();
			simsolutionCriteria.andStatusEqualTo(new Integer(0));
			List<Simsolution> simsolutions = simsolutionDao.selectByExample(simsolutionExample);  //未批改的题目
			if(simsolutions.size()>0)
			{
				for(Simsolution simsolution : simsolutions)
				{
					Simproblem simproblem = simproblemDao.selectByPrimaryKey(simsolution.getSimproblemId());  //找对应的题目
					
					if(simproblem==null) continue;  //找不到题目
					AnswerExample answerExample = new AnswerExample();
					AnswerExample.Criteria answerCriteria = answerExample.createCriteria();
					answerCriteria.andSimproblemIdEqualTo(simproblem.getSimproblemId());  //找到这题的答案
					List<Answer> answers = answerDao.selectByExample(answerExample);
					if(answers.size()>0){
						BigDecimal bd = new BigDecimal(0);  //分数

						int type = simproblem.getType().intValue();   //题目类型
						
						if(type==1 || type==3)  //单选和判断 只需要匹配一个答案即可 且答案必定只有一个
						{
							for(Answer answer : answers)  //答题正确
							{
								if(answer.getContent().equals(simsolution.getAnswer())){
									simsolution.setStatus(new Integer(1));
									bd = simproblem.getScore();
									break;
								}
							}	
						}
						else if(type==5) //简答题
						{
							for(Answer answer : answers)  //简答题 包含答案即可
							{
								if(simsolution.getAnswer().contains(answer.getContent())){
									simsolution.setStatus(new Integer(1));
									bd = simproblem.getScore();
									break;
								}
							}
						}
						else if(type==2){ //多选题
							JSONArray arr = JSONArray.fromObject(simsolution.getAnswer());
						    boolean flag = true;
						    for(int i=0;i<answers.size();i++)  //必须选对所有选项
						    {
						    	if(!arr.contains(answers.get(i).getContent())){
						    		flag = false;
						    		break;
						    	}
						    }
						    if(flag){
						    	bd = simproblem.getScore();
						    }
						}
						else if(type==4)  //填空题 对应的空必须等于正确答案
						{
							JSONArray arr = JSONArray.fromObject(simsolution.getAnswer());
							double count = 0;
							for(int i=0;i<simproblem.getBlanks().intValue();i++)  //第i+1个空
							{
								for(int j=0;j<answers.size();j++){
									if(answers.get(j).getPos().intValue()==i+1 && arr.get(i)!=null && answers.get(j).getContent().equals(arr.get(i))){
										count++;  //答对一个空
										break;
									}
								}
								
							}
							
						bd = new BigDecimal(simproblem.getScore().doubleValue() * (count/simproblem.getBlanks().intValue())); //根据对的空 给分
							
						}
						
                        Simsolution newSimsolution = new Simsolution();
                        newSimsolution.setScore(bd);
                        newSimsolution.setStatus(new Integer(1));
                        newSimsolution.setSimsolutionId(simsolution.getSimsolutionId());
						simsolutionDao.updateByPrimaryKeySelective(newSimsolution);  //更新提交记录

						ContestStatus contestStatus =  contestStatusDao.selectByPrimaryKey(simsolution.getContestStatusId());
						if(contestStatus!=null)
						{
							contestStatus.setScore(new BigDecimal(contestStatus.getScore().doubleValue() + bd.doubleValue())); //更新分数
							contestStatusDao.updateByPrimaryKey(contestStatus);
						}
					}
					
					
				}
				
			}
			
			//编程题
			SolutionExample solutionExample = new SolutionExample();
			SolutionExample.Criteria solutionCriteria = solutionExample.createCriteria();
			short result = 1;
			solutionCriteria.andResultGreaterThan(result);  //result大于1 即评判完成的题目
			solutionCriteria.andStatusEqualTo(new Integer(0));  //未完成批改
			List<Solution> solutions = solutionDao.selectByExample(solutionExample);
			for(Solution solution : solutions)
			{
				Problem problem = problemDao.selectByPrimaryKey(solution.getProblemId()); //找对应题目
				double score = problem.getScore().doubleValue();  //分数
				ContestStatus contestStatus = contestStatusDao.selectByPrimaryKey(solution.getContestStatusId()); //考试记录
				if(contestStatus!=null)
				{
					contestStatus.setScore(new BigDecimal(contestStatus.getScore().doubleValue() + score * solution.getPassRate().doubleValue())); //根据通过率给分
					contestStatusDao.updateByPrimaryKey(contestStatus);  //更新记录
					
					solution.setStatus(new Integer(1));
				    solutionDao.updateByPrimaryKey(solution); //更新这一题为已经批改
				}
			}
			
			ContestStatusExample contestStatusExample = new ContestStatusExample();
			ContestStatusExample.Criteria contestStatusCriteria = contestStatusExample.createCriteria();
			contestStatusCriteria.andStatusEqualTo(new Integer(1));   //所有待完成批改的考试记录
			List<ContestStatus> contestStatuss = contestStatusDao.selectByExample(contestStatusExample);
			
			for(ContestStatus contestStatus : contestStatuss)
			{
				simsolutionExample = new SimsolutionExample();
				simsolutionCriteria = simsolutionExample.createCriteria();
				simsolutionCriteria.andContestStatusIdEqualTo(contestStatus.getContestStatusId());
				simsolutionCriteria.andStatusEqualTo(new Integer(0)); //未批改
	            simsolutions = simsolutionDao.selectByExample(simsolutionExample);	
	            if(simsolutions.size()>0)  //存在未批改
	            	continue;
	            
	            solutionExample = new SolutionExample();
	            solutionCriteria = solutionExample.createCriteria();
	            solutionCriteria.andContestStatusIdEqualTo(contestStatus.getContestStatusId());
	            solutionCriteria.andStatusEqualTo(new Integer(0));
	            solutions = solutionDao.selectByExample(solutionExample);
	            if(solutions.size()>0) continue;
	            
	            contestStatus.setStatus(new Integer(2));  //这次考试已经批改完成了
	            contestStatusDao.updateByPrimaryKey(contestStatus);
	           
	           //尝试生成该试卷的答卷情况PDF 
	           OneContest oneContest = new OneContest();
	           
	           OnePaper paper = new OnePaper();  
	           Contest contest = contestDao.selectByPrimaryKey(contestStatus.getContestId());
	           if(contest==null) continue;
	           
	           Contestpaper contestPaper = contestpaperDao.selectByPrimaryKey(contest.getPaperId());
	           if(contestPaper==null) continue;
	           paper.setContestpaper(contestPaper); //试卷信息
 	           
	           oneContest.setContest(contest);  //考试信息
	            
	           User student = userDao.selectByPrimaryKey(contestStatus.getStudent());
	           if(student==null) continue;
	           oneContest.setStudent(student);  //考生信息
	           
	  //         paper.setContestStatus(contestStatus);
	           
	           SimproblemExample simproblemExample = new SimproblemExample();  
	           SimproblemExample.Criteria simproblemExampleCriteria = simproblemExample.createCriteria();
	           simproblemExampleCriteria.andPaperIdEqualTo(contest.getPaperId());
	           List<Simproblem> simproblems = simproblemDao.selectByExample(simproblemExample); //该试卷所有填空选择题
	           
	           if(simproblems.size()>0)
	           {
	        	   List<OneSimproblem> oneProblems = new ArrayList<OneSimproblem>();
	        	   for(Simproblem simproblem : simproblems)
	        	   {
	        		   OneSimproblem oneProblem =  new OneSimproblem(); 
	        		   oneProblem.setSimproblem(simproblem);  //题目
	        		   
	        		   simsolutionExample = new SimsolutionExample();
	        		   simsolutionCriteria.andSimproblemIdEqualTo(simproblem.getSimproblemId());
	        		   simsolutionCriteria.andContestStatusIdEqualTo(contestStatus.getContestStatusId());
	        		   simsolutions = simsolutionDao.selectByExample(simsolutionExample);
	        		   if(simsolutions.size()>0)
	        			   oneProblem.setSimsolution(simsolutions.get(0)); //作答情况
	        		   
	        		   OptionsExample optionExample = new OptionsExample();
	        		   OptionsExample.Criteria optionExampleCriteria = optionExample.createCriteria();
	        		   optionExampleCriteria.andSimproblemIdEqualTo(simproblem.getSimproblemId());
	        		   List<Options> options = optionDao.selectByExample(optionExample);
	        		   oneProblem.setOption(options);
	        		   AnswerExample answerExample = new AnswerExample();
	        		   AnswerExample.Criteria answerExampleCriteria = answerExample.createCriteria();
	        		   answerExampleCriteria.andSimproblemIdEqualTo(simproblem.getSimproblemId());
	        		   List<Answer> answers = answerDao.selectByExample(answerExample);
	        		   oneProblem.setAnswer(answers);
	        		   
	        		   oneProblems.add(oneProblem);
	        	   }
                   paper.setSimp(oneProblems);
	           }
	           
	           ProblemExample problemExample = new ProblemExample();
	           ProblemExample.Criteria problemExampleCriteria = problemExample.createCriteria();
	           problemExampleCriteria.andPaperIdEqualTo(contest.getPaperId());
	           List<ProblemWithBLOBs> problems  = problemDao.selectByExampleWithBLOBs(problemExample);
	           if(problems.size()>0)
	           {
	        	   List<OneProblem> oneProblems = new ArrayList<OneProblem>();
	        	   
	        	   for(ProblemWithBLOBs problem : problems)
	        	   {
	        		   OneProblem oneProblem = new OneProblem();
	        		   oneProblem.setProblem(problem);  //题目内容
	        		   
	        		   solutionExample = new SolutionExample();
	        		   solutionCriteria = solutionExample.createCriteria();
	        		   solutionCriteria.andContestStatusIdEqualTo(contestStatus.getContestStatusId());
	        		   solutionCriteria.andProblemIdEqualTo(problem.getProblemId());
	        		   List<SolutionWithBLOBs> solutionList = solutionDao.selectByExampleWithBLOBs(solutionExample);
	        		   if(solutions.size()>0)
	        			   oneProblem.setSolution(solutionList.get(0));  //作答情况
	        		   
	        		   oneProblems.add(oneProblem);
	        	   }
	        	   
	        	   paper.setProb(oneProblems);
	           }
	           
	            
	           oneContest.setPaper(paper);
			}
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) { 
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
