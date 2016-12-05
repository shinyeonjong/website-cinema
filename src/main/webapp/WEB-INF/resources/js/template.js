function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function locationJoinTerms(e) {
	var url = '/movie/mainService/getTemplate';
		dir = 'join/joinTerms';
		page = 'joinTerms';
		js	= 'join/joinTerms';
		css = 'join/joinTerms';
	e.preventDefault();
	e.stopPropagation();
	
	
	submit(url, dir, page, js, css);
}
function locationLogon() {
	var $userID  = $('#user-id'),
		$userPWD = $('#user-pwd'),
		url		 = '/movie/async/asyncService/login',
		params	 = 
			{
				'userID' : $userID.val(),
				'userPWD' : $userPWD.val()
			},
		cbf		 = 
			function(result) {
				console.log(result.success);
				if(result.success) {
					locationMain();
				} else {
					alert(result.data);
				}
			};
		
	if($userID.val() != '' && $userPWD.val() != '') {
		$.post(url, params, cbf);
	} else {
		alert('아이디와 패스워드 모두 입력해주세요.');
	}
}

function locationMain() {
	var url 	= '/';
	
	submit(url);
}

/*shin searchPwd*/
function locationSearchPwd(e) {
	var url = '/movie/mainService/getTemplate',
		dir = 'searchPwd',
		page = 'searchPwd',
		js	 = 'searchPwd/searchPwd',
		css = 'searchPwd/searchPwd';
	e.preventDefault();
	e.stopPropagation();
	submit(url, dir, page, js, css);
}

function logout() {
	
	var url = '/movie/mainService/logout';
		cbf = function() {
			locationMain();
		}
	$.post(url, cbf);
}
/*shin myInfo*/
function locationMyInfo(params) {
	var url  = '/movie/mainService/viewMyInfo';
		dir  = 'myInfo';
		page = 'myInfo';
		js   = 'myInfo/myInfo';
		css  = 'myInfo/myInfo';
	submit(url, dir, page, js, css, params);
}

/**아이디 찾기**/
function locationSearchID(e) {
	var url  = '/movie/mainService/getTemplate'
		dir  = 'searchId';
		page = 'searchId';
		js   = 'searchId/searchId';
		css  = 'searchId/searchId';
	e.preventDefault();
	e.stopPropagation();
	submit(url, dir, page, js, css);
}


function locationMenus(method, action, directory, page) {
	var getMethod 	 = method;
	var getAction 	 = action;
	var getDirectory = directory;
	var getPage 	 = page;
	$(document).ready(function() {
		$('form').attr({'method' : getMethod});
		$('form').attr({'action' : getAction});
		$('#hidden-dir').val(getDirectory);
		$('#hidden-page').val(getPage);
		$('form').submit();
		
	});
}
	
function submit(action, directory, page, js, css, params) {
	$(document).ready(function() {
		$('form').attr({'method' : 'POST'});
		$('form').attr({'action' : action});
		$('#hidden-dir').val(directory);
		$('#hidden-page').val(page);
		$('#hidden-js').val(js);
		$('#hidden-css').val(css);
		$('.js_hiddenParam').val(params);
		$('form').submit();
	});
}

function submitGET(action, directory, page, js, css) {
	$(document).ready(function() {
		$('form').attr({'method' : 'GET'});
		$('form').attr({'action' : action});
		$('#hidden-dir').val(directory);
		$('#hidden-page').val(page);
		$('#hidden-js').val(js);
		$('#hidden-css').val(css);
		$('form').submit();
	});
}


function pwdRegexCheck(pwd) {
	var regex = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
	return regex.test(pwd);
}

function idRegexCheck(id) {
	var regex = /^[A-Za-z0-9_-]{4,15}$/;
	return regex.test(id);
}

function emailRegexCheck(email) {
	var regex = /^[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\.[_0-9a-zA-Z-]+){1,2}$/;
	return regex.test(email);
}

function searchMovieInfo() {
	var searchWord = $('.js_searchMovieText').val();

	if(searchWord.length < 2) {
		alert('검색어는 2자 이상 입력해주세요.');
	} else {
		$('.js_hiddenParam').val(searchWord);
		submit('/movie/mainService/searchMovieInfo', 'include/section', 'searchMovieInfo', 'searchMovieInfo', 'searchMovieInfo');
	}
}


function managerMenuBtnClick() {
	submit(
		'/movie/mainService/maganerMenu',
		'managerMenu',
		'managerMenu',
		'managerMenu/managerMenu',
		'managerMenu/managerMenu'
	);
}
function locationInsertMovie() {
	var url = '/movie/mainService/managerMovieInsertForm';
	
	submit(url);
}

function locationEventPage() {
	var noticeNo = $(this).data('noticeNo');
	location.href='/movie/mainService/noticeBoardView?noticePage=1&noticeNo=' + noticeNo;
}

function setEventTemplate() {
	var $form = $('.js_form');
	
	$form.on('click', '.js_logo', locationMain)
		 .on('click', '.js_lobin-btn', locationLogon)
		 .on('click', '.js_join', locationJoinTerms)
		 .on('click', '.js_goMain', locationMain)
		 .on('click', '.js_searchPWD', locationSearchPwd)
		 .on('click', '.js_searchID', locationSearchID)
		 .on('click', '.js_myInfo', locationMyInfo)
		 .on('click', '.js_logout', logout)
		 .on('click', '.js_searchMovieBtn', searchMovieInfo)
		 .on('click', '.js_managerMenuBtn', managerMenuBtnClick)
		 .on('click', '.js_searchIdBack',locationSearchID)
		 .on('click', '.js_joinResult', locationMain)
		 .on('click', '.js_mainInsertMovieBtn', locationInsertMovie)
		 .on('click', '.js_mainEventImage', locationEventPage)
		 
}

function initTemplate() {
	setEventTemplate();
}

initTemplate();
