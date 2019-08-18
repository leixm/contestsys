/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 简单题目作答类
 * */
package com.code.model;

import java.math.BigDecimal;

public class Simsolution {
    private Integer simsolutionId;

    private Integer simproblemId;

    private Integer contestStatusId;

    private BigDecimal score;

    private Integer status;

    private String answer;

    public Integer getSimsolutionId() {
        return simsolutionId;
    }

    public void setSimsolutionId(Integer simsolutionId) {
        this.simsolutionId = simsolutionId;
    }

    public Integer getSimproblemId() {
        return simproblemId;
    }

    public void setSimproblemId(Integer simproblemId) {
        this.simproblemId = simproblemId;
    }

    public Integer getContestStatusId() {
        return contestStatusId;
    }

    public void setContestStatusId(Integer contestStatusId) {
        this.contestStatusId = contestStatusId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }
}