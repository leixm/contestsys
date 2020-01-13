/** 
 * @author: lxm
 * @create_date: 2019.5.3
 * @description: 用户管理 
 * */
package com.app.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.app.service.impl.ClassService;
import com.app.service.impl.UserService;
import com.app.tools.PathHelper;
import com.app.tools.RandomValidateCode;
import com.code.model.Course;
import com.code.model.LayResponse;
import com.code.model.Response;
import com.code.model.User;
import com.code.model.loginUser;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class UserController {

	@Resource
	private UserService userService;
	
	@Resource  
	private ClassService classService;
	
	/**
	 * @author lxm
	 * @description 用户登录
	 * @return 响应状态
	 */
	@RequestMapping(value = "User/Login", method = { RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String Login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Response re = new Response();
		re.setSuccess(0);
		String userName = request.getParameter("username");		//userName相当于userId
		String passWord = request.getParameter("password");
		String rancode = request.getParameter("rancode");
		if (userName == null || passWord == null) {
			re.setMsg("参数错误");
			return JSONObject.fromObject(re).toString();
		}
		if (rancode == null || !ValidateRanCode(request, rancode)) {
			re.setMsg("验证码错误");
			return JSONObject.fromObject(re).toString();
		} 
        
		int state = userService.Login(userName, passWord);
	//	System.out.println("###username=" + userName + " password=" + passWord + " rancode=" + rancode + " state=" + state);
		if (state < 0) {
			re.setMsg("账号状态异常");
		} else if (state == 0) {
			re.setMsg("账号/邮箱不存在");
		} else if (state == 1) {
			re.setMsg("密码错误");
		} else { 
			request.getSession().setAttribute("user", userService.getUser(userName));
			re.setMsg("登录成功");
			re.setSuccess(1);
			re.setType(state);
		} 
		System.out.println(JSONObject.fromObject(re).toString());
		return JSONObject.fromObject(re).toString();
	}
	
	/**
	 * @author lxm
	 * @description 用户注册
	 * @return 响应状态
	 */
	@RequestMapping(value = "User/Register", method = {RequestMethod.POST })
	@ResponseBody
	public String Register(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		User user = new User();
		user.setPassword(request.getParameter("password"));
		user.setUserId(request.getParameter("userId"));
		user.setEmail(request.getParameter("email"));
		user.setRealname(request.getParameter("realname"));
		/*System.out.println("passuserid-----"+user.getUserId());
		System.out.println("password-----"+user.getPassword());
		System.out.println("passrancode-----"+request.getParameter("rancode"));
		System.out.println("email-----"+request.getParameter("email"));*/
		Response re = new Response();
		re.setSuccess(0);
		String rancode = request.getParameter("rancode");
		if (user == null || user.getUserId() == null || user.getPassword() == null || user.getUserId() == null) {
			re.setMsg("参数错误");
			return JSONObject.fromObject(re).toString();
		}
		if (rancode == null || !ValidateRanCode(request, rancode)) {
			re.setMsg("验证码错误");
			return JSONObject.fromObject(re).toString();
		}
		String result = userService.Register(user);
		if (result.equals("注册成功")) {
			re.setSuccess(1);
		}
		re.setMsg(result);
		return JSONObject.fromObject(re).toString();
	}
	
	/**
	 * @author lxm
	 * @description 激活用户
	 * @return 响应状态
	 */
	@RequestMapping(value = "User/Validate", method = { RequestMethod.POST,RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String Validate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String email = request.getParameter("email");
		String validateCode = request.getParameter("validatecode");
		if (email == null || validateCode == null) {
			return "激活失败";
		}
		
		String result = userService.ProcessActivate(email, validateCode);
		if (result.equals("激活成功"))
        return result;
		 
		return "激活失败";

	}
	
	/**
	 * @author lxm
	 * @description 请求重置密码
	 * @return 响应状态
	 */
	@RequestMapping(value = "User/ApplyResetPassword", method = {
			RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String ApplyResetPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Response re = new Response();
		re.setSuccess(0);
		String email = request.getParameter("email");
		String rancode = request.getParameter("rancode");
		if (rancode == null || !ValidateRanCode(request, rancode)) {
			re.setMsg("验证码错误");
			return JSONObject.fromObject(re).toString();
		}
		if (email == null) {
			re.setMsg("请输入邮箱地址");
			return JSONObject.fromObject(re).toString();
		}
		String result = userService.ApplyResetPassword(email);
		if (result.equals("申请成功"))
			re.setSuccess(1);
		re.setMsg(result);  
		System.out.println(JSONObject.fromObject(re).toString());
		return JSONObject.fromObject(re).toString();
	}
	
	/**
	 * @author lxm
	 * @description 重置密码
	 * @return 响应状态
	 */
	@RequestMapping(value = "User/ResetPassword", method = { RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String ResetPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Response re = new Response();
		re.setSuccess(0);
		String email = request.getParameter("email");
		String newPassword = request.getParameter("newpassword");
		String validateCode = request.getParameter("validatecode");
		if (email == null || newPassword == null || validateCode == null) {
			re.setMsg("参数错误");
			return JSONObject.fromObject(re).toString();
		}
		String result = userService.ResetPassword(email, newPassword, validateCode);
		if (result.equals("密码重置成功"))
			re.setSuccess(1);
		re.setMsg(result);
		System.out.println(JSONObject.fromObject(re).toString());
		return JSONObject.fromObject(re).toString();
	}
	
	/**
	 * @author lxm
	 * @description 获取验证码
	 * @return 验证码图片
	 */
	@RequestMapping(value = "User/GetRanCode", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void GetRanCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RandomValidateCode ran = new RandomValidateCode();
		ran.getRandcode(request, response);
	}
	
	/**
	 * @author lxm
	 * @description 校对验证码
	 * @return 响应状态
	 */
	boolean ValidateRanCode(HttpServletRequest request, String ranCode) {
		String key = (String) request.getSession().getAttribute("randomcode_key");
		if (key == null)
			return false;
		if (ranCode.toLowerCase().equals(key.toLowerCase())) {
			request.getSession().removeAttribute("randomcode_key");
			return true;
		}

		return false;
	}
	
	/**
	 * @author lxm
	 * @param keyword: 关键字
	 * @description 查询学生信息
	 * @return 学生实例列表
	 */
	@RequestMapping(value = "User/GetAllStudents", method = { RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String GetAllStudents(HttpServletRequest request, HttpServletResponse response, String Keyword)
			throws Exception {
		//获取分页所需相关数据
		String pageSize = request.getParameter("limit"); //一页多少个
		String pageNumber = request.getParameter("page");	//第几页
		
		List resultList =  userService.GetAllStudents(Keyword,pageSize,pageNumber);
		//获取分页插件的数据只能通过PageInfo来获取
		PageInfo pInfo = new PageInfo(resultList);
		Long total = pInfo.getTotal();
		
		JSONObject obj = new JSONObject();
		JSONArray arr = JSONArray.fromObject(resultList);
	
		obj.put("code", 0);
		obj.put("msg", "返回成功");
		obj.put("count", total.intValue());
		obj.put("data", arr);
		return obj.toString();
	}
	

	/**
	 * @author lxm
	 * @description 跳转至添加学生页面
	 * @return 视图、班级信息
	 */
	@RequestMapping(value = "addstudent.do", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView PrepareAddUser(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("classes", classService.GetAllClass("root",null,null,null));
		mav.setViewName("student-add.jsp");
		return mav; 
	}
	
	/**
	 * @author lxm
	 * @param user: 学生实例
	 * @description 添加学生
	 * @return 响应状态 
	 */
	@RequestMapping(value = "User/AddStudent", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String AddUser(HttpServletRequest request, User user) {
		System.out.println("AddUser!!!!!!\n" + JSONObject.fromObject(user).toString());
		LayResponse response = new LayResponse();
		response.setCode(1);
		if (userService.AddUser(user) > 0) {
			response.setCode(0);
			return JSONObject.fromObject(response).toString();
		}
		response.setMsg("添加失败");
		return JSONObject.fromObject(response).toString();
	}
	
	/**
	 * @author lxm
	 * @param id: 学生id
	 * @description 更新学生信息
	 * @return 视图、班级信息、学生信息
	 */
	@RequestMapping(value = "editstudent.do", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView EditUser(HttpServletRequest request, String id) {

		ModelAndView mav = new ModelAndView();
		mav.addObject("user", userService.getUser(id));
		mav.addObject("classes", classService.GetAllClass("root",null,null,null));
		mav.setViewName("student-edit.jsp");
		return mav;
	}
	
	/**
	 * @author lxm
	 * @param user:用户实例
	 * @description 更新用户信息
	 * @return 响应状态
	 */
	@RequestMapping(value = "User/UpdateUser", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String UpdateUser(HttpServletRequest request, User user) {
		System.out.println(JSONObject.fromObject(user).toString());
		LayResponse response = new LayResponse();
		response.setCode(1);  
		if(user.getPassword().trim().isEmpty())
			user.setPassword(null);
		if (userService.UpdateUser(user) > 0) {
			response.setCode(0);
			return JSONObject.fromObject(response).toString();
		} else {
			response.setMsg("更新失败");
			return JSONObject.fromObject(response).toString();
		}
	}
	
	/**
	 * @author lxm
	 * @param id: 用户id
	 * @description 删除用户
	 * @return 响应状态
	 */
	@RequestMapping(value = "User/DeleteUser", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String DeleteUser(HttpServletRequest request, String id) {

		LayResponse response = new LayResponse();
		response.setCode(1);
		if (userService.DeleteUser(id) > 0) {
			response.setCode(0);
			return JSONObject.fromObject(response).toString();
		} else {
			response.setMsg("更新失败");
			return JSONObject.fromObject(response).toString();
		}
	} 
	
	/**
	 * @author lxm
	 * @param ids: 用户id列表
	 * @description 批量删除用户
	 * @return 响应状态
	 */
	@RequestMapping(value = "User/DelAll", consumes = "application/json", produces = "text/html;charset=UTF-8", method = {RequestMethod.POST })
	@ResponseBody
	public String DelAll(HttpServletRequest request, @RequestBody List<String> ids) {

		System.out.println(JSONArray.fromObject(ids).toString());
		  LayResponse response = new LayResponse(); response.setCode(1);
		  if(userService.DeleteAllUser(ids)>0){
		   response.setCode(0); 
		   System.out.println(JSONObject.fromObject(response).toString());
		   return JSONObject.fromObject(response).toString(); 
		   }
		  else{
		  response.setMsg("删除失败"); 
		  return JSONObject.fromObject(response).toString(); }
		 
	}
	
	/**
	 * @author lxm
	 * @param Keyword: 关键字
	 * @description 查询教师
	 * @return 教师实例列表
	 */
	@RequestMapping(value = "User/GetAllTeachers", method = { RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String GetAllTeachers(HttpServletRequest request, HttpServletResponse response, String Keyword)
			throws Exception {
		//获取分页所需相关数据
		String pageSize = request.getParameter("limit"); //一页多少个
		String pageNumber = request.getParameter("page");	//第几页
		
		List resultList =  userService.GetAllTeachers(Keyword,pageSize,pageNumber);
		//获取分页插件的数据只能通过PageInfo来获取
		PageInfo pInfo = new PageInfo(resultList);
		Long total = pInfo.getTotal();
		
		JSONObject obj = new JSONObject();
		JSONArray arr = JSONArray.fromObject(resultList);
		
		obj.put("code", 0);
		obj.put("msg", "返回成功");
		obj.put("count", total.intValue());
		obj.put("data", arr);
		return obj.toString();
	}
	
	/**
	 * @author lxm
	 * @param user: 用户实例
	 * @description 添加教师
	 * @return 响应状态
	 */
	@RequestMapping(value = "User/AddTeacher", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String AddTeacher(HttpServletRequest request, User user) {
		LayResponse response = new LayResponse();
		response.setCode(1);
		user.setLevel(new Integer(1));
		if (userService.AddUser(user) > 0) {
			response.setCode(0);
			return JSONObject.fromObject(response).toString();
		}
		response.setMsg("添加失败");
		return JSONObject.fromObject(response).toString();
	}
	
	/**
	 * @author lxm
	 * @description 跳转至添加教师页面
	 * @return 视图
	 */
	@RequestMapping(value = "addteacher.do", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView PrepareAddTeacher(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("teacher-add.jsp");
		return mav;
	}
	
	/**
	 * @author lxm
	 * @param id: 用户id
	 * @description 跳转至更新教师页面
	 * @return 视图、用户信息
	 */
	@RequestMapping(value = "editteacher.do", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView EditTeacher(HttpServletRequest request, String id) {

		ModelAndView mav = new ModelAndView();
		mav.addObject("user", userService.getUser(id));
		mav.setViewName("teacher-edit.jsp");
		return mav;
	}
	
	/**
	 * @author lxm
	 * @description 上传文件
	 * @return url信息
	 */
	@RequestMapping(value = "upload/fileUpload", method = RequestMethod.POST)
	@ResponseBody
	public void fileUpload(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("file upload!");
		// 文件保存目录路径
		String savePath = PathHelper.getNormativePath(request,"upload/");

		// 文件保存目录URL
		String saveUrl = request.getContextPath() + "/upload/";

		// 定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");

		// 最大文件大小
		long maxSize = 1000000;

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();

			if (!ServletFileUpload.isMultipartContent(request)) {
				out.println(getError("请选择文件。"));
				return;
			}
			// 检查目录
			File uploadDir = new File(savePath);
			if (!uploadDir.isDirectory()) {
				uploadDir.mkdirs();
			}
			// 检查目录写权限
			if (!uploadDir.canWrite()) {
				out.println(getError("上传目录没有写权限。"));
				return;
			}

			String dirName = "image";
			if (!extMap.containsKey(dirName)) {
				out.println(getError("目录名不正确。"));
				return;
			}
			// 创建文件夹
			savePath += dirName + "/";
			saveUrl += dirName + "/";
			File saveDirFile = new File(savePath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String ymd = sdf.format(new Date());
			savePath += ymd + "/";
			saveUrl += ymd + "/";
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}

			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> itr = multiRequest.getFileNames();
			while (itr.hasNext()) {
				// 取得上传文件 (遍历)
				MultipartFile file = multiRequest.getFile(itr.next());
				if (file != null) {
					// 取得当前上传文件的文件名称
					String fileName = file.getOriginalFilename();

					// 检查文件大小
					if (file.getSize() > maxSize) {
						out.println(getError("上传文件大小超过限制。"));
						return;
					}
					// 检查扩展名
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					if (!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)) {
						out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
						return;
					}

					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
					try {
						File uploadedFile = new File(savePath, newFileName);
						file.transferTo(uploadedFile);
					} catch (Exception e) {
						out.println(getError("上传文件失败。"));
						return;
					}

					JSONObject obj = new JSONObject();
					obj.put("error", 0);
					obj.put("url", saveUrl + newFileName);
					out.println(obj.toString());
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * @author zzs
	 * @description 获取用户名
	 * @return 用户名
	 */
	@RequestMapping(value = "User/getUsername", method = {
			RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getUsername(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取所登录用户的user对象
				HttpSession session = request.getSession(); 
				User user = (User)session.getAttribute("user");	
				String className = "";
				loginUser logUser = new loginUser();
				if(user!=null) { 
					if(user.getLevel()==0) {	//判断是学生才需要加班级
						com.code.model.Class classModel = classService.GetClass(user.getClassId().toString());
						className = classModel.getName();
						logUser.setClassId(user.getClassId());
						logUser.setClassName(className);
					}
					logUser.setEmail("");
					logUser.setLevel(user.getLevel());
					logUser.setPassword("");
					logUser.setRealname(user.getRealname());
					logUser.setUserId(user.getUserId());
				}
				return JSONObject.fromObject(logUser).toString();
	}
	
	/**
	 * @author zzs
	 * @description 将用户选择的系统课程类型存入session
	 * @return 
	 */
	@RequestMapping(value = "User/changeSystemCourse", method = {
			RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String saveSystemCourseToSession(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String course_id = request.getParameter("course_id");
		String course_name = request.getParameter("course_name");
		//System.out.println("course_id--------"+course_id);
		//获取所登录用户的user对象
		HttpSession session = request.getSession(); 
	    session.setAttribute("course_id",course_id);  
	    session.setAttribute("course_name",course_name);  
	    //自己把SessionID保存在cookie中  ，即使浏览器禁止了cookie也能用session（！！！！！）
	    Cookie cookie=new Cookie("JSESSIONID", session.getId());  
	    //设置cookie保存时间  
	    cookie.setMaxAge(60*20);  
	    //被创建的cookie返回浏览器  
	    response.addCookie(cookie);  
	    
	    //System.out.println("sessionCourseId_----"+session.getAttribute("course_id"));
	    
		resultMap.put("msg", "请求成功！");
		resultMap.put("status", 0);
		return JSONObject.fromObject(resultMap).toString();
	}
	
	/**
	 * @author zzs
	 * @description 获取所有/某位教师所任课的课程列表
	 * @return 
	 */
	@RequestMapping(value = "User/getSelectCourse", method = {
			RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getSelectCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LayResponse layResp = new LayResponse();//layui参数返回格式
		layResp.setCode(1); //默认设置为1
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		
		//获取所登录用户的user对象
		HttpSession session = request.getSession(); 
	    User user = (User)session.getAttribute("user");
	    int level = user.getLevel();
	    String userId = user.getUserId().toString();
	    String type = request.getParameter("type"); // 当type为list时，为查询列表，需要所有的包括老师的课程信息
		if("list".equals(type)&&"root".equals(userId)) {
			userId = "";
		}

	    String keyword = request.getParameter("keyword");
		//获取分页所需相关数据
		String pageSize = request.getParameter("limit"); //一页多少个
		String pageNumber = request.getParameter("page");	//第几页

	    if(level > 0) {		//管理员或教师
	    	//userId为参，null的时候默认查全部的课程，即管理员角色
	    	resultList = userService.selCourseNameByTeacherId(userId,keyword,pageSize,pageNumber);
	    }else {
			layResp.setCode(0);
			layResp.setMsg("抱歉，权限不足！");
			return JSONObject.fromObject(layResp).toString();
	    }

		//获取分页插件的数据只能通过PageInfo来获取
		PageInfo pInfo = new PageInfo(resultList);
		Long total = pInfo.getTotal();

	    Map<String,Object> resultMap = new HashMap<String, Object>();
	    //查看session是否有已选择的课程，有的话传回前端
	    String courseId = (String)session.getAttribute("course_id");
	    String courseName = (String)session.getAttribute("course_name");
	    //System.out.println("courseObj+++"+courseId+"====name==="+courseName);
	    if(courseId != null && courseName != null) {
	    	resultMap.put("select_id", courseId);
	    	resultMap.put("select_name", courseName);
	    }
	    resultMap.put("dataList", resultList);


		layResp.setCode(0);
		layResp.setMsg("请求成功！");
		layResp.setData(resultMap);
		layResp.setCount(total.intValue());
		return JSONObject.fromObject(layResp).toString();
	}
	
	
	/**
	 * @author lxm
	 * @description 跳转添加课程页面
	 * @return 视图及教师实例列表
	 */
	@RequestMapping(value = "addCourse.do", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView PrepareAddCourse(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("course-add.jsp");
		return mav;
	}
	
	/**
	 * @author zzs
	 * @description 跳转添加任课教师页面
	 * @return 视图
	 */
	@RequestMapping(value = "addCourseTeach.do", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView preAddTeach(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		//获取登录对象，判断其角色
		User user = (User)request.getSession().getAttribute("user");
		String userId = user.getUserId().toString();
		int level = user.getLevel();
		
		if(level == 2) {
			 mav.addObject("teachers",userService.getAllTeacher());
		}else {
			 mav.addObject("teachers",userService.getTeacherById(userId));
		}
		mav.addObject("courses", userService.selCourseNameByTeacherId("root", null, null, null));
		mav.setViewName("course-addTeach.jsp");
		return mav;
	}
	
	/**
	 * @author zzs
	 * @param cla: 班级信息
	 * @description 添加任课关系
	 * @return 响应状态
	 */
	@RequestMapping(value = "Course/addTeach", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String AddTeach(HttpServletRequest request) {
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
		
		String courseId = request.getParameter("courseId"); 
		String teacherId = request.getParameter("teacherId");
		if(courseId != null && !"".equals(courseId) && teacherId != null && !"".equals(teacherId)) {
			int resultNum = userService.addTeach(courseId,teacherId);
			if(resultNum > 0) {
				response.setCode(0);
				response.setMsg("添加成功!成功增加"+ resultNum +"条记录");
				return JSONObject.fromObject(response).toString();
			}else {
				response.setMsg("添加失败,所选任课教师可能已存在");
				return JSONObject.fromObject(response).toString();
			}
		}
		response.setMsg("添加失败,请选择班级/任课教师");
		return JSONObject.fromObject(response).toString();
	}
	
	/**
	 * @author zzs
	 * @description 添加课程
	 * @return 响应状态
	 */
	@RequestMapping(value = "Course/addCourse", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String AddCourse(HttpServletRequest request) {
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
		String courseName = request.getParameter("courseName");
		if(courseName==null || "".equals(courseName)) {
			response.setMsg("课程名称不能为空！");
			return JSONObject.fromObject(response).toString();
		}
		courseName = courseName.replaceAll(" ", "");	//去除空格
		int result = userService.addCourse(courseName);
		if ( result == 1) {		//返回0表示插入失败，返回1成功，返回2表示班级已存在无法
			response.setCode(0);
			response.setMsg("添加成功!");
			return JSONObject.fromObject(response).toString();
		}else if(result == 2) {
			response.setMsg("班级已存在");
			return JSONObject.fromObject(response).toString();
		}
		response.setMsg("添加失败");
		return JSONObject.fromObject(response).toString();
	}
	
	/**
	 * @author zzs
	 * @param courseId: 课程id
	 * @description 跳转更新课程页面
	 * @return 视图、班级信息、教师信息
	 */
	@RequestMapping(value = "editCourse.do", method = { RequestMethod.POST,RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView editCourse(HttpServletRequest request, String courseId) {
		ModelAndView mav = new ModelAndView();
		//System.out.println(JSONObject.fromObject(classService.GetClass(id)).toString());
		List<Map<String,Object>> courseList = userService.selCourseObjById(courseId);
		int cId = Integer.parseInt(courseList.get(0).get("course_id").toString());
		String cName = courseList.get(0).get("course_name").toString();
		
		Course cour = new Course();
		cour.setCourseId(cId);
		cour.setCourseName(cName);
		
		mav.addObject("courses",cour);
		mav.setViewName("course-edit.jsp");
		return mav;
	}
	
	/**
	 * @author zzs
	 * @param cla: 班级id
	 * @description 更新班级
	 * @return 响应状态
	 */
	@RequestMapping(value = "Course/updateCourse", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateCourse(HttpServletRequest request) {
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
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		String courseName = request.getParameter("courseName").replaceAll(" ", "");
		System.out.println("course"+courseId+"3213"+courseName);
		
		if (userService.updateCourse(courseId,courseName) > 0) {
			response.setMsg("更新成功");
			response.setCode(0);
			return JSONObject.fromObject(response).toString();
		} else {
			response.setMsg("更新失败");
			return JSONObject.fromObject(response).toString();
		}
	}
	
	/**
	 * @author zzs
	 * @param id: 班级id
	 * @description 删除单个课程
	 * @return 响应状态
	 */
	@RequestMapping(value = "Course/deleteCourse", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deleteCourse(HttpServletRequest request, String id) {
		
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
		
		if (userService.deleteCourse(id) > 0) {
			response.setCode(0);
			return JSONObject.fromObject(response).toString();
		} else {
			response.setMsg("更新失败");
			return JSONObject.fromObject(response).toString();
		}
	}
	
	/**
	 * @author zzs
	 * @param classId,teacherId
	 * @description 移除任课关系
	 * @return 响应状态
	 */
	@RequestMapping(value = "Course/deleteTeach", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deleteTeach(HttpServletRequest request, String courseId, String teacherId) {
		
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
				
		if (userService.deleteTeach(courseId,teacherId) > 0) {
			response.setCode(0);
			return JSONObject.fromObject(response).toString();
		} else {
			response.setMsg("更新失败");
			return JSONObject.fromObject(response).toString();
		}
	}
	
	/**
	 * @author zzs
	 * @param ids: 课程id列表
	 * @description 删除多个k课程
	 * @return 响应状态
	 */
	@RequestMapping(value = "Course/delAll", consumes = "application/json", produces = "text/html;charset=UTF-8", method = {RequestMethod.POST })
	@ResponseBody
	public String delAllCourse(HttpServletRequest request, @RequestBody List<String> ids) {
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
		int count = userService.deleteAllCourse(ids);
	    if(count>0){
		   response.setCode(0); 
		   response.setMsg("成功删除"+count+"门课程"); 
		   return JSONObject.fromObject(response).toString(); 
	    }
	    else{
		  response.setMsg("删除失败"); 
		  return JSONObject.fromObject(response).toString(); 
	    }
	}
	
	
	//复用方法
	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toString();
	}
	
}
