 package com.allnightMovies.service;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.allnightMovies.di.Action;
import com.allnightMovies.model.data.MainMenu;
import com.allnightMovies.model.data.MenuList;
import com.allnightMovies.model.data.cinemaInfo.CinemaFrequentlyBoardDTO;
import com.allnightMovies.model.data.cinemaInfo.CinemaFrequentlyBoardNumberDTO;
import com.allnightMovies.model.data.cinemaInfo.CinemaNoticeBoardDTO;
import com.allnightMovies.model.data.cinemaInfo.CinemaNoticeSearchBoardDTO;
import com.allnightMovies.model.data.cinemaInfo.CinemaQuestionBoardDTO;
import com.allnightMovies.model.data.cinemaInfo.CinemaSeatDTO;
import com.allnightMovies.model.data.cinemaInfo.CinemaSeatReserveInfo;
import com.allnightMovies.model.data.movieInfo.MovieBasicInfo;
import com.allnightMovies.model.data.movieInfo.MovieBasicInfoCast;
import com.allnightMovies.model.data.movieInfo.MovieCurrentFilmDTO;
import com.allnightMovies.model.data.movieInfo.MovieReviewBoard;
import com.allnightMovies.model.data.movieInfo.MovieReviewBoardDTO;
import com.allnightMovies.model.data.movieInfo.MovieScreeningDateInfo;
import com.allnightMovies.model.data.movieInfo.MovieScreeningsPlannedDTO;
import com.allnightMovies.model.data.movieInfo.MovieShowTimesMap;
import com.allnightMovies.model.data.movieInfo.MovieShowTitleDTO;
import com.allnightMovies.model.data.movieInfo.MovieStillCut;
import com.allnightMovies.model.data.movieInfo.MovieshowTableDTO;
import com.allnightMovies.model.data.movieInfo.TicketingMovieTimeInfo;
import com.allnightMovies.model.data.theater.CinemaIntroduceDTO;
import com.allnightMovies.model.data.userInfo.ManagerMemberInquiryDTO;
import com.allnightMovies.model.data.userInfo.ManagerUserReserveDTO;
import com.allnightMovies.model.data.userInfo.MovieEndTimeDTO;
import com.allnightMovies.model.data.userInfo.UserCheckEmptySeatsDTO;
import com.allnightMovies.model.data.userInfo.UserPersonalInfoDTO;
import com.allnightMovies.model.data.userInfo.UserSelectTicketingInfo;
import com.allnightMovies.model.data.userInfo.UserTicketingInfo;
import com.allnightMovies.model.params.Params;
import com.allnightMovies.utility.MonthCalendar;
import com.allnightMovies.utility.Paging;
import com.allnightMovies.utility.Paging2;
import com.allnightMovies.utility.ParseCheck;
import com.allnightMovies.utility.RegexCheck;
import com.allnightMovies.utility.SendEmail;
import com.allnightMovies.utility.UtilityEnums;


// @Service 어노테이션
// 스프링이 구동될 때 내부 메소드들이 미리 만들어져 올라가 있다.
// 메인 컨트롤러에서는 별도의 생성 없이 사용 가능.
@Service
public class MainService implements Action {
	private Params params;
//	private MenuList menuList = new MenuList();
	
//	@Autowired
//	MovieMapper movieMapper;
	@Autowired
	DBService dbService;

	
	// 여기서 온갖것들을 실행시켜주면 된다.movieTime
	// ModelAndView 객체에 view 단에서 찍어내야 하는 페이지들도 올려두고 ...
	@Override
	public ModelAndView execute(Params params) throws Throwable {
		Method method = this.getClass().getDeclaredMethod(params.getMethod());
		this.params = params;
		
								// invoke(Object this, Object...args)
		return (ModelAndView) method.invoke(this);
	}

/*****은정. 기본 template의 작동*****/
	// 기본 템플레이트 출력
	public ModelAndView getTemplate() throws Exception {
		
		List<MainMenu> list = this.dbService.getMenus();
		Map<String, MainMenu> mainMenuMap = new MenuList(list).getMainMenuMap();
		ModelAndView mav = new ModelAndView("template");
		String main = this.params.getMain() == null ? "movie" : this.params.getMain();
		
		String sub = this.params.getSub() == null ? mainMenuMap.get(main).getSubMenuList().get(0).getSubMenuPage() : this.params.getSub();
		mav.addObject("main", mainMenuMap.get(main));
		mav.addObject("sub", sub);
		mav.addObject("list", list);
		mav.addObject("directory", this.params.getDirectory());
		mav.addObject("page", this.params.getPage());
		mav.addObject("contentCSS", this.params.getContentCSS() == null ? "mainPage" : this.params.getContentCSS());
		mav.addObject("contentjs", this.params.getContentjs() == null ? "mainPage" : this.params.getContentjs());
		mav.addObject("keepLogin", this.params.getKeepLogin());
		
		return mav;
	}

	
	// 로그아웃
	public ModelAndView logout() throws Exception {
		this.params.getSession().invalidate();
		return this.getTemplate();
	}
	
/*****은정. loginPage *****/	
	public ModelAndView loginPage() throws Exception {
		ModelAndView mav = new ModelAndView("login");
		mav.addObject("loginResult", true);
		return mav;
	}
	
	public ModelAndView requiredManager() {
		ModelAndView mav = new ModelAndView("requiredManager");
		return mav;
	}
	
	
/*****은정. join 회원가입 시의 작동*****/	
	public ModelAndView idCheck() throws Exception {
		ModelAndView mav = new ModelAndView("join/resultText");

		String resultMessage = "사용이 가능한 아이디입니다.";
		String id = this.params.getUserIDCheck();
		boolean bool = true;
		
		if(!RegexCheck.idRegexCheck(id)) {
			resultMessage = "사용할 수 없는 아이디입니다.";
			bool = false;
		}
		if(this.dbService.idCheck(id) > 0) {
			resultMessage = "이미 사용중인 아이디입니다.";
			bool = false;
		}
		
		mav.addObject("result", resultMessage);
		mav.addObject("resultBool", bool);
		mav.addObject("resultBoolID", "id-bool");
		return mav;
	}

	public ModelAndView sendEmail() throws Exception {
		ModelAndView mav = new ModelAndView("join/resultText");
		HttpSession session = this.params.getSession();
		
		// 인증번호 생성
		Random rand = new Random();
		int randNum = rand.nextInt(900000) + 100000;
		System.out.println(">>메인서비스 sendEmail() 인증번호 : " + randNum);
		
		// 메일 발송
		new SendEmail(String.valueOf(randNum), this.params.getUserEmail());
		String result = "인증번호가 발송되었습니다.";
		
		boolean bool = true;
		
		// 세션에 인증번호와 인증을 보낸 시간 등록
		session.setAttribute("certificationNum", randNum);
		session.setAttribute("confirmTime", System.currentTimeMillis());
		
		mav.addObject("result", result);
		mav.addObject("resultBool", bool);
		mav.addObject("resultBoolID", "email-bool");
		return mav;
	}

	public ModelAndView confirmCheck() throws Exception {
		ModelAndView mav = new ModelAndView("join/resultText");
		String result = "입력하신 인증번호와 일치합니다.";
		int inputConfirmNum = this.params.getConfirmNum();
		HttpSession session = this.params.getSession();
		Integer saveConfirmNum = session.getAttribute("certificationNum") == null ? null : (int) session.getAttribute("certificationNum");

		boolean bool = true;
		
		// 인증을 보낸지 3분이 지났다면
		if(System.currentTimeMillis() - (Long)session.getAttribute("confirmTime") > UtilityEnums.CONFIRM_TIME.getConfirmTime()) {
			session.removeAttribute("certificationNum");
			session.setAttribute("confirmTime", 0);
			result = "입력 시간이 초과되었습니다. 인증번호를 다시 받아주세요.";
			bool = false;
		
		// 인증번호를 보낸 적이 없다면	
		} else if(saveConfirmNum == 0 || saveConfirmNum == null) {
			result = "인증번호를 받아주세요.";
			bool = false;
			
		// 인증번호가 불일치하다면
		} else if(!(saveConfirmNum == inputConfirmNum)) {
			result = "인증번호가 일치하지 않습니다.";
			System.out.println(">>메인서비스 confirmCheck() : 저장된 번호-"+ this.params.getSession().getAttribute("certificationNum"));
			bool = false;
			
		} else {
			session.setAttribute("certificationNum", 0);
			session.setAttribute("isConfirm", true);
		}
		mav.addObject("result", result);
		mav.addObject("resultBool", bool);
		mav.addObject("resultBoolID", "confirm-bool");
		
		return mav;
	}

	public ModelAndView confirmNumInit() throws Exception {
		ModelAndView mav = new ModelAndView();
		HttpSession session = this.params.getSession();
		session.setAttribute("isConfirm", false);
		return mav;
	}
		
	public ModelAndView locationJoinSuccess() throws Exception {
		this.params.setDirectory("join");
		this.params.setPage("joinResult");
		this.params.setContentCSS("join/joinSuccess");
		this.params.setContentjs("join/joinSuccess");
		return this.getTemplate();	
	}

	@Override
	public String executeString(Params params) throws Throwable {
		return null;
	}
	
/*****은정. ticketing *****/
	public ModelAndView ticketing() throws Exception {
		this.params.setDirectory("reservation/ticketing");
		this.params.setPage("ticketing");
		this.params.setContentCSS("reservation/ticketing");
		this.params.setContentjs("reservation/ticketing");
		
		MovieScreeningDateInfo screeningDate = this.dbService.getMaxScreeningDate();
		screeningDate.setScreeningDate();
		
		ModelAndView mav = this.getTemplate();
		mav.addObject("cal", new MonthCalendar());
		mav.addObject("screening", screeningDate);
		mav.addObject("movieTitle", this.dbService.getMovieTitle());
		return mav;
	}
		
	public ModelAndView calendar() {
		MovieScreeningDateInfo screeningDate = this.dbService.getMaxScreeningDate();
		screeningDate.setCalendarMonth(this.params.getCalendarMonth());
		screeningDate.setScreeningDate();
		ModelAndView mav = new ModelAndView("reservation/ticketing/calendar");
		mav.addObject("cal", new MonthCalendar(this.params.getCalendarYear(), this.params.getCalendarMonth()));
		mav.addObject("screening", screeningDate);
		return mav;
	}
	
	public ModelAndView screeningPlanned() {
		ModelAndView mav = new ModelAndView("reservation/ticketing/screeningPlanned");
		List<TicketingMovieTimeInfo> list = this.dbService.getMovieTime(this.params.getMovieTitle(), this.params.getScreeningDate());
		mav.addObject("movieTimeList", list);
		return mav;
	}
	
	public ModelAndView seatInfo() {
		ModelAndView mav = new ModelAndView("reservation/ticketing/seatInfo");
		String screeningDateTime = this.params.getScreeningDate() + " " + this.params.getMovieTime();
		String movieTitle = this.params.getMovieTitle();
		int theater = this.params.getTheater();
		String strTheater = String.valueOf(theater);
		// 이미 예약되어있는 좌석 정보
		CinemaSeatReserveInfo seatReserveInfo = new CinemaSeatReserveInfo();
		seatReserveInfo.setMovieTitle(movieTitle)
					   .setTheater(theater)
					   .setMovieScreeningDate(screeningDateTime);
		
		List<CinemaSeatDTO> seatInfoList = this.dbService.getTheaterSeatInfo(seatReserveInfo);
		int colCnt = this.dbService.getTheaterSeatColCnt(strTheater);
		colCnt = theater == 1 ? colCnt + 1 : colCnt;

		
		int moviePrice = this.dbService.getTicketPriceInfo(screeningDateTime, strTheater);

		mav.addObject("moviePrice", moviePrice);
		mav.addObject("seatInfoList", seatInfoList);
		mav.addObject("colCnt", colCnt);
		return mav;
	}
	
/*****은정. ticketing : paying *****/
	public ModelAndView ticketingPaying() {
		ModelAndView mav = new ModelAndView("reservation/ticketing/payPopupPage-paying");
		return mav;
	}
	
	public ModelAndView ticketingTry() throws ParseException {
		ParseCheck check = new ParseCheck();
		boolean result = true;
		String cardType 		= this.params.getCardType();
		String cardNum 			= this.params.getCardNum();
		String cardPWD 			= this.params.getCardPWD();
		String cardExpiryMonth 	= this.params.getCardExpiryDateMonth();
		String cardExpiryYear	= this.params.getCardExpiryDateYear();
		String cardOwnerBirth 	= this.params.getCardOwnerBirth();
		String[] seatArr 		= this.params.getSeatArr().split(",");
		
		
		if(cardType.equals("선택")) {
			result = false;
		}
		if(cardNum.length() != 16 || !check.isParseLong(cardNum)) {
			result = false;
		}
		if(!check.isParseInt(cardPWD) || cardPWD.length() != 2) {
			result = false;
		}

		SimpleDateFormat format = new SimpleDateFormat("yy-MM");
		String[] toDate = (format.format(System.currentTimeMillis())).split("-");
		
		if(!check.isParseInt(cardExpiryMonth) || !check.isParseInt(cardExpiryYear)) {
			result = false;
		}
		if(cardExpiryMonth.length() != 2 || cardExpiryYear.length() != 2) {
			result = false;
		}
		int toYear = Integer.parseInt(toDate[0]);
		int expiryYear = Integer.parseInt(cardExpiryYear);
		int toMonth = Integer.parseInt(toDate[1]);
		int expiryMonth = Integer.parseInt(cardExpiryMonth);
		if(toYear > expiryYear) {
			result = false;
		}
		if(toYear == expiryYear && expiryMonth < toMonth) {
			result = false;
		}
		
		if(expiryMonth > 12) {
			result = false;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		Date today = new Date();
		Date inputDate = dateFormat.parse(cardOwnerBirth);
		double diffDays = (today.getTime() - inputDate.getTime()) / (24 * 60 * 60 * 1000);
		if(diffDays < 1) {
			result = false;
		}
		String resultMessage = "";
		
		List<String> seatsList = Arrays.asList(seatArr);
		if(result) {
			String userID 		 = (String) this.params.getSession().getAttribute("userID");
			String screeningDate = this.params.getScreeningDate();
			String movieTitle 	 = this.params.getMovieTitle();
			int theater 		 = this.params.getTheater();
			
			
			// 먼저 사용자가 선택한 좌석이 이미 예매되어있는지를 확인한다.
			UserCheckEmptySeatsDTO checkSeatsDTO = new UserCheckEmptySeatsDTO();
			checkSeatsDTO.setMovieScreeningDate(screeningDate)
						 .setMovieTitle(movieTitle)
						 .setSeatsList(seatsList)
						 .setTheater(theater);

			for(String n : checkSeatsDTO.getSeatsList()) {
				System.out.print(n + ",");
			}
			Integer seats = this.dbService.checkEmptySeats(checkSeatsDTO);
			if(seats > 0) {
				resultMessage = "이미 예약된 좌석입니다.<br/>다른 좌석을 선택해주세요.";
				result = false;
			} else {
				
				String ticketNum = screeningDate.replace(".", "").substring(4, 8);
				ticketNum += "-" + String.valueOf(System.currentTimeMillis()).substring(5, 13);
				Integer moviePrice = this.dbService.getTicketPriceInfo(screeningDate, String.valueOf(theater));
				
				SimpleDateFormat formatDate = new SimpleDateFormat("yyyy.MM.dd HH:mm");
				Date d = formatDate.parse(screeningDate);
				
				
				
				UserTicketingInfo userTicketingInfo = new UserTicketingInfo();
				userTicketingInfo.setMovieScreeningDate(screeningDate)
								 .setMovieTitle(movieTitle)
								 .setTheater(theater)
								 .setTheaterSeat(seatsList)
								 .setUserID(userID)
								 .setUserTicketCount(seatsList.size())
								 .setUserTicketNumber(ticketNum)
								 .setUserTotalPrice(moviePrice * seatsList.size())
							 	 .setMovieScreeningDateType(d);
				this.dbService.userTicketingInfoInsert(userTicketingInfo);
				resultMessage = "예매가 완료되었습니다. <br/> 예매확인 페이지에서 확인 가능합니다.";
			}
		
		} else {
			resultMessage = "카드 정보가 정확하지 않습니다. <br/> 다시 진행해주세요.";
			result = false;
		}
		
		ModelAndView mav = new ModelAndView("reservation/ticketing/ticketingResult");
		mav.addObject("resultBool", result);
		mav.addObject("resultMessage", resultMessage);
		return mav;
	}
	
	public ModelAndView ticketingCancelSet() {
		return new ModelAndView("reservation/ticketing/paypopup");
	}
	
	
/***** 은정. 예매내역확인 *****/
	public ModelAndView ticketingConfirmation() {
		ModelAndView mav = new ModelAndView("myInfo/ticketConfirmation/ticketInfoView");
		String userID = (String) this.params.getSession().getAttribute("userID");
		List<UserSelectTicketingInfo> ticketingInfo = this.dbService.reservationSeatInfo(userID);
		List<UserSelectTicketingInfo> sendTicketingInfo = new ArrayList<UserSelectTicketingInfo>();
		
		for(UserSelectTicketingInfo info : ticketingInfo) {
			MovieEndTimeDTO movieEndTime = this.dbService.getMovieEndTime(info.getUserTicketNumber());
			info.setMovieEndTime(movieEndTime.getMovieEndTime());
			info.setMoviePoster(movieEndTime.getMoviePoster());
			sendTicketingInfo.add(info);
		}
		
		mav.addObject("ticketingInfo", sendTicketingInfo);
		return mav;
	}
	
	public ModelAndView ticketingCancel() {
		ModelAndView mav = new ModelAndView("myInfo/ticketConfirmation/cancelTicketResult");
		HttpSession session =  this.params.getSession();
		
		this.dbService.cancelTicket(this.params.getTicketNum(), (String) session.getAttribute("userID"));
		
		return mav;
	}
	
/***** 은정. 영화검색 
 * @throws Exception *****/	
	public ModelAndView searchMovieInfo() throws Exception {
		ModelAndView mav = this.getTemplate();
		List<MovieBasicInfo> movieList = this.dbService.searchMovieInfo("%" + this.params.getSearchWord() + "%");
		for(int i = 0, size = movieList.size(); i < size; i++) {
			MovieBasicInfo basicInfo = movieList.get(i);
			
			String[] castSplitArr = basicInfo.getMovieCast().split(",");
			
			List<MovieBasicInfoCast> castList = new ArrayList<MovieBasicInfoCast>();
			
			for(String cast : castSplitArr) {
				String[] castResult = cast.split("\\(");
				
				MovieBasicInfoCast castInfo = new MovieBasicInfoCast();
				castInfo.setCast(castResult[0]);
				try {
					castInfo.setAct("(" + castResult[1]);
				} catch(java.lang.ArrayIndexOutOfBoundsException e) {
					
				}
				
				castList.add(castInfo);
			}
			basicInfo.setMovieCastList(castList);
			basicInfo.setMovieAgeLimitText(basicInfo.getMovieAgeLimit() == 0 ? "전체관람가" : basicInfo.getMovieAgeLimit() + "세 관람가");
		}
		mav.addObject("movieList" , movieList);
		mav.addObject("searchWord",this.params.getSearchWord());
		return mav;
	}
	
/***** 은정. 매니저메뉴 *****/
	/** 전체 메뉴 **/
	public ModelAndView maganerMenu() throws Exception {
		ModelAndView mav = this.getTemplate();
		
		List<ManagerMemberInquiryDTO> members = this.dbService.getMemberInfo();
		
		int page = this.params.getMainPaing() == null ? 1 : this.params.getMainPaing();
		
		int userInfoCount = this.dbService.userInfoTotCount();
		
		Paging mainPaging = new Paging(userInfoCount, 10, page, 5);
		mainPaging.setBoardPaging();
		
		mav.addObject("userInfoPaging", this.dbService.userInfoTotList(mainPaging.getStartPageNum(), mainPaging.getEndPageNum()));
		mav.addObject("userInfoTotCount", mainPaging.getTotalPageCount());
		mav.addObject("infoMainBoardPage", mainPaging);
		mav.addObject("userInfoCount", userInfoCount);
		mav.addObject("checkPage", page);
		mav.addObject("memberList", members);
		return mav;
	} 
	/** 회원정보관리 : 페이징 처리 **/
	public ModelAndView managePaging() {
		
		ModelAndView mav = new ModelAndView("/managerMenu/managerMemberMenu");
		int page = this.params.getMainPaing() == null ? 1 : this.params.getMainPaing();
		int userInfoCount = this.dbService.userInfoTotCount();
		
		Paging mainPaging = new Paging(userInfoCount, 10, page, 5);
		mainPaging.setBoardPaging();
		
		mav.addObject("memberList", this.dbService.userInfoTotList(mainPaging.getStartPageNum(), mainPaging.getEndPageNum()));
		mav.addObject("userInfoTotCount", mainPaging.getTotalPageCount());
		mav.addObject("infoMainBoardPage", mainPaging);
		mav.addObject("userInfoCount", userInfoCount);
		mav.addObject("checkPage", page);
		return mav;
	}
	
	/** 회원정보관리 : 탈퇴시키기 **/
	public ModelAndView managerWithdrawal() {
		this.dbService.managerWithdrawalMember(this.params.getUserID());

		return this.managePaging();
	}
	/** 회원정보관리 : 탈퇴 복구 **/
	public ModelAndView managerRestore() {
		this.dbService.managerRestoreMember(this.params.getUserID());

		return this.managePaging();
	}
	/** 회원정보관리 : 정보검색 **/
	public ModelAndView searchMemberInfo() {
		ModelAndView mav = new ModelAndView("managerMenu/managerMemberMenu");
		String userID = "%" + (this.params.getUserID() == null ? "" : this.params.getUserID()) + "%";
		String userName = "%" + (this.params.getUserName() == null ? "" : this.params.getUserName()) + "%";
		String userBirth = "%" + (this.params.getUserBirth() == null ? "" : this.params.getUserBirth()) + "%";
		System.out.println(userName);
		List<ManagerMemberInquiryDTO> searchUserList = this.dbService.searchMemberInfo(userID, userName, userBirth);

		mav.addObject("memberList", searchUserList);
		
		return mav;
	}


	public ModelAndView managerReserveMenu() {
		ModelAndView mav = new ModelAndView("managerMenu/managerReserveMenu");
		
		ManagerUserReserveDTO reserveDto = new ManagerUserReserveDTO();
		reserveDto.setUserTicketNumber("%%");
		
		List<ManagerUserReserveDTO> reserveList = this.dbService.managerReservationInfo(reserveDto);
		List<String> movieList = this.dbService.managerGetMovieTitle();
		List<Integer> theaterList = this.dbService.managerGetTheaterCnt();
		int totPrice = 0;
		for(ManagerUserReserveDTO dto : reserveList) {
			totPrice += dto.getTicketPrice();
		}
		mav.addObject("reserveList", reserveList);
		mav.addObject("movieTitleList", movieList);
		mav.addObject("theaterList", theaterList);
		mav.addObject("totPrice", totPrice);
		return mav;
	}
	
	public ModelAndView searchReserveInfo() {
		ModelAndView mav = new ModelAndView("managerMenu/managerReserveMenu");
		ManagerUserReserveDTO dto = new ManagerUserReserveDTO();
		String ticketNum = this.params.getTicketNumPost() + this.params.getTicketNumBack();
		dto	.setUserTicketingDate(this.params.getSearchDate())
			.setMovieTitle(this.params.getManagerMovieTitle())
			.setTheater(this.params.getSearchTheater())
			.setUserTicketNumber("%" + (ticketNum == null ? "" : ticketNum) + "%");
		
		List<ManagerUserReserveDTO> reserveList = this.dbService.managerReservationInfo(dto);
		List<String> movieList = this.dbService.managerGetMovieTitle();
		List<Integer> theaterList = this.dbService.managerGetTheaterCnt();
		int totPrice = 0;
		for(ManagerUserReserveDTO reserveDto : reserveList) {
			totPrice += reserveDto.getTicketPrice();
		}
		mav.addObject("reserveList", reserveList);
		mav.addObject("movieTitleList", movieList);
		mav.addObject("theaterList", theaterList);
		mav.addObject("totPrice", totPrice);
		return mav;
	}
	
	public ModelAndView managetTicketCancel() {
		String[] ticketNumArr = this.params.getTicketNums().split(",");
		for(String ticketNum : ticketNumArr) {
			this.dbService.managerTicketCancel(ticketNum);			
		}
		return this.managerReserveMenu();
	}
	
	public ModelAndView managerScreeningPlannedModify() throws Exception {
		ModelAndView mav = this.getTemplate();
		List<String> movieList = this.dbService.managerGetMovieTitle();
		List<Integer> theaterList = this.dbService.managerGetTheaterCnt();
		mav.addObject("movieList", movieList);
		mav.addObject("theaterList", theaterList);
		return mav;
	}
	
	
/*******ID찾기(회원정보) 수진*******/	 //TODO 수진
	public ModelAndView searchId() throws Exception {
		
		boolean userInfoResult = false;
		String searchIdUserName = this.params.getSearchIdUserName();
		String searchIdUserBirth = this.params.getSearchIdUserBirth();
		String searchIdUserGender = this.params.getSearchIdUserGender();
		
		if(searchIdUserName == "" 
				&& searchIdUserBirth == "" 
				&& searchIdUserGender == "") {
			userInfoResult = false;
		}
		//생일 정보 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		Date todayDate = dateFormat.parse(this.params.getSearchIdUserBirth());
		
		double rightDays = (today.getTime() - todayDate.getTime()) / (24 * 60 * 60 * 1000);
		if(rightDays < 1) {
			userInfoResult = false;
		}
		//세션저장//
		List<Params> userSearchId = this.dbService.searchId(searchIdUserName, searchIdUserBirth, searchIdUserGender);
		Integer result = this.dbService.searchIdCount(searchIdUserName, searchIdUserBirth, searchIdUserGender);
		
		
		this.params.setDirectory("searchId");
		this.params.setPage("searchIdResult");
		this.params.setContentCSS("searchId/searchId");
		this.params.setContentjs("searchId/searchId");
		
		ModelAndView mav = this.getTemplate();
		mav.addObject("searchIdUserName", searchIdUserName);
		mav.addObject("userSearchId", userSearchId);
		mav.addObject("result", result);
		return mav;
	}
	
/*****수진. 아이디찾기(email)*****/
	public ModelAndView searchIDEmailResult() throws Exception {
		this.params.setDirectory("searchId");
		this.params.setPage("searchIdEmailResult");
		this.params.setContentCSS("searchId/searchId");
		this.params.setContentjs("searchId/searchIDEmailResult");
		
		String searchIdUserEmail = (String) this.params.getSession().getAttribute("searchIdUserEmail");
		//db보내기
		List<Params> userEmail = this.dbService.searchIDEmail(searchIdUserEmail);
		Integer userEmailIDCount = this.dbService.searchEmailCount(searchIdUserEmail);
		
		ModelAndView mav = this.getTemplate();
		mav.addObject("userEmail", userEmail);
		mav.addObject("userEmailIDCount", userEmailIDCount);
		return mav;
		
	}
	
	
/*****수진. 상영시간표List*****/
	public ModelAndView showtimes() throws Exception {
		this.params.setContentCSS("reservation/timeTable");
		this.params.setContentjs("reservation/timeTable");
		List<MovieShowTimesMap> movieTimeTable = this.dbService.showtimes();
		
		for(int i = 0, size=movieTimeTable.size(); i < size; i++) {
			MovieShowTimesMap showTime = movieTimeTable.get(i);
			List<MovieShowTitleDTO> showTitle = showTime.getMovieShowTitleDTO();
			
			for(int j = 0, JSize = showTitle.size(); j <JSize; j++) {
				MovieShowTitleDTO titleDTO = showTitle.get(j);

				List<MovieshowTableDTO> showTable = titleDTO.getMovieshowTableDTO();
				for(int k = 0, kSize = showTable.size(); k < kSize; k++) {

				}
			}
		}
		System.out.println("showTimes 옵니다.");
		ModelAndView mav = this.getTemplate();
		mav.addObject("movieTimeTable", movieTimeTable);
		return mav;
	}
	
/****수진 .고객센터*****/
	public ModelAndView serviceCenter() throws Exception {
		this.params.setContentCSS("service/serviceCenter");
		this.params.setContentjs("service/serviceCenter");
		HttpSession session = this.params.getSession();
		String LoginUserID = (String)session.getAttribute("userID");
		
		Integer userStates  =  (Integer) session.getAttribute("userStatus");
		ModelAndView mav = this.getTemplate();
		mav.addObject("loginUserId",LoginUserID);
		mav.addObject("userStatus", userStates);

		return mav;
	}
	//자주묻는페이지 가져오기
	public ModelAndView serviceCenterFreQuentlyBoard() throws Exception {
		ModelAndView mav = new ModelAndView("service/include/serviceFrequenty");
		
		HttpSession session = this.params.getSession();
		Integer userStatus     = (Integer) session.getAttribute("userStatus");
		
		int page = this.params.getPageboard() == null ? 1 : this.params.getPageboard();
		
		int totBoardList = this.dbService.serviceCentergetBoardCount();
		//List<CinemaFrequentlyBoardDTO> MovieFrequentlyBoardDTO = this.dbService.serviceCenter();

		Paging boardPaging = new Paging(totBoardList, 7, page, 4); //들어왔을때 page값 기본적으로 1 주기
		boardPaging.setBoardPaging();
		
		//mav.addObject("MovieFrequentlyBoardDTO", MovieFrequentlyBoardDTO);
		mav.addObject("boardPage", this.dbService.serviceCentergetBoard(boardPaging.getStartPageNum(), boardPaging.getEndPageNum()));
		mav.addObject("pageCount",boardPaging.getTotalPageCount());
		mav.addObject("pageGroup",boardPaging);
		mav.addObject("contentCSS", "service/service/ServiceCenterManagement");
		mav.addObject("userStatus", userStatus);
		mav.addObject("checkPage", page);
		mav.addObject("userStatus", userStatus);
		return mav;
	}
	
	/***검색 후 리스트 뿌려주기**/
	public ModelAndView getUserSearchList() throws Exception {
		ModelAndView mav = new ModelAndView("service/include/serviceFrequenty");
		CinemaFrequentlyBoardNumberDTO cinemaFrequentlyBoardNumberDTO = new CinemaFrequentlyBoardNumberDTO();
		
		HttpSession session = this.params.getSession();
		Integer userStatus     = (Integer) session.getAttribute("userStatus");
		
		this.params.setPageboard(1);
		int page = this.params.getPageboard();
		
		String serviceCenterSearchWord = this.params.getServiceCenterSearchWord();
		
		int countlist = this.dbService.userSearchList("%"+serviceCenterSearchWord+"%");
		
		List<CinemaFrequentlyBoardDTO> MovieFrequentlyBoardDTO = this.dbService.serviceCenter();
		Paging boardPaging = new Paging(countlist,7, page, 4);
		boardPaging.setBoardPaging();

		cinemaFrequentlyBoardNumberDTO.setSearchStartNum(boardPaging.getStartPageNum());
		cinemaFrequentlyBoardNumberDTO.setSearchEndNum(boardPaging.getEndPageNum());
		cinemaFrequentlyBoardNumberDTO.setServiceCenterSearchWord("%"+serviceCenterSearchWord+"%");
		
		List<CinemaFrequentlyBoardDTO> boardPage = this.dbService.getUserSearchList(cinemaFrequentlyBoardNumberDTO.getSearchStartNum(), cinemaFrequentlyBoardNumberDTO.getSearchEndNum(), cinemaFrequentlyBoardNumberDTO.getServiceCenterSearchWord());

		
		mav.addObject("MovieFrequentlyBoardDTO", MovieFrequentlyBoardDTO);
		mav.addObject("boardPage",boardPage);
		mav.addObject("pageCount", countlist);
		mav.addObject("serviceCenterSearchWord", serviceCenterSearchWord);
		mav.addObject("checkPage", page);
		mav.addObject("pageGroup",boardPaging);
		mav.addObject("search","Search");
		mav.addObject("userStatus", userStatus);
		return mav;
	}
	
	//검색 다음/이전 페이지 리스트
	public ModelAndView getUserSearhPage() throws Exception {
		ModelAndView mav = new ModelAndView("service/include/serviceFrequenty");
		HttpSession session = this.params.getSession();
		Integer userStatus     = (Integer) session.getAttribute("userStatus");
		
		String serviceCenterSearchWord = this.params.getServiceCenterSearchWord();
		
		int countlist = this.dbService.userSearchList("%"+serviceCenterSearchWord+"%");
		
		int page = this.params.getPageboard();
		List<CinemaFrequentlyBoardDTO> MovieFrequentlyBoardDTO = this.dbService.serviceCenter();
		
		Paging boardPaging = new Paging(countlist, 7, page, 4);
		boardPaging.setBoardPaging();
		
		List<CinemaFrequentlyBoardDTO> boardPage = this.dbService.getUserSearchList(boardPaging.getStartPageNum(),boardPaging.getEndPageNum(), "%"+serviceCenterSearchWord+"%");

		mav.addObject("MovieFrequentlyBoardDTO", MovieFrequentlyBoardDTO);
		mav.addObject("boardPage",boardPage);
		mav.addObject("pageCount", countlist);
		mav.addObject("serviceCenterSearchWord", serviceCenterSearchWord);
		mav.addObject("checkPage", page);
		mav.addObject("pageGroup",boardPaging);
		mav.addObject("search","Search");
		mav.addObject("userStatus", userStatus);
		
		return mav;
	}
		/*문의사항*/
	public ModelAndView questionBoard() throws Exception {
		ModelAndView mav = new ModelAndView("service/include/serviceQuestion");
		
		
		//A무조건 1페이지
		int totQuestionBoardCount = this.dbService.questionBoardCount();
		Paging questionBoardPaging = new Paging(totQuestionBoardCount,7, 1, 3);
		questionBoardPaging.setBoardPaging();
		
		HttpSession session = this.params.getSession();
		String LoginUserID = (String)session.getAttribute("userID");
		Integer userStatus     = (Integer) session.getAttribute("userStatus");
		
		mav.addObject("userStatus", userStatus);
		mav.addObject("questionBoardPage", this.dbService.questionBoard(questionBoardPaging.getStartPageNum(), questionBoardPaging.getEndPageNum()));
		mav.addObject("questionBoardPageCount", questionBoardPaging.getTotalPageCount());
		mav.addObject("questionBoardGroup", questionBoardPaging);
		mav.addObject("contentCSS", "service/service/ServiceCenterManagement");

		mav.addObject("loginUserId",LoginUserID);
		return mav;
		
	}
	
//문의 사항 게시판 전환
	public ModelAndView serviceCenterQuestionBoardChange() throws Exception {
		ModelAndView mav = new ModelAndView("service/include/serviceQuestion");
		
		int questionBoard = this.params.getQuestionBoard();
		
		int totQuestionBoardCount = this.dbService.questionBoardCount();
		System.out.println(totQuestionBoardCount + " : 총수");
		Paging questionBoardPaging = new Paging(totQuestionBoardCount, 7, questionBoard , 3);
		questionBoardPaging.setBoardPaging();
		
		HttpSession session = this.params.getSession();
		String LoginUserID = (String)session.getAttribute("userID");
		
		mav.addObject("questionBoardPage", this.dbService.questionBoard(questionBoardPaging.getStartPageNum(), questionBoardPaging.getEndPageNum()));
		mav.addObject("questionBoardPageCount", questionBoardPaging.getTotalPageCount());
		mav.addObject("questionBoardGroup", questionBoardPaging);
		mav.addObject("checkPage", questionBoard);
		mav.addObject("loginUserId",LoginUserID);
		
		return mav;
	}
	
/***수진 문의사항 글쓰기***/
	public ModelAndView questionBoardWriteForm() throws Exception {
		ModelAndView mav = new ModelAndView("service/include/AskWriteBoard");
		
		HttpSession session = this.params.getSession();
		String LoginUserID = (String)session.getAttribute("userID");
		Integer userStatus     = (Integer) session.getAttribute("userStatus");
		
		mav.addObject("userStatus", userStatus);
		mav.addObject("contentCSS", "service/serviceCenter");
		mav.addObject("contentjs", "service/service/questionBoard");
		mav.addObject("LoginUserID", LoginUserID);
		mav.addObject("userStatus", userStatus);
		
		return mav;
	}
/***수진 문의사항 글보기***/
	public ModelAndView questionViewBoard() throws Exception {
		Integer questionBoardNum = this.params.getQuestionBoardNum();
		CinemaQuestionBoardDTO questionBoardList = this.dbService.questionBoardList(questionBoardNum);
		
		HttpSession session = this.params.getSession();
		String LoginUserID = (String)session.getAttribute("userID");
		Integer userStatus     = (Integer) session.getAttribute("userStatus");
		String isUserRight = questionBoardList.getUser_Id();
		
		

		boolean isUserConfirm = false;
		if(isUserRight.equals(LoginUserID)) {
			isUserConfirm=true;
		}
		
		System.out.println(userStatus + " : 로그ㄷ인한사람 상태");
		System.out.println(questionBoardList.getIsPwd() + " : ??");
		System.out.println(LoginUserID + " ??"); //로그인 한사람.
		System.out.println(isUserRight + " : ??"); //게시글 쓴사람
		
		String result = "";
		if(questionBoardList.getIsPwd() == 1 && userStatus != 2) {
			result = "/service/include/reCheckPwdWriteForm";
//		} else if(LoginUserID == null)  { 
//			result = "/service/include/reCheckPwdWriteForm";
		} else {
			result = "/service/include/questionViewBoard";
		}
		
		ModelAndView mav = new ModelAndView(result);
	
		
		mav.addObject("userStatus", userStatus);
		mav.addObject("contentCSS", "service/service/questionBoard");
		mav.addObject("contentjs", "service/service/questionBoard");
		mav.addObject("questionBoardList", questionBoardList);
		mav.addObject("loginUserId", LoginUserID);
		mav.addObject("isUserRight", isUserRight);
		mav.addObject("isUserConfirm", isUserConfirm);
		return mav;
	}
	
///*******수진. 문의사항 글등록 *******/
	
	public ModelAndView InsertAskWriteBoard() throws Exception {

		String title         = this.params.getInsertTitle();
		String content       = this.params.getInsertTextArea();
		Integer writePwd     = this.params.getInsertboardPWd() == null ? 0 : this.params.getInsertboardPWd();
		int isPwd            = this.params.isInsertPwdcheck() == true ? 1 : 0;
		
		HttpSession session = this.params.getSession();
		String user_Id = (String)session.getAttribute("userID");
 

		CinemaQuestionBoardDTO cinemaQuestionBoardDTO = new CinemaQuestionBoardDTO();

				cinemaQuestionBoardDTO.setTitle(title);
				cinemaQuestionBoardDTO.setContent(content);
				cinemaQuestionBoardDTO.setUser_Id(user_Id);
				cinemaQuestionBoardDTO.setWritePwd(writePwd);
				cinemaQuestionBoardDTO.setIsPwd(isPwd);
			
		this.dbService.InsertAskWriteBoard(cinemaQuestionBoardDTO);
		ModelAndView mav = this.questionBoard();

		//mav.addObject("isResult", isResult);
		mav.addObject("loginUserId",user_Id);
		return mav;
		
	}
	
	//수정폼으로 가기
	public ModelAndView updateWriteForm() throws Exception {
		Integer questionBoardNum = this.params.getQuestionBoardNum();
		CinemaQuestionBoardDTO questionBoardList = this.dbService.questionBoardList(questionBoardNum);
		
		ModelAndView mav = new ModelAndView("service/include/serviceUpdateWriteForm");
		//update 쿼리
		mav.addObject("contentCSS", "service/service/serviceCenter");
		mav.addObject("contentjs", "service/service/serviceCenter");
		mav.addObject("questionBoardList", questionBoardList);
		return mav;
	}
	
	//수정 완전 완료
	public ModelAndView completeUPdateWriteBoard() throws Exception {
		
		String no            = this.params.getUpdateQuestionBoardNum();
		String title         = this.params.getInsertTitle();
		String content       = this.params.getInsertTextArea();

		int writePwd         = this.params.getInsertboardPWd() == null ? 0 : this.params.getInsertboardPWd();
		int isPwd            = this.params.isInsertPwdcheck() == true ? 1 : 0;
		

		//int questionBoard = this.params.getQuestionBoard();
		int totQuestionBoardCount = this.dbService.questionBoardCount();
		Paging questionBoardPaging = new Paging(totQuestionBoardCount,7, 1, 4);
		questionBoardPaging.setBoardPaging();
		
		HttpSession session = this.params.getSession();
		String LoginUserID = (String)session.getAttribute("userID");
		
		//System.out.println(user_Id);
		//System.out.println(write_date);

		CinemaQuestionBoardDTO cinemaQuestionBoardDTO = new CinemaQuestionBoardDTO();

			cinemaQuestionBoardDTO.setTitle(title);
			cinemaQuestionBoardDTO.setContent(content);
			cinemaQuestionBoardDTO.setWritePwd(writePwd);
			cinemaQuestionBoardDTO.setIsPwd(isPwd);
			cinemaQuestionBoardDTO.setNo(no);
			//cinemaQuestionBoardDTO.setWrite_date(write_date);
			//cinemaQuestionBoardDTO.setUser_Id(user_Id);
		this.dbService.completeUPdateWriteBoard(title, content, writePwd, isPwd, no);
		ModelAndView mav = this.questionBoard();

		mav.addObject("loginUserId",LoginUserID);
	
	
		return mav;
	}
	
	//게시글 문의사항 삭제 --> 보류시키기
	public ModelAndView completeDeleteQuestionBoard() throws Exception {

		HttpSession session = this.params.getSession();
		String user_Id = (String)session.getAttribute("userID");
		
		String completeDeleteQuestionBoardNum = this.params.getUpdateQuestionBoardNum();
		
		this.dbService.completeDeleteQuestionBoard(completeDeleteQuestionBoardNum);
		this.dbService.completeDeleteReplyQuestionBoard(completeDeleteQuestionBoardNum);
		
		ModelAndView mav = this.questionBoard();
		
		mav.addObject("loginUserId",user_Id);
		return mav;
	}

	//비밀글확인
	public ModelAndView reCheckPwdWriteForm() throws Exception {
		ModelAndView mav = new ModelAndView("service/include/reCheckPwdWriteForm");
		
		this.params.setDirectory("service");
		this.params.setPage("service/include/reCheckPwdWriteForm");
		mav.addObject("contentCSS", "service/service/serviceCenter");
		mav.addObject("contentjs", "service/service/serviceCenter");
		return mav;
	}
	
	public ModelAndView insertPwdCheck() throws Exception {
		ModelAndView mav = new ModelAndView("/service/include/confirmBoardCheck");
		Integer questionBoardNum = this.params.getQuestionBoardNum();
		CinemaQuestionBoardDTO questionBoardList = this.dbService.questionBoardList(questionBoardNum);
		
		mav.addObject("questionBoardList", questionBoardList);
		return mav;
	}
/*******수진<관리자> .고객센터 자주묻는게시판 폼 열기**********/
	public ModelAndView managementServiceCenterWriteForm() throws Exception {
		ModelAndView mav = new ModelAndView("/service/serviceCenterManager/serviceCenterManagement");
		
		mav.addObject("contentCSS", "service/service/questionBoard");
		mav.addObject("contentjs", "service/service/questionBoard");
		return mav;
	}
	
	//관리자 글등록
	public ModelAndView managementWriteBoard() throws Exception {
		String question = this.params.getQuestion();
		String asked    = this.params.getAsked();
		
		this.dbService.managementWriteBoard(question, asked);
		ModelAndView mav = this.serviceCenterFreQuentlyBoard();
		
		return mav;
	}
	
	//관리자 수정폼으로 가기
	public ModelAndView managementUpdateBoard() throws Exception {
		
		ModelAndView mav = new ModelAndView("/service/serviceCenterManager/serviceCenterUpdateForm");
		//넘ㅂ 받앙서 그 넘버에 대한거 가져오기
		String no = this.params.getNo();
		System.out.println("관리자가 선택한 번호 : " + no);
		
		CinemaFrequentlyBoardDTO cinemaFrequentlyBoardDTO = this.dbService.managementFrequentlyBoardCount(no);
		
		mav.addObject("cinemaFrequentlyBoardDTO", cinemaFrequentlyBoardDTO);
		return mav;
	}
	//자주묻는페이지 수정 완료
	public ModelAndView managementUpdateBoardComplete() throws Exception {
		
		String question = this.params.getQuestion();
		String asked    = this.params.getAsked();
		String no       = this.params.getNo();
		System.out.println("관리자가 수정한 제목 : "  + question);
		System.out.println("관리자가 수정한 내용: "   + asked);
		System.out.println("관리가자 수정할 번호 : "  + no);

		CinemaFrequentlyBoardDTO completeBoardForm = new CinemaFrequentlyBoardDTO();
			completeBoardForm.setQUESTION(question);
			completeBoardForm.setASKED(asked);
			completeBoardForm.setNO(no);
		this.dbService.managementUpdateFormComplete(question, asked, no);
		ModelAndView mav = this.serviceCenterFreQuentlyBoard();
		return mav;
	}
	
	//관리자 자주묻는 질문 삭제하기
	public ModelAndView managementDeleteBoardComplete() throws Exception {
		
		String no = this.params.getNo();
		System.out.println();
		System.out.println("관리자가 삭제할 번호 : " + no);
		this.dbService.managementDeleteFormComplete(no);
		ModelAndView mav = this.serviceCenterFreQuentlyBoard();
		return mav;
	}

	//문의사항 답글폼으로가기
	public ModelAndView managementReplyBoardForm() throws Exception {
			ModelAndView mav = new ModelAndView("/service/serviceCenterManager/serviceCenterReplyWriteForm");
			String userQuestionTitle = this.params.getUserQuestionTitle();
			Integer questionBoardNum = this.params.getQuestionBoardNum();
			System.out.println(questionBoardNum +" : 사용자의 글번호");
			System.out.println(userQuestionTitle + " : 사용자가 쓴 제목 가져오기");
			
			CinemaQuestionBoardDTO CinemaQuestionBoardDTO = this.dbService.questionBoardList(questionBoardNum);
			String no 		  = CinemaQuestionBoardDTO.getNo();
			String title 	  = CinemaQuestionBoardDTO.getTitle();
			String content    = CinemaQuestionBoardDTO.getContent();
			String user_id    = CinemaQuestionBoardDTO.getUser_Id();
			int writepwd      = CinemaQuestionBoardDTO.getWritePwd();
			int setPwd 		  = CinemaQuestionBoardDTO.getIsPwd();
			String replyNo    = CinemaQuestionBoardDTO.getReplyNo();
			String replyStep  = CinemaQuestionBoardDTO.getReplyStep();
			String replyDepth = CinemaQuestionBoardDTO.getReplyDepth();
//			
			System.out.println(setPwd + " :비밀글이냐");
//			
			mav.addObject("no", no);
			mav.addObject("title", title);
			mav.addObject("content", content);
			mav.addObject("user_id", user_id);
			mav.addObject("writepwd", writepwd);
			mav.addObject("setPwd", setPwd);
			mav.addObject("replyNo", replyNo);
			mav.addObject("replyStep", replyStep);
			mav.addObject("replyDepth", replyDepth);
			return mav;
	}
	
	public ModelAndView managementReplyBoardFormComplete() throws Exception {
		
		Integer writePwd  = this.params.getReplyPwd() == null ? 0 : this.params.getReplyPwd();
		int isPwd     = this.params.isReplytPwdcheck() == true ? 1 : 0;
		
		System.out.println(writePwd + ": qlalfqjsgh");
		HttpSession session = this.params.getSession();
		String user_id = (String)session.getAttribute("userID");
		
		System.out.println(user_id + " : 누구");
		String title = this.params.getReplyTitle();
		String content = this.params.getReplyContent();
		String replyNo = this.params.getReplyNo();
		String replyDepth = this.params.getReplyDepth();
		String replyStep = this.params.getReplyStep();

		
		this.dbService.insertReplyBoard(title, content, user_id, writePwd, isPwd, replyNo, replyStep, replyDepth);
		this.dbService.updateDepth(replyNo, replyStep);
		
		ModelAndView mav = this.questionBoard();
		return mav;
	}

	
	public ModelAndView managerStillCutModify() {
		System.out.println("managerStillCutModify : 여기");
		return new ModelAndView("movie/include/managerModifyStilcut");
	}
	
	/*******연종. PWD찾기 SHIN*******/ //TODO 연종
	public ModelAndView searchPwdID() throws Exception {
		ModelAndView mav = this.getTemplate();
		String searchPwdUserID = this.params.getSearchPwdUserID();
		Integer result = this.dbService.searchPWD(searchPwdUserID);
		
		HttpSession session =  this.params.getSession();	
		session.setAttribute("userId", searchPwdUserID);
		
		mav.addObject("result", result);
		return mav;
	}
	public ModelAndView searchPwdsendEmail() throws Exception {
		ModelAndView mav = this.getTemplate();
		Random rand = new Random();
		int randNum = rand.nextInt(900000) + 100000;
		System.out.println("mainservice 인증번호> : " + randNum);
		
		HttpSession session = this.params.getSession();
		String searchPwdUserID = (String)session.getAttribute("userId");
		String userEmail = this.dbService.searchEmail(searchPwdUserID);
		HttpSession sessionRandNum = this.params.getSession();
		sessionRandNum.setAttribute("randNum", randNum);
		new SendEmail(String.valueOf(randNum), userEmail); 
		return mav;
	}
	public ModelAndView checkPwdConfirmNum() throws Exception {
		this.params.setDirectory("searchPwd");
		this.params.setPage("searchPwdChangePwd");
		this.params.setContentCSS("searchPwd/searchPwd");
		this.params.setContentjs("searchPwd/searchPwd");
		return this.getTemplate();	
	}
	public ModelAndView updatePWD() throws Exception {
		HttpSession session = this.params.getSession();
		String searchPwdUserID = (String)session.getAttribute("userId");
		String searchPwdNewPwd = this.params.getSearchPwdNewPwd();
		this.dbService.updateNewPwd(searchPwdUserID, searchPwdNewPwd);
		this.params.setDirectory("searchPwd");
		this.params.setPage("searchPwdChangeCompleted");
		return this.logout();
	}
//------------------------------------------------------------------------
	
/*******연종. MyINFO SHIN*******/	
	public ModelAndView viewMyInfo() throws Exception {
		this.params.setDirectory("myInfo");
		this.params.setPage("myInfo");
		this.params.setContentCSS("myInfo/myInfo");
		this.params.setContentjs("myInfo/myInfo");
		ModelAndView mav = this.getTemplate();
		HttpSession session = this.params.getSession();
		String myInfoID = (String)session.getAttribute("userID");
		UserPersonalInfoDTO myInfoDTO = this.dbService.selectMyInfo(myInfoID);
		mav.addObject("myInfoList", myInfoDTO);
		mav.addObject("ticketing", this.params.getParams());
		return mav;
	}
	public ModelAndView myInfoChagePwdResult() throws Exception {
		this.params.setDirectory("myInfo");
		this.params.setPage("myInfoChagePwdResult");
		this.params.setContentCSS("myInfo/changePwd");
		this.params.setContentjs("myInfo/changePwd");
		return this.getTemplate();	
	}
	public ModelAndView myInfoChangeEmailResult() throws Exception {
		this.params.setDirectory("myInfo");
		this.params.setPage("myInfoChangeEmailResult");
		this.params.setContentCSS("myInfo/changeEmail");
		this.params.setContentjs("myInfo/changeEmail");
		return this.logout();
	}
	public ModelAndView myInfoChangePwdResult()throws Exception{
		this.params.setDirectory("myInfo");
		this.params.setPage("myInfoChangePwdResult");
		this.params.setContentCSS("myInfo/changePwd");
		this.params.setContentjs("myInfo/changePwd");
		return this.logout();	
	}	
	
//------------------------------------------------------------------------
/*******연종. MOVIE CURRENT FIRM 현재상영작*******/	
	public ModelAndView currentFilm() throws Exception{
		ModelAndView mav = this.getTemplate();
		List<MovieCurrentFilmDTO> currentFilmDTO = this.dbService.getCurrentFilmDTO();
		Integer filmNum = currentFilmDTO.size();
		mav.addObject("directory", "movie");
		mav.addObject("page", "currentFilm");
		mav.addObject("contentCSS", "movie/currentFilm");
		mav.addObject("contentjs", "movie/currentFilm");
		mav.addObject("CurrentFilmDTO", currentFilmDTO);
		mav.addObject("filmNum", filmNum);
		return mav;
	}
	public ModelAndView sortScore() throws Exception{
		ModelAndView mav = new ModelAndView("movie/include/currentFilmSort");
		List<MovieCurrentFilmDTO> currentFilmDTO = this.dbService.sortScore();
		Integer filmNum = currentFilmDTO.size();
		mav.addObject("directory", "movie");
		mav.addObject("page", "currentFilmSort");
		mav.addObject("contentCSS", "movie/currentFilm");
		mav.addObject("contentjs", "movie/currentFilm");
		mav.addObject("CurrentFilmDTO", currentFilmDTO);
		mav.addObject("filmNum", filmNum);
		return mav;
	}
	public ModelAndView sortTicketing() throws Exception{
		ModelAndView mav = new ModelAndView("movie/include/currentFilmSort");
		List<MovieCurrentFilmDTO> currentFilmDTO = this.dbService.sortTicketing();
		Integer filmNum = currentFilmDTO.size();
		mav.addObject("directory", "movie");
		mav.addObject("page", "currentFilmSort");
		mav.addObject("contentCSS", "movie/currentFilm");
		mav.addObject("contentjs", "movie/currentFilm");
		mav.addObject("CurrentFilmDTO", currentFilmDTO);
		mav.addObject("filmNum", filmNum);
		return mav;
	}
/*******연종. MOVIE PLANNED FIRM 상영예정작*******/		
	public ModelAndView screeningsPlanned() throws Exception{
		ModelAndView mav = this.getTemplate();
		List<MovieScreeningsPlannedDTO> screeningsPlannedDTO = this.dbService.getPlannedFilmDTO();
		Integer filmNum = screeningsPlannedDTO.size();
		
		mav.addObject("directory", "movie");
		mav.addObject("page", "screeningsPlanned");
		mav.addObject("contentCSS", "movie/screeningsPlanned");
		mav.addObject("contentjs", "movie/screeningsPlanned");
		mav.addObject("ScreeningsPlannedDTO", screeningsPlannedDTO);
		mav.addObject("filmNum", filmNum);
		return mav;
	}
//------------------------------------------------------------------------
/*******연종. THEATER wayToCome.jsp 오시는길*******/	
	public ModelAndView wayToCome() throws Exception{
		ModelAndView mav = this.getTemplate();
		mav.addObject("directory", "theater");
		mav.addObject("page", "wayToCome");
		mav.addObject("contentCSS", "theater/wayToCome");
		mav.addObject("contentjs", "theater/wayToCome");
		return mav;
	}
/*******연종. THEATER introduce.jsp 극장소개*******/	
	public ModelAndView introduce() throws Exception{
		ModelAndView mav = this.getTemplate();
		List<CinemaIntroduceDTO> cinemaIntroduceDTO = this.dbService.getCinemaIntroImg();
		int imgCount =  cinemaIntroduceDTO.size();
		mav.addObject("imgSrc", cinemaIntroduceDTO);
		mav.addObject("imgCount", imgCount);
		mav.addObject("directory", "theater");
		mav.addObject("page", "introduce");
		mav.addObject("contentCSS", "theater/introduce");
		return mav;
	}
//-----------------------------------------------------------------------
/*******연종. SERVICE notice.jsp 공지사항*******/
	//1. 처음 공지사항 을눌렀을때  리스트를 뿌려줌 
	public ModelAndView notice() throws Exception {
		ModelAndView mav = this.getTemplate();
		
		int totalList = this.dbService.getNoticeBoardCount();
		this.params.setNoticePage(1);
		int noticePage = this.params.getNoticePage();
		
		Paging2 paging = new Paging2();
		paging.makePaging(totalList, noticePage, 10, 10);
		System.out.println("notice()");
		List<CinemaNoticeBoardDTO> noticeDTO = this.dbService.getCinemaNoticeBoardDTO(paging.getStartPageList(), paging.getEndPageList());
		mav.addObject("noticeDTO", noticeDTO);
		mav.addObject("paging", paging);
		mav.addObject("directory", "service");
		mav.addObject("page", "notice/notice");
		mav.addObject("contentCSS", "service/notice/notice");
		mav.addObject("contentjs", "service/notice/notice");
		return mav;
	}
	
	//2. 비동기로 사용자가 클릭한 page 를 가지고 계산한 paging 처리
	public ModelAndView noticeBoard() throws Exception {
		ModelAndView mav = new ModelAndView("service/notice/noticeBoard");
		int totalList = this.dbService.getNoticeBoardCount();
		int noticePage = this.params.getNoticePage();
		Paging2 paging = new Paging2();
		paging.makePaging(totalList, noticePage, 10, 10);
		List<CinemaNoticeBoardDTO> noticeDTO = this.dbService.getCinemaNoticeBoardDTO(paging.getStartPageList(), paging.getEndPageList());
		
		mav.addObject("noticePage", noticePage);
		mav.addObject("noticeDTO", noticeDTO);
		mav.addObject("paging", paging);
		mav.addObject("directory", "service");
		mav.addObject("page", "notice/notice");
		mav.addObject("contentCSS", "service/notice/notice");
		mav.addObject("contentjs", "service/notice/notice");
		return mav;         
	}
	//3. noticeBoard에서 글제목을 클릭하면 실행
	public ModelAndView noticeBoardView() throws Exception {
		ModelAndView mav = this.getTemplate();
		Integer noticePage = this.params.getNoticePage();
		Integer noticeNo = this.params.getNoticeNo();
		
		CinemaNoticeBoardDTO noticeDTO = this.dbService.getNoticeBoardContent(noticeNo);
		String content = noticeDTO.getContent();
		String title = noticeDTO.getTitle();
		String writeDate = noticeDTO.getWriteDate();
		
		mav.addObject("title", title);
		mav.addObject("content", content);
		mav.addObject("writeDate", writeDate);
		mav.addObject("directory", "service");
		mav.addObject("page", "notice/noticeBoardView");
		mav.addObject("contentCSS", "service/notice/noticeBoard");
		mav.addObject("contentjs", "service/notice/notice");
		mav.addObject("noticePage", noticePage);
		mav.addObject("noticeNo", noticeNo);
		return mav;
	}
	//4. 목록보기를 누르면 사용자가 마지막으로 본 page 리스트가 불림
	public ModelAndView locationNoticeBoard() throws Exception {
		ModelAndView mav = this.getTemplate();
		int totalList = this.dbService.getNoticeBoardCount();
		int noticePage = this.params.getNoticePage();
		
		Paging2 paging = new Paging2();
		paging.makePaging(totalList, noticePage, 10, 10);
		List<CinemaNoticeBoardDTO> noticeDTO = this.dbService.getCinemaNoticeBoardDTO(paging.getStartPageList(), paging.getEndPageList());
		
		mav.addObject("noticeDTO", noticeDTO);
		mav.addObject("paging", paging);
		mav.addObject("directory", "service");
		mav.addObject("page", "notice/notice");
		mav.addObject("contentCSS", "service/notice/notice");
		mav.addObject("contentjs", "service/notice/notice");
		return mav;
	}
	//5. 검색후 리스트 뿌려짐 항상1page
	public ModelAndView searchNoticeBoard() throws Exception {
		ModelAndView mav = new ModelAndView("service/notice/noticeBoard");
		CinemaNoticeSearchBoardDTO searchBoardDTO = new CinemaNoticeSearchBoardDTO();
		//사용자가 검색한 단어 저장 
		String searchWord = this.params.getNoticeSearachWord();
		//페이징처리를 위한 과정 
		this.params.setNoticePage(1);
		int noticePage = this.params.getNoticePage();
		int totalList = this.dbService.searchBoardCount("%"+searchWord+"%");
		
		Paging2 paging = new Paging2();
		paging.makePaging(totalList, noticePage, 10, 10);
		
		searchBoardDTO.setBlockStartNum(paging.getStartPageList());
		searchBoardDTO.setBlockEndNum(paging.getEndPageList());
		searchBoardDTO.setSearchWord("%"+searchWord+"%");
		
		List<CinemaNoticeBoardDTO> noticeBoardDTO = this.dbService.searchBoard(searchBoardDTO.getBlockStartNum(), searchBoardDTO.getBlockEndNum(),searchBoardDTO.getSearchWord());
		
		mav.addObject("totalList", totalList);
		mav.addObject("noticeDTO", noticeBoardDTO);
		mav.addObject("paging", paging);
		mav.addObject("search", "Search");
		mav.addObject("directory", "service");
		mav.addObject("page", "notice/notice");
		mav.addObject("contentCSS", "service/notice/notice");
		mav.addObject("contentjs", "service/notice/notice");
		
		return mav;
	}
	
	public ModelAndView searchNoticeBoardPage() throws Exception {
		ModelAndView mav = new ModelAndView("service/notice/noticeBoard");
		//사용자가 검색한 단어 저장 
		String searchWord = this.params.getNoticeSearachWord();
		//페이징처리를 위한 과정 
		int noticePage = this.params.getNoticePage();
		int totalList = this.dbService.searchBoardCount("%"+searchWord+"%");
		
		Paging2 paging = new Paging2();
		paging.makePaging(totalList, noticePage, 10, 10);
		
		List<CinemaNoticeBoardDTO> noticeBoardDTO = this.dbService.searchBoard(paging.getStartPageList(), paging.getEndPageList(), "%"+searchWord+"%");
		
		mav.addObject("totalList", totalList);
		mav.addObject("noticeDTO", noticeBoardDTO);
		mav.addObject("paging", paging);
		mav.addObject("search", "Search");
		mav.addObject("directory", "service");
		mav.addObject("page", "notice/notice");
		mav.addObject("contentCSS", "service/notice/notice");
		mav.addObject("contentjs", "service/notice/searchNotice");
		return mav;
	}
	
	public ModelAndView managerInsertNoticeForm() throws Exception {
		ModelAndView mav = this.getTemplate();
		mav.addObject("directory", "service/notice/manager");
		mav.addObject("page", "insertNotice");
		mav.addObject("contentCSS", "service/notice/managerNotice");
		mav.addObject("contentjs", "service/notice/managerNotice");
		return mav;
	}
	
	public ModelAndView managerUpdateNoticeForm() throws Exception {
		ModelAndView mav = this.getTemplate();
		Integer noticePage = this.params.getManagerNoticePage();
		Integer noticeNo = this.params.getManagerNoticeNo();
		
		CinemaNoticeBoardDTO noticeDTO = this.dbService.getNoticeBoardContent(noticeNo);
		String content = noticeDTO.getContent();
		String title = noticeDTO.getTitle();
		String writeDate = noticeDTO.getWriteDate();
		Integer important = noticeDTO.getImportant();
		
		mav.addObject("directory", "service/notice/manager");
		mav.addObject("page", "updateNotice");
		mav.addObject("contentCSS", "service/notice/managerNotice");
		mav.addObject("contentjs", "service/notice/managerModify");
		mav.addObject("title", title);
		mav.addObject("content", content);
		mav.addObject("writeDate", writeDate);
		mav.addObject("noticePage", noticePage);
		mav.addObject("noticeNo", noticeNo);
		mav.addObject("important", important);
		return mav;
	}
	
/*************SHIN _ 영화상세정보***********/ //TODO
	public ModelAndView movieDetailInfo() throws Exception {
		ModelAndView mav = this.getTemplate();
		
		String movieTitle = this.params.getMovieInfoTitle();
		MovieBasicInfo movieBasicInfo = this.dbService.getMovieBasicInfo(movieTitle);
		boolean reviewResult = true;
		
		String movieReleadeDate = movieBasicInfo.getMovieReleaseDate();
		Date currentTime = new Date ();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		boolean countResult = true;
		
		try {
			date = format.parse(movieReleadeDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int compare = date.compareTo(currentTime);
		if(compare >= 0){
			reviewResult = false;
		}
		
		List<MovieStillCut> movieStillCutDTO = this.dbService.getStillcut(movieTitle);
		if(movieStillCutDTO.size() == 0) {
			countResult = false;
		}
		
		mav.addObject("movieStillCutDTO", movieStillCutDTO);
		mav.addObject("movieStillCutCount", countResult);
		mav.addObject("reviewResult", reviewResult);
		mav.addObject("movieBasicInfo", movieBasicInfo);
		mav.addObject("directory", "movie");
		mav.addObject("page", "movieBasicInfo");
		mav.addObject("contentCSS", "movie/movieBasicInfo");
		mav.addObject("contentjs", "movie/movieBasicInfo");
		return mav;
	}
	
	public ModelAndView getReviewBoard() throws Exception {
		ModelAndView mav = new ModelAndView("movie/include/reviewBoard");
		MovieReviewBoardDTO movieReviewBoardDTO = new MovieReviewBoardDTO();
		String movieTitle = this.params.getMovieInfoTitle();
		HttpSession session = this.params.getSession();
		String user = (String)session.getAttribute("userID");
		
		List<MovieReviewBoard> reviewBoardDTO =  this.dbService.getReviewBoard(movieTitle);
		this.params.setMovieInfoReviewPage(1);
		int reviewPage = this.params.getMovieInfoReviewPage();
		Paging2 paging = new Paging2();
		paging.makePaging(reviewBoardDTO.size(), reviewPage, 5, 5);
		
		movieReviewBoardDTO.setBlockStartNum(paging.getStartPageList());
		movieReviewBoardDTO.setBlockEndNum(paging.getEndPageList());
		movieReviewBoardDTO.setMovieTitle("%"+movieTitle+"%");
		
		List<MovieReviewBoard> reviewBoardListDTO = this.dbService.getReviewBoardList(movieReviewBoardDTO.getBlockStartNum(), movieReviewBoardDTO.getBlockEndNum(), movieReviewBoardDTO.getMovieTitle());
		
		mav.addObject("reviewBoardListDTO", reviewBoardListDTO);
		mav.addObject("reviewBoardCount", reviewBoardDTO.size());
		mav.addObject("paging", paging);
		mav.addObject("userCheck", user);
		return mav;
	}
	
	public ModelAndView getReviewBoardPage() throws Exception {
		ModelAndView mav = new ModelAndView("movie/include/reviewBoard");
		String movieTitle = this.params.getMovieInfoTitle();
		HttpSession session = this.params.getSession();
		String user = (String)session.getAttribute("userID");
		
		List<MovieReviewBoard> reviewBoardDTO =  this.dbService.getReviewBoard(movieTitle);
		
		int reviewPage = this.params.getMovieInfoReviewPage();
		Paging2 paging = new Paging2();
		paging.makePaging(reviewBoardDTO.size(), reviewPage, 5, 5);
		List<MovieReviewBoard> reviewBoardListDTO = this.dbService.getReviewBoardList(paging.getStartPageList(), paging.getEndPageList(), "%"+movieTitle+"%");
		
		mav.addObject("reviewBoardListDTO", reviewBoardListDTO);
		mav.addObject("reviewBoardCount", reviewBoardDTO.size());
		mav.addObject("paging", paging);
		mav.addObject("userCheck", user);
		return mav;
	}
	
	public ModelAndView insertReviewBoard() throws Exception {
		ModelAndView mav = new ModelAndView("movie/include/reviewBoard");
		MovieReviewBoard movieReviewBoard = new MovieReviewBoard();
		
		HttpSession session = this.params.getSession();
		String userID = (String)session.getAttribute("userID");
		Integer reviewEvaluate = this.params.getReviewScore();
		String reviewContents = this.params.getReviewContents();
		String movieTitle = this.params.getMovieInfoTitle();
		
		movieReviewBoard.setReviewContents(reviewContents);
		movieReviewBoard.setReviewWriter(userID);
		movieReviewBoard.setMovieTitle(movieTitle);
		movieReviewBoard.setReviewEvaluate(reviewEvaluate);
		
		this.dbService.insertReview(movieReviewBoard.getReviewEvaluate(),
				movieReviewBoard.getReviewContents(),
				movieReviewBoard.getReviewWriter(),
				movieReviewBoard.getMovieTitle());
		
		List<MovieReviewBoard> reviewBoardDTO =  this.dbService.getReviewBoard(movieTitle);
		
		this.params.setMovieInfoReviewPage(1);
		int reviewPage = this.params.getMovieInfoReviewPage();
		Paging2 paging = new Paging2();
		paging.makePaging(reviewBoardDTO.size(), reviewPage, 5, 5);
		
		List<MovieReviewBoard> reviewBoardListDTO = this.dbService.getReviewBoardList(paging.getStartPageList(), paging.getEndPageList(), "%"+movieTitle+"%");
		
		mav.addObject("reviewBoardListDTO", reviewBoardListDTO);
		mav.addObject("reviewBoardCount", reviewBoardDTO.size());
		mav.addObject("paging", paging);
		mav.addObject("userCheck", userID);
		return mav;
	}
	
	
	public ModelAndView deleteReviewBoard() throws Exception {
		ModelAndView mav = new ModelAndView("movie/include/reviewBoard");
		HttpSession session = this.params.getSession();
		String userID = (String)session.getAttribute("userID");
		
		String movieTitle = this.params.getMovieInfoTitle();
		Integer reviewNo = this.params.getReviewNo();
		
		List<MovieReviewBoard> reviewBoardDTO =  this.dbService.getReviewBoard(movieTitle);
		
		Paging2 paging = new Paging2();
		paging.makePaging(reviewBoardDTO.size(), 1, 5, 5);
		
		if(userID.equals(this.params.getDeleteReviewID())) {
			this.dbService.deleteReview(reviewNo);
		}
		if(userID.equals("AllnightMovies")) {
			this.dbService.deleteReview(reviewNo);
		}
		
		System.out.println("관리자  >> " + userID.equals("AllnightMovies"));
		List<MovieReviewBoard> reviewBoardListDTO = this.dbService.getReviewBoardList(paging.getStartPageList(), paging.getEndPageList(), movieTitle);
		mav.addObject("reviewBoardListDTO", reviewBoardListDTO);
		mav.addObject("reviewBoardCount", reviewBoardDTO.size());
		mav.addObject("paging", paging);
		mav.addObject("userCheck", userID);
		return mav;
	}

	public ModelAndView managerMovieModifyForm() throws Exception {
		ModelAndView mav = this.getTemplate();
		String movieTitle = this.params.getMovieInfoTitle();
		
		MovieBasicInfo movieBasicInfo = this.dbService.getMovieBasicInfo(movieTitle);
		
		boolean reviewResult = true;
		String movieReleadeDate = movieBasicInfo.getMovieReleaseDate();
		Date currentTime = new Date ();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		boolean countResult = true;
		
		try {
			date = format.parse(movieReleadeDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int compare = date.compareTo(currentTime);
		if(compare >= 0){
			reviewResult = false;
		}
		
		List<MovieStillCut> movieStillCutDTO = this.dbService.getStillcut(movieTitle);
		if(movieStillCutDTO.size() == 0) {
			countResult = false;
		}
		
		List<MovieReviewBoard> reviewBoardDTO =  this.dbService.getReviewBoard(movieTitle);
		
		int reviewPage = this.params.getMovieInfoReviewPage();
		Paging2 paging = new Paging2();
		paging.makePaging(reviewBoardDTO.size(), reviewPage, 5, 5);
		List<MovieReviewBoard> reviewBoardListDTO = this.dbService.getReviewBoardList(paging.getStartPageList(), paging.getEndPageList(), "%"+movieTitle+"%");
		
		mav.addObject("reviewBoardListDTO", reviewBoardListDTO);
		mav.addObject("reviewBoardCount", reviewBoardDTO.size());
		mav.addObject("paging", paging);
		
		mav.addObject("movieStillCutDTO", movieStillCutDTO);
		mav.addObject("movieStillCutCount", countResult);
		mav.addObject("reviewResult", reviewResult);
		mav.addObject("movieBasicInfo", movieBasicInfo);
		
		mav.addObject("directory", "movie/manager");
		mav.addObject("page", "managerMovieInfo");
		mav.addObject("contentCSS", "movie/managerMovieInfo");
		mav.addObject("contentjs", "movie/managerMovieInfo");
		return mav;
	}
	
	public ModelAndView managerMovieInsertForm() throws Exception {
		ModelAndView mav = this.getTemplate();
		mav.addObject("directory", "movie/manager");
		mav.addObject("page", "managerInsertMovie");
		mav.addObject("contentCSS", "movie/managerInsertMovie");
		mav.addObject("contentjs", "movie/managerInsertMovie");
		return mav;
	}
	
}
