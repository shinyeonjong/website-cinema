<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<label class="screeningModify__label">관</label>
	<select class="js_selectTheater screeningModify__input theater-select">
		<c:forEach items="${theaterList }" var="theater">
			<option>${theater}</option>
		</c:forEach>
	</select>
	
	<label class="screeningModify__label">영화제목</label>
	<select class="js_selectMovieTitle screeningModify__input">
		<c:forEach items="${movieList }" var="movieTitle">
			<option>${movieTitle }</option>
		</c:forEach>
	</select>
	
	<label class="screeningModify__label">날짜 선택</label>
	<input class="js_selectDate screeningModify__input" type="date"/>
	
	<label class="screeningModify__label">시간 입력</label>
	<input class="js_selectTime screeningModify__input" type="text" placeholder="24:00" maxlength="5"/>
	<button class="js_screeningModifyDeleteBtn screeningModify__btn" type="button">
		-
	</button>
