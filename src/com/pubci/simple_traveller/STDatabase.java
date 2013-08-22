package com.pubci.simple_traveller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class STDatabase {

	public static final String KEY_TITLE = "title";
	public static final String KEY_LOCATION = "mainLocation";
	public static final String KEY_DATE = "date";
	public static final String KEY_DAYS = "days";
	public static final String KEY_TRAVELBY = "travellingBy";
	public static final String KEY_EXPENDITURE = "expenditure";

	private static final String DATABASE_NAME = "Simple_TravellerDB";
	private static final String DATABASE_TABLE_TRIP = "Trip_Data";
	private static final String DATABASE_TABLE_PLACES = "Places_Data";
	private static final String DATABASE_TABLE_TRIP_PLACES_DATA = "Trip_Places_Data";
	private static final int DATABASE_VERSION = 1;

	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);

		}

		// execute only once, then onUpgrade will call
		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL("CREATE TABLE " + DATABASE_TABLE_TRIP + " (" + KEY_TITLE
					+ " TEXT PRIMARY KEY NOT NULL, " + KEY_LOCATION
					+ " TEXT NOT NULL, " + KEY_DATE + " TEXT NOT NULL, "
					+ KEY_DAYS + " TEXT NOT NULL, " + KEY_TRAVELBY
					+ " TEXT NOT NULL, " + KEY_EXPENDITURE + " TEXT NOT NULL);");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_TRIP);
			onCreate(db);
		}

	}

	public STDatabase(Context c) {
		ourContext = c;
	}

	public STDatabase open() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();

		return this;
	}

	public void close() {
		ourHelper.close();
	}

	public long createEntry(String title, String location, String date,
			String days, String travel, String expenditure) {

		ContentValues cv = new ContentValues();
		cv.put(KEY_TITLE, title);
		cv.put(KEY_LOCATION, location);
		cv.put(KEY_DATE, date);
		cv.put(KEY_DAYS, days);
		cv.put(KEY_TRAVELBY, travel);
		cv.put(KEY_EXPENDITURE, expenditure);
		return ourDatabase.insert(DATABASE_TABLE_TRIP, null, cv);

	}

	public String getDataofTrips() {

		String[] columns = new String[] { KEY_TITLE, KEY_LOCATION, KEY_DATE,
				KEY_DAYS, KEY_TRAVELBY, KEY_EXPENDITURE };

		Cursor c = ourDatabase.query(DATABASE_TABLE_TRIP, columns, null, null,
				null, null, null);
		String result = "";

		int iTitle = c.getColumnIndex(KEY_TITLE);
		int iLocation = c.getColumnIndex(KEY_LOCATION);
		int iDate = c.getColumnIndex(KEY_DATE);
		int iDays = c.getColumnIndex(KEY_DAYS);
		int iTravel = c.getColumnIndex(KEY_TRAVELBY);
		int iExpenditure = c.getColumnIndex(KEY_EXPENDITURE);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iTitle) + " "
					+ c.getString(iLocation) + " " + c.getString(iDate) + " "
					+ c.getString(iDays) + " " + c.getString(iTravel) + " "
					+ c.getString(iExpenditure) + "\n";
		}

		return result;
	}

	public boolean checkTitleAvailability(String s) {

		String[] columns = new String[] { KEY_TITLE, KEY_LOCATION, KEY_DATE,
				KEY_DAYS, KEY_TRAVELBY, KEY_EXPENDITURE };
		Cursor c = ourDatabase.query(DATABASE_TABLE_TRIP, columns, KEY_TITLE
				+ "=" + s, null, null, null, null);
		
		c.moveToLast();
		int count=c.getCount();
		
		if(count==0)
		{
			return false;
		}
		
		return true;
		
	}

}
