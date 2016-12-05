package com.allnightMovies.model.data.movieInfo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class ManagerMovieTimeAddDTO {
	private String movieTitle;
	private String movieReleaseDate;
}
