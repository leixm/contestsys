/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 试卷管理服务
 * */
package com.app.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.annotation.SystemServiceLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.ContestMapper;
import com.app.dao.ContestpaperMapper;
import com.app.dao.SimproblemMapper;
import com.code.model.Contestpaper;
import com.code.model.Simproblem;
import com.github.pagehelper.PageHelper;

import net.sf.json.JSONArray;

@Service
public class ContestPaperService {

	@Autowired
	private ContestpaperMapper contestpaperDao;
	@Autowired
	private SimproblemMapper simpDao;
	@Autowired
	private ContestMapper contestDao;
	
	
	public List GetAllContestPaper(String Keyword,String startTime,String endTime,int simCourseId,String pageSize,String pageNumber){
        //System.out.println("key=" + Keyword + " startTime=" + startTime + "endTime=" + endTime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		//分页所需相关参数的计算
		if(pageSize!=null&&pageNumber!=null) {
			int pageSizeInt = Integer.parseInt(pageSize);
			int pageNumberInt = Integer.parseInt(pageNumber);
			PageHelper.startPage(pageNumberInt,pageSizeInt,true);//使用后数据库语句自动转为分页查询语句进行数据查询
		}


		/*if(Keyword!=null && !Keyword.trim().isEmpty()){
			if(startTime!=null && endTime!=null && !startTime.trim().isEmpty() && !endTime.trim().isEmpty()){
				list = contestpaperDao.listAllByKeywordAndDate(Keyword, startTime, endTime);
			}
			else{
				list = contestpaperDao.listAllByKeyword(Keyword);
			}
		}else if(startTime!=null && endTime!=null && !startTime.trim().isEmpty() && !endTime.trim().isEmpty())
		{
			list = contestpaperDao.listAllByDate(startTime,endTime);
		}else
		{
			list = contestpaperDao.listAll();  //都为空
		}*/

		list = contestpaperDao.listAllByKeywordAndDate(Keyword, startTime, endTime,simCourseId);

	    for(Map<String,Object> map : list){
	        map.put("date", sdf.format((Date)map.get("date")));
	    }
	    System.out.println(JSONArray.fromObject(list).toString());
	    return list;
	}

	@SystemServiceLog(description = "添加考试试卷")
	public int AddContestPaper(Contestpaper contestpaper){
		return contestpaperDao.insertSelective(contestpaper);
	}

	@SystemServiceLog(description = "更新试卷信息")
	public int UpdateContestPaper(Contestpaper contestpaper){
		return contestpaperDao.updateByPrimaryKeySelective(contestpaper);
	}

	@SystemServiceLog(description = "删除试卷")
	public int DeleteContestPaper(String id){
		//根据id查询试卷是否用作考试
		List contestList = contestDao.selContestByPaperId(id);
		if(!contestList.isEmpty()) {
			return -2;
		}
		
		return contestpaperDao.deleteByPrimaryKey(Integer.parseInt(id));
	}
	

	
	public Contestpaper GetContestPaper(String id){
		return contestpaperDao.selectByPrimaryKey(Integer.parseInt(id));
	}
	
	
	@SystemServiceLog(description = "批量删除试卷")
	public int DeleteAllContestPaper(List<String> ids) {  
	   int count = 0;
	   for(String id : ids){
		 count += contestpaperDao.deleteByPrimaryKey(Integer.parseInt(id));
		 System.out.println("count=" + count);
	   } 
	   return count;
	}
	
	/**
	 * 查询可以添加复用题目的某课程下、某个教师出的试卷(排除已存在此题的试卷)(管理员出的试题都可以查询到)
	 * @param teacherId
	 * @param simCourseId
	 * @return
	 */
	public List<Map<String,Object>> selReusePaper(String teacherId,int simCourseId) {
		
		List<Map<String,Object>> paperList = contestpaperDao.selReusePaper(teacherId,simCourseId);
		
		return paperList;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
