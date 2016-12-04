<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="js_reviewBoardContainer">
	<!-- 1.관리자체크 -->
	<c:choose>
		<c:when test="${isManager}">
			<br><br>
		</c:when>
		<c:otherwise>
			<!-- 별점수/텍스트/등록버튼 -->
			<div class="reviewBoard">
				<span class="reviewBoardFont">
					<!-- <label class="reviewBoard-score"> -->
					<span class="star-input">
					  <span class="input js_reviewScore">
					    <input id="p1" type="radio" name="star-input" value="1"><label for="p1">1</label>
					    <input id="p2" type="radio" name="star-input" value="2"><label for="p2">2</label>
					    <input id="p3" type="radio" name="star-input" value="3"><label for="p3">3</label>
					    <input id="p4" type="radio" name="star-input" value="4"><label for="p4">4</label>
					    <input id="p5" type="radio" name="star-input" value="5"><label for="p5">5</label>
					    <input id="p6" type="radio" name="star-input" value="6"><label for="p6">6</label>
					    <input id="p7" type="radio" name="star-input" value="7"><label for="p7">7</label>
					    <input id="p8" type="radio" name="star-input" value="8"><label for="p8">8</label>
					    <input id="p9" type="radio" name="star-input" value="9"><label for="p9">9</label>
					    <input id="p10" type="radio" name="star-input"  value="10"><label for="p10">10</label>
					  </span>
					  <output for="star-input"><b class="js_starScore">0</b>점</output>
					</span>
				</span>
				<!-- 2.유저체크 -->
				<c:choose>
					<c:when test="${userCheck == null}">
						<textarea class="reviewTextarea js_reviewText" placeholder="로그인 후 이용해주세요" readonly="readonly"></textarea>
					</c:when>
					<c:otherwise>
						<textarea class="reviewTextarea js_reviewText" maxlength="120" placeholder="글자수는 120자로 제한됩니다."></textarea>
					</c:otherwise>
				</c:choose>
				<button class="reviewBoardBtn js_reviewInsertBtn" type="button">등록</button>
			</div>
		</c:otherwise>
	</c:choose>
	
	
	<!-- 리뷰 출력 -->
	<div class="reviewBoardList">
		<c:forEach begin="0" end="${reviewBoardCount}" items="${reviewBoardListDTO}"  varStatus="reviewBoardCount" var="reviewBoardListDTO">
		<div class="reviewBoard-list">
			<span class="reviewBoard-list__userinfo">
				<label class="reviewBoard-list__score">${reviewBoardListDTO.reviewEvaluate}점</label>
				<label class="reviewBoard-list__date">${reviewBoardListDTO.writeDate}</label>
			</span>
			<pre class="reviewContentTextarea js_reviewContents">${reviewBoardListDTO.reviewContents}</pre>
			<label class="reviewBoard-list__userinfo js_writerID" data-write-id="${reviewBoardListDTO.reviewWriter}">${reviewBoardListDTO.reviewWriter}</label>
			
			<c:if test="${reviewBoardListDTO.reviewWriter == userCheck}">
				<button class="deleteBtn js_deleteBtn" type="button" value="${reviewBoardListDTO.reviewNo}">삭제</button>
			</c:if>
			<c:if test="${isManager}">
				<button class="deleteBtn js_deleteBtn" type="button" value="${reviewBoardListDTO.reviewNo}">삭제</button>
			</c:if>
		</div>	
		</c:forEach>
		
	</div>
	<!-- 페이징 -->
	<div class="reviewBoard-list__buttom">
		<c:if test="${paging.startPageBlock != 1}">
			<label class="js_firstPage paging bold" data-firstPage="${paging.startPageNum}"  >◀</label>
			<label class="js_prePage paging bold" data-prePage="${paging.startPageBlock - 1}" >◁</label>
        </c:if>
        
        <c:forEach begin="${paging.startPageBlock}" end="${paging.endPageBlock}" var="page">
        	<c:choose>
	        	<c:when test="${params.noticePage == page}">
	       			<label class="paging_current js_currentPage paging bold" data-currentPage="${page}">${page}&nbsp;&nbsp;</label>
	        	</c:when>
	        	<c:otherwise>
	    	    	<label class="js_currentPage paging bold" data-currentPage="${page}">${page}&nbsp;&nbsp;</label>
	        	</c:otherwise>
        	</c:choose>
        </c:forEach>
        
        <c:if test="${paging.endPageBlock != paging.endPageNum}">
			<label class="js_nextPage paging bold" data-nextPage="${paging.endPageBlock + 1}">▷</label>
			<label class="js_lastPage paging bold" data-lastPage="${paging.endPageNum}">▶</label>
      	</c:if>
	</div>
</div>