/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 试卷管理服务
 * */
package com.app.service;

import com.app.dao.ContestMapper;
import com.app.dao.ContestpaperMapper;
import com.app.dao.SimproblemMapper;
import com.code.model.Contestpaper;
import com.github.pagehelper.PageHelper;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ContestPaperService {

	public List GetAllContestPaper(String Keyword,String startTime,String endTime,int simCourseId,String pageSize,String pageNumber);

	public int AddContestPaper(Contestpaper contestpaper);

	public int UpdateContestPaper(Contestpaper contestpaper);

	public int DeleteContestPaper(String id);

	public Contestpaper GetContestPaper(String id);

	public int DeleteAllContestPaper(List<String> ids);

	public List<Map<String,Object>> selReusePaper(String teacherId,int simCourseId);

	
}
