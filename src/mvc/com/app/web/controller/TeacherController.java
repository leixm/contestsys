/** 
 * @author zzs
 * @create_date 2019.8.1
 * @description 教师相关业务控制器
 **/
package com.app.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.annotation.SystemControllerLog;
import com.app.service.impl.ContestPaperService;
import com.code.model.*;
import com.lowagie.text.Paragraph;
import com.sun.jna.IntegerType;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.app.service.TeacherService;
import com.app.tools.ExportExcelUtil;
import com.app.tools.PathHelper;
import com.app.tools.RandomString;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
public class TeacherController {
	@Resource
	private TeacherService teacherService;

	@Resource
	private ContestPaperService contestpaperService;
	//导出excel用  文件分隔符
	public static final String FILE_SEPARATOR = System.getProperties()
			.getProperty("file.separator");
	
	
	
	@RequestMapping(value="Teacher/selAllpaper",method={RequestMethod.POST},produces="text/html;charset=UTF-8")
	/**
	 * 查老师所有试题(reObj封装的是List<OnePaper>对象)
	 * @param request
	 * @param response
	 * @return 属于同个userId的所有的卷子
	 */
	@ResponseBody
	public String selAllpaper(HttpServletRequest request,HttpServletResponse response) {
		
		//User user = (User)request.getSession().getAttribute("user");
		//String userId = user.getUserId();
		//测试数据
		User user = new User();
		String userId = "123";
		
		//测试
		user.setLevel(1);
		user.setUserId(userId);
		
		// System.out.println(JSONObject.fromObject(teacherService.selAllpaper(user)).toString());
		return JSONObject.fromObject(teacherService.selAllpaper(user)).toString();
	}

	
	

	//@RequestBody Response resp,
	@RequestMapping(value="Teacher/addNewpaper",method={RequestMethod.POST},produces="text/html;charset=UTF-8")
	/**
	 * 添加新试卷
	 * @param request
	 * @param response
	 * @return 1添加成功     0添加失败
	 */
	//@RequestBody Response resp,
	@ResponseBody
	@SystemControllerLog(description = "手动组卷")
	public String addNewpaper(@RequestBody String resp ,HttpServletRequest request,HttpServletResponse response) {
		LayResponse layresp = new LayResponse();
		layresp.setCode(1);
		
		//获取所登录用户的user对象
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String userId = user.getUserId();
		int level = user.getLevel();
		
		if(level < 1) {
			layresp.setMsg("无操作权限");
			return JSONObject.fromObject(layresp).toString();
		}
		
		String basePath = PathHelper.getNormativePath(request);
		
		user.setLevel(user.getLevel());
		user.setUserId(userId);

		//1、使用JSONObject(把json字符串转Java对象)
		JSONObject jsonObject=JSONObject.fromObject(resp);
		//复杂对象需要借助Map(所有容器对象都得put进去)
		Map classMap = new HashMap();
		classMap.put("simp",OneSimproblem.class);
		classMap.put("prob", OneProblem.class);
		classMap.put("option", Options.class);
		classMap.put("answer", Answer.class);
		classMap.put("urls", UrlData.class);
		OnePaper newpaper=(OnePaper)JSONObject.toBean(jsonObject,OnePaper.class,classMap);
		System.out.println("手动组卷内容为：======="+resp);
		List<OneSimproblem> oneSimps = newpaper.getSimp();
		
		return JSONObject.fromObject(teacherService.addNewpaper(newpaper,user,oneSimps,basePath)).toString();
	}
	
	/**
	 * 导出年级成绩表========================此接口已经没有调用
	 * 解析：从数据库表获取组建表格需要的List<ScoreExcel>对象，然后通过Excel导出工具导出workbook对象到服务器，在利用download从上边下载到本地即可
	 * @param user	具体老师
	 * @param contest 具体考试
	 * @return 
	 * @return 导出结果
	 */
	@RequestMapping(value="Teacher/gradeScoreExcel",produces="text/html;charset=UTF-8")
	@ResponseBody
	@SystemControllerLog(description = "导出成绩表")
	protected String exportGradeScoreExcel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LayResponse resp = new LayResponse();
		resp.setCode(1); //默认设置失败code
		User user = (User)request.getSession().getAttribute("user");
		
		int level = user.getLevel();
		if(level < 1) {
			resp.setMsg("无操作权限");
			return JSONObject.fromObject(resp).toString();
		}

		int simCourseId = 0;
		if(request.getSession().getAttribute("course_id")!=null) {
			simCourseId = Integer.parseInt(request.getSession().getAttribute("course_id").toString());
		}
		String userId = user.getUserId();
		if(level == 2) {
			userId = "root";
		}
		/*----获取导出的数据集开始----*/
		String className = request.getParameter("classname"); 
		String stuId = request.getParameter("stuid");
		String stuName = request.getParameter("stuname");
		String contestName = request.getParameter("contestname");
		//System.out.println(stuId);
		//System.out.println(contestName);
		//判断是否非空后再来去掉前后空格，防止空指针
		if(className!=null||"".equals(className)) {
			className = className.trim();
		}
		if(stuId!=null||"".equals(stuId)) {
			stuId = stuId.trim();	
		}
		if(stuName!=null||"".equals(stuName)) {
			stuName = stuName.trim();
		}
		if(contestName!=null||"".equals(contestName)) {
			contestName = contestName.trim();
		}
		//设置空字段，防止进行分页查询，方便重用查询成绩方法
		String pageSize = null;
		String pageNumber = null;

		List statusList = new ArrayList();
		statusList.add(2);
		//数据库查询返回的学生成绩结果集
		List<Map<String,Object>> resultList = teacherService.selStuScore(className, stuId, stuName, contestName,simCourseId,statusList,userId,pageSize,pageNumber);
		/*----获取导出的数据集结束----*/
		
		//自动创建文件夹docs
		File file=new File(PathHelper.getNormativePath(request,"docs"));
		if(!file.exists()){//如果文件夹不存在
			file.mkdir();//创建文件夹
		}
		//文件上传路径（注意要在服务器创建对应的docs文件夹，不然会出错）
		String docsPath = PathHelper.getNormativePath(request,"docs");
		//固定头
		String[] headers = {"学号", "姓名","考试名称", "班级", "成绩"};
		//从session获取信息
		/*User user = (User)request.getSession().getAttribute("user");
		Contest contest = (Contest)request.getSession().getAttribute("contest");*/
		
		//存放导出表格所需要的学生个人成绩信息
		List<ScoreExcel> scoreExcelList = new ArrayList<>();
		
		if(resultList.size()>0) {
			for(Map<String,Object> map : resultList) {
				//创建bean存数据（需要判断是否map中有数据，防止空指针）
				ScoreExcel scoreExcel = new ScoreExcel();
				scoreExcel.setStudentId(map.get("stuid").toString());
				scoreExcel.setName(map.get("stuname").toString());
				scoreExcel.setContestName(map.get("contestname").toString());
				scoreExcel.setClassName(map.get("classname").toString());
				scoreExcel.setScore(map.get("score").toString());
				scoreExcelList.add(scoreExcel);
			}
		}
		try {
			
			// "xxx.xls"名字必须和Util的一致
			String fileName ="学生成绩表.xls";
			OutputStream out = new FileOutputStream(docsPath + FILE_SEPARATOR + fileName);
			String tableTitle = "学生成绩表";
			//利用导出工具 写出workbook对象到指定路径
			new ExportExcelUtil().exportExcel(tableTitle,headers, scoreExcelList, out);
			out.close(); //关闭输出流
			//JOptionPane.showMessageDialog(null, "导出成功!");
			//System.out.println("excel导出成功！");
			//路径要对应上面out
			String filePath = docsPath + FILE_SEPARATOR + fileName;
			//从服务器下载表格到本地
			download(filePath, response);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		resp.setMsg("导出excel表格成功");
		resp.setCode(0);//表示成功
		return JSONObject.fromObject(resp).toString();
	}
	
	//下载文件方法
	private void download(String path, HttpServletResponse response) {
		try {
			//防止路劲中有空格作怪干扰
			URLDecoder.decode(path,"UTF-8");
			URLEncoder.encode(path,"UTF-8");
			// path是指欲下载的文件的路径。
			File file = new File(path);

			if(!file.exists()){
			    //先得到文件的上级目录，并创建上级目录，在创建文件
			    file.getParentFile().mkdir();
			    try {
			        //创建文件
			        file.createNewFile();
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			}
			// 取得文件名。
			String filename = file.getName();
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes(),"ISO-8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/vnd.ms-excel;charset=gb2312");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 组卷端上传编程题输入输出文件
	 * @param partFiles
	 * @param request
	 * @return
	 */
	@RequestMapping(value="Teacher/upload",method={RequestMethod.POST},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String uploadProblem(@RequestParam MultipartFile file,HttpServletRequest request) {
		InputStream inputStream = null;
		Response response = new Response();
		response.setSuccess(0);
		try {
			User user = (User)request.getSession().getAttribute("user");



			String filename = file.getOriginalFilename();
			if(!( (filename.substring(filename.lastIndexOf('.') + 1 , filename.length()).equals("txt"))))
				return "文件上传失败(只支持txt格式)";


			//	获取到的是当前绝对项目路劲并且有/号  	“/”指代项目根目录，所以代码返回的是项目在容器中的实际发布运行的根路径
			String ranString = RandomString.getRandomString(30);

			String path = PathHelper.getNormativePath(request,"/uploadTemp/"+ ranString);
			File newFile = new File(path);
			File parentFile = newFile.getParentFile();
			if(!parentFile.exists())
				parentFile.mkdirs();
			inputStream = file.getInputStream();
			FileUtils.copyInputStreamToFile(inputStream, newFile);


			if(inputStream!=null){
				inputStream.close();
			}
			response.setMsg(ranString);
			response.setSuccess(1);
			return JSONObject.fromObject(response).toString();

		} catch (Exception e) {
			e.printStackTrace();
			response.setMsg("上传失败");
			return JSONObject.fromObject(response).toString();
		}
	}
	

	
	/**
	 * 根据搜索条件模糊查询出学生成绩表
	 * @param request
	 * @param response
	 * @return 返回查询到的学生成绩等相关信息
	 */
	@RequestMapping(value="Teacher/selStuScore",method={RequestMethod.POST},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String selStuScore(HttpServletRequest request,HttpServletResponse response) {
		LayResponse layResp = new LayResponse();//layui参数返回格式
		layResp.setCode(1); //默认设置为1
		//获取所登录用户的user对象
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int level = user.getLevel();
		if(level < 1) {
			layResp.setMsg("无操作权限");
			return JSONObject.fromObject(response).toString();
		}
		String userId = user.getUserId();
		if(level == 2) {	//管理员权限
			userId = "root";
		}
		
		
		
		int simCourseId = 0;
		if(session.getAttribute("course_id")!=null) {
			simCourseId = Integer.parseInt(session.getAttribute("course_id").toString());
		}

		String className = request.getParameter("classname"); 
		String stuId = request.getParameter("stuid");
		String stuName = request.getParameter("stuname");
		String contestName = request.getParameter("contestname");
		//判断是否非空后再来去掉前后空格，防止空指针
		if(className!=null||"".equals(className)) {
			className = className.replaceAll(" ","");
		}
		if(stuId!=null||"".equals(stuId)) {
			stuId = stuId.replaceAll(" ","");	
		}
		if(stuName!=null||"".equals(stuName)) {
			stuName = stuName.replaceAll(" ","");
		}
		if(contestName!=null||"".equals(contestName)) {
			contestName = contestName.replaceAll(" ","");
		}
		//获取分页所需相关数据
		String pageSize = request.getParameter("limit"); //一页多少个
		String pageNumber = request.getParameter("page");	//第几页

		List statusList = new ArrayList();
		statusList.add(2); // 2表示只查询判题好了的cstatus
		
		if(request.getParameter("shortAnswer")!=null) {	// 表示当主观题批改页面调用此接口，查询正在批改的题
			statusList.add(1);
		}
		// 根据userId等条件，查询某个教师/管理员权限下的<负责的>学生的成绩等信息
		List<Map<String,Object>> resultList = teacherService.selStuScore(className, stuId, stuName, contestName,simCourseId,statusList,userId,pageSize,pageNumber); //数据库查询返回的学生成绩结果集
		//获取分页插件的数据只能通过PageInfo来获取
		PageInfo pInfo = new PageInfo(resultList);
		Long total = pInfo.getTotal();
		
		if(resultList.size() > 0) {
			//返回规定格式给前端
			layResp.setCode(0);
			layResp.setCount(total.intValue());
			layResp.setData(resultList);
			
			return JSONObject.fromObject(layResp).toString();
		}
		layResp.setMsg("无数据");
		return JSONObject.fromObject(layResp).toString();
	}
	
	/**
	 * @param cStatusId: 考试状态id————contest_status_id
	 * @description 更新学生成绩信息
	 * @return 视图、班级信息、学生信息
	 */
	@RequestMapping(value = "editScore.do", method = { RequestMethod.POST,RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView editScore(HttpServletRequest request, String stuid, String stuname, String classname, String contestname, String score, String cstatusid) {
		
		ModelAndView mav = new ModelAndView();
		ScoreObject scoreObj = new ScoreObject();
		scoreObj.setClassName(classname);
		scoreObj.setContestName(contestname);
		scoreObj.setcStatusId(cstatusid);
		scoreObj.setScore(score);
		scoreObj.setStuId(stuid);
		scoreObj.setStuName(stuname);
		mav.addObject("scoreobj",scoreObj);
		mav.setViewName("score-edit.jsp");
		return mav;
	}
	/**
	 * @param cstatusid: 所更新成绩对应的表的主键id
	 * @param score: 用户重新更新的成绩
	 * @description 更新用户信息
	 * @return 响应状态
	 */
	@RequestMapping(value = "Teacher/updateScore", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@SystemControllerLog(description = "修改学生总成绩")
	public String updateScore(HttpServletRequest request,String score,String cstatusid) {
		LayResponse response = new LayResponse();
		response.setCode(1);
		//获取所登录用户的user对象
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int level = user.getLevel();
		if(level < 1) {
			response.setMsg("无操作权限");
			return JSONObject.fromObject(response).toString();
		}

		if(!score.trim().isEmpty()&&!cstatusid.trim().isEmpty()){
			//将新修改的成绩根据cstatusid存进对应的表
			int result = teacherService.updateScore(cstatusid, score);
			if(result > 0) {
				response.setCode(0); //0代表成功 
				response.setMsg("成绩更新成功");
				return JSONObject.fromObject(response).toString();
			}else {
				response.setMsg("成绩更新失败");
				return JSONObject.fromObject(response).toString();
			}
			
		} else {
			response.setMsg("成绩更新失败");
			return JSONObject.fromObject(response).toString();
		}
	}
	
	/**
	 * @description 查所有的班级名称
	 * @return 响应状态
	 */
	@RequestMapping(value = "Teacher/selAllClassObj", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String selAllClassObj(HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String, Object>();

		//获取所登录用户的user对象
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String userId = user.getUserId();

		List<Map<String,Object>> classList = teacherService.selAllClassObj(userId);
		List<String> classNameList = new ArrayList<String>();
		List<String> classIdList = new ArrayList<String>();
		
		if(classList.size() > 0) {
			for (Map<String, Object> classObj : classList) {
				//System.out.println("classObj---"+classObj);
				classNameList.add(classObj.get("name").toString());
				classIdList.add(classObj.get("class_id").toString());
			}
		}
		//System.out.println(classNameList);
		resultMap.put("classnames", classNameList);
		resultMap.put("classidlist", classIdList);
		resultMap.put("msg", "查询成功");
		return JSONObject.fromObject(resultMap).toString();
	}
	
	/**
	 * @description 查所有的考试名称
	 * @return 响应状态
	 */
	@RequestMapping(value = "Teacher/selAllContestTitle", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String selAllContestTitle(HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<Map<String,Object>> contestList = teacherService.selAllContestObj();
		List<String> contestNameList = new ArrayList<String>();
		
		if(contestList.size() > 0) {
			for (Map<String, Object> contestObj : contestList) {
				contestNameList.add(contestObj.get("title").toString());
			}
		}
		resultMap.put("contestname", contestNameList);
		resultMap.put("msg", "查询成功");
		return JSONObject.fromObject(resultMap).toString();
	}
	

	/**
	 * 查询所有的班级的所有考试的平均分
	 * @return 按照Echars所需要的格式返回JSON格式的参数 
	 */
	@RequestMapping(value = "Teacher/selClassContestAVG", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String selClassContestAVG(HttpServletRequest request) {
		//获取所登录用户的user对象
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String userId = user.getUserId();

		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<String> seriesList = new ArrayList<String>(); //存series供图表用
		Set set = new HashSet(); //利用set特性来去重
		List<String> titleList = new ArrayList<String>();	//格式为：['titleList', 'C语言测试', 'JAVA测试', 'python测试','C++测试'],
		titleList.add("titleList");		//------+1
		
		List<String> classNameList = new ArrayList<String>();	//格式为：['软件一班','软件一班','软件一班'],
		List<List<String>> allAvgList = new ArrayList<>(); //格式为： ['软件一班', '43.3', 85.8, 93.7,77.3],
		
		//创建供sql in 语句查询的对象，下面对前端参数进行分割拼接成list
		List claList = new ArrayList();
		List conList = new ArrayList();
		//默认定义班级名称为查询到的前四个班级
		List<Map<String,Object>> tempList = teacherService.selAllClassObj(userId);
		if(tempList.size()>4) {	//只有大于4个班级才需要进行循环赋值，否则只需要显示全部即可
			for (int i=0; i<4; i++) {
				claList.add(tempList.get(i).get("name").toString());
			}
		}
		
		if(request.getParameter("selclass") != null && !("null").equals(request.getParameter("selclass"))) {
			claList.clear(); //清楚前面干扰的初始化数据
			String className = request.getParameter("selclass").toString();
			className = className.substring(2,className.length()-2); //去掉首尾["  "]
			className = className.replaceAll("\",\"",",");
			String str[] = className.split(",");
			for (String string : str) {
				claList.add(string);
			}
		}else if(claList.size()==0) {
			claList = null;
		}
		
		
		
		if(request.getParameter("selcontest") != null && !("null").equals(request.getParameter("selcontest"))) {
			String contest = request.getParameter("selcontest").toString();
			contest = contest.substring(2,contest.length()-2); //去掉首尾["  "]
			contest = contest.replaceAll("\",\"",",");
			String str[] = contest.split(",");
			for (String string : str) {
				conList.add(string);
			}
		}else {
			conList = null;
		}
		
		//获取某个班级某场考试的平均分
		List<Map<String,Object>> classList = teacherService.selClassContestAVG(claList, conList);
		if(classList.size() > 0) {
			for (Map<String, Object> map : classList) {
				String title = map.get("title").toString();
				String classname = map.get("classname").toString();
				if(set.add(title)) { //去重判断
					titleList.add(title);
				}
				if(set.add(classname)) { //去重判断
					classNameList.add(classname);
				}
			}
			
			//计算并拼接 series: [ {type: 'bar'},{type: 'bar'}]
			int size = titleList.size()-1;	//减一是因为前面默认加了titleList.add("titleList")
			String series = "{type: 'bar'}";
			for(int i=0;i<size;i++)  {
				seriesList.add(series);
			}
			//System.out.println("seriesList----"+seriesList);
			Set set2 = new HashSet(); //利用set特性来去重
			//添加完班级名称和考试名称集合后，遍历查询数据的键值对集合，组装特定格式数据（格式为： ['软件一班', '43.3', 85.8, 93.7,77.3],）
			allAvgList.add(titleList);//添加每个bar的名称集合在集合第一位置
			
			List<String> titleList2 = new ArrayList<String>();	//用于遍历，便于组装参数
			titleList2.addAll(titleList);
			titleList2.remove(0);	//去掉干扰项"titleList"，便于组装参数
			
			for(String classname : classNameList) {
				List<String> classAvgList = new ArrayList<String>(); //格式为： ['软件一班', '43.3', 85.8, 93.7,77.3],
				classAvgList.add(classname);
				for(String title : titleList2) {
					String average = "0";	//给某个班级没有进行某门考试的位置赋值0，防止数据错位
					for (Map<String, Object> map : classList) {
						if(map.get("title").equals(title) && map.get("classname").equals(classname)) {
							average = map.get("average").toString();
						}
					}
					classAvgList.add(average); //格式为： ['软件一班', '43.3', 85.8, 93.7,77.3],
					//System.out.println("classAvgList-----"+classAvgList);
				}
				allAvgList.add(classAvgList);
				//System.out.println("allAvgList-----"+allAvgList);
			}
		}
		resultMap.put("series", seriesList);
		resultMap.put("dataset", allAvgList);
		resultMap.put("msg", "请求成功");
		resultMap.put("status", 1);
		return JSONObject.fromObject(resultMap).toString();
	}
	
	/**
	 * 查询所有的班级的所有考试的最高分
	 * @return 按照Echars所需要的格式返回JSON格式的参数 
	 */
	@RequestMapping(value = "Teacher/selClassContestMAX", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String selClassContestMAX(HttpServletRequest request) {
		//获取所登录用户的user对象
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String userId = user.getUserId();

		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<String> seriesList = new ArrayList<String>(); //存series供图表用
		Set set = new HashSet(); //利用set特性来去重
		List<String> titleList = new ArrayList<String>();	//格式为：['titleList', 'C语言测试', 'JAVA测试', 'python测试','C++测试'],
		titleList.add("titleList");		//------+1
		
		List<String> classNameList = new ArrayList<String>();	//格式为：['软件一班','软件一班','软件一班'],
		List<List<String>> allMaxScoreList = new ArrayList<>(); //格式为： ['软件一班', '43.3', 85.8, 93.7,77.3],
		
		//创建供sql in 语句查询的对象，下面对前端参数进行分割拼接成list
		List claList = new ArrayList();
		List conList = new ArrayList();
		
		//默认定义班级名称为查询到的前四个班级
		List<Map<String,Object>> tempList = teacherService.selAllClassObj(userId);
		if(tempList.size()>4) {	//只有大于4个班级才需要进行循环赋值，否则只需要显示全部即可
			for (int i=0; i<4; i++) {
				claList.add(tempList.get(i).get("name").toString());
			}
		}
				
		if(request.getParameter("selclass") != null && !("null").equals(request.getParameter("selclass"))) {
			claList.clear(); //清楚前面干扰的初始化数据
			String className = request.getParameter("selclass").toString();
			className = className.substring(2,className.length()-2); //去掉首尾["  "]
			className = className.replaceAll("\",\"",",");
			String str[] = className.split(",");
			for (String string : str) {
				claList.add(string);
			}
		}else if(claList.size()==0) {
			claList = null;
		}
		
		if(request.getParameter("selcontest") != null  && !("null").equals(request.getParameter("selcontest"))) {
			String contest = request.getParameter("selcontest").toString();
			contest = contest.substring(2,contest.length()-2); //去掉首尾["  "]
			contest = contest.replaceAll("\",\"",",");
			String str[] = contest.split(",");
			for (String string : str) {
				conList.add(string);
			}
		}else {
			conList = null;
		}
		//获取某个班级某场考试的平均分
		List<Map<String,Object>> classList = teacherService.selClassContestAVG(claList, conList);
		if(classList.size() > 0) {
			for (Map<String, Object> map : classList) {
				String title = map.get("title").toString();
				String classname = map.get("classname").toString();
				if(set.add(title)) { //去重判断
					titleList.add(title);
				}
				if(set.add(classname)) { //去重判断
					classNameList.add(classname);
				}
			}
			
			//计算并拼接 series: [ {type: 'bar'},{type: 'bar'}]
			int size = titleList.size()-1;	//减一是因为前面默认加了titleList.add("titleList")
			String series = "{type: 'bar'}";
			for(int i=0;i<size;i++)  {
				seriesList.add(series);
			}
			Set set2 = new HashSet(); //利用set特性来去重
			//添加完班级名称和考试名称集合后，遍历查询数据的键值对集合，组装特定格式数据（格式为： ['软件一班', '43.3', 85.8, 93.7,77.3],）
			allMaxScoreList.add(titleList);//添加每个bar的名称集合在集合第一位置
			
			List<String> titleList2 = new ArrayList<String>();	//用于遍历，便于组装参数
			titleList2.addAll(titleList);
			titleList2.remove(0);	//去掉干扰项"titleList"，便于组装参数
			
			for(String classname : classNameList) {
				List<String> classMaxList = new ArrayList<String>(); //格式为： ['软件一班', '43.3', 85.8, 93.7,77.3],
				classMaxList.add(classname);
				for(String title : titleList2) {
					String maxscore = "0";	//给某个班级没有进行某门考试的位置赋值0，防止数据错位
					for (Map<String, Object> map : classList) {
						if(map.get("title").equals(title) && map.get("classname").equals(classname)) {
							maxscore = map.get("maxscore").toString();
						}
					}
					classMaxList.add(maxscore); //格式为： ['软件一班', '43.3', 85.8, 93.7,77.3],
				}
				allMaxScoreList.add(classMaxList);
				//System.out.println("allAvgList-----"+allMaxScoreList);
			}
		}
		resultMap.put("series", seriesList);
		resultMap.put("dataset", allMaxScoreList);
		resultMap.put("msg", "请求成功");
		resultMap.put("status", 1);
		return JSONObject.fromObject(resultMap).toString();
	}
	
	/**
	 * 查询通用题库列表
	 * @return 返回JSON格式的列表信息 
	 */
	@RequestMapping(value = "Teacher/selSimproblemList", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String selSimproblemList (HttpServletRequest request) {
		LayResponse response = new LayResponse();
		response.setCode(1);  
		//获取所登录用户的user对象
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int level = user.getLevel();
		if(level < 1) {
			response.setMsg("无操作权限");
			return JSONObject.fromObject(response).toString();
		}
		
		int simCourseId = 0;
		if(session.getAttribute("course_id")!=null) {
			simCourseId = Integer.parseInt(session.getAttribute("course_id").toString());
		}
		int simType = 0;
		if(request.getParameter("simType")!=null) {
			simType = Integer.parseInt(request.getParameter("simType"));
		}
		String simPaperTitle = request.getParameter("simPaperTitle");
		if(simPaperTitle!=null&&!"".equals(simPaperTitle)) {	//去除前后空格
			simPaperTitle = simPaperTitle.trim();
		}
		//获取分页所需相关数据
		String pageSize = request.getParameter("limit"); //一页多少个
		String pageNumber = request.getParameter("page");	//第几页
		
		List<Map<String,Object>> resultList = teacherService.selSimproblemList(simCourseId, simPaperTitle, simType,pageSize,pageNumber);
		//获取分页插件的数据只能通过PageInfo来获取
		PageInfo pInfo = new PageInfo(resultList);
		Long total = pInfo.getTotal();
		
		response.setCode(0);
		response.setMsg("请求成功");
		response.setCount(total.intValue());
		response.setData(resultList);
		return JSONObject.fromObject(response).toString();
	}
	
	/**
	 * 删除单条simproblem
	 * @return 返回JSON格式的列表信息 
	 */
	@RequestMapping(value = "Teacher/delSimproblem", produces = "text/html;charset=UTF-8")
	@ResponseBody
	@SystemControllerLog(description = "删除单条simproblem")
	public String delSimproblem(HttpServletRequest request,String simId) {
		LayResponse response = new LayResponse();
		response.setCode(1);  
		//获取所登录用户的user对象
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int level = user.getLevel();
		if(level < 1) {
			response.setMsg("无操作权限");
			return JSONObject.fromObject(response).toString();
		}
		
		if(teacherService.delSimproblemById(Integer.parseInt(simId)) > 0) {
			response.setMsg("删除成功");
			response.setCode(0);
			return JSONObject.fromObject(response).toString();
		}
		response.setMsg("删除失败");
		return JSONObject.fromObject(response).toString();
	}
	
	/**
	 * 删除多条simproblem
	 * @return 返回JSON格式的列表信息 
	 */
	@RequestMapping(value = "Teacher/delBatchSimproblem", produces = "text/html;charset=UTF-8")
	@ResponseBody
	@SystemControllerLog(description = "批量删除simproblem")
	public String delBatchSimproblem(HttpServletRequest request,@RequestBody List<String> ids) {
		LayResponse response = new LayResponse();
		response.setCode(1);  
		//获取所登录用户的user对象
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int level = user.getLevel();
		if(level < 2) {
			response.setMsg("无操作权限");
			return JSONObject.fromObject(response).toString();
		}
		
		int count = teacherService.delBatchSimproblemByIds(ids);
	    if(count>0){
		   response.setCode(0); 
		   response.setMsg("成功删除"+count+"条记录"); 
		   return JSONObject.fromObject(response).toString(); 
	    }
	    else{
		  response.setMsg("删除失败"); 
		  return JSONObject.fromObject(response).toString(); 
	    }
	  
	}


	/**
	 * @author zzs
	 * @description 检查用户是否有权限对题目进行修改(教师只能给负责的试卷的题目进行修改)
	 * @return 响应状态
	 */
	@RequestMapping(value = "Teacher/judgePowerToEditSimproblem", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String judgePowerToEditSimproblem(HttpServletRequest request) throws ParseException {
		//规定返回对象，默认设置Code为1表示失败
		LayResponse response = new LayResponse();
		response.setCode(1);

		//获取登录对象，判断其角色
		User user = (User)request.getSession().getAttribute("user");
		int level = user.getLevel(); //学生： 0  老师：1  管理员：2
		String userId = user.getUserId();

		if(level>0) {
			String simPaperId = request.getParameter("simPaperId");
			if(simPaperId == null || "undefined".equals(simPaperId)) {
				if( level>1) {
					response.setCode(0);
					response.setMsg("操作权限验证通过");
					return JSONObject.fromObject(response).toString();
				}
				response.setCode(1);
				response.setMsg("无权限，编辑失败");
				return JSONObject.fromObject(response).toString();
			}
			//判断操作用户的id是否和contest表中的teacher一样
			Contestpaper selPaper = teacherService.selOneContestPaperById(simPaperId);
			String selUserId = selPaper.getTeacher();	//获取某个simPaperId对应下的userId
			if(selUserId.equals(userId) || level>1) {	//用户的id是否和contest表中的teacher一样  或者  是管理员
				response.setCode(0);
				response.setMsg("操作权限验证通过");
				return JSONObject.fromObject(response).toString();
			}else {	//无权限编辑题目内容
				response.setMsg("无法执行操作，请选择自己负责的试卷中的题目进行编辑");
				return JSONObject.fromObject(response).toString();
			}

		}else {
			response.setMsg("无权限，编辑失败");
			return JSONObject.fromObject(response).toString();
		}
	}


	/**
	 * @author zzs
	 * @param simId: simproblemId
	 * @description 跳转更新考试页面
	 * @return 考试视图、试卷信息、教师信息、考试信息
	 */
	@RequestMapping(value = "editsimproblem.do", method = { RequestMethod.POST,RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView editSimproblem(HttpServletRequest request, String simId) {
		//获取登录对象，判断其角色
		User user = (User)request.getSession().getAttribute("user");
		int level = user.getLevel(); //学生： 0  老师：1  管理员：2
		String userId = user.getUserId();

		String simType = request.getParameter("simType");
		ModelAndView mav = new ModelAndView();

		if ("单选题".equals(simType) || "多选题".equals(simType) ) {
			mav = teacherService.getOneChoiceMav(simId);
		}else if("判断题".equals(simType) || "填空题".equals(simType)) {
			mav = teacherService.getFillBlankAndJudgementMav(simId);
		}else if("简答题".equals(simType)) {
			mav = teacherService.getShortAnswerMav(simId);
		}

		return mav;
	}


	/**
	 * 更新通用题库题目的内容等信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "Teacher/updateSimpleProblem", method = { RequestMethod.POST,RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@SystemControllerLog(description = "更新通用题库题目的内容等信息")
	public String updateSimpleProblem(HttpServletRequest request) {
		LayResponse response = new LayResponse();
		response.setCode(1);
		//获取所登录用户的user对象
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int level = user.getLevel();
		if(level < 1) {
			response.setMsg("无操作权限");
			return JSONObject.fromObject(response).toString();
		}

		int simId = Integer.parseInt(request.getParameter("simId").trim());
		BigDecimal simScore = new BigDecimal(request.getParameter("simScore").trim());
		String simType = request.getParameter("simType").trim();
		String simContent = request.getParameter("simContent").trim();	// 内容去前后空格

		String result = "抱歉，题目未进行修改";		// 返回结果
		//根据题目类型进行不同方法调用和参数获取
		if("单选题".equals(simType) || "多选题".equals(simType)){
			int optionNum = Integer.parseInt(request.getParameter("optionNum"));
			int answerNum = Integer.parseInt(request.getParameter("answerNum"));

			// 根据答案和选项个数来进行处理
			List<String> optionList = new ArrayList<>();
			List<String> answerList = new ArrayList<>();
			for (int i=1; i<=optionNum; i++) {
				String opKey = "option" + i;
				String opContent = request.getParameter(opKey).trim();
				optionList.add(opContent);
			}

			for (int i=1; i<=answerNum; i++) {
				String anKey = "answer" + i;
				String anContent = request.getParameter(anKey).trim();
				answerList.add(anContent);
			}

			result = teacherService.updateChoiceQuestion(simId,simScore,simContent,optionList,answerList);
		} else if("判断题".equals(simType) || "填空题".equals(simType)){
			int answerNum = Integer.parseInt(request.getParameter("answerNum"));

			// 根据答案个数来进行处理
			List<String> answerList = new ArrayList<>();
			for (int i=1; i<=answerNum; i++) {
				String anKey = "answer" + i;
				String anContent = request.getParameter(anKey).trim();
				answerList.add(anContent);
			}
			result = teacherService.updateFillBlankAndJudgement(simId,simScore,simContent,answerList);
		} else {	// 简答题
			result = teacherService.updateShortAnswer(simId,simScore,simContent);
		}

		if("".equals(result)) {
			response.setCode(0);
			response.setMsg("题目修改成功");
		} else {
			response.setMsg(result);
		}
		return JSONObject.fromObject(response).toString();
	}


	/**
	 * @author zzs
	 * @description 跳转题目复用页面
	 * @return 视图、考试信息、教师信息
	 */
	@RequestMapping(value = "reuseSimproblem.do", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView prepareReuseSimproblem(HttpServletRequest request) {
		// 获取所属课程id
		int simCourseId = 0;
		if(request.getSession().getAttribute("course_id")!=null) {
			simCourseId = Integer.parseInt(request.getSession().getAttribute("course_id").toString());
		}

		//获取登录对象，判断其角色
		User user = (User)request.getSession().getAttribute("user");
		int level = user.getLevel(); //学生： 0  老师：1  管理员：2
		String userId = user.getUserId();
		String simId = request.getParameter("simId");
		
		// 管理员角色、教师角色
		ModelAndView mav = new ModelAndView();
		mav.addObject("simId", simId);
		mav.addObject("paper",contestpaperService.selReusePaper(user.getUserId(), simCourseId));
		mav.setViewName("simple_problem-reuse.jsp");
		return mav;
		
	}


	/**
	 * 复用通用题库题目等信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "Teacher/reuseSimproblem", method = { RequestMethod.POST,RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@SystemControllerLog(description = "复用通用题库题目")
	public String reuseSimproblem(HttpServletRequest request) {
		LayResponse response = new LayResponse();
		response.setCode(1);
		//获取所登录用户的user对象
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int level = user.getLevel();
		if(level < 1) {
			response.setMsg("无操作权限");
			return JSONObject.fromObject(response).toString();
		}

		String simIdStr = request.getParameter("simId");		// 逗号分隔的字符串
		String paperIdStr = request.getParameter("paperId");
		
		if(paperIdStr == null || "".equals(paperIdStr)) {
			response.setMsg("请选择试卷后，再进行添加");
			return JSONObject.fromObject(response).toString();
		}
		
		int result = teacherService.reuseSimproblem(simIdStr,paperIdStr);

		if(result > 0) {
			response.setCode(0);
			response.setMsg("请求成功");
			HashMap paperMap = new HashMap();
			paperMap.put("paperId",paperIdStr);
			response.setData(paperMap);	// 	paperIdStr 用来重新搞试卷里的题目pos
			return JSONObject.fromObject(response).toString();
		}

		response.setMsg("复用题目失败");
		return JSONObject.fromObject(response).toString();
	}


	/**
	 * @author zzs
	 * @description 跳转批改题目页面
	 * @return 批改题目视图
	 */
	@RequestMapping(value = "correct.do", method = { RequestMethod.POST,RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView prePareCorrect(HttpServletRequest request) {
		int cStatusId = Integer.parseInt(request.getParameter("cStatusId"));
		int cStatus = Integer.parseInt(request.getParameter("cStatus"));		// 考试状态是否已经批改

		int type = 5; //简答题
		List<Map<String,Object>> correctList = teacherService.selSolutionSimproblemByTypeAndcStatusId(type,cStatusId);

		ModelAndView mav = new ModelAndView();

		mav.addObject("correctObjects", correctList);
		mav.addObject("cStatusId",cStatusId);
		mav.addObject("cStatus",cStatus);
		if(correctList.isEmpty()) {
			mav.setViewName("error.html");		// 表示没有需要手动批改的题目
			return mav;
		}
		mav.setViewName("simple_problem-correct.jsp");
		return mav;

	}


	/**
	 * 手动批改通用题库题目
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "Teacher/correctSimpleProblem", method = { RequestMethod.POST,RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@SystemControllerLog(description = "手动批改通用题库题目")
	public String correctSimpleProblem(HttpServletRequest request) {
		LayResponse response = new LayResponse();
		response.setCode(1);
		//获取所登录用户的user对象
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int level = user.getLevel();
		if(level < 1) {
			response.setMsg("无操作权限");
			return JSONObject.fromObject(response).toString();
		}

		String simCountStr = request.getParameter("simCount");		// 题目数量
		int cStatusId = Integer.parseInt(request.getParameter("cStatusId"));
		int cStatus= Integer.parseInt(request.getParameter("cStatus"));
		//System.out.println("cStatus---------"+cStatus);

		if(simCountStr == null || "".equals(simCountStr)) {
			response.setMsg("此试卷无需手动批改");
			return JSONObject.fromObject(response).toString();
		}

		int simCount = Integer.parseInt(simCountStr);

		Map<String,Object> realScoreMap = new HashedMap();
		double oldScoreSum = 0.0;
		double realScoreSum = 0.0;

		// 获取教师评分分数 和 作答Id
		for(int i=1; i<=simCount; i++) {
			String soluId = request.getParameter("solutionId"+i);
			String oldSocre = request.getParameter("oldScore"+i);
			String realScore = request.getParameter("realScore"+i);
			realScoreMap.put(soluId,realScore);
			if(oldSocre == null || "".equals(oldSocre)) {
				oldSocre = "0.0";
			}
			oldScoreSum += Double.parseDouble(oldSocre);
			realScoreSum += Double.parseDouble(realScore);
		}

		int result = teacherService.correctSimpleProblem(cStatusId,cStatus,realScoreMap,oldScoreSum,realScoreSum);
		if(result > 0) {
			response.setCode(0);
			response.setMsg("请求成功");
		}else {
			response.setMsg("分数更新失败，请重新操作");
		}

		return JSONObject.fromObject(response).toString();
	}
}
