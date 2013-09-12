package com.pubci.simple_traveller;

public class Trip {

	
	private String title,location,date,days,travel,totalExp;

	public Trip(String title,String location,String date,String days,String travel,String totalExp) {
		this.title = title;
		this.location = location;
		this.date = date;
		this.days = days;
		this.travel = travel;
		this.totalExp = totalExp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getTravel() {
		return travel;
	}

	public void setTravel(String travel) {
		this.travel = travel;
	}

	public String getTotalExp() {
		return totalExp;
	}

	public void setTotalExp(String totalExp) {
		this.totalExp = totalExp;
	}

	
}
