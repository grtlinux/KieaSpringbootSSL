package org.tain.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.tain.config.interceptor.TestInterceptor;
import org.tain.utils.CurrentInfo;

import lombok.extern.java.Log;

@Configuration
@Log
public class InterceptorConfig implements WebMvcConfigurer{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		log.info("KANG-20200604 >>>>> " + CurrentInfo.get());
		
		registry.addInterceptor(new TestInterceptor())
			.addPathPatterns("/test/**")
			.excludePathPatterns("/test/login/**", "/test/user/**");
		
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
