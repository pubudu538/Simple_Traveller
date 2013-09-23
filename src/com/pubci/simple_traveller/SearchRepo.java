package com.pubci.simple_traveller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.Gson;
import com.pubci.simple_traveller.placeinendpoint.model.PlaceIn;
import com.pubci.simple_traveller.tripinendpoint.Tripinendpoint;
import com.pubci.simple_traveller.tripinendpoint.model.CollectionResponseTripIn;
import com.pubci.simple_traveller.tripinendpoint.model.TripIn;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;

public class SearchRepo extends Fragment implements OnClickListener,
		OnItemClickListener {

	TextView searchTitle, emptyList;
	EditText searchPlaceET;
	ListView searchPlaceList;
	Button searchPlaceB;
	View rootView;
	ArrayAdapter<String> adapter;
	ArrayList<TripIn> savedTripList = new ArrayList<TripIn>();
	ArrayList<PlaceIn> savedMarkerList = new ArrayList<PlaceIn>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.searchrepo, container, false);

		initialization();
		searchPlaceB.setOnClickListener(this);
		searchPlaceList.setOnItemClickListener(this);
		return rootView;
	}

	private void initialization() {
		// TODO Auto-generated method stub
		searchTitle = (TextView) rootView.findViewById(R.id.searchTitleTV);
		searchPlaceET = (EditText) rootView.findViewById(R.id.searchPlaceET);
		searchPlaceList = (ListView) rootView.findViewById(R.id.searchedList);
		searchPlaceB = (Button) rootView.findViewById(R.id.searchPlaceB);
		emptyList = (TextView) rootView.findViewById(R.id.listemptyTV);

		Typeface font = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/Jandaf.ttf");
		searchTitle.setTypeface(font);
		searchPlaceB.setTypeface(font);
		emptyList.setTypeface(font);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		String searchText = searchPlaceET.getText().toString();

		if (searchText.equals("") == false) {

			ConnectionDetector connt = new ConnectionDetector(getActivity()
					.getApplicationContext());
			boolean haveInternet = connt.isConnectingToInternet();

			if (haveInternet == false) {

				Dialog d = new Dialog(getActivity());
				d.setTitle("No Internet Connection!");
				TextView tv = new TextView(getActivity());
				tv.setText("Please turn on your Internet Connection");
				d.setContentView(tv);
				d.show();
			} else {

				// new TripInTaskReceiverByName().execute(searchText);
				searchPlaceList.setVisibility(View.INVISIBLE);
				new TripInTaskReceiver().execute(searchText);
			}

		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
		// TODO Auto-generated method stub

		int position = arg2;

		TripIn tripin = savedTripList.get(position);

		String str = tripin.getTitle() + "###" + tripin.getLocation() + "###"
				+ tripin.getDate() + "###" + tripin.getDays() + "###"
				+ tripin.getTravel() + "###" + tripin.getTotalExp() + "###"
				+ tripin.getTripKey();

		Intent ip = new Intent(getActivity(), TripView.class);
		Bundle pack = new Bundle();
		pack.putString("tripin", str);
		ip.putExtras(pack);
		startActivity(ip);

	}

	private class TripInTaskReceiver extends
			AsyncTask<String, Integer, CollectionResponseTripIn> {

		ProgressDialog dialog;
		String locationNeeded;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			dialog = new ProgressDialog(getActivity());
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMax(100);
			dialog.setCancelable(false);
			dialog.setMessage("Searching...");
			dialog.show();

		}

		@Override
		protected CollectionResponseTripIn doInBackground(String... str) {
			// TODO Auto-generated method stub

			locationNeeded = str[0];

			Tripinendpoint.Builder endpointBuilder = new Tripinendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			endpointBuilder = CloudEndpointUtils.updateBuilder(endpointBuilder);

			CollectionResponseTripIn result;

			Tripinendpoint endpoint = endpointBuilder.build();

			try {

				result = endpoint.listTripIn().execute();

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

		@SuppressLint("DefaultLocale")
		@Override
		protected void onPostExecute(CollectionResponseTripIn result) {
			// TODO Auto-generated method stub

			if (result == null) {
				emptyList.setVisibility(View.VISIBLE);
			} else {

				savedTripList.clear();
				int listValue = 0;

				for (int i = 0; i < result.getItems().size(); i++) {

					if (listValue == 10) {
						break;
					} else {
						String loc = result.getItems().get(i).getLocation();

						if (loc != null) {

							Locale locale = Locale.getDefault();

							String lf = loc.toLowerCase(locale);
							String rf = locationNeeded.toLowerCase(locale);

							if (lf.equals(rf)) {
								savedTripList.add(result.getItems().get(i));
								listValue++;
							}
						}

					}

				}

				if (savedTripList.size() == 0) {
					emptyList.setVisibility(View.VISIBLE);
				} else {

					getShortList(savedTripList);

					String[] list = new String[savedTripList.size()];

					for (int j = 0; j < savedTripList.size(); j++) {
						list[j] = savedTripList.get(j).getTitle();
					}

					emptyList.setVisibility(View.INVISIBLE);
					adapter = new ArrayAdapter<String>(getActivity(),
							android.R.layout.simple_list_item_1, list);
					searchPlaceList.setAdapter(adapter);
					searchPlaceList.setVisibility(View.VISIBLE);
				}
			}

			dialog.dismiss();

		}

		private void getShortList(ArrayList<TripIn> sList) {

			ArrayList<TripIn> newList = new ArrayList<TripIn>();
			newList.add(sList.get(0));

			for (int i = 1; i < savedTripList.size(); i++) {

				boolean have = false;

				for (int j = 0; j < newList.size(); j++) {

					if (sList.get(i).getTitle()
							.equals(newList.get(j).getTitle())) {

						String olddate = sList.get(i).getDate();
						String newDate = newList.get(j).getDate();

						if (olddate != null && newDate != null) {
							if (olddate.equals(newDate) == false) {
								newList.add(sList.get(i));
								// have = true;
								break;
							} else {
								have = true;
							}
						}

					}
				}
				if (have == false) {
					newList.add(sList.get(i));
				}

			}

			savedTripList.clear();
			savedTripList.addAll(newList);

		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			dialog.incrementProgressBy(values[0]);
		}

	}

}
