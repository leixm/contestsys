/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 考试状态管理服务
 * */
package com.app.service.impl;

import com.annotation.SystemServiceLog;
import com.app.dao.*;
import com.app.tools.PdfHelper;
import com.code.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContestStatusService {

    @Autowired
    private UserMapper userDao;
    @Autowired
    private ContestpaperMapper cpaperDao;
    @Autowired
    private SimproblemMapper simproblemDao;
    @Autowired
    private ProblemMapper problemDao;
    @Autowired
    private SolutionMapper solutionDao;
    @Autowired
    private ContestpaperMapper contestpaperDao;
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


    @SystemServiceLog(description = "导出试卷为PDF文件")
    public String GetContestStatusPdf(int contestStatusId){
        String path = "";
        try {
            ContestStatus contestStatus = cStatusDao.selectByPrimaryKey(contestStatusId);
            //尝试生成该试卷的答卷情况PDF
            OneContest oneContest = new OneContest();

            OnePaper paper = new OnePaper();
            Contest contest = contestDao.selectByPrimaryKey(contestStatus.getContestId());
            if(contest == null) {
                return null;
            }

            Contestpaper contestPaper = contestpaperDao.selectByPrimaryKey(contest.getPaperId());
            if(contestPaper == null) {
                return null;
            }

            paper.setContestpaper(contestPaper); //试卷信息
            oneContest.setContest(contest);  //考试信息

            User student = userDao.selectByPrimaryKey(contestStatus.getStudent());
            if(student==null) {
                return null;
            }
            oneContest.setStudent(student);  //考生信息

            //paper.setContestStatus(contestStatus);

            SimproblemExample simproblemExample = new SimproblemExample();
            SimproblemExample.Criteria simproblemExampleCriteria = simproblemExample.createCriteria();
            simproblemExampleCriteria.andPaperIdEqualTo(contest.getPaperId());
            List<Simproblem> simproblems = simproblemDao.selectByExampleWithBLOBs(simproblemExample); //该试卷所有填空选择题

            if(simproblems.size()>0) {
                List<OneSimproblem> oneProblems = new ArrayList<OneSimproblem>();
                for(Simproblem simproblem : simproblems) {
                    OneSimproblem oneProblem =  new OneSimproblem();
                    oneProblem.setSimproblem(simproblem);  //题目

                    SimsolutionExample simsolutionExample = new SimsolutionExample();
                    simsolutionExample.clear();
                    SimsolutionExample.Criteria simsolutionCriteria = simsolutionExample.createCriteria();
                    simsolutionCriteria.andSimproblemIdEqualTo(simproblem.getSimproblemId());
                    simsolutionCriteria.andContestStatusIdEqualTo(contestStatus.getContestStatusId());

                    List<Simsolution> simsolutions = simsolutionDao.selectByExampleWithBLOBs(simsolutionExample);
                    if(simsolutions.size()>0) {
                        oneProblem.setSimsolution(simsolutions.get(0)); //作答情况
                    }

                    OptionsExample optionExample = new OptionsExample();
                    OptionsExample.Criteria optionExampleCriteria = optionExample.createCriteria();
                    optionExampleCriteria.andSimproblemIdEqualTo(simproblem.getSimproblemId());
                    List<Options> options = optionDao.selectByExampleWithBLOBs(optionExample);
                    oneProblem.setOption(options);
                    AnswerExample answerExample = new AnswerExample();
                    AnswerExample.Criteria answerExampleCriteria = answerExample.createCriteria();
                    answerExampleCriteria.andSimproblemIdEqualTo(simproblem.getSimproblemId());
                    List<Answer> answers = answerDao.selectByExampleWithBLOBs(answerExample);
                    oneProblem.setAnswer(answers);

                    oneProblems.add(oneProblem);
                }
                paper.setSimp(oneProblems);
            }

            ProblemExample problemExample = new ProblemExample();
            ProblemExample.Criteria problemExampleCriteria = problemExample.createCriteria();
            problemExampleCriteria.andPaperIdEqualTo(contest.getPaperId());
            List<ProblemWithBLOBs> problems  = problemDao.selectByExampleWithBLOBs(problemExample);
            if(problems.size()>0) {
                List<OneProblem> oneProblems = new ArrayList<OneProblem>();

                for(ProblemWithBLOBs problem : problems) {
                    OneProblem oneProblem = new OneProblem();
                    oneProblem.setProblem(problem);  //题目内容

                    SolutionExample solutionExample = new SolutionExample();
                    solutionExample.clear();
                    SolutionExample.Criteria solutionCriteria = solutionExample.createCriteria();
                    solutionCriteria.andContestStatusIdEqualTo(contestStatus.getContestStatusId());
                    solutionCriteria.andProblemIdEqualTo(problem.getProblemId());
                    List<SolutionWithBLOBs> solutionList = solutionDao.selectByExampleWithBLOBs(solutionExample);
                    if(solutionList.size()>0)
                        oneProblem.setSolution(solutionList.get(0));  //作答情况

                    oneProblems.add(oneProblem);
                }

                paper.setProb(oneProblems);
            }

            oneContest.setPaper(paper);

            path = (new PdfHelper()).GeneratePDFForContestStatus(oneContest,contestStatusId,contestStatus.getScore().setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());

        } catch (Exception e) {
            e.printStackTrace();
        }
//        return PdfHelper.pdf_dir + "/" + contestStatusId + ".pdf";
        return path;
    }
}
