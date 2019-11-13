/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 答案类
 * */
package com.code.model;

public class Answer {
    private Integer answerId;

    private Integer simproblemId;

    private Integer pos;

    private String content;

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public Integer getSimproblemId() {
        return simproblemId;
    }

    public void setSimproblemId(Integer simproblemId) {
        this.simproblemId = simproblemId;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}