package com.itheima.reggie.interceptor;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：sean
 * @date ：Created in 2022/4/17
 * @description ：拦截器实现登录检查，但只能检查控制器
 * @version: 1.0
 */
//@Component
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("[拦截检查,控制器执行前 preHandle:{}]",request.getRequestURI());
		//4、判断登录状态，如果已登录，则直接放行
		if(request.getSession().getAttribute("employee") != null){
			log.info("[登录检查，已登录 id:{}]",request.getSession().getAttribute("employee"));
			return true;
		}
		log.info("[登录失败，未授权....]");
		//5、如果未登录则返回未登录结果
		response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.info("[拦截检查,控制器执行后，postHandle]");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		log.info("[拦截检查,控制器执行完成，afterCompletion]");
	}
}
