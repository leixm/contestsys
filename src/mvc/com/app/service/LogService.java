/** 
 * @author zzs
 * @create_date 2019.8.1
 * @description 教师相关业务服务接口
 **/
package com.app.service;


import com.code.model.LogOperation;

import java.util.List;
import java.util.Map;

public interface LogService {
	int add(LogOperation log);

	/**
	 * 根据日期查询日志列表
	 * @param startTime
	 * @param endTime
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	List<Map<String,Object>> getLogList(String startTime,String endTime,String pageSize,String pageNumber);

}
