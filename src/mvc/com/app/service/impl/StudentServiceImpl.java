/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 学生管理服务
 * */
package com.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.ContestMapper;
import com.app.dao.ContestStatusMapper;
import com.app.dao.ContestpaperMapper;
import com.app.dao.ProblemMapper;
import com.app.dao.SimproblemMapper;
import com.app.dao.SimsolutionMapper;
import com.app.dao.SolutionMapper;
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

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
    private ContestpaperMapper contestpaperDao;
	
	@Autowired
    private ContestMapper ContestDao;
	
	@Autowired
	private ContestStatusMapper contestStatusDao;
	
	@Autowired
    private ProblemMapper problemDao;
	
	@Autowired
    private SimproblemMapper simproblemDao;
	
	@Autowired
    private SimsolutionMapper simsolutionDao;
	
	@Autowired
    private SolutionMapper solutionDao;
	

	@Override
	public Contestpaper selectContestpaperByPrimaryKey(Integer paperId) {
		return contestpaperDao.selectByPrimaryKey(paperId);
	}

	@Override
	public Contest selectContestByPrimaryKey(Integer contestId) {
		return ContestDao.selectByPrimaryKey(contestId) ;
	}

	@Override
	public ContestStatus selectContestStatusByPrimaryKey(Integer contestStatusId) {
		return contestStatusDao.selectByPrimaryKey(contestStatusId);
	}

	@Override
	public List<OneProblem> getProblemAndSolutionByPaperId(Integer paperId) {
		return problemDao.getProblemAndSolutionByPaperId(paperId);
	}

	@Override
	public List<OneSimproblem> getSimproblemAndOptionByPaperId(Integer paperId) {
		return simproblemDao.getSimproblemAndOptionByPaperId(paperId);
	}

	@Override
	public int insert(SolutionWithBLOBs record) {
		return solutionDao.insert(record);
	}

	@Override
	public int insert(Simsolution record) {
		return simsolutionDao.insert(record);
	}

	




	


}
