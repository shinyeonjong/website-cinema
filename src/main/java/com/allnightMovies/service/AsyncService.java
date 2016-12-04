package com.allnightMovies.service;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.allnightMovies.di.AsyncAction;
import com.allnightMovies.model.data.AsyncResult;
import com.allnightMovies.model.data.cinemaInfo.CinemaNoticeBoardDTO;
import com.allnightMovies.model.data.cinemaInfo.CinemaQuestionBoardDTO;
import com.allnightMovies.model.data.movieInfo.ManagerScreeningPlannedUpdateDTO;
import com.allnightMovies.model.data.movieInfo.MovieBasicInfoDTO;
import com.allnightMovies.model.data.userInfo.UserPersonalInfoDTO;
import com.allnightMovies.model.data.userInfo.UserPersonalLoginInfoDTO;
import com.allnightMovies.model.params.Params;
import com.allnightMovies.utility.Paging;
import com.allnightMovies.utility.RegexCheck;
import com.allnightMovies.utility.SendEmail;


@Service
public class AsyncService implements AsyncAction {
	private Params params;
	@Autowired
	DBService dbService;
	
	@SuppressWarnings("rawtypes")
	@Override
	public AsyncResult asyncExcute(Params params) throws Throwable {
	   Method method = this.getClass().getDeclaredMethod(params.getMethod());
	   this.params = params;
	   return (AsyncResult) method.invoke(this);
	}
/*****은정. join success check*****/	
	public AsyncResult<String> joinSuccessCheck() throws Exception {
		String userID 	 = params.getUserIDCheck();
		String userPWD 	 = params.getUserPWD();
		String userRePWD = params.getUserRePWD();
		String userName  = params.getUserName();
		String userBirth = params.getUserBirth();
		
		AsyncResult<String> asyncResult = new AsyncResult<String>();
		
		boolean checkBool = true;
		
		// 중복되는 아이디인지 체크
		if(this.dbService.idCheck(userID) > 0) {
			System.out.println("중복되는 아이디인지 : " + (this.dbService.idCheck(userID) > 0));
			checkBool = false;
		}
		// 아이디 길이기 4~15자인지 체크
		if(userID.length() < 4 || userID.length() > 15) {
			System.out.println("아이디 길이기 4~15자 : " + (userID.length() < 4 || userID.length() > 15));
			checkBool = false;
		}
		
		// 아이디 regex 체크
		if(!RegexCheck.idRegexCheck(userID)) {
			System.out.println("아이디 regex : " + !RegexCheck.idRegexCheck(userID));
			checkBool = false;
		}
		// 비밀번호 regex 체크
		if(!RegexCheck.passwdRegexCheck(userPWD)) {
			System.out.println("비밀번호 regex : " + !RegexCheck.passwdRegexCheck(userPWD));
			checkBool = false;
		}
		// 비밀번호 8~15자 이내인지 체크
		if(userPWD.length() < 8 || userPWD.length() > 15) {
			System.out.println("비밀번호 8~15자 이내 : " + (userPWD.length() < 8 || userPWD.length() > 15));
			checkBool = false;
		}
		// pwd와 repwd 일치하는지 체크
		if(!userPWD.equals(userRePWD)) {
			System.out.println("pwd와 repwd 일치 : " + !userPWD.equals(userRePWD));
			checkBool = false;
		}
		// 이름 regex 체크
		if(userName == "") {
			checkBool = false;
		}
		if(!RegexCheck.nameRegecCheck(params.getUserName())) {
			System.out.println("이름 regex : " + !RegexCheck.nameRegecCheck(params.getUserName()));
			checkBool = false;
		}
		if(userBirth == "") {
			System.out.println("birth 가 공백");
			checkBool = false;
		}
		// 생일이 오늘날짜 이후인지 체크
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		Date inputDate = dateFormat.parse(params.getUserBirth());
		double diffDays = (today.getTime() - inputDate.getTime()) / (24 * 60 * 60 * 1000);
		if(diffDays < 1) {
			System.out.println("생일이 오늘날짜 이후 : " + (diffDays < 1) + " / diffDays : " + diffDays);
			checkBool = false;
		}
		// 이메일 regex 체크
		if(!RegexCheck.emailRegexCheck(params.getUserEmail())) {
			System.out.println("이메일 regex" + !RegexCheck.emailRegexCheck(params.getUserEmail()));
			checkBool = false;
		}
		// 인증을 받은 상태인지 체크
		checkBool = (boolean) params.getSession().getAttribute("isConfirm");
		
		String resultStr = null;
		
		if(checkBool) {
			UserPersonalInfoDTO personalDTO = new UserPersonalInfoDTO();
			personalDTO.setUserName(userName);
			personalDTO.setUserID(userID);
			personalDTO.setUserPWD(userPWD);
			personalDTO.setUserGender(this.params.getUserGender());
			personalDTO.setUserEmail(this.params.getUserEmail());
			personalDTO.setUserBirth(userBirth);
			this.dbService.insertJoinUserInfo(personalDTO);
			this.params.getSession().setAttribute("userID", this.params.getUserIDCheck());
			resultStr = "/movie/mainService/locationJoinSuccess";
		}
		asyncResult.setSuccess(checkBool);
		asyncResult.setData(resultStr);
		return asyncResult;
	}
	
	// 로그인
	public AsyncResult<String> login() throws Exception {
		UserPersonalLoginInfoDTO userLoginInfo = this.dbService.login(this.params);
		String result = null;
		boolean resultBool = true;
		System.out.println(this.params.getRequest().getRequestURL());
		if(userLoginInfo != null && userLoginInfo.getUserStates() != 0) {
			if(this.params.getUserPWD().equals(userLoginInfo.getUserPWD())) {
				HttpSession session = this.params.getSession();
				session.setAttribute("userID", userLoginInfo.getUserID());
				session.setAttribute("userStatus", userLoginInfo.getUserStates());
				if(session.getAttribute("requestURL") != null) {
					result = session.getAttribute("requestURL").toString();
					session.removeAttribute("requestURL");
					if(result.contains("async")) {
						result = "/";
					}
				} else {
					result = "/";
				}
			} else {
				result = "비밀번호가 일치하지 않습니다.";
				resultBool = false;
			}
		} else {
			result = "탈퇴하였거나 존재하지 않는 아이디입니다.";
			resultBool = false;
		}
		
		
		
		AsyncResult<String> async = new AsyncResult<String>();
		async.setData(result);
		async.setSuccess(resultBool);
		
		return async;
	}

/*****은정. TICKETING : Get Movie Poster *****/
   public AsyncResult<String> getMoviePoster() {
      String moviePoster = this.dbService.getMoviePoster(this.params.getMovieTitle());
      AsyncResult<String> async = new AsyncResult<String>();
      async.setData(moviePoster);
      return async;
   }
   
   /*****은정. TICKETING : Manager Screening Planned Update  *****/
   public AsyncResult<String> screeningPlannedUpdate() {
	   String[] theaters = this.params.getTheaters().split("#");
	   String[] movieTitles = this.params.getMovieTitles().split("#");
	   String[] dateTimes = this.params.getDateTimes().split("#");
	   String[] prices = this.params.getPrices().split("#");
	   boolean resultBool = true;
	   List<ManagerScreeningPlannedUpdateDTO> dtoList = new ArrayList<ManagerScreeningPlannedUpdateDTO>();
 	   for(int i = 0, size = theaters.length; i < size; i++) {
		   ManagerScreeningPlannedUpdateDTO dto = new ManagerScreeningPlannedUpdateDTO();
		   dto.setMovieTheather(Integer.parseInt(theaters[i]));
		   dto.setMovieTitle(movieTitles[i]);
		   dto.setMovieScreeningDate(dateTimes[i]);
		   dto.setMoviePrice(Integer.parseInt(prices[i]));
		   dto.setMovieScreeningDate2(dateTimes[i]);
		   dtoList.add(dto);
		   if(this.dbService.searchScreeningPlannedCnt(dto) > 0) {
			   resultBool = false;
			   break;
		   }
	   }
 
	   if(resultBool) {
		   for(ManagerScreeningPlannedUpdateDTO dto : dtoList) {
	 	   
			   this.dbService.insertMovieScreeningInfo(dto);
		   }
	   }
	   AsyncResult<String> async = new AsyncResult<String>();
	   async.setSuccess(resultBool);
	   async.setData("/movie/mainService/showtimes");
	   return async;
   }
   
   public AsyncResult<String> movieDelete() {
		int movieNo = this.params.getMovieNO();
		System.out.println(movieNo);
		this.dbService.movieDelete(movieNo);
		String url = this.dbService.movieReleaseDateCheck(movieNo) == 1 ?
				"/movie/mainService/screeningsPlanned" :
				"/movie/mainService/currentFilm" ;
		AsyncResult<String> async = new AsyncResult<String>();
		async.setData(url);
		return async;
	}
   
/*****연종. chagePwd success check*****/   //TODO 연종
   public AsyncResult<String> chagePwdSuccessCheck() throws Exception {
      String newPWD = params.getMyInfoNewPwd();
      String newPWDcheck = params.getMyInfoNewPwdCheck();
      String presentPWD = params.getMyInfoPresentPwd();
      AsyncResult<String> asyncResult = new AsyncResult<String>();
      
      //사용자아이디 session  
      HttpSession session = params.getSession();
      String myInfoID = (String)session.getAttribute("userID");
      String dbPresentPWD = this.dbService.selectUserPWD(myInfoID);
      
      
      System.out.println("바꿀 비밀번호 : " + newPWD + "," + newPWDcheck);
      
      System.out.println("현재비밀번호와 디비 비번 : " + presentPWD + ", " +dbPresentPWD);

      System.out.println("디비현재비밀번호 : " + dbPresentPWD);
      
      boolean isCheck = true;
      String resultStr = "false";
      
      //현재PWD 사용자입력PWD 동일한지 체크
      if(!(presentPWD.equals(dbPresentPWD))) {
         isCheck =  false;
      }
      //바꿀PWD 바꿀PWD확인 동일한지 체크
      if(!(newPWD.equals(newPWDcheck))) {
         isCheck =  false;
      }
      //현재비밀번호  형식체크
      if(!RegexCheck.passwdRegexCheck(presentPWD) || !RegexCheck.passwdRegexCheck(newPWD)) {
         isCheck =  false;
      }
      //현재비밀번호 8~15자 이내인지 체크
      if(presentPWD.length() < 8 || presentPWD.length() > 15 || newPWD.length() < 8 || newPWD.length() > 15) {
         isCheck =  false;
      }
      
      if(isCheck) {
    	  isCheck =  true;
         this.dbService.updateNewPwd(myInfoID, newPWD);
         resultStr = "/movie/mainService/myInfoChangePwdResult";
         //this.params.getSession().invalidate();
      }
      asyncResult.setData(resultStr);
      System.out.println("asyncResult 비동기결과: " + resultStr);
      return asyncResult;
   }

/*****연종. 이메일 변경 인증번호 발송 *****/
   public AsyncResult<String> sendEmailConfirmNum() throws Exception {
      AsyncResult<String> asyncResult = new AsyncResult<String>();
      Random rand = new Random();
      int randNum = rand.nextInt(900000) + 100000;
      System.out.println("AsyncResult sendEmailConfirmNum 인증번호발송  >> : " + randNum);
      
      HttpSession session = this.params.getSession();
      String userID = (String)session.getAttribute("userID");
      String userEmail = this.dbService.searchEmail(userID);
      
      HttpSession sessionRandNum = this.params.getSession();
      sessionRandNum.setAttribute("randNum", randNum);
      new SendEmail(String.valueOf(randNum), userEmail); 
      asyncResult.setData("true");
      return asyncResult;
   }   

/*****연종. 이메일 변경 및 확인  updateEmailAddr *****/   
   public AsyncResult<String> updateEmailAddr() throws Exception {
      AsyncResult<String> asyncResult = new AsyncResult<String>();
      
      HttpSession session = this.params.getSession();
      String userID = (String)session.getAttribute("userID"); 
      String randNum = String.valueOf(session.getAttribute("randNum"));
      
      String chageEmailResult = "false";
      String userRandNum = this.params.getMyInfoEmailConfirmNum();
      String chageEmailAddr = this.params.getMyInfoChageEmail();
      
      boolean isCheck = true;
      //이메일 유효성 체크
      if(!RegexCheck.emailRegexCheck(chageEmailAddr)){
         isCheck = false;
      }
      //인증번호 체크
      if(!(randNum.equals(userRandNum))) {
         isCheck = false;
      }
      
      if(isCheck) {
         session.setAttribute("randNum", 0);
         this.dbService.updateEmailAddr(chageEmailAddr, userID);
         chageEmailResult = "/movie/mainService/myInfoChangeEmailResult";
      }
      asyncResult.setData(chageEmailResult);
      return asyncResult;
   }
/***********연종. 회원탈퇴***************/   
   public AsyncResult<String> userWithdraw() throws Exception {
      AsyncResult<String> asyncResult = new AsyncResult<String>();
      
      String withdrawUserpwd = this.params.getWithdrawUserPwd();
      String withdrawResult = "false";
      
      HttpSession session = this.params.getSession();
      String myInfoID = (String)session.getAttribute("userID");
      String presentUserPWD = this.dbService.selectUserPWD(myInfoID);
      
      boolean isCheck = true;
      //사용자 PWD확인
      if(!(withdrawUserpwd.equals(presentUserPWD))) {
         isCheck = false;
      }
      if(isCheck) {
         this.dbService.updateWithdraw(myInfoID);
         withdrawResult =  "/movie/mainService/logout";
      }
      
      asyncResult.setData(withdrawResult);
      return asyncResult;
   }
   

/***********연종. 비밀번호찾기 인증번호***************/   
   public AsyncResult<String> checkPwdConfirmNum() throws Exception {
      AsyncResult<String> asyncResult = new AsyncResult<String>();
      String userConfirmNum = this.params.getSearchPwdConfirmNum();
      HttpSession session = this.params.getSession();
      String serverRandNum = String.valueOf(session.getAttribute("randNum"));
      String searchPwdResult = "false";
      
      boolean isCheck = true;
      //인증번호확인
      if(!(serverRandNum.equals(userConfirmNum))) {
         isCheck = false;
      }
      if(isCheck) {
         searchPwdResult = "/movie/mainService/checkPwdConfirmNum";
      } 
      
      asyncResult.setData(searchPwdResult);
      return asyncResult;
   }
   
   /**수진 이메일 인증번호 생성하여 보내기**/
   //이메일
   @SuppressWarnings("rawtypes")
   public AsyncResult ConfirmNumberSend() throws Exception {
      System.out.println("온거냐");
      String result = null;
      Boolean isCheckResult = true;
      String searchIdUserEmail = this.params.getSearchIdUserEmail();
      System.out.println("searchIdUserEmail : " + searchIdUserEmail);
      
      //이메일 세션 저장
      HttpSession session = this.params.getSession();
      session.setAttribute("searchIdUserEmail", searchIdUserEmail);
      
      if(searchIdUserEmail == "") {
         result = "정확히 입력해주세요.";
         isCheckResult = false;
      }
      if(!RegexCheck.emailRegexCheck(params.getSearchIdUserEmail())) {
         result = "이메일 형식이 맞지 않습니다.";
         isCheckResult = false;
      }
      
      if(isCheckResult) {
         //인증번호생성
         Random randomNum = new Random();
         int confirmNumRandom = randomNum.nextInt(900000) + 100000;
         System.out.println(confirmNumRandom + " : 인증번호");
         
         //TODO
         new SendEmail(String.valueOf(confirmNumRandom), searchIdUserEmail);
         
         //인증번호 세션에 저장
         //인증번호 저장
         session.setAttribute("confirmNumRandom", confirmNumRandom);
         session.setAttribute("currentConfirmTime", System.currentTimeMillis());
         session.setAttribute("isCheckNumber", false);
         System.out.println(session + "session");
         
         System.out.println("인증번호 세션에 저장확인 :" + this.params.getSession().getAttribute("confirmNumRandom"));
         result = "인증번호가 전송되었습니다 :) 확인 부탁드립니다.";
         System.out.println(result + " : 결과");
      
      } else {
         isCheckResult = false;
         result = "인증번호전송이 실패되었습니다. 다시 확인해주세요 :(";
      }
      
      AsyncResult<String> async = new AsyncResult<String>();
      isCheckResult = true;
      async.setData(result);
      return async;
   }
   
   /*******수진 인증번호일치여부 수진*********/
   @SuppressWarnings("rawtypes")
   public AsyncResult confirmNumberCheck() throws Exception {
      String result = "입력하신 인증번호와 일치합니다.";
      int searchIdUserConfirmNum = this.params.getSearchIdUserConfirmNum();
      System.out.println(searchIdUserConfirmNum + " : 사용자가 입력한 인증번혼"); // 인증번호 보낼때 그 인증번호 불러와서 비교하기..!
      //세션에 집어넣기
      HttpSession session = this.params.getSession();
      Integer sessionSaveNum = (Integer) session.getAttribute("confirmNumRandom");
      
      System.out.println(sessionSaveNum + "저장된 인증번호");
      boolean bool = true;
      
      //인증번호가 비었을 경우
      if(searchIdUserConfirmNum == 0 || sessionSaveNum == null) {
         bool = false;
         result = "정확히 입력 바랍니다.";
         System.out.println(result + " : 결과");
      } else if(sessionSaveNum != searchIdUserConfirmNum) {
         bool = false;
         result = "인증번호가 일치 하지않습니다.";
         System.out.println(result + " : 결과");
      } else {
         bool = true;
         result = "인증번호 일치합니다.";
         System.out.println(result + " : 결과");
         session.setAttribute("confirmNumRandom", 0);
      }
      
      AsyncResult<String> async = new AsyncResult<String>();
      System.out.println(bool + " : bool");
      System.out.println(result + " : 결과");
      async.setSuccess(bool);
      return async;
      
   }
   
   @SuppressWarnings("rawtypes")
   public AsyncResult confirmNumInit() throws Exception {
      HttpSession session = this.params.getSession();
      session.setAttribute("isConfirm", false);
      System.out.println("");
      AsyncResult<String> async = new AsyncResult<String>();
      async.setData("<label class=\"join__resultText\" style=\"color:red;\">인증번호를 받아주세요.</label>");      
      
      return async;
   }
   //수진. 마지막 버튼 눌렀을때
   public AsyncResult<String> emailSendMessage() throws Exception {
      AsyncResult<String> asyncResult = new AsyncResult<String>();
      
      String searchIdUserEmail = this.params.getSearchIdUserEmail();
      Integer userConfirmNum = this.params.getSearchIdUserConfirmNum();
      
      System.out.println(searchIdUserEmail + "메일");
      System.out.println(userConfirmNum + "인증");
       
      boolean emailAllCheck = false;
      
      if(searchIdUserEmail == "" && userConfirmNum == null) {
         emailAllCheck = false;
      } else {
         emailAllCheck = true;
      }
      asyncResult.setSuccess(emailAllCheck);
      return asyncResult;
   }
   
   //자주묻는게시판 전환
   public AsyncResult<String> pagingBoard() throws Exception {
      
      int totBoardList = this.dbService.serviceCentergetBoardCount();
      System.out.println("service글목록 갯수 : " + totBoardList);
      
      int page = this.params.getPageboard();
      System.out.println(page + "page");
      
      Paging boardPaging = new Paging(totBoardList, 7,page, 5);
      boardPaging.setBoardPaging();
      
      String boardpagingResult = "/movie/mainService/serviceCenter";
      AsyncResult<String> asyncResult = new AsyncResult<String>();
      asyncResult.setData(boardpagingResult);
      return asyncResult;
         
   }
   
   /*******수진. 문의사항 글등록 *******/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AsyncResult<String> InsertAskWriteBoard() throws Exception {
		
		String title     = this.params.getInsertTitle();
		String content   = this.params.getInsertTextArea();
		Integer writePwd     = this.params.getInsertboardPWd() == null ? null : this.params.getInsertboardPWd();
		int isPwd        = this.params.isInsertPwdcheck() == true ? 1 : 0;
		
		//1은 비밀글 등록 // 2면 일반글등록
		boolean isResult = true;
		
		HttpSession session = this.params.getSession();
		String user_Id = (String)session.getAttribute("userID");

		if(title == "" && content == "") {
			isResult = false;
		}
		if(isPwd == 1 && writePwd == null) {
			isResult = false;
		}
		if(isResult) {
			isResult = true;
		}
		AsyncResult<String> asyncResult = new AsyncResult<String>();
		asyncResult.setSuccess(isResult);
		asyncResult.setData("async");
		return asyncResult;
		
	}
	
	//수진. 문의사항 글 등록시 입력한 비밀번호 체크
	public AsyncResult<Boolean> insertPwdCheck() throws Exception {
		ModelAndView mav = new ModelAndView("/service/include/confirmBoardCheck");
		Integer questionBoardNum = this.params.getQuestionBoardNum();
		Integer userInsertPwd   = this.params.getUserInsertPwd(); //비번체크확인   
		
		System.out.println(questionBoardNum + " : 비번확인글 선택한 번호");
		
		CinemaQuestionBoardDTO questionBoardList = this.dbService.questionBoardList(questionBoardNum);
		int getUserPwd = questionBoardList.getWritePwd();

		boolean isResult = true;
		if(userInsertPwd == null) {
			isResult = false;
		}
		if(userInsertPwd != getUserPwd) {
			isResult = false;
		}
//		if(questionBoardNum.intValue() < 0 || questionBoardNum.length() > 5) {
//			isResult = false;
//		}
		if(isResult) {
			isResult = true;
		}
		
		AsyncResult<Boolean> asyncResult = new AsyncResult<Boolean>();
		asyncResult.setData(isResult);
		return asyncResult;
		
	}
	
	//수진 문의사항 수정
		public AsyncResult<Boolean> completeUPdateWriteBoard() throws Exception {
		
		String no            = this.params.getUpdateQuestionBoardNum();
		String title         = this.params.getInsertTitle();
		String content       = this.params.getInsertTextArea();

		Integer writePwd         = this.params.getInsertboardPWd() == null ? null : this.params.getInsertboardPWd();
		int isPwd            = this.params.isInsertPwdcheck() == true ? 1 : 0;
		
		System.out.println();
		
		System.out.println("_______수정완료________");
		System.out.println("수정 사항번호 : " + no);
		System.out.println("제목 : " + title);
		System.out.println("내용 : " + content);
		System.out.println("비밀번호 : " + writePwd);
		System.out.println("여부 : " + isPwd);
		//System.out.println(user_Id);
		//System.out.println(write_date);
		
		boolean isResult = true;
		
		if(title =="" && content == "") {
			isResult = false;
		}
		if(writePwd == null && isPwd == 1) {
			isResult = false;
		}
		AsyncResult<Boolean> asyncResult = new AsyncResult<Boolean>();
		asyncResult.setData(isResult);
		return asyncResult;
	}
	
	/*********************수진.관리자 문의사항페이지*****************************/
		public AsyncResult<Boolean> managementUpdateBoardComplete() throws Exception {
			
			String question = this.params.getQuestion();
			String asked    = this.params.getAsked();
			String no       = this.params.getNo();
			
			System.out.println("관리자가 수정한 제목 : "  + question);
			System.out.println("관리자가 수정한 내용: "   + asked);
			System.out.println("관리가자 수정할 번호 : "  + no);
			
			//db업데이트 쿼리문
			boolean isResult = true;
			if(question == "" && asked == "") {
				isResult = false;
			}
			if(isResult) {
				isResult = true;
			}
			AsyncResult<Boolean> asyncResult = new AsyncResult<Boolean>();
			asyncResult.setData(isResult);
			return asyncResult;
		}
		
		//관리자 답글 달기
		
		public AsyncResult<Boolean> managementReplyBoardFormComplete() throws Exception {
			
			Integer writePwd  = this.params.getReplyPwd() == null ? null : this.params.getReplyPwd();
			int isPwd     = this.params.isReplytPwdcheck() == true ? 1 : 0;
			
			
			
			String title = this.params.getReplyTitle();
			String content = this.params.getReplyContent();
			
			boolean isResult = true;
			
			if(title =="" && content == "") {
				isResult = false;
			}
			if(writePwd == null && isPwd == 1) {
				isResult = false;
			}
			AsyncResult<Boolean> asyncResult = new AsyncResult<Boolean>();
			asyncResult.setData(isResult);
			return asyncResult;
		}
/* 연종. 관리자 공지사항 등록*/
	public AsyncResult<String> managerInsertNotice() throws Exception { 
		AsyncResult<String> asyncResult = new AsyncResult<String>();
		CinemaNoticeBoardDTO cinemaNoticeBoardDTO = new CinemaNoticeBoardDTO();
		Integer noticeImportant = 0;
		
		String noticeTitle = this.params.getManagerNoticeTitle();
		String noticeContents = this.params.getManagerNoticeContents();
		boolean isNoticeImportant = this.params.isManagerNoticeImportant();
		
		if(isNoticeImportant) {
			noticeImportant = 1;
		}
		cinemaNoticeBoardDTO.setContent(noticeContents);
		cinemaNoticeBoardDTO.setImportant(noticeImportant);
		cinemaNoticeBoardDTO.setTitle(noticeTitle);
		
		System.out.println("noticeTitle  >>  " + cinemaNoticeBoardDTO.getTitle());
		System.out.println("noticeContents  >>  " + cinemaNoticeBoardDTO.getContent());
		System.out.println("isNoticeImportant  >>  " + cinemaNoticeBoardDTO.getImportant());
		
		this.dbService.insertNoticeBoard(cinemaNoticeBoardDTO.getTitle(),
										 cinemaNoticeBoardDTO.getContent(),
										 cinemaNoticeBoardDTO.getImportant());
		
		System.out.println("noticeImportant  >>  " + noticeImportant);
		asyncResult.setData("/movie/mainService/notice");
		return asyncResult;
	}
	
	public AsyncResult<String> managerUpdatetNotice() throws Exception { 
		AsyncResult<String> asyncResult = new AsyncResult<String>();
		CinemaNoticeBoardDTO cinemaNoticeBoardDTO = new CinemaNoticeBoardDTO();
		Integer noticeImportant = 0;
		Integer noticePage = this.params.getManagerNoticePage();
		Integer noticeNO = this.params.getManagerNoticeNo();
		
		String noticeTitle = this.params.getManagerNoticeTitle();
		String noticeContents = this.params.getManagerNoticeContents();
		boolean isNoticeImportant = this.params.isManagerNoticeImportant();
		
		if(isNoticeImportant) {
			noticeImportant = 1;
		}
		cinemaNoticeBoardDTO.setContent(noticeContents);
		cinemaNoticeBoardDTO.setImportant(noticeImportant);
		cinemaNoticeBoardDTO.setTitle(noticeTitle);
		cinemaNoticeBoardDTO.setNo(noticeNO);
		
		this.dbService.updateNoticeBoard(cinemaNoticeBoardDTO.getTitle(),  cinemaNoticeBoardDTO.getContent(),
											cinemaNoticeBoardDTO.getImportant(), cinemaNoticeBoardDTO.getNo());
		asyncResult.setData("/movie/mainService/notice?noticePage=" + noticePage + "&noticeNo=" + noticeNO);
		return asyncResult;
	}
	
	public AsyncResult<String> managerDeleteNotice() throws Exception { 
		AsyncResult<String> asyncResult = new AsyncResult<String>();
		Integer noticePage = this.params.getManagerNoticePage();
		Integer noticeNO = this.params.getManagerNoticeNo();
		
		this.dbService.deleteNoticeBoard(noticeNO);
		
		asyncResult.setData("/movie/mainService/notice?noticePage=" + noticePage + "&noticeNo=" + noticeNO);
		return asyncResult;
	}
	
	public AsyncResult<String> managerUpdateMovieInfo() throws Exception { 
		AsyncResult<String> asyncResult = new AsyncResult<String>();
		MovieBasicInfoDTO movieBasicInfoDTO = new MovieBasicInfoDTO();
		
		movieBasicInfoDTO.setMovieTitle(		this.params.getManagerMovieTitle()      );
		movieBasicInfoDTO.setMovieGenre(		this.params.getManagerMovieGenre()      );
		movieBasicInfoDTO.setMovieDirector(		this.params.getManagerMovieDirector()   );
		movieBasicInfoDTO.setMovieAuthor(		this.params.getManagerMovieAuthor()     );
		movieBasicInfoDTO.setMovieCast(			this.params.getManagerMovieCast()       );
		movieBasicInfoDTO.setMovieReleaseDate(	this.params.getManagerMovieReleaseDate());
		movieBasicInfoDTO.setMovieAgeLimitText(	this.params.getManagerMovieAge()        );
		movieBasicInfoDTO.setMovieRuntime(		this.params.getManagerMovieRuntime()    );
		movieBasicInfoDTO.setNo(				this.params.getMovieNO()                );
		movieBasicInfoDTO.setMovieIntro(		this.params.getManagerMovieIntro()      );
		
		this.dbService.updateMovieInfo(movieBasicInfoDTO);
		asyncResult.setData("/movie/mainService/movieDetailInfo?movieInfoTitle=" + this.params.getManagerMovieTitle() + "&movieNO"+ this.params.getMovieNO());
		return asyncResult;
	}
	
}
   