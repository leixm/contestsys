/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 学生管理服务
 * */
package com.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.ContestMapper;
import com.app.dao.ContestStatusMapper;
import com.app.dao.ContestpaperMapper;
import com.app.dao.ProblemMapper;
import com.app.dao.SimproblemMapper;
import com.app.dao.SimsolutionMapper;
import com.app.dao.SolutionMapper;
import com.app.dao.UserMapper;
import com.code.model.Contest;
import com.code.model.ContestStatus;
import com.code.model.Contestpaper;
import com.code.model.OneProblem;
import com.code.model.OneSimproblem;
import com.code.model.Simsolution;
import com.code.model.SolutionWithBLOBs;
import com.github.pagehelper.PageHelper;


@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
    private ContestpaperMapper contestpaperDao;
	
	@Autowired
    private ContestMapper ContestDao;
	
	@Autowired
	private ContestStatusMapper contestStatusDao;
	
	@Autowired
    private ProblemMapper problemDao;
	
	@Autowired
    private SimproblemMapper simproblemDao;
	
	@Autowired
    private SimsolutionMapper simsolutionDao;
	
	@Autowired
    private SolutionMapper solutionDao;
	
	@Autowired
	private UserMapper userDao;

	@Override
	public Contestpaper selectContestpaperByPrimaryKey(Integer paperId) {
		return contestpaperDao.selectByPrimaryKey(paperId);
	}

	@Override
	public Contest selectContestByPrimaryKey(Integer contestId) {
		return ContestDao.selectByPrimaryKey(contestId) ;
	}

	@Override
	public ContestStatus selectContestStatusByPrimaryKey(Integer contestStatusId) {
		return contestStatusDao.selectByPrimaryKey(contestStatusId);
	}

	@Override
	public List<OneProblem> getProblemAndSolutionByPaperId(Integer paperId) {
		return problemDao.getProblemAndSolutionByPaperId(paperId);
	}

	@Override
	public List<OneSimproblem> getSimproblemAndOptionByPaperId(Integer paperId) {
		return simproblemDao.getSimproblemAndOptionByPaperId(paperId);
	}

	@Override
	public int insert(SolutionWithBLOBs record) {
		return solutionDao.insert(record);
	}

	@Override
	public int insert(Simsolution record) {
		return simsolutionDao.insert(record);
	}

	@Override
	public int updateContestStatus(ContestStatus record){
		return contestStatusDao.updateByPrimaryKeySelective(record);
	}
	/**
	 * 根据搜索条件模糊查询出学生成绩表
	 * @param 1、班级名称
	 * @param 2、学生学号
	 * @param 3、学生名字
	 * @param 4、考试名称
	 * @return 成绩实体Map对象集合
	 */
	@Override
	public List<Map<String, Object>> selOneStuScore(String className, String stuId, String stuName, String contestName,String pageSize,String pageNumber) {
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(); //返回结果的容器
		//分页所需相关参数的计算
		if(pageSize!=null&&pageNumber!=null) {
			int pageSizeInt = Integer.parseInt(pageSize);
			int pageNumberInt = Integer.parseInt(pageNumber);
			PageHelper.startPage(pageNumberInt,pageSizeInt,true);//使用后数据库语句自动转为分页查询语句进行数据查询
		}
		resultList = contestStatusDao.selStuScoreBykeyword(className, stuId, stuName, contestName); //根据参数查询学生成绩等字段，如果参数全部为空自动查询全部学生的相关成绩
		return resultList;
	}
	
	 /**
	  * 根据条件更新学生用户的个人信息
	  * @param stuId	检索用的学生id
	  * @param stuName
	  * @param stuEmail
	  * @param newPwd 新密码
	  * @return 更新是否成功 
	  */
	@Override
	public int updateStuInfo(String stuId, String stuName, String stuEmail, String newPwd) {
		System.out.println("stuInfo----"+stuId+"---"+stuName+"---"+stuEmail+"---"+newPwd);
		return userDao.updateStuInfoByStuId(stuId, stuName, stuEmail, newPwd);
	}
	
	/**
	 * 根据条件查询学生个人的考试列表
	 * @param stuId	用户Id	
	 * @param keyword	搜索条件
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selOwnContest(String stuId, String keyword,String pageSize,String pageNumber) {
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(); //返回结果的容器
		//分页所需相关参数的计算
		if(pageSize!=null&&pageNumber!=null) {
			int pageSizeInt = Integer.parseInt(pageSize);
			int pageNumberInt = Integer.parseInt(pageNumber);
			PageHelper.startPage(pageNumberInt,pageSizeInt,true);//使用后数据库语句自动转为分页查询语句进行数据查询
		}
		resultList = contestStatusDao.selOwnContestBykeyword(stuId, keyword); //根据参数查询学生考试等字段，如果参数全部为空自动查询全部
		return resultList;
	}


}
