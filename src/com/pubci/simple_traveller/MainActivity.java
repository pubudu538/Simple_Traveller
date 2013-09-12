package com.pubci.simple_traveller;

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

	ImageButton addtrip, mytrips, searchtrip;
	TextView addtv, mytripstv, searchtv, uploadtv;
	String classes[] = { "AddTrip", "MyTrips", "SearchTrips" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initialize();
		addtrip.setOnClickListener(this);
		mytrips.setOnClickListener(this);
		searchtrip.setOnClickListener(this);

	}

	// Basic initialization and font setup
	private void initialize() {
		// TODO Auto-generated method stub
		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/saloonf.ttf");
		addtrip = (ImageButton) (findViewById(R.id.addtripB));
		mytrips = (ImageButton) (findViewById(R.id.mytripsB));
		searchtrip = (ImageButton) (findViewById(R.id.searchtripB));
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
			// try {
			// ourClass = Class.forName("com.pubci.simple_travller."
			// + classes[2]);
			// Intent ourIntent = new Intent(MainActivity.this, ourClass);
			// startActivity(ourIntent);
			//
			//
			// } catch (ClassNotFoundException e) {
			// e.printStackTrace();
			// }

			// Intent i = new Intent("com.pubci.simple_traveller.SQLVIEW");
			// startActivity(i);
			break;
		case R.id.uploadB:

			break;
		}

	}

}
