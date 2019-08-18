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

import net.sf.json.JSONArray;

@Service
public class ContestService {
	
	@Autowired
	private ContestStatusMapper contestStatusDao;

	@Autowired
	private ContestMapper contestDao;
	
	@Autowired
	private UserMapper userDao;
	
	public JSONArray GetAllContest(String Keyword,String startTime,String endTime){
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
	    System.out.println(JSONArray.fromObject(list).toString());
	    return JSONArray.fromObject(list);
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
	
	public void AddContestClass(Integer contest_id,Integer class_id)
	{

		UserExample userExample = new UserExample();
		UserExample.Criteria criteria = userExample.createCriteria();
		criteria.andClassIdEqualTo(class_id);
		List<User> users = userDao.selectByExample(userExample);
        for(User user : users){
        	ContestStatus contestStatus = new ContestStatus();
        	contestStatus.setStudent(user.getUserId());
        	contestStatus.setContestId(contest_id);
        	contestStatus.setScore(new BigDecimal(0));
        	contestStatus.setStatus(new Integer(0));
        	contestStatusDao.insert(contestStatus);
        }
		
	}

}
