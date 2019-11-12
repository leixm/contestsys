/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 通用响应类
 * */
package com.code.model;

public class Response {

	private int success;
	private String msg;
	private int type;
	
	private Object reObj;
	
	public Object getReObj() {
		return reObj;
	}
	public void setReObj(Object reObj) {
		this.reObj = reObj;
	}
	public int getSuccess() {
		return success;
	}
	public void setSuccess(int success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
