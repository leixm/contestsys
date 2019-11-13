package com.lxm;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.util.Date;
import java.util.List;

import com.code.model.Answer;
import com.code.model.Contestpaper;
import com.code.model.OnePaper;
import com.code.model.OneProblem;
import com.code.model.OneSimproblem;
import com.code.model.Options;
import com.code.model.ProblemWithBLOBs;
import com.code.model.Simproblem;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Calendar;

/**
 * @author Administrator
 *
 */
public class test {

    private static JSONObject createJSONObject(){   
        JSONObject jsonObject = new JSONObject();   
        jsonObject.put("username","huangwuyi");   
        jsonObject.put("sex", "��");   
        jsonObject.put("QQ", "999999999");   
        jsonObject.put("Min.score", new Integer(99));   
        jsonObject.put("nickname", "�����ľ�");   
        return jsonObject;   
    }
    
    private List<Date> GetPreWeek()
    {
    	List<Date> list = new ArrayList<Date>();
    	Date date = new Date();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    	while(cal.get(Calendar.DAY_OF_WEEK)!=1)
    		cal.add(Calendar.DATE, -1);
    	
    	for(int i=0;i<7;i++)
    	{
    		date = cal.getTime();
    		list.add(date);
    		cal.add(Calendar.DATE, -1);
    	}
    	Collections.reverse(list);
    	return list;
    }

    private void Query() throws SQLException, ClassNotFoundException
    {
    	//Date date = new java.sql.Time(new Date().getTime());
    	String sql = "insert into course(courseid,aa) values(74,''{0}'')";
     //   jdbcHelper jd = new jdbcHelper();
        java.util.Date date = new java.util.Date();          // ��ȡһ��Date����
        Timestamp ts = new Timestamp(date.getTime());
       // jd.Insert(MessageFormat.format(sql,ts.toString()));
    }
	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException {   

		
		List<OneSimproblem> oneSimproblems = new ArrayList<OneSimproblem>();
		
			Simproblem simproblem = new Simproblem();
		    simproblem.setContent("单选题");
		    simproblem.setPaperId(new Integer(1));
		    simproblem.setPos(new Integer(1));
		    simproblem.setType(new Integer(1));
		    simproblem.setSimproblemId(new Integer(1));
		    simproblem.setScore(new BigDecimal(5));

		    
		    List<Options> optionList = new ArrayList<Options>();
		    for(int i=1;i<=4;i++)
		    {
		    	Options option = new Options();
			    option.setOptionId(new Integer(i));
			    option.setPos(i);
			    option.setSimproblemId(new Integer(1));
			    option.setContent("选项" + i);
			    optionList.add(option);
		    }
		    List<Answer> answerList = new ArrayList<Answer>();
		    Answer answer = new Answer();
		    answer.setAnswerId(new Integer(1));
		    answer.setContent("选项1");
		    answer.setSimproblemId(new Integer(1));
		    answerList.add(answer);
		    
		    OneSimproblem onesimproblem = new OneSimproblem();
		    onesimproblem.setOption(optionList);
		    onesimproblem.setSimproblem(simproblem);  //单选题添加完毕
		    onesimproblem.setAnswer(answerList);
		    oneSimproblems.add(onesimproblem);
	    
	    
	    simproblem.setContent("多选题");
	    simproblem.setPos(new Integer(2));
	    simproblem.setType(new Integer(2));
	    
	    optionList.clear();
	    for(int i=5;i<=8;i++)
	    {
	    	Options option = new Options();
		    option.setOptionId(new Integer(i));
		    option.setPos(i);
		    option.setSimproblemId(new Integer(1));
		    option.setContent("选项" + i);
		    optionList.add(option);
	    }
	    answerList.clear();
	    answer.setAnswerId(new Integer(2));
	    answer.setContent("选项1");
	    answer.setSimproblemId(new Integer(2));
	    answerList.add(answer);
	    
	    onesimproblem.setOption(optionList);
	    onesimproblem.setSimproblem(simproblem);  //多选题添加完毕
	    onesimproblem.setAnswer(answerList);
	    oneSimproblems.add(onesimproblem);
        
        
	    
        simproblem.setContent("判断题");
	    simproblem.setPos(new Integer(3));
	    simproblem.setType(new Integer(3));
	    
	    optionList.clear();
	    for(int i=9;i<=10;i++)
	    {
	    	Options option = new Options();
		    option.setOptionId(new Integer(i));
		    option.setPos(i);
		    option.setSimproblemId(new Integer(1));
		    option.setContent("选项" + i);
		    optionList.add(option);
	    }
	    answerList.clear();
	    answer.setAnswerId(new Integer(3));
	    answer.setContent("选项1");
	    answer.setSimproblemId(new Integer(3));
	    answerList.add(answer);
	    
	    onesimproblem.setOption(optionList);
	    onesimproblem.setSimproblem(simproblem);  //判断题添加完毕
	    onesimproblem.setAnswer(answerList);
	    oneSimproblems.add(onesimproblem);
        
        
        
        simproblem.setContent("填空题,问题1:___问题2___");
	    simproblem.setPos(new Integer(4));
	    simproblem.setType(new Integer(4));
	    simproblem.setBlanks(2);
	    
	    answerList.clear();
	    answer.setAnswerId(new Integer(4));
	    answer.setContent("答案1");
	    answer.setSimproblemId(new Integer(4));
	    answer.setPos(new Integer(1));
	    answerList.add(answer);
	    
	    answer.setAnswerId(new Integer(5));
	    answer.setContent("答案2");
	    answer.setSimproblemId(new Integer(4));
	    answer.setPos(new Integer(2));
	    answerList.add(answer);
	    
	    onesimproblem.setOption(optionList);
	    onesimproblem.setSimproblem(simproblem);  //填空题添加完毕
	    onesimproblem.setAnswer(answerList);
	    oneSimproblems.add(onesimproblem);
        
        OnePaper onepaper = new OnePaper();
        onepaper.setSimp(oneSimproblems);
        
        
        OneProblem oneproblem = new OneProblem();
        ProblemWithBLOBs problem = new ProblemWithBLOBs();
        problem.setProblemId(new Integer(1));
        problem.setDescription("madoka小朋友最近在玩一堆积木。这堆积木都是圆柱体。现在madoka想把M个积木堆成M层体积为N的物体。为了让堆出来的这货看上去比较和谐，madoka规定堆在较下面的圆柱体的底面半径和高度都必须比上面的大。现在madoka想把堆成物体的外表面涂色（不包括最下面一层底面），madoka想知道怎么来堆积木，才能使总涂色面积S最小假设所有积木的底面半径和高度均为正整数，且每种积木madoka都有无限个。");
        problem.setInput("有多组数据。每组数据包括两个正整数n,m(n<=10000,m<=20)，m表示堆m层，n表示要求的体积为nπ（π是圆周率）。");
        problem.setOutput("对每组数据输出一个正整数s，表示最小面积为sπ（π是圆周率）。如果无解输出0");
        problem.setSampleInput("100 2");
        problem.setSampleOutput("68");
        problem.setTimeLimit(new Integer(2));
        problem.setMemoryLimit(new Integer(64));
        problem.setTitle("积木");
        problem.setHint("动态规划");
        problem.setScore(new BigDecimal(5));
        problem.setPos(new Integer(5));
        List<OneProblem> oneproblems = new ArrayList<OneProblem>();
        oneproblems.add(oneproblem);
        onepaper.setProb(oneproblems);
        
        Contestpaper contestPaper = new Contestpaper();
        contestPaper.setTitle("期末测试");
        contestPaper.setTeacher("4562224314");
        contestPaper.setPaperId(new Integer(1));
        contestPaper.setDate(new Date());
        
        onepaper.setContestpaper(contestPaper);
        System.out.println(JSONObject.fromObject(onepaper).toString());
	}  

}


