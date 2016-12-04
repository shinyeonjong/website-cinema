var cardType, 			
	cardNum,		  			
	cardPWD,			
	cardExpiryDateMonth,
	cardExpiryDateYear, 	
	cardOwnerBirth; 		





function inputNum() {
	var $num = $(this);
	console.log('gg');
	$num.val($num.val().replace(/[^0-9]/g,""));
	if($num.val().length == $num.attr('maxlength')) {
		$num.next('.js_inputs').focus();
	}
}

String.prototype.replaceAll = function(org, dest) {
    return this.split(org).join(dest);
}

function payingBtnClick() {
	cardType 			= $('.js_cardType').val(),
	cardNum 			= $('.js_cardNumber1').val() +
			  			  $('.js_cardNumber2').val() +
			  			  $('.js_cardNumber3').val() +
			  			  $('.js_cardNumber4').val(),
	cardPWD 			= $('.js_cardPWD').val(),
	cardExpiryDateMonth = $('.js_cardExpiryDate1').val(),
	cardExpiryDateYear 	= $('.js_cardExpiryDate2').val(),
	cardOwnerBirth 		= $('.js_cardOwnerBirth').val();
	
	console.log(cardType);
	var url = '/movie/mainService/ticketingTry',
		params = {
			'cardType' 			  : cardType,		       
			'cardNum' 			  : cardNum,		  			
			'cardPWD' 			  : cardPWD,			       
			'cardExpiryDateMonth' : cardExpiryDateMonth,       
			'cardExpiryDateYear'  : cardExpiryDateYear, 	   
			'cardOwnerBirth' 	  : cardOwnerBirth,
			'screeningDate'		  : screeningDate + ' ' + movieTime,
			'screeningDateType'	  : screeningDate.replaceAll('.','') + movieTime.replaceAll(':',''),
			'movieTitle'		  : movieTitle,
			'personCnt'			  : $personCnt.text(),
			'theater'			  : theater,
			'seatArr'			  : seatArr.toString()
		},
		cbf = function(result) {
			$('.js_popupMovieBaseGround').html(result);
		}
	
	if(cardInfoValidation()) {
		$.post(url, params, cbf);
	} else {
		alert('입력하신 정보가 정확하지 않습니다. 다시 입력해주세요.');
	}
}

function cardInfoValidation() {
	
	if(!cardTypeValidation(cardType)) {
		return false;
	}
	if(!cardNumValidation(cardNum)) {
		return false;
	}
	if(!cardPWDValidation(cardPWD)) {
		return false;
	}
	if(!cardExpiryDateValidation(cardExpiryDateMonth, cardExpiryDateYear)) {
		return false;
	}
	if(!cardOwnerBirthValidation(cardOwnerBirth)) {
		return false;
	}
	return true;
}

function cardTypeValidation(cardType) {
	if(cardType == '선택') {
		return false;
	}
	return true;
}

function cardNumValidation(cardNum) {
	if(!($.isNumeric(cardNum)) || cardNum.length != 16) {
		return false;
	}
	return true;
}

function cardPWDValidation(cardPwd) {
	if(!($.isNumeric(cardPwd)) || cardPwd.length != 2) {
		return false;
	}
	return true;
}

function cardExpiryDateValidation(month, year) {
	var toDate = new Date();
	var toYear = parseInt((toDate.getFullYear()).toString().substring(2, 4));
	var toMonth = toDate.getMonth() + 1;
	
	if(!($.isNumeric(month)) || !($.isNumeric(year))) {
		return false;
	}
	if(month.length != 2 || year.length != 2) {
		return false;
	}
	if(month > 12) {
		return false;
	}
	if(year < toYear ) {
		return false;
	}
	if(year == toYear && month < toMonth) {
		return false
	}
	return true;
}

function cardOwnerBirthValidation(date) {

	var y = parseInt(date.substring(0, 2), 10),
		m = parseInt(date.substring(2, 4), 10), 
		d = parseInt(date.substring(4, 6), 10); 
	
	var dt = new Date(y, m-1, d); 
	var toYear = new Date();
	
	y = y <= parseInt((toYear.getFullYear()).toString().substring(2, 4)) ?
		y + 2000 : y + 1900;
	
	if(dt.getDate() != d) {
		return false;
	
	} else if(dt.getMonth()+1 != m) {
		return false
	
	} else if(toYear.getFullYear() < y) {
		return false
	
	} else if(toYear.getFullYear() <= y && toYear.getMonth()+1 < m) {
		return false;
	
	} else {
		return true;
	
	} 	
}

function payingCancelBtnClick() {
	payPagePopupCancel();
	var url = '/movie/mainService/ticketingCancelSet',
		cbf = function(result) {
			$('.js_payPopupPage').html(result);
		}
	$.post(url, cbf);
}

function setPayingEvents() {
	var $payingContainer = $('.js_payingContainer');
	$payingContainer.on('keyup', '.js_cardNumber1', inputNum)
					.on('keyup', '.js_cardNumber2', inputNum)
					.on('keyup', '.js_cardNumber3', inputNum)
					.on('keyup', '.js_cardNumber4', inputNum)
					.on('keyup', '.js_cardPWD', inputNum)
					.on('keyup', '.js_cardExpiryDate1', inputNum)
					.on('keyup', '.js_cardExpiryDate2', inputNum)
					.on('keyup', '.js_cardOwnerBirth', inputNum)
					.on('click', '.js_payingButton', payingBtnClick)
					.on('click', '.js_popMoviePayingCancel', payingCancelBtnClick)
}

function initPaying() {	
	setPayingEvents();
	console.log('paying 로딩됨')
}
initPaying();
