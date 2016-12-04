<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- com.allnightMovies.model.data.movieInfo.MainPageEventDTO.java -->
<article class="mainContiner">
<!-- 슬라이더 이미지 -->
	<div class="slider">
		<figure>
			<c:forEach begin="0" end="${mainEventImg}" step="1" items="${mainEventDTO}" var="mainEventDTO" >
				<img class="js_mainEventImage main-event-image" alt="" src="/../resources/img/mainPage/${mainEventDTO.mainEventImg}" data-notice-no="${mainEventDTO.noticeNo}">
			</c:forEach>
		</figure>
	</div>
<!-- 최신 개봉작 -->	
	<div class="mainContiner_middle" >
		<label class="maintitleFont">최신개봉작
			<c:if test="${isManager}">
				<button class="js_mainInsertMovieBtn movieInsertBtn" type="button">영화등록</button>
			</c:if>
		</label>
		<c:forEach begin="0" end="4" step="1" items="${NewFilmDTO}" var="NewFilmDTO">
			<span class="mainCurrnetFilm font">
				<span class="mainFilmImg" style="background-image: url('/../resources/img/poster/${NewFilmDTO.moviePoster}');">
					<a class="hiddenText" href="/movie/mainService/movieDetailInfo?movieInfoTitle=${NewFilmDTO.movieTitle}&movieNO=${NewFilmDTO.no}">상세보기</a>
				</span>
				<span class="mainFilmImg__title">
					<label class="ageLimit${NewFilmDTO.movieAgeLimit }">${NewFilmDTO.movieAgeLimit }</label>
					<label class="screeningsPlanned-posters__movietitle">${NewFilmDTO.movieTitle }</label>
				</span>
			</span>
		</c:forEach>
	</div>
<!-- 최신 공지사항  -->		
	<div class="mainContiner_bottom">
	
		<div class="mainContiner-notice">
			<label class="maintitleFont">공지사항  <a class="locationNotice" href="movie/mainService/notice">+</a></label>
			<span class="mainContiner-notice-title">
				<label class="noticeTitle">제목</label>
				<label class="noticeDate">작성일</label>
			</span>
			<c:forEach begin="0" end="10" items="${mainNoticeDTO}" var="mainNoticeDTO">
			<span class="mainContiner-notice-content">
				<c:choose>
					<c:when test="${mainNoticeDTO.important == 1}">
						<label class="noticeTitle">
							<img class="importImg" src="/resources/img/notice/notice.png">&nbsp;&nbsp;
							<a  class="noticeTitle import" href="/movie/mainService/noticeBoardView?noticePage=1&noticeNo=${mainNoticeDTO.no}">${mainNoticeDTO.title}</a>
						</label>
						<label class="noticeDate">${mainNoticeDTO.writeDate}</label>
					</c:when>
					<c:otherwise>
						<label class="noticeTitle">
							<a class="noticeTitle titleHover" href="/movie/mainService/noticeBoardView?noticePage=1&noticeNo=${mainNoticeDTO.no}">${mainNoticeDTO.title}</a>
						</label>
						<label class="noticeDate">${mainNoticeDTO.writeDate}</label>
					</c:otherwise>
				</c:choose>
			</span>
			</c:forEach>
		</div>
		
		<div class="mainContiner-notice2">
		</div>
		
	</div>
</article>

 
 