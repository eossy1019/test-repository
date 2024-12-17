package com.kh.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.spring.member.model.vo.Member;

public class LoginInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//로그인 정보를 얻어와서 로그인이 되어있는지 판별 후 로그인이 되어있지 않다면 요청을 막아주기
		//회원 권한 설정
		//잘못된 접근 막기
		
		HttpSession session = request.getSession();
		
		//로그인 정보 추출
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		//로그인이 되어있는지 체크 
		if(loginUser == null) {
			session.setAttribute("alertMsg", "로그인 후 이용 가능한 서비스 입니다.");
			//응답 뷰 정보 response에 담아주기
			
			response.sendRedirect(request.getContextPath()); //메인으로 처리
			
			return false;//기존 요청 막기 
		}
		
		//return true여야 요청 흐름 유지 
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

}
