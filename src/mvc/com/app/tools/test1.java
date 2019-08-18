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

import com.app.dao.UserMapper;
import com.app.service.impl.UserService;
import com.code.model.Answer;
import com.code.model.Contestpaper;
import com.code.model.OnePaper;
import com.code.model.OneProblem;
import com.code.model.OneSimproblem;
import com.code.model.Options;
import com.code.model.ProblemWithBLOBs;
import com.code.model.Simproblem;
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
	private UserService userService;

public void test() throws IOException
{
	
	
    

	List<OneSimproblem> oneSimproblems = new ArrayList<OneSimproblem>();
	
	List<Answer> answerList = new ArrayList<Answer>();
	Simproblem simproblem = new Simproblem();
	List<Options> optionList = new ArrayList<Options>();
	Answer answer = new Answer();
	    simproblem.setContent("单选题");
	    simproblem.setPaperId(new Integer(1));
	    simproblem.setPos(new Integer(1));
	    simproblem.setType(new Integer(1));
	    simproblem.setSimproblemId(new Integer(1));
	    simproblem.setScore(new BigDecimal(5));

	    for(int i=1;i<=4;i++)
	    {
	    	Options option = new Options();
		    option.setOptionId(new Integer(i));
		    option.setPos(i);
		    option.setSimproblemId(new Integer(1));
		    option.setContent("选项" + i);
		    optionList.add(option);
	    }
	    

	    answer.setAnswerId(new Integer(1));
	    answer.setContent("选项1");
	    answer.setSimproblemId(new Integer(1));
	    answerList.add(answer);
	    
	    OneSimproblem onesimproblem = new OneSimproblem();
	    onesimproblem.setOption(optionList);
	    onesimproblem.setSimproblem(simproblem);  //单选题添加完毕
	    onesimproblem.setAnswer(answerList);
	    oneSimproblems.add(onesimproblem);
    
/*	simproblem = new Simproblem();
	simproblem.setScore(new BigDecimal(5));
	simproblem.setSimproblemId(new Integer(2));
	simproblem.setPaperId(new Integer(1));
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
    oneSimproblems.add(onesimproblem);*/
    
    
   /*  simproblem = new Simproblem();
	simproblem.setScore(new BigDecimal(5));
	simproblem.setSimproblemId(new Integer(3));
	simproblem.setPaperId(new Integer(1));
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
    oneSimproblems.add(onesimproblem);*/
    
    
 /*     simproblem = new Simproblem();
	simproblem.setScore(new BigDecimal(5));
	simproblem.setSimproblemId(new Integer(4));
	simproblem.setPaperId(new Integer(1));
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
    */
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
    problem.setPos(new Integer(2));
    oneproblem.setProblem(problem);
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

/*    UserExample userExample = new UserExample();
    UserExample.Criteria criteria = userExample.createCriteria();
    criteria.andClassIdEqualTo(new Integer(1));
    UserMapper userDao = new UserMapper();
    User user = new User();
    */
    
    
 //   FileHelper fh = new FileHelper();
//	String content = fh.getFileContent("G:\\测试数据\\text.json");

//	JSONObject obj = JSONObject.fromObject(content);
//    OnePaper paper = (OnePaper) JSONObject.toBean(obj, OnePaper.class); 
    HTMLGenerator ge = new HTMLGenerator();

    String paperContent = ge.GenerateAllProblem(onepaper);
    System.out.println(paperContent);
    
    
    
}



@Test
public void tt() throws UnsupportedEncodingException, MessagingException, GeneralSecurityException
{
	int state = userService.LoginByUserName("1614080902217", "771226822");
	System.out.println(state);
}





}
