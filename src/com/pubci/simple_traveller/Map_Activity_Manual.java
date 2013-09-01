package com.pubci.simple_traveller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class Map_Activity_Manual extends FragmentActivity implements
		OnClickListener, OnMapLongClickListener {

	private GoogleMap mMap;
	private int trip_id;

	Button search;
	EditText searchtext;
	final Context context = this;
	ArrayList<Marker> markerList = new ArrayList<Marker>();
	Marker marker;

	// private static final LatLng SRILANKA = new LatLng(7, 81);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity_manual);

		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());

		if (status != ConnectionResult.SUCCESS) { // Google Play Services are
													// not available

			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();

		} else {
			initialize();
			checkConnection();
			setCurrentLocation();
			mMap.setOnMapLongClickListener(this);
	
			
		}
		// search.setOnClickListener(this);
	}

	private void checkConnection() {

		boolean haveInternet;

		ConnectionDetector cont = new ConnectionDetector(
				getApplicationContext());
		haveInternet = cont.isConnectingToInternet();

		if (haveInternet == false) {
			Dialog d = new Dialog(this);
			d.setTitle("Internet Connection Unavailable");
			TextView tv = new TextView(this);
			tv.setText("Please turn on your Internet Connection to proceed!");
			d.setContentView(tv);
			d.show();
		}

	}

	private void setCurrentLocation() {

		// Getting LocationManager object from System Service LOCATION_SERVICE
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

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

	private void onLocationChanged(Location location) {

		double latitude = location.getLatitude();
		double longitude = location.getLongitude();

		// Creating a LatLng object for the current location
		LatLng latLng = new LatLng(latitude, longitude);

		// Showing the current location in Google Map
		mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

		// Zoom in the Google Map
		mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

	}

	private void initialize() {

		mMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		search = (Button) findViewById(R.id.searchLocationB);
		searchtext = (EditText) findViewById(R.id.searchET);

		mMap.setMyLocationEnabled(true); // enable my location layer on the map

		Bundle gotBasket = getIntent().getExtras();
		trip_id = gotBasket.getInt("trip");

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.searchLocationB:

			// Geocoder gc = new Geocoder(this);
			//
			// try {
			// List<Address> addresses = gc.getFromLocationName(
			// searchtext.toString(), 5);
			//
			// if (addresses.size() > 0) {
			//
			// LatLng point = new LatLng(0, 0);
			// // mMap.addMarker(new MarkerOptions().position(point).icon(
			// // BitmapDescriptorFactory
			// // .fromResource(R.drawable.ic_launcher)));
			//
			// CameraUpdate update = CameraUpdateFactory.newLatLngZoom(
			// point, 14);
			// mMap.animateCamera(update);
			//
			// // CameraUpdate update =
			// // CameraUpdateFactory.newLatLng(LOCATION_BURNABY);
			// // map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			// // CameraUpdate update =
			// // CameraUpdateFactory.newLatLngZoom(LOCATION_BURNABY, 9);
			// // map.animateCamera(update);
			// //
			//
			// }
			//
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			//
			// // mMap.addMarker(new MarkerOptions().position(point).icon(
			// // BitmapDescriptorFactory
			// // .fromResource(R.drawable.ic_launcher)));
			//
			// // List<Address> addresses =
			// //
			// geoCoder.getFromLocationName(txtsearch.getText().toString(),5);
			// //
			// // if(addresses.size() > 0)
			// // {
			// // p = new GeoPoint( (int) (addresses.get(0).getLatitude() *
			// 1E6),
			// // (int) (addresses.get(0).getLongitude() * 1E6));
			// //
			// // controller.animateTo(p);
			// // controller.setZoom(12);
			// //
			// // MapOverlay mapOverlay = new MapOverlay();
			// // List<Overlay> listOfOverlays = map.getOverlays();
			// // listOfOverlays.clear();
			// // listOfOverlays.add(mapOverlay);
			// //
			// // map.invalidate();
			// // txtsearch.setText("");
			// // }
			// // else
			// // {
			// // AlertDialog.Builder adb = new
			// // AlertDialog.Builder(GoogleMap.this);
			// // adb.setTitle("Google Map");
			// // adb.setMessage("Please Provide the Proper Place");
			// // adb.setPositiveButton("Close",null);
			// // adb.show();
			// // }

			break;
		}
	}

	@Override
	public void onMapLongClick(final LatLng point) {

		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View promptView = layoutInflater.inflate(R.layout.dialog, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		alertDialogBuilder.setView(promptView);
		final EditText dialogTitle = (EditText) promptView
				.findViewById(R.id.dTitleET);
		final EditText dialogDescription = (EditText) promptView
				.findViewById(R.id.dDescriptionET);
		final RadioButton rbFood = (RadioButton) promptView
				.findViewById(R.id.foodRB);
		final RadioButton rbStay = (RadioButton) promptView
				.findViewById(R.id.stayingRB);
		final RadioButton rbVisit = (RadioButton) promptView
				.findViewById(R.id.visitingRB);
		final RadioButton rbToilet = (RadioButton) promptView
				.findViewById(R.id.toiletRB);

		alertDialogBuilder
				.setNegativeButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						String title = dialogTitle.getText().toString();
						String description = dialogDescription.getText()
								.toString();
						int type = 0;

						BitmapDescriptor icon = null;

						if (rbFood.isChecked()) {
							icon = BitmapDescriptorFactory
									.fromResource(R.drawable.food);
							type = 1;
						} else if (rbStay.isChecked()) {
							icon = BitmapDescriptorFactory
									.fromResource(R.drawable.stay);
							type = 2;
						} else if (rbVisit.isChecked()) {

							icon = BitmapDescriptorFactory
									.fromResource(R.drawable.visit);
							type = 3;

						} else if (rbToilet.isChecked()) {
							icon = BitmapDescriptorFactory
									.fromResource(R.drawable.toilet);
							type = 4;
						}

						marker = new Marker(title, description, type,
								point.latitude, point.longitude);
						markerList.add(marker);

						addMarkerToDatabase(marker);

						mMap.addMarker(new MarkerOptions().position(point)
								.icon(icon).title(title).snippet(description)
								.draggable(false));

						dialog.dismiss();
					}

				})
				.setPositiveButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								dialog.cancel();
							}
						}).setCancelable(false).setTitle("Add A Place...");

		AlertDialog alertD = alertDialogBuilder.create();
		alertD.show();

	}

	public void addMarkerToDatabase(Marker marker) {

		try {

			STDatabase entry = new STDatabase(Map_Activity_Manual.this);
			entry.open();
			entry.createEntryPlaces(trip_id, marker.getTitle(),
					marker.getDescription(), marker.getType(),
					Double.toString(marker.getPointLat()),
					Double.toString(marker.getPointLong()));
			entry.close();

		} catch (Exception e) {

			Dialog d = new Dialog(this);
			d.setTitle("Unable to add the place");
			TextView tv = new TextView(this);
			tv.setText("Error");
			d.setContentView(tv);
			d.show();

		}
	}
	
	
	//*********************************************************************
	
	//******************************************************************
	
	

}
