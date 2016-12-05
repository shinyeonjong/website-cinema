package com.allnightMovies.model.data.cinemaInfo;

import lombok.Data;
import lombok.experimental.Accessors;
@Accessors(chain=true)
@Data
public class UserClickShowtimesDTO {
	public String movieTitle;
	public String movieTime;
	public int movieTheater;
	public int movieYear;
	public int movieMonth;
	public int movieDay;
}
