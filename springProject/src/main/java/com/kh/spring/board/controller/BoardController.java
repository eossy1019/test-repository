package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.scheduler.service.SchedulerTest;
import com.kh.spring.common.template.Pagination;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board") //해당 컨트롤러의 메소드 매핑주소는 /board 뒤에 붙게된다.
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private SchedulerTest sc;
	
	
	//목록 페이지 이동 메소드
	@GetMapping("/list")
	public String selectList(@RequestParam(value="currentPage",defaultValue="1")
							  int currentPage,Model model) {
		
		
		int listCount = boardService.listCount();
		int pageLimit = 10;
		int boardLimit = 5;
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
		
		//long start = System.currentTimeMillis();
		//게시글 목록 조회하기
		ArrayList<Board> list = boardService.selectList(pi);
		
		//long finish = System.currentTimeMillis();
		
		//log.debug("게시글 목록 조회 측정 시간 : {}초",(finish-start)/1000.0);
		
		
		model.addAttribute("list",list);
		model.addAttribute("pi",pi);
		
		
		return "board/boardListView";
	}
	
	@GetMapping("/search")
	public String searchList(@RequestParam(value="currentPage",defaultValue="1") 
							 int currentPage
							,String condition
							,String keyword
							,Model model) {
		
		
		//전달받은 검색 조건 맵에 담기 
		HashMap<String,String> hashMap = new HashMap<>();
		hashMap.put("condition",condition);
		hashMap.put("keyword",keyword);
		
		int searchListCount = boardService.searchListCount(hashMap);
		int pageLimit = 10;
		int boardLimit = 5;
		
		PageInfo pi = Pagination.getPageInfo(searchListCount, currentPage, pageLimit, boardLimit);
		
		ArrayList<Board> searchList = boardService.searchList(hashMap,pi);
		
		model.addAttribute("pi",pi);
		model.addAttribute("hashMap",hashMap);
		model.addAttribute("list",searchList);
		
		
		return "board/boardListView";
	}
	
	
	//상세보기 요청
	@GetMapping("/detail")
	public ModelAndView selectBoard(int bno,
							  ModelAndView mv) {
		
		//해당 글번호로 조회수 증가 작업하기 
		int result = boardService.increaseCount(bno);
		
		//조회수 증가가 제대로 이루어졌다면 상세조회
		if(result>0) {
			Board b = boardService.selectBoard(bno);
			mv.addObject("b", b).setViewName("board/boardDetailView");
			
			//상세보기시 삭제처리 스케줄러 
			//sc.deleteBoardSc(bno); //게시글 번호 전달
			
		}else {
			mv.addObject("errorMsg","상세보기 실패!").setViewName("common/errorPage");
		}
		
		return mv;
		
	}
	
	//글작성 페이지
	@GetMapping("/insert")
	public String insertBoard() {
		
		//작성페이지로 이동
		return "board/boardEnrollForm";
	}
	
	
	//글등록 
	@PostMapping("/insert")
	public String insertBoard(Board b,
							 MultipartFile upfile
							 //,ArrayList<MultipartFile> upfile
							 //,MultipartFile[] upfile
							 ,HttpSession session
							 ) {
		//첨부파일 처리를 하려면 form에서 enctype을 multipart/form-data로 설정해야한다.
		//이때 기존 요청과는 달라졌기 때문에 데이터를 전달 받을 수 없음.
		//multipart 처리시 데이터를 전달받기 위해선 MultipartResolver가 필요하다.
		//pom.xml에 di 두개 commons-fileupload / commons-io 를 추가하고
		//root-context에서 MultipartResolver Bean을 등록하여 사용한다.
		
		
//		for(MultipartFile mpf : upfile) {
//			
//			System.out.println(mpf);
//			
//		}
		
//		for(int i=0; i<upfile.length;i++) {
//			
//			System.out.println(upfile[i].getOriginalFilename());
//		}
			
		
		//전달된 파일이 있을 경우 - 파일명 수정 작업후 서버로 업로드,원본명과 수정된 파일명을 DB에 등록하기 
		//전달된 파일이 없어도 MultipartFile 객체는 생성되어 주입되기때문에 null값 비교가 아닌 
		//내부 필드값을 이용하여 비교해야한다. 이때 전달된 파일명이 없을땐 "" 로 처리되는것을 이용하기
		
		//전달된 파일이 있을경우(파일명이 빈문자열로 넘어오지 않는 경우)
		if(!upfile.getOriginalFilename().equals("")) {
			
			//파일명 수정작업하기 
			String changeName = saveFile(upfile,session);
			
			//파일 정보를 Board 에 담아주기 
			b.setOriginName(upfile.getOriginalFilename());
			b.setChangeName("/resources/uploadFiles/"+changeName);
		}
		
		
		int result = boardService.insertBoard(b);
		
		if(result>0) {
			session.setAttribute("alertMsg","게시글 작성 성공!");
		}else {
			session.setAttribute("alertMsg","게시글 작성 실패!");
		}
		
		return "redirect:/board/list";
	}
	
	
	//게시글 삭제 메소드
	@PostMapping("/delete")
	public String deleteBoard(int boardNo
							 ,String filePath
							 ,HttpSession session) {
		
		
		int result = boardService.deleteBoard(boardNo);
		
		if(result>0) {//성공시 파일 삭제 
			
			if(!filePath.equals("")) {//전달된 파일경로가 있다면 
				//실제 파일 경로 잡아서 객체 연결 후 삭제 메소드처리 
				new File(session.getServletContext().getRealPath(filePath)).delete();
			}
			session.setAttribute("alertMsg", "게시글 삭제 성공!");
			return "redirect:/board/list"; //삭제 후 목록으로
			
		}else {
			
			session.setAttribute("alertMsg", "게시글 삭제 실패!");
			return "redirect:/board/detail?bno="+boardNo; //삭제 실패 - 기존 상세보기페이지
		}
		
	}
	
	
	
	//게시글 수정페이지로
	@GetMapping("/update")
	public String updateForm(int bno
							,Model model) {
		
		Board b = boardService.selectBoard(bno);
		
		model.addAttribute("b",b);
		
		return "board/boardUpdateForm";
	}
	
	
	@PostMapping("/update")
	public String updateBoard(Board b
							 ,MultipartFile reUpfile
							 ,HttpSession session) {
		//새로운 첨부파일이 있다면 기존 첨부파일 찾아서 삭제하는 작업 처리 하기 
		//정보 수정 성공시 성공 메시지와 함께 기존 첨부파일 있다면 삭제 하고 디테일뷰로 
		//정보 수정 실패시 실패 메시지와 함께 디테일뷰로 이동
		
		//기존 첨부파일이 있고 새로운 첨부파일이 있는 경우 
		//서버에 업로드되어 있던 기존 첨부파일 삭제 필요 
		
		String deleteFile = null; //삭제해야할 파일명 저장할 변수
		
		
		if(!reUpfile.getOriginalFilename().equals("")) {
			
			//기존 첨부파일이 있었는지 확인 
			if(b.getOriginName()!=null) {
				//기존 첨부파일이 서버에 업로드되어있는 파일명 저장해놓기 
				deleteFile = b.getChangeName(); //삭제할 기존파일명 저장
			}
			
			//새로 업로드된 파일 정보 데이터베이스 등록 및 파일 서버 업로드처리 
			String changeName = saveFile(reUpfile,session);
			
			//업로드 처리 후 변경된 파일명 데이터베이스에 등록하기 위해 b에 세팅 
			b.setOriginName(reUpfile.getOriginalFilename());//원본파일명
			b.setChangeName(changeName);//서버 업로드된 파일명
			
		}
		
		int result = boardService.updateBoard(b);
		
		if(result>0) { //데이터베이스에 제대로 수정작업 처리 후 
			//기존 파일이 있으면 삭제 
			if(deleteFile!=null) {
				//파일객체로 기존 파일 경로 잡아서 삭제
				new File(session.getServletContext().getRealPath(deleteFile)).delete();
			}
			session.setAttribute("alertMsg", "게시글 수정 성공!");
		}else {//실패
			session.setAttribute("alertMsg", "게시글 수정 실패!");
		}
		
		return "redirect:detail?bno="+b.getBoardNo();
		
	}
	
	
	
	
	
	
	
	//파일 업로드 처리 메소드 
	public String saveFile(MultipartFile upfile
						  ,HttpSession session) {
		//1.원본 파일명 추출
		String originName = upfile.getOriginalFilename();
		
		//2.시간 형식 문자열로 만들기 
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		//3.확장자 추출하기 (파일명 뒤에서부터 . 찾아서 잘라내기)
		String ext = originName.substring(originName.lastIndexOf("."));
		
		//4.랜덤값 5자리 뽑기
		int ranNum = (int)(Math.random()*90000+10000);
		
		//5.합치기
		String changeName = currentTime+ranNum+ext;
		
		//6.업로드하고자 하는 서버의 물리적인 경로 찾아내기 
		String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
		
		try {
			
			//7.경로와 수정파일명을 합쳐서 파일 업로드 처리 
			upfile.transferTo(new File(savePath+changeName));
		
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return changeName;
	}
	
	/*
	@ResponseBody
	@RequestMapping(value="replyList",produces = "application/json;charset=UTF-8")
	public String replyList(int boardNo) {
		
		ArrayList<Reply> rList = boardService.replyList(boardNo);
		
		//JSON화 시키기
		JSONArray jArr = new JSONArray();
		
		for(Reply r : rList) {
			
			//JSON Object에 조회해온 데이터 담아주기
			JSONObject jobj = new JSONObject();
			jobj.put("replyNo", r.getReplyNo());
			jobj.put("createDate", r.getCreateDate());
			jobj.put("replyContent", r.getReplyContent());
			jobj.put("replyWriter", r.getReplyWriter());
			
			jArr.add(jobj); //JSON 배열에 추가 
		}
		
		//JSONArray를 문자열로 변환해서 리턴하기 
		
		return jArr.toJSONString();
		
	}
	
	*/
	
	
	//GSON 이용해서 처리하기 
	@ResponseBody
	@RequestMapping(value="replyList",produces = "application/json;charset=UTF-8")
	public String replyList(int boardNo) {
		
		ArrayList<Reply> rList = boardService.replyList(boardNo);
		
		
		return new Gson().toJson(rList);
	}
	
	
	
	//댓글 등록 
	@ResponseBody
	@PostMapping("insertReply")
	public int insertReply(Reply r) {
		
		int result = boardService.insertReply(r);
		
		return result;
	}
	
	//조회수 top5 게시글 목록 조회
	
	@ResponseBody
	@RequestMapping(value="selectTopList",produces = "application/json;charset=UTF-8")
	public ArrayList<Board> selectTopList(){
		//게시글 목록을 조회해오는 작업을 수행할때
		//성능 테스트를 위해 시간을 측정하는 작업을 수행하고자 한다.
		
		//시간측정 
		
		//long start = System.currentTimeMillis(); //시작시간 측정 
		
		ArrayList<Board> list = boardService.selectTopList();
		
		//long finish = System.currentTimeMillis(); //종료시간 측정
		
		//log.info("top5목록 조회에 걸린 시간 : {}초",(finish-start)/1000.0);
		
		//게시글 목록 조회에서도 시간 측정을 해보세요 debug로 찍어서 나올수 있도록
		//또한 로그가 프로젝트내에 selectBoardTime.log로 파일에 찍힐 수 있도록 해보기
		
		return list;
	}
	
	
	
	
	

}
