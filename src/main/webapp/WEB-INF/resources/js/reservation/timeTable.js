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
		if($(this).text() == '▼ 12.01') {
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

function locationTicketingPage() {
	var movieTitle = $(this).data('movie-title'),
		movieTime = $(this).data('movie-time'),
		movieTheater = $(this).data('movie-theater'),
		movieDate = $(this).data('movie-date'),
		movieYear = $(this).data('movie-year');
	
	var addParams = 'movieTitle=' + movieTitle +
					'&movieTime=' + movieTime +
					'&theater=' + movieTheater +
					'&screeningDate=' + movieDate +
					'&movieYear=' + movieYear;
	location.href='/movie/mainService/ticketing?' + addParams ;
}

function setShowTimes() {
	var $container = $('.js_showtimeTableConatainer');
	var $showTime       = $('.js_showClickTimeTable');
		showTime = $showTime;
		console.log(showTime);
	
		$container
			.on('click', '.js_showClickTimeTable' ,movie_date)
			.on('click', '.js_screeningPlannedAdd', screeningPlannedAdd)
			.on('click', '.js_movieroom1', locationTicketingPage)
}

function initShowTimes() {

	setShowTimes();
}

initShowTimes();