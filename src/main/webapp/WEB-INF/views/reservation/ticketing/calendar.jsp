<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="http://icono-49d6.kxcdn.com/icono.min.css">
	<div class="calendar__year">
		<span class="icono-caretLeftCircle js_calendarPrev">
		</span>
		<span>
			<label class="js_calendarYear">${cal.year }</label>년 <label class="js_calendarMonth">${cal.month }</label>월
		</span>
		<span class="icono-caretRightCircle js_calendarNext">
		</span>
	</div>
	<table class="calendar__dayTable">
		<tr class="calendar__week__row">
			<td class="calendar__week__data">일</td>
			<td class="calendar__week__data">월</td>
			<td class="calendar__week__data">화</td>
			<td class="calendar__week__data">수</td>
			<td class="calendar__week__data">목</td>
			<td class="calendar__week__data">금</td>
			<td class="calendar__week__data">토</td>
		</tr>
		<c:forEach items="${cal.days }" var="days">
			<tr class="calendar__day__row">
				<c:forEach items="${days }" var="day">
					<c:choose>
						<c:when test="${day != 0 }">
							<c:choose>
								<c:when test="${(cal.year >= screening.minScreening[0] && cal.year <= screening.maxScreening[0]) 
											&&  (cal.month >= screening.minScreening[1] && cal.month <= screening.maxScreening[1])
											&&  (day >= screening.minScreening[2] && day <= screening.maxScreening[2])}">
									<c:choose>
										<c:when test="${day == userChoiceInfo.movieDay }">
											<td class="
												js_ticketingDateClick 
												js_userChoiceDay 
												calendar__day-background 
												cursor 
												calendar__day-color-possible
												calendar__day-hover-background"
												data-movie-time="${userChoiceInfo.movieTime }"
												data-movie-theater="${userChoiceInfo.movieTheater }">
										</c:when>
										
										<c:otherwise>
											<td class="
												js_ticketingDateClick 
												calendar__day-background 
												cursor 
												calendar__day-color-possible
												calendar__day-hover-background">
										</c:otherwise>
									</c:choose>
										
										${day }
									
									</td>
								</c:when>
								
								<c:otherwise>
									<td class="
										js_nonTicketingDateClick 
										calendar__day-background 
										cursor 
										calendar__day-color-impossible 
										calendar__day-hover-background">
										
										${day }
									
									</td>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<td class="calendar__day-background"></td>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</tr>
		</c:forEach>
	</table>
