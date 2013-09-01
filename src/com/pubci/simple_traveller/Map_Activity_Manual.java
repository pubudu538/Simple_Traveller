package com.pubci.simple_traveller;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

public class Map_Activity_Manual extends FragmentActivity implements
		OnClickListener, OnMapLongClickListener, LocationListener {

	private GoogleMap mMap;
	private int trip_id;
	private int mapType;

	Button addLocation;
	TextView titleMapTV;
	EditText searchtext;
	final Context context = this;
	ArrayList<Marker> markerList = new ArrayList<Marker>();
	Marker marker;
	LocationManager locationManager;

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
			addLocation.setOnClickListener(this);

		}
		
	}

	public void GPSSetting() {

	}

	private void checkConnection() {

		boolean haveInternet;

		ConnectionDetector cont = new ConnectionDetector(
				getApplicationContext());
		haveInternet = cont.isConnectingToInternet();

		if (mapType == 1) {
			boolean isGPS = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			if (isGPS == false) {

				Dialog d = new Dialog(this);
				d.setTitle("GPS is Unavailable");
				TextView tv = new TextView(this);
				tv.setText("Please turn on GPS to proceed!");
				d.setContentView(tv);
				d.show();

			}

		} else {

			if (haveInternet == false) {
				Dialog d = new Dialog(this);
				d.setTitle("Internet Connection Unavailable");
				TextView tv = new TextView(this);
				tv.setText("Please turn on your Internet Connection to proceed!");
				d.setContentView(tv);
				d.show();
			}
		}

	}

	private void setCurrentLocation() {

		// Getting LocationManager object from System Service LOCATION_SERVICE
		// locationManager = (LocationManager)
		// getSystemService(LOCATION_SERVICE);

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

	private void initialize() {

		mMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		mMap.setMyLocationEnabled(true); // enable my location layer on the map

		Bundle gotBasket = getIntent().getExtras();
		trip_id = gotBasket.getInt("trip");

		Bundle gotBaskettype = getIntent().getExtras();
		mapType = gotBaskettype.getInt("type");
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/saloonf.ttf");
		titleMapTV = (TextView) findViewById(R.id.titleMapTV);
		titleMapTV.setTypeface(font);
		addLocation = (Button) findViewById(R.id.addLocationB);
		addLocation.setTypeface(font);

		if (mapType == 1) {
			addLocation.setVisibility(View.VISIBLE);
			titleMapTV.setText("    Using GPS ");

		} else {
			titleMapTV.setText("    Add Locations Manually!");
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.addLocationB:
			
			Location locat=mMap.getMyLocation();
			LatLng locationPoint=new LatLng(locat.getLatitude(),locat.getLongitude());
			
			onMapLongClick(locationPoint);
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

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
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
