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
import com.app.dao.ClassMapper;
import com.app.dao.ContestStatusMapper;
import com.app.dao.UserMapper;
import com.app.service.ExcelService;
import com.code.model.LayResponse;
import com.code.model.User;
import com.github.pagehelper.PageHelper;

@Service
public class ExcelServiceImpl implements ExcelService{
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ClassMapper classMapper;
	
	@Autowired
	private ContestStatusMapper contestStatusMapper;
	
	
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
				continue;
			}
			
			//sheet.getLastRowNum() 的值是 10，所以Excel表中的数据至少是10条；不然报错 NullPointerException
			if(row.getCell(0) == null) {
				throw new MyException("导入失败(第"+(r+1)+"行,学号不能为空)");
			}
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第一个单元格的值，转为字符串
			String userId = row.getCell(0).getStringCellValue().replaceAll(" ",""); //去掉所有空格
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
			String realname = row.getCell(1).getStringCellValue().replaceAll(" ",""); //去掉所有空格
			if(realname.equals("")) {
				throw new MyException("导入失败(第"+(r+1)+"行,姓名不能为空)");
			}
			
			if(row.getCell(2) == null) {
				throw new MyException("导入失败(第"+(r+1)+"行,密码不能为空)");
			}
			row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第三个单元格的值
			String password = row.getCell(2).getStringCellValue().replaceAll(" ",""); //去掉所有空格
			if(password.equals("")) {
				throw new MyException("导入失败(第"+(r+1)+"行,密码不能为空)");
			}

			if(row.getCell(3) == null) {
				throw new MyException("导入失败(第"+(r+1)+"行,班级不能为空)");
			}
			row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第四个单元格的值
			String classname = row.getCell(3).getStringCellValue().replaceAll(" ",""); //去掉所有空格 
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
				continue ;		//跳过此次循环（单次而已）
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
		System.out.println(layResponse.getMsg());
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
				continue;
			}
			
			//sheet.getLastRowNum() 的值是 10，所以Excel表中的数据至少是10条；不然报错 NullPointerException
			
			if(row.getCell(0) == null) {
				throw new MyException("导入失败(第"+(r+1)+"行,工号不能为空)");
			}
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第一个单元格的值，转为字符串
			String userId = row.getCell(0).getStringCellValue().replaceAll(" ",""); //去掉所有空格
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
			String realname = row.getCell(1).getStringCellValue().replaceAll(" ",""); //去掉所有空格
			if(realname.equals("")) {
				throw new MyException("导入失败(第"+(r+1)+"行,姓名不能为空)");
			}
			
			if(row.getCell(2) == null) {
				throw new MyException("导入失败(第"+(r+1)+"行,密码不能为空)");
			}
			row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);//设置每一行的 第三个单元格的值为字符串
			String password = row.getCell(2).getStringCellValue().replaceAll(" ",""); //去掉所有空格
			if(password.equals("")) {
				throw new MyException("导入失败(第"+(r+1)+"行,密码不能为空)");
			}
		
			//根据userId 判断数据库是否已经存在此条数据；是则跳过，不用操作插入
			List<Map<String,Object>> userList = userMapper.selUserById(userId);
			if(!userList.isEmpty()) {
				extraMessage += r+1 + "、";  //记录第几行数据数据库已经存在
				existNum ++;
				continue ;		//跳过此次循环（单次而已）
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
		System.out.println(layResponse.getMsg());
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
				continue;
			}
			
			//sheet.getLastRowNum() 的值是 10，所以Excel表中的数据至少是10条；不然报错 NullPointerException
			
			/*if(row.getCell(1) == null) {
				throw new MyException("导入失败(第"+(r+1)+"行,工号不能为空)");
			}
			row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第一个单元格的值，转为字符串
			String userId = row.getCell(1).getStringCellValue().replaceAll(" ",""); //去掉所有空格
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
			String className = row.getCell(0).getStringCellValue().replaceAll(" ",""); //去掉所有空格
			if(className.equals("")) {
				throw new MyException("导入失败(第"+(r+1)+"行,班级名称不能为空)");
			}
			
		
			//根据班级名称是否已经存在， 判断数据库是否已经存在此条数据；是则跳过，不用操作插入
			List<Map<String,Object>> classIdList = classMapper.selByClassname(className);
			if(!classIdList.isEmpty()) {
				extraMessage += r+1 + "、";  //记录第几行数据数据库已经存在
				existNum ++;
				continue ;		//跳过此次循环（单次而已）
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
		System.out.println(layResponse.getMsg());
		return layResponse;
	}
	
}
