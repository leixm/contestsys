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
import com.code.model.Answer;
import com.code.model.Contest;
import com.code.model.LayResponse;
import com.code.model.OnePaper;
import com.code.model.OneProblem;
import com.code.model.OneSimproblem;
import com.code.model.Options;
import com.code.model.Response;
import com.code.model.ScoreExcel;
import com.code.model.ScoreObject;
import com.code.model.UrlData;
import com.code.model.User;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
public class TeacherController {
	@Resource
	private TeacherService teacherService;
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
		
		System.out.println(JSONObject.fromObject(teacherService.selAllpaper(user)).toString());
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
	public String addNewpaper(@RequestBody String resp ,HttpServletRequest request,HttpServletResponse response) {


		User user = new User();
		String userId = "123";
		String basePath = PathHelper.getNormativePath(request);
		//测试
		user.setLevel(1);
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
		System.out.println(resp);
		List<OneSimproblem> oneSimps = newpaper.getSimp();
		return JSONObject.fromObject(teacherService.addNewpaper(newpaper,user,oneSimps,basePath)).toString();
	}
	
	
	
	/**
	 * 添加新考试
	 * @param request
	 * @param response
	 * @return 0添加考试失败  1添加考试成功  -1权限不足
	 */
	@RequestMapping(value="Teacher/addContest",method={RequestMethod.POST},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addContest(HttpServletRequest request,HttpServletResponse response) throws ParseException {
		
		String startTimeStr = request.getParameter("starttime");
		String endTimeStr = request.getParameter("endtime");
		Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTimeStr);
		Date endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTimeStr);
		int paperId = Integer.parseInt(request.getParameter("paperid"));
		String title = request.getParameter("title");
		
		
//		User user = (User)request.getSession().getAttribute("user");
//		String userId = user.getUserId();
		
		//测试
		User user = new User();
		String userId = "123";
		user.setLevel(1);
		user.setUserId(userId);
		
		//存数据进实体
		Contest contest = new Contest();
		contest.setContestId(6);
		contest.setEndtime(endTime);
		contest.setStarttime(startTime);
		contest.setPaperId(paperId);
		contest.setTeacher(userId);
		contest.setTitle(title);
		System.out.println(contest.getEndtime());
		System.out.println(contest.getTitle());
		//String className = request.getParameter("classname");
		String className = "软件一班";
		return JSONObject.fromObject(teacherService.addContest(contest, className)).toString();
		
	}
	
	
	
	
	/**
	 * 教师查询所有考试Status
	 * @return Response
	 * 
	 */
	@RequestMapping(value="Teacher/selContestStatus",method={RequestMethod.POST},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String selContestStatus(HttpServletRequest request,HttpServletResponse response) {
		//User user = (User)request.getSession().getAttribute("user");
		//测试
		User user = new User();
		user.setUserId("123");
		user.setLevel(1);

		return JSONObject.fromObject(teacherService.selContestStatus(user)).toString();
	}
	
	/**
	 *  查询全级学生分数
	 * 
	 */
	@RequestMapping(value="Teacher/selGradeScore",method={RequestMethod.POST},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String selGradeScore(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//从session获取信息
		/*User user = (User)request.getSession().getAttribute("user");
		Contest contest = (Contest)request.getSession().getAttribute("contest");*/
		//捏数据测试
		User user = new User();
		user.setLevel(1);
		Contest contest = new Contest();
		contest.setContestId(1014);
		contest.setTitle("期中考试");
		contest.setPaperId(129);
		contest.setTeacher("123");

		//返回规定格式给前端
		JSONObject obj = new JSONObject();
		JSONArray arr = JSONArray.fromObject(teacherService.selGradeScore(user,contest));
		obj.put("code", 0);
		obj.put("msg", "返回成功");
		obj.put("count", arr.size());
		obj.put("data", arr);
		return obj.toString();
	}
	
	
	/**
	 * 导出年级成绩表
	 * 解析：从数据库表获取组建表格需要的List<ScoreExcel>对象，然后通过Excel导出工具导出workbook对象到服务器，在利用download从上边下载到本地即可
	 * @param user	具体老师
	 * @param contest 具体考试
	 * @return 
	 * @return 导出结果
	 */
	@RequestMapping(value="Teacher/gradeScoreExcel",produces="text/html;charset=UTF-8")
	@ResponseBody
	protected String exportGradeScoreExcel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LayResponse resp = new LayResponse();
		resp.setCode(1); //默认设置失败code
		User user = (User)request.getSession().getAttribute("user");
		System.out.println("level------------"+user.getLevel());
		if(user.getLevel()==0) {
			resp.setMsg("该用户没有导出excel表格权限");
			return JSONObject.fromObject(resp).toString();
		}
		/*----获取导出的数据集开始----*/
		String className = request.getParameter("classname"); 
		String stuId = request.getParameter("stuid");
		String stuName = request.getParameter("stuname");
		String contestName = request.getParameter("contestname");
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
		//数据库查询返回的学生成绩结果集
		List<Map<String,Object>> resultList = teacherService.selStuScore(className, stuId, stuName, contestName,pageSize,pageNumber); 
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
				scoreExcel.setContestNname(map.get("contestname").toString());
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
			System.out.println("excel导出成功！");
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
	
	/* 测试代码
	@RequestMapping(value="Teacher/selAllContest",method={RequestMethod.POST},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String selAllContest(HttpServletRequest request,HttpServletResponse response) {
		//User user = (User)request.getSession().getAttribute("user");
				User user = new User();
				user.setUserId("123");
		
		return JSONObject.fromObject(teacherService.selAllContest(user)).toString();
	}
	
	@RequestMapping(value="Teacher/ajax",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String ajax(HttpServletRequest request,HttpServletResponse response) {
			
		//设置字符编码格式
		//req.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//首先获得客户端发送来的数据(keyword)
		String keyword = request.getParameter("keyword");
		//获得关键字之后进行处理，得到关联数据
		List<String> listData = getData(keyword);
		
		//System.out.println(JSONArray.fromObject(listData));
		//JSONArray.fromObject(listData);
		//返回json格式
		//resp.getWriter().write(JSONArray.fromObject(listData).toString());	
		
		JSONObject a = new JSONObject();
		a.put("name", "你好");
		JSONObject b = new JSONObject();
		b.put("data", a);
		
		return JSONArray.fromObject(listData).toString();
	}
	
	public List<String> getData(String keyword){
		//定义一个容器,存放模拟数据
		List<String> datas = new ArrayList<String>();
			datas.add("ajax");
			datas.add("ajax提交form表单");
			datas.add("ajax教程");
			datas.add("baidu");
			datas.add("bt");
			datas.add("byte");
		List<String> list = new ArrayList<String>();
		for (String data : datas) {
			if(data.contains(keyword)){
				list.add(data);
			}
		}
		return list;
	}*/
	
	/**
	 * 根据搜索条件模糊查询出学生成绩表
	 * @param request
	 * @param response
	 * @return 返回查询到的学生成绩等相关信息
	 */
	@RequestMapping(value="Teacher/selStuScore",method={RequestMethod.POST},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String selStuScore(HttpServletRequest request,HttpServletResponse response) {
		//获取user对象
	/*	HttpSession session = request.getSession(); 
		User user = (User)session.getAttribute("user");		
		System.out.println("userName:----"+user.getUserId());*/
		
		LayResponse layResp = new LayResponse();//layui参数返回格式
		layResp.setCode(1); //默认设置为1
		
		String className = request.getParameter("classname"); 
		String stuId = request.getParameter("stuid");
		String stuName = request.getParameter("stuname");
		String contestName = request.getParameter("contestname");
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
		//获取分页所需相关数据
		String pageSize = request.getParameter("limit"); //一页多少个
		String pageNumber = request.getParameter("page");	//第几页
		List<Map<String,Object>> resultList = teacherService.selStuScore(className, stuId, stuName, contestName,pageSize,pageNumber); //数据库查询返回的学生成绩结果集
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
	public String updateScore(HttpServletRequest request,String score,String cstatusid) {
		LayResponse response = new LayResponse();
		response.setCode(1);  
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
		List<Map<String,Object>> classList = teacherService.selAllClassObj();
		List<String> classNameList = new ArrayList<String>();
		List<String> classIdList = new ArrayList<String>();
		
		if(classList.size() > 0) {
			for (Map<String, Object> classObj : classList) {
				System.out.println("classObj---"+classObj);
				classNameList.add(classObj.get("name").toString());
				classIdList.add(classObj.get("class_id").toString());
			}
		}
		System.out.println(classNameList);
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
		System.out.println("contest----"+contestList);
		resultMap.put("contestname", contestNameList);
		resultMap.put("msg", "查询成功");
		return JSONObject.fromObject(resultMap).toString();
	}
	
	/**
	 * 测试
	 */
	@RequestMapping(value = "Teacher/selAjaxData", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String selAjaxData(HttpServletRequest request) {
		String keyword = request.getParameter("name");
		String result = keyword + ",你好ajax,前端后端,大数据,数据分析";
		return result;  
	}
	
	/**
	 * 查询所有的班级的所有考试的平均分
	 * @return 按照Echars所需要的格式返回JSON格式的参数 
	 */
	@RequestMapping(value = "Teacher/selClassContestAVG", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String selClassContestAVG(HttpServletRequest request) {
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
		List<Map<String,Object>> tempList = teacherService.selAllClassObj();
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
			System.out.println("seriesList----"+seriesList);
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
					System.out.println("classAvgList-----"+classAvgList);
				}
				allAvgList.add(classAvgList);
				System.out.println("allAvgList-----"+allAvgList);
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
		List<Map<String,Object>> tempList = teacherService.selAllClassObj();
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
				System.out.println("allAvgList-----"+allMaxScoreList);
			}
		}
		resultMap.put("series", seriesList);
		resultMap.put("dataset", allMaxScoreList);
		resultMap.put("msg", "请求成功");
		resultMap.put("status", 1);
		return JSONObject.fromObject(resultMap).toString();
	}
	
}
