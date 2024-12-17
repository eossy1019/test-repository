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
    </style>
</head>
<body>
    
    <%@include file="/WEB-INF/views/common/header.jsp" %>

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>마이페이지</h2>
            <br>
            
            <!-- 
            	updateMember() 메소드를 이용하여 정보수정 처리해보기 
            	매핑주소 : update.me
            	성공시 : 정보수정 성공! 메시지와 함께 마이페이지로 되돌아오기 (변경된 정보 갱신)
            	실패시 : 에러페이지로 정보수정 실패! 메시지와 함께 포워딩하기 
            	
            	성별도 선택되어 있도록 구현해보기
             -->

            <form action="update.me" method="post">
                <div class="form-group">
                    <label for="userId">* ID : </label>
                    <input type="text" class="form-control" id="userId" value="${loginUser.userId }" name="userId" readonly> <br>

                    <label for="userName">* Name : </label>
                    <input type="text" class="form-control" id="userName" value="${loginUser.userName }" name="userName" required> <br>

                    <label for="email"> &nbsp; Email : </label>
                    <input type="text" class="form-control" id="email" value="${loginUser.email }" name="email"> <br>

                    <label for="age"> &nbsp; Age : </label>
                    <input type="number" class="form-control" id="age" value="${loginUser.age }" name="age"> <br>

                    <label for="phone"> &nbsp; Phone : </label>
                    <input type="tel" class="form-control" id="phone" value="${loginUser.phone }" name="phone"> <br>
                    
                    <label for="address"> &nbsp; Address : </label>
                    <input type="text" class="form-control" id="address" value="${loginUser.address }" name="address"> <br>
                    
                    <label for=""> &nbsp; Gender : </label> &nbsp;&nbsp;
                    <input type="radio" id="Male" value="M" name="gender">
                    <label for="Male">남자</label> &nbsp;&nbsp;
                    <input type="radio" id="Female" value="F" name="gender">
                    <label for="Female">여자</label> &nbsp;&nbsp;
                </div> 
                <br>
                <div class="btns" align="center">
                    <button type="submit" class="btn btn-primary">수정하기</button>
                    <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteForm">회원탈퇴</button>
                </div>
            </form>
        </div>
        <br><br>
        
    </div>
    
    <script>
    	//남 여 체크박스 선택시키기 
    	$(function(){
    		
    		var gender = "${loginUser.gender}";
    		
    		//필수 입력사항이 아니기때문에 없을 수 도 있음 
    		
    		if(gender != ""){//값이 있으면
    			//input요소중 value값이 gender에 담긴 값과 일치하는 요소 선택해서 checked 시키기
    			$("input[value=${loginUser.gender}]").attr("checked",true);
    		}
    	});
    </script>
    
    
    <!-- 
    	메소드명 : deleteMember
    	매핑 주소 : delete.me
    	성공시 회원 탈퇴 성공 메시지와 함께 메인페이지로 이동 (재요청) - 세션에 있는 로그인 정보 삭제
    	실패시 회원 탈퇴 실패 메시지와 함께 마이페이지로 이동 (재요청) 
    	회원탈퇴 처리는 delete 또는 update로 구현 
     -->

    <!-- 회원탈퇴 버튼 클릭 시 보여질 Modal -->
    <div class="modal fade" id="deleteForm">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">

                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title">회원탈퇴</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>

                <form action="delete.me" method="post">
                    <!-- Modal body -->
                    <div class="modal-body">
                        <div align="center">
                            탈퇴 후 복구가 불가능합니다. <br>
                            정말로 탈퇴 하시겠습니까? <br>
                        </div>
                        <br>
                            <label for="userPwd" class="mr-sm-2">Password : </label>
                            <input type="password" class="form-control mb-2 mr-sm-2" placeholder="Enter Password" id="userPwd" name="userPwd"> <br>
                    </div>
                    <!-- Modal footer -->
                    <div class="modal-footer" align="center">
                        <button type="submit" class="btn btn-danger">탈퇴하기</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>
</html>