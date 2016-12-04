<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="js_screeningPlannedModContainer screeningModify">
	<h2 class="screeningModify__head">상영시간등록</h2>
	<button class="js_addBtn screeningModify__Btn" type="button" >
		추가
	</button>
	<ul class="js_addListUl screeningModify__list">
		<li class="js_addListLi">
			<div class="js_modifyCompDiv screeningModify__comp">
				<jsp:include page="./modifyComp.jsp"/>
			</div>		
		</li>
	</ul>
	<button class="js_screeningSubmitBtn screeningModify__submit-btn" type="button">
		등록
	</button>
</div>