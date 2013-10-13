package com.pubci.simple_traveller;

/* Simple Traveller
 * @author Pubudu Gunatilaka
 * @version 1.0
 *   */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.pubci.simple_traveller.placeinendpoint.Placeinendpoint;
import com.pubci.simple_traveller.placeinendpoint.model.CollectionResponsePlaceIn;
import com.pubci.simple_traveller.placeinendpoint.model.PlaceIn;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TripView extends Activity implements OnClickListener {

	TextView addtripHeadingTV, titleTV, locationTV, dateTV, daysTV, travelbyTV,
			totalExpTV;
	EditText titleET, locationET, dateET, daysET, travelbyET, totalExpET;
	Button gotoMapB;
	Trip trip;
	String[] values;
	ArrayList<String> markerList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.addtrip);
		initialize();
		setFocus();
		setFont();
		setValues();
		gotoMapB.setOnClickListener(this);
	}

	private void setValues() {

		titleET.setText(trip.getTitle());
		locationET.setText(trip.getLocation());

		String date = trip.getDate();

		if (date.equals("null")) {
			dateET.setText(" - ");
		} else {
			dateET.setText(date);
		}

		String days = trip.getDays();

		if (days.equals("null")) {
			daysET.setText(" - ");
		} else {
			daysET.setText(days);
		}

		String travel = trip.getTravel();

		if (travel.equals("null")) {
			travelbyET.setText(" - ");
		} else {
			travelbyET.setText(travel);
		}

		String total = trip.getTotalExp();

		if (total.equals("null")) {
			totalExpET.setText(" - ");
		} else {
			totalExpET.setText(total);
		}

	}

	private void setFocus() {

		titleET.setFocusable(false);
		locationET.setFocusable(false);
		dateET.setFocusable(false);
		daysET.setFocusable(false);
		travelbyET.setFocusable(false);
		totalExpET.setFocusable(false);

	}

	private void initialize() {

		addtripHeadingTV = (TextView) findViewById(R.id.addtripheading);
		titleTV = (TextView) findViewById(R.id.titleTV);
		locationTV = (TextView) findViewById(R.id.mainlocationTV);
		dateTV = (TextView) findViewById(R.id.dateofthetripTV);
		daysTV = (TextView) findViewById(R.id.daysspendTV);
		travelbyTV = (TextView) findViewById(R.id.travellingTV);
		totalExpTV = (TextView) findViewById(R.id.expendiitureTV);

		titleET = (EditText) findViewById(R.id.titleET);
		locationET = (EditText) findViewById(R.id.mainlocationET);
		dateET = (EditText) findViewById(R.id.dateET);
		daysET = (EditText) findViewById(R.id.daysspendET);
		travelbyET = (EditText) findViewById(R.id.travellingET);
		totalExpET = (EditText) findViewById(R.id.expenditureET);

		gotoMapB = (Button) findViewById(R.id.saveB);
		gotoMapB.setText("Go to Map");

		Bundle gotBasket = getIntent().getExtras();
		String str = gotBasket.getString("tripin");
		values = str.split("###");

		trip = new Trip(values[0], values[1], values[2], values[3], values[4],
				values[5]);

	}

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

		titleET.setTypeface(ft);
		locationET.setTypeface(ft);
		dateET.setTypeface(ft);
		daysET.setTypeface(ft);
		travelbyET.setTypeface(ft);
		totalExpET.setTypeface(ft);

		gotoMapB.setTypeface(font);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.saveB:

			String tripkey = values[6];
			new PlaceinGetTask().execute(tripkey);

			break;
		}
	}

	private class PlaceinGetTask extends
			AsyncTask<String, Integer, CollectionResponsePlaceIn> {

		ProgressDialog dialog;
		String key;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			dialog = new ProgressDialog(TripView.this);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMax(100);
			dialog.setCancelable(false);
			dialog.setMessage("Getting Required Data..");
			dialog.show();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			dialog.incrementProgressBy(values[0]);
		}

		@Override
		protected CollectionResponsePlaceIn doInBackground(String... params) {
			// TODO Auto-generated method stub

			key = params[0];

			Placeinendpoint.Builder endpointBuilder = new Placeinendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			endpointBuilder = CloudEndpointUtils.updateBuilder(endpointBuilder);

			CollectionResponsePlaceIn result;

			Placeinendpoint endpoint = endpointBuilder.build();

			try {

				result = endpoint.listPlaceIn().execute();

				for (int i = 0; i < 20; i++) {
					publishProgress(5);
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = null;
			}

			return result;
		}

		@Override
		protected void onPostExecute(CollectionResponsePlaceIn result) {
			// TODO Auto-generated method stub

			if (result != null) {
				for (int i = 0; i < result.getItems().size(); i++) {
					String tempK = result.getItems().get(i).getTripKey();
					if (key.equals(tempK)) {
						getValues(result.getItems().get(i));
					}
				}
			}

			dialog.dismiss();

			Intent ip = new Intent(TripView.this, MapSearchView.class);
			Bundle bPack = new Bundle();
			bPack.putStringArrayList("mark", markerList);
			bPack.putString("maptitle", trip.getTitle());
			ip.putExtras(bPack);
			startActivity(ip);

		}

		private void getValues(PlaceIn p) {
			// TODO Auto-generated method stub

			String st = p.getTitle() + "###" + p.getDescription() + "###"
					+ p.getType().toString() + "###"
					+ p.getLatitude().toString() + "###"
					+ p.getLongitude().toString();

			markerList.add(st);
		}

	}
}
