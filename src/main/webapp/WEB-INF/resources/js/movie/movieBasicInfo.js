
function init() {
	setEvent();
}

init();

function setEvent() {
	var $container = $('.js_movieBasicInfoContainer');
	
	$container
		.on('click', '.js_tab_review', tabReview)
		.on('click', '.js_firstPage', locationFirstPage)
		.on('click', '.js_prePage', locationPrePage)
		.on('click', '.js_currentPage', locationCurrentPage)
		.on('click', '.js_nextPage', locationNextPage)
		.on('click', '.js_lastPage', locationLastPage)
		.on('click', '.js_reviewInsertBtn', reviewInsert)
		.on('click', '.js_deleteBtn', reviewDelete)
		.on('click', '.js_movieModifyBtn', managerMovieModifyForm)
		.on('click', '.js_movieIntroModifyBtn', managerMovieModifyForm)
		.on('keyup', '.js_reviewText', reviewText)
		.on('click', '.js_movieInsertBtn', managerMovieInsertForm)
		
}

function reviewText() {
	if ($(this).val().length > $(this).attr('maxlength')) {
        alert('최대 120자까지 입력 가능 합니다.');
        $(this).val($(this).val().substr(0, $(this).attr('maxlength')));
    }
}

function managerMovieInsertForm() {
	var url = '/movie/mainService/managerMovieInsertForm';
	
	submit(url);
}

function managerMovieModifyForm() {
	var $movieTitle = $('.js_basicInfomovieTitle'),
		movieTitle  = $movieTitle.attr('data-movie-info-title'),
		$movieNO    = $('.js_movieModifyBtn'),
		movieNO     = $movieNO.data('movieNo');
	
		url	    = '/movie/mainService/managerMovieModifyForm?movieInfoTitle=' + movieTitle + '&movieNO='+ movieNO ;
	
	submit(url);
}

/*******************글 삭제 *******************/
function reviewDelete() {
	var $movieInfoTitle = $('.js_basicInfomovieTitle'),
		movieInfoTitle  = $movieInfoTitle.attr('data-movie-info-title'),
		$userID 	    = $('.js_writerID'),
		userID 		    = $userID.attr('data-write-id'),
		$reviewContents = $('.js_reviewContents'),
		reviewContents  = $reviewContents.text(),
		deleteNO = $('.js_deleteBtn').val(),
			
		url	    = '/movie/mainService/deleteReviewBoard',
		params  = {
					'reviewNo' : deleteNO,
					'movieInfoTitle' : movieInfoTitle,
					'deleteReviewID' : userID
					},
		cbf		= function(deleteResult) {
					$('.js_reviewBoardContainer').html(deleteResult);
				  };
				  
	  alert('작성하신 글을 삭제합니다.');		  
	  $.post(url, params, cbf);			  
}

/*******************글등록*******************/
function reviewInsert() {
	var $reviewText = $('.js_reviewText'),
		$reviewScore = $('.js_starScore'),
		$movieInfoTitle = $('.js_basicInfomovieTitle'),
    	reviewText = $reviewText.val(),
		reviewScore = $reviewScore.text(),
		movieInfoTitle = $movieInfoTitle.attr('data-movie-info-title'),
		
		url	    = '/movie/mainService/insertReviewBoard',
		params  = { 
				     'reviewContents' : reviewText,
					 'reviewScore' : reviewScore,
				 	 'movieInfoTitle' : movieInfoTitle
			   	  },
		cbf		= function(insertResult) {
						$('.js_reviewBoardContainer').html(insertResult);
				  };
		if(reviewText == '') {
			alert('글을 입력해 주세요.');
		} else {
			$.post(url, params, cbf);
		}
}

/*******************탭이동*******************/
function tabReview() {
	var $movieInfoTitle = $('.js_basicInfomovieTitle'),
		movieInfoTitle = $movieInfoTitle.attr('data-movie-info-title'),
		
		url = '/movie/mainService/getReviewBoard',  
		params = { 'movieInfoTitle' : movieInfoTitle }
		cbf	= function(mav) {
				$('.js_reviewBoardContainer').html(mav);
			  }; 
			  
	$.post(url,params, cbf);
}

/*******************페이징*******************/
/*처음PAGE*/
function locationFirstPage() {
	var firstPageData = $('.js_firstPage').attr('data-firstPage'),
		$movieInfoTitle = $('.js_basicInfomovieTitle'),
		movieInfoTitle = $movieInfoTitle.attr('data-movie-info-title'),
	
	
		url	    = '/movie/mainService/getReviewBoardPage',
		params  = { 
					'movieInfoReviewPage' : firstPageData,
					'movieInfoTitle' : movieInfoTitle
				  },
		cbf     = function(mav) {
					$('.js_reviewBoardContainer').html(mav);
				  };
				  
	$.post(url, params, cbf);
}
/*이전PAGE*/
function locationPrePage() {
	var prePageData = $('.js_prePage').attr('data-prePage'),
		$movieInfoTitle = $('.js_basicInfomovieTitle'),
		movieInfoTitle = $movieInfoTitle.attr('data-movie-info-title'),
	
		url	  		 = '/movie/mainService/getReviewBoardPage',
		params       = { 
						 'movieInfoReviewPage' : prePageData,
						 'movieInfoTitle' : movieInfoTitle
						},
		cbf   	     = function(mav){
						$('.js_reviewBoardContainer').html(mav);
					   };
					   
	$.post(url, params, cbf);	
}
/*현재PAGE*/
function locationCurrentPage() {
	var currentPage = $(this).attr('data-currentPage'),
		$movieInfoTitle = $('.js_basicInfomovieTitle'),
		movieInfoTitle = $movieInfoTitle.attr('data-movie-info-title'),
	
		url	         = '/movie/mainService/getReviewBoardPage',
		params       = { 
		 				 'movieInfoReviewPage' : currentPage,
						 'movieInfoTitle' : movieInfoTitle
						},
		cbf   	     = function(mav){
						$('.js_reviewBoardContainer').html(mav);
					   }
		$.post(url, params, cbf);
	
}
/*다음PAGE*/
function locationNextPage() {
	var nextPageData = $('.js_nextPage').attr('data-nextPage'),
		$movieInfoTitle = $('.js_basicInfomovieTitle'),
		movieInfoTitle = $movieInfoTitle.attr('data-movie-info-title'),
	
		url	         = '/movie/mainService/getReviewBoardPage',
		params       = { 
						 'movieInfoReviewPage' : nextPageData ,
						 'movieInfoTitle' : movieInfoTitle
						},
		cbf   	     = function(mav){
						$('.js_reviewBoardContainer').html(mav);
					   };
	$.post(url, params, cbf);
}
/*마지막PAGE*/
function locationLastPage() {
	var lastPageData = $('.js_lastPage').attr('data-lastPage'),
		$movieInfoTitle = $('.js_basicInfomovieTitle'),
		movieInfoTitle = $movieInfoTitle.attr('data-movie-info-title'),
		
		url	  		 = '/movie/mainService/getReviewBoardPage',
		params       = { 
						 'movieInfoReviewPage' : lastPageData  ,
						 'movieInfoTitle' : movieInfoTitle
						},
		
		cbf   	     = function(mav){
						$('.js_reviewBoardContainer').html(mav);
					   };
	$.post(url, params, cbf);
}

/*************reviewBoard.js**************/

var $star = $(".star-input");
var checkStarScore = 0;
function starFocusIn() {
	$(this).addClass("focus");
}

function starFocusOut() {
	var $this = $(this);
    setTimeout(function(){
      if($this.find(":focus").length === 0){
        $this.removeClass("focus");
      }
    }, 100);
}

function starChange() {
	$('.js_starScore').text($(this).next().text());
	checkStarScore = $(this).next().text();
}

function starMouseOver() {
	var $checked = $star.find(":checked");
    if($checked.length === 0){
      $('.js_starScore').text("0");
    } else {
      $('.js_starScore').text($checked.next().text());
    }
    
	$('.js_starScore').text($(this).text());
}

function starMouseLeave() {
	$('.js_starScore').text(checkStarScore);
}

var starRating = function(){
     
  $(document)
    .on("focusin", ".star-input>.input", starFocusIn)
    .on("focusout", ".star-input>.input", starFocusOut)
    .on("change", ".star-input :radio", starChange)
    .on("mouseover", ".star-input label", starMouseOver)
    .on("mouseleave", ".star-input>.input", starMouseLeave);
};
starRating();
