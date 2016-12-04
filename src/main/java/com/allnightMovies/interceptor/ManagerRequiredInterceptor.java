package com.allnightMovies.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ManagerRequiredInterceptor extends HandlerInterceptorAdapter{	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("userStatus") != null && (int)session.getAttribute("userStatus") == 2) {
			return true;
		} else {
			response.sendRedirect("/movie/mainService/requiredManager");
			return false;
		}
	}
}
