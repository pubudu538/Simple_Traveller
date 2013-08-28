package com.pubci.simple_traveller;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map_Activity_Manual extends FragmentActivity implements
		OnClickListener {

	private GoogleMap mMap;
	Button search;
	EditText searchtext;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity_manual);

		initialize();
		setCurrentLocation();
		search.setOnClickListener(this);
	}

	private void setCurrentLocation() {
		// TODO Auto-generated method stub

		// mMap.
	}

	private void initialize() {
		// TODO Auto-generated method stub
		mMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		search = (Button) findViewById(R.id.searchLocationB);
		searchtext = (EditText) findViewById(R.id.searchET);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.searchLocationB:

			Geocoder gc = new Geocoder(this);
			
			try {
				List<Address> addresses = gc.getFromLocationName(
						searchtext.toString(), 5);

				if (addresses.size() > 0) {
					
					LatLng point = new LatLng(addresses.get(0).getLatitude(),
							addresses.get(0).getLongitude());
//					mMap.addMarker(new MarkerOptions().position(point).icon(
//							BitmapDescriptorFactory
//									.fromResource(R.drawable.ic_launcher)));
					
					CameraUpdate update=CameraUpdateFactory.newLatLngZoom(point, 14);
					mMap.animateCamera(update);
					
					
//					CameraUpdate update = CameraUpdateFactory.newLatLng(LOCATION_BURNABY);
//					map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//					CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_BURNABY, 9);
//					map.animateCamera(update);
//					
					
					
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// mMap.addMarker(new MarkerOptions().position(point).icon(
			// BitmapDescriptorFactory
			// .fromResource(R.drawable.ic_launcher)));

			// List<Address> addresses =
			// geoCoder.getFromLocationName(txtsearch.getText().toString(),5);
			//
			// if(addresses.size() > 0)
			// {
			// p = new GeoPoint( (int) (addresses.get(0).getLatitude() * 1E6),
			// (int) (addresses.get(0).getLongitude() * 1E6));
			//
			// controller.animateTo(p);
			// controller.setZoom(12);
			//
			// MapOverlay mapOverlay = new MapOverlay();
			// List<Overlay> listOfOverlays = map.getOverlays();
			// listOfOverlays.clear();
			// listOfOverlays.add(mapOverlay);
			//
			// map.invalidate();
			// txtsearch.setText("");
			// }
			// else
			// {
			// AlertDialog.Builder adb = new
			// AlertDialog.Builder(GoogleMap.this);
			// adb.setTitle("Google Map");
			// adb.setMessage("Please Provide the Proper Place");
			// adb.setPositiveButton("Close",null);
			// adb.show();
			// }

			break;
		}
	}
}
