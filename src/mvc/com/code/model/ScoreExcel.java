package com.code.model;


/**
 * 用于导出的年级成绩表实体类
 * @author zzs
 *
 */
public class ScoreExcel {
	private String id;
	
	String studentId;
	
	String name;
	
	String contestName;
	
	String className;
	
	String score;
	
	public ScoreExcel() {
		
	}
	
	public ScoreExcel(String studentId, String name, String contestNname, String className, String score) {
		super();
		this.studentId = studentId;
		this.name = name;
		this.contestName = contestName;
		this.className = className;
		this.score = score;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getContestName() {
		return contestName;
	}
	public void setContestName(String contestNname) {
		this.contestName = contestNname;
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
