/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 自定义异常工具类
 * */
package com.app.tools;

public class ServiceException extends Exception {
	private static final long serialVersionUID = -1708015121235851228L;
	
	public ServiceException(String message) {
		super(message);
	}
}
