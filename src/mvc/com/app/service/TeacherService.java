package com.app.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.code.model.Class;
import com.code.model.Contest;
import com.code.model.OnePaper;
import com.code.model.OneSimproblem;
import com.code.model.Response;
import com.code.model.User;

public interface TeacherService {
	
	/**
	 * 查询属于某个教师的所有contest对象
	 * @param 教师
	 * @return	属于同个教师的所有contest
	 */
	public Response selAllContest(User user);
	
	/**
	 * 查询老师出的所有卷子
	 * @param user
	 * @return 属于同个userId的所有的卷子
	 */
	public Response selAllpaper(User user);
	
	/**
	 * 添加新的试卷
	 * @param newpaper 从Response获取到的OnePaper 
	 * @return 1添加成功     0添加失败
	 */
	public Response addNewpaper(OnePaper newpaper,User user,List<OneSimproblem> oneSimps);
	
	/**
	 * 教师查询所有考试Status
	 * @return Response
	 * 
	 */
	public Response selContestStatus(User user);
	
	/**
	 * 查询计算所有班级某场考试的平均分
	 * @param cla 具体班级
	 * @param contest 具体考试
	 * @return
	 */
	public Response selClassAverageScore(User user,Class cla,Contest contest);
	
	/**
	 * 查询计算所有班级某场考试的最高分分
	 * @param cla 具体班级
	 * @param contest 具体考试
	 * @return
	 */
	public Response selClassHighestScore(User user,Class cla,Contest contest);
	
	/**
	 * 查询计算所有班级某场考试的最低分
	 * @param cla 具体班级
	 * @param contest 具体考试
	 * @return
	 */
	public Response selClasslowestScore(User user,Class cla,Contest contest);
	
	/**
	 * 添加一门新的考试  
	 * @return 0添加考试失败  1添加考试成功  -1权限不足
	 */
	public Response addContest(Contest contest,String className);
	
	/**
	 * 导出年级成绩表
	 * @param user	具体老师
	 * @param contest 具体考试
	 * @return 导出结果
	 */
	public Response exportGradeScoreExcel(User user,Contest contest);
	
	/**
	 * 查询全级学生分数
	 * @param user
	 * @param contest
	 * @return
	 */
	public List<Map<String,Object>>  selGradeScore(User user,Contest contest);
	
}
