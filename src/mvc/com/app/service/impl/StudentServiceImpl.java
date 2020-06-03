/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 学生管理服务
 * */
package com.app.service.impl;

import com.app.dao.*;
import com.app.service.StudentService;
import com.code.model.*;
import com.code.model.UserExample.Criteria;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service
public class StudentServiceImpl implements StudentService {
	
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
		List<OneSimproblem> oneSimproblemList = simproblemDao.getSimproblemAndOptionByPaperId(paperId);

		if(oneSimproblemList.size() > 1) {		// 大于两道题才需要进行随机题序
			List<OneSimproblem> danxuanList = new ArrayList<>();
			List<OneSimproblem> duoxuanList = new ArrayList<>();
			List<OneSimproblem> panduanList = new ArrayList<>();
			List<OneSimproblem> tiankongList = new ArrayList<>();
			List<OneSimproblem> jiandaList = new ArrayList<>();

			/**打乱各部分题序，简单防止作弊**/
			for(OneSimproblem simp : oneSimproblemList) {		// 将各部分题目分配到几个list中
				switch (simp.getSimproblem().getType()) {
					case 1:
						danxuanList.add(simp);
						break;

					case 2:
						duoxuanList.add(simp);
						break;

					case 3:
						panduanList.add(simp);
						break;

					case 4:
						tiankongList.add(simp);
						break;

					case 5:
						jiandaList.add(simp);
						break;
				}
			}

			/**	小算法： 利用集合打乱的方法Collections.shuffle(list)对各个题型list进行打乱
			 * 			再将各个list加到总list
			 * 			然后按照题型type的先后顺序的再来setPos
			 * 			从而达到打乱题序的目的	 **/
			Collections.shuffle(danxuanList);
			Collections.shuffle(duoxuanList);
			Collections.shuffle(panduanList);
			Collections.shuffle(tiankongList);
			Collections.shuffle(jiandaList);

			oneSimproblemList.clear();
			oneSimproblemList.addAll(danxuanList);
			oneSimproblemList.addAll(duoxuanList);
			oneSimproblemList.addAll(panduanList);
			oneSimproblemList.addAll(tiankongList);
			oneSimproblemList.addAll(jiandaList);

			for(int i=0; i<oneSimproblemList.size(); i++) {
				oneSimproblemList.get(i).getSimproblem().setPos(i+1);
			}
		}
		System.out.println("thispaper------"+oneSimproblemList);
		return oneSimproblemList;
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
	public List<Map<String, Object>> selOneStuScore(String stuId,String contestName,String pageSize,String pageNumber) {
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(); //返回结果的容器
		//分页所需相关参数的计算
		if(pageSize!=null&&pageNumber!=null) {
			int pageSizeInt = Integer.parseInt(pageSize);
			int pageNumberInt = Integer.parseInt(pageNumber);
			PageHelper.startPage(pageNumberInt,pageSizeInt,true);//使用后数据库语句自动转为分页查询语句进行数据查询
		}
		resultList = contestStatusDao.selOneStuScore(stuId,contestName); //根据参数查询学生成绩等字段，如果参数全部为空自动查询全部学生的相关成绩
		return resultList;
	}
	
	 /**
	  * 根据条件更新用户的个人信息（修改个人信息（修改资料））
	  * @param stuId	检索用的学生id
	  * @param stuName
	  * @param stuEmail
	  * @param newPwd 新密码
	  * @return 更新是否成功 
	  */
	@Override
//	@SystemServiceLog(description = "修改个人信息（修改资料）")
	public int updateStuInfo(String stuId, String stuName, String stuEmail, String newPwd) {
		//校验邮箱是否已经存在
		if(stuEmail!=null && !"".equals(stuEmail)) {
			UserExample example = new UserExample();
			Criteria criteria = example.createCriteria();
			criteria.andEmailEqualTo(stuEmail.toString());
			List<User> userList = userDao.selectByExample(example);
			if(userList.size() > 0) {	// 表邮箱已有
				User user = userList.get(0);	
				//校验是否是自身原来的邮箱
				if(!user.getUserId().equals(stuId.trim())) {
					return -2;
				}
			}
		}
		
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
	
	/**
	 * 查询某个学生的个人成绩和班级考试的平均分
	 * @param stuId	用户Id	
	 * @param contestName 考试名字
	 * @return 所有对象
	 */
	@Override
	public List<Map<String, Object>> selOneStuScoreAVG(String stuId, List contestName) {
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(); //返回结果的容器
		resultList = contestStatusDao.selOneStuScoreAVGbyKeyword(stuId, contestName);
		return resultList;
	}
	
	/**
	 * 查询某个学生的所有考试名称
	 * @param stuId	用户Id	
	 * @return 所有对象
	 */
	@Override
	public List<Map<String, Object>> selStuContestTitle(String stuId) {
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(); //返回结果的容器
		resultList = contestStatusDao.selStuContestTitleByKeyword(stuId);
		return resultList;
	}


}
