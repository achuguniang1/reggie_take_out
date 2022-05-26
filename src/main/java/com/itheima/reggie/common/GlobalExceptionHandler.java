package com.itheima.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;


/**
 * @author ：sean
 * @date ：Created in 2022/4/17
 * @description ：
 * @version: 1.0
 */
@ControllerAdvice(annotations = {Controller.class, RestController.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {


	/* 仅适用完整性约束
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
		log.error(ex.getMessage());
		if(ex.getMessage().contains("Duplicate entry")){
			String[] split = ex.getMessage().split(" ");
			String msg = split[2] + "已存在";
			return R.error(msg);
		}
		return R.error("未知错误");
	}
	*/

	// 能适用所有数据库异常，需要自己判断情况
	@ExceptionHandler(SQLException.class)
	public R<String> exceptionHandler(SQLException ex){
		log.error("[数据库异常,{}]",ex.getMessage());
		if(ex.getMessage().contains("Duplicate entry")){
			String[] split = ex.getMessage().split(" ");
			String msg = split[2] + "已存在";
			return R.error(msg);
		}
		return R.error("未知错误");
	}

	@ExceptionHandler(CustomException.class)
	public R<String> exceptionHandler(CustomException ex){
		log.error(ex.getMessage());
		return R.error(ex.getMessage());
	}
}
