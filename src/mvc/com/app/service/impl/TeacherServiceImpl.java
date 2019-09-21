/** 
 * @author zzs
 * @create_date 2019.8.1
 * @description 教师相关业务服务
 **/
package com.app.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.map.HashedMap;
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
import com.code.model.ProblemExample;
import com.code.model.ProblemWithBLOBs;
import com.code.model.Response;
import com.code.model.ScoreExcel;
import com.code.model.Simproblem;
import com.code.model.SimproblemExample;
import com.code.model.Simsolution;
import com.code.model.SimsolutionExample;
import com.code.model.User;
import com.code.model.UserExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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

	// 输入文件和输出文件存放地址
//	private String InputAndOutputDataDir = "F:/test_data";
	private String InputAndOutputDataDir = "/home/judge/data";

	/**
	 * @author zzs
	 * @param contest:Contest实体
	 * @param class:班级名称
	 * @description 添加一场考试
	 * @return 状态响应
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
	 * @author zzs
	 * @param user:用户实例
	 * @description 查询老师出的所有卷子 
	 * @return 状态响应
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
	 * @author zzs
	 * @param newpaper:OnePaper实例
	 * @param user:用户实例
	 * @param oneSimps:List<OneSimproblem>实例
	 * @description 添加新的试卷
	 * @return 状态响应
	 */
	@Override
	public Response addNewpaper(OnePaper newpaper,User user,List<OneSimproblem> oneSimps,String basePath) {
		Response resp = new Response();
		int maxSimpId;
		int maxProbId;
		
		if(simpDao.selSimpCount()>0) {
			maxSimpId = simpDao.selMaxSimpId();
		}else {
			maxSimpId = 1000;
		}
		
		if(probDao.selProbCount()>0) {
			maxProbId = probDao.selMaxProbId();
		}else {
			maxProbId = 1000;
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
						//此时的ProbId
						int nowProbId = ++maxProbId;
						prob.getProblem().setProblemId(nowProbId);
						prob.getProblem().setPaperId(thisPaperId);
						probDao.insertSelective(prob.getProblem());
						//将插入的编程题的输入输出文件存放到服务器目录    格式：ProblemId-组号-in/out
						for(Entry<String, String> entry :  prob.getProblem().getFileMap().entrySet()) {
							String filename = entry.getValue();
							String groupId = entry.getKey().split("/")[0];
							String suffix = entry.getKey().split("/")[1];
							basePath = basePath.replace("\\","/");
							String srcPath =  basePath + "uploadTemp/" +  filename;
							String destPath = InputAndOutputDataDir + "/" + String.valueOf(nowProbId) + "/" + groupId + "." + suffix;
							File srcFile = new File(srcPath);
							File destFile = new File(destPath);
							if(!destFile.getParentFile().exists())
								destFile.getParentFile().mkdirs();

							try {
								copyFile(srcFile,destFile);
							} catch (Exception e) {
								e.printStackTrace();
								resp.setMsg("程序输入输出文件复制失败");
								resp.setSuccess(-1);
								return resp;
							}
						}
					 
						
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
	 * @author zzs
	 * @param user:用户实例
	 * @description 教师查询所有考试Status
	 * @return 状态响应
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
	//返回权限的level值
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
	
	/***
     * copy file
     *
     * @param src
     * @param dest
     * @throws IOException
     */
    private void copyFile(File src, File dest) throws Exception {
        BufferedInputStream reader = null;
        BufferedOutputStream writer = null;
        //自动创建没有存在的目录
        if(!dest.getParentFile().exists()) {
        	dest.getParentFile().mkdirs();
        }
        
        try {
            reader = new BufferedInputStream(new FileInputStream(src));
            writer = new BufferedOutputStream(new FileOutputStream(dest));
            byte[] buff = new byte[reader.available()];
            while ((reader.read(buff)) != -1) {
                writer.write(buff);
            }
            
        } catch (Exception e) {
            throw e;
        } finally {
            writer.flush();
            writer.close();
            reader.close();
            
            // 记录
            String temp = "\ncopy:\n" + src + "\tsize:" + src.length()
                    + "\nto:\n" + dest + "\tsize:" + dest.length()
                    + "\n complate\n totoal:";
            System.out.println(temp);
        }
    }
	
    //根据userId查询老师所任教班级对应的classid
    private List<Integer> selTeacherClassId(User user) {
    	List<Integer> userIds = new ArrayList<Integer>();
    	if(user.getLevel() == 1) {
			ClassExample classExample = new ClassExample();
			ClassExample.Criteria criteria = classExample.createCriteria();
			criteria.andTeacherEqualTo(user.getUserId());
			List<Class> classes = classDao.selectByExample(classExample);
			
			if(classes.size()>0) {
				for(Class cla : classes) {
					userIds.add(cla.getClassId());
				}
			}
			return userIds;
		}else {
			return null;
		}
    }
    
    //查询并返回某个班级的某场考试的分数集合
    private List<BigDecimal> selAllScoresFromClassContest(User user,Class cla,Contest contest) {
    	List<BigDecimal> scores = null;
		
		if(user.getLevel() == 1) {
			//根据classId查询某个班级学生
			UserExample userExample = new UserExample();
			UserExample.Criteria criteria = userExample.createCriteria();
			criteria.andClassIdEqualTo(cla.getClassId());
			List<User> users = userDao.selectByExample(userExample);
			if(users.size()>0) {
				for(User stu : users) {
					//根据学生id和contestid查询具体某场考试所有的考试情况
					ContestStatusExample cStatusExample = new ContestStatusExample();
					ContestStatusExample.Criteria criteria2 = cStatusExample.createCriteria();
					criteria2.andContestIdEqualTo(contest.getContestId());
					criteria2.andStudentEqualTo(stu.getUserId());
					List<ContestStatus> cStatuses = contestStatusDao.selectByExample(cStatusExample);
					//对考试情况进行分析,存分数进容器
					if(cStatuses.size()>0) {
						scores = new ArrayList<BigDecimal>();
						for(ContestStatus cStatus : cStatuses) {
							if(cStatus.getStatus()==1) {
								scores.add(cStatus.getScore());
							}
						}
					}
				}
			}
		}
		//没有权限查询返回null
		return null;
    }
    
    /**
	 * 查询计算所有每个班级某场考试的平均分(舍弃了0分)
	 * @param cla 具体班级
	 * @param contest 具体考试
	 * @return 状态响应
	 */
	@Override
	public Response selClassAverageScore(User user,Class cla,Contest contest) {
		Response resp = new Response();
		List<BigDecimal> scores = null;
		int num = 0;
		BigDecimal sum = new BigDecimal(0.00);
		//调用封装好的查询方法
		scores = selAllScoresFromClassContest(user, cla, contest);
		if(scores!=null) {
			for(BigDecimal score : scores) {
				//判断分数是否为0分
				if(score.compareTo(BigDecimal.ZERO)!=0) {
					sum.add(score);
					num++;
				}
			}
			//计算平均分保留两位小数
			BigDecimal result = sum.divide(new BigDecimal(num), 2,BigDecimal.ROUND_HALF_UP);
			//返回结果
			resp.setMsg("成功查询此次考试班级平均分");
			resp.setReObj(result);
			resp.setSuccess(1);
			return resp;
		}
		
		resp.setMsg("没有权限查询");
		resp.setSuccess(-1);
		return resp;
	}		
	
	/**
	 * 查询计算所有每个班级某场考试的最高分分
	 * @param cla 具体班级
	 * @param contest 具体考试
	 * @return 状态响应
	 */
	@Override
	public Response selClassHighestScore(User user,Class cla, Contest contest) {
		Response resp = new Response();
		List<BigDecimal> scores = null;
		
		if(user.getLevel() == 1) {
			//根据classId查询某个班级学生
			UserExample userExample = new UserExample();
			UserExample.Criteria criteria = userExample.createCriteria();
			criteria.andClassIdEqualTo(cla.getClassId());
			List<User> users = userDao.selectByExample(userExample);
			if(users.size()>0) {
				for(User stu : users) {
					//根据学生id和contestid查询具体某场考试所有的考试情况
					ContestStatusExample cStatusExample = new ContestStatusExample();
					ContestStatusExample.Criteria criteria2 = cStatusExample.createCriteria();
					criteria2.andContestIdEqualTo(contest.getContestId());
					criteria2.andStudentEqualTo(stu.getUserId());
					List<ContestStatus> cStatuses = contestStatusDao.selectByExample(cStatusExample);
					//对考试情况进行分析,存分数进容器
					if(cStatuses.size()>0) {
						scores = new ArrayList<BigDecimal>();
						for(ContestStatus cStatus : cStatuses) {
							if(cStatus.getStatus()==1) {
								scores.add(cStatus.getScore());
							}
						}
					}
					//获取容器分数最大值
					BigDecimal highestScore = Collections.max(scores);
					//保留两位小数
					DecimalFormat df1 = new DecimalFormat("0.00");
					String maxScore = df1.format(highestScore);
					
					resp.setReObj(maxScore);
					resp.setMsg("成功查询此次考试班级最高分");
					resp.setSuccess(1);
					return resp;
				}
			}
			
		}else {
			resp.setMsg("没有权限查询");
			resp.setSuccess(-1);
			return resp;
		}
		//程序走不到的
		return resp;
	}
		
	/**
	 * 查询计算所有每个班级某场考试的最低分
	 * @param cla 具体班级
	 * @param contest 具体考试
	 * @return 状态响应
	 */
	@Override
	public Response selClasslowestScore(User user,Class cla, Contest contest) {
		Response resp = new Response();
		List<BigDecimal> scores = null;
		//调用封装好的查询方法
		scores = selAllScoresFromClassContest(user, cla, contest);
		if(scores!=null) {
			//获取容器分数最大值
			BigDecimal lowestScore = Collections.min(scores);
			//保留两位小数
			DecimalFormat df1 = new DecimalFormat("0.00");
			String minScore = df1.format(lowestScore);
			resp.setReObj(minScore);
			resp.setMsg("成功查询此次考试班级最高分");
			resp.setSuccess(1);
			return resp;
		}
		resp.setMsg("没有权限查询");
		resp.setSuccess(-1);
		return resp;
	}

	/**
	 * 导出年级成绩表
	 * @param user	具体老师
	 * @param contest 具体考试
	 * @return 导出结果
	 */
	@Override
	public Response exportGradeScoreExcel(User user, Contest contest) {
		Response resp = new Response();
		//存放导出表格所需要的学生个人成绩信息
		List<ScoreExcel> scoreExcelList = new ArrayList<>();
		if(user.getLevel() == 1) {
			//根据学生id和contestid查询具体某场考试所有的考试情况
			ContestStatusExample cStatusExample = new ContestStatusExample();
			ContestStatusExample.Criteria criteria = cStatusExample.createCriteria();
			criteria.andContestIdEqualTo(contest.getContestId());
			List<ContestStatus> cStatuses = contestStatusDao.selectByExample(cStatusExample);
			//遍历考试的具体status
			if(cStatuses.size()>0) {
				for(ContestStatus cStatus : cStatuses) {
					if(cStatus.getStatus()==1) {
						//查询参加考试的学生
						User student = userDao.selectByPrimaryKey(cStatus.getStudent());
						if(student.getClassId()==null || "".equals(student.getClassId())) {
							continue;
						}
						//查询参加考试学生属于的班级
						Class cla = classDao.selectByPrimaryKey(student.getClassId());;
						//创建bean存数据
						ScoreExcel scoreExcel = new ScoreExcel();
						scoreExcel.setStudentId(cStatus.getStudent());
						scoreExcel.setClassName(cla.getName());
						scoreExcel.setName(student.getRealname());
						scoreExcel.setScore(cStatus.getScore().toString());
						scoreExcelList.add(scoreExcel);
					}else {
						//成绩批改中
						resp.setMsg("成绩批改中，无法导出成绩表");
						resp.setSuccess(-1);
						return resp;
					}
				}
				
				//返回结果
				resp.setSuccess(1);
				resp.setReObj(scoreExcelList);
				return resp;
			}
		}
		//没有权限查询返回null
		resp.setSuccess(-1);
		resp.setMsg("权限不足，无法进行操作");
		return resp;
	}

	/**
	 * 查询全级学生分数
	 * @param user
	 * @param contest
	 * @return 返回带有某科学生成绩信息的list<Map<String,Object>>
	 */
	@Override
	public List<Map<String,Object>>  selGradeScore(User user, Contest contest) {
		//创建存数据键值对容器对象
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> dataMap;
		
		List<ScoreExcel> scoreExcelList =  (List<ScoreExcel>) exportGradeScoreExcel(user,contest).getReObj();
		
		if(scoreExcelList.size()>0) {
			for(ScoreExcel scoreExcel : scoreExcelList) {
				dataMap = new HashedMap();
				dataMap.put("studentId", scoreExcel.getStudentId());
				dataMap.put("name", scoreExcel.getName());
				dataMap.put("className", scoreExcel.getClassName());
				dataMap.put("score", scoreExcel.getScore());
				dataMap.put("contestTitle", contest.getTitle());
				list.add(dataMap);
			}
		}
		
		return list;
	}

	/**
	 * 查询属于某个教师的所有contest对象
	 * @param 教师
	 * @return	属于同个教师的所有contest
	 */
	@Override
	public Response selAllContest(User user) {
		Response resp = new Response();
		//查权限
		int level = checkLevelService(user);
		if(level==1) {
			String id = user.getUserId();
			List<Contest> allContest = contestDao.selAllContest(id);
			resp.setSuccess(1);
			resp.setReObj(allContest);
			return resp;
		}
		
		resp.setMsg("无查询权限");
		resp.setSuccess(-1);
		return resp;
	}

	
	/**
	 * 根据搜索条件模糊查询出学生成绩表
	 * @param 1、班级名称
	 * @param 2、学生学号
	 * @param 3、学生名字
	 * @param 4、考试名称
	 * @return 成绩实体Map对象集合
	 */
	@Override
	public List<Map<String, Object>> selStuScore(String className, String stuId, String stuName, String contestName,String pageSize,String pageNumber) {
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(); //返回结果的容器
		//分页所需相关参数的计算
		if(pageSize!=null&&pageNumber!=null) {
			int pageSizeInt = Integer.parseInt(pageSize);
			int pageNumberInt = Integer.parseInt(pageNumber);
			PageHelper.startPage(pageNumberInt,pageSizeInt,true);//使用后数据库语句自动转为分页查询语句进行数据查询
		}
		resultList = contestStatusDao.selStuScoreBykeyword(className, stuId, stuName, contestName); //根据参数查询学生成绩等字段，如果参数全部为空自动查询全部学生的相关成绩
		return resultList;
	}

	/**
	 * 根据搜索条件更新学生成绩表
	 * @param cstatusid: 所更新成绩对应的表的主键id
	 * @param score: 用户重新更新的成绩
	 * @return 更新操作返回的状态
	 */
	@Override
	public int updateScore(String cStatusId, String score) {
		BigDecimal scoreDecimal = new BigDecimal(score);  
		int cStatusIdInt = Integer.parseInt(cStatusId);
		//返回更新数据总条数
		return contestStatusDao.updateScore(cStatusIdInt, scoreDecimal);
	}
	
}






















