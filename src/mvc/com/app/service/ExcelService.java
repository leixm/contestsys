package com.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.code.model.LayResponse;

public interface ExcelService {
	
	
	/**
	 * 根据搜索条件模糊查询出学生成绩表
	 * @param 1、班级名称
	 * @param 2、学生学号
	 * @param 3、学生名字
	 * @param 4、考试名称
	 * @return 成绩实体Map对象集合
	 */
	public List<Map<String,Object>> selStuScoreByKeyword(String className,String stuId,String stuName,String contestName); 
	
	
	/**
	 * 
	 * 批量导入学生用户
	 * @param fileName	文件名 带文件类型结尾
	 * @param file		文件流
	 * @return		导入结果等信息
	 * @throws Exception	抛出导入带有的错误信息
	 */
	LayResponse batchImportStudent(String fileName, MultipartFile file) throws Exception;
	
	/**
	 * 
	 * 批量导入教师用户
	 * @param fileName	文件名 带文件类型结尾
	 * @param file		文件流
	 * @return		导入结果等信息
	 * @throws Exception	抛出导入带有的错误信息
	 */
	LayResponse batchImportTeacher(String fileName, MultipartFile file) throws Exception;
	
	/**
	 * 
	 * 批量导入班级
	 * @param fileName	文件名 带文件类型结尾
	 * @param file		文件流
	 * @return		导入结果等信息
	 * @throws Exception	抛出导入带有的错误信息
	 */
	LayResponse batchImportClass(String fileName, MultipartFile file) throws Exception;
}
