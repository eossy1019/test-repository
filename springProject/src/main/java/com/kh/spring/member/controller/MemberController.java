package com.kh.spring.member.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.common.scheduler.service.SchedulerTest;
import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

@Controller //Controller 타입의 어노테이션을 부여하면 spring이 bean scan을 통해서 Controller bean으로 등록해준다.
public class MemberController {
	
	//사용할 Service 객체를 선언해놓고 호출해서 사용하기
	//private MemberService service = new MemberServiceImpl();
	/*
	 * 기존 객체 생성 방식
	 * 객체간의 결합도가 높다.(소스코드의 수정이 일어날 경우 직접 찾아서 변경해야함)
	 * 서비스가 동시에 많은 횟수가 요청될 경우 그만큼 객체 생성이 발생한다.
	 * 
	 *  Spring의 DI(Dependency Injection)를 이용한 방식
	 *  객체를 생성해서 주입해준다(객체간의 결합도를 낮춰줌)
	 *  new 라는 키워드 없이 선언문만 사용해도 되지만 @Autowired라는 어노테이션을 반드시 사용해야한다.
	 * 
	 * @Autowired : 스프링이 Bean을 자동으로 주입할 수 있도록 하는 어노테이션.
	 * 
	 * */
	
	@Autowired
	private MemberService memberService;
	
	
	@Autowired
	private BCryptPasswordEncoder bcrytPasswordEncoder;
	
	
	//예약 스케줄러 등록 
	@Autowired
	//private TaskScheduler taskScheduler2; - 가 등록되어 있는 서비스 등록하여 사용하기
	private SchedulerTest sc; //--위에 작성한 TaskScheduler를 이용한 메소드가 정의되어있는 Service SchedulerTest를 사용
	
	
	/*
	 * 각 요청 매핑에 따라서 메소드로 기능을 구현한다.
	 * @RequestMapping() 어노테이션에 각 매핑주소를 넣게되면 해당 매핑요청이 왔을때 메소드가 호출되어 요청을 처리하게 된다.
	 * */
	
	
	/*
	 * Spring에서 파라미터(요청시 전달데이터)를 받아주는 방법 
	 * 
	 *  1.HttpServletRequest 이용해보기 - 기존 jsp/servlet에서 하던 방식
	 *    요청을 처리하는 메소드의 매개변수에 필요한 객체를 입력하면 spring이 메소드 호출 시 자동으로 해당 객체를 생성해서 주입해준다.
	 * 
	 * */
	/*
	@RequestMapping("login.me")
	public String loginMember(HttpServletRequest request) {
		
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		
		System.out.println(userId);
		System.out.println(userPwd);
		
		//반환값으로 응답하고자하는 view의 이름을 반환하거나 (forward)
		//redirect: 와함께 경로 반환하기(재요청)
		// 기존 : /WEB-INF/views/main.jsp 
		// spring : main 
		return "main";
	}
	*/
	
	/*
	 * 2. @RequestParam 어노테이션을 이용하는 방법
	 * 	 request.getParameter("키")로 벨류를 뽑아오는 역할을 대신 수행해주는 어노테이션
	 * 	 value 속성의 값으로 jsp에서 작성했던 name속성값을 담으면 해당 매개변수로 받아올 수 있다.
	 * 	 만약,넘어온 값이 비어있는 형태라면 defaultValue 속성으로 기본값을 지정해줄 수 있다.
	 * */
	/*
	@RequestMapping("login.me")
	public String loginMember( @RequestParam(value="userId",defaultValue = "aaa")String userId,
							   @RequestParam(value="userPwd")String userPwd) {
		
		System.out.println(userId);
		System.out.println(userPwd);
		
		return "main";
	}
	*/
	
	
	/*
	 * 3. @Reuqestparam 어노테이션을 생략하는 방법
	 * 	 단, 매개변수명을 jsp의 name 속성값과 일치시켜야만 해당 데이터가 파싱되어 들어온다.
	 * 	 또한 어노테이션을 생략했기 때문에 위에 작성했던 defaultValue속성은 사용할 수 없음.
	 * */
	/*
	@RequestMapping("login.me")
	public String loginMember(String userId,String userPwd,String email) {
		
		System.out.println(userId);
		System.out.println(userPwd);
		System.out.println(email);//null - 없는 키값에 대해서는 null  
		
		Member m = new Member();
		m.setUserId(userId);
		m.setUserPwd(userPwd);
		m.setEmail(email);
		
		return "main";
	}
	*/
	
	/*
	 * 4.커맨드 객체 방식 
	 *  해당 메소드의 매개변수로 
	 *  요청시 전달값을 담고자 하는 VO 클래스 타입을 세팅 후 
	 *  요청시 전달값의 키값 (jsp의 name 속성값) 을 VO 클래스에 담고자하는 필드명으로 작성
	 *  
	 *  스프링 컨테이너가 해당 객체를 기본생성자로 생성 후 내부적으로 setter 메소드를 찾아서 
	 *  요청 시 전달값을 해당 필드에 담아주는 내부적인 원리
	 *  
	 *  반드시 name 속성값과 담고자 하는 필드명이 동일해야한다.
	 * 
	 * */
	
	
	/*
	 * 요청 처리 후 응답데이터를 담고 응답페이지로 포워딩 또는 url 재요청하는 방법
	 *  
	 *  HttpSession이 필요하다면 메소드 매개변수에 넣어주면 스프링 컨테이너가 자동으로 객체를 주입해준다.
	 *  
	 *  
	 * 1.스프링에서 제공하는 Model객체를 사용하는 방법
	 * 	 포워딩할 응답뷰로 전달하고자 하는 데이터를 맵 형식 (key-value)으로 담을 수 있는 영역
	 * 	 Model객체는 requestScope 이다
	 *   단, setAttribute가 아닌 addAttribute 메소드를 이용한다.
	 * 
	 * 
	 * */
	/*
	@RequestMapping("login.me")
	public String loginMember(Member m
						     ,HttpSession session
						     ,Model model) {
		
		Member loginUser = memberService.loginMember(m);
		
		
		if(loginUser == null) {//로그인 실패 
			//오류 메시지를 에러페이지로 전달해보기 (위임) model 객체 이용하여
			
			model.addAttribute("errorMsg","로그인 실패!!");
			
			
//			  포워딩 방식 (파일 경로를 포함한 파일명 제시)
//			  servlet-context.xml의 주소 자동완성 도구 - viewResolver 
//			  -접두어 : /WEB-INF/views/
//			  -접미어 : .jsp 
//			  -중간값만 작성하면 됨
//			  에러페이지 경로 : /WEB-INF/views/common/errorPage.jsp 
			  
			  
			
			return "common/errorPage"; // viewResolver가 /WEB-INF/views/common/errorPage.jsp 경로로 만들어준다.
		
		}else {//로그인 성공 - session에 로그인유저정보 담고 메인페이지로 재요청
			
			session.setAttribute("loginUser", loginUser);
			session.setAttribute("alertMsg", "환영합니다. "+loginUser.getUserId()+"님!");
			
			//재요청
			return "redirect:/"; // contextPath뒤에 있는 / 즉 메인페이지로 요청됨
		}
	}
	*/
	
	
	/*
	 * 스프링에서 제공하는 ModelAndView 객체를 이용하여 응답해보기 
	 * Model은 데이터를 key-value 세트로 담을 수 있는 공간이라고 한다면
	 * View는 응답뷰에 대한 정보를 담을 수 있는 공간이다.
	 * 이 경우에는 반환형을 String이 아닌 ModelAndView 형태로 작성하여 return 한다.
	 * 
	 * Model과 View가 결합된 형태의 객체
	 * 단, Model객체는 단독 사용 가능하지만 View 객체는 단독 사용 불가 (ModelAndView만 가능)
	 * */
	
	//로그인 메소드 암호화 작업 후 
	@RequestMapping("login.me")
	public ModelAndView loginMember(Member m
							 ,String saveId
						     ,HttpSession session
						     ,ModelAndView mv
						     ,HttpServletResponse response) {
		
		//아이디 저장 체크박스가 체크되어있는지 판별하는법 saveId  != null 
		//체크가 되어 넘어왔다면 쿠키를 만들어서 해당 아이디값을 쿠키에 저장하고 
		//체크가 되어있지 않았다면 기존에 있던 쿠키를 만료시켜서 아이디 저장을 해제한다.
		
		/*
		 * 쿠키 (Cookie)
		 * 서버에 저장되지 않고 클라이언트에 저장되는 문자열 기반 저장소
		 * 키와 벨류 세트로 이루어진 데이터이다.
		 * 사용자가 요청하지 않아도 브라우저가 request시 header에 추가하여 서버에 전송된다.
		 * 서버와 뷰에서 모두 접근 가능함 (접근범위 설정가능)
		 * 만료시간을 설정 가능하고 문자열만 저장이 가능하다.
		 * 
		 * */
		
		Cookie cookie;//쿠키 선언
		
		//아이디저장이 체크되었다면 
		if(saveId!=null) {

			//사용자가 입력한 아이디값을 userId라는 이름의 쿠키에 저장하기 
			cookie = new Cookie("userId",m.getUserId());//쿠키는 생성시 이름과 값이 있어야한다. 
			//쿠키의 수명 
			cookie.setMaxAge(60*60*24); //초단위 표현 (하루)
			
			//쿠키를 응답 객체(response)에 담아서 전달
			response.addCookie(cookie); 
		}else {
			//체크가 해제되었다면 기존 쿠키 삭제 
			cookie = new Cookie("userId",null);
			cookie.setMaxAge(0); //수명 0으로
			
			response.addCookie(cookie); //응답객체에 담아주기
		}
		
		//평문 비밀번호를 전달받고 데이터베이스에 있는 암호문과 비교하여 
		//해당 암호문을 복호화(해독)했을때 평문이 나오는지 판별하는 도구 사용
		//BcryptPasswordEncoder 가 제공하는 matches(평문,암호문) 메소드를 이용한다.
		//내부적으로 복호화 작업이 진행되고 두 구문이 일치하는지 판별하여 true 또는 false를 반환한다.
		
		//아이디만 이용하여 데이터베이스에 해당 아이디에 대한 정보가 있는지 판별 후 있으면 조회해오기 
		Member loginUser = memberService.loginMember(m);
		
		//System.out.println("데이터 베이스에서 조회한 회원 비밀번호 : "+loginUser.getUserPwd());
		
		//System.out.println(bcrytPasswordEncoder.matches(m.getUserPwd(), loginUser.getUserPwd()));
		
		if(loginUser!=null && bcrytPasswordEncoder.matches(m.getUserPwd(), loginUser.getUserPwd())) {//로그인 성공
			session.setAttribute("loginUser", loginUser);
			session.setAttribute("alertMsg", "환영합니다. "+loginUser.getUserId()+"님!");
			
			//modelAndView객체에 view 정보 담아주기
			//return "redirect:/";
			mv.setViewName("redirect:/");
			
			//로그인 성공시 예약기능 사용
			//sc.taskTest(); //3초뒤 log찍기 기능
			//sc.scTriggerTest();//트리거 스케줄 사용
			//sc.scTriggerTest2();//custom 트리거 스케줄 사용
			
		}else {//로그인 실패
			
			//modelAndView객체에 데이터 담기 
			//model.addAttribute("errorMsg","로그인 실패!!");
			//mv.addObject("errorMsg", "로그인 실패!!");
						
			//return "common/errorPage";
			//mv.setViewName("common/errorPage");
			
			
			//페이지에서 쿠키 설정 갱신을 위해 재요청 방식으로 하기 
			mv.setViewName("redirect:/");
			session.setAttribute("alertMsg", "로그인 실패!!");
			
		}
		
		return mv;//데이터와 뷰정보가 담긴 ModelAndView 객체 반환
	}
	
	//로그아웃 메소드
	//@RequestMapping("logout.me")
	//@GetMapping()
	//@PostMapping()
	@RequestMapping(value="logout.me",method = RequestMethod.GET)
	public String logoutMember(HttpSession session) {
		
		//로그인유저정보 지우기
		session.removeAttribute("loginUser");
		
		return "redirect:/";//메인페이지로 보내기
	}
	
	
	
	//회원 가입폼 이동 메소드 
	//@RequestMapping(value="insert.me",method = RequestMethod.GET)
	@GetMapping("insert.me")
	public String enrollForm() {
		//회원가입 페이지로 포워딩 
		//이동경로 /WEB-INF/views/member/memberEnrollForm.jsp 
		return "member/memberEnrollForm";
	}
	
	
	//회원 가입 메소드 
	//@RequestMapping(value="insert.me",method = RequestMethod.POST)
	@PostMapping("insert.me")
	public String insertMember(Member m
							,HttpSession session
							,Model model) {
		
		//나이를 입력하지 않은 경우 int자료형인 age필드에 "" 빈문자열이 들어가려고 하기 때문에 
		//오류가 발생한다. 파라미터값은 문자열로 넘어오기 때문.
		//Member VO의 age 필드를 String으로 변경하기 
		
		//비밀번호가 사용자가 입력한 그대로 저장되는것을 방지하기.
		//Bcrypt 암호화 방식을 이용하여 암호문으로 저장하여 보안성을 유지한다
		//1)스프링 시큐리티 모듈에서 제공 (pom.xml에 DI 추가하기)
		//2)BCryptPasswordEncoder 클래스를 xml파일에 bean 으로 등록하기
		//3)web.xml 에 spring-security.xml 파일을 로딩할 수 있도록 추가하기.
		
		//암호화 작업 해보기 
		//System.out.println("암호화 되기 전 평문 비밀번호 : "+m.getUserPwd());
		
		//암호화작업 : bcryptPasswordEncoder.encode(평문)
		String encPwd = bcrytPasswordEncoder.encode(m.getUserPwd());
		
		//System.out.println("암호화 된 비밀번호 : "+encPwd);
		
		//암호화된 비밀번호를 Member에 담고 등록 처리 해보기 
		m.setUserPwd(encPwd);
		
		//서비스에 회원가입 메소드 호출 및 전달 
		int result = memberService.insertMember(m);
		
		if(result>0) { //회원가입 성공시 - 메인페이지요청
			
			session.setAttribute("alertMsg", "성공적으로 회원가입 되었습니다.");
			return "redirect:/";
			
		}else {//회원가입 실패시 에러페이지로 포워딩
			
			model.addAttribute("errorMsg","회원 가입 실패!!");
			
			return "common/errorPage";
		}
	}
	
	
	//마이페이지 이동 메소드
	@RequestMapping("mypage.me")
	public String myPage() {
		
		return "member/mypage";
	}
	
	
	//정보 수정 메소드 
	@RequestMapping("update.me")
	public ModelAndView updateMember(Member m,
									 ModelAndView mv,
									 HttpSession session) {
		
		
		//update
		int result = memberService.updateMember(m);
		
		//성공시 갱신된 정보로 마이페이지 이동하기 
		if(result>0) {
			//세션에 담겨있던 기존 정보 갱신하기 
			//변경된 데이터 다시 조회해오기 (기존에 로그인 사용할때 만들어둔 loginMember() 메소드 이용)
			Member updateMem = memberService.loginMember(m);
			
			//세션에 갱신하기 loginUser에 조회해온 객체 담기
			session.setAttribute("loginUser", updateMem);
			session.setAttribute("alertMsg", "정보 수정 성공!");
			
			//응답 뷰정보 담기
			mv.setViewName("redirect:mypage.me");
		}else {
			//실패시 에러페이지로 이동 
//			mv.addObject("errorMsg","정보 수정 실패!");
//			mv.setViewName("common/errorPage");
			
			//modelandview 객체에 전달데이터와 뷰 정보를 담아야할때 메소드체이닝을 이용하여 연속적으로 작성 가능 (단, 뷰정보 마지막에 넣을것)
			mv.addObject("errorMsg","정보 수정 실패!").setViewName("common/errorPage");
		}
		
		return mv;
	}
	
	
	//회원 탈퇴 메소드
	@RequestMapping("delete.me")
	public String deleteMember(String userPwd
							  ,HttpSession session) {
		
		//사용자의 아이디를 이용하여 데이터베이스에서 회원정보 조회해오고 
		//전달받은 평문(비밀번호)와 데이터베이스에서 조회한 암호문(비밀번호)를 비교하여
		//일치하면 탈퇴처리가 되도록 판별한다.
		
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		//로그인할때 데이터 베이스에서 회원 비밀번호도 조회해왔으니 
		//세션에 있는 로그인 정보에서 비밀번호 추출(암호문)
		
		//평문과 암호문을 비교하여 같은지 판별하는 메소드 bcryptPasswordEncoder.matches(평문,암호문) 이용
		
		if(bcrytPasswordEncoder.matches(userPwd, loginUser.getUserPwd())) { //평문과 암호문을 비교했을때 같다면 
			
			int result = memberService.deleteMember(loginUser.getUserId()); //아이디로 회원정보 삭제하기 
			
			if(result>0) {//성공시
				
				session.setAttribute("alertMsg", "회원 탈퇴가 완료되었습니다.");
				session.removeAttribute("loginUser"); //회원 정보 세션에서 지우기
				
				return "redirect:/";//메인으로
			}else {//실패시
				session.setAttribute("alertMsg", "회원 탈퇴 실패");
				
				return "redirect:mypage.me"; //마이페이지로
			}
			
		}else {
			session.setAttribute("alertMsg", "비밀번호를 잘못 입력하셨습니다.");
			
			return "redirect:/mypage.me"; //마이페이지로
		}
		
	}
	
	
	//아이디 중복 체크 메소드 
	@ResponseBody //데이터로 반환하기 위한 어노테이션
	@RequestMapping(value="/idCheck.me",produces = "text/html;charset=UTF-8")
	public String idCheck(String inputId) {
		
		//전달받은 아이디로 중복체크
		/*
		 * 기존에 사용했던 loginMember 메소드 재활용
		Member m = new Member();
		m.setUserId(inputId);
		
		Member user = memberService.loginMember(m);
		
		System.out.println(user);
		*/
		
		//해당 입력 아이디와 일치하는 정보가 존재하는지 판별용 변수
		int result = memberService.idCheck(inputId);
		
		String responseStr = "";
		
		if(result>0) {//중복(사용불가)
			responseStr = "NNNNN";
			
		}else {//사용 가능
			responseStr = "NNNNY";
		}
		
		return responseStr;
	}
	
	
	@ResponseBody
	@RequestMapping(value="loginMemberTest",produces = "application/json;charset=UTF-8")
	//요청시 body영역에 담은 데이터 추출하기 위한 어노테이션 @RequestBody
	public Member loginMemberTest(@RequestBody Member m) { 
		//아이디만 이용하여 데이터베이스에 해당 아이디에 대한 정보가 있는지 판별 후 있으면 조회해오기 
		System.out.println(m);
		Member loginUser = memberService.loginMember(m);
		
		System.out.println("데이터 베이스에서 조회한 회원 비밀번호 : "+loginUser.getUserPwd());
		
		System.out.println(bcrytPasswordEncoder.matches(m.getUserPwd(), loginUser.getUserPwd()));
		
		if(loginUser!=null && bcrytPasswordEncoder.matches(m.getUserPwd(), loginUser.getUserPwd())) {//로그인 성공
			
			return loginUser;
		}else {//로그인 실패
			return loginUser;
		}
	
	}
}
