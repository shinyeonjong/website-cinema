<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container__showtimes js_showtimeTableConatainer">
	<div>
		<h2 class="container__showtimes__title">상영시간표</h2>
		<c:if test="${isManager }">
			<button class="js_screeningPlannedAdd container__showtimes__title__btn" type="button">
				상영시간 등록
			</button>
		</c:if>
	</div>
	<c:forEach items = "${movieTimeTable }" var="movieTimeTable" varStatus="status">
		<div class = "container__movie__screening__date">
			<button class="movie_screening_date js_showClickTimeTable screening-date-btn${status.index }" type="button">▼ ${movieTimeTable.getScreening_Date() }</button>
		
			<div class="movie_screening_panel js_moviePanel screening-date-panel${status.index }">
				<c:forEach items="${movieTimeTable.getMovieShowTitleDTO() }" var="movieTitleDTO">
					<div class="movie__info">
						<span class="movie__title js_movieTitle">
							${movieTitleDTO.getMovie_title()}
						</span>
						<c:forEach items ="${movieTitleDTO.getMovieshowTableDTO()}" var="movieTimeTableInfo">
							<span class="movie__room1 js_movieroom1" 
							data-movie-title="${movieTitleDTO.getMovie_title()}" 
							data-movie-time="${movieTimeTableInfo.getScreening_time()}"
							data-movie-theater="${movieTimeTableInfo.getMovie_theather()}"
							data-movie-date="${movieTimeTable.getScreening_Date() }">
								${movieTimeTableInfo.getScreening_time()}(${movieTimeTableInfo.getMovie_theather()}관)
							</span>
						</c:forEach>
					</div>
				</c:forEach>	
				</div>
		</div>
	</c:forEach>
</div>

