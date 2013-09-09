package com.pubci.simple_traveller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.TextView;

public class MyTrips extends Activity implements OnItemClickListener {

	ListView listView;
	String[] titleList;
	int titleClicked;

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

	private String[] getTitles() {

		STDatabase info = new STDatabase(this);
		info.open();
		// String data = info.getDataofTrips();
		String data = info.getTitles();
		info.close();

		String[] strs = data.split(System.getProperty("line.separator"));
		// String[] strTitles = new String[strs.length];
		//
		// for (int i = 0; i < strs.length; i++) {
		// String[] strBlock = strs[i].split(" ");
		// strTitles[i] = strBlock[1];
		// }

		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/Jandaf.ttf");
		
		for(int i=0;i<strs.length;i++)
		{
			
		}
		
		return strs;
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case R.id.viewOption:

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
		case R.id.shareOption:

			// Dialog d = new Dialog(this);
			// d.setTitle("Share Options");
			// TextView tv = new TextView(this);
			// tv.setText("Not Yet Implemented!");
			// d.setContentView(tv);
			// d.show();
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

	}

}
