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

        #updateForm>table {width:100%;}
        #updateForm>table * {margin:5px;}
    </style>
</head>
<body>
        
    <%@ include file="/WEB-INF/views/common/header.jsp" %>

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 수정하기</h2>
            <br>

            <form id="updateForm" method="post" action="update" enctype="multipart/form-data">
            	<!-- 게시글 번호 전달 -->
            	<input type="hidden" name="boardNo" value="${b.boardNo }">
                <table algin="center">
                    <tr>
                        <th><label for="title">제목</label></th>
                        <td><input type="text" id="title" class="form-control" value="${b.boardTitle }" name="boardTitle" required></td>
                    </tr>
                    <tr>
                        <th><label for="writer">작성자</label></th>
                        <td><input type="text" id="writer" class="form-control" value="${b.boardWriter }" name="boardWriter" readonly></td>
                    </tr>
                    <tr>
                        <th><label for="upfile">첨부파일</label></th>
                        <td>
                            <input type="file" id="upfile" class="form-control-file border" name="reUpfile">
                           
                           <!-- 기존 파일이 있을때만 보여주기 및 전달하기 -->
                           <c:if test="${not empty b.originName}">
	                            현재 업로드된 파일 : 
	                            <a href="${contextPath}${b.changeName}" download="${b.originName }">${b.originName }</a>
	                            
	                            <!-- 기존 파일이 있었다면 해당 파일 정보 전달하기 -->
	                            <input type="hidden" name="changeName" value="${b.changeName }">
	                            <input type="hidden" name="originName" value="${b.originName }">
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <th><label for="content">내용</label></th>
                        <td><textarea id="content" class="form-control" rows="10" style="resize:none;" name="boardContent" required>${b.boardContent }</textarea></td>
                    </tr>
                </table>
                <br>

                <div align="center">
                    <button type="submit" class="btn btn-primary">수정하기</button>
                    <button type="button" class="btn btn-danger" onclick="javascript:history.go(-1);">이전으로</button>
                </div>
            </form>
        </div>
        <br><br>

    </div>
    
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
    
</body>
</html>