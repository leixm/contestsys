/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 考试页面生成工具类
 * */
package com.app.tools;

import com.code.model.OnePaper;
import com.code.model.OneProblem;
import com.code.model.OneSimproblem;
import com.code.model.ProblemWithBLOBs;

public class HTMLGenerator {

	String Create_div_question(String content,int num)
	{
		String s = "<div class=\"div_question\" id=\"div" + num +"\">" + content + "<div class=\"errorMessage\"></div></div>";
		return s;
	}
	String Create_div_title_question_all(String content,int num)
	{
		String s = "<div class=\"div_title_question_all\"><div class=\"div_topic_question\">" + num + ".</div>" + content + "<div style=\"clear:both;\"></div></div>";
		return s;
	}
	String Create_div_title_question(String content,int num)
	{
		String s = "<div id=\"divTitle" + num +"\" class=\"div_title_question\">" + content + "<span class=\"req\">*</span></div>";
		return s;
	}
	String Create_div_table_radio_question(String content,int num)
	{
		String s = "<div class=\"div_table_radio_question\" id=\"divquestion" + num + "\"><div class=\"div_table_clear_top\"></div>" + content + "<div class=\"div_table_clear_bottom\"></div></div>";
		return s;
	}
	String Create_inputtext(int num)
	{
		String s = "<textarea title=\"\" style=\"overflow: auto;width:62%;height:66px;\" class=\"inputtext\" value=\"\" id=\"q" + num + "\" name=\"q" + num + "\"></textarea>";
		return s;
	}
	String Create_Button(int num)
	{
		String s = "<input type=\"button\" value=\"编辑代码\" class=\"submitbutton\" style=\"width: 20%;\" onclick=\"ProblemChange("+ num + ")\" data-toggle=\"modal\" data-target=\"#myModal\">";
		return s; 
	}
	String Create_ulradiocheck(String content)
	{
		String s = "<ul class=\"ulradiocheck\">" + content + "<div style=\"clear:both;\"></div></ul>";
		return s;
	}
	String Create_jqRadio(String content,int num,int count)
	{
		String id = "q" + num + "_" + count;
		String s = "<li style=\"width: 99%;\"><a href=\"javascript:\" class=\"jqRadio\" rel=\"" + id + "\"></a><input style=\"display:none;\" type=\"radio\" id=\""+ id +"\" value=\"" + content +"\" /><label id=\"" + id + "_text\" for=\"" + id + "\">" + content + "</label></li>";
		return s;
	}
	String Create_clear_div(){
		return "<div style=\"clear:both;\"></div>";
	}
	String Create_jqCheckbox(String content,int num,int count)
	{
		String id = "q" + num + "_" + count;
		String s = "<li style=\"width:99%;\"><a href=\"javascript:\" class=\"jqCheckbox\" rel=\"" + id +"\"></a><input style=\"display:none;\" id=\"" + id + "\" type=\"checkbox\"  value=\"" + content + "\" /><label id=\"" + id + "_text\">" + content + "</label></li>";
	    return s;
	}
	String CreateProblemContent(ProblemWithBLOBs obj)
	{
		String s = "题目标题：" + obj.getTitle() +"<br>题目描述："+ obj.getDescription() +"<br>时间限制:" + obj.getTimeLimit() +" sec<br>内存限制:" + obj.getMemoryLimit() + " MB<br>数据输入:<br>" + obj.getSampleInput() + "<br>数据输出<br>" + obj.getSampleOutput() +"<br>提示:<br>" + obj.getHint();
		return s;
	}
	String Create_underline(String content,int num,int count)
	{
		return content + "：<input style=\"width:63px;\" type=\"text\" class=\"underline\" id=\"q" + num + "_" + count + "\" />";
	}
	String GenerateAllProblem(OnePaper obj){
		int totalCount = obj.getSimp().size() + obj.getProb().size();
        String content = "";
		//var problemDiv = document.getElementById("fieldset1");
		for(int index=1;index<=totalCount;index++)
		{
			boolean flag = false;
			int type = 0;
			for(int i=0;i<obj.getSimp().size();i++)
			if(obj.getSimp().get(i).getSimproblem().getPos()==index){
				flag = true;
				type = obj.getSimp().get(i).getSimproblem().getType().intValue();
				content += GenerateSimProblem(obj.getSimp().get(i),type,index);
				break;
			}
			
			if(!flag)  
			for(int i=0;i<obj.getProb().size();i++)
			{
				if(obj.getProb().get(i).getProblem().getPos().intValue()==index)
				{
					content += GenerateProblem(obj.getProb().get(i),index);
					break; 
				}
			}
			
		}
		return content;
	}
	String GenerateProblem(OneProblem obj,int index)
	{
		String s = Create_Button(index);
		
		s = Create_ulradiocheck(s);
		s = s + Create_clear_div();
		s = Create_div_table_radio_question(s,index);
		String head = Create_div_title_question(CreateProblemContent(obj.getProblem()),index);
		head = Create_div_title_question_all(head,index);
		s = Create_div_question(head + s,index);
		return s;
		
	}
	String GenerateSimProblem(OneSimproblem obj,int type,int index)
	{
		String s = ""; 
		if(type==1){  //单选
			for(int i=1;i<=obj.getOption().size();i++)  //生成选项div
				s += Create_jqRadio(obj.getOption().get(i-1).getContent(),index,i);
			
			s = Create_ulradiocheck(s);
			s = s + Create_clear_div(); 
			s = Create_div_table_radio_question(s,index);
			String head = Create_div_title_question(obj.getSimproblem().getContent(),index);
			head = Create_div_title_question_all(head,index);
			s = Create_div_question(head + s,index);

		}
		else if(type==2){ //多选
			for(int i=1;i<=obj.getOption().size();i++)  //生成选项div
				s += Create_jqCheckbox(obj.getOption().get(i-1).getContent(),index,i);
			
			s = Create_ulradiocheck(s);
			s = s + Create_clear_div();
			s = Create_div_table_radio_question(s,index);
			String head = Create_div_title_question(obj.getSimproblem().getContent(),index);
			head = Create_div_title_question_all(head,index);
			s = Create_div_question(head + s,index);
		
		}
		else if(type==3){ //判断
			s += Create_jqRadio("正确",index,1);
			s += Create_jqRadio("错误",index,2);
			
			s = Create_ulradiocheck(s);
			s = s + Create_clear_div();
			s = Create_div_table_radio_question(s,index);
			String head = Create_div_title_question(obj.getSimproblem().getContent(),index);
			head = Create_div_title_question_all(head,index);
			s = Create_div_question(head + s,index);
			
		}
		else if(type==4){ //填空

			String content = obj.getSimproblem().getContent();
			String[] str = content.split("___");
			s = "";
			for(int i=0;i<str.length-1;i++){
				s += Create_underline(str[i],index,i+1);
			}
			s += str[str.length-1];

			s = Create_div_title_question(s,index);  

			s = Create_div_title_question_all(s,index); 

			
			String s1 = "";
			s1 = Create_ulradiocheck(s1);
			s1 = s1 + Create_clear_div();
			s1 = Create_div_table_radio_question(s1,index);
			s = Create_div_question(s + s1,index);
		
		}
		else if(type==5){  //简答
		s = Create_inputtext(index);
		
		s = Create_div_table_radio_question(s,index);
		String head = Create_div_title_question(obj.getSimproblem().getContent(),index);
		head = Create_div_title_question_all(head,index);
		s = Create_div_question(head + s,index);
	
		}
		return s;
		
		
		
	}
	
}
