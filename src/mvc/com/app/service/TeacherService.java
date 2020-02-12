/** 
 * @author zzs
 * @create_date 2019.8.1
 * @description 教师相关业务服务接口
 **/
package com.app.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.code.model.*;
import com.code.model.Class;
import org.springframework.web.servlet.ModelAndView;

public interface TeacherService {
	/**
	 * @author zzs
	 * @param contest:Contest实体
	 * @param class:班级名称
	 * @description 添加一场考试
	 * @return 状态响应
	 */
	Response addContest(Contest contest,String className);
	
	/**
	 * @author zzs
	 * @param user:用户实例
	 * @description 查询老师出的所有卷子 
	 * @return 状态响应
	 */
	Response selAllpaper(User user);
	
	/**
	 * @author zzs
	 * @param newpaper:OnePaper实例
	 * @param user:用户实例
	 * @param oneSimps:List<OneSimproblem>实例
	 * @description 添加新的试卷
	 * @return 状态响应
	 */
	Response addNewpaper(OnePaper newpaper,User user,List<OneSimproblem> oneSimps,String basePath);
	
	/**
	 * @author zzs
	 * @param user:用户实例
	 * @description 教师查询所有考试Status
	 * @return 状态响应
	 */
	Response selContestStatus(User user);
	
	/**
	 * 查询属于某个教师的所有contest对象
	 * @param 教师
	 * @return	属于同个教师的所有contest
	 */
	Response selAllContest(User user);
	
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
	Response selClassHighestScore(User user,Class cla,Contest contest);
	
	/**
	 * 查询计算所有班级某场考试的最低分
	 * @param cla 具体班级
	 * @param contest 具体考试
	 * @return
	 */
	Response selClasslowestScore(User user,Class cla,Contest contest);
	
	
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
	List<Map<String,Object>>  selGradeScore(User user,Contest contest);
	
	/**
	 * 根据搜索条件模糊查询出学生成绩表
	 * @param 1、班级名称
	 * @param 2、学生学号
	 * @param 3、学生名字
	 * @param 4、考试名称
	 * @return 成绩实体Map对象集合
	 */
	public List<Map<String,Object>> selStuScore(String className,String stuId,String stuName,String contestName,int simCourseId,List statusList,String userId,String pageSize,String pageNumber);
	

	/**
	 * 查询出所有学生成绩表
	 * @return 成绩实体Map对象集合
	 */
	List<Map<String,Object>> selAllStuScore();
	
	/**
	 * 根据搜索条件模糊查询出学生成绩表
	 * @param cstatusid: 所更新成绩对应的表的主键id
	 * @param score: 用户重新更新的成绩
	 * @return 更新操作返回的状态
	 */
	int updateScore(String cStatusId,String score);
	
	/**
	 * 查询所有的班级对象
	 * @return 所有的班级对象
	 */
	List<Map<String,Object>> selAllClassObj(String userId);
	
	/**
	 * 查询所有的考试对象
	 * @return 所有的考试对象
	 */
	List<Map<String,Object>> selAllContestObj();
	
	/**
	 * 查询所有的班级的所有考试的平均分
	 * @return 所有对象
	 */
	List<Map<String,Object>> selClassContestAVG(List className,List contestName);
	
	/**
	 * 查询通用题库列表
	 * @return 所有对象
	 */
	List<Map<String,Object>> selSimproblemList(int simCourseId,String simPaperTitle,int simType,String pageSize,String pageNumberr);
	
	/**
	 * 删除单条simproblem
	 * @param simId 
	 * @return
	 */
	int delSimproblemById(int simId);
	
	/**
	 * 删除多条simproblem
	 * @param ids 
	 * @return
	 */
	int delBatchSimproblemByIds(List<String> ids);

	/**
	 * 根据paperId查询contestPaper对象
	 * @return
	 */
	Contestpaper selOneContestPaperById(String paperId);

	/**
	 *根据Id查询simproblem对象
	 * @return
	 */
	Simproblem selSimproblemById(String simId);

	/**
	 * 获取单选题返回的页面对象
	 * @return
	 */
	ModelAndView getOneChoiceMav(String simId);

	/**
	 * 获取填空判断题返回的页面对象
	 * @return
	 */
	ModelAndView getFillBlankAndJudgementMav (String simId);

	/**
	 * 获取简答题返回的页面对象
	 * @return
	 */
	ModelAndView getShortAnswerMav (String simId);

	/**
	 * 更新选择题内容和答案等信息
	 * @param simId
	 * @param simScore
	 * @param simContent
	 * @param optionList
	 * @param answerList
	 * @return	返回的是结果信息 ""代表成功， 有内容代表失败
	 */
	String updateChoiceQuestion(int simId,BigDecimal simScore,String simContent,List<String> optionList,List<String> answerList);

	/**
	 * 更新填空、判断题内容和答案等信息
	 * @return  ""代表成功， 有内容代表失败
	 */
	String updateFillBlankAndJudgement(int simId,BigDecimal simScore,String simContent,List<String> answerList);

	/**
	 * 更新简答题内容和答案等信息
	 * @return  ""代表成功， 有内容代表失败
	 */
	String updateShortAnswer(int simId,BigDecimal simScore,String simContent);


	/**
	 * 复用通用题库题目等信息
	 * @param request
	 * @return		1-成功  -1 失败
	 */
	int reuseSimproblem(String simId, String paperId);


	/**
	 * 查询批改主观题页面显示的相关信息
	 * @param type
	 * @param cStatusId
	 * @return
	 */
	List<Map<String,Object>> selSolutionSimproblemByTypeAndcStatusId(int type,int cStatusId);

	/**
	 * 批改题目  分数追加
	 * @return 1 成功
	 */
	int correctSimpleProblem(int cStatusId,int cStatus,Map<String,Object> realScoreMap,double oldScoreSum,double realScoreSum);
}
