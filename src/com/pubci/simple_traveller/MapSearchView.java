package com.pubci.simple_traveller;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MapSearchView extends FragmentActivity implements LocationListener {

	private GoogleMap mMap;
	TextView titleMapTV;
	ArrayList<Marker> markerList = new ArrayList<Marker>();
	ArrayList<String> sList = new ArrayList<String>();
	String mapTitle;
	LocationManager locationManager;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		setContentView(R.layout.map_activity_manual);
		initialize();
		getValues();

	}

	private void getValues() {

		for (int i = 0; i < sList.size(); i++) {
			String[] val = sList.get(i).split("###");
			Marker markerT = new Marker(val[0], val[1],
					Integer.parseInt(val[2]), Double.parseDouble(val[3]),
					Double.parseDouble(val[4]));
			markerList.add(markerT);
		}

		if (markerList.size() == 0) {
			setCurrentLocation();
			Toast.makeText(getApplicationContext(),
					"No Places Found for This Map!", Toast.LENGTH_LONG).show();
		} else {
			setMarkerLocation();
		}

	}

	private void setCurrentLocation() {
		// TODO Auto-generated method stub

		// Creating a criteria object to retrieve provider
		Criteria criteria = new Criteria();

		// Getting the name of the best provider
		String provider = locationManager.getBestProvider(criteria, true);

		// Getting Current Location
		Location location = locationManager.getLastKnownLocation(provider);

		if (location != null) {
			onLocationChanged(location);
		}

	}

	private void setMarkerLocation() {

		double latitude = markerList.get(0).getPointLat();
		double longitude = markerList.get(0).getPointLong();

		// Creating a LatLng object for the current location
		LatLng latLng = new LatLng(latitude, longitude);

		// Showing the current location in Google Map
		mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

		// Zoom in the Google Map
		mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

		BitmapDescriptor icon = null;

		for (int i = 0; i < markerList.size(); i++) {

			LatLng position = new LatLng(markerList.get(i).getPointLat(),
					markerList.get(i).getPointLong());

			if (markerList.get(i).getType() == 1) {
				icon = BitmapDescriptorFactory.fromResource(R.drawable.food2);
			} else if (markerList.get(i).getType() == 2) {
				icon = BitmapDescriptorFactory.fromResource(R.drawable.stay);
			} else if (markerList.get(i).getType() == 3) {
				icon = BitmapDescriptorFactory.fromResource(R.drawable.visit2);
			} else if (markerList.get(i).getType() == 4) {
				icon = BitmapDescriptorFactory.fromResource(R.drawable.toilet2);
			}

			String description = markerList.get(i).getDescription();

			if (description.equals("null")) {
				description = "N/A";
			}
			mMap.addMarker(new MarkerOptions().position(position).icon(icon)
					.title(markerList.get(i).getTitle()).snippet(description)
					.draggable(false));

		}

	}

	private void initialize() {

		Bundle gotB = getIntent().getExtras();
		sList = gotB.getStringArrayList("mark");
		mapTitle = gotB.getString("maptitle");

		mMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/saloonf.ttf");
		titleMapTV = (TextView) findViewById(R.id.titleMapTV);
		titleMapTV.setTypeface(font);
		titleMapTV.setText("   Map - " + mapTitle);

		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

	}

	@Override
	public void onLocationChanged(Location location) {

		double latitude = location.getLatitude();
		double longitude = location.getLongitude();

		// Creating a LatLng object for the current location
		LatLng latLng = new LatLng(latitude, longitude);

		// Showing the current location in Google Map
		mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

		// Zoom in the Google Map
		mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
