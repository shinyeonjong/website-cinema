package com.allnightMovies;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.allnightMovies.interceptor.KeepLoginCheckInterceptor;
import com.allnightMovies.interceptor.LoginCookieAddInterceptor;
import com.allnightMovies.interceptor.LoginRequiredPageInterceptor;
import com.allnightMovies.interceptor.LogoutInterceptor;
import com.allnightMovies.interceptor.ManagerCheckInterceptor;
import com.allnightMovies.interceptor.ManagerRequiredInterceptor;


@SpringBootApplication
public class AllnightMoviesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AllnightMoviesApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(new KeepLoginCheckInterceptor()).addPathPatterns("/");
				registry.addInterceptor(new LoginCookieAddInterceptor()).addPathPatterns("/movie/mainService/login");
				registry.addInterceptor(new LogoutInterceptor()).addPathPatterns("/movie/mainService/logout");
				registry.addInterceptor(new LoginRequiredPageInterceptor()).addPathPatterns(
						"/movie/mainService/ticketing",
						"/movie/mainService/questionBoardWriteForm", 
						"/movie/mainService/viewMyInfo",
						"/movie/async/asyncService/InsertAskWriteBoard",
						"/movie/mainService/InsertAskWriteBoard",
						"/movie/mainService/questionViewBoard",
						"/movie/mainService/updateWriteForm",
						"/movie/async/asyncService/completeUPdateWriteBoard");
				registry.addInterceptor(new ManagerCheckInterceptor()).addPathPatterns("/**");
				
				registry.addInterceptor(new ManagerRequiredInterceptor()).addPathPatterns(
						"/movie/mainService/maganerMenu",
						"/movie/mainService/managerMovieInsertForm",
						"/movie/mainService/managerMovieModifyForm",
						"/movie/mainService/managerScreeningPlannedModify",
						"/movie/mainService/managementServiceCenterWriteForm",
						"/movie/mainService/managementReplyBoardForm",
						"/movie/async/asyncService/managementReplyBoardFormComplete"
						
						);
				
			}
		};
	}
}