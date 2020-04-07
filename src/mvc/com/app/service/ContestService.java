/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 考试信息管理服务
 * */
package com.app.service;

import com.app.dao.ContestMapper;
import com.app.dao.ContestStatusMapper;
import com.app.dao.UserMapper;
import com.code.model.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ContestService {

	public List GetAllContest(String Keyword,int fkCourseId, String startTime,String endTime,String userId,String pageSize,String pageNumber);

	public int AddContest(Contest contest);

	public int UpdateContest(Contest contest);

	public int DeleteContest(String id);

	public Contest GetContest(String id);

	public int DeleteAllContest(List<String> ids);

	public List<Map<String,Object>> GetAllContestStudent(String id,String pageSize,String pageNumber);
	/**
	 * 根据考试id和班级id添加contest_status
	 * @param contest_id
	 * @param class_id
	 */
	public void AddContestClass(Integer contest_id,Integer class_id);

	/**
	 * 根据考试id和班级id 移除contest_status
	 * @param contest_id
	 * @param class_id
	 */
	@Transactional(rollbackFor=Exception.class)	
	public void removeContestClass(Integer contest_id,Integer class_id);

	/**
	 * 根据考试id获取contest对象
	 * @param
	 */
	public List<Map<String,Object>> selOneContestById(String contestId);

	/**
	 * 根据考试userid获取contest对象
	 * @param userId
	 */
	public List<Map<String,Object>> selAllContestByUserId(String userId);
}
