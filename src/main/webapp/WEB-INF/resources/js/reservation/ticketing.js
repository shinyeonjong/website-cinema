var movieTitle;
var movieTime;
var $personCnt;
var theater;
var moviePrice;
var seatArr;
var $movieTitle;
var $screeningDate;
var $movieTime;

document.write('<script src="/resources/js/service/notice/managerNotice.js"></script>');

function calendarPrevBtnClick() {
	var thisMonth 	= $('.js_calendarMonth').text() - 1,
		thisYear 	= $('.js_calendarYear').text(),
		month 		= thisMonth < 1 ? 12 : thisMonth,
		year 		= thisMonth < 1 ? thisYear - 1 : thisYear,
		
		url 		= '/movie/mainService/calendar',
		params 		= {
				'calendarMonth' : month,
				'calendarYear' : year
		},
		cbf 		= function(result) {
				$('.calendar').html(result);
		};
	$.post(url, params, cbf);
		
}

function calendarNextBtnClick() {
	var thisMonth 	= Number($('.js_calendarMonth').text()) + 1,
		thisYear 	= Number($('.js_calendarYear').text()),
		month 		= thisMonth > 12 ? 1 : thisMonth,
		year 		= thisMonth > 12 ? thisYear + 1 : thisYear,
		
		url 		= '/movie/mainService/calendar',
		params 		= {
				'calendarMonth' : month,
				'calendarYear' : year
		},
		cbf 		= function(result) {
				$('.calendar').html(result);
		};
	$.post(url, params, cbf);
}

function movieTitleClick() {
	$personCnt = undefined;
	$('.js_seatInfo').html('');
	if($movieTitle != undefined) {
		cssColor($movieTitle, '', '');
	}
	$movieTitle = $(this);
	var title = $movieTitle.text().trim();
		movieTitle = title.substr(3, title.length).trim();
	if($screeningDate == undefined) {
		$('.js_screeningTimeViewer').text('예매일자를 선택해주세요.');
	} else {
		getMovieTicketingInfo();
	}
	
	cssColor($movieTitle, '#e8e3e5');
}

function ticketingDateClick() {
	
	$personCnt = undefined;
	$('.js_seatInfo').html('');
	if($screeningDate != undefined) {
		cssColor($screeningDate, '', '');
	}
	
	$screeningDate = $(this);
	var year = $('.js_calendarYear').text(),
		month = $('.js_calendarMonth').text(),
		date = $screeningDate.text().trim();
	
	date = date < 10 ? '0' + date : date;
	screeningDate = year + '.' + month + '.' + date;
//	#ffd5e3
	if(movieTitle == undefined) {
		$('.js_screeningTimeViewer').text('영화를 선택해주세요.');
	} else {
		getMovieTicketingInfo();
	}
	cssColor($screeningDate, '#ffd5e3');
}
function nonTicketingDateClick() {
	$personCnt = undefined;
	$('.js_screeningTimeViewer').text('상영중인 영화 정보가 없습니다.');
}

function getMovieTicketingInfo() {
	var url = '/movie/mainService/screeningPlanned',
		params = {
				'screeningDate' : screeningDate,
				'movieTitle' : movieTitle
		},
		cbf = function(result) {
			$('.js_movieTime').html(result);
		};
	
	$.post(url, params, cbf);
}

function movieTimeClick() {
	if($movieTime != undefined) {
		cssColor($movieTime, '', '');
	}
	$movieTime = $(this);
	movieTime = $movieTime.text().trim();
	theater = $movieTime.attr('data-theater');
	
	cssColor($movieTime, '', 'lightcoral');
	
	var url = '/movie/mainService/seatInfo',
		params = {
				'screeningDate' : screeningDate,
		        'movieTitle' : movieTitle,
		        'movieTime' : $movieTime.text(),
		        'theater' : theater     
		},
		cbf = function(result) {
			$('.js_seatInfo').html(result);
		};
		
	$.post(url, params, cbf);
}

function ticketingPersonCntClick() {
	if($moviePrice == undefined) {
		var $moviePrice = $('.js_moviePrice');		
	} 
	if($personCnt != undefined) {
		cssColor($personCnt, '', '');
	}
	seatArr = [];

	cssColor($('.js_seatNum'), '', '');
	
	$personCnt = $(this);
	cssColor($personCnt, 'rosybrown', 'white');

	var price = $moviePrice.attr('data-price');
	$moviePrice.text(numberWithCommas(price * $personCnt.text()))
	moviePrice = $moviePrice.text().trim();
}

function seatNumberClick() {
	// 인원수 선택이 되지 않았다면
	if($personCnt == undefined) {
		alert('인원수를 선택해주세요.');
		return;
	}
	
	var $clickSeat = $(this),
		clickSeatNum = $clickSeat.attr('data-seatNum');

	// 선택한 자리가 이미 선택한 자리라면
	if(seatArr.includes(clickSeatNum)) {
		seatArr.splice(seatArr.indexOf(clickSeatNum), 1);
		cssColor($clickSeat, '', '');
		console.log(seatArr);
		$('.js_letTicketingBtn').css({'visibility': 'hidden'});
		return;
	}
	
	// 이미 선택된 인원만큼 자리를 다 선택했다면
	if(seatArr.length < $personCnt.text()) {
		seatArr.push(clickSeatNum);
		cssColor($clickSeat, '#c74848', '#ffd6d6')
	} else {
		console.log(seatArr);
		alert('더이상 선택할 수 없습니다.');
	}
	
	if(seatArr.length == $personCnt.text()) {
		$('.js_letTicketingBtn').css({'visibility': 'visible'});
	}
}

function payPagePopup() {
	var left = ( $(window).scrollLeft() + ($(window).width() - $('.js_payPopupPage').width()) / 2 ),
		top = $(window).scrollTop();
	
	$('.js_payPopupPage').css({
		'display' : 'inline',
		'left' : left,
		'top' : '0px',
		'position' : 'fixed'
	});
	
	var $popupContainer = $('.js_payPopupPage');
	$popupContainer.on('click', '.js_popMovieCancel', payPagePopupCancel)
				   .on('click', '.js_popMoviePaying', payPagePopupPaying)
	
	showTicketingInfoAndPaying();
	
}

function showTicketingInfoAndPaying() {
	var $popMovieTitle = $('.js_popMovieTitle'),
		$popMovieDate = $('.js_popMovieDate'),
		$popMovieTime = $('.js_popMovieTime'),
		$popMovieTheater = $('.js_popMovieTheater'),
		$popMovieCnt = $('.js_popMovieCnt'),
		$popMovieSeat = $('.js_popMovieSeat'),
		$popMoviePrice = $('.js_popMoviePrice');
	var url = '/movie/async/asyncService/getMoviePoster',
		params = {'movieTitle' : movieTitle};
		cbf = function(result) {
			$popMovieTitle.text('<' + movieTitle + '>');
			$popMovieDate.text(screeningDate);
			$popMovieTime.text(movieTime);
			$popMovieTheater.text(theater + '관');
			$popMovieSeat.text(seatArr);
			$popMovieCnt.text('총' + $personCnt.text() + '매');
			$popMoviePrice.text('결제되는 금액 ' + moviePrice + '원');
			
			$('.js_popMoviePoster').html(
				'<img alt="poster" src="/resources/img/poster/' + result.data + '" width="175px" height="265px"/>'
			);
		}
		
	$.post(url, params, cbf);
}

function payPagePopupCancel() {
	$('.js_payPopupPage').css({
		'display' : 'none',
		'position' : 'absolute'
	});
}

function payPagePopupPaying() {
	var url = '/movie/mainService/ticketingPaying',
		cbf = function(result) {
			$('.js_popupMovieBaseGround').html(result);
			$('.js_payingMessagePrice').text($('.js_moviePrice').text());
		}
	
	$.post(url,cbf);
}

function setEvent() {
	var $container = $('.js_ticketing');
	$container.on('click', '.js_calendarPrev', calendarPrevBtnClick)
			  .on('click', '.js_calendarNext', calendarNextBtnClick)
			  .on('click', '.js_movieTitleClick', movieTitleClick)
			  .on('click', '.js_ticketingDateClick', ticketingDateClick)
			  .on('click', '.js_nonTicketingDateClick', nonTicketingDateClick)
			  .on('click', '.js_movieTimeClick', movieTimeClick)
			  .on('click', '.js_ticketingPersonCnt', ticketingPersonCntClick)
			  .on('click', '.js_seatNum', seatNumberClick)
			  .on('click', '.js_letTicketingBtn', payPagePopup)
}

function cssColor(element, backgroundColor, color) {
	element.css({
		'background-color' : backgroundColor,
		'color' : color
	});
}

function init() {
	setEvent();
}

init();