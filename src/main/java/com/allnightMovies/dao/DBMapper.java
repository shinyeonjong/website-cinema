package com.allnightMovies.dao;




import java.util.List;


import com.allnightMovies.model.data.MainMenu;
import com.allnightMovies.model.data.userInfo.UserPersonalInfoDTO;
import com.allnightMovies.model.data.movieInfo.MovieShowTimesMap;
import com.allnightMovies.model.params.Params;


public interface DBMapper {
	public List<MainMenu> getMenus() throws Exception;
	public Integer idCheck(String id);
	public String login(Params params);
	public Integer insertJoinUserInfo(UserPersonalInfoDTO userDTO);
	public List<MovieShowTimesMap> showtimes() throws Exception;
	
}