/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 学生管理服务-接口
 * */
package com.app.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.code.model.Contest;
import com.code.model.ContestStatus;
import com.code.model.Contestpaper;
import com.code.model.OneProblem;
import com.code.model.OneSimproblem;
import com.code.model.Simsolution;
import com.code.model.SimsolutionExample;
import com.code.model.Solution;
import com.code.model.SolutionExample;
import com.code.model.SolutionWithBLOBs;

public interface StudentService {
	
	Contestpaper selectContestpaperByPrimaryKey(Integer paperId);
	
	Contest selectContestByPrimaryKey(Integer contestId);
	
	ContestStatus selectContestStatusByPrimaryKey(Integer contestStatusId);
	
	List<OneProblem> getProblemAndSolutionByPaperId(Integer paperId);
	
	List<OneSimproblem> getSimproblemAndOptionByPaperId(Integer paperId);
	
	int insert(SolutionWithBLOBs record);
	
	int insert(Simsolution record);

}
