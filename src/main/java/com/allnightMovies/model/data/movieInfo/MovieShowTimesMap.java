package com.allnightMovies.model.data.movieInfo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieShowTimesMap {
	private String screening_Date; // 영화날짜
	//private List<MovieShowTimesDTO> movieShowTimesDTO;
	private String screeningYear;
	
	//영화 class에서 영화명 뽑아오기
	//그안에 시간 관 list있음.
	private List<MovieShowTitleDTO> movieShowTitleDTO;

}
