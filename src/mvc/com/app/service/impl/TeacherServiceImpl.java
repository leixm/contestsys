/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 教师管理服务
 * */
package com.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.AnswerMapper;
import com.app.dao.ClassMapper;
import com.app.dao.ContestMapper;
import com.app.dao.ContestStatusMapper;
import com.app.dao.ContestpaperMapper;
import com.app.dao.OptionsMapper;
import com.app.dao.ProblemMapper;
import com.app.dao.SimproblemMapper;
import com.app.dao.SimsolutionMapper;
import com.app.dao.SolutionMapper;
import com.app.dao.UserMapper;
import com.app.service.TeacherService;
import com.code.model.Answer;
import com.code.model.AnswerExample;
import com.code.model.Class;
import com.code.model.ClassExample;
import com.code.model.Contest;
import com.code.model.ContestExample;
import com.code.model.ContestStatus;
import com.code.model.ContestStatusExample;
import com.code.model.Contestpaper;
import com.code.model.ContestpaperExample;
import com.code.model.OnePaper;
import com.code.model.OneProblem;
import com.code.model.OneSimproblem;
import com.code.model.Options;
import com.code.model.OptionsExample;
import com.code.model.Problem;
import com.code.model.ProblemExample;
import com.code.model.ProblemWithBLOBs;
import com.code.model.Response;
import com.code.model.Simproblem;
import com.code.model.SimproblemExample;
import com.code.model.Simsolution;
import com.code.model.SimsolutionExample;
import com.code.model.SolutionWithBLOBs;
import com.code.model.User;
import com.code.model.UserExample;
import com.itextpdf.text.log.SysoCounter;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService{
	@Autowired
	private UserMapper userDao;
	@Autowired
	private ContestpaperMapper cpaperDao;
	@Autowired
	private SimproblemMapper simpDao;
	@Autowired
	private ProblemMapper probDao;
	@Autowired
	private SolutionMapper soluDao;
	@Autowired
	private ContestpaperMapper paperDao;
	@Autowired
	private OptionsMapper optionDao;
	@Autowired
	private SimsolutionMapper simsolutionDao;
	@Autowired
	private AnswerMapper answerDao;
	@Autowired
	private ContestStatusMapper cStatusDao;
	@Autowired
	private ClassMapper classDao;
	@Autowired
	private ContestMapper contestDao;
	@Autowired
	private ContestStatusMapper contestStatusDao;
	
	/**
	 * 添加一门新的考试  
	 * @return 0添加考试失败  1添加考试成功  -1权限不足
	 */
	@Override
	public Response addContest(Contest contest,String className) {
		Response resp = new Response();
		//查权限
		User user = new User();
		user.setUserId(contest.getTeacher());
		int level = checkLevelService(user);
		if(level==1) {
			if(contest!=null) {
				contestDao.insertSelective(contest);
			}
			
			int classId = 0;
			//根据班级名字和老师共同定位班级Id
			ClassExample classExample = new ClassExample();
			ClassExample.Criteria criteria = classExample.createCriteria();
			criteria.andNameEqualTo(className);
			criteria.andTeacherEqualTo(contest.getTeacher());
			List<Class> classes = classDao.selectByExample(classExample);
			if(classes.size()==1) {
				classId = classes.get(0).getClassId();
			}
			//查询学生(0代表学生)
			List<User> students = selUser(classId, 0);
			ContestStatus cStatus;
			if(students!=null) {
				for(User student:students) {
					cStatus = new ContestStatus();
					cStatus.setContestId(contest.getContestId());
					cStatus.setStudent(student.getUserId());
					cStatus.setStatus(1);
					cStatusDao.insertSelective(cStatus);
				}
				resp.setMsg("添加考试成功");
				resp.setSuccess(1);
				return resp;
			}
			resp.setMsg("添加考试失败");
			resp.setSuccess(0);
			return resp;
		}
		resp.setMsg("没有权限添加试卷");
		resp.setSuccess(-1);
		return resp;
	}
	
	
	/**
	 * 查询老师出的所有卷子 
	 * @param user
	 * @return 属于同个userId的所有的卷子
	 */
	@Override
	public Response selAllpaper(User user) {
		Response resp = new Response();
		List<OnePaper> list = new ArrayList<>();
		OnePaper onepaper;
		if(user.getLevel()>0) {
			List<Contestpaper> paper = selContestpaper(user.getUserId()); 
			if(paper!=null) {
				for(Contestpaper p : paper) {
					onepaper = new OnePaper();
					onepaper.setContestpaper(p);
					onepaper.setSimp(selSimproblem(p.getPaperId()));
					onepaper.setProb(selProblem(p.getPaperId()));
					list.add(onepaper);
				}
			resp.setSuccess(1);
			resp.setMsg("查所有试卷成功");
			resp.setReObj(list);
			return resp;
			}
		}else{
			resp.setMsg("没有权限查看试卷");
			resp.setSuccess(-1);
			return resp;
		}
		resp.setMsg("查所有试卷成功失败");
		resp.setSuccess(0);
		return resp;
	}
	
	/**
	 * 添加新的试卷
	 * @param newpaper 从Response获取到的OnePaper 
	 * @return 1添加成功     0添加失败
	 */
	@Override
	public Response addNewpaper(OnePaper newpaper,User user,List<OneSimproblem> oneSimps) {
		Response resp = new Response();
		int maxSimpId;
		if(simpDao.selSimpCount()>0) {
			maxSimpId = simpDao.selMaxSimpId();
		}else {
			maxSimpId = 1000;
		}
		

		//表示当前的paperId
		int thisPaperId;
		if(paperDao.selPaperCount()>0) {
			thisPaperId = paperDao.selMaxPaperId()+1;
		}else {
			thisPaperId = 1001;
		}
		
		
		if(user.getLevel()>0) {
			if(newpaper!=null) {
				Contestpaper cPaper = newpaper.getContestpaper();
				if(cPaper!=null) {
					cPaper.setDate(null);
					cPaper.setPaperId(thisPaperId);
					cpaperDao.insertSelective(cPaper);
				}
				//List<OneSimproblem> oneSimps = newpaper.getSimp();
				List<OneProblem> oneProbs = newpaper.getProb();

				if(oneSimps!=null) {
					for(OneSimproblem simp:oneSimps) {
						if(simp.getSimproblem()!=null) {
							simp.getSimproblem().setSimproblemId(++maxSimpId);
							simp.getSimproblem().setPaperId(thisPaperId);
							simpDao.insert(simp.getSimproblem());
						}
						if(simp.getAnswer()!=null) {
							for(Answer an:simp.getAnswer()) {
								if(an!=null) {
									an.setSimproblemId(maxSimpId);
									an.setAnswerId(null);
									answerDao.insertSelective(an);
								}
							}
						}
						if(simp.getOption()!=null) {
							for(Options op:simp.getOption()) {
								if(op!=null) {
									op.setOptionId(null);
									op.setSimproblemId(maxSimpId);
									optionDao.insertSelective(op);
								}
							}
						}
					}
				}
				if(oneProbs!=null) {
					for(OneProblem prob:oneProbs) {
						prob.getProblem().setPaperId(thisPaperId);
						probDao.insertSelective(prob.getProblem());
					}
				}
				resp.setMsg("添加新的试卷成功");
				resp.setSuccess(1);
				return resp;
			}
		}else{
			resp.setMsg("没有权限添加试卷");
			resp.setSuccess(-1);
			return resp;
		}
		
		resp.setMsg("添加新的试卷失败");
		resp.setSuccess(0);
		return resp;
	}
	
	/**
	 * 教师查询所有考试Status
	 * @return Response
	 * 
	 */
	@Override
	public Response selContestStatus(User user) {
		Response resp = new Response();
		if(user.getLevel()>0) {
			//查询属于某个老师负责的考试
			ContestExample contestExample = new ContestExample();
			ContestExample.Criteria criteria = contestExample.createCriteria();
			criteria.andTeacherEqualTo(user.getUserId());
			List<Contest> contests = contestDao.selectByExample(contestExample);
			//contestStatus大容器
			List<ContestStatus> status = new ArrayList<ContestStatus>();
			
			if(contests!=null) {
				for(Contest contest:contests) {
					//根据contestId查询所有属于这场考试的contestStatus
					ContestStatusExample statusExample = new ContestStatusExample();
					ContestStatusExample.Criteria criteria1 = statusExample.createCriteria();
					criteria1.andContestIdEqualTo(contest.getContestId());
					//将所有考试Status集合在一起
					status.addAll(contestStatusDao.selectByExample(statusExample));
				}
			}
			if(status.size()>0) {
				resp.setReObj(status);
				resp.setMsg("查询contestStatus成功");
				resp.setSuccess(1);
				return resp;
			}else {
				resp.setMsg("contestStatus为空");
				resp.setSuccess(-1);
				return resp;
			}
			
		}else {
			resp.setMsg("没有权限查询contestStatus");
			resp.setSuccess(-1);
			return resp;
		}
	}
	
	
	
	
	//查权限
	private int checkLevelService(User user) {
		UserExample userExample = new UserExample();
		UserExample.Criteria criteria = userExample.createCriteria();
		criteria.andUserIdEqualTo(user.getUserId());
		
		List<User> list = userDao.selectByExample(userExample);
		if(list.size()==1) {
			return list.get(0).getLevel();
		}
		return 0;
	}
	
	//查选择填空题
	private List<OneSimproblem> selSimproblem(int paperId) {
		//创建装选择填空的对象容器
		List<OneSimproblem> list = new ArrayList<>();
		//创建目标对象
		OneSimproblem oneSim; 
		SimproblemExample simpExample = new SimproblemExample();
		SimproblemExample.Criteria criteria = simpExample.createCriteria();
		criteria.andPaperIdEqualTo(paperId);
		//获取所有属于某张试卷的选择题
		List<Simproblem> simproblem = simpDao.selectByExampleWithBLOBs(simpExample);
		if(simproblem!=null) {
			for(Simproblem sim : simproblem) {
				 oneSim = new OneSimproblem();
				 oneSim.setSimproblem(sim);
				 oneSim.setAnswer(selAnswer(sim.getSimproblemId()));
				 //获取选择选项（过滤填空题type=4是填空题）
				 if(sim.getType()!=4) {
					oneSim.setOption(selOptions(sim.getSimproblemId()));
				 }
				 //装一道题进入容器
				 list.add(oneSim);
			}
			return list;
		}
		
		return null;
	}
	
	//查编程题+答案
	private List<OneProblem> selProblem(int paperId) {
		//创建对象容器
		List<OneProblem> list = new ArrayList<>(); 
		OneProblem onePro;
		
		ProblemExample probExample = new ProblemExample();
		ProblemExample.Criteria criteria = probExample.createCriteria();
		criteria.andPaperIdEqualTo(paperId);
		//获得编程题目集合
		//List<Problem> pros = null;
		List<ProblemWithBLOBs> pros1 = probDao.selectByExampleWithBLOBs(probExample);
		//pros.addAll(pros1);
		if(pros1!=null) {
			for(ProblemWithBLOBs prob : pros1) {
				onePro = new OneProblem();
			/*	SolutionExample soluExample = new SolutionExample();
				SolutionExample.Criteria criteria2 = soluExample.createCriteria();
				criteria2.andProblemIdEqualTo(prob.getProblemId());
				
				List<Solution> solu = soluDao.selectByExample(soluExample);*/
				//题目和答案装进容器
				onePro.setProblemId(prob.getProblemId());
				onePro.setProblem(prob);
				//onePro.setSolution(solu.get(0));
				list.add(onePro);
			}
			return list;
		}
		return null;
	}
		
	
	//查询卷名
	private List<Contestpaper> selContestpaper(String userId) {
		ContestpaperExample paperExample = new ContestpaperExample();
		ContestpaperExample.Criteria criteria = paperExample.createCriteria();
		//此处的teacher是一一对应userId
		criteria.andTeacherEqualTo(userId);
		
		List<Contestpaper> list = paperDao.selectByExample(paperExample);
		
		return list;
	}
	

	//查选择题选项
	private List<Options> selOptions(int simId) {
		OptionsExample optionExample = new OptionsExample();
		OptionsExample.Criteria criteria = optionExample.createCriteria();
		criteria.andSimproblemIdEqualTo(simId);
		List<Options> list = optionDao.selectByExampleWithBLOBs(optionExample);
		//List<Options> list2 = optionDao.selectByExampleWithBLOBs(optionExample);
		//list.addAll(list2);
		
		return list;
	}
	//查选择题学生作答情况
	private Simsolution selSimSolution(int simId) {
		SimsolutionExample simsolu = new SimsolutionExample();
		SimsolutionExample.Criteria criteria = simsolu.createCriteria();
		criteria.andSimproblemIdEqualTo(simId);
		List<Simsolution> list = simsolutionDao.selectByExample(simsolu);
		
		if(list.size()==1) {
			return list.get(0);
		}
		return null;
	}
	
	//查选择填空答案
	private List<Answer> selAnswer(int simId) {
		AnswerExample answerExample = new AnswerExample();
		AnswerExample.Criteria criteria = answerExample.createCriteria();
		criteria.andSimproblemIdEqualTo(simId);
		List<Answer> list = answerDao.selectByExampleWithBLOBs(answerExample);
		
		return list;
	}
	
	//查询某个班级对应的老师或学生(查学生用传0，查老师传1)
	private List<User> selUser(int classId,int level) {
		//新建用来存放筛选出来的User的容器
		List<User> users = new ArrayList<User>();
		
		UserExample userExample = new UserExample();
		UserExample.Criteria criteria = userExample.createCriteria();
		criteria.andClassIdEqualTo(classId);
		List<User> list = userDao.selectByExample(userExample);
		if(list!=null) {
			for(User user:list) {
				if(user.getLevel().equals(level)) {
					users.add(user);
				}
			}
		}
		return users;
	}

	
}
