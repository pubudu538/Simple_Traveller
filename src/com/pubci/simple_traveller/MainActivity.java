package com.pubci.simple_traveller;

import java.io.IOException;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.pubci.simple_traveller.placeinendpoint.Placeinendpoint;
import com.pubci.simple_traveller.placeinendpoint.model.PlaceIn;
import com.pubci.simple_traveller.tripinendpoint.Tripinendpoint;
import com.pubci.simple_traveller.tripinendpoint.model.TripIn;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	ImageButton addtrip, mytrips, searchtrip, uploadTrip;
	TextView addtv, mytripstv, searchtv, uploadtv;
	String classes[] = { "AddTrip", "MyTrips", "SearchTrips" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// new TripInTask().execute();
		// new PlaceInTask().execute();

		setContentView(R.layout.activity_main);
		initialize();
		addtrip.setOnClickListener(this);
		mytrips.setOnClickListener(this);
		searchtrip.setOnClickListener(this);
		uploadTrip.setOnClickListener(this);

	}

	// Basic initialization and font setup
	private void initialize() {
		// TODO Auto-generated method stub
		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/saloonf.ttf");
		addtrip = (ImageButton) (findViewById(R.id.addtripB));
		mytrips = (ImageButton) (findViewById(R.id.mytripsB));
		searchtrip = (ImageButton) (findViewById(R.id.searchtripB));
		uploadTrip = (ImageButton) (findViewById(R.id.uploadB));
		addtv = (TextView) (findViewById(R.id.addtripTV));
		mytripstv = (TextView) (findViewById(R.id.mytripsTV));
		searchtv = (TextView) (findViewById(R.id.searchtripTV));
		uploadtv = (TextView) findViewById(R.id.uploadTV);
		addtv.setTypeface(font);
		mytripstv.setTypeface(font);
		searchtv.setTypeface(font);
		uploadtv.setTypeface(font);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		Class ourClass;

		switch (v.getId()) {

		// setting up new intents for other activities
		case R.id.addtripB:
			try {
				ourClass = Class.forName("com.pubci.simple_traveller."
						+ classes[0]);

				Bundle backpacktrip = new Bundle();
				backpacktrip.putString("mytrips", "mytripsstatusoff");
				Intent ourIntent = new Intent(MainActivity.this, ourClass);
				ourIntent.putExtras(backpacktrip);
				startActivity(ourIntent);

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;

		case R.id.mytripsB:
			try {
				ourClass = Class.forName("com.pubci.simple_traveller."
						+ classes[1]);
				Intent ourIntent = new Intent(MainActivity.this, ourClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;

		case R.id.searchtripB:
			try {
				ourClass = Class.forName("com.pubci.simple_traveller."
						+ classes[2]);
				Intent ourIntent = new Intent(MainActivity.this, ourClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			// Intent i = new Intent("com.pubci.simple_traveller.SQLVIEW");
			// startActivity(i);
			break;
		case R.id.uploadB:

			Intent ip = new Intent(MainActivity.this, Upload.class);
			startActivity(ip);

			break;
		}

	}

	private class TripInTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub

			TripIn tripin = new TripIn();

			tripin.setTitle("app run - title");
			tripin.setLocation("loc working");

			Tripinendpoint.Builder builder = new Tripinendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			builder = CloudEndpointUtils.updateBuilder(builder);

			Tripinendpoint endpoint = builder.build();

			try {
				endpoint.insertTripIn(tripin).execute();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

	}

	private class PlaceInTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			PlaceIn placein = new PlaceIn();

			Double lat = 9.666666;
			placein.setTitle("its working");
			placein.setLatitude(lat);

			Placeinendpoint.Builder builder = new Placeinendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			builder = CloudEndpointUtils.updateBuilder(builder);

			Placeinendpoint endpoint = builder.build();

			try {
				endpoint.insertPlaceIn(placein).execute();
				// endpoint.insertCheckIn(checkin).execute();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

	}

}
