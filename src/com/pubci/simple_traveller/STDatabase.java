package com.pubci.simple_traveller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class STDatabase {

	// attributes of Trip_Data table
	public static final String KEY_T_ROWID = "_id";
	public static final String KEY_T_TITLE = "title";
	public static final String KEY_T_LOCATION = "mainLocation";
	public static final String KEY_T_DATE = "date";
	public static final String KEY_T_DAYS = "days";
	public static final String KEY_T_TRAVELBY = "travellingBy";
	public static final String KEY_T_EXPENDITURE = "expenditure";

	// attributes of Places_Data table
	public static final String KEY_P_ROWID = "_id";
	public static final String KEY_P_TRIP_ID = "trip_id";
	public static final String KEY_P_TITLE = "title";
	public static final String KEY_P_DESCRIPTION = "description";
	public static final String KEY_P_TYPE = "type";
	public static final String KEY_P_LATITUDE = "latitude";
	public static final String KEY_P_LONGITUDE = "longitude";

	private static final String DATABASE_NAME = "Simple_TravellerDB";
	private static final String DATABASE_TABLE_TRIP = "Trip_Data";
	private static final String DATABASE_TABLE_PLACES = "Places_Data";

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

			db.execSQL("CREATE TABLE " + DATABASE_TABLE_TRIP + " ("
					+ KEY_T_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ KEY_T_TITLE + " TEXT NOT NULL, " + KEY_T_LOCATION
					+ " TEXT NOT NULL, " + KEY_T_DATE + " TEXT NOT NULL, "
					+ KEY_T_DAYS + " TEXT NOT NULL, " + KEY_T_TRAVELBY
					+ " TEXT NOT NULL, " + KEY_T_EXPENDITURE
					+ " TEXT NOT NULL);");

			db.execSQL("CREATE TABLE " + DATABASE_TABLE_PLACES + " ("
					+ KEY_P_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ KEY_P_TRIP_ID + " INTEGER NOT NULL, " + KEY_P_TITLE
					+ " TEXT NOT NULL, " + KEY_P_DESCRIPTION
					+ " TEXT NOT NULL, " + KEY_P_TYPE + " INTEGER NOT NULL, "
					+ KEY_P_LATITUDE + " TEXT NOT NULL, " + KEY_P_LONGITUDE
					+ " TEXT NOT NULL);");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_TRIP);
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_PLACES);
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

	public long createEntryTripData(String title, String location, String date,
			String days, String travel, String expenditure) {

		ContentValues cv = new ContentValues();
		cv.put(KEY_T_TITLE, title);
		cv.put(KEY_T_LOCATION, location);
		cv.put(KEY_T_DATE, date);
		cv.put(KEY_T_DAYS, days);
		cv.put(KEY_T_TRAVELBY, travel);
		cv.put(KEY_T_EXPENDITURE, expenditure);

		return ourDatabase.insert(DATABASE_TABLE_TRIP, null, cv);

	}

	public long createEntryPlaces(int tripId, String title, String description,
			int type, String latitude, String longitude) {

		ContentValues cv = new ContentValues();
		cv.put(KEY_P_TRIP_ID, tripId);
		cv.put(KEY_P_TITLE, title);
		cv.put(KEY_P_DESCRIPTION, description);
		cv.put(KEY_P_TYPE, type);
		cv.put(KEY_P_LATITUDE, latitude);
		cv.put(KEY_P_LONGITUDE, longitude);

		return ourDatabase.insert(DATABASE_TABLE_PLACES, null, cv);

	}

	public String getDataofTrips() throws SQLException {

		String[] columns = new String[] { KEY_T_ROWID, KEY_T_TITLE,
				KEY_T_LOCATION, KEY_T_DATE, KEY_T_DAYS, KEY_T_TRAVELBY,
				KEY_T_EXPENDITURE };

		Cursor c = ourDatabase.query(DATABASE_TABLE_TRIP, columns, null, null,
				null, null, null);
		String result = "";

		int iRowId = c.getColumnIndex(KEY_T_ROWID);
		int iTitle = c.getColumnIndex(KEY_T_TITLE);
		int iLocation = c.getColumnIndex(KEY_T_LOCATION);
		int iDate = c.getColumnIndex(KEY_T_DATE);
		int iDays = c.getColumnIndex(KEY_T_DAYS);
		int iTravel = c.getColumnIndex(KEY_T_TRAVELBY);
		int iExpenditure = c.getColumnIndex(KEY_T_EXPENDITURE);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iRowId) + " " + c.getString(iTitle)
					+ " " + c.getString(iLocation) + " " + c.getString(iDate)
					+ " " + c.getString(iDays) + " " + c.getString(iTravel)
					+ " " + c.getString(iExpenditure) + "\n";
		}

		return result;
	}

	public String getDataofPlaces() throws SQLException {

		String[] columns = new String[] { KEY_P_ROWID, KEY_P_TRIP_ID,
				KEY_P_TITLE, KEY_P_DESCRIPTION, KEY_P_TYPE, KEY_P_LATITUDE,
				KEY_P_LONGITUDE };

		Cursor c = ourDatabase.query(DATABASE_TABLE_PLACES, columns, null, null,
				null, null, null);
		String result = "";

		int iRowId = c.getColumnIndex(KEY_P_ROWID);
		int iTripId = c.getColumnIndex(KEY_P_TRIP_ID);
		int iTitle = c.getColumnIndex(KEY_P_TITLE);
		int iDescription = c.getColumnIndex(KEY_P_DESCRIPTION);
		int iType = c.getColumnIndex(KEY_P_TYPE);
		int iLatitude = c.getColumnIndex(KEY_P_LATITUDE);
		int iLongitude = c.getColumnIndex(KEY_P_LONGITUDE);
		

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iRowId) + " " + c.getString(iTripId)
					+ " " + c.getString(iTitle) + " " + c.getString(iDescription)
					+ " " + c.getString(iType) + " " + c.getString(iLatitude)
					+ " " + c.getString(iLongitude) + "\n";
		}

		return result;
	}

	public boolean checkTitleAvailability(String s) {

		String[] columns = new String[] { KEY_T_ROWID, KEY_T_TITLE,
				KEY_T_LOCATION, KEY_T_DATE, KEY_T_DAYS, KEY_T_TRAVELBY,
				KEY_T_EXPENDITURE };

		Cursor c = ourDatabase.query(DATABASE_TABLE_TRIP, columns, KEY_T_TITLE
				+ "=" + s, null, null, null, null);

		if (c != null) {

			return true;
		}

		return false;

	}

	public void updateEntryTripData(String title, String location, String date,
			String days, String travel, String expenditure) throws SQLException {

		Cursor c = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE_TRIP,
				null);
		c.moveToLast();

		int rows = Integer.parseInt(c.getString(0));

		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(KEY_T_TITLE, title);
		cvUpdate.put(KEY_T_LOCATION, location);
		cvUpdate.put(KEY_T_DATE, date);
		cvUpdate.put(KEY_T_DAYS, days);
		cvUpdate.put(KEY_T_TRAVELBY, travel);
		cvUpdate.put(KEY_T_EXPENDITURE, expenditure);
		ourDatabase.update(DATABASE_TABLE_TRIP, cvUpdate, KEY_T_ROWID + "="
				+ rows, null);

	}
	
	public int getTripId()
	{
		Cursor c = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE_TRIP,
				null);
		c.moveToLast();

		int rows = Integer.parseInt(c.getString(0));
		
		return rows;
	}

}
