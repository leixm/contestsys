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
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.app.service.TeacherService;
import com.app.tools.ExportExcelUtil;
import com.code.model.Answer;
import com.code.model.Contest;
import com.code.model.OnePaper;
import com.code.model.OneProblem;
import com.code.model.OneSimproblem;
import com.code.model.Options;
import com.code.model.Response;
import com.code.model.ScoreExcel;
import com.code.model.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
public class TeacherController {
	@Resource
	private TeacherService teacherService;
	//导出excel用
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
        OnePaper newpaper=(OnePaper)JSONObject.toBean(jsonObject,OnePaper.class,classMap);
        System.out.println(resp);
        List<OneSimproblem> oneSimps = newpaper.getSimp();
		return JSONObject.fromObject(teacherService.addNewpaper(newpaper,user,oneSimps)).toString();
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
		Response resp = new Response();
		//路径
		String docsPath = request.getSession().getServletContext().getRealPath("docs");
		//固定头
		String[] headers = {"学号", "姓名", "班级", "成绩"};
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
		
		//导出excel
		List<ScoreExcel> dataset = new ArrayList<ScoreExcel>();
		resp = teacherService.exportGradeScoreExcel(user, contest);
		//判断是否能从impl层获取到数据
		if(resp.getSuccess()==1) {
			dataset = (List<ScoreExcel>) resp.getReObj();
		}else {
			return JSONObject.fromObject(resp).toString();
		}
		
		try {
			//考试名字
			String contestName = contest.getTitle();
			// "xxx.xls"名字必须和Util的一致
			String fileName =contestName + "年级成绩表.xls";
			OutputStream out = new FileOutputStream(docsPath + FILE_SEPARATOR
					+ fileName);
			String tableTitle = contestName + "年级成绩表";
			//利用导出工具 写出workbook对象
			new ExportExcelUtil().exportExcel(tableTitle,headers, dataset, out);
			
			out.close();
			JOptionPane.showMessageDialog(null, "导出成功!");
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
		resp.setSuccess(1);
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
	public String uploadProblem(@RequestParam MultipartFile[] files,HttpServletRequest request) {
	    InputStream inputStream = null;    
	    List<String> filePaths = new ArrayList<>();
	    try {
	    	User user = (User)request.getSession().getAttribute("user");
	 
	    	
	    	for (int i = 0; i < files.length; i++) {
	    		String filename = files[i].getOriginalFilename();
	    		if(!( (filename.substring(filename.lastIndexOf('.') + 1 , filename.length()).equals("txt")))) 
	    	    return "文件上传失败(只支持txt格式)";
	    	}
	        for (int i = 0; i < files.length; i++) {
	            String filename = files[i].getOriginalFilename();
	            //	获取到的是当前绝对项目路劲并且有/号  	“/”指代项目根目录，所以代码返回的是项目在容器中的实际发布运行的根路径
	            String path = request.getServletContext().getRealPath("/uploadTemp/"+ filename);
	            filePaths.add(path);
	            File file = new File(path);
	            File parentFile = file.getParentFile();
	            if(!parentFile.exists())
	            	parentFile.mkdirs();
	            inputStream = files[i].getInputStream();
	            FileUtils.copyInputStreamToFile(inputStream, file);
	        }
	        
	        if(inputStream!=null){
	            inputStream.close();
	        }
	        return JSONArray.fromObject(filePaths).toString();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "文件上传失败！";
	    } 
	}
	
	/**
	 * 组卷端上传编程题输入输出文件
	 * @param partFiles
	 * @param request
	 * @return
	 */
	@RequestMapping(value="Teacher/selAllContest",method={RequestMethod.POST},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String selAllContest(HttpServletRequest request,HttpServletResponse response) {
		//User user = (User)request.getSession().getAttribute("user");
		User user = new User();
		user.setUserId("123");
		return JSONObject.fromObject(teacherService.selAllContest(user)).toString();
	}
}
