/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 试卷类
 * */
package com.code.model;

import java.util.Date;

public class Contestpaper {
    private Integer paperId;

    private String teacher;

    private String title;

    private Date date;

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
}