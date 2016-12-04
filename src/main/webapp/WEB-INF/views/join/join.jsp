<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="background">
	<div class="join-form">
		<div class="join-form__title">
			<h2>회원가입</h2>
		</div>
		<div class="join-form__info1">
			
			<input class="js_userID" type="text" placeholder="아이디 입력" onkeyup="idCheck()" autocomplete="off" name="userIDCheck" />
			<div id="idResult" class="join__resultText"></div>
			
			<input class="js_userPWD" type="password" placeholder="비밀번호 입력" name="userPWD" onkeyup="pwdCheck()" />
			<div id="pwdResult" class="join__resultText"></div>
			
			<input id="user-re-pwd" name="userRePWD" type="password" placeholder="비밀번호 재입력" onkeyup="rePwdCheck()" />
			<div id="rePwdResult" class="join__resultText"></div>
		
		</div>
		<div class="join-form__info2">
			
			<input id="user-name" type="text" placeholder="이름 입력" name="userName" onkeyup="userNameCheck()" />
			<div id="user-name-result" class="join__resultText"></div>
			
			<select class="join-form__info2__gender" name="userGender" id="user-gender">
				<option>남자</option>
				<option>여자</option>
			</select>
			
			<input type="date" name="userBirth" id="user-birth" onkeyup="userBirthdayCheck()" />
			<div id="user-birthday-check" class="join__resultText"></div>
			
			<div>
				<input type="email" placeholder="이메일 입력" name="userEmail" onkeyup="userEmailCheck()" id="user-email" autocomplete="off" />
				<button class="join-form__info2__confirm-button" id="email-send-btn" onclick="sendEmail()" type="button">
					인증번호 발송
				</button>
				<div id="user-email-check" class="join__resultText"></div>
			</div>
			<div>
				<input type="number" placeholder="인증번호 입력" id="confirm-number"/>
				<button class="join-form__info2__input-confirm-num" id="confirm-number" onclick="confirmNumCheck()" type="button">확인</button>
			</div>
			<div id="confirm-result" class="join__resultText"></div>
		</div>
		<div class="join-form__join-btn">
			<button class="join-button" type="button" onclick="joinSuccessCheck()">
				회원가입
			</button>
		</div>
	</div>
</div>