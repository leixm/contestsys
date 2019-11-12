/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 试卷类-用于出题、做题
 * */
package com.code.model;

import java.util.List;

public class OnePaper {
	private Contestpaper contestpaper;  //试卷信息
	private List<OneSimproblem> simp;   //题目列表 
	private List<OneProblem> prob;  //编程题列表
	public Contestpaper getContestpaper() {
		return contestpaper;
	}
	public void setContestpaper(Contestpaper contestpaper) {
		this.contestpaper = contestpaper;
	}
	public List<OneSimproblem> getSimp() {
		return simp;
	}
	public void setSimp(List<OneSimproblem> simp) {
		this.simp = simp;
	}
	public List<OneProblem> getProb() {
		return prob;
	}
	public void setProb(List<OneProblem> prob) {
		this.prob = prob;
	}
	
}
