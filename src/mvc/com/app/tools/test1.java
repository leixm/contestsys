package com.app.tools;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import junit.framework.TestCase;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.platform.win32.WinBase.SYSTEM_INFO.PI;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.MSG;
import com.sun.jna.platform.win32.WinUser.WINDOWINFO;
import com.sun.jna.ptr.IntByReference;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.awt.Color; 
import java.io.File; 
import java.io.FileNotFoundException; 
import java.io.FileOutputStream; 
import java.io.IOException; 
import java.text.DecimalFormat; 
import java.text.NumberFormat; 
import java.util.ArrayList; 
import java.util.Date;

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
import com.app.service.impl.UserService;
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
import com.code.model.UserExample;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;




@RunWith(SpringJUnit4ClassRunner.class)	// 不能是PowerMock等别的class，否则无法识别spring的配置文件
@ContextConfiguration("file:resource/spring/applicationContext.xml")	// 读取spring配置文件
public class test1 {

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

@Test
public void test() throws IOException
{

		
		SimsolutionExample simsolutionExample = new SimsolutionExample(); 
		SimsolutionExample.Criteria simsolutionCriteria = simsolutionExample.createCriteria();
		simsolutionCriteria.andStatusEqualTo(new Integer(0));
		List<Simsolution> simsolutions = simsolutionDao.selectByExampleWithBLOBs(simsolutionExample);  //未批改的题目
		if(simsolutions.size()>0)
		{
			for(Simsolution simsolution : simsolutions)
			{
				Simproblem simproblem = simproblemDao.selectByPrimaryKey(simsolution.getSimproblemId());  //找对应的题目
				
				if(simproblem==null) continue;  //找不到题目
				AnswerExample answerExample = new AnswerExample();
				AnswerExample.Criteria answerCriteria = answerExample.createCriteria();
				answerCriteria.andSimproblemIdEqualTo(simproblem.getSimproblemId());  //找到这题的答案
				List<Answer> answers = answerDao.selectByExampleWithBLOBs(answerExample);
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
						String answerString =  simsolution.getAnswer();
						String[] arr = answerString.split("§§§");
					    boolean flag = true;
					    for(int i=0;i<answers.size();i++)  //必须选对所有选项
					    {  
					    	if(!arr[i].equals(answers.get(i).getContent())){
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
						String answerString =  simsolution.getAnswer();
						String[] arr = answerString.split("§§§");
						double count = 0;
						for(int i=0;i<simproblem.getBlanks().intValue();i++)  //第i+1个空
							if(answers.get(i).getContent().equals(arr[i])) count++;
							
						
						
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
		List<SolutionWithBLOBs> solutions = solutionDao.selectByExampleWithBLOBs(solutionExample);
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
            simsolutions = simsolutionDao.selectByExampleWithBLOBs(simsolutionExample);	
            if(simsolutions.size()>0)  //存在未批改
            	continue;
            
            solutionExample = new SolutionExample();
            solutionCriteria = solutionExample.createCriteria();
            solutionCriteria.andContestStatusIdEqualTo(contestStatus.getContestStatusId());
            solutionCriteria.andStatusEqualTo(new Integer(0));
            solutions = solutionDao.selectByExampleWithBLOBs(solutionExample);
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
           List<Simproblem> simproblems = simproblemDao.selectByExampleWithBLOBs(simproblemExample); //该试卷所有填空选择题
           
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
        		   simsolutions = simsolutionDao.selectByExampleWithBLOBs(simsolutionExample);
        		   if(simsolutions.size()>0)
        			   oneProblem.setSimsolution(simsolutions.get(0)); //作答情况
        		   
        		   OptionsExample optionExample = new OptionsExample();
        		   OptionsExample.Criteria optionExampleCriteria = optionExample.createCriteria();
        		   optionExampleCriteria.andSimproblemIdEqualTo(simproblem.getSimproblemId());
        		   List<Options> options = optionDao.selectByExampleWithBLOBs(optionExample);
        		   oneProblem.setOption(options);
        		   AnswerExample answerExample = new AnswerExample();
        		   AnswerExample.Criteria answerExampleCriteria = answerExample.createCriteria();
        		   answerExampleCriteria.andSimproblemIdEqualTo(simproblem.getSimproblemId());
        		   List<Answer> answers = answerDao.selectByExampleWithBLOBs(answerExample);
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



public void tt()
{
	List<Simsolution> list = simsolutionDao.selectByExampleWithBLOBs(new SimsolutionExample());
	System.out.println(JSONArray.fromObject(list).toString());
}





}
