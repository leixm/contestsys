/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 考试类-用于出题、做题
 * */
package com.code.model;

public class OneContest {

	private Contest contest;  //考试信息 
	private OnePaper paper; //试卷内容
	private User student;  //考生信息
	public User getStudent() {
		return student;
	}
	public void setStudent(User student) {
		this.student = student;
	}
	public Contest getContest() {
		return contest;
	}
	public void setContest(Contest contest) {
		this.contest = contest;
	}
	public OnePaper getPaper() {
		return paper;
	}
	public void setPaper(OnePaper paper) {
		this.paper = paper;
	}
} 
