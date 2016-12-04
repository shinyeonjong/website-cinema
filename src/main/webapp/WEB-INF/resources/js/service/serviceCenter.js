/***자주묻는게시판, 문의 사항 탭 ***/
function frequentlyBoardTab() {
	
	var url = '/movie/mainService/serviceCenterFreQuentlyBoard'
		cbf = function(result) {
			var $frequently   = $('.js_frequentlyBoardContainer');
				
				$frequently
					.html(result);
				$('.container__serviceCenterFrequenty_content .serviceCenterFrequenty__contents')
				.css('display', 'none');
				$('.js_frequentlyBoard').css('color','tan');
				$('.js_QuestionBoard').css('color','');
		};
	$.post(url, cbf);
}

function questionBoardTab() {
	var url = '/movie/mainService/questionBoard'
		cbf = function(result) {
			var $questionTab = $('.js_frequentlyBoardContainer');
			$('.js_QuestionBoard').css('color','tan');
			$('.js_frequentlyBoard').css('color','');
			$questionTab
				.html(result);

		};
	$.post(url, cbf);
}
/*************************************자주묻는게시판*********************************************/

/***자주묻는게시판 슬라이드(제목누르면 실행)***/
function frequentlyBoard() {
			$(this).parent().siblings('.on').toggleClass('on'); //재목(부모함수)를 누르면 그 class에 감싸있는 것을 on한다.
			$(this).parent().siblings().children('.serviceCenterFrequenty__contents').slideUp('fast'); //제목을 누르면 자식함수가 다시 올라온다
			$(this).parent().toggleClass('on'); //부모를 누르면 감싸있는 자식이 on된다.
			$(this).siblings('.serviceCenterFrequenty__contents').stop('true','true').slideToggle('fast');
}

/***페이징 번호를 눌렀을때 다음으로 전환하는 메소드***/
function serviceCenterButton() {
	var pageboard = $(this).text();
	url    = '/movie/mainService/serviceCenterFreQuentlyBoard'
	params = {'pageboard' : pageboard}
	cbf    = function(result) {
			var $frequently = $('.js_serviceContentTab');
			
			$frequently
				.html(result);
			$('.container__serviceCenterFrequenty_content .serviceCenterFrequenty__contents')
				.css('display', 'none');
	};
		$.post(url, params, cbf);
}

/***페이징처리 다음버튼 처리***/
function pagingNextButton() {
	var $userClickPageNum      = $('.js_nextButton');
		userClickPageNum       = $userClickPageNum.attr('data-nextpage');
		
	url    = '/movie/mainService/serviceCenterFreQuentlyBoard';
	params = {'pageboard' : userClickPageNum};
	cbf    = function(result) {
			var $frequently = $('.js_serviceContentTab');
			$frequently
				.html(result);
			$('.container__serviceCenterFrequenty_content .serviceCenterFrequenty__contents')
				.css('display', 'none');
	};
		$.post(url, params, cbf);
}


/***페이징처리 이전버튼 처리***/
function pagingPreButton() {
	var $userClickPageNum = $('.js_preButton');
		userClickPageNum  = $userClickPageNum.attr('data-prepage');
		
	url    = '/movie/mainService/serviceCenterFreQuentlyBoard';
	params = {'pageboard' : userClickPageNum};
	cbf    = function(result) {
			var $frequently = $('.js_serviceContentTab');
			$frequently
				.html(result);
				$('.container__serviceCenterFrequenty_content .serviceCenterFrequenty__contents')
					.css('display', 'none');
		};
	$.post(url, params, cbf);
}


/*******************자주묻는게시판 다음 페이지********************/

//자주묻는게시판 검색(처음페이지)
function searchQuestionBoard() {
	var $userSearchInput = $('.js_searchCenter_input');
		userSearchInput  = $('.js_searchCenter_input').val();
		
		console.log(userSearchInput);
		
	url     = '/movie/mainService/getUserSearchList',
	params  = {'serviceCenterSearchWord' : userSearchInput},
			
	cbf     = function(mav) {
				$('.js_frequentlyBoardContainer').html(mav);
				
				$('.container__serviceCenterFrequenty_content .serviceCenterFrequenty__contents')
					.css('display', 'none');
			};
			if(userSearchInput == '') {
				alert('입력바랍니다.');
				return false;
			}
			$.post(url, params, cbf);	
}

//자주묻는 게시판 검색 이전 페이지
function searchPrePageBoard() {
	var preSearchPage = $('.js_preButtonSearch').attr('data-prepage');
	
	url     = '/movie/mainService/getUserSearhPage',
	params  = {'pageboard' : preSearchPage,
				'serviceCenterSearchWord' : userSearchInput},
	cbf     = function(mav) {
				$('.js_frequentlyBoardContainer').html(mav);
				$('.container__serviceCenterFrequenty_content .serviceCenterFrequenty__contents')
					.css('display', 'none');
			};
	$.post(url, params, cbf);	
}


//검색 다음 게시판
function searchNextPageBoard() {
	var nextSearchPage = $('.js_nextButtonSearch').attr('data-nextpage');
	$userSearchInput = $('.js_searchCenter_input');
	userSearchInput  = $('.js_searchCenter_input').val();
	
	url     = '/movie/mainService/getUserSearhPage',
	params  = {'pageboard' : nextSearchPage,
			   'serviceCenterSearchWord' : userSearchInput},
	
	cbf     = function(mav) {
				$('.js_frequentlyBoardContainer').html(mav);
				$('.container__serviceCenterFrequenty_content .serviceCenterFrequenty__contents')
					.css('display', 'none');
			};
	$.post(url, params, cbf);	
}

//현재페이지
function searchCurrentPageBoard() {
	var CurrentSearchPage = $(this).attr('data-currentPage');
		PageNum           = $(this).attr('data-PageNum');
	console.log(CurrentSearchPage);

	url     = '/movie/mainService/getUserSearhPage',
	params  = {'pageboard' : CurrentSearchPage,
				'serviceCenterSearchWord' : userSearchInput},
	cbf     = function(mav) {
				$('.js_frequentlyBoardContainer').html(mav);
				$('.container__serviceCenterFrequenty_content .serviceCenterFrequenty__contents')
					.css('display', 'none');
			};
	$.post(url, params, cbf);	
}




/*********************************문의사항게시판*********************************************/

//현재 
function questionBoardPageNumber() {
	var questionBoard = $(this).text();
		
	
	url    = '/movie/mainService/serviceCenterQuestionBoardChange';
	params = {'questionBoard' : questionBoard};
	cbf    = function(result) {
				var $question = $('.js_questionBoardContainer');
				
				$question
				.html(result);
	};
		$.post(url, params, cbf);
}
	
//이전페이지
function questionBoardPagePreNumber() {
	var $userClickPageNum = $('.js_questionPreButton');
	userClickPageNum  = $userClickPageNum.attr('data-QuestionprePage');
	
		url    = '/movie/mainService/serviceCenterQuestionBoardChange';
		params = {'questionBoard' : userClickPageNum};
		cbf    = function(result) {

				var $question = $('.js_questionBoardContainer');
					$question
						.html(result);
				
		};
		$.post(url, params, cbf);
}

//다음페이지
function questionBoardpageNextNumber() {
	
	var $userClickPageNum = $('.js_questionNextButton');
	userClickPageNum  = $userClickPageNum.attr('data-QuestionnextPage');
	
	url    = '/movie/mainService/serviceCenterQuestionBoardChange';
	params = {'questionBoard' : userClickPageNum};
	cbf    = function(result) {

			var $question = $('.js_questionBoardContainer');
			$question
				.html(result)
				.show();
	};
		$.post(url, params, cbf);
	}

/******글보기*******/
function questionViewBoard() {
	
	var userClickPageNum = $(this).attr('data-questionBoardPageNum');
	
	var url    = '/movie/mainService/questionViewBoard';
		params = {'questionBoardNum' : userClickPageNum};
		cbf    = function(mav) {
			var $questionBoardViewchange = $('.js_frequentlyBoardContainer');
				
				$questionBoardViewchange.html(mav);
	};
		
	$.ajax(
		{
			url : url,
			type : 'POST',
			data : params,
			beforeSend : function(xmlHttpRequest) {
				xmlHttpRequest.setRequestHeader("AJAX", "true");
			},
			error:function(xhr, textStatus, error) {
				if(xhr.status == "500") {
					location.href='/movie/mainService/loginPage';
				}
			},
			success : cbf
		}
	);
}


//글등록페이지이동
function questionWriteForm() {
	var url    = '/movie/mainService/questionBoardWriteForm';
		cbf    = function(mav) {
			var $questionBoardViewchange = $('.js_frequentlyBoardContainer');
				$questionBoardViewchange.html(mav);
	};
		$.post(url, cbf);
}


function activePwdInput() {
	if($(".js_boardCheck").is(":checked") == false) {
		$('.js_boardWriteBoardPwd').attr({'readonly' : true});
	} else {
		$('.js_boardWriteBoardPwd').attr({'readonly' : false});
		
	}
}


//문의사항 글등록하기
function insertQuestionBoard() {
	
	var isResult         = true;
		$insertTitle    = $('.js_boardContent');
	    $insertTextArea = $('.js_boardTextArea');
	    $insertboardPwd = $('.js_boardWriteBoardPwd');
	    
	    insertPwdcheck  = $(".js_boardCheck").is(':checked'); //비밀번호체크여부
	    insertTitle     = $('.js_boardContent').val(); 
	    insertTextArea  = $('.js_boardTextArea').val();
	    insertboardPWd  = $('.js_boardWriteBoardPwd').val(); //비
	    
	    //유효성 검사
	    InsertPwdCheck      = /([^0-9])/;
	    insertPwdLength = insertboardPWd.length;
	    
	    
	    url    = '/movie/async/asyncService/InsertAskWriteBoard';
	    params = {
	    		'insertPwdcheck' : insertPwdcheck,
	    		'insertTitle'    : insertTitle,
	    		'insertTextArea' : insertTextArea,
	    		'insertboardPWd' : insertboardPWd,
	    		beforeSend : function(xmlHttpRequest) {
	    			xmlHttpRequest.setRequestHeader("AJAX", "true")
	    		}
	    };
		    cbf    = function(result) {
		    	
		    	if(result.success) {
		    		$.post(url2, params2, cbf2);
		    	} else {
		    		if(result.data == 'async') {
		    			alert('모두 입력 바랍니다.');
		    		} else {
		    			$('.js_templateBody').html(result);
		    		}
		    	}
		    };
		    
		    url2    = '/movie/mainService/InsertAskWriteBoard',
		    params2 = {
		    		'insertPwdcheck' : insertPwdcheck,
		    		'insertTitle'    : insertTitle,
		    		'insertTextArea' : insertTextArea,
		    		'insertboardPWd' : insertboardPWd
		    },
		    cbf2   = function(result2) {
		    	var $questionBoardViewchange = $('.js_frequentlyBoardContainer');
				$questionBoardViewchange.html(result2);
		    }
		    
		    if(insertTitle == '' || insertTextArea == '') {
		    	alert('모두입력해주세요!');
		    	isResult    = false;
		    } else if(insertPwdcheck == true && insertboardPWd == '') {
		    	alert('비밀번호 입력 해주세요');
		    	isResult    = false;
		    } else if(insertPwdLength < 0 || insertPwdLength > 6 ) {
		    	alert('숫자 4글자로 입력해주세요!');
		    	$('.js_boardWriteBoardPwd').val('');
		    	isResult = false;
		    } else {
		    	$.post(url, params, cbf);
		    	isResult    = true;
		    }
	}


function passWordCheck() {
	if(event.keyCode >= 48 && event.keyCode <=57) {
		return true;
	} else if($(".js_boardCheck").is(":checked") == false) {
		alert('체트박스먼저 체크하세요!');
		return false;
	}else {
		alert("숫자0~9까지만 입력하세요!");
		event.returnValue = false;
	}
}
		    	

	    

//문의 사항 수정폼
function updateQuestionBoard() {
	userClickNum = $('.js_boardViewNo').attr('data-userClickNum');
	
		console.log(userClickNum);
	
	    url    = '/movie/mainService/updateWriteForm?'+ userClickNum;
	    params = {'questionBoardNum' : userClickNum};
	    cbf    = function(mav) {
	    	console.log(mav);
	    	var $questionBoardViewchange = $('.js_frequentlyBoardContainer');
			
			$questionBoardViewchange.html(mav);
	    };

	    $.ajax(
    		{
    			url : url,
    			type : 'POST',
    			data : params,
    			beforeSend : function(xmlHttpRequest) {
    				xmlHttpRequest.setRequestHeader("AJAX", "true");
    			},
    			error:function(xhr, textStatus, error) {
    				if(xhr.status == "500") {
    					location.href='/movie/mainService/loginPage';
    				}
    			},
    			success : cbf
    		}
    	);
}


function UpdateFormPassWordCheck() {
	if(event.keyCode >= 48 && event.keyCode <=57) {
		return true;
	} else if($(".js_UpdateboardCheck").is(":checked") == false) {
		alert('체트박스먼저 체크하세요!');
		return false;
	} else {
		alert("숫자0~9까지만 입력하세요!");
		event.returnValue = false;
	}
}




function activeRePwdInput() {
	if($(".js_UpdateboardCheck").is(":checked") == false) {
		$('.js_UpdateboardWriteBoardPwd').attr({'readonly' : true});
	} else {
		$('.js_UpdateboardWriteBoardPwd').attr({'readonly' : false});
		
	}
}


//완전한 수정
function completeUpdateQuestionBoard() {
	
	userClickNum = $('.js_boardViewNo').attr('data-userClickNum');
	
	var isResult         = true;
	 
		$insertTitle     =  $('.js_UpdateboardContent'); //제목
	    $insertTextArea  = $('.js_UpdateBoardTextArea'); //내용
	    $insertboardPwd  = $('.js_UpdateboardWriteBoardPwd'); //비밀번호
	    $insertUser_id   = $('.js_UpdateboardUserId');
	    $insertWriteDate = $('.js_UpdateboardWriteDate');
	    
	    
	    
	    insertPwdcheck  = $(".js_UpdateboardCheck").is(":checked"); //비밀번호체크여부
	    insertTitle     = $('.js_UpdateboardContent').val(); 
	    insertTextArea  = $('.js_UpdateBoardTextArea').val();
	    insertboardPWd  = $('.js_UpdateboardWriteBoardPwd').val(); //비
	    insertUser_id  = $('.js_UpdateboardUserId').val();
	    insertWriteDate = $('.js_UpdateboardWriteDate').val();

	    insertPwdLength = insertboardPWd.length;
	    
	    url    =  '/movie/async/asyncService/completeUPdateWriteBoard';
	    params = {
	    		'updateQuestionBoardNum' : userClickNum,
	    		'insertTitle'            : insertTitle,
	    		'insertTextArea'         : insertTextArea,
	    		'insertboardPWd'         : insertboardPWd,
	    		'insertPwdcheck'         : insertPwdcheck	
	    		//'insertUser_id'          : insertUser_id,
	    		//'insertWriteDate'        : insertWriteDate
	    	};
	    
	    cbf    = function(result) {
	    	if(result.data) {
	    		$.post(url2, params2, cbf2);
	    	} else {
	    		alert('모두입력바랍니다.');
	    	}
	    };
	    
	    url2    = '/movie/mainService/completeUPdateWriteBoard',
	    params2 = {
	    		'updateQuestionBoardNum' : userClickNum,
	    		'insertTitle'            : insertTitle,
	    		'insertTextArea'         : insertTextArea,
	    		'insertboardPWd'         : insertboardPWd,
	    		'insertPwdcheck'         : insertPwdcheck	
	    },
	    cbf2    = function(result2) {
			    	var $questionBoardViewchange = $('.js_frequentlyBoardContainer');
					$questionBoardViewchange.html(result2);
	    }
	    if(insertTitle == '' || insertTextArea == '') {
	    	alert('모두입력해주세요!');
	    	isResult = false;
	    }else if(insertPwdcheck == true && insertboardPWd == '') {
	    	alert('비밀번호 입력 해주세요');
	    	isResult = false;
	    }  else if(insertPwdLength < 0 || insertPwdLength > 6 ) {									여긴데
	    	alert('숫자 4글자로 입력해주세요!');
	    	$('.js_boardWriteBoardPwd').val('');
	    	isResult = false; 
	    } else {
	    	isResult = true;
	    	$.ajax(
    			{
    				url : url,
    				type : 'POST',
    				data : params,
    				beforeSend : function(xmlHttpRequest) {
    					xmlHttpRequest.setRequestHeader("AJAX", "true");
    				},
    				error:function(xhr, textStatus, error) {
    					if(xhr.status == "500") {
    						location.href='/movie/mainService/loginPage';
    					}
    				},
    				success : cbf
    			}
    		);
	    }
}

//문의사항 삭제
function deleteQuestionBoard() {
	userClickNum = $('.js_boardViewNo').attr('data-userClickNum');
	console.log(userClickNum);
	
	url     = '/movie/mainService/completeDeleteQuestionBoard';
	params  = {'updateQuestionBoardNum' : userClickNum};
	cbf     = function(mav) {
				alert('삭제 성공!');
				var $questionBoardViewchange = $('.js_frequentlyBoardContainer');
				$questionBoardViewchange.html(mav);
	};
		if(confirm('정말 삭제하시겠습니까?') == true) {
			$.post(url, params, cbf);
		} else {
			return;
		}
}

//비번체크
function recheckpwdQuestionForm() {
	var 
		$userInsertPwd      = $('.QuestionBoardReCheckPWd_input');
		userInsertPwd       = $('.QuestionBoardReCheckPWd_input').val(); //내가입력한 비밀번호
		
		userClickNum  = $('.js_QuestionReCheckPwdboardViewNo').attr('data-questionBoardPageNum');

		console.log(userClickNum);
		//글등록한 번호 가지고 디비가서 비교해서 결과값 추출
		url    =  '/movie/async/asyncService/insertPwdCheck';
		params = {
				'userInsertPwd'       : userInsertPwd,
				'questionBoardNum'    : userClickNum
			};
		cbf    = function(result) {
			
			console.log(result.mav);

				if(result.data) {
					$.post(url2, param2, cbf2);
				} else {
					alert('다시 입력 바랍니다');
				}
		
				//true false로 main에서 값 가지고 와서
				//mav가 아닌 메소드로 이동하고 싶엉
		};
		
		url2	= '/movie/mainService/insertPwdCheck',
		param2	= {
				'questionBoardNum'    : userClickNum
		},
		cbf2	= function(result2) {
			var $questionBoardViewchange = $('.js_frequentlyBoardContainer');
			$questionBoardViewchange.html(result2);
		}
		
		
		if(userInsertPwd == '') {
			alert('입력해주세요');
		}
		else {
			$.post(url, params, cbf);
		}
		
}


function RepassWordCheck() {
	if(event.keyCode >= 48 && event.keyCode <=57) {
		return true;
	}else {
		alert("숫자0~9까지만 입력하세요!");
		event.returnValue = false;
	}
}

/****************************관리자 페이지********************************************/
//자주묻는게시판 글등록폼
function managementFreQuentlyWriteBoard() {
	
	url    = '/movie/mainService/managementServiceCenterWriteForm',
	cbf    = function(mav) {
			 $('.js_frequentlyBoardContainer').html(mav);
	};
	$.post(url, cbf);
}

//관리자 글등록

function managementFreQuentlyInsertBoard() {
	var 
		$question    = $('.js_managementQuestionContent');
		$asked       = $('.js_managementQuestionButton_confirmTextArea');
		
		question     = $('.js_managementQuestionContent').val();
		asked		 = $('.js_managementQuestionButton_confirmTextArea').val();
		
		url    = '/movie/mainService/managementWriteBoard',
		params = {'question' : question,
				  'asked'     : asked},
		cbf    = function(mav) {
					$('.js_frequentlyBoardContainer').html(mav);
					$('.container__serviceCenterFrequenty_content .serviceCenterFrequenty__contents')
					.css('display', 'none');
				
		};
		
		if(asked == '' || question =='') {
			alert('모두입력해주세요');
		} else {
			$.post(url, params, cbf)
		}
}

//수정하는 폼으로 가기
function managementFreQuentlyUpdateBoard() {
	var no      = $(this).attr('data-clickManageNum');
		
	url    = '/movie/mainService/managementUpdateBoard',
	params = { 'no' : no},
	cbf    = function(mav) {
			 $('.js_frequentlyBoardContainer').html(mav);
	};
	$.post(url, params, cbf);
}

//수정완료
function managementFreQuentlyUpdateComplete() {
	
	var isResult  = false;
		$question = $('.js_managementQuestionUpdateContent');
		$asked    = $('.js_managementQuestionUpdateButton_confirmTextArea');
		
		question  = $('.js_managementQuestionUpdateContent').val();
		asked     = $('.js_managementQuestionUpdateButton_confirmTextArea').val();
		no		  = $(this).attr('data-updateFormNum');
		
		console.log(question);
		console.log(asked);
		console.log(no);
		
		url    = '/movie/async/asyncService/managementUpdateBoardComplete',
		params = {'question' : question,
				  'asked'    : asked,
				  'no'        : no},
		cbf    = function(result) {
					if(result.data) {
						$.post(url2, params2, cbf2);
						
					} else {
						alert('모두 입력하세요');
					}
					
		};
		url2	= '/movie/mainService/managementUpdateBoardComplete',
		params2	= {
				'question' : question,
				 'asked'   : asked,
				 'no'      : no
		},
		cbf2	= function(result2) {
			var $questionBoardViewchange = $('.js_frequentlyBoardContainer');
			$questionBoardViewchange.html(result2);
			$('.container__serviceCenterFrequenty_content .serviceCenterFrequenty__contents')
			.css('display', 'none');
		}
		
		if(question == '' || asked == '') {
			alert('모두입력해주세요');
			isResult = false;
		}
		else {  
			isResult = true;
			$.post(url, params, cbf);
		}
}

function managementFreQuentlyDeleteComplete() {
	var 
		no = $(this).attr('data-clickManageNum');
	
	url		= '/movie/mainService/managementDeleteBoardComplete',
	params	= {'no' : no},
	cnf		= function(mav) {
			alert('삭제성공');
				$('.js_frequentlyBoardContainer').html(mav);
			};
			if(confirm('정말 삭제하시겠습니까?') == true) {
				$.post(url, params, cbf);
			} else {
				return;
			}
}
/*****************관리자 문의사항************************/
//문의사항 게시판 답글달기
function managementReplyWriteForm() {
	var $userQuestionTitle = $('.js_questionViewBoardTitle');
		userQuestionBoardNum = $('.js_boardViewNo').attr('data-userClickNum');
	
		userQuestionTitle  = $('.js_questionViewBoardTitle').val();
	
	console.log(userQuestionTitle);
	console.log(userQuestionBoardNum);
	
	url    = '/movie/mainService/managementReplyBoardForm',
	params = {'userQuestionTitle' : userQuestionTitle,
			  'questionBoardNum' : userQuestionBoardNum},
	cbf    = function(mav) {
			 $('.js_frequentlyBoardContainer').html(mav);
	};
	$.post(url, params, cbf);
}


function activePwdInputManager() {
	if($(".js_ReplyBoardCheck").is(":checked") == false) {
		$('.js_ReplyBoardWriteBoardPwd').attr({'readonly' : true});
	} else {
		$('.js_ReplyBoardWriteBoardPwd').attr({'readonly' : false});
		
	}
}

//답글 등록
function managementReplyWriteComplete() {

	
	var isResult         = true;
		userClickNum = $('.js_boardViewNo').attr('data-userClickNum');
		$replyTitle  = $('.js_ReplyBoardContent');
		replyTitle   = $('.js_ReplyBoardContent').val();
		
		$replyContent = $('.js_ReplyBoardTextArea');
		replyContent  = $('.js_ReplyBoardTextArea').val();
		
		$replyPwd     = $('.js_ReplyBoardWriteBoardPwd');
		replyPwd      = $('.js_ReplyBoardWriteBoardPwd').val();
		
		$replyNo	  = $('.js_replyNo');
		replyNo		  = $('.js_replyNo').val();

		$replyDepth   = $('.js_replyDepth');
		replyDepth    = $('.js_replyDepth').val();
		 
		$replyStep    = $('.js_replyStep');
		replyStep     = $('.js_replyStep').val();
		
	    replytPwdcheck  = $(".js_ReplyBoardCheck").is(":checked"); //비밀번호체크여부
		
		
		console.log(replyTitle);
		console.log(replytPwdcheck);
		console.log(replyContent);
		console.log(replyPwd);
		console.log(replyNo);
		console.log(replyDepth);
		console.log(replyStep);
		
		url		= '/movie/async/asyncService/managementReplyBoardFormComplete',
		params	= {'userClickNum' : userClickNum,
					'replyTitle' : replyTitle,
					'replyContent' : replyContent,
					'replyPwd' : replyPwd,
					'replyNo' : replyNo,
					'replyDepth' : replyDepth,
					'replyStep' : replyStep,
					'replytPwdcheck': replytPwdcheck},
					
		cbf		=	function(result) {
				if(result.data) {
					$.post(url2, params2, cbf2)
				} else {
					alert('다시 입력바랍니다');
				}
		};
		
		url2    = '/movie/mainService/managementReplyBoardFormComplete',
		params2 = {'userClickNum' : userClickNum,
					'replyTitle' : replyTitle,
					'replyContent' : replyContent,
					'replyPwd' : replyPwd,
					'replyNo' : replyNo,
					'replyDepth' : replyDepth,
					'replyStep' : replyStep,
					'replytPwdcheck': replytPwdcheck},
		cbf2	= function(result2) {
					var $questionBoardViewchange = $('.js_frequentlyBoardContainer');
					$questionBoardViewchange.html(result2);
		}

		if(replyTitle == '' || replyContent == '') {
			isResult = false;
			alert('모두입력해주세요');
		} else if(replytPwdcheck == true && replyPwd == '') {
			isResult = false;
			alert('비밀번호 입력해주세요');
		} else {
			isResult = true;
			$.post(url, params, cbf);
		}
}


/************************시작***************************/
function setServiceCenter() {
	console.log('setEvent');
	var $container     = $('.js_serviceCenter');
		$questionBoard = $('.js_js_questionBoardWriteContainer');
	
		//$container.on('click', '.js_content',serviceCenter);//고객센터안에있는 탭
		$container.on('click', '.js_frequentlyBoard', frequentlyBoardTab) //자주묻는게시판 탭이동
		          .on('click', '.js_QuestionBoard', questionBoardTab) // 문의사항게시판으로 탭이동
		          .on('click', '.js_sub', frequentlyBoard) // 자주묻는질문들
		          .on('click', '.js_pagingNumber' , serviceCenterButton) //페이징번호
		          .on('click', '.js_currentNumber', serviceCenterButton)
		          .on('click', '.js_nextButton',pagingNextButton) // 다음버튼
		          .on('click', '.js_preButton', pagingPreButton) //이전버튼
	
		
		/**문의사항게시판**/
		
				.on('click', '.js_currentQuestionPage', questionBoardPageNumber)  //현재번호
				.on('click', '.js_QuestionPagingNumber', questionBoardPageNumber) //페이지이동
				.on('click', '.js_questionPreButton', questionBoardPagePreNumber) //이전페이지이동
				.on('click', '.js_questionNextButton', questionBoardpageNextNumber) //다음페이지이동
				.on('keyPress','.js_boardWriteBoardPwd',passWordCheck)  //비번 유효성체크
				.on('KeyPress','.js_questionBoardReChecked',RepassWordCheck)
				
				
				.on('click', '.js_QuestionWriteForm', questionWriteForm) // 글쓰기폼이동
				.on('click', '.js_questionBoard_title', questionViewBoard) //문의사항
				.on('click', '.js_QuestionList', questionBoardTab) //목록으로
				.on('click', '.js_questionButtonCancel',frequentlyBoardTab) //목록으로(취소)
				.on('click', '.js_QuestionUpdateForm', questionBoardTab) //
				.on('click', '.js_serviceCenter_button',searchQuestionBoard) //검색버튼
				.on('click', '.js_preButtonSearch',searchPrePageBoard)
				.on('click', '.js_nextButtonSearch' ,searchNextPageBoard)
				.on('click', '.js_pagingNumberSearch',searchCurrentPageBoard)
				.on('click', '.js_currentNumberSearch',searchCurrentPageBoard)
				
				.on('click', '.js_questionButtonConfirm', insertQuestionBoard) //글 등록하기
				.on('click', '.js_boardCheck',activePwdInput) //비밀글체크
				.on('click', '.js_QuestionWriteDeleteBtn', deleteQuestionBoard)//삭제하기
				.on('click', '.js_QuestionWriteUpdateBtn', updateQuestionBoard) //수정하는 폼으로
				.on('click', '.js_QuestionUpdateBtn',completeUpdateQuestionBoard) //수정하기
				.on('KeyPress', '.js_UpdateboardWriteBoardPwd' ,UpdateFormPassWordCheck) //수정폼에서 비번체크
				.on('click', '.js_UpdateboardCheck', activeRePwdInput) //비밀글 체크됬는지안됬는지여부
				.on('click', '.js_rePwdCheckQuestionForm', recheckpwdQuestionForm) //비밀글 체크됬는지안됬는지여부
		
		
		/**************************************관리자 등록(*******************************************/
		
		.on('click', '.js_button_FrequentlyInsertBtn', managementFreQuentlyWriteBoard) //자주묻는게시판->글등록폼으로가기
		.on('click', '.js_managementQuestionButtonConfirm',managementFreQuentlyInsertBoard) //글등록
		.on('click', '.js_managementQuestionButtonCancel',frequentlyBoardTab)//글등록폼에서 취소버튼
		.on('click', '.js_Updatebtn',managementFreQuentlyUpdateBoard) //수정폼으로가기
		.on('click', '.js_managementQuestionUpdateButtonConfirm',managementFreQuentlyUpdateComplete)
		.on('click', '.js_Deletebtn', managementFreQuentlyDeleteComplete) //글삭제
		.on('click', '.js_ReplyQuestionBtn', managementReplyWriteForm) //답글폼으로가기
		.on('click', '.js_ReplyBoardCheck',activePwdInputManager)
		.on('click', '.js_ReplyQuestionButtonConfirm',managementReplyWriteComplete)
		.on('click', '.js_ReplyQuestionButtonCancel',questionBoardTab)
}

function initServiceCenter() {
	frequentlyBoardTab();
	setServiceCenter();
	console.log('init');
}
	
initServiceCenter();
