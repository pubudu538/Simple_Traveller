package com.pubci.simple_traveller;

/* Simple Traveller
 * @author Pubudu Gunatilaka
 * @version 1.0
 *   */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyTrips extends Activity implements OnItemClickListener {

	ListView listView;
	String[] titleList;
	int titleClicked;
	boolean pauseAtTrip = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mytrips);

		listView = (ListView) findViewById(R.id.myTripList);
		titleList = getTitles();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				titleList);

		// Assign adapter to ListView
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(this);

		registerForContextMenu(listView);

	}

	// get titles of the routes from the database
	public String[] getTitles() {

		STDatabase info = new STDatabase(this);
		info.open();
		String data = info.getTitles();
		info.close();

		String[] strs = data.split(System.getProperty("line.separator"));

		return strs;
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// context menu for list items when they are clicked

		switch (item.getItemId()) {
		case R.id.viewOption:

			pauseAtTrip = true;
			Bundle backpacktrip = new Bundle();
			backpacktrip.putString("mytrips", titleList[titleClicked]);
			Intent trip = new Intent(MyTrips.this, AddTrip.class);
			trip.putExtras(backpacktrip); // adding bundle to the intent
			startActivity(trip);

			break;

		case R.id.deleteOption:

			STDatabase info = new STDatabase(this);
			info.open();
			String deleteTitle = titleList[titleClicked];
			info.deleteTripEntry(deleteTitle);
			info.close();

			Intent intent = new Intent(MyTrips.this, MyTrips.class);
			startActivity(intent);

			break;

		}

		return true;

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mytrip_menu, menu);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
		// TODO Auto-generated method stub

		titleClicked = position;
		openContextMenu(v);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		if (pauseAtTrip == false) {
			finish();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		pauseAtTrip = false;
	}

}
