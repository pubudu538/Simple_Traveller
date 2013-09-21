package com.pubci.simple_traveller;

public class Marker {

	private String title;
	private String description;
	private int type;
	private double point_lat, point_long;

	public Marker(String title, String description, int type, double plat,
			double plong) {
		this.title = title;
		this.description = description;
		this.type = type;
		point_lat = plat;
		point_long = plong;
	}

	public void setMakerLatitude(double lat) // set the latitude
	{
		point_lat = lat;
	}

	public void setMakerLongitude(double longitude) // set the longitude
	{
		point_long = longitude;
	}

	public String getTitle() { // return the title of the marker
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() { // return the description of the marker
		return description;
	}

	public int getType() { // return the category of the marker
		return type;

	}

	public double getPointLat() { // return the latitude
		return point_lat;
	}

	public double getPointLong() { // return the longitude
		return point_long;
	}
}
