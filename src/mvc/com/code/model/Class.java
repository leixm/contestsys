/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 班级类
 * */
package com.code.model;

public class Class {
    private Integer classId;

    private String name;

    private String teacher;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher == null ? null : teacher.trim();
    }
}