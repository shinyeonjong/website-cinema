initManagerReserveMenu();
function initManagerReserveMenu() {
	console.log('dd');
	setEventManagerReserverMenu();
}

function setEventManagerReserverMenu() {
	var $reserveContainer = $('.js_managerReserveContainer')
	$reserveContainer
		.on('click', '.js_reserveSearchBtn', searchReserveInfo)
		.on('keyup', '.js_reserveTicketNumPost', inputNum)
		.on('keyup', '.js_reserveTicketNumBack', inputNum)
		.on('click', '.js_ticketCancelBtn', ticketCancelBtnClick)
}

function inputNum() {
	var $num = $(this);
	
	$num.val($num.val().replace(/[^0-9]/g,""));
	if($num.val().length == $num.attr('maxlength')) {
		$num.next('.js_inputs').focus();
	}
}


function searchReserveInfo() {
	var date = $('.js_reserveDate').val(),
		movieTitle = $('.js_reserveMovieTitle').val() == '선택' ? '' : $('.js_reserveMovieTitle').val(),
		movieTheater = $('.js_reserveTheater').val() == '선택' ? '' : $('.js_reserveTheater').val(),
		ticketNumPost = $('.js_reserveTicketNumPost').val(),
		ticketNumBack = $('.js_reserveTicketNumBack').val(),
		ticketNum = ticketNumPost + '-' + ticketNumBack,
		url = '/movie/mainService/searchReserveInfo',
		params = {
			'searchDate' : date,
			'managerMovieTitle' : movieTitle,
			'searchTheater' : movieTheater,
			'ticketNumPost' : ticketNumPost,
			'ticketNumBack' : ticketNumBack
		},
		cbf = function(result) {
			$('.js_managerBody').html(result);
		}
	
	$.post(url, params, cbf);
}

function ticketCancelBtnClick() {
	var $ticketCancelBox = $('.js_ticketCancelBox'),
		date = new Date,
		m = date.getMonth() + 1,
		d = date.getDate(),
		year = date.getFullYear(),
		month = m < 10 ? '0' + m : m,
		day = d < 10 ? '0' + d : d,
		thisDay = year + '.' + month + '.' + day;
	var ticketNums = "";

	var cnt = 0;
	for(var i = 0, size = $ticketCancelBox.length; i < size; i++) {
		if($ticketCancelBox[i].checked) {
			cnt += 1;
			if($ticketCancelBox[i].dataset.ticketingdate.slice(0, 10) < thisDay) {
				alert('지나간 날짜의 티켓은 취소할 수 없습니다.');
				return;
			}
			
			ticketNums += $ticketCancelBox[i].dataset.ticketNum + ',';
		}
		
	}
	
	if(cnt == 0) {
		alert('선택된 정보가 없습니다.');
		return;
	}
	
	var url = '/movie/mainService/managetTicketCancel',
		params = {
			'ticketNums' : ticketNums
		},
		
		cbf = function(result) {
			alert('예매취소가 완료되었습니다.');
			$('.js_managerBody').html(result);	
		}
	
	$.post(url, params, cbf);
}