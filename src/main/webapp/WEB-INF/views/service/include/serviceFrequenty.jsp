<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="js_frequentlyBoardContainer">
	<div class="container__serviceCenterFrequenty">
		<div class="serviceCenter_search">
			<img  class="search-icon"></img>
			<input class="serviceCenter_Searchinput js_searchCenter_input" type="text" placeholder="고객센터 내용 검색"/>
			<button class="serviceCenter_Searchbutton js_serviceCenter_button" type="button">검색</button>
		<c:if test="${isManager}">
			<div class="div_managerButtonBtn">
				<button class="button_managerWriteBtn js_button_FrequentlyInsertBtn" type="button">글등록하기</button>
			</div>
		</c:if>
		</div>
		<ul class="container__serviceCenterFrequenty_content js_service_List">
			<c:choose>
				<c:when test="${pageCount == 0 }">
					<li>검색 결과가 없습니다. 다시 입력해주세요</li>
				</c:when>
				<c:otherwise>
					<c:forEach items="${boardPage }" var="boardPage">
						<li>
							<div class="serviceCenterFrequenty__subject js_sub">Q. ${boardPage.getQUESTION() }</div>	
							<div class="serviceCenterFrequenty__contents js_con">${boardPage.getASKED() }
							<input class="js_Number" type="hidden" data-message="${boardPage.getNO()}">
								<c:if test="${userStatus == 2 }">
									<div class="serviceCenterFrequenty__buttons">
										<button class="serviceCenterFrequenty__Updatebutton js_Updatebtn" data-clickManageNum="${boardPage.getNO()}" type="button">수정하기</button>
										<button class="serviceCenterFrequenty__Canelbutton js_Deletebtn" data-clickManageNum="${boardPage.getNO()}" type="button">삭제하기</button>
									</div>
								</c:if>	
							</div>
						</li>
					</c:forEach>
						<li>
							<div class="serviceCenterFrequenty__pageButton">
								<c:if test="${pageGroup.isPreButton()}">
									<span class="js_preButton${search }" data-prepage="${pageGroup.viewStartPageNum - 1}" >◀</span>
								</c:if>
								<c:forEach begin="${pageGroup.viewStartPageNum }" end="${pageGroup.viewEndPageNum }" var="page">
									<div class="serviceCener_paging_button">
										<c:choose>
											<c:when test="${checkPage == page }">
												<span class="serviceCenterFrequenty__pageButton--active js_currentNumber${search } " data-PageNum=${page }>${page}</span>
											</c:when>
											
											<c:otherwise>
												<span class="js_pagingNumber${search }" data-currentPage=${page }>${page}</span>
											</c:otherwise>
										</c:choose>
									</div>
								</c:forEach>
								<c:if test="${pageGroup.isNextButton() }">
									<span data-nextpage="${pageGroup.viewEndPageNum + 1 }"  class="js_nextButton${search }">▶</span>
								</c:if>
							</div>
						</li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>					


	
	
</div>