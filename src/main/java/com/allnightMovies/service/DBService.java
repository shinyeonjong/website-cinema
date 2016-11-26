package com.allnightMovies.service;


import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allnightMovies.dao.DBMapper;
import com.allnightMovies.model.data.MainMenu;
import com.allnightMovies.model.data.cinemaInfo.CinemaFrequentlyBoardDTO;
import com.allnightMovies.model.data.cinemaInfo.CinemaQuestionBoardDTO;
import com.allnightMovies.model.data.cinemaInfo.CinemaNoticeBoardDTO;
import com.allnightMovies.model.data.cinemaInfo.CinemaTheaterSeatDTO;
import com.allnightMovies.model.data.movieInfo.MovieCurrentFilmDTO;
import com.allnightMovies.model.data.movieInfo.MovieScreeningDateInfo;
import com.allnightMovies.model.data.movieInfo.MovieScreeningsPlannedDTO;
import com.allnightMovies.model.data.movieInfo.MovieShowTimesMap;
import com.allnightMovies.model.data.movieInfo.TicketingMovieTimeInfo;
import com.allnightMovies.model.data.movieInfo.TicketingMovieTitleInfo;
import com.allnightMovies.model.data.theater.CinemaIntroduceDTO;
import com.allnightMovies.model.data.userInfo.UserPersonalInfoDTO;
import com.allnightMovies.model.data.userInfo.UserPersonalLoginInfoDTO;
import com.allnightMovies.model.params.Params;

import oracle.net.aso.d;



@Service
public class DBService {
	
	@Autowired
	DBMapper dbMapper;
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
	
	public List<CinemaTheaterSeatDTO> getTheaterSeatInfo(int theater) {
		return dbMapper.getTheaterSeatInfo(theater);
	}

	public Integer getTicketPriceInfo(String screeningDate, String theater) {
		return dbMapper.getTicketPriceInfo(screeningDate, theater);
	}
	
	public String getMoviePoster(String title) {
		return dbMapper.getMoviePoster(title);
	}
/** jung. 상영시간표 **/
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
	
	
/**JUNG 문의사항게시판 **/
	public Integer questionBoardCount() throws Exception {
		System.out.println("Dbservie");
		return dbMapper.questionBoardCount();
	}
	public ArrayList<CinemaQuestionBoardDTO> questionBoard(int startPageNum, int endPageNum) throws Exception {
		return dbMapper.questionBoard(startPageNum, endPageNum);
	}
	public CinemaQuestionBoardDTO questionBoardList(Integer questionBoardNum) throws Exception {
		return dbMapper.questionBoardList(questionBoardNum);
		
	}

	
	
/** shin. SEARCH PWD **/	
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
}