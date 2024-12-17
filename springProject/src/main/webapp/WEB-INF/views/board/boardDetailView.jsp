<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <style>
        .content {
            background-color:rgb(247, 245, 245);
            width:80%;
            margin:auto;
        }
        .innerOuter {
            border:1px solid lightgray;
            width:80%;
            margin:auto;
            padding:5% 10%;
            background-color:white;
        }

        table * {margin:5px;}
        table {width:100%;}
    </style>
</head>
<body>
        
   	<%@ include file = "/WEB-INF/views/common/header.jsp" %>

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 상세보기</h2>
            <br>

            <button class="btn btn-secondary" style="float:right;" onclick="history.back();">목록으로</button>
            <br><br>

            <table id="contentArea" algin="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3">${b.boardNo }</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${b.boardWriter }</td>
                    <th>작성일</th>
                    <td>${b.createDate }</td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td colspan="3">
                    	<c:choose>
	                    	<c:when test="${not empty b.originName}">
		                        <a href="${contextPath }${b.changeName}" download="${b.originName }">${b.originName }</a>
	                    	</c:when>
	                    	<c:otherwise>
	                    		첨부파일이 없습니다.
	                    	</c:otherwise>
                    	</c:choose>
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4"><p style="height:150px;">${b.boardContent }</p></td>
                </tr>
            </table>
            <br>
	            <div align="center">
	                <!-- 수정하기, 삭제하기 버튼은 이 글이 본인이 작성한 글일 경우에만 보여져야 함 -->
	                <a class="btn btn-primary" href="update?bno=${b.boardNo}">수정하기</a>
	                <button class="btn btn-danger" id="deleteBtn">삭제하기</button>
	            </div>
			
			<c:if test="${not empty loginUser and loginUser.userId eq b.boardWriter}">
			
            
            </c:if>
            
            <br><br>
            
            <script>
            	//삭제하기 버튼을 눌렀을때 삭제처리 post 방식으로 처리하기 
            	//mapping주소로 쿼리스트링을 전달하는 get방식 경우 url 요청만으로 삭제처리를 해버릴 수 있기 때문에
            	//중요 작업은 post로 처리해야한다. 
            	
            	//삭제하기 버튼을 클릭했을때 post요청으로 삭제처리 될 수 있도록 작성해보기 
            	//서버에 업로드 되어있는 파일도 삭제해야하기 때문에 파일이 있다면 파일 경로도 전달해주기 
            	//버튼 눌렀을때 confirm을 이용하여 확인 누르면 삭제처리 하기
            	
            	//메소드명 : deleteBoard() 이용
            	$("#deleteBtn").click(function(){
            		//form 태그 생성해서 데이터 담아주고 전송 
            		
            		var check = confirm("정말 삭제하시겠습니까?");
            		
            		if(check){
	            		var form = $("<form>");
	            		var inputObj = $("<input>"); //boardNo
	            		var filePath = $("<input>"); //filePath
	            		
	            		//생성된 form태그에 action과 method 속성 채워주기 
	            		form.prop("action","delete").prop("method","post");
	            		
	            		//input에 name,value 채워주기 
	            		inputObj.prop("type","hidden").prop("name","boardNo").prop("value","${b.boardNo}");
	            		//서버에 업로드 되어있는 파일도 삭제 필요 
	            		filePath.prop("type","hidden").prop("name","filePath").prop("value","${b.changeName}");
	            		
	            		//form에 input들 넣어주기 
	            		form.append(inputObj).append(filePath);
	            		
	            		//문서에 포함시키기 
	            		
	            		$("body").append(form);
	            		
	            		//서버 전송요청
	            		form.submit();
            		}
            		
            	});
            
            
            </script>
            
            

            <!-- 댓글 기능은 나중에 ajax 배우고 나서 구현할 예정! 우선은 화면구현만 해놓음 -->
            <table id="replyArea" class="table" align="center">
                <thead>
                	<c:choose>
                		<c:when test="${empty loginUser }">
                			<tr>
                				<th colspan="2">
                					<textarea class="form-control" cols="55" rows="2" style="resize:none; width:100%;" readonly>로그인 후 이용해주세요.</textarea>			
                				</th>
                			</tr>
                		</c:when>
                		<c:otherwise>
		                    <tr>
		                        <th colspan="2">
		                            <textarea class="form-control" name="" id="content" cols="55" rows="2" style="resize:none; width:100%;"></textarea>
		                        </th>
		                        <th style="vertical-align:middle"><button class="btn btn-secondary">등록하기</button></th>
		                    </tr>
                		</c:otherwise>
                    </c:choose>
                    
                    <tr>
                        <td colspan="3">댓글(<span id="rcount"></span>)</td>
                    </tr>
                    
                    
                    
                </thead>
                <tbody>
                    
                </tbody>
            </table>
        </div>
        <br><br>
        
        <!-- 댓글 비동기를 이용하여 목록 조회해오기 -->
        <script>
        	$(function(){
        		
        		//페이지가 처음 띄워졌을때 함수 호출
        		selectReplyList();
        	});
        	
        	function selectReplyList(){
        		//댓글 목록 조회하여 tbody에 넣어주기 
        		//요청 주소 : replyList
        		//메소드명 : replyList()
				        		
        		$.ajax({
        			url : "replyList",
        			data : {
        				boardNo : ${b.boardNo}
        			},
        			success : function(replyList){
        				
        				var str = "";
        				
        				for(var i=0; i<replyList.length; i++){
        					str += "<tr>"
        						 + "<td>"+replyList[i].replyWriter+"</>"
        						 + "<td>"+replyList[i].replyContent+"</>"
        						 + "<td>"+replyList[i].createDate+"</>"
        						 +"</tr>";
        				}
        				
        				$("#replyArea tbody").html(str);
        				$("#rcount").text(replyList.length);
        				
        			},
        			error : function(error){
        				
        				console.log(error);
        				console.log("통신 오류");
        			}
        				
        		});
        		
        	}
        	
        	
        	//댓글작성 버튼 눌렀을때 ajax를 이용하여 댓글 등록 처리 해보기 .
        	//insertReply() 메소드명
        	//insertReply 매핑주소
        	//댓글 작성처리 후 반환값 숫자를 이용하여 0보다 크면 성공 - alert 으로 작성 성공 메시지 후 댓글 목록 갱신과 작성란 비우기 
        	//0보다 크지 않으면 alert으로 작성 실패 메시지 띄우기 
        	
        	
        	$("#replyArea button").click(function(){
        		$.ajax({
        			url : "insertReply",
        			type : "post",
        			data : {
        				replyContent : $("#content").val(),
        				replyWriter : "${loginUser.userId}",
        				refBno : ${b.boardNo}
        			},
        			success : function(result){
        				//결과값으로 화면 처리 
        				if(result>0){
        					alert("댓글 작성 성공!");
        					selectReplyList(); //댓글 목록 다시 조회 (갱신)
        					$("#content").val(""); //작성란 비워주기
        				}else{
        						
        					alert("댓글 작성 실패!");
        				}
        			},
        			error : function(){
        				console.log("통신 오류");
        			}
        			
        		});
        	});
        	
        
        </script>
        
        
        
        

    </div>
    
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
    
</body>
</html>