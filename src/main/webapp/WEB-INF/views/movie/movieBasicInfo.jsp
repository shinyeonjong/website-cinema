<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="movieBasicInfo js_movieBasicInfoContainer">
<script async="async" type="text/javascript" src="/resources/js/movie/managerMovieInfo.js"></script>
	
	<!-- 작품소개/ 스틸컷/ 평점리뷰 -->
	<div class="movieBasicInfo-intro">
		<img class="moviePoster" src="/resources/img/poster/${movieBasicInfo.moviePoster}"></img>
		<span class="span-movieBasicInfo-intro">
			<label class="intro__lable__title js_basicInfomovieTitle" data-movie-info-title="${movieBasicInfo.movieTitle}">${movieBasicInfo.movieTitle}</label><br><br>
			<label class="intro__label">개요&nbsp;:&nbsp;${movieBasicInfo.movieGenre}</label><br><br>
			<label class="intro__label">감독&nbsp;:&nbsp;${movieBasicInfo.movieDirector}&nbsp;|&nbsp;저자&nbsp;:&nbsp;${movieBasicInfo.movieAuthor}</label><br><br>
			<label class="intro__label">출연&nbsp;:&nbsp;${movieBasicInfo.movieCast}</label><br><br>
			<label class="intro__label">등급&nbsp;:&nbsp;${movieBasicInfo.movieAgeLimitText}&nbsp;관람가</label><br><br>
			<c:if test="${reviewResult}">
				<label class="intro__label">평점&nbsp;:&nbsp;
				<c:choose>
					<c:when test="${movieBasicInfo.reviewEvaluate % 2 == 0}">
						<c:forEach begin="1" end="${movieBasicInfo.reviewEvaluate / 2}" >
							<img class="review-StarImg" src="/resources/img/reviewEvaluate/fullstart.png">
						</c:forEach>
						<c:forEach begin="1" end="${5 - (movieBasicInfo.reviewEvaluate / 2)}">
							<img class="review-StarImg" src="/resources/img/reviewEvaluate/star.png">
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach begin="1" end="${movieBasicInfo.reviewEvaluate / 2}" >
							<img class="review-StarImg" src="/resources/img/reviewEvaluate/fullstart.png">
						</c:forEach>
						<c:forEach begin="1" end="${movieBasicInfo.reviewEvaluate % 2}" >
							<img class="review-StarImg" src="/resources/img/reviewEvaluate/halfstar.png">
						</c:forEach>
						<c:forEach begin="1" end="${5 - (movieBasicInfo.reviewEvaluate / 2)}">
							<img class="review-StarImg" src="/resources/img/reviewEvaluate/star.png">
						</c:forEach>
					</c:otherwise>
				</c:choose>
				&nbsp;&nbsp;${movieBasicInfo.reviewEvaluate}&nbsp;점
				</label><br><br>
				<label class="intro__label">상영시간&nbsp;:&nbsp;${movieBasicInfo.movieRuntime}분</label><br><br>
				<label class="intro__label">개봉일&nbsp;:&nbsp;${movieBasicInfo.movieReleaseDate}</label><br><br>
			</c:if>
		</span> 
	</div>
	<div class="intro_managerModeButtons">
		<c:if test="${isManager}">
			<button class="intro_managerModeBtn js_movieModifyBtn" data-movie-no="${movieBasicInfo.no}" type="button">기본정보수정</button>
			<button class="intro_managerModeBtn js_movieInsertBtn" data-movie-no="${movieBasicInfo.no}" type="button">영화추가</button>
		</c:if>
	</div>
	<!-- 작품소개/ 스틸컷/ 평점리뷰 -->
	<div class="movieBasicInfo-detail">
		<!-- input -->
		<input id="tab-intro" type="radio" name="tab" checked="checked"/>	
		<input id="tab-stillCut" type="radio" name="tab"/>	
		<input id="tab-review" type="radio" name="tab"/>	
		
		<!-- label 화면에 표시되는 탭 제목-->
		<label for="tab-intro">작품소개</label>
		<label class="js_tab_stillcut"for="tab-stillCut">스틸컷</label>
		<label class="js_tab_review" for="tab-review">평점/리뷰</label>
		
		<!-- 탭 내용 : 탭 제목을 선택했을 때 표시되는 본문 -->
 			<div class="tab-intro_content">
				<pre class="tab-intro_content__intro js_movieIntro">${movieBasicInfo.movieIntro}</pre>
			</div>
			
			<!-- 스틸컷 사진 -->
			<div class="js_stillcutContainer tab-stillCut_content">
				<jsp:include page="./include/reviewStillcut.jsp"></jsp:include> 
			</div>
			
			<!-- 평점/리뷰 -->
			<div class="tab-review_content">
				<c:choose>
					<c:when test="${reviewResult}">
						<jsp:include page="./include/reviewBoard.jsp"></jsp:include> 
					</c:when>
					<c:otherwise>
						<span class="tab-intro_content__reviewfalse">!&nbsp;개봉전에는 평점을 등록할 수  없습니다.</span>
					</c:otherwise>
				</c:choose>
			</div>
	</div>
</div>
