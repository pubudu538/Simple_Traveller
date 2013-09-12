package com.pubci.simple_traveller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddTrip extends Activity implements OnClickListener {

	TextView addtripHeadingTV, titleTV, locationTV, dateTV, daysTV, travelbyTV,
			totalExpTV, addPlacesTV;
	EditText titleET, locationET, dateET, daysET, travelbyET, totalExpET;
	Button saveUpdateB, manualB, GpsB;
	ImageView down1, down2;
	boolean myTripsOn = false;
	String tripStatus;
	int tripID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.addtrip);
		initialize();
		setFont();

		saveUpdateB.setOnClickListener(this);
		manualB.setOnClickListener(this);
		GpsB.setOnClickListener(this);
	}

	// setting the custom fonts
	private void setFont() {

		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/Jandaf.ttf");
		Typeface ft = Typeface.createFromAsset(getAssets(), "fonts/eras.TTF");

		addtripHeadingTV.setTypeface(font);
		titleTV.setTypeface(font);
		locationTV.setTypeface(font);
		dateTV.setTypeface(font);
		daysTV.setTypeface(font);
		travelbyTV.setTypeface(font);
		totalExpTV.setTypeface(font);
		addPlacesTV.setTypeface(font);

		titleET.setTypeface(ft);
		locationET.setTypeface(ft);
		dateET.setTypeface(ft);
		daysET.setTypeface(ft);
		travelbyET.setTypeface(ft);
		totalExpET.setTypeface(ft);
		
		saveUpdateB.setTypeface(font);
		manualB.setTypeface(font);
		GpsB.setTypeface(font);

	}

	// Basic initialization
	private void initialize() {

		addtripHeadingTV = (TextView) findViewById(R.id.addtripheading);
		titleTV = (TextView) findViewById(R.id.titleTV);
		locationTV = (TextView) findViewById(R.id.mainlocationTV);
		dateTV = (TextView) findViewById(R.id.dateofthetripTV);
		daysTV = (TextView) findViewById(R.id.daysspendTV);
		travelbyTV = (TextView) findViewById(R.id.travellingTV);
		totalExpTV = (TextView) findViewById(R.id.expendiitureTV);
		addPlacesTV = (TextView) findViewById(R.id.addplacesTV);

		titleET = (EditText) findViewById(R.id.titleET);
		locationET = (EditText) findViewById(R.id.mainlocationET);
		dateET = (EditText) findViewById(R.id.dateET);
		daysET = (EditText) findViewById(R.id.daysspendET);
		travelbyET = (EditText) findViewById(R.id.travellingET);
		totalExpET = (EditText) findViewById(R.id.expenditureET);

		saveUpdateB = (Button) findViewById(R.id.saveB);
		manualB = (Button) findViewById(R.id.manualB);
		GpsB = (Button) findViewById(R.id.GpsB);
		down1 = (ImageView) findViewById(R.id.downIV1);
		down2 = (ImageView) findViewById(R.id.downIV2);

		Bundle gotBasket = getIntent().getExtras();
		tripStatus = gotBasket.getString("mytrips");

		if (tripStatus.equals("mytripsstatusoff") == false) {
			myTripsOn = true;
			myTripSetting();
		}
	}

	private void myTripSetting() {
		// TODO Auto-generated method stub

		addtripHeadingTV.setText("Trip Route");
		titleET.setText(tripStatus);
		tripID = getTripId(tripStatus);
		String[] data = getTripRouteData(tripID);

		locationET.setText(data[1]);
		dateET.setText(data[2]);
		daysET.setText(data[3]);
		travelbyET.setText(data[4]);
		totalExpET.setText(data[5]);

		setFocus(false);

		manualB.setVisibility(View.VISIBLE);
		GpsB.setVisibility(View.VISIBLE);
		addPlacesTV.setVisibility(View.VISIBLE);
		down1.setVisibility(View.VISIBLE);
		down2.setVisibility(View.VISIBLE);

		saveUpdateB.setText("Edit Data");

	}

	public String[] getTripRouteData(int number) {

		STDatabase db = new STDatabase(AddTrip.this);
		db.open();
		String[] str = db.getTripInfoByID(number);
		db.close();

		return str;

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.saveB:

			String title = titleET.getText().toString();

			if (title.equals("")) { // checks for valid title
				Dialog d = new Dialog(this);
				d.setTitle("Invalid Title!");
				TextView tv = new TextView(this);
				tv.setText(" Please type a valid text for the Title");
				d.setContentView(tv);
				d.show();

				// Saving data for the first time
			} else if (saveUpdateB.getText().equals("Save Data") == true) {

				saveUpdateB.setText("Update Data");
				manualB.setVisibility(View.VISIBLE);
				GpsB.setVisibility(View.VISIBLE);
				addPlacesTV.setVisibility(View.VISIBLE);

				boolean didwork = true;

				// Inserting data to the database
				try {

					String location = locationET.getText().toString();
					String date = dateET.getText().toString();
					String days = daysET.getText().toString();
					String travel = travelbyET.getText().toString();
					String expenditure = totalExpET.getText().toString();

					STDatabase entry = new STDatabase(AddTrip.this);
					entry.open();
					entry.createEntryTripData(title, location, date, days,
							travel, expenditure);
					entry.close();
				} catch (Exception e) {

					didwork = false;
					Dialog d = new Dialog(this);
					d.setTitle("ERRORS");
					TextView tv = new TextView(this);
					tv.setText(" err");
					d.setContentView(tv);
					d.show();

				} finally {
					if (didwork) { // message will pop-up if data is added
						Dialog d = new Dialog(this);
						d.setTitle("Trip Route Added!");
						TextView tv = new TextView(this);
						tv.setText(" Now You can add places to the Map manually or using GPS ");
						d.setContentView(tv);
						d.show();
						down1.setVisibility(View.VISIBLE);
						down2.setVisibility(View.VISIBLE);
						tripID = getTripId(title);

					}
				}

			} else if (saveUpdateB.getText().equals("Update Data") == true) { // update
																				// the
																				// data

				boolean didWork = true;

				// access the database to update the previously stored data
				try {

					String location = locationET.getText().toString();
					String date = dateET.getText().toString();
					String days = daysET.getText().toString();
					String travel = travelbyET.getText().toString();
					String expenditure = totalExpET.getText().toString();

					STDatabase updateEntry = new STDatabase(this);
					updateEntry.open();
					updateEntry.updateEntryTripData(tripID, title, location,
							date, days, travel, expenditure);
					updateEntry.close();

					if (myTripsOn == true) {
						saveUpdateB.setText("Edit Data");
						setFocus(false);
					}

				} catch (Exception e) {

					didWork = false;
					String error = e.toString();
					Dialog d = new Dialog(this);
					d.setTitle("Dang It");
					TextView tv = new TextView(this);
					tv.setText(error);
					d.setContentView(tv);
					d.show();
				} finally {
					if (didWork) { // message will pop-up if data is updated
						Dialog d = new Dialog(this);
						d.setTitle("Data Successfully Updated!");
						TextView tv = new TextView(this);
						tv.setText(" Now You can add places to the Map manually or using GPS ");
						d.setContentView(tv);
						d.show();

					}
				}

			} else if (saveUpdateB.getText().equals("Edit Data") == true) {

				setFocus(true);
				saveUpdateB.setText("Update Data");

			}
			break;

		case R.id.manualB:

			int tripIdforPass = tripID; // get trip_id from the database
			Bundle backpackTrip = new Bundle();
			Bundle backpackType = new Bundle();
			Bundle backpackStatus = new Bundle();
			backpackTrip.putInt("trip", tripIdforPass); // adding trip_id to the
														// bundle, store places
														// with trip_id
			backpackType.putInt("type", 0); // adding type of the map to the
											// bundle , 0 for manual

			if (myTripsOn == true) {
				backpackStatus.putInt("status", 1); // If myTripsOn for 1

			} else {
				backpackStatus.putInt("status", 0); // If myTripsOn for 0
			}

			Intent manual = new Intent(AddTrip.this, Map_Activity_Manual.class);
			manual.putExtras(backpackTrip); // adding bundle to the intent
			manual.putExtras(backpackType); // adding bundle to the intent
			manual.putExtras(backpackStatus);
			startActivity(manual); // start the new intent

			break;

		case R.id.GpsB:

			int tripId_gps = tripID; // get trip_id from the database
			Bundle packtrip = new Bundle();
			Bundle packtype = new Bundle();
			Bundle packStatus = new Bundle();
			packtrip.putInt("trip", tripId_gps); // adding trip_id to the bundle
													// , store places with
													// trip_id
			packtype.putInt("type", 1); // adding type of the map to the bundle
										// , 1 for GPS

			if (myTripsOn == true) {
				packStatus.putInt("status", 1); // If myTripsOn for 1

			} else {
				packStatus.putInt("status", 0); // If myTripsOn for 0
			}

			Intent gps = new Intent(AddTrip.this, Map_Activity_Manual.class);
			gps.putExtras(packtrip); // adding bundle to the intent
			gps.putExtras(packtype); // adding bundle to the intent
			gps.putExtras(packStatus);
			startActivity(gps); // start the new intent

			// Intent i = new Intent("com.pubci.simple_traveller.SQLVIEW");
			// startActivity(i);

			break;

		}
	}

	private void setFocus(boolean f) {

		if (f == false) {
			titleET.setFocusable(f);
			locationET.setFocusable(f);
			dateET.setFocusable(f);
			daysET.setFocusable(f);
			travelbyET.setFocusable(f);
			totalExpET.setFocusable(f);
		} else {
			titleET.setFocusableInTouchMode(true);
			locationET.setFocusableInTouchMode(true);
			dateET.setFocusableInTouchMode(true);
			daysET.setFocusableInTouchMode(true);
			travelbyET.setFocusableInTouchMode(true);
			totalExpET.setFocusableInTouchMode(true);

		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		if (myTripsOn == false) {
			finish();
		}

	}

	// get the trip_id from the database
	public int getTripId(String tit) {

		STDatabase db = new STDatabase(AddTrip.this);
		db.open();
		int id = db.getTripId(tit);
		db.close();

		return id;
	}
	
	

}
