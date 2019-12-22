/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 考试信息管理服务
 * */
package com.app.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.ContestMapper;
import com.app.dao.ContestStatusMapper;
import com.app.dao.UserMapper;
import com.code.model.Contest;
import com.code.model.ContestStatus;
import com.code.model.ContestStatusExample;
import com.code.model.User;
import com.code.model.UserExample;
import com.github.pagehelper.PageHelper;

import net.sf.json.JSONArray;

@Service
public class ContestService {
	
	@Autowired
	private ContestStatusMapper contestStatusDao;

	@Autowired
	private ContestMapper contestDao;
	
	@Autowired
	private UserMapper userDao;
	
	public List GetAllContest(String Keyword,String startTime,String endTime,String pageSize,String pageNumber){
		//分页所需相关参数的计算
		//根据参数查询学生成绩等字段，如果参数全部为空自动查询全部学生的相关成绩
		if(pageSize!=null&&pageNumber!=null) {
			int pageSizeInt = Integer.parseInt(pageSize);
			int pageNumberInt = Integer.parseInt(pageNumber);
			PageHelper.startPage(pageNumberInt,pageSizeInt,true);//使用后数据库语句自动转为分页查询语句进行数据查询
		}
        System.out.println("key=" + Keyword);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		if(Keyword!=null && !Keyword.trim().isEmpty()){
			if(startTime!=null && endTime!=null && !startTime.trim().isEmpty() && !endTime.trim().isEmpty()){
				list = contestDao.listAllByKeywordAndDate(Keyword, startTime, endTime);
			}
			else{
				list = contestDao.listAllByKeyword(Keyword);
			}
		}else if(startTime!=null && endTime!=null && !startTime.trim().isEmpty() && !endTime.trim().isEmpty())
		{
			list = contestDao.listAllByDate(startTime,endTime);
		}else
		{
			list = contestDao.listAll();  //都为空
		}
		
         
	    
	    for(Map<String,Object> map : list){
	        map.put("startTime", sdf.format((Date)map.get("startTime")));
	    	map.put("endTime", sdf.format((Date)map.get("endTime")));
	    }
	    System.out.println("contestList_------"+JSONArray.fromObject(list).toString());
	    return list;
	}
	
	public int AddContest(Contest contest){
		return contestDao.insertSelective(contest);
	}
	
	public int UpdateContest(Contest contest){
		return contestDao.updateByPrimaryKeySelective(contest);
	}
	
	public int DeleteContest(String id){
		return contestDao.deleteByPrimaryKey(Integer.parseInt(id));
	}
	

	
	public Contest GetContest(String id){
		return contestDao.selectByPrimaryKey(Integer.parseInt(id));
	}
	
	public int DeleteAllContest(List<String> ids)
	{  
	   int count = 0;
	   for(String id : ids){
		 count += contestDao.deleteByPrimaryKey(Integer.parseInt(id));
		 System.out.println("count=" + count);
	   } 
	   return count;
	}
	
	public List<Map<String,Object>> GetAllContestStudent(String id)
	{
       return contestStatusDao.GetAllContestStudent(id);
	}
	
	/**
	 * 根据考试id和班级id添加contest_status
	 * @param contest_id
	 * @param class_id
	 */
	public void AddContestClass(Integer contest_id,Integer class_id)
	{

		UserExample userExample = new UserExample();
		UserExample.Criteria criteria = userExample.createCriteria();
		criteria.andClassIdEqualTo(class_id);
		List<User> users = userDao.selectByExample(userExample);
		
		/*判断同一门考试（contest_id）同个学生（user_id）是否已经存在考试表格,如果有，则不添加到contest_status表*/
		
		//查询数据库中contest_id和userid条件下存在的学生，将其记录到existentUserId中
		List<String> existentUserId = new ArrayList<String>();
		for(User user : users){ 
	    	ContestStatusExample cStatusExample = new ContestStatusExample();
	    	ContestStatusExample.Criteria criteria1 = cStatusExample.createCriteria();
	    	criteria1.andContestIdEqualTo(contest_id);
	    	criteria1.andStudentEqualTo(user.getUserId());
			List<ContestStatus> contestStatus = contestStatusDao.selectByExample(cStatusExample);
			if(contestStatus.size()>0) {
				for (ContestStatus contestStatus2 : contestStatus) {
					existentUserId.add(contestStatus2.getStudent());
				}
			}
		}
		//System.out.println("existentUserId-----"+existentUserId);
		
		if(existentUserId.size()>0) {
			for(int i=0; i<users.size(); i++) {
				for(String str : existentUserId) {
					if(str.equals(users.get(i).getUserId())) {
						users.remove(i);	//去掉已存在contestStatus表格中的考生名单,防止重复插入考试
					}
				}
			}
    	}
		
		for(User user : users){
			ContestStatus contestStatus = new ContestStatus();
        	contestStatus.setStudent(user.getUserId());
        	contestStatus.setContestId(contest_id);
        	contestStatus.setScore(new BigDecimal(0));
        	contestStatus.setStatus(new Integer(0));
        	contestStatusDao.insert(contestStatus);
		}
	}
	
	/**
	 * 根据考试id获取contest对象
	 * @param contest_id
	 */
	public List<Map<String,Object>> selOneContestById(String contestId) {
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		
		resultList = contestDao.listAllByKeyword(contestId);
		return resultList;
	}
	
	/**
	 * 根据考试userid获取contest对象
	 * @param userId
	 */
	public List<Map<String,Object>> selAllContestByUserId(String userId) {
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		
		resultList = contestDao.selAllByUserId(userId);
		System.out.println("resultList----"+resultList);
		return resultList;
	}
}
