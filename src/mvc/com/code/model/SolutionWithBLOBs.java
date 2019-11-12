/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 编程题作答BLOB类
 * */
package com.code.model;

public class SolutionWithBLOBs extends Solution {
    private String error;

    private String source;

    private String compileinfo;

    private String runtimeinfo;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error == null ? null : error.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getCompileinfo() {
        return compileinfo;
    }

    public void setCompileinfo(String compileinfo) {
        this.compileinfo = compileinfo == null ? null : compileinfo.trim();
    }

    public String getRuntimeinfo() {
        return runtimeinfo;
    }

    public void setRuntimeinfo(String runtimeinfo) {
        this.runtimeinfo = runtimeinfo == null ? null : runtimeinfo.trim();
    }
}