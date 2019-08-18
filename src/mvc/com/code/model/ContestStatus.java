/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 考试状态类
 * */
package com.code.model;


import java.math.BigDecimal;

public class ContestStatus {
    private Integer contestStatusId;

    private Integer status;

    private String student;

    private Integer contestId;

    private BigDecimal score;

    public Integer getContestStatusId() {
        return contestStatusId;
    }

    public void setContestStatusId(Integer contestStatusId) {
        this.contestStatusId = contestStatusId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student == null ? null : student.trim();
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }
}