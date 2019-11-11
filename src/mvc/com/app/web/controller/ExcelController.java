package com.app.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.service.ExcelService;
import com.app.tools.ExcelExportUtils;
import com.code.model.LayResponse;
import com.code.model.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@RestController
@RequestMapping("/Excel")
public class ExcelController {

	@Autowired
	private ExcelService excelService;
	
	private LayResponse layResponse = new LayResponse();
	
	final static String separator = File.separator;		//系统分隔符
	
	
	/**
	 * 根据查询参数导出学生成绩表  （ 接口一：用于存放前端传来的查询参数）
	 * @param response	
	 * @param request	
	 */
	  /**
     * 由于ajax无法直接导出excel,所以第一次把请求生成的ExcelParam缓存起来,然后前端再次window.open(url);
     */
    public static Map<String, Map<String,String>> excelParamCache = new HashMap<String, Map<String,String>>();	//缓存容器，用来存取前端传来的查询参数，供导出使用
    
    @RequestMapping(value = "/exportStuScore")
    public String exportStuScore(HttpServletResponse response,HttpServletRequest request ) {
		LayResponse resp = new LayResponse();
		resp.setCode(1); 	//默认设置失败code
		
		User user = (User)request.getSession().getAttribute("user");
		if(user==null) {
			resp.setMsg("请先登录系统！");
			return JSONObject.fromObject(resp).toString();
		}else {
			if(user.getLevel()==0) {
				resp.setMsg("该用户没有导出excel表格权限");
				return JSONObject.fromObject(resp).toString();
			}
		}
		
		/*----获取前端传参----*/
		String className = request.getParameter("classname"); 
		String stuId = request.getParameter("userid");
		String stuName = request.getParameter("stuname");
		String contestName = request.getParameter("contestname");
		
		
		Map<String,String> paramMap = new HashMap<>();
		paramMap.put("className", className);
		paramMap.put("stuId", stuId);
		paramMap.put("stuName", stuName);
		paramMap.put("contestName", contestName);
		
	    String key = UUID.randomUUID().toString();
	    System.out.println("key1===="+key);
	    excelParamCache.put(key, paramMap);			//存参进缓存容器
	    
		resp.setMsg("导出学生年级成绩excel表成功！");
		resp.setCode(0);
		resp.setData(key);
		return JSONObject.fromObject(resp).toString();
    }
    
    
    
    
    /**
	 * 根据查询参数导出学生成绩表  （ 接口二：根据参数下载excel）
	 * @param response	
	 * @param request	
	 */
    @RequestMapping(value = "/exportStuScore/download")		
    public void exportStuScoreDownload(HttpServletResponse response,HttpServletRequest request ) {
    	List<Map<String, Object>> scoreExcelList = new ArrayList<>();			//存放导出表格所需要的学生个人成绩信息
		//利用key获取缓存参数
    	String key = request.getParameter("key");
    	Map<String,String> paramMap = excelParamCache.get(key);
    	System.out.println("key2======="+key);
    	excelParamCache.remove(key);	//用完key之后记得去掉，防止占用容器位置
    	
		/*----获取导出的数据集开始----*/
		String className = paramMap.get("className"); 
		String stuId = paramMap.get("stuId");
		String stuName = paramMap.get("stuName");
		String contestName = paramMap.get("contestName");
		
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
    	scoreExcelList = excelService.selStuScoreByKeyword(className, stuId, stuName, contestName);
		// "xxx.xls"名字必须和Util的一致
		String fileName ="学生成绩表.xls";
		String tableTitle = "学生成绩表";
		String[] headers = {"学号", "姓名","考试名称", "班级", "成绩"};
		String dateType = "yyyy-MM-dd";
		
		List<String> paramList = new ArrayList<String>();	//导出表格字段对应的数据库表字段集合，顺序需要严格按照导出表格来
		paramList.add("stuid");
		paramList.add("stuname");
		paramList.add("contestname");
		paramList.add("classname");
		paramList.add("score");
		//调用根据各个参数去调用导出excel静态方法，并下载到本地电脑
		exportExcelDwonloadMethod(response, request, scoreExcelList, paramList, fileName, tableTitle, headers, dateType);
    }
    
    
    
    
    /**
     * 下载批量导入模板
     * @param fileName  需要下载的文件名
     * @param response	
     * @param request	
     */
    @RequestMapping(value = "/downloadDemo/importDemo")		
    public void downloadImportDemo(HttpServletResponse response,HttpServletRequest request,@RequestParam String fileName) {
    	String classPath = this.getClass().getResource("/").getPath(); 
    	downloadDemoMethod(response, request, classPath, fileName);
    }
    
    
    
    
    /**
     * 批量导入学生角色（默认激活，用于管理员或老师添加学生使用）
     * @param file	前端传来的文件流
     * @param type	区分是导入何种角色============== 学生—— 0 ， 教师 —— 1， 班级 —— 2
     * @param session	
     * @return	导入结果，或者是导入错误信息
     */
    @RequestMapping("/importObject")
    public String importStudent(HttpServletRequest request, @RequestParam(value = "file")MultipartFile file, HttpSession session,int type) {
		String fileName = file.getOriginalFilename();	//文件名，用于校验文件格式
		layResponse.setCode(1); 	//默认设置失败code
		
		User user = (User)request.getSession().getAttribute("user");
		if(user==null) {
			layResponse.setMsg("请先登录系统！");
			//return JSONObject.fromObject(layResponse).toString();
		}else {
			if(user.getLevel()==0) {
				layResponse.setMsg("该用户没有导出excel表格权限");
				//return JSONObject.fromObject(layResponse).toString();
			}
		}
		
		try {
			if(type == 0) {					//批量导入学生
				layResponse = excelService.batchImportStudent(fileName, file);
			}
			else if(type == 1) {			//批量导入教师
				layResponse = excelService.batchImportTeacher(fileName, file);
	    	}
	    	
	    	else if(type == 2) {			//批量导入班级
	    		layResponse = excelService.batchImportClass(fileName, file);
	    	}
			
	    	else {
	    		layResponse.setCode(1);  //1表示失败
				layResponse.setMsg("tpye为无效值");
				return JSONArray.fromObject(layResponse).toString();
	    	}
			
		} catch (Exception e) {
			e.printStackTrace();
			layResponse.setCode(1);  //1表示失败
			layResponse.setMsg(e.getMessage());
			System.out.println("fail====="+e.getMessage());
		}finally {
			System.out.println("success====="+layResponse.getMsg());
			return JSONArray.fromObject(layResponse).toString();
		}
    }
  
    
    
    
    //根据文件名去根目录的demoFileDownload文件夹下载对应名字的模板文件
    /**
     * 复用方法，用来下载导入模板等文件
     * @param response
     * @param request
     * @param fileName  需要下载的文件名字
     * @param classPath 类路径
     */
    private  static void downloadDemoMethod(HttpServletResponse response,HttpServletRequest request,String classPath, String fileName) {
    	//String classPath = ExcelController.class.getClass().getResource("/").getPath(); 		//类路径，static方法无法用，会出现空指针
    	String webappRoot = classPath.replaceAll("WEB-INF/classes/", ""); 	//根目录
    	webappRoot  = webappRoot.replace("%20"," ");	//替换空格
    	
    	String filePath = webappRoot + File.separator + "demoFileDownload" + File.separator + fileName;		//模板文件绝对路径
    	
    	File file = new File(filePath);
        // 取得文件名。
        InputStream fis = null;
        
        try {
            fis = new FileInputStream(file);
            request.setCharacterEncoding("UTF-8");
            String agent = request.getHeader("User-Agent").toUpperCase();
            if ((agent.indexOf("MSIE") > 0) || ((agent.indexOf("RV") != -1) && (agent.indexOf("FIREFOX") == -1)))
                fileName = URLEncoder.encode(fileName, "UTF-8");
            else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setHeader("Content-Length", String.valueOf(file.length()));
 
            byte[] b = new byte[1024];
            int len;
            while ((len = fis.read(b)) != -1) {
                response.getOutputStream().write(b, 0, len);
            }
            response.flushBuffer();
            
        }catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
        	//在finally里关闭流
        	if(null != fis) {
        		try {
    				fis.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
        	}
        }
    	
    }
    
    //根据各个参数去调用导出excel公共方法，并下载到本地电脑
    /**	
     * 复用方法：根据查询参数等来导出学生成绩表 
     * @param response
     * @param request
     * @param selExcelList		数据库表查询到的数据集
     * @param paramList			需要导出的数据字段名称集合，如（stuid，classname等）用来寻找selExcelList中Map的值的key值的集合
     * @param fileName			导出的EXCEL文件名字
     * @param tableTitle		EXCEL表格的sheet名和表格第一行的表格标题
     * @param headers			EXCEL表格的列头
     * @param dateType			EXCEL表格中日期的展现格式，如：'yyyy-MM-dd'
     */
    private static void exportExcelDwonloadMethod(HttpServletResponse response,HttpServletRequest request,List<Map<String, Object>> selExcelList,List<String> paramList,
    	 String fileName,String tableTitle,String[] headers,String dateType) {
    	OutputStream os = null;
    	 try {
 			//利用导出工具 写出workbook对象到指定路径
 			response.setContentType("application/vnd.ms-excel;charset=utf-8");
 			os = response.getOutputStream();
 			response.setHeader("Content-disposition", "attachment;filename="+ java.net.URLEncoder.encode(fileName, "UTF-8"));//默认Excel名称
 			HSSFWorkbook wb = new ExcelExportUtils().exportExcel(tableTitle,headers, paramList,selExcelList,"yyyy-MM-dd");
 			wb.write(os);
 			os.flush();
 			os.close();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}finally {
 			//在finally里关闭流
        	if(null != os) {
        		try {
        			os.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
        	}
 		}
    }
    
    
    
    
    
    
    
    
    
}
