/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 班级管理服务
 * */
package com.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.app.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.ClassMapper;
import com.github.pagehelper.PageHelper;

@Service
public class ClassServiceImpl implements ClassService {

	@Autowired
	private ClassMapper classDao;
	
	
	public List GetAllClass(String teacherId,String className,String pageSize,String pageNumber){
		//分页所需相关参数的计算
		//根据参数查询学生成绩等字段，如果参数全部为空自动查询全部学生的相关成绩
		if(pageSize!=null&&pageNumber!=null) {
			int pageSizeInt = Integer.parseInt(pageSize);
			int pageNumberInt = Integer.parseInt(pageNumber);
			PageHelper.startPage(pageNumberInt,pageSizeInt,true);//使用后数据库语句自动转为分页查询语句进行数据查询
		}
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = classDao.listAllByKeyword(teacherId,className);
	    
	    return list;
	}

//	@SystemServiceLog(description = "添加班级")
	public int AddClass(com.code.model.Class cla){
		List<Map<String,Object>> resultList = classDao.listAllByKeyword(null, cla.getName());
		if(!resultList.isEmpty()) {
			return 2;
		}
		int result = classDao.insertSelective(cla);
		
		return result;
	}

//	@SystemServiceLog(description = "更新班级信息")
	public int UpdateClass(com.code.model.Class cla){
		return classDao.updateByPrimaryKeySelective(cla);
	}

//	@SystemServiceLog(description = "删除班级")
	public int DeleteClass(String id){
		return classDao.deleteByPrimaryKey(Integer.parseInt(id));
	}

//	@SystemServiceLog(description = "删除班级任课关系")
	public int deleteTeach(String classsId,String teacherId){
		int classInt = Integer.parseInt(classsId);
		return classDao.deleteTeachById(classInt,teacherId);
	}
	
	public com.code.model.Class GetClass(String id){
		return classDao.selectByPrimaryKey(Integer.parseInt(id));
	}

//	@SystemServiceLog(description = "批量删除班级")
	public int DeleteAllClass(List<String> ids)
	{  
	   int count = 0;
	   for(String id : ids){
		 count += classDao.deleteByPrimaryKey(Integer.parseInt(id));
	   } 
	   return count;
	}

//	@SystemServiceLog(description = "添加班级任课信息")
	public int AddTeach(String classId,String teacherId){
		String[] classIdArr = classId.split(",");
		String[] teacherIdArr = teacherId.split(",");
		int insertNum = 0;
		if(classIdArr.length > 0 && teacherIdArr.length > 0) {
			for(String claStr : classIdArr) {
				classId = claStr;
				for(String teaStr : teacherIdArr) {
					teacherId = teaStr;
					//校验是否已存在任课关系，有则跳过
					List<Map<String,Object>> confList = classDao.selClassConfByKeyword(classId, teacherId);
					if(!confList.isEmpty()) {
						continue;
					}
					//进行插入操作
					classDao.insertTeachByKeyword(classId,teacherId);
					insertNum ++;
				}	
			}
			return insertNum;
		}
		return 0;
	}
}
