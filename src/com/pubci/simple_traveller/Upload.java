package com.pubci.simple_traveller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.pubci.simple_traveller.placeinendpoint.Placeinendpoint;
import com.pubci.simple_traveller.placeinendpoint.model.PlaceIn;
import com.pubci.simple_traveller.tripinendpoint.Tripinendpoint;
import com.pubci.simple_traveller.tripinendpoint.model.TripIn;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Upload extends Activity implements OnClickListener {

	Button uploadB;
	ListView listView;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.upload);

		uploadB = (Button) findViewById(R.id.uploadButton);
		listView = (ListView) findViewById(R.id.uploadList);

		String[] titles = getTitles();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, titles);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listView.setAdapter(adapter);
		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/Jandaf.ttf");
		uploadB.setTypeface(font);

		uploadB.setOnClickListener(this);

	}

	public String[] getTitles() {

		STDatabase info = new STDatabase(this);
		info.open();
		String data = info.getTitles();
		info.close();

		String[] strs = data.split(System.getProperty("line.separator"));

		return strs;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		// check for internet connection before uploading
		ConnectionDetector connt = new ConnectionDetector(
				getApplicationContext());
		boolean haveInternet = connt.isConnectingToInternet();

		if (haveInternet == false) {
			Dialog d = new Dialog(this);
			d.setTitle("No Internet Connection!");
			TextView tv = new TextView(this);
			tv.setText("Please turn on your Internet Connection");
			d.setContentView(tv);
			d.show();
		} else {

			boolean worked = false;
			SparseBooleanArray checked = listView.getCheckedItemPositions();
			ArrayList<String> selectedItems = new ArrayList<String>();

			for (int i = 0; i < checked.size(); i++) {
				// Item position in adapter
				int position = checked.keyAt(i);
				// Add sport if it is checked i.e.) == TRUE!
				if (checked.valueAt(i)) {
					selectedItems.add(adapter.getItem(position));
				}
			}

			for (int i = 0; i < selectedItems.size(); i++) {

				int tripID = getTripId(selectedItems.get(i));
				String[] values = getTripRouteData(tripID);
				String key = getTripKey(values[0]);
				values[0] = values[0] + ",###," + key;

				new TripDataTask().execute(values);

				ArrayList<Marker> markerList = getMarkers(tripID);
				for (int j = 0; j < markerList.size(); j++) {
					String titleKey = markerList.get(j).getTitle() + "####"
							+ key;
					markerList.get(j).setTitle(titleKey);
				}

				new PlaceDataTask().execute(markerList);
				worked = true;
				listView.setItemChecked(checked.keyAt(i), false);
			}

			Dialog d = new Dialog(this);
			String resultTV = "Please select trip routes to upload!";
			if (worked) {
				resultTV = "Successfully Uploaded!";
			}
			d.setTitle(resultTV);
			d.show();
		}
	}

	private String getTripKey(String title) {
		// TODO Auto-generated method stub

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String currentDateandTime = sdf.format(new Date());
		String result = title + currentDateandTime;

		return result;
	}

	private class TripDataTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... str) {
			// TODO Auto-generated method stub

			String[] values = str[0].split(",###,");
			TripIn tripin = new TripIn();

			tripin.setTitle(values[0]);
			tripin.setLocation(str[1]);
			tripin.setDate(str[2]);
			tripin.setDays(str[3]);
			tripin.setTravel(str[4]);
			tripin.setTotalExp(str[5]);
			tripin.setTripKey(values[1]);

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

	private class PlaceDataTask extends
			AsyncTask<ArrayList<Marker>, Void, Void> {

		@Override
		protected Void doInBackground(ArrayList<Marker>... markerList) {
			// TODO Auto-generated method stub

			for (int i = 0; i < markerList[0].size(); i++) {

				PlaceIn placein = new PlaceIn();

				String values[] = markerList[0].get(i).getTitle().split("####");

				placein.setTitle(values[0]);
				placein.setDescription(markerList[0].get(i).getDescription());
				placein.setType(markerList[0].get(i).getType());
				placein.setLatitude(markerList[0].get(i).getPointLat());
				placein.setLongitude(markerList[0].get(i).getPointLong());
				placein.setTripKey(values[1]);

				Placeinendpoint.Builder builder = new Placeinendpoint.Builder(
						AndroidHttp.newCompatibleTransport(),
						new JacksonFactory(), null);

				builder = CloudEndpointUtils.updateBuilder(builder);

				Placeinendpoint endpoint = builder.build();

				try {
					endpoint.insertPlaceIn(placein).execute();
					// endpoint.insertCheckIn(checkin).execute();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return null;
		}

	}

	private void uploadPlaceData(int tripID) {

		ArrayList<Marker> markerList = getMarkers(tripID);

		for (int i = 0; i < markerList.size(); i++) {

			PlaceIn placein = new PlaceIn();

			placein.setTitle(markerList.get(i).getTitle());
			placein.setDescription(markerList.get(i).getDescription());
			placein.setType(markerList.get(i).getType());
			placein.setLatitude(markerList.get(i).getPointLat());
			placein.setLongitude(markerList.get(i).getPointLong());

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
		}
	}

	public ArrayList<Marker> getMarkers(int num) {

		ArrayList<Marker> list;
		STDatabase getMarkerEntries = new STDatabase(Upload.this);
		getMarkerEntries.open();
		list = getMarkerEntries.getPlacesById(num);
		getMarkerEntries.close();

		return list;
	}

	private void uploadTripData(String[] values) {

		TripIn tripin = new TripIn();

		tripin.setTitle(values[0]);
		tripin.setLocation(values[1]);
		tripin.setDate(values[2]);
		tripin.setDays(values[3]);
		tripin.setTravel(values[4]);
		tripin.setTotalExp(values[5]);

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
	}

	public int getTripId(String tit) {

		STDatabase db = new STDatabase(Upload.this);
		db.open();
		int id = db.getTripId(tit);
		db.close();

		return id;
	}

	public String[] getTripRouteData(int number) {

		STDatabase db = new STDatabase(Upload.this);
		db.open();
		String[] str = db.getTripInfoByID(number);
		db.close();

		return str;

	}
}
