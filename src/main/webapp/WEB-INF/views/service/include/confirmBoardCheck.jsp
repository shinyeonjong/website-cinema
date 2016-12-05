<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="questionBoardWriteForm js_questionBoardWriteContainer font">
	<label class="questionBoardWrite_title">문의하기</label>
	
	<div class="questionBoard-info">
		<div class="questionBoardView_sub">문의사항이나 불편사항은 이 곳에 남겨주세요. 최대한 빠른 시간 안에 답변 드리겠습니다.</div>
		<div class="questionBoardView_sub2">금요일 오후 6시 이후 접수된 문의는 그 다음주에 처리될 수 있습니다. FAQ를 이용하시면 궁금증을 더 빠르게 해결하실 수 있습니다.</div>
	</div>
		<div class="questionBoard-content">
			<label class="questionBoardView_content">문의 내용</label>
		</div>
		
		<div class="questionBoard">
			<div class="questionBoardView-title">
				<label class="boardViewtitle">제목</label>
				<c:choose>
					<c:when test="${questionBoardList.getIsPwd() == 1 }">
						<input class="boardViewwriteBoard" type="text" value="${questionBoardList.getTitle() }" readonly>
						<input class="boardViewcheck" type="checkbox" checked disabled ><label>비밀글</label>
					</c:when>
					
					<c:otherwise>
						<input class="boardViewwriteBoard" type="text" value="${questionBoardList.getTitle() }" readonly>
						<input class="boardViewcheck" type="checkbox" disabled ><label>비밀글</label>
					</c:otherwise>
				</c:choose>
			</div>
			
			<div class="questionBoardView-detail">
				<label class="boardViewwriter">작성자</label>
				<input class="boardwriteBoardViewinput" value = "${questionBoardList.getUser_Id() }" type="text" readonly >
				<label class="boardViewwriteDate">작성일</label>
				<input class="boardwriteBoardViewinput"  value="${questionBoardList.getWrite_date() }" type="text" readonly>
			</div>
		
		
			<div class="questionBoardView-sub">
				<div class="questionBoardView_subject">
					<label class="boardViewSub">내용</label>
					<pre class="boardViewTextarea">${questionBoardList.getContent() }</pre>
				</div>
			</div>

			<c:choose>
				<c:when test="${resultBool}">
					<div class="serviceCenter_viewForm">
						<button class="serviceCenter_QuestionWrite js_QuestionWriteUpdateBtn" type="button">수정하기</button>
						<button class="serviceCenter_QuestionWrite js_QuestionWriteDeleteBtn" type="button">삭제하기</button>
						<button class="serviceCenter_QuestionWrite js_QuestionList" type="button">글목록</button>
					</div>
				
				</c:when>
				
				<c:otherwise>
					<div class="serviceCenter_viewForm">
						<button class="serviceCenter_QuestionWrite js_QuestionList" type="button">글목록</button>
					</div>
				</c:otherwise>			
			</c:choose>
		</div>
</div>

<input type="hidden" class="js_boardViewNo" data-userClickNum="${questionBoardList.getNo()}">
