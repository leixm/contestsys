package com.code.model;

import java.util.Date;

public class Course {
	int courseId;
	String courseName;
	Date courseCreatetime;
	
	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Date getCourseCreatetime() {
		return courseCreatetime;
	}

	public void setCourseCreatetime(Date courseCreatetime) {
		this.courseCreatetime = courseCreatetime;
	}
	
	
}
