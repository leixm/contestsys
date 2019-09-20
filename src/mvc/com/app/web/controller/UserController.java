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
import java.util.Random;

import javax.annotation.Resource;
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
import com.app.tools.RandomValidateCode;
import com.code.model.LayResponse;
import com.code.model.Response;
import com.code.model.User;
import com.code.model.loginUser;

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
		String userName = request.getParameter("username");
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
			re.setMsg("用户名不存在");
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
	 * @param 用户实例
	 * @description 用户注册
	 * @return 响应状态
	 */
	@RequestMapping(value = "User/Register", consumes = "application/json", produces = "text/html;charset=UTF-8", method = {
			RequestMethod.POST })
	@ResponseBody
	public String Register(HttpServletRequest request, HttpServletResponse response, @RequestBody User user)
			throws Exception {
		Response re = new Response();
		re.setSuccess(0);
		String rancode = request.getParameter("rancode");
		if (user == null || user.getUserId() == null || user.getPassword() == null || user.getEmail() == null) {
			re.setMsg("参数错误");
			return JSONObject.fromObject(re).toString();
		}
		if (rancode == null || !ValidateRanCode(request, rancode)) {
			re.setMsg("验证码错误");
			return JSONObject.fromObject(re).toString();
		}
		String result = userService.Register(user);
		if (result.equals("注册成功"))
			re.setSuccess(1);
		re.setMsg(result); 
		System.out.println(JSONObject.fromObject(re).toString());
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
		JSONObject obj = new JSONObject();
		JSONArray arr = userService.GetAllStudents(Keyword);
		obj.put("code", 0);
		obj.put("msg", "返回成功");
		obj.put("count", arr.size());
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
		mav.addObject("classes", classService.GetAllClass(""));
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
		mav.addObject("classes", classService.GetAllClass(""));
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
		JSONObject obj = new JSONObject();
		JSONArray arr = userService.GetAllTeachers(Keyword);
		obj.put("code", 0);
		obj.put("msg", "返回成功");
		obj.put("count", arr.size());
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
		String savePath = request.getServletContext().getRealPath("/") + "upload/";

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
					com.code.model.Class classModel = classService.GetClass(user.getClassId().toString());
					className = classModel.getName();
					if(user.getLevel()==0) {
						logUser.setClassId(user.getClassId());
						logUser.setClassName(className);
					}
					logUser.setEmail(user.getEmail());
					logUser.setLevel(user.getLevel());
					logUser.setPassword(user.getPassword());
					logUser.setRealname(user.getRealname());
					logUser.setUserId(user.getUserId());
				}
				return JSONObject.fromObject(logUser).toString();
	}
	
	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toString();
	}
	
}
