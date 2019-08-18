/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 简单题目类
 * */
package com.code.model;

import java.math.BigDecimal;

public class Simproblem {
    private Integer simproblemId;

    private Integer type;

    private Integer paperId;

    private BigDecimal score;

    private Integer pos;

    private Integer blanks;

    private String content;

    public Integer getSimproblemId() {
        return simproblemId;
    }

    public void setSimproblemId(Integer simproblemId) {
        this.simproblemId = simproblemId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public Integer getBlanks() {
        return blanks;
    }

    public void setBlanks(Integer blanks) {
        this.blanks = blanks;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}