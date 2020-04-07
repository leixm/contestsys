/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 班级管理服务
 * */
package com.app.service;

import com.app.dao.ClassMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ClassService {

	public List GetAllClass(String teacherId,String className,String pageSize,String pageNumber);

	public int AddClass(com.code.model.Class cla);

	public int UpdateClass(com.code.model.Class cla);

	public int DeleteClass(String id);

	public int deleteTeach(String classsId,String teacherId);

	public com.code.model.Class GetClass(String id);

	public int DeleteAllClass(List<String> ids);

	public int AddTeach(String classId,String teacherId);
}
