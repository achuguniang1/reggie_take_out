package com.itheima.reggie.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.itheima.reggie.interceptor.LoginCheckInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author ：sean
 * @date ：Created in 2022/4/16
 * @description ：
 * @version: 1.0
 */
@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurationSupport {

	@Autowired
	private ObjectMapper objectMapper;
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
		Jackson2ObjectMapperBuilderCustomizer customizer = jacksonObjectMapperBuilder ->
			jacksonObjectMapperBuilder
				.serializerByType(Long.class, ToStringSerializer.instance)
				.serializerByType(Long.TYPE, ToStringSerializer.instance);
		return customizer;
	}


	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		log.info("[配置静态资源映射]");
		registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
		registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
	}

	@Override
	protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		log.info("扩展消息转换器");
		//创建消息转换器对象
		MappingJackson2HttpMessageConverter messageConverter
			= new MappingJackson2HttpMessageConverter();
		//设置对象转换器，底层使用Jackson将Java对象转为json
		messageConverter.setObjectMapper(new JacksonObjectMapper());
		//messageConverter.setObjectMapper(objectMapper);
		converters.add(0,messageConverter);
	}

	//@Autowired
	//private LoginCheckInterceptor loginCheckInterceptor;
	// 拦截器实现
	//@Override
	//protected void addInterceptors(InterceptorRegistry registry) {
	//	super.addInterceptors(registry);
	//	registry.addInterceptor(loginCheckInterceptor)
	//		.addPathPatterns("/**")
	//		.excludePathPatterns("/backend/**","/front/**")
	//	    .excludePathPatterns("/error","/employee/login","/employee/logout");
	//}
}
