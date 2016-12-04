<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="managementQuestionBoardWriteForm js_managementQuestionBoardWriteContainer font">
		<div class="managementQuestion-content">
			<label class="managementQuestion_content">문의 등록하기</label>
		</div>
		<div class="managementQuestionBoard">
			<div class="managementQuestionBoard-title">
				<label class="managementQuestiontitle js_managementQuestionButton_confirmTitle">제목</label>
				<input class="managementQuestionwriteBoard js_managementQuestionContent" type="text">
			</div>
			
			<div class="managementQuestion-sub">
				<div class="managementQuestion_subject">
					<label class="managementQuestionSub">내용</label>
					<textarea class="managementQuestionTextarea js_managementQuestionButton_confirmTextArea" rows="" cols=""></textarea>
				</div>
			</div>
			<div class="managementQuestionbutton">
				<button class="managementQuestionButton_cancel js_managementQuestionButtonCancel" type="button">취소</button>
				<button class="managementQuestionButton_confirm js_managementQuestionButtonConfirm" type="button">확인</button>
			</div>
		
		</div>

</div>