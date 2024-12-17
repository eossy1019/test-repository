package com.kh.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class TestInterceptor implements HandlerInterceptor{
	
	
	
	//간섭 시점 1 : 요청 처리 전 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//request : 사용자는 무엇을 요청했는가? 
		//response : 사용자에게 보낼 정보가 있는지 
		//handler : 이 요청은 누가 처리할것인지 
		
		System.out.println("테스트 인터셉터 요청 처리 전");
		System.out.println(request.getSession().getAttribute("loginUser"));
		System.out.println(response);
		System.out.println(handler);
		
		//return true여야 요청 처리됨 
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	//간섭 시점 2 : 요청 처리 후 
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		//request : 사용자는 무엇을 요청했는가? 
		//response : 사용자에게 보낼 정보가 있는지 
		//handler : 이 요청은 누가 처리할것인지 
		//modelAndView : model - 전달 데이터 / view - 이동할 뷰페이지 정보
		
		System.out.println("테스트 인터셉터 요청 처리 후");
		System.out.println(request.getSession().getAttribute("loginUser"));
		System.out.println(handler);
		System.out.println(modelAndView);
		
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	//간섭 시점 3 : 사용자에게 출력 되기 직전
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		//request : 사용자는 무엇을 요청했는가? 
		//response : 사용자에게 보낼 정보가 있는지 
		//handler : 이 요청은 누가 처리할것인지 
		//exception : 처리과정중 예외가 발생했는지 
		
		System.out.println("테스트 인터셉터 사용자에게 출력 직전");
		System.out.println(ex);
		
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	
	

}
