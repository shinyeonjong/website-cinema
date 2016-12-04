<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="managementQuestionBoardUpdateForm js_managementQuestionBoardUpdateContainer font">
		<div class="managementQuestionUpdate-content">
			<label class="managementQuestionUpdate_content">문의 수정하기</label>
		</div>
		<div class="managementQuestionBoard">
			<div class="managementQuestionUpdateBoard-title">
				<label class="managementQuestionUpdatetitle js_managementQuestionUpdateButton_confirmTitle">제목</label>
				<input class="managementQuestionUpdateBoard js_managementQuestionUpdateContent" value="${cinemaFrequentlyBoardDTO.getQUESTION() }" type="text">
			</div>
			
			<div class="managementQuestionUpdate-sub">
				<div class="managementQuestionUpdate_subject">
					<label class="managementQuestionUpdateSub">내용</label>
					<textarea class="managementQuestionUpdateTextarea js_managementQuestionUpdateButton_confirmTextArea">"${cinemaFrequentlyBoardDTO.getASKED() }</textarea>
				</div>
			</div>
			<div class="managementQuestionUpdatebutton">
				<button class="managementQuestionUpdateButton_cancel js_managementQuestionUpdateButtonCancel" type="button">뒤로가기</button>
				<button class="managementQuestionUpdateButton_confirm js_managementQuestionUpdateButtonConfirm" data-updateFormNum="${cinemaFrequentlyBoardDTO.getNO() }" type="button">수정하기</button>
			</div>
		
		</div>

</div>