/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 班级管理服务
 * */
package com.app.service.impl;


import com.app.dao.LogOperationMapper;
import com.app.service.LogService;
import com.code.model.LogOperation;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LogServiceImpl implements LogService {
	@Autowired
	LogOperationMapper logDao;


	@Override
	public int add(LogOperation log) {

		return logDao.insertSelective(log);
	}

	/**
	 * 根据日期查询日志列表
	 * @param startTime
	 * @param endTime
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getLogList(String startTime,String endTime,String pageSize, String pageNumber) {
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(); //返回结果的容器
		//分页所需相关参数的计算
		if(pageSize!=null&&pageNumber!=null) {
			int pageSizeInt = Integer.parseInt(pageSize);
			int pageNumberInt = Integer.parseInt(pageNumber);
			PageHelper.startPage(pageNumberInt,pageSizeInt,true);//使用后数据库语句自动转为分页查询语句进行数据查询
		}

		resultList = logDao.listAllByDate(startTime,endTime);

		return resultList;
	}


}
