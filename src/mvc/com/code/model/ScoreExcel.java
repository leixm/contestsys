package com.code.model;

public class ScoreExcel {
	String studentId;
	String name;
	String className;
	String score;
	

	public ScoreExcel() {
		
	}
	public ScoreExcel(String studentId, String name, String className, String score) {
		super();
		this.studentId = studentId;
		this.name = name;
		this.className = className;
		this.score = score;
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
