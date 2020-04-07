/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 学生管理服务-接口
 * */
package com.app.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.code.model.Contest;
import com.code.model.ContestStatus;
import com.code.model.Contestpaper;
import com.code.model.OneProblem;
import com.code.model.OneSimproblem;
import com.code.model.Simsolution;
import com.code.model.SimsolutionExample;
import com.code.model.Solution;
import com.code.model.SolutionExample;
import com.code.model.SolutionWithBLOBs;

public interface StudentService {
	
	Contestpaper selectContestpaperByPrimaryKey(Integer paperId);
	
	Contest selectContestByPrimaryKey(Integer contestId);
	
	ContestStatus selectContestStatusByPrimaryKey(Integer contestStatusId);
	
	List<OneProblem> getProblemAndSolutionByPaperId(Integer paperId);
	
	List<OneSimproblem> getSimproblemAndOptionByPaperId(Integer paperId);
	
	int insert(SolutionWithBLOBs record);
	
	int insert(Simsolution record);

	int updateContestStatus(ContestStatus record);

	/**
	 * 根据搜索条件模糊查询出学生成绩表
	 * @param 1、班级名称
	 * @param 2、学生学号
	 * @param 3、学生名字
	 * @param 4、考试名称
	 * @return 成绩实体Map对象集合
	 */
	 List<Map<String,Object>> selOneStuScore(String stuId,String contestName,String pageSize,String pageNumber);
	
	 /**
	  * 根据条件更新学生用户的个人信息
	  * @param stuId	检索用的学生id
	  * @param stuName
	  * @param stuEmail
	  * @param newPwd 新密码
	  * @return 更新是否成功
	  */
	 int updateStuInfo(String stuId,String stuName,String stuEmail,String newPwd);
	
	/**
	 * 根据条件查询学生个人的考试列表
	 * @param stuId	用户Id	
	 * @param keyword	搜索条件
	 * @return
	 */
	 List<Map<String,Object>> selOwnContest(String stuId, String keyword, String pageSize, String pageNumber);
	
	/**
	 * 查询某个学生的个人成绩和班级考试的平均分
	 * @param stuId	用户Id	
	 * @param contestName 考试名字
	 * @return 所有对象
	 */
	 List<Map<String,Object>> selOneStuScoreAVG(String stuId,List contestName);
	
	/**
	 * 查询某个学生的所有考试名称
	 * @param stuId	用户Id	
	 * @return 所有对象
	 */
	 List<Map<String,Object>> selStuContestTitle(String stuId);
	
}
