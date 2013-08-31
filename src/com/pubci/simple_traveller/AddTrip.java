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

	private void setFont() {

		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/Jandaf.ttf");
		Typeface ft = Typeface.createFromAsset(getAssets(), "fonts/ants.TTF");

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

	}

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
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.saveB:

			String title = titleET.getText().toString();

			if (title.equals("")) {
				Dialog d = new Dialog(this);
				d.setTitle("Invalid Title!");
				TextView tv = new TextView(this);
				tv.setText(" Please type a valid text for the Title");
				d.setContentView(tv);
				d.show();

			} else if (saveUpdateB.getText().equals("Save Data") == true) {

				saveUpdateB.setText("Update Data");
				manualB.setVisibility(View.VISIBLE);
				GpsB.setVisibility(View.VISIBLE);
				addPlacesTV.setVisibility(View.VISIBLE);

				boolean didwork = true;

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
					if (didwork) {
						Dialog d = new Dialog(this);
						d.setTitle("Trip Route Added!");
						TextView tv = new TextView(this);
						tv.setText(" Now You can add places to the Map manually or using GPS ");
						d.setContentView(tv);
						d.show();
						down1.setVisibility(View.VISIBLE);
						down2.setVisibility(View.VISIBLE);
					}
				}

			} else if (saveUpdateB.getText().equals("Update Data") == true) {

				boolean didWork = true;

				try {

					String location = locationET.getText().toString();
					String date = dateET.getText().toString();
					String days = daysET.getText().toString();
					String travel = travelbyET.getText().toString();
					String expenditure = totalExpET.getText().toString();

					STDatabase updateEntry = new STDatabase(this);
					updateEntry.open();
					updateEntry.updateEntryTripData(title, location, date,
							days, travel, expenditure);
					updateEntry.close();

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
					if (didWork) {
						Dialog d = new Dialog(this);
						d.setTitle("Data Successfully Updated!");
						TextView tv = new TextView(this);
						tv.setText(" Now You can add places to the Map manually or using GPS ");
						d.setContentView(tv);
						d.show();
					}
				}

			}

			break;
		case R.id.manualB:

			int tripId = getTripId();
			Bundle backpack = new Bundle();
			backpack.putInt("trip", tripId);
			Intent manual = new Intent(AddTrip.this, Map_Activity_Manual.class);
			manual.putExtras(backpack);
			startActivity(manual);

			break;

		case R.id.GpsB:

			Intent i = new Intent("com.pubci.simple_traveller.SQLVIEW");
			startActivity(i);

			break;

		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

	private int getTripId() {

		STDatabase db = new STDatabase(AddTrip.this);
		db.open();
		int id = db.getTripId();
		db.close();

		return id;
	}

}
