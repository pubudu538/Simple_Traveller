package com.pubci.simple_traveller;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	ImageButton addtrip, mytrips, searchtrip;
	TextView addtv, mytripstv, searchtv;
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
		addtv.setTypeface(font);
		mytripstv.setTypeface(font);
		searchtv.setTypeface(font);

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

		case R.id.addtripB:
			try {
				ourClass = Class.forName("com.pubci.simple_traveller."
						+ classes[0]);
				Intent ourIntent = new Intent(MainActivity.this, ourClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;

		case R.id.mytripsB:
			try {
				ourClass = Class.forName("com.pubci.simple_travller."
						+ classes[1]);
				Intent ourIntent = new Intent(MainActivity.this, ourClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;

		case R.id.searchtripB:
			try {
				ourClass = Class.forName("com.pubci.simple_travller."
						+ classes[2]);
				Intent ourIntent = new Intent(MainActivity.this, ourClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;
		
			
			

		}

	}
}
