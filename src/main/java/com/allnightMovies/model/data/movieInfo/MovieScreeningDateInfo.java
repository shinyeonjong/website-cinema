package com.allnightMovies.model.data.movieInfo;

import lombok.Data;

@Data
public class MovieScreeningDateInfo {
	private String screeningMinDate;
	private String screeningMaxDate;
	
	private String[] minScreening;
	private String[] maxScreening;
	
	private int calendarMonth;
	
	public void setScreeningDate() {
		this.screeningMinDate = this.screeningMinDate.split(" ")[0];
		this.screeningMaxDate = this.screeningMaxDate.split(" ")[0];
		System.out.println(this.screeningMinDate);
		System.out.println(this.screeningMaxDate);
		
		this.minScreening = this.screeningMinDate.split("\\.");
		this.maxScreening = this.screeningMaxDate.split("\\.");
		if(Integer.parseInt(this.minScreening[2]) > Integer.parseInt(this.maxScreening[2])) {
			if(calendarMonth < Integer.parseInt(this.maxScreening[1])) {
				this.maxScreening[2] = "31";
			} else {
				this.minScreening[2] = "1";
				
			}
		}
	}
}
