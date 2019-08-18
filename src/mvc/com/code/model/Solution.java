/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 编程题作答类
 * */
package com.code.model;

import java.math.BigDecimal;
import java.util.Date;

public class Solution {
    private Integer solutionId;

    private Integer problemId;

    private Integer contestStatusId;

    private Integer time;

    private Integer memory;

    private Date inDate;

    private Short result;

    private Byte language;

    private String ip;

    private BigDecimal passRate;

    private Integer status;

    private Date judgetime;

    public Integer getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(Integer solutionId) {
        this.solutionId = solutionId;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public Integer getContestStatusId() {
        return contestStatusId;
    }

    public void setContestStatusId(Integer contestStatusId) {
        this.contestStatusId = contestStatusId;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Short getResult() {
        return result;
    }

    public void setResult(Short result) {
        this.result = result;
    }

    public Byte getLanguage() {
        return language;
    }

    public void setLanguage(Byte language) {
        this.language = language;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public BigDecimal getPassRate() {
        return passRate;
    }

    public void setPassRate(BigDecimal passRate) {
        this.passRate = passRate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getJudgetime() {
        return judgetime;
    }

    public void setJudgetime(Date judgetime) {
        this.judgetime = judgetime;
    }
}