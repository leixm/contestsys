/** 
 * @author zzs
 * @create_date 2019.8.1
 * @description 教师相关业务服务接口
 **/
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
	 * @author zzs
	 * @param contest:Contest实体
	 * @param class:班级名称
	 * @description 添加一场考试
	 * @return 状态响应
	 */
	public Response addContest(Contest contest,String className);
	
	/**
	 * @author zzs
	 * @param user:用户实例
	 * @description 查询老师出的所有卷子 
	 * @return 状态响应
	 */
	public Response selAllpaper(User user);
	
	/**
	 * @author zzs
	 * @param newpaper:OnePaper实例
	 * @param user:用户实例
	 * @param oneSimps:List<OneSimproblem>实例
	 * @description 添加新的试卷
	 * @return 状态响应
	 */
	public Response addNewpaper(OnePaper newpaper,User user,List<OneSimproblem> oneSimps,String basePath);
	
	/**
	 * @author zzs
	 * @param user:用户实例
	 * @description 教师查询所有考试Status
	 * @return 状态响应
	 */
	public Response selContestStatus(User user);
	
	/**
	 * 查询属于某个教师的所有contest对象
	 * @param 教师
	 * @return	属于同个教师的所有contest
	 */
	public Response selAllContest(User user);
	
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
	 * @return 成绩实体Map对象集合
	 */
	public List<Map<String,Object>>  selGradeScore(User user,Contest contest);
	
	/**
	 * 根据搜索条件模糊查询出学生成绩表
	 * @param 1、班级名称
	 * @param 2、学生学号
	 * @param 3、学生名字
	 * @param 4、考试名称
	 * @return 成绩实体Map对象集合
	 */
	public List<Map<String,Object>> selStuScore(String className,String stuId,String stuName,String contestName,String pageSize,String pageNumber); 
	
	/**
	 * 根据搜索条件模糊查询出学生成绩表
	 * @param cstatusid: 所更新成绩对应的表的主键id
	 * @param score: 用户重新更新的成绩
	 * @return 更新操作返回的状态
	 */
	public int updateScore(String cStatusId,String score); 
	
	/**
	 * 查询所有的班级对象
	 * @return 所有的班级对象
	 */
	public List<Map<String,Object>> selAllClassObj(); 
	
	/**
	 * 查询所有的考试对象
	 * @return 所有的考试对象
	 */
	public List<Map<String,Object>> selAllContestObj(); 
	
	/**
	 * 查询所有的班级的所有考试的平均分
	 * @return 所有对象
	 */
	public List<Map<String,Object>> selClassContestAVG(List className,List contestName); 
}
