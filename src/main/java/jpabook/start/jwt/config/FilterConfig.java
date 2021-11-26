package jpabook.start.jwt.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jpabook.start.jwt.filter.MyFiler1;
import jpabook.start.jwt.filter.MyFiler2;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<MyFiler1> filter1(){
		FilterRegistrationBean<MyFiler1> bean = new FilterRegistrationBean<>(new MyFiler1());
		bean.addUrlPatterns("/*");
		bean.setOrder(1);//낮은 번호가 필터중에서 가장 먼저 시행됨.
		return bean;
	}
	@Bean
	public FilterRegistrationBean<MyFiler2> filter2(){
		FilterRegistrationBean<MyFiler2> bean = new FilterRegistrationBean<>(new MyFiler2());
		bean.addUrlPatterns("/*");
		bean.setOrder(0);//낮은 번호가 필터중에서 가장 먼저 시행됨.
		return bean;
		
	}
}
