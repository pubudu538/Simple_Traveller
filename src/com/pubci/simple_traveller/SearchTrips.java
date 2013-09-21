package com.pubci.simple_traveller;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;

public class SearchTrips extends Activity {

	ActionBar.Tab Tab1, Tab2;
	Fragment fragmentTab1 = new SearchRepo();
	Fragment fragmentTab2 = new SearchWeb();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.searchtrips);

		ActionBar actionBar = getActionBar();

		// Hide Actionbar Icon
		actionBar.setDisplayShowHomeEnabled(false);

		// Hide Actionbar Title
		actionBar.setDisplayShowTitleEnabled(false);

		// Create Actionbar Tabs
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set Tab Icon and Titles
		Tab1 = actionBar.newTab().setText("Search Repo");
		Tab2 = actionBar.newTab().setText("Search Web");

		// Set Tab Listeners
		Tab1.setTabListener(new TabListener(fragmentTab1));
		Tab2.setTabListener(new TabListener(fragmentTab2));

		// Add tabs to actionbar
		actionBar.addTab(Tab1);
		actionBar.addTab(Tab2);

	}

}