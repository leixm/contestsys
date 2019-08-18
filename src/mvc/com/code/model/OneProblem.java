/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 编程题类-用于出题、做题
 * */
package com.code.model;

public class OneProblem {
	
	private Integer problemId;
	
	public Integer getProblemId() {
		return problemId;
	}
	public void setProblemId(Integer problemId) {
		this.problemId = problemId;
	}
	private ProblemWithBLOBs problem;  //题目信息
	
    private SolutionWithBLOBs solution; //作答信息
	
	public ProblemWithBLOBs getProblem() {
		return problem;
	}
	public void setProblem(ProblemWithBLOBs problem) {
		this.problem = problem;
	}
	public SolutionWithBLOBs getSolution() {
		return solution;
	}
	public void setSolution(SolutionWithBLOBs solution) {
		this.solution = solution;
	}
	
	 
	
}
