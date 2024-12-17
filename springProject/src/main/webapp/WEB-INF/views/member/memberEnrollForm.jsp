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
            <h2>회원가입</h2>
            <br>

            <form action="insert.me" method="post">
                <div class="form-group">
                    <label for="userId">* ID : </label>
                    <input type="text" class="form-control" id="enrollUserId" placeholder="Please Enter ID" name="userId" required>
					<div id="checkResult" style='font-size:0.8em; display:none'></div>
					<br>
                    <label for="userPwd">* Password : </label>
                    <input type="password" class="form-control" id="enrollUserPwd" placeholder="Please Enter Password" name="userPwd" required> <br>

                    <label for="checkPwd">* Password Check : </label>
                    <input type="password" class="form-control" id="checkPwd" placeholder="Please Enter Password" required> <br>

                    <label for="userName">* Name : </label>
                    <input type="text" class="form-control" id="userName" placeholder="Please Enter Name" name="userName" required> <br>

                    <label for="email"> &nbsp; Email : </label>
                    <input type="text" class="form-control" id="email" placeholder="Please Enter Email" name="email"> <br>

                    <label for="age"> &nbsp; Age : </label>
                    <input type="number" class="form-control" id="age" placeholder="Please Enter Age" name="age"> <br>

                    <label for="phone"> &nbsp; Phone : </label>
                    <input type="tel" class="form-control" id="phone" placeholder="Please Enter Phone (-포함)" name="phone"> <br>
                    
                    <label for="address"> &nbsp; Address : </label>
                    <input type="text" class="form-control" id="address" placeholder="Please Enter Address" name="address"> <br>
                    
                    <label> &nbsp; Gender : </label> &nbsp;&nbsp;
                    <input type="radio" id="Male" value="M" name="gender" checked>
                    <label for="Male">남자</label> &nbsp;&nbsp;
                    <input type="radio" id="Female" value="F" name="gender">
                    <label for="Female">여자</label> &nbsp;&nbsp;
                </div> 
                <br>
                <div class="btns" align="center">
                    <button type="submit" class="btn btn-primary" disabled>회원가입</button>
                    <button type="reset" class="btn btn-danger">초기화</button>
                </div>
            </form>
        </div>
        <br><br>

    </div>


	<script>
		$(function(){
			/*
				사용자가 입력한 아이디가 중복인지 확인하는 작업 처리 해보기 (비동기처리)
				사용자가 키를 입력하면 이벤트 발생 (또는 버튼 만들어서 처리 해도 됨)
				중복인지 아닌지 확인하여 네이버처럼 NNNNN(사용불가) 또는 NNNNNY(사용가능)으로 응답 데이터를 받아서
				응답데이터에 따라 result div에 사용가능한 아이디 입니다 또는 사용 불가능한 아이디 입니다. 라는 텍스트를 보여주기 
				이때 사용 가능한 아이디라면 회원가입 버튼이 활성화 되도록 작업하기.
				idCheck() 메소드 이용
			*/
			
			
			//사용자가 입력하는 input요소 추출 
			var inputId = $("#enrollUserId");
			
			
			inputId.keyup(function(){
				
				//5글자 이상일때만 서버에 요청
				if(inputId.val().length>4){
					
					$.ajax({
						url : "idCheck.me",
						data : {
							inputId : inputId.val()
						},
						success : function(result){
							
							if(result=='NNNNN'){//중복 
								$("#checkResult").show();
								$("#checkResult").css("color","red").text("사용불가능한 아이디 입니다.");
								//사용 불가능이면 회원가입 버튼 비활성화
								$(".btns>button[type=submit]").attr("disabled",true);
							}else{
								$("#checkResult").show();
								$("#checkResult").css("color","green").text("사용가능한 아이디 입니다.");
								//사용 가능시 회원가입 버튼 활성화
								$(".btns>button[type=submit]").attr("disabled",false);
							}
							
							
							
						},
						error : function(){
							console.log("통신 오류");
						}
					});
				}
			});
			
			
			
		});
	
	
	</script>










    <!-- 푸터바 -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>
</html>