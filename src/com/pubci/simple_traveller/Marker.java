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

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public int getType() {
		return type;

	}

	public double getPointLat() {
		return point_lat;
	}

	public double getPointLong() {
		return point_long;
	}
}
