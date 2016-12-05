var initStatus;

function initManagerModify() {
	console.log('init');
	
	if(initStatus == 1) {
		initStatus = 0;
		return;
	}
	
	initStatus = 1;
	setEventManagerModify();
}
initManagerModify();

function setEventManagerModify() {
	console.log('setEvent');
	var $container = $('.js_insertNoticeForm');
	
	$container
		.on('click', '.js_insertBtn', insertNotice)
		
}
function insertNotice(e) {
	var $noticeTitle = $('.js_noticeTitle'),
		$noticeImportant = $('.js_noticeImportant'),
		$noticeContents = $('.js_noticeContents'),
		managerNoticeTitle = $noticeTitle.val(),
		managerNoticeContents = $noticeContents.val(),
		managerNoticeImportant = $noticeImportant.is(':checked');
		
		if (managerNoticeTitle == '') {
			alert('글 제목을 입력해 주세요');
		} else if(managerNoticeContents == '') {
			alert('글 내용을 입력해 주세요');
		} else if(managerNoticeTitle.length > 32) {
			alert('제목은 30자까지만 입력이 가능합니다.');
		} else {
			url	   = '/movie/async/asyncService/managerInsertNotice',
			params = {
					'managerNoticeTitle' : managerNoticeTitle,
					'managerNoticeContents' : managerNoticeContents,
					'managerNoticeImportant' : managerNoticeImportant
			},
			cbf	   = function(insertResult){
						location.href=insertResult.data;
						alert('공지사항 등록이 완료되었습니다.');
					};
					
			$.post(url, params, cbf);	
		}
}