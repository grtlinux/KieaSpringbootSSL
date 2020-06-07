package org.tain.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.tain.config.interceptor.HomeInterceptor;

import lombok.extern.java.Log;

@Configuration
@Log
public class InterceptorConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		log.info("KANG-20200522 >>>>> InterceptorConfig.addInterceptors().....");

		registry.addInterceptor(new HomeInterceptor())
			.addPathPatterns("/home/**")
			//.excludePathPatterns("/login/**", "/user/**")
			;
		
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
