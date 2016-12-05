package com.allnightMovies.model.params;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Params {
	private HttpServletRequest request;
	private HttpSession session;
	private MultipartHttpServletRequest multiReq;
	
	private String main;
	private String sub;
	private String method;
//메뉴 경로 및 메소드 호출시 사용--------------------------------------------------------------	
	
	private String directory;
	private String page;
	private String contentCSS;
	private String contentjs;
//메뉴경로가 아닌 페이지 호출시---------------------------------------------------------------	
	
	private String  userName;
	private String  userID;
	private String  userIDCheck;
	private String  userPWD;
	private String  userRePWD;
	private String  userGender;
	private String  userEmail;
	private String  userBirth;
	private Integer confirmNum;
	private String 	params;
// 유저회원가입------------------------------------------------------------------------------
	private int    userStates;
	private String keepLogin;	// 로그인 유지 체크여부

// 패스워드 찾기------------------------------------------------------------------
	private String searchPwdUserID;
	private String searchPwdConfirmNum;
	private String searchPwdNewPwd;
	
	private String searchIdConfirmNum; //아이디 이메일인증번호(사용자가 입력한 번호)
	private String searchIdConfirmNumCheck; //아이디이메일 저장된 인증번호
	
// 아이디 찾기
	private String searchIdUserName;
	private String searchIdUserBirth;
	private String searchIdUserGender;
	private String searchIdUserEmail;
	private Integer searchIdUserConfirmNum;
	
// 비밀번호 변경	------------------------------------------------------------------
	private String myInfoPresentPwd;
	private String myInfoNewPwd;
	private String myInfoNewPwdCheck;
	
// 이메일 변경 ------------------------------------------------------------------
	private String myInfoChageEmail;
	private String myInfoEmailConfirmNum;

// 회원탈퇴 	------------------------------------------------------------------
	private String withdrawUserPwd;

// 예매하기(ticketing)
	private String screeningDate;
	private String movieTitle;
	private String movieTime;
	private int personCnt;
	private int theater;
	private String seatArr;
	private int calendarMonth;
	private int calendarYear;
	private String screeningDateType;
	
// 결제
	private String cardType;
	private String cardNum;
	private String cardPWD;
	private String cardExpiryDateMonth;
	private String cardExpiryDateYear;
	private String cardOwnerBirth;
	
// 예매취소
	private String ticketNum;

// 공지사항 게시판
	private int noticeUserClickPage;/*삭제예정*/
	private int noticePage;
	private int noticeNo;
	
// 고객센터 게시판
	private Integer pageboard; //자주묻는게시판 페이징
	private Integer questionBoard; //문의사항게시판 페이징
	private Integer questionBoardNum;//고객센터 게시글 번호
	private String serviceCenterSearchWord;
	private String noticeSearachWord;
	private String updateQuestionBoardNum; //문의사항 게시글번호
	
// 고객센터 (관리자)->자주묻는게시판
	private String question;
	private String asked;
	private String no;
	
	private String userQuestionTitle;
	
	//답글달기
	private String replyTitle;
	private String replyContent;
	private Integer replyPwd;
	private String replyNo;
	private String replyDepth;
	private String replyStep;
	private boolean replytPwdcheck;
	

// 문의 사항 게시판
	private String insertTextArea; //내용
	private String insertTitle; //제목
	private Integer insertboardPWd; //비밀번호
	private boolean insertPwdcheck; //비번체크여부
	private Integer userInsertPwd; //확인비밀번호
	private String insertUser_id;
	private String insertWriteDate;
	
//연종. 영화 상세보기-------------------------------------------------------
	private String movieInfoTitle;
	private int movieNO;
	private int movieInfoReviewPage;
	
//연종. 리뷰 등록-------------------------------------------------------
	private int reviewScore;
	private String reviewContents;
	private String deleteReviewID;
	private int reviewNo;
	
// 연종. 관리자 공지사항등록 -------------------------------------------------------
	private String managerNoticeTitle;
	private String managerNoticeContents;
	private boolean managerNoticeImportant;
	private Integer managerNoticePage;
	private Integer managerNoticeNo;

// 연종. 관리자 영화수정/등록 -------------------------------------------
	
	private String managerMovieTitle;
	private String managerMovieGenre;
	private String managerMovieDirector;
	private String managerMovieAuthor;
	private String managerMovieCast;
	private String managerMovieReleaseDate;
	private String managerMovieIntro;
	private Integer managerMovieAge;
	private Integer managerMovieRuntime;
	
//메인 검색
	private String searchWord;

//파일업로드시 사용
	private String locationPath;
	private String fileUpload;

	
//메인 페이징
	private Integer mainPaing;
	
//매니저 메뉴
	private String searchDate;
	private String searchTheater;
	private String ticketNumPost;
	private String ticketNumBack;
	private String ticketNums;
	private String theaters;
	private String movieTitles;
	private String dateTimes;
	private String prices;
	
	private int movieYear;
	private String userChoiceMovieTime;
	private int userChoiceMovieTheater;
}