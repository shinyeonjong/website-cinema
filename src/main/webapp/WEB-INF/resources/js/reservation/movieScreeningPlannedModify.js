initScreeningPlannedMod();

function initScreeningPlannedMod() {
	setEventsMod();
}

function setEventsMod() {
	var $container = $('.js_screeningPlannedModContainer');
	$container
		.on('click', '.js_addBtn', compAdd)
		.on('click', '.js_screeningSubmitBtn', screeningSubmitBtnClick)
		.on('click', '.js_screeningModifyDeleteBtn', screeningModifyDeleteBtnClick)
		.on('change', '.js_selectDate', selectDateCheck)
}

function selectDateCheck() {
	var releaseDate = $('.js_selectMovieTitle').children("option:selected").data('movieScreeningDate');
	if(releaseDate > $(this).val()) {
		alert('개봉일자 : ' + $('.js_selectMovieTitle').children("option:selected").data('movieScreeningDate')
				+ '\n개봉일자 이전은 등록할 수 없습니다.');
		$(this).val(releaseDate);
	}
}

function compAdd() {
	var $list = $('.js_addListUl');
	$list.append('<li class="js_addListLi">' + $('.js_addListLi').html() + '</li>');	
}

function screeningSubmitBtnClick() {
	var $selectTheater = $('.js_selectTheater'),
		$selectMovieTitle = $('.js_selectMovieTitle'),
		$selectDate = $('.js_selectDate'),
		$selectTime = $('.js_selectTime');
	
	var movieTheater='', movieTitle='', dateTime='', price='';
	
	for(var i = 0, size = $selectTheater.length; i < size; i++) {
		movieTheater += $selectTheater[i].value + '#';
		movieTitle += $selectMovieTitle[i].value + '#';
		
		var t = $selectTime[i].value.split(':')[0];
		price += (t > 6 &&  t < 10 ? 7000 : 10000) + '#';
		dateTime += $selectDate[i].value.replaceAll('-', '.') + ' ' + $selectTime[i].value + '#';
	}
	
	
	var url = '/movie/async/asyncService/screeningPlannedUpdate',
		params = {
			'theaters' : movieTheater,
			'movieTitles' : movieTitle,
			'dateTimes' : dateTime,
			'prices' : price
		},
		cbf = function(result) {
			if(result.success) {
				locationMenus('POST','/movie/mainService/showtimes','reservation','showtimes' );
			} else {
				alert('중복되는 시간이 있습니다. 다시 입력해주세요.');
			}
		}
	$.post(url, params, cbf);
}

function screeningModifyDeleteBtnClick() {
	if($('.js_addListLi').length > 1) {
		$(this).parents('.js_addListLi').remove();
	} else {
		alert('더 이상 삭제할 수 없습니다.');
	}
}


String.prototype.replaceAll = function(org, dest) {
    return this.split(org).join(dest);
}