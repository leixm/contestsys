package com.app.service.impl;
/**
 * Excel文档处理模块  业务层
 * author:zzs
 * 
 */
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.math.BigDecimal;

import com.app.dao.*;
import com.code.model.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.app.common.MyException;
import com.app.service.ExcelService;
import com.github.pagehelper.PageHelper;

@Service
public class ExcelServiceImpl implements ExcelService{
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ClassMapper classMapper;
	
	@Autowired
	private ContestStatusMapper contestStatusMapper;

	@Autowired
	private SimproblemMapper simproblemMapper;
	@Autowired
	private OptionsMapper optionMapper;
	@Autowired
	private AnswerMapper answerMapper;
	@Autowired
	private ContestpaperMapper paperMapper;
	
	private LayResponse layResponse = new LayResponse();
	
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	
	
	/**
	 * 根据搜索条件模糊查询出学生成绩表
	 * @param 1、班级名称
	 * @param 2、学生学号
	 * @param 3、学生名字
	 * @param 4、考试名称
	 * @return 成绩实体Map对象集合
	 */
	public List<Map<String,Object>> selStuScoreByKeyword(String className,String stuId,String stuName,String contestName) {
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(); //返回结果的容器
		
		resultList = contestStatusMapper.selStuScoreBykeyword(className, stuId, stuName, contestName); //根据参数查询学生成绩等字段，如果参数全部为空自动查询全部学生的相关成绩
		return resultList;
	}
	

	
	
	/**
	 * 
	 * 批量导入学生用户
	 * @param fileName	文件名 带文件类型结尾
	 * @param file		文件流
	 * @return		导入结果等信息
	 * @throws Exception	抛出导入带有的错误信息
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)		// 回滚注解，抛出异常自动回滚
	public LayResponse batchImportStudent(String fileName, MultipartFile file) throws Exception {
		int successNum = 0; //导入成功数量
		int existNum = 0; //已存在数据数量
		
		List<User> stuList = new ArrayList<>();
		String extraMessage = ""; //附加通知，用来通知有几条信息是数据库已经存在的
		
		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
			throw new MyException("上传文件格式不正确");
		}
		boolean isExcel2003 = true;
		if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
			isExcel2003 = false;
		}
		InputStream is = file.getInputStream();
		Workbook wb = null;		
		if (isExcel2003) {
			wb = new HSSFWorkbook(is);
		} else {
			wb = new XSSFWorkbook(is);
		}
		is.close();   //释放流（否则会读取不到新的文件）
		//获取第一个sheet
		Sheet sheet = wb.getSheetAt(0);
		
		for (int r = 2; r <= sheet.getLastRowNum(); r++) {		//r = 2 表示从第三行开始循环 ，第一行(r=0)为表头，第二行为样例数据
			Row row = sheet.getRow(r);		//通过sheet表单对象得到 行对象
			if (row == null){
				break;
			}
			
			//sheet.getLastRowNum() 的值是 10，所以Excel表中的数据至少是10条；不然报错 NullPointerException
			if(row.getCell(0) == null) {
				throw new MyException("导入失败(第"+(r+1)+"行,学号不能为空)");
			}
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第一个单元格的值，转为字符串
			String userId = StringTrim(row.getCell(0).getStringCellValue()); //去掉所有空格
			if(userId.equals("")) {
				throw new MyException("导入失败(第"+(r+1)+"行,学号不能为空)");
			}
			
			//校验是否为数字格式
			Pattern pattern = Pattern.compile("[0-9]*");
	        Matcher isNum = pattern.matcher(userId);
	        
			if(!isNum.matches()){//循环时，得到每一行的单元格进行判断
				throw new MyException("导入失败(第"+(r+1)+"行,学号只能为数字)");
			}
			
			if(row.getCell(1) == null) {
				throw new MyException("导入失败(第"+(r+1)+"行,姓名不能为空)");
			}
			row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第二个单元格的值
			String realname = StringTrim(row.getCell(1).getStringCellValue()); //去掉所有空格
			if(realname.equals("")) {
				throw new MyException("导入失败(第"+(r+1)+"行,姓名不能为空)");
			}
			
			if(row.getCell(2) == null) {
				throw new MyException("导入失败(第"+(r+1)+"行,密码不能为空)");
			}
			row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第三个单元格的值
			String password = StringTrim(row.getCell(2).getStringCellValue()); //去掉所有空格
			if(password.equals("")) {
				throw new MyException("导入失败(第"+(r+1)+"行,密码不能为空)");
			}

			if(row.getCell(3) == null) {
				throw new MyException("导入失败(第"+(r+1)+"行,班级不能为空)");
			}
			row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第四个单元格的值
			String classname = StringTrim(row.getCell(3).getStringCellValue()); //去掉所有空格
			if(classname.equals("")) {
				throw new MyException("导入失败(第"+(r+1)+"行,班级不能为空)");
			}
			
			//查询数据库是否有存在此  班级对象     有则取其classId   	无则抛出错误
			List<Map<String,Object>> classList = classMapper.selByClassname(classname);
			if(classList.isEmpty()) {	
				throw new MyException("导入失败(第"+(r+1)+"行,其班级不存在，请先创建班级，再进行导入)");
			}
			int classId = Integer.parseInt(classList.get(0).get("class_id").toString());
			//System.out.println("classid===="+classId);
			//根据userId 判断数据库是否已经存在此条数据；是则跳过，不用操作插入
			List<Map<String,Object>> userList = userMapper.selUserById(userId);
			if(!userList.isEmpty()) {
				extraMessage += r+1 + "、";  //记录第几行数据数据库已经存在
				existNum ++;
				break ;		//跳过此次循环（单次而已）
			}
			
			User stuObj = new User();
			//完整的循环一次 就组成了一个对象
			stuObj.setUserId(userId);
			stuObj.setPassword(password);
			stuObj.setRealname(realname);
			stuObj.setClassId(classId);
			stuObj.setLevel(0);
			stuObj.setState(1);
			stuList.add(stuObj);
			successNum ++;		//成功数加一
		}
		
		for (User oneStu : stuList) {
			//向数据库插入新学生用户
			userMapper.insertStuByExcel(oneStu);
		}
		//组装信息
		if(!extraMessage.equals("")) {
			extraMessage =  "第" + extraMessage.substring(0,extraMessage.length()-1) 
							+ "行,共" + existNum + "条数据已存在，跳过导入。";	//去掉最后的、号
		}
		layResponse.setCode(0);
		layResponse.setMsg(extraMessage + "成功导入"+ successNum + "条数据！");
		// System.out.println(layResponse.getMsg());
		return layResponse;
	}




	/**
	 * 
	 * 批量导入教师用户
	 * @param fileName	文件名 带文件类型结尾
	 * @param file		文件流
	 * @return		导入结果等信息
	 * @throws Exception	抛出导入带有的错误信息
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)		// 回滚注解，抛出异常自动回滚
	public LayResponse batchImportTeacher(String fileName, MultipartFile file) throws Exception {
		int successNum = 0; //导入成功数量
		int existNum = 0; //已存在数据数量
		
		List<User> teaList = new ArrayList<>();
		String extraMessage = ""; //附加通知，用来通知有几条信息是数据库已经存在的
		
		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
			throw new MyException("上传文件格式不正确");
		}
		boolean isExcel2003 = true;
		if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
			isExcel2003 = false;
		}
		InputStream is = file.getInputStream();
		Workbook wb = null;		
		if (isExcel2003) {
			wb = new HSSFWorkbook(is);
		} else {
			wb = new XSSFWorkbook(is);
		}
		is.close();   //释放流（否则会读取不到新的文件）
		//获取第一个sheet
		Sheet sheet = wb.getSheetAt(0);
		
		for (int r = 2; r <= sheet.getLastRowNum(); r++) {		//r = 2 表示从第三行开始循环 ，第一行(r=0)为表头，第二行为样例数据
			Row row = sheet.getRow(r);		//通过sheet表单对象得到 行对象
			if (row == null){
				break;
			}
			
			//sheet.getLastRowNum() 的值是 10，所以Excel表中的数据至少是10条；不然报错 NullPointerException
			
			if(row.getCell(0) == null) {
				throw new MyException("导入失败(第"+(r+1)+"行,工号不能为空)");
			}
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第一个单元格的值，转为字符串
			String userId = StringTrim(row.getCell(0).getStringCellValue()); //去掉所有空格
			if(userId.equals("")) {
				throw new MyException("导入失败(第"+(r+1)+"行,工号不能为空)");
			}
			//校验是否为数字格式
			Pattern pattern = Pattern.compile("[0-9]*");
	        Matcher isNum = pattern.matcher(userId);
			if(!isNum.matches()){//循环时，得到每一行的单元格进行判断
				throw new MyException("导入失败(第"+(r+1)+"行,工号只能为数字)");
			}
			
			if(row.getCell(1) == null) {
				throw new MyException("导入失败(第"+(r+1)+"行,姓名不能为空)");
			}
			row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);//设置每一行的 第二个单元格的值为字符串
			String realname = StringTrim(row.getCell(1).getStringCellValue()); //去掉所有空格
			if(realname.equals("")) {
				throw new MyException("导入失败(第"+(r+1)+"行,姓名不能为空)");
			}
			
			if(row.getCell(2) == null) {
				throw new MyException("导入失败(第"+(r+1)+"行,密码不能为空)");
			}
			row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);//设置每一行的 第三个单元格的值为字符串
			String password = StringTrim(row.getCell(2).getStringCellValue());//去掉所有空格
			if(password.equals("")) {
				throw new MyException("导入失败(第"+(r+1)+"行,密码不能为空)");
			}
		
			//根据userId 判断数据库是否已经存在此条数据；是则跳过，不用操作插入
			List<Map<String,Object>> userList = userMapper.selUserById(userId);
			if(!userList.isEmpty()) {
				extraMessage += r+1 + "、";  //记录第几行数据数据库已经存在
				existNum ++;
				break ;		//跳过此次循环（单次而已）
			}
			
			User teaObj = new User();
			//完整的循环一次 就组成了一个对象
			teaObj.setUserId(userId);
			teaObj.setPassword(password);
			teaObj.setRealname(realname);
			teaObj.setLevel(1);
			teaObj.setState(1);
			teaList.add(teaObj);
			successNum ++;		//成功数加一
		}
		
		for (User oneTea : teaList) {
			//向数据库插入新教师用户
			userMapper.insertTeaByExcel(oneTea);
		}
		//组装信息
		if(!extraMessage.equals("")) {
			extraMessage =  "第" + extraMessage.substring(0,extraMessage.length()-1) 
							+ "行,共" + existNum + "条数据已存在，跳过导入。";	//去掉最后的、号
		}
		layResponse.setCode(0);
		layResponse.setMsg(extraMessage + "成功导入"+ successNum + "条数据！");
		// System.out.println(layResponse.getMsg());
		return layResponse;
	}




	/**
	 * 
	 * 批量导入班级
	 * @param fileName	文件名 带文件类型结尾
	 * @param file		文件流
	 * @return		导入结果等信息
	 * @throws Exception	抛出导入带有的错误信息
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)		// 回滚注解，抛出异常自动回滚
	public LayResponse batchImportClass(String fileName, MultipartFile file) throws Exception {
		int successNum = 0; //导入成功数量
		int existNum = 0; //已存在数量
		List<com.code.model.Class> classList = new ArrayList<>();
		String extraMessage = ""; //附加通知，用来通知有几条信息是数据库已经存在的
		
		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
			throw new MyException("上传文件格式不正确");
		}
		boolean isExcel2003 = true;
		if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
			isExcel2003 = false;
		}
		InputStream is = file.getInputStream();
		Workbook wb = null;		
		if (isExcel2003) {
			wb = new HSSFWorkbook(is);
		} else {
			wb = new XSSFWorkbook(is);
		}
		is.close();   //释放流（否则会读取不到新的文件）
		//获取第一个sheet
		Sheet sheet = wb.getSheetAt(0);
		
		for (int r = 2; r <= sheet.getLastRowNum(); r++) {		//r = 2 表示从第三行开始循环 ，第一行(r=0)为表头，第二行为样例数据
			Row row = sheet.getRow(r);		//通过sheet表单对象得到 行对象
			if (row == null){
				break;
			}
			
			//sheet.getLastRowNum() 的值是 10，所以Excel表中的数据至少是10条；不然报错 NullPointerException
			
			/*if(row.getCell(1) == null) {
				throw new MyException("导入失败(第"+(r+1)+"行,工号不能为空)");
			}
			row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第一个单元格的值，转为字符串
			String userId = StringTrim(row.getCell(1).getStringCellValue()); //去掉所有空格
			if(userId.equals("")) {
				throw new MyException("导入失败(第"+(r+1)+"行,工号不能为空)");
			}
			//校验是否为数字格式
			Pattern pattern = Pattern.compile("[0-9]*");
	        Matcher isNum = pattern.matcher(userId);
			if(!isNum.matches()){//循环时，得到每一行的单元格进行判断
				throw new MyException("导入失败(第"+(r+1)+"行,工号只能为数字)");
			}
			//查询数据库是否有存在此  教师对象       	无则抛出错误
			List<Map<String,Object>> userList = userMapper.selUserById(userId);
			//System.out.println("teacher===="+userList);
			if(userList.isEmpty()) {	
				throw new MyException("导入失败(第"+(r+1)+"行,其任课老师工号不存在，请检查工号正确性，再进行导入)");
			}*/
			
			
			if(row.getCell(0) == null) {
				throw new MyException("导入失败(第"+(r+1)+"行,班级名称不能为空)");
			}
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);//设置每一行的 第二个单元格的值为字符串
			String className = StringTrim(row.getCell(0).getStringCellValue()); //去掉所有空格
			if(className.equals("")) {
				throw new MyException("导入失败(第"+(r+1)+"行,班级名称不能为空)");
			}
			
		
			//根据班级名称是否已经存在， 判断数据库是否已经存在此条数据；是则跳过，不用操作插入
			List<Map<String,Object>> classIdList = classMapper.selByClassname(className);
			if(!classIdList.isEmpty()) {
				extraMessage += r+1 + "、";  //记录第几行数据数据库已经存在
				existNum ++;
				break ;		//跳过此次循环（单次而已）
			}
			
			com.code.model.Class classObj = new com.code.model.Class();
			//完整的循环一次 就组成了一个对象
			classObj.setName(className);
			classList.add(classObj);
			successNum ++;		//成功数加一
		}
		
		for (com.code.model.Class oneClass : classList) {
			//向数据库插入新教师用户
			classMapper.insert(oneClass);
		}
		//组装信息
		if(!extraMessage.equals("")) {
			extraMessage =  "第" + extraMessage.substring(0,extraMessage.length()-1) 
							+ "行,共" + existNum + "条数据已存在，跳过导入。";	//去掉最后的、号
		}
		layResponse.setCode(0);
		layResponse.setMsg(extraMessage + "成功导入"+ successNum + "条数据！");
		// System.out.println(layResponse.getMsg());
		return layResponse;
	}

	/**
	 *
	 * 批量导入通用题
	 * @param fileName	文件名 带文件类型结尾
	 * @param file		文件流
	 * @return		导入结果等信息
	 * @throws Exception	抛出导入带有的错误信息
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)		// 回滚注解，抛出异常自动回滚
	public LayResponse batchImportSimproblem(String fileName, MultipartFile file, int courseId, String teacherId, String importPaper) throws Exception {
		List<com.code.model.Class> classList = new ArrayList<>();
		String extraMessage = ""; //附加通知，用来通知有几条信息是数据库已经存在的等信息

		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
			throw new MyException("上传文件格式不正确");
		}
		boolean isExcel2003 = true;
		if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
			isExcel2003 = false;
		}
		InputStream is = file.getInputStream();
		Workbook wb = null;
		if (isExcel2003) {
			wb = new HSSFWorkbook(is);
		} else {
			wb = new XSSFWorkbook(is);
		}
		is.close();   //释放流（否则会读取不到新的文件）

		int paperId = 0;		// 初始化试卷Id,作为参数传，为0代表单纯导入试题；  为其他值证明导入的是一张试卷；
		if(!"0".equals(importPaper)) {		// 1代表导入一张新试卷，需要paperId
			if(paperMapper.selPaperCount()>0) {
				paperId = paperMapper.selMaxPaperId()+1;
			}else {
				paperId = 1001;
			}
		}

		for (int i = 0; i < wb.getNumberOfSheets(); i++) {//获取每个Sheet表
			//获取第一个sheet
			Sheet sheet = wb.getSheetAt(i);		//获取第i个sheet表格
			String sheelName = sheet.getSheetName();
			sheelName = StringTrim(sheelName);

			if(sheelName.isEmpty()){
				continue;
			}
			switch(sheelName){
				case "试卷标题":
					if(paperId!=0) {
						extraMessage += importOneContestpaper(sheet,paperId,courseId,teacherId);
					}
					break;

				case "单选题":
					extraMessage += importOneChoice(sheet,courseId,paperId);
					break;
				case "多选题":
					extraMessage += importMoreChoice(sheet,courseId,paperId);
					break;
				case "填空题":
					extraMessage += importFillBlank(sheet,courseId,paperId);
					break;
				case "判断题":
					extraMessage += importJudgment(sheet,courseId,paperId);
					break;
				case "简答题":
					extraMessage += importShortAnswer(sheet,courseId,paperId);
					break;
				default :
					break;
			}

		}
		Map<String,Object> paperIdMap = new HashedMap();
		paperIdMap.put("paperId",paperId);
		layResponse.setCode(0);
		layResponse.setMsg(extraMessage);
		layResponse.setData(paperIdMap);
		// System.out.println(layResponse.getMsg());
		return layResponse;
	}


	/**
	 * 导入试卷标题方法
	 * @param sheet
	 * @return		导入成功后等的附加信息
	 */
	private String importOneContestpaper(Sheet sheet,int paperId, int fkCourserId, String teacherId) throws Exception{
		String choiceName = "试卷标题";
		int r = 2;
		Row row = sheet.getRow(r);        //通过sheet表单对象得到3行对象，试题标题表只需要填第三行
		if (row == null) {
			throw new MyException(choiceName + "导入失败(第"+(r+1)+"行,请在表格第三行填写试卷标题，其他位置填写不作录入)");
		}

		if(row.getCell(0) == null) {		//表示第一个单元格
			throw new MyException(choiceName + "导入失败(第"+(r+1)+"行,试卷标题不能为空)");
		}
		row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);//设置每一行的 第1个单元格的值为字符串
		String paperTitle = StringTrim(row.getCell(0).getStringCellValue()); //去掉所有空格
		if("".equals(paperTitle)) {
			throw new MyException(choiceName + "导入失败(第"+(r+1)+"行,试卷标题不能为空)");
		}
		// 试卷是否已存在
		List<Map<String,Object>> existPaper = paperMapper.selPaperByTitle(paperTitle);
		if(!existPaper.isEmpty()) {
			throw new MyException(choiceName + "导入失败(第"+(r+1)+"行,该试卷标题已存在，请重新填写试卷标题)");
		}
		// 入库
		Contestpaper cPaper = new Contestpaper();
		cPaper.setPaperId(paperId);
		cPaper.setTeacher(teacherId);
		cPaper.setTitle(paperTitle);
		cPaper.setFkCourseId(fkCourserId);

		paperMapper.insertSelective(cPaper);

		return "--试卷标题导入成功！";
	}

	/**
	 * 导入单选题方法   单选题type为1
	 * @param sheet		单选题sheet页
	 * @return		导入成功后等的附加信息
	 */
	private String importOneChoice(Sheet sheet,int fkCourserId,int paperId) throws Exception{
		return importOptionCommonMethod(sheet,1,fkCourserId, paperId);
	}

	/**
	 * 导入多选题方法		多选题type为2
	 * @param sheet		多选题sheet页
	 * @return		导入成功后等的附加信息
	 */
	private String importMoreChoice(Sheet sheet, int fkCourserId,int paperId) throws Exception{
		return importOptionCommonMethod(sheet,2, fkCourserId, paperId);
	}

	/**
	 * 导入选择题复用方法
	 * @param sheet
	 * @param choiceType  1--单选题、2--多选题
	 * @return
	 * @throws Exception
	 */
	private String importOptionCommonMethod (Sheet sheet,int choiceType, int fkCourserId,int paperId) throws Exception{
		String resultMessage = "";
		String existMessage = "";
		int importNum = 0; //导入成功数量
		int existNum = 0; //已存在数量
		String choiceName = "单选题";
		if(choiceType == 2) {
			choiceName = "多选题";
		}

		int lastRowNum = getRealLastNum(sheet); // 获得总共有多少行数据(准确)

		if (lastRowNum <= 1) {
			return choiceName + "数据为空！";
		}else {
			for (int r = 2; r <= lastRowNum; r++) {        //r = 2 表示从第三行开始循环 ，第一行(r=0)为表头，第二行为样例数据
				Row row = sheet.getRow(r);        //通过sheet表单对象得到 行对象
				if (row == null) {
					continue;
				}

				if(row.getCell(1) == null) {		//表示第二个单元格
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,题目分值不能为空)");
				}
				row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);//设置每一行的 第二个单元格的值为字符串
				String score = StringTrim(row.getCell(1).getStringCellValue()); //去掉所有空格
				BigDecimal scoreDecimal = new BigDecimal("2.0");		//默认分数
				if(!score.equals("")&&checkIsNum(score)) {		//非空且为数值
					scoreDecimal = new BigDecimal(score);
				}else if("".equals(score)) {
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,题目分值不能为空)");
				}else {
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,题目分值所填写内容数据类型有误)");
				}

				if(row.getCell(2) == null) {
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,题目内容不能为空)");
				}
				row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);//设置每一行的 第x个单元格的值为字符串
				String content = StringTrim(row.getCell(2).getStringCellValue()); //去掉所有空格
				if("".equals(score)) {
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,题目内容不能为空)");
				}

				if(row.getCell(3) == null) {
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,选项1不能为空)");
				}
				row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);//设置每一行的 第x个单元格的值为字符串
				String opTemp = StringTrim(row.getCell(3).getStringCellValue()); //去掉所有空格
				if("".equals(opTemp)) {	// 看第一个选项是否为空，空则报错
					throw new MyException(choiceName + "数据导入失败(第" + (r + 1) + "行,选项1不能为空)");
				}

				List<String> optionList = new ArrayList<>();		//选项集合
				for(int i=3; i<16; i++) {		//遍历选项单元格，遇到空单元格跳出循环(因为选项个数不确定)
					if(row.getCell(i) == null) {		//遇到空单元格跳出循环
						break;
					}
					row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);//设置每一行的 第x个单元格的值为字符串
					String option = StringTrim(row.getCell(i).getStringCellValue()); //去掉所有空格
					if("".equals(option)) {
						break;
					}
					optionList.add(option);
				}

				List<String> answerOptionList = new ArrayList<>();		// 答案集合
				if(!optionList.isEmpty()) {
					for (int j=0; j<optionList.size(); j++) {		// 遍历并获取正确答案（选项末尾有*表示是正确答案）
						String opStr = optionList.get(j);
						if(opStr.substring(opStr.length()-1,opStr.length()).equals("*")){
							String answerOptionStr = opStr.substring(0,opStr.length()-1);	// 去掉*号
							optionList.set(j,answerOptionStr);	// 替换没有*号的值进选项集合
							answerOptionList.add(answerOptionStr);
						}
					}
				} else {
					throw new MyException(choiceName + "数据导入失败(第" + (r + 1) + "行,选项不能为空)");
				}
				if(answerOptionList.isEmpty()) {
					throw new MyException(choiceName + "数据导入失败(第" + (r + 1) + "行,答案不能为空)");
				}

				/**进行实体类赋值并进行数据库操作--start--**/
				List<Map<String,Object>> existSimList = simproblemMapper.selSimByContent(content,choiceType);		// 根据内容查通用题是否存在
				if(!existSimList.isEmpty() && paperId == 0) {		//单纯导入通用题库的时候，对已存在的题进行跳过
					existMessage += r+1+"、"; // 记录第几行数据数据库已经存在
					existNum ++;
					continue;		//跳过此次循环（单次而已）
				}

				// 因为插入答案和选项等需要外键SimpleProblemId，所以需要查询sim表中最大的simId是多少
				int maxSimpId = simproblemMapper.selMaxSimpId();
				int newSimId = 1001;	// 数据库表空时，默认id从1001开始
				if(maxSimpId > 0) {
					newSimId = maxSimpId + 1;
				}
				Simproblem simproblem = new Simproblem();
				simproblem.setBlanks(0);
				simproblem.setContent(content);
				simproblem.setScore(scoreDecimal);
				simproblem.setType(choiceType);		//题目类型，1为单选、2为多选
				simproblem.setSimproblemId(newSimId);
				simproblem.setFkCourseId(fkCourserId);
				if(paperId!=0) {		// 当paperId不等0表示导入形式为导入一张试卷
					simproblem.setPaperId(paperId);
				}
				simproblemMapper.insert(simproblem);

				for(int i=0; i<optionList.size(); i++) {
					Options o = new Options();
					o.setContent(optionList.get(i));
					o.setPos(i+1);
					o.setSimproblemId(newSimId);
					optionMapper.insertSelective(o);
				}

				for(int i=0; i<answerOptionList.size(); i++) {
					Answer a = new Answer();
					a.setContent(answerOptionList.get(i));
					a.setPos(i);
					a.setSimproblemId(newSimId);
					answerMapper.insertSelective(a);
				}
				/**进行实体类赋值并进行数据库操作--end--**/
				importNum ++;
			}
		}
		//组装信息
		if(!existMessage.equals("")) {
			resultMessage =  "--"+choiceName + "导入提示： 第" + existMessage.substring(0,existMessage.length()-1) 	// 去掉最后的、号
					+ "行,共" + existNum + "条数据已存在，跳过导入。" + "成功导入"+ importNum + "条数据！";
		}else {
			resultMessage = "--"+choiceName + "导入提示： "+ "成功导入"+ importNum + "条数据！";
		}
		return resultMessage;
	}

	/**
	 * 导入填空题方法  填空题type为4
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
	private String importFillBlank(Sheet sheet, int fkCourserId,int paperId) throws Exception{
		String resultMessage = "";
		String existMessage = "";
		int importNum = 0; //导入成功数量
		int existNum = 0; //已存在数量
		String choiceName = "填空题";
		int choiceType = 4;		//题目类型

		int lastRowNum = sheet.getLastRowNum(); // 获得总共有多少行数据(不准确)

		//遍历每一行第一个单元格内容，有内容则表示这一行是需要导入
		for (int r = 0; r <= lastRowNum; r++) {
			Row row = sheet.getRow(r);        //通过sheet表单对象得到行对象
			if(row.getCell(0) == null) {
				lastRowNum = r-1;
				break;
			}
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);//设置每一行的 第二个单元格的值为字符串
			String rowNum = StringTrim(row.getCell(0).getStringCellValue()); //去掉所有空格
			if("".equals(rowNum)) {
				lastRowNum = r-1;
				break;
			}
		}
		// System.out.printf(choiceName+"lastRowNum行数=="+lastRowNum);

		if (lastRowNum <= 1) {
			return choiceName + "数据为空！";
		}else {
			for (int r = 2; r <= lastRowNum; r++) {        //r = 2 表示从第三行开始循环 ，第一行(r=0)为表头，第二行为样例数据
				Row row = sheet.getRow(r);        //通过sheet表单对象得到 行对象
				if (row == null) {
					continue;
				}

				if(row.getCell(1) == null) {		//表示第二个单元格
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,题目分值不能为空)");
				}
				row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);//设置每一行的 第二个单元格的值为字符串
				String score = StringTrim(row.getCell(1).getStringCellValue()); //去掉所有空格
				BigDecimal scoreDecimal = new BigDecimal("2.0");		//默认分数
				if(!score.equals("")&&checkIsNum(score)) {		//非空且为数值
					scoreDecimal = new BigDecimal(score);
				}else if("".equals(score)) {
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,题目分值不能为空)");
				}else {
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,题目分值所填写内容数据类型有误)");
				}

				int checkBlankNum = 0;		//检验空格数是否和答案数一致（根据"___"分割后的数组个数来判断）
				if(row.getCell(2) == null) {
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,题目内容不能为空)");
				}
				row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);//设置每一行的 第x个单元格的值为字符串
				String content = StringTrim(row.getCell(2).getStringCellValue()); //去掉所有空格
				if("".equals(content)) {
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,题目内容不能为空)");
				}else {
					checkBlankNum = content.split("____").length - 1;		// 空数量
					content = content.replaceAll("____", "___________");	// 对填空题内容中的___进行统一处理，本来是四个_，替换成11个_
					// System.out.println("checkBlankNum--"+checkBlankNum);
					// System.out.println("content=="+content);
				}

				if(row.getCell(3) == null) {
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,答案1不能为空)");
				}
				row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);//设置每一行的 第x个单元格的值为字符串
				String anTemp = StringTrim(row.getCell(3).getStringCellValue()); //去掉所有空格
				if("".equals(anTemp)) {	// 看第一个选项是否为空，空则报错
					throw new MyException(choiceName + "数据导入失败(第" + (r + 1) + "行,答案1不能为空)");
				}

				List<String> answerList = new ArrayList<>();		//答案集合
				for(int i=3; i<16; i++) {		//遍历选项单元格，遇到空单元格跳出循环(因为选项个数不确定)
					if(row.getCell(i) == null) {		//遇到空单元格跳出循环
						break;
					}
					row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);//设置每一行的 第x个单元格的值为字符串
					String answer = StringTrim(row.getCell(i).getStringCellValue()); //去掉所有空格
					if("".equals(answer)) {
						break;
					}
					answerList.add(answer);
					// System.out.println("answerList.size()--"+answerList.size());
				}
				if(answerList.size() != checkBlankNum) {
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,填空数量与答案数量不一致)");
				}

				/**进行实体类赋值并进行数据库操作--start--**/
				List<Map<String,Object>> existSimList = simproblemMapper.selSimByContent(content,choiceType);		// 根据内容与题目类型查通用题是否存在
				if(!existSimList.isEmpty() && paperId == 0) {		//单纯导入通用题库的时候，对已存在的题进行跳过
					existMessage += r+1+"、"; // 记录第几行数据数据库已经存在
					existNum ++;
					continue;		//跳过此次循环（单次而已）
				}

				// 因为插入答案和选项等需要外键SimpleProblemId，所以需要查询sim表中最大的simId是多少
				int maxSimpId = simproblemMapper.selMaxSimpId();
				int newSimId = 1001;	// 数据库表空时，默认id从1001开始
				if(maxSimpId > 0) {
					newSimId = maxSimpId + 1;
				}
				Simproblem simproblem = new Simproblem();
				simproblem.setBlanks(checkBlankNum);
				simproblem.setContent(content);
				simproblem.setScore(scoreDecimal);
				simproblem.setType(choiceType);		//题目类型
				simproblem.setSimproblemId(newSimId);
				simproblem.setFkCourseId(fkCourserId);
				if(paperId!=0) {		// 当paperId不等0表示导入形式为导入一张试卷
					simproblem.setPaperId(paperId);
				}
				simproblemMapper.insert(simproblem);

				for(int i=0; i<answerList.size(); i++) {
					Answer a = new Answer();
					a.setContent(answerList.get(i));
					a.setPos(i);
					a.setSimproblemId(newSimId);
					answerMapper.insertSelective(a);
				}
				/**进行实体类赋值并进行数据库操作--end--**/
				importNum ++;
			}
		}
		//组装信息
		if(!existMessage.equals("")) {
			resultMessage =  "--"+choiceName + "导入提示： 第" + existMessage.substring(0,existMessage.length()-1) 	// 去掉最后的、号
					+ "行,共" + existNum + "条数据已存在，跳过导入。" + "成功导入"+ importNum + "条数据！";
		}else {
			resultMessage = "--"+choiceName + "导入提示： "+ "成功导入"+ importNum + "条数据！";
		}
		return resultMessage;
	}

	/**
	 * 导入判断题方法  判断题type为3
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
	private String importJudgment(Sheet sheet, int fkCourserId,int paperId) throws Exception{
		String resultMessage = "";
		String existMessage = "";
		int importNum = 0; //导入成功数量
		int existNum = 0; //已存在数量
		String choiceName = "判断题";
		int choiceType = 3;		//题目类型

		int lastRowNum = getRealLastNum(sheet); // 获得总共有多少行数据(准确)

		if (lastRowNum <= 1) {
			return choiceName + "数据为空！";
		}else {
			for (int r = 2; r <= lastRowNum; r++) {        //r = 2 表示从第三行开始循环 ，第一行(r=0)为表头，第二行为样例数据
				Row row = sheet.getRow(r);        //通过sheet表单对象得到 行对象
				if (row == null) {
					continue;
				}

				if(row.getCell(1) == null) {		//表示第二个单元格
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,题目分值不能为空)");
				}
				row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);//设置每一行的 第二个单元格的值为字符串
				String score = StringTrim(row.getCell(1).getStringCellValue()); //去掉所有空格
				BigDecimal scoreDecimal = new BigDecimal("2.0");		//默认分数
				if(!score.equals("")&&checkIsNum(score)) {		//非空且为数值
					scoreDecimal = new BigDecimal(score);
				}else if("".equals(score)) {
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,题目分值不能为空)");
				}else {
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,题目分值所填写内容数据类型有误)");
				}

				if(row.getCell(2) == null) {
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,题目内容不能为空)");
				}
				row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);//设置每一行的 第x个单元格的值为字符串
				String content = StringTrim(row.getCell(2).getStringCellValue()); //去掉所有空格
				if("".equals(content)) {
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,题目内容不能为空)");
				}

				if(row.getCell(3) == null) {
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,答案内容不能为空)");
				}
				row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);//设置每一行的 第x个单元格的值为字符串
				String answer = StringTrim(row.getCell(3).getStringCellValue()); //去掉所有空格
				if("".equals(answer)) {
					throw new MyException(choiceName + "数据导入失败(第" + (r + 1) + "行,答案内容不能为空)");
				}

				/**进行实体类赋值并进行数据库操作--start--**/
				List<Map<String,Object>> existSimList = simproblemMapper.selSimByContent(content,choiceType);		// 根据内容与题目类型查通用题是否存在
				if(!existSimList.isEmpty() && paperId == 0) {		//单纯导入通用题库的时候，对已存在的题进行跳过
					existMessage += r+1+"、"; // 记录第几行数据数据库已经存在
					existNum ++;
					continue;		//跳过此次循环（单次而已）
				}

				// 因为插入答案和选项等需要外键SimpleProblemId，所以需要查询sim表中最大的simId是多少
				int maxSimpId = simproblemMapper.selMaxSimpId();
				int newSimId = 1001;	// 数据库表空时，默认id从1001开始
				if(maxSimpId > 0) {
					newSimId = maxSimpId + 1;
				}
				Simproblem simproblem = new Simproblem();
				simproblem.setBlanks(0);
				simproblem.setContent(content);
				simproblem.setScore(scoreDecimal);
				simproblem.setType(choiceType);		//题目类型
				simproblem.setSimproblemId(newSimId);
				simproblem.setFkCourseId(fkCourserId);
				if(paperId!=0) {		// 当paperId不等0表示导入形式为导入一张试卷
					simproblem.setPaperId(paperId);
				}
				simproblemMapper.insert(simproblem);

				Answer a = new Answer();
				a.setContent(answer);
				a.setPos(0);
				a.setSimproblemId(newSimId);
				answerMapper.insertSelective(a);
				/**进行实体类赋值并进行数据库操作--end--**/
				importNum ++;
			}
		}
		//组装信息
		if(!existMessage.equals("")) {
			resultMessage =  "--"+choiceName + "导入提示： 第" + existMessage.substring(0,existMessage.length()-1) 	// 去掉最后的、号
					+ "行,共" + existNum + "条数据已存在，跳过导入。" + "成功导入"+ importNum + "条数据！";
		}else {
			resultMessage = "--"+choiceName + "导入提示： "+ "成功导入"+ importNum + "条数据！";
		}
		return resultMessage;

	}
	/**
	 * 导入简答题方法  type为5  不用导入答案 教师主观判题
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
	private String importShortAnswer(Sheet sheet, int fkCourserId,int paperId) throws Exception{
		String resultMessage = "";
		String existMessage = "";
		int importNum = 0; //导入成功数量
		int existNum = 0; //已存在数量
		String choiceName = "简答题";
		int choiceType = 5;		//题目类型

		int lastRowNum = getRealLastNum(sheet); // 获得总共有多少行数据(准确)

		if (lastRowNum <= 1) {
			return choiceName + "数据为空！";
		}else {
			for (int r = 2; r <= lastRowNum; r++) {        //r = 2 表示从第三行开始循环 ，第一行(r=0)为表头，第二行为样例数据
				Row row = sheet.getRow(r);        //通过sheet表单对象得到 行对象
				if (row == null) {
					continue;
				}

				if(row.getCell(1) == null) {		//表示第二个单元格
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,题目分值不能为空)");
				}
				row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);//设置每一行的 第二个单元格的值为字符串
				String score = StringTrim(row.getCell(1).getStringCellValue()); //去掉所有空格
				BigDecimal scoreDecimal = new BigDecimal("2.0");		//默认分数
				if(!score.equals("")&&checkIsNum(score)) {		//非空且为数值
					scoreDecimal = new BigDecimal(score);
				}else if("".equals(score)) {
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,题目分值不能为空)");
				}else {
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,题目分值所填写内容数据类型有误)");
				}

				if(row.getCell(2) == null) {
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,题目内容不能为空)");
				}
				row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);//设置每一行的 第x个单元格的值为字符串
				String content = StringTrim(row.getCell(2).getStringCellValue()); //去掉所有空格
				if("".equals(content)) {
					throw new MyException(choiceName + "数据导入失败(第"+(r+1)+"行,题目内容不能为空)");
				}

				/**进行实体类赋值并进行数据库操作--start--**/
				List<Map<String,Object>> existSimList = simproblemMapper.selSimByContent(content,choiceType);		// 根据内容与题目类型查通用题是否存在
				if(!existSimList.isEmpty() && paperId == 0) {		//单纯导入通用题库的时候，对已存在的题进行跳过
					existMessage += r+1+"、"; // 记录第几行数据数据库已经存在
					existNum ++;
					continue;		//跳过此次循环（单次而已）
				}

				// 因为插入答案和选项等需要外键SimpleProblemId，所以需要查询sim表中最大的simId是多少
				int maxSimpId = simproblemMapper.selMaxSimpId();
				int newSimId = 1001;	// 数据库表空时，默认id从1001开始
				if(maxSimpId > 0) {
					newSimId = maxSimpId + 1;
				}
				Simproblem simproblem = new Simproblem();
				simproblem.setBlanks(0);
				simproblem.setContent(content);
				simproblem.setScore(scoreDecimal);
				simproblem.setType(choiceType);		//题目类型
				simproblem.setSimproblemId(newSimId);
				simproblem.setFkCourseId(fkCourserId);
				if(paperId!=0) {		// 当paperId不等0表示导入形式为导入一张试卷
					simproblem.setPaperId(paperId);
				}
				simproblemMapper.insert(simproblem);

				/**进行实体类赋值并进行数据库操作--end--**/
				importNum ++;
			}
		}
		//组装信息
		if(!existMessage.equals("")) {
			resultMessage =  "--"+choiceName + "导入提示： 第" + existMessage.substring(0,existMessage.length()-1) 	// 去掉最后的、号
					+ "行,共" + existNum + "条数据已存在，跳过导入。" + "成功导入"+ importNum + "条数据！";
		}else {
			resultMessage = "--"+choiceName + "导入提示： "+ "成功导入"+ importNum + "条数据！";
		}
		return resultMessage;
	}


	/**
	 * 校验是否为数字复用方法（包括正负数和小数等）
	 * @param str	需要检验的字符串
	 * @return	true、false
	 */
	private boolean checkIsNum(String str) {
		//校验是否为数字格式(正负包括小数等)
		String pattern = "^[\\+\\-]?[\\d]+(\\.[\\d]+)?$";
		Pattern p = Pattern.compile(pattern);
		Matcher isNum = p.matcher(str);

		return isNum.matches();
	}

	/**
	 * @Description: 自定义去空格 excel专用     poi导入的字符串空格ASCll值：160，而空格（Space）的ASCII码值是：32
	 * @Author:
	 * @Date: 14:44 2019/4/1
	 */
	public static String StringTrim(String str){
		return str.replaceAll("[\\s\\u00A0]+","").trim();
	}

	/**
	 * 根据传进来的sheet的第一列，题目序号是否有值来判断正确的行数
	 * @param sheet
	 * @return
	 */
	private int getRealLastNum(Sheet sheet) {
		int lastRowNum = sheet.getLastRowNum(); // 获得总共有多少行数据(不准确)

		//遍历每一行第一个单元格内容，有内容则表示这一行是需要导入
		for (int r = 0; r <= lastRowNum; r++) {
			Row row = sheet.getRow(r);        //通过sheet表单对象得到 行对象
			if(row.getCell(0) == null) {
				lastRowNum = r-1;
				break;
			}
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
			String rowNum = StringTrim(row.getCell(0).getStringCellValue()); //去掉所有空格
			// System.out.println("rowNum--"+rowNum);
			if("".equals(rowNum)) {
				lastRowNum = r-1;
				break;
			}
		}
		return lastRowNum;
	}


	/**
	 * 为新的试题的simproblem配置pos值
	 * @param paperId
	 * @return
	 */
	public int updateSimPosByPaperId(int paperId) {
		// 根据paperId查所有的simId，simId按照Type来排列
		List<Map<String,Object>> simIdList = simproblemMapper.selSimIdByPaperId(paperId);
		// 根据simId的集合的大小，从1开始来插入pos
		if(!simIdList.isEmpty()) {
			for(int i=1; i<=simIdList.size(); i++) {
				String simId = simIdList.get(i-1).get("simId").toString();
				simproblemMapper.updatePosBySimId(Integer.parseInt(simId),i);
			}
			return 1;
		}
		return 0;
	}
}
