/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 班级管理服务
 * */
package com.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.ClassMapper;
import com.github.pagehelper.PageHelper;

import net.sf.json.JSONArray;

@Service
public class ClassService {

	@Autowired
	private ClassMapper classDao;
	
	
	public List GetAllClass(String Keyword,String pageSize,String pageNumber){
		//分页所需相关参数的计算
		//根据参数查询学生成绩等字段，如果参数全部为空自动查询全部学生的相关成绩
		if(pageSize!=null&&pageNumber!=null) {
			int pageSizeInt = Integer.parseInt(pageSize);
			int pageNumberInt = Integer.parseInt(pageNumber);
			PageHelper.startPage(pageNumberInt,pageSizeInt,true);//使用后数据库语句自动转为分页查询语句进行数据查询
		}
        System.out.println("key=" + Keyword);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if(Keyword==null || Keyword.trim().isEmpty())
			list = classDao.listAll();

		else list = classDao.listAllByKeyword(Keyword);
	    
	    System.out.println(JSONArray.fromObject(list).toString());
	    return list;
	}
	
	public int AddClass(com.code.model.Class cla){
		return classDao.insertSelective(cla);
	}
	
	public int UpdateClass(com.code.model.Class cla){
		return classDao.updateByPrimaryKeySelective(cla);
	}
	
	public int DeleteClass(String id){
		return classDao.deleteByPrimaryKey(Integer.parseInt(id));
	}
	
	
	public com.code.model.Class GetClass(String id){
		return classDao.selectByPrimaryKey(Integer.parseInt(id));
	}
	
	public int DeleteAllClass(List<String> ids)
	{  
	   int count = 0;
	   for(String id : ids){
		 count += classDao.deleteByPrimaryKey(Integer.parseInt(id));
		 System.out.println("count=" + count + " id=" + id);
	   } 
	   return count;
	} 
	
}
