<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=75bbea1849a873d5c02946bcb71bb424"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/common/header.jsp" %>
	
	
		
	<div class="content">
        <br><br>
        <button onclick="location.href='websocket/basic'">기본 채팅 서버</button>
        <button onclick="location.href='websocket/group'">그룹 채팅 서버</button>
        <button onclick="location.href='websocket/member'">멤버 채팅 서버</button>
        <button onclick="location.href='websocket/chat'">개인 채팅 서버</button>
        <div class="innerOuter" style="padding:5% 10%;">
            <h2>게시글 조회수 TOP 5</h2>
     
            <br>
            <table id="boardList" class="table table-hover" align="center">
                <thead>
                    <tr>
                        <th>글번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>조회수</th>
                        <th>작성일</th>
                        <th>첨부파일</th>
                    </tr>
                </thead>
                <tbody>
                	
                	<!-- 조회수 기준으로 상위 top5 게시글 목록 조회해오기 -->
                </tbody>
            </table>
            <br>

        </div>
        <br><br>
    </div>
	
	<script>
		$(function(){
			selectTopList();
		});
		
		//메소드명 selectTopList()
		//첨부파일 경우 있으면 별표 없으면 빈칸 
		//비동기 통신으로 처리하기 
		//해당 글 클릭했을때 글 상세보기 작업까지 수행해볼것
		function selectTopList(){
			$.ajax({
				url : "board/selectTopList",
				success : function(list){
					var str = "";
					
					for(var board of list){
						str += "<tr>"
							 + "<td>"+board.boardNo+"</td>"
							 + "<td>"+board.boardTitle+"</td>"
							 + "<td>"+board.boardWriter+"</td>"
							 + "<td>"+board.count+"</td>"
							 + "<td>"+board.createDate+"</td>"
							 + "<td>";
							 if(board.originName !=null){
								 str+="★";
							 }
							 +"</td></tr>";
					}
					
					$("#boardList tbody").html(str);
					
					
				},
				error : function(){
					console.log("통신 오류");
				}
			});
		}
		
		
		//글 클릭했을때 해당 글로 상세보기하기
		/*
		$("#boardList tbody>tr").click(function(){
			console.log($(this)); 
		});
		*/
		
		//동적으로 추가된 요소는 위 방식으로 선택할 수 없다. 
		//동적으로 추가된 요소에 이벤트를 동작시키려면 
		//상위요소 선택자 -> 하위요소 선택자 방식으로 처리해야한다.
		$("#boardList").on("click","tbody>tr",function(){
			
			var bno = $(this).children().first().text();
			
			//상대경로 (현재 url위치로부터 시작 마지막 / 기준 뒤로 )
			//location.href="board/detail?bno="+bno;
			//절대경로 (contextPath로 부터 시작)
			location.href="${contextPath}/board/detail?bno="+bno;
			
			
		});
		
		
		
		
		
		
	</script>
	
	
	
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>