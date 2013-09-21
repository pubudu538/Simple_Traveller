package com.pubci.simple_traveller;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MyTripsNew2 extends ListActivity {

	String[] Fruits = { "Apple", "Banana", "Orange", "Mango", "Grapes",
			"Jack Fruit", "Strawberry", "cucumber", "pumpkin" };
	Button uploadB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// setContentView(R.layout.mytripsnew2);
		ListView lstView = getListView();// getListView();
		// lstView.setChoiceMode(0); // CHOICE_MODE_NONE
		// lstView.setChoiceMode(1); //CHOICE_MODE_SINGLE
		lstView.setChoiceMode(2); // CHOICE_MODE_MULTIPLE
		lstView.setTextFilterEnabled(true);
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_checked, getTitles()));
		// note : You will get pop-up menu when type R . layout .
		// Then select your option. It will display items with different
		// customization

		uploadB = new Button(this);
		uploadB.setText("Upload");
		uploadB.setGravity(Gravity.CENTER);
		lstView.addFooterView(uploadB);
	}

	public String[] getTitles() {

		STDatabase info = new STDatabase(this);
		info.open();
		String data = info.getTitles();
		info.close();

		String[] strs = data.split(System.getProperty("line.separator"));

		return strs;
	}

}
