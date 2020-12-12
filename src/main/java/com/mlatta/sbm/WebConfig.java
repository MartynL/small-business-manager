package com.mlatta.sbm;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    @Description("setting index view for root path")
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("Index");
	}
    
    
    
}
