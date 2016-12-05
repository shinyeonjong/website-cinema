<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
	<c:when test="${movieTimeList.size() != 0}">
		<div class="js_screeningTimeViewer  movie-time-list">
			<c:forEach items="${movieTimeList }" var="theater">
				<div class="movie-time-list__label">
					<label class="movie-time-theater">${theater.movieTheather } 관</label>
					<c:forEach items="${theater.movieTime }" var="movieTime">
						<c:choose>
							<c:when test="${theater.movieTheather == userChoiceInfo.movieTheater &&
											movieTime == userChoiceInfo.movieTime }">
							
								<label class="js_movieTimeClick js_userChoiceTime  movie-time" data-theater="${theater.movieTheather }">${movieTime }</label>
							
							</c:when>
						
							<c:otherwise>
								<label class="js_movieTimeClick movie-time" data-theater="${theater.movieTheather }">${movieTime }</label>
							</c:otherwise>
						
						</c:choose>
					
					</c:forEach>
				</div>
			</c:forEach>
		</div>
	</c:when>
	<c:otherwise>
		<div class="js_screeningTimeViewer  screening__default-text">상영 정보가 없습니다.</div>
	</c:otherwise>
</c:choose>
<script>
	$('.js_userChoiceTime').click();
</script>