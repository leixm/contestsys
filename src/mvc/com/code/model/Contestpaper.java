package com.code.model;

import java.util.Date;

public class Contestpaper {
    private Integer paperId;

    private String teacher;

    private String title;

    private Date date;

    private Integer papertype;

    private Integer fkCourseId;

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher == null ? null : teacher.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPapertype() {
        return papertype;
    }

    public void setPapertype(Integer papertype) {
        this.papertype = papertype;
    }

    public Integer getFkCourseId() {
        return fkCourseId;
    }

    public void setFkCourseId(Integer fkCourseId) {
        this.fkCourseId = fkCourseId;
    }
}