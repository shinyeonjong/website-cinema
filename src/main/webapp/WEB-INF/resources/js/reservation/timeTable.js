var checked = 0;

function movie_date() {
	//var movie_timeTable = document.getElementsByClassName('movie_screening_date');
	var $showTime       = $('.js_showClickTimeTable');
		$moviePanel     = $('.js_moviePanel');
		
		checked = checked == 0 ? 1 : 0;
		
		indexNo = $(this).index($('.js_showClickTimeTable'));
		console.log(indexNo);
		showTime   = $showTime;
		moviePanel = $moviePanel;
//		$('.js_movieTitle:first').classList.toggle('show');
		
		console.log(showTime);
		console.log(moviePanel);

	 	this.classList.toggle('active');
		this.nextElementSibling.classList.toggle('show');
		if($(this).text() == '▼ 11.27') {
			if(checked == 1) {
				$('.screening-date-panel0').css({
				    'opacity': '0',
				    'max-height' : '0px'
				});
				
				$('button.screening-date-btn0').css({
					'background-color' : ''
				});
			} else {
				$('.screening-date-panel0').css({
				    'opacity': '1',
				    'max-height' : '800px'
				});
			 	this.classList.toggle('active');
				this.nextElementSibling.classList.toggle('show');
			}
		}
		
}

function screeningPlannedAdd() {
	submit(
		'/movie/mainService/managerScreeningPlannedModify',
		'reservation/managerMovieScreeningPlanned',
		'movieScreeningPlannedModify',
		'reservation/movieScreeningPlannedModify',
		'reservation/movieScreeningPlannedModify'
	);
}

function setShowTimes() {
	var $container = $('.js_showtimeTableConatainer');
	var $showTime       = $('.js_showClickTimeTable');
		showTime = $showTime;
		console.log(showTime);
	
		$container
			.on('click', '.js_showClickTimeTable' ,movie_date)
			.on('click', '.js_screeningPlannedAdd', screeningPlannedAdd)	
}

function initShowTimes() {

	setShowTimes();
}

initShowTimes();