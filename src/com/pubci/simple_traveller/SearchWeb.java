package com.pubci.simple_traveller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Typeface;

public class SearchWeb extends Fragment implements OnClickListener {

	WebView browser;
	Button searchB;
	EditText searchwebET;
	TextView searchTitle;
	final String weburl = "http://wikitravel.org/en/";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.searchweb, container, false);

		browser = (WebView) rootView.findViewById(R.id.wvBrowser);
		browser.setWebViewClient(new WebClient());
		browser.getSettings().setJavaScriptEnabled(true);
		browser.getSettings().setLoadWithOverviewMode(true);
		browser.getSettings().setUseWideViewPort(true);

		searchB = (Button) rootView.findViewById(R.id.searchwebB);
		searchwebET = (EditText) rootView.findViewById(R.id.searchwebET);
		searchTitle = (TextView) rootView.findViewById(R.id.searchwebTitle);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/Jandaf.ttf");

		searchTitle.setTypeface(font);
		searchB.setTypeface(font);

		// browser.loadUrl("");
		searchB.setOnClickListener(this);

		return rootView;

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		String place = searchwebET.getText().toString();

		if (place.equals("")) {
			Dialog d = new Dialog(getActivity());
			d.setTitle("Please enter a valid Input");
			d.show();
		} else {

			boolean internet = checkInternetConn();

			if (internet == false) {
				Dialog d = new Dialog(getActivity());
				d.setTitle("Internet Connection Unavailable");
				TextView tv = new TextView(getActivity()
						.getApplicationContext());
				tv.setText("Please turn on your Internet Connection to proceed!");
				d.setContentView(tv);
				d.show();
			} else {

				browser.loadUrl(weburl + place);
				Toast.makeText(getActivity().getApplicationContext(),
						"Please wait until page loads..", Toast.LENGTH_LONG)
						.show();

			}
		}
	}

	private boolean checkInternetConn() {

		ConnectionDetector cont = new ConnectionDetector(getActivity()
				.getApplicationContext());
		boolean haveInternet = cont.isConnectingToInternet();

		return haveInternet;
	}

}
