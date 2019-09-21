package com.code.model;

public class ScoreExcel {
	String studentId;
	String name;
	String contestNname;
	String className;
	String score;
	
	public ScoreExcel() {
		
	}
	
	public ScoreExcel(String studentId, String name, String contestNname, String className, String score) {
		super();
		this.studentId = studentId;
		this.name = name;
		this.contestNname = contestNname;
		this.className = className;
		this.score = score;
	}
	public String getContestNname() {
		return contestNname;
	}
	public void setContestNname(String contestNname) {
		this.contestNname = contestNname;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
}
