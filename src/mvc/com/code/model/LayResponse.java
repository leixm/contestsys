/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 响应类（用于适应layui前端）
 * */
package com.code.model;

public class LayResponse {

	private int code;	// 0表示成功  1 表示失败  
	private String msg;	
	private int count;	
	private Object data;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
