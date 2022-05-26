package com.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：sean
 * @date ：Created in 2022/4/17
 * @description ：
 * @version: 1.0
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter")
public class LoginCheckFilter implements Filter {
	private final AntPathMatcher pathMatcher = new AntPathMatcher();
	private String[] authUrl = new String[]{
		"/employee/login",
		"/employee/logout",
		"/common/**",
		"/backend/**",
		"/front/**"
	};

	@Override
	public void doFilter(ServletRequest servletRequest,
						 ServletResponse servletResponse,
						 FilterChain filterChain) throws IOException, ServletException {
		//过滤器的处理逻辑
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		//1、获取本次请求的URI
		String requestURI = request.getRequestURI();
		log.info("[拦截请求:{}]",requestURI);
		//2、判断本次请求是否需要处理
		boolean isAuth = checkAuthUrl(requestURI);
		//3、如果不需要处理，则直接放行
		if(isAuth){
			log.info("[拦截请求-放行-{}]",requestURI);
			filterChain.doFilter(request,response);
			return;
		}
		//4、判断登录状态，如果已登录，则直接放行
		if(request.getSession().getAttribute("employee") != null){
			log.info("[登录检查，已登录 id:{}]",request.getSession().getAttribute("employee"));
			BaseContext.setCurrentId((Long)request.getSession().getAttribute("employee"));
			filterChain.doFilter(request,response);
			return;
		}
		log.info("[登录失败，未授权....]");
		//5、如果未登录则返回未登录结果
		response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
	}

	private boolean checkAuthUrl(String requestURI) {
		for (String url:authUrl){
			if(pathMatcher.match(url,requestURI)){
				return true;
			}
		}
		return false;
	}
}
