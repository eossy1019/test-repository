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

        #boardList {text-align:center;}
        #boardList>tbody>tr:hover {cursor:pointer;}

        #pagingArea {width:fit-content; margin:auto;}
        
        #searchForm {
            width:80%;
            margin:auto;
        }
        #searchForm>* {
            float:left;
            margin:5px;
        }
        .select {width:20%;}
        .text {width:53%;}
        .searchBtn {width:20%;}
    </style>
</head>
<body>
    
  	<%@ include file ="/WEB-INF/views/common/header.jsp" %>

    <div class="content">
        <br><br>
        <div class="innerOuter" style="padding:5% 10%;">
            <h2>게시판</h2>
            <br>
            <!-- 로그인 후 상태일 경우만 보여지는 글쓰기 버튼 -->
            <c:if test="${not empty loginUser }">
	            <a class="btn btn-secondary" style="float:right;" href="insert">글쓰기</a>
            </c:if>
            
            <br>
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
                
                	<c:choose>
                		<c:when test="${empty list}">
                			<tr>
                				<td colspan="6">조회된 게시글이 없습니다.</td>
                			</tr>
                		</c:when>
                		<c:otherwise>
                			<c:forEach items="${list}" var="b">
			                    <tr>
			                        <td>${b.boardNo }</td>
			                        <td>${b.boardTitle }</td>
			                        <td>${b.boardWriter }</td>
			                        <td>${b.count }</td>
			                        <td>${b.createDate }</td>
			                        <td>
			                        	<c:if test="${not empty b.originName}">
				                        	★
			                        	</c:if>
			                        </td>
			                    </tr>
		                    </c:forEach>
                    </c:otherwise>
                   	</c:choose>
                </tbody>
            </table>
            <br>
            
            <script>
            	//글을 클릭했을때 해당 글을 상세보기 할 수 있는 함수 작성
            	$(function(){
            		$("#boardList>tbody tr").click(function(){
            			//글번호 추출
            			var bno = $(this).children().first().text();
            			
            			location.href="detail?bno="+bno;
            			
            		});
            	});
            
            </script>
            
            

            <div id="pagingArea">
                <ul class="pagination">
                    <li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
                    
                    <c:forEach var="i" begin="${pi.startPage }" end="${pi.endPage }" >
                    	<c:choose>
    						<c:when test="${empty hashMap }">
			                    <li class="page-item"><a class="page-link" href="list?currentPage=${i}">${i}</a></li>
    						</c:when>                	
	                    	<c:otherwise>
	                    		<c:url var="searchUrl" value="search">
	                    			<c:param name="condition" value="${hashMap.condition }"/>
	                    			<c:param name="keyword" value="${hashMap.keyword }"/>
	                    			<c:param name="currentPage" value="${i}"/>
	                    		</c:url>
	                    		<li class="page-item">
	                    			<a class="page-link" href="${searchUrl}">${i}</a>
	                    		</li>
	                    	</c:otherwise>
                    	</c:choose>
                    </c:forEach>
                   
                    <li class="page-item"><a class="page-link" href="#">Next</a></li>
                </ul>
            </div>

            <br clear="both"><br>
	
				
			
			<!-- 
				searchList() 메소드명으로 작성 
				키워드랑 카테고리 유지될 수 있도록 처리해보기
				동적 sql 사용해보기
			 -->
	
		
            <form id="searchForm" action="${contextPath}/board/search" method="get" align="center">
                <div class="select">
                    <select class="custom-select" name="condition">
                        <option value="writer">작성자</option>
                        <option value="title">제목</option>
                        <option value="content">내용</option>
                    </select>
                </div>
                <div class="text">
                    <input type="text" class="form-control" name="keyword" value="${hashMap.keyword }">
                </div>
                <button type="submit" class="searchBtn btn btn-secondary">검색</button>
            </form>
            <br><br>
        </div>
        <br><br>
    </div>
    
    <script>
    	$(function(){
    		$("option[value='${hashMap.condition}']").attr("selected",true);
    	});
    </script>
    
    
    
    
    
    

    <jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>
</html>