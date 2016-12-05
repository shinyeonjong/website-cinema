<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="ReplyQuestionBoardWriteForm js_ReplyQuestionBoardWriteContainer font">
		<div class="ReplyQuestionBoard-content">
			<label class="ReplyQuestionBoard_content">문의 답글달기</label>
		</div>
		<div class="ReplyQuestionBoard">
		<c:choose>
			<c:when test="${setPwd == 1}">
				<div class="ReplyQuestionBoard-title">
					<label class="ReplyBoardtitle js_ReplyBoardTitle">제목</label>
					<input class="ReplyBoardwriteBoard js_ReplyBoardContent" type="text" value="[답글]: AllnightMovies입니다."readonly>
					<input class="ReplyBoardcheck js_ReplyBoardCheck"  type="checkbox" disabled="disabled" checked><label>비밀글</label>
				</div>
				
				<div class="ReplyQuestionBoard-sub">
					<div class="ReplyQuestionBoard_subject">
						<label class="ReplyBoardSub">내용</label>
						<textarea class="ReplyBoardTextarea js_ReplyBoardTextArea">안녕하세요 AllnightMovies 입니다. 이곳에 찾아주신 모든 고객분들께 감사드립니다.
						</textarea>
					</div>
					<div class="ReplyQuestionBoard-pwd">
						<label class="ReplyBoardPwd">비밀번호</label>
						<input class="ReplyBoardwriteBoardPwd js_ReplyBoardWriteBoardPwd" value="${writepwd }" type="password" readonly="readonly">
					</div>
				</div>
			</c:when>
			
			<c:otherwise>
				<div class="ReplyQuestionBoard-title">
					<label class="ReplyBoardtitle js_ReplyBoardTitle">제목</label>
					<input class="ReplyBoardwriteBoard js_ReplyBoardContent" type="text" value="[답글]: AllnightMovies입니다." readonly>
					<input class="ReplyBoardcheck js_ReplyBoardCheck"  type="checkbox" disabled="disabled" ><label>비밀글</label>
				</div>
				<div class="ReplyQuestionBoard-sub">
					<div class="ReplyQuestionBoard_subject">
						<label class="ReplyBoardSub">내용</label>
						<textarea class="ReplyBoardTextarea js_ReplyBoardTextArea">안녕하세요 AllnightMovies 입니다. 이곳에 찾아주신 모든 고객분들께 감사드립니다.</textarea>
					</div>
					<div class="ReplyQuestionBoard-pwd">
						<label class="ReplyBoardPwd">비밀번호</label>
						<input class="ReplyBoardwriteBoardPwd js_ReplyBoardWriteBoardPwd"  value="${writePwd }" type="password" readonly>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
			<div class="ReplyQuestionbutton">
				<button class="ReplyQuestionButton_cancel js_ReplyQuestionButtonCancel" type="button">취소</button>
				<button class="ReplyQuestionButton_confirm js_ReplyQuestionButtonConfirm" type="button">확인</button>
			</div>
		
		</div>
		
		
		<input class="js_replyNo" type="hidden" value="${replyNo }">
		<input class="js_replyDepth" type="hidden" value="${replyDepth +1 }">
		<input class="js_replyStep" type="hidden" value="${replyStep + 1 }">
		<input class="js_questionBoardNo" type="hidden" value="${questionBoardNo }">

</div>