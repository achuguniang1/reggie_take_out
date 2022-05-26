package com.itheima.reggie.common;

/**
 * @author ：sean
 * @date ：Created in 2022/4/18
 * @description ：
 * @version: 1.0
 */
public class BaseContext {
	private static final ThreadLocal<Long> THREAD_LOCAL_USER_ID = new ThreadLocal<>();

	/**
	 * 设置值
	 * @param id
	 */
	public static void setCurrentId(Long id){
		THREAD_LOCAL_USER_ID.set(id);
	}
	/**
	 * 获取值
	 * @return
	 */
	public static Long getCurrentId(){
		return THREAD_LOCAL_USER_ID.get();
	}
}
