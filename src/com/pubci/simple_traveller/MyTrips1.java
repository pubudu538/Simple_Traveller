package com.pubci.simple_traveller;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

public class MyTrips1 extends ListActivity {

	String classes[] = { "hi", "man" };
	ListView listview;
	ArrayAdapter<String> myAdapter;
	PopupMenu popup;
	private final static int ONE = 1;
	private final static int TWO = 2;
	private final static int THREE = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		String[] titlesOfTrips = getTitles();

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, getTitles()));

	

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		

		// Dialog d = new Dialog(this);
		// d.setTitle("Location is Unavailable");
		// TextView tv = new TextView(this);
		// tv.setText("Please wait until the location is available!");
		// d.setContentView(tv);
		// d.show();

	}

	private String[] getTitles() {

		STDatabase info = new STDatabase(this);
		info.open();
		String data = info.getDataofTrips();
		info.close();

		String[] strs = data.split(System.getProperty("line.separator"));
		String[] strTitles = new String[strs.length];

		for (int i = 0; i < strs.length; i++) {
			String[] strBlock = strs[i].split(" ");
			strTitles[i] = (i + 1) + ". " + strBlock[1];
		}

		return strTitles;
	}

}
