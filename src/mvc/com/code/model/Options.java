/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 题目选项类
 * */
package com.code.model;

public class Options {
    private Integer optionId;

    private Integer simproblemId;

    private Integer pos;

    private String content;

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
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