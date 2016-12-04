package com.allnightMovies.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allnightMovies.dao.DBMapper;
import com.allnightMovies.model.data.MainMenu;
import com.allnightMovies.model.data.cinemaInfo.CinemaFrequentlyBoardDTO;
import com.allnightMovies.model.data.cinemaInfo.CinemaNoticeBoardDTO;
import com.allnightMovies.model.data.cinemaInfo.CinemaQuestionBoardDTO;
import com.allnightMovies.model.data.cinemaInfo.CinemaSeatDTO;
import com.allnightMovies.model.data.cinemaInfo.CinemaSeatReserveInfo;
import com.allnightMovies.model.data.movieInfo.MainPageEventDTO;
import com.allnightMovies.model.data.movieInfo.ManagerScreeningPlannedUpdateDTO;
import com.allnightMovies.model.data.movieInfo.MovieBasicInfo;
import com.allnightMovies.model.data.movieInfo.MovieBasicInfoDTO;
import com.allnightMovies.model.data.movieInfo.MovieCurrentFilmDTO;
import com.allnightMovies.model.data.movieInfo.MovieReviewBoard;
import com.allnightMovies.model.data.movieInfo.MovieScreeningDateInfo;
import com.allnightMovies.model.data.movieInfo.MovieScreeningsPlannedDTO;
import com.allnightMovies.model.data.movieInfo.MovieShowTimesMap;
import com.allnightMovies.model.data.movieInfo.MovieStillCut;
import com.allnightMovies.model.data.movieInfo.TicketingMovieTimeInfo;
import com.allnightMovies.model.data.movieInfo.TicketingMovieTitleInfo;
import com.allnightMovies.model.data.theater.CinemaIntroduceDTO;
import com.allnightMovies.model.data.userInfo.ManagerMemberInquiryDTO;
import com.allnightMovies.model.data.userInfo.ManagerUserReserveDTO;
import com.allnightMovies.model.data.userInfo.MovieEndTimeDTO;
import com.allnightMovies.model.data.userInfo.UserCheckEmptySeatsDTO;
import com.allnightMovies.model.data.userInfo.UserPersonalInfoDTO;
import com.allnightMovies.model.data.userInfo.UserPersonalLoginInfoDTO;
import com.allnightMovies.model.data.userInfo.UserSelectTicketingInfo;
import com.allnightMovies.model.data.userInfo.UserTicketingInfo;
import com.allnightMovies.model.params.Params;



@Service
public class DBService {
	
	@Autowired
	DBMapper dbMapper;
	
	
//	public void doTestInsert() throws Throwable{
//		
//		System.out.println("init");
//		
//		List<CinemaSeatDTO> insertList = Lists.newArrayList();
//		
//		List<String> rowList = Arrays.asList(new String [] {
//				"A", "B", "C", "D",  "E","Z","F", "G", "H", "I","J"
//		});
//		
//		for(int i = 0; i < rowList.size(); i++) {
//			String rows = rowList.get(i);
//			
//			for(int j = 0, col = 1; j < 11; j++) {
//				CinemaSeatDTO dto = new CinemaSeatDTO();
//				if(rows.equals("Z")) {
//					dto
//					.setTheater("3")
//					.setRow("")
//					.setCol("")
//					.setName("")
//					.setStatus(0);
//
//				} else {
//						dto
//						.setTheater("3")
//						.setRow(rows)
//						.setCol(col + "")
//						.setName(rows + col)
//						.setStatus(1);
//						col++;
//					}
//				
//				
//				insertList.add(dto);
//			}
//			
//		}
//		
//		for(CinemaSeatDTO dto : insertList) {
//			System.out.println(dto.toString());
//			this.dbMapper.doTestInsert(dto);
//		}
//		
//	}
//	
/** ji. Menu Loading **/
	public List<MainMenu> getMenus() throws Exception {
		return dbMapper.getMenus();
	}
	
/** ji. Login **/
	
	public UserPersonalLoginInfoDTO login(Params params) {
		return dbMapper.login(params);
	}

/** ji. Join **/
	public Integer idCheck(String id) throws Exception {
		return dbMapper.idCheck(id);
	}

	public Integer insertJoinUserInfo(UserPersonalInfoDTO userDTO) {
		return dbMapper.insertJoinUserInfo(userDTO);
	}

/** ji. ticketing **/
	public MovieScreeningDateInfo getMaxScreeningDate() {
		return dbMapper.getMaxScreeningDate();
	}
	
	public List<TicketingMovieTitleInfo> getMovieTitle() {
		return dbMapper.getMovieTitle();
	}
	
	public List<TicketingMovieTimeInfo> getMovieTime(String movieTitle, String date) {
		return dbMapper.getMovieTime(movieTitle, date);
	}

	public Integer getTicketPriceInfo(String screeningDate, String theater) {
		return dbMapper.getTicketPriceInfo(screeningDate, theater);
	}
	
	public String getMoviePoster(String title) {
		return dbMapper.getMoviePoster(title);
	}
	
	public Integer checkEmptySeats(UserCheckEmptySeatsDTO checkEmptyDTO) {
		return dbMapper.checkEmptySeats(checkEmptyDTO);
	}
	
	public Integer userTicketingInfoInsert(UserTicketingInfo ticketingInfo) {
		return dbMapper.userTicketingInfoInsert(ticketingInfo);
	}
	
	public List<CinemaSeatDTO> getTheaterSeatInfo(CinemaSeatReserveInfo reserveInfo) {
		return dbMapper.getTheaterSeatInfo(reserveInfo);
	}
	
	public Integer getTheaterSeatColCnt(String theater) {
		return dbMapper.getTheaterSeatColCnt(theater);
	}
	
/** ji. ticketing confirmation 예매확인 **/
	public List<UserSelectTicketingInfo> reservationSeatInfo(String userID) {
		return dbMapper.reservationSeatInfo(userID);
	}
	
	public MovieEndTimeDTO getMovieEndTime(String ticketNum) {
		return dbMapper.getMovieEndTime(ticketNum);
	}
	
/** ji. cancel ticket 예매취소 **/
	public void cancelTicket(String ticketNum, String userID) {
		dbMapper.cancelTicket(ticketNum, userID);
	}

/** ji. search movie info 영화 검색 **/
	public List<MovieBasicInfo> searchMovieInfo(String searchWord) {
		return dbMapper.searchMovieInfo(searchWord);
	}
/** ji. manager menu 메니저메뉴 **/
	public List<ManagerMemberInquiryDTO> getMemberInfo() {
		return dbMapper.getMemberInfo();
	}
	/** ji. manager menu 메니저 회원탈퇴 **/	
	public void managerWithdrawalMember(String userID) {
		dbMapper.managerWithdrawalMember(userID);
	}
	/** ji. manager menu 메니저 탈퇴복구 **/	
	public void managerRestoreMember(String userID) {
		dbMapper.managerRestoreMember(userID);
	}
	/** ji manager menu paging **/
	public Integer userInfoTotCount() {
		return dbMapper.userInfoTotCount();
	}
	public ArrayList<ManagerMemberInquiryDTO> userInfoTotList(int userInfoListStartPage, int userInfoEndPage) {
		return dbMapper.userInfoTotList(userInfoListStartPage, userInfoEndPage);
	}
	/** ji. manager menu 메니저 유저 검색 **/	
	public List<ManagerMemberInquiryDTO> searchMemberInfo(String userID, String userName, String userBirth) {
		return dbMapper.searchMemberInfo(userID, userName, userBirth);
	}
	public List<ManagerUserReserveDTO> managerReservationInfo(ManagerUserReserveDTO dto) {
		return dbMapper.managerReservationInfo(dto);
	}
	public List<String> managerGetMovieTitle() {
		return dbMapper.managerGetMovieTitle();
	}
	public List<Integer> managerGetTheaterCnt() {
		return dbMapper.managerGetTheaterCnt();
	}
	public void managerTicketCancel(String ticketNum) {
		dbMapper.managerTicketCancel(ticketNum);
	}
	public Integer searchScreeningPlannedCnt(ManagerScreeningPlannedUpdateDTO dto) {
		return dbMapper.searchScreeningPlannedCnt(dto);
	}
	public void insertMovieScreeningInfo(ManagerScreeningPlannedUpdateDTO dto) {
		dbMapper.insertMovieScreeningInfo(dto);
	}
	
	public void movieDelete(Integer movieNo) {
		dbMapper.movieDelete(movieNo);
	}
	
	public Integer movieReleaseDateCheck(Integer movieNo) {
		return dbMapper.movieReleaseDateCheck(movieNo);
	}
	
/** jung. 상영시간표 **/ //TODO 수진
	public List<MovieShowTimesMap> showtimes() throws Exception {
		return dbMapper.showtimes();
	}
/** jung. 아이디 찾기**/
	public List<Params> searchId(String searchIdUserName, String searchIdUserBirth, String searchIdUserGender) throws Exception {
		System.out.println("searchIdUserGender" + searchIdUserGender);
		return dbMapper.searchId(searchIdUserName, searchIdUserBirth, searchIdUserGender);
	}
	public Integer searchIdCount(String searchIdUserName, String searchIdUserBirth, String searchIdUserGender) throws Exception {
		return dbMapper.searchIdCount(searchIdUserName, searchIdUserBirth, searchIdUserGender);
	}
	public Integer searchEmailCount(String searchIdUserEmail) throws Exception {
		return dbMapper.searchEmailCount(searchIdUserEmail);
	}
	public List<Params> searchIDEmail(String searchIdUserEmail) throws Exception {
		return dbMapper.searchIDEmail(searchIdUserEmail);
	}

/**jung. 고객센터(자주묻는질문) **/
	public List<CinemaFrequentlyBoardDTO> serviceCenter() throws Exception {
		return dbMapper.serviceCenter();
	}
	public ArrayList<CinemaFrequentlyBoardDTO> serviceCentergetBoard(int startPageNum, int endPageNum) throws Exception {
		return dbMapper.serviceCentergetBoard(startPageNum, endPageNum);
	}
	public Integer serviceCentergetBoardCount() throws Exception {
		return dbMapper.serviceCentergetBoardCount();
	}
	
	public Integer userSearchList(String serviceCenterSearchWord) throws Exception {
		return dbMapper.userSearchList(serviceCenterSearchWord);
	}
	public List<CinemaFrequentlyBoardDTO> getUserSearchList(int searchStartNum, int searchEndNum, String serviceCenterSearchWord) {
		return dbMapper.getUserSearchList(searchStartNum, searchEndNum, serviceCenterSearchWord);
	}
	
/**JUNG 문의사항게시판 **/
	public Integer questionBoardCount() throws Exception {
		return dbMapper.questionBoardCount();
	}
	public ArrayList<CinemaQuestionBoardDTO> questionBoard(int startPageNum, int endPageNum) throws Exception {
		return dbMapper.questionBoard(startPageNum, endPageNum);
	}
	public CinemaQuestionBoardDTO questionBoardList(Integer questionBoardNum) throws Exception {
		return dbMapper.questionBoardList(questionBoardNum);
	}
	public Integer InsertAskWriteBoard(CinemaQuestionBoardDTO cinemaQuestionBoardDTO) throws Exception {
		return dbMapper.InsertAskWriteBoard(cinemaQuestionBoardDTO);
	}
	
	public CinemaQuestionBoardDTO completeUPdateWriteBoard(String title, String content, int writePwd, int isPwd, String no) throws Exception {
		return dbMapper.completeUPdateWriteBoard(title, content, writePwd, isPwd, no);
	}
	
	public Integer completeDeleteQuestionBoard(String completeDeleteQuestionBoardNum) throws Exception {
		return dbMapper.completeDeleteQuestionBoard(completeDeleteQuestionBoardNum);
	}
	
	public Integer completeDeleteReplyQuestionBoard(String completeDeleteQuestionBoardNum) throws Exception {
		return dbMapper.completeDeleteReplyQuestionBoard(completeDeleteQuestionBoardNum);
	}

/**JUNG 고객센터 자주묻는질문 관리자페이지 **/
	public CinemaFrequentlyBoardDTO managementWriteBoard(String question, String asked) throws Exception {
		return dbMapper.managementWriteBoard(question, asked);
	}
	
	public CinemaFrequentlyBoardDTO managementFrequentlyBoardCount(String no) throws Exception {
		return dbMapper.managementFrequentlyBoardCount(no);
	}
	
	public CinemaFrequentlyBoardDTO managementUpdateFormComplete(String question, String asked, String no) throws Exception {
		return dbMapper.managementUpdateFormComplete(question, asked, no);
	}
	
	public Integer managementDeleteFormComplete(String no) throws Exception {
		return dbMapper.managementDeleteFormComplete(no);
	}
	
/** JUNG 고객센터 문의사항 관리자페이지 **/
	public CinemaQuestionBoardDTO insertReplyBoard(String title, String content, String user_id, int writePwd, int isPwd, String  replyNo, String replyStep, String replyDepth) throws Exception {
		return dbMapper.insertReplyBoard(title, content, user_id, writePwd, isPwd, replyNo, replyStep, replyDepth);
	}
	public CinemaQuestionBoardDTO updateDepth(String replyNo, String replyStep) throws Exception {
		return dbMapper.updateDepth(replyNo, replyStep);
	}
/** shin. SEARCH PWD **/ //TODO 연종
	public Integer searchPWD(String searchPwdUserID)  {
		return dbMapper.searchPWD(searchPwdUserID);
	}
	
	public String searchEmail(String searchPwdUserID) {
		return dbMapper.searchEmail(searchPwdUserID);
	}
	
	public Params updateNewPwd(String searchPwdUserID, String searchPwdNewPwd) {
		return dbMapper.updateNewPwd(searchPwdUserID, searchPwdNewPwd);
	}

/** shin. MY INFO **/	
	public UserPersonalInfoDTO selectMyInfo(String myInfoID) {
		return dbMapper.selectMyInfo(myInfoID);
	}
	public String selectUserPWD(String myInfoID) {
		return dbMapper.selectUserPWD(myInfoID);
	}
	public Params updateWithdraw(String myInfoID) {
		return dbMapper.updateWithdraw(myInfoID);
	}
	public String updateEmailAddr(String emailAddr, String userID) {
		return dbMapper.updateEmailAddr(emailAddr, userID);
	}
	
/** shin. MOVIE	currentFilm **/
	public List<MovieCurrentFilmDTO> getCurrentFilmDTO() {
		return dbMapper.getCurrentFilmDTO();
	}
	public List<MovieScreeningsPlannedDTO> getPlannedFilmDTO() {
		return dbMapper.getPlannedFilmDTO();
	}
	public List<MovieCurrentFilmDTO> sortScore() {
		return dbMapper.sortScore();
	}
	public List<MovieCurrentFilmDTO> sortTicketing() {
		return dbMapper.sortTicketing();
	}
	
/** shin. SERVICE noticeBoard **/
	public int getNoticeBoardCount() {
		return dbMapper.getNoticeBoardCount();
	}
	public List<CinemaNoticeBoardDTO> getCinemaNoticeBoardDTO(int blockStartNum, int blockEndNum) {
		return dbMapper.getCinemaNoticeBoardDTO(blockStartNum, blockEndNum);
	}
	public CinemaNoticeBoardDTO getNoticeBoardContent(Integer noticeNo) {
		return dbMapper.getNoticeBoardContent(noticeNo);
	}
	public List<CinemaNoticeBoardDTO> searchBoard(Integer blockStartNum, Integer blockEndNum, String searchWord) {
		return dbMapper.searchBoard(blockStartNum, blockEndNum, searchWord);
	}
	public int searchBoardCount(String searchWord) {
		return dbMapper.searchBoardCount(searchWord);
	}
	
/** shin. THEATER introduce **/
	public List<CinemaIntroduceDTO> getCinemaIntroImg() {
		return dbMapper.getCinemaIntroImg();
	}
	
/** shin. MAIN eventImg**/
	public List<MainPageEventDTO> getMainEvnetImg() {
		return dbMapper.getMainEvnetImg();
	}
	public List<MovieCurrentFilmDTO> getNewFilmDTO() {
		return dbMapper.getNewFilmDTO();
	}
	public List<CinemaNoticeBoardDTO> getMainNoticeDTO() {
		return dbMapper.getMainNoticeDTO();
	}
	
/** shin. 영화 상세정보  **/
	public MovieBasicInfo getMovieBasicInfo(String movieTitle) {
		return dbMapper.getMovieBasicInfo(movieTitle);
	}
	public List<MovieReviewBoard> getReviewBoard(String movieTitle) {
		return dbMapper.getReviewBoard(movieTitle);
	}
	public List<MovieReviewBoard> getReviewBoardList(Integer blockStartNum, Integer blockEndNum, String movieTitle) {
		return dbMapper.getReviewBoardList(blockStartNum, blockEndNum, movieTitle);
	}
	public MovieReviewBoard insertReview(Integer score, String contents, String userID, String movieTitle ) {
		return dbMapper.insertReview(score, contents, userID, movieTitle);
	}
	public MovieReviewBoard deleteReview(Integer reviewNo) {
		return dbMapper.deleteReview(reviewNo);
	}
	public List<MovieStillCut> getStillcut(String movieTitle) {
		return dbMapper.getStillcut(movieTitle);
	}
/* shin 관리자 공지사항 등록*/
	public CinemaNoticeBoardDTO insertNoticeBoard(String title, String content, int important) {
		return dbMapper.insertNoticeBoard(title, content, important);
	}
	public CinemaNoticeBoardDTO updateNoticeBoard(String title, String content, int important, int no) {
		return dbMapper.updateNoticeBoard(title, content, important, no);
	}
	public Integer deleteNoticeBoard(Integer noticeNO) {
		return dbMapper.deleteNoticeBoard(noticeNO);
	}
/*shin. 영화*/
	public void updateMovieInfo(MovieBasicInfoDTO dto) {
		dbMapper.updateMovieInfo(dto);
	}
	public void insertNewMovie(MovieBasicInfo dto) {
		dbMapper.insertNewMovie(dto);
	}
	public void insertStillcut(String fileName, String movieTitle) {
		dbMapper.insertStillcut(fileName, movieTitle);
	}
	public Integer getMovieNO(String movieTitle) {
		return dbMapper.getMovieNO(movieTitle);
	}
}