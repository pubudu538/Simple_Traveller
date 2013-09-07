package com.pubci.simple_traveller;

import android.app.Activity;
import android.app.Dialog;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.TextView;

public class SqlView extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlview);
		
		TextView tv=(TextView)findViewById(R.id.tvSQLInfo);
		
		try{
			STDatabase info =new STDatabase(this);
			info.open();
			String data=info.getDataofTrips();
			info.close();
			
			tv.setText(data);
		}
		catch(SQLException e)
		{
			Dialog d = new Dialog(this);
			d.setTitle("ERRORS");
			TextView tvError = new TextView(this);
			tvError.setText(e.toString());
			d.setContentView(tv);
			d.show();
		}
		
	}
	

}
