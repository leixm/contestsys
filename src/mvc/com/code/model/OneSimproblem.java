/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 简单题目类-用于出题、做题
 * */
package com.code.model;
import java.util.List;
public class OneSimproblem {
	
	private int simproblemId;  //ID simproblem里面有 可以不管
	private Simproblem simproblem; //题目
	private List<Options> option; //选项信息 
	private Simsolution simsolution; //作答情况
 	private List<Answer> answer; //答案

	public List<Options> getOption() {
		return option;
	}
	public void setOption(List<Options> option) {
		this.option = option;
	}
	public int getSimproblemId() {
		return simproblemId;
	}
	public void setSimproblemId(int simproblemId) {
		this.simproblemId = simproblemId;
	}
	public Simproblem getSimproblem() {
		return simproblem;
	}
	public void setSimproblem(Simproblem simproblem) {
		this.simproblem = simproblem;
	}

	public Simsolution getSimsolution() {
		return simsolution;
	}
	public void setSimsolution(Simsolution simsolution) {
		this.simsolution = simsolution;
	}
	public List<Answer> getAnswer() {
		return answer;
	}
	public void setAnswer(List<Answer> answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "OneSimproblem{" +
				"simproblemId=" + simproblemId +
				", simproblem=" + simproblem +
				", option=" + option +
				", simsolution=" + simsolution +
				", answer=" + answer +
				'}';
	}
}
