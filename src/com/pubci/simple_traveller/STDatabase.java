package com.pubci.simple_traveller;

/* Simple Traveller
 * @author Pubudu Gunatilaka
 * @version 1.0
 *   */

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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

	// Inner class, DB helper
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

	// open the database to read/write
	public STDatabase open() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();

		return this;
	}

	// close the database after read/write
	public void close() {
		ourHelper.close();
	}

	// insert trip data on the database TripData
	public long createEntryTripData(Trip trip) {

		ContentValues cv = new ContentValues();
		cv.put(KEY_T_TITLE, trip.getTitle());
		cv.put(KEY_T_LOCATION, trip.getLocation());
		cv.put(KEY_T_DATE, trip.getDate());
		cv.put(KEY_T_DAYS, trip.getDays());
		cv.put(KEY_T_TRAVELBY, trip.getTravel());
		cv.put(KEY_T_EXPENDITURE, trip.getTotalExp());

		return ourDatabase.insert(DATABASE_TABLE_TRIP, null, cv);

	}

	// insert marker data to the database Places
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

	// get data of the trips completely
	public String getDataofTrips() throws SQLException {

		String[] columns = new String[] { KEY_T_ROWID, KEY_T_TITLE,
				KEY_T_LOCATION, KEY_T_DATE, KEY_T_DAYS, KEY_T_TRAVELBY,
				KEY_T_EXPENDITURE };

		Cursor c = ourDatabase.query(DATABASE_TABLE_TRIP, columns, null, null,
				null, null, null);
		String result = "";

		if (c != null) {

			int iRowId = c.getColumnIndex(KEY_T_ROWID);
			int iTitle = c.getColumnIndex(KEY_T_TITLE);
			int iLocation = c.getColumnIndex(KEY_T_LOCATION);
			int iDate = c.getColumnIndex(KEY_T_DATE);
			int iDays = c.getColumnIndex(KEY_T_DAYS);
			int iTravel = c.getColumnIndex(KEY_T_TRAVELBY);
			int iExpenditure = c.getColumnIndex(KEY_T_EXPENDITURE);

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				result = result + c.getString(iRowId) + " "
						+ c.getString(iTitle) + " " + c.getString(iLocation)
						+ " " + c.getString(iDate) + " " + c.getString(iDays)
						+ " " + c.getString(iTravel) + " "
						+ c.getString(iExpenditure) + "\n";
			}

			return result;
		}
		return null;
	}

	// get data of the places completely
	public String getDataofPlaces() throws SQLException {

		String[] columns = new String[] { KEY_P_ROWID, KEY_P_TRIP_ID,
				KEY_P_TITLE, KEY_P_DESCRIPTION, KEY_P_TYPE, KEY_P_LATITUDE,
				KEY_P_LONGITUDE };

		Cursor c = ourDatabase.query(DATABASE_TABLE_PLACES, columns, null,
				null, null, null, null);
		String result = "";

		if (c != null) {

			int iRowId = c.getColumnIndex(KEY_P_ROWID);
			int iTripId = c.getColumnIndex(KEY_P_TRIP_ID);
			int iTitle = c.getColumnIndex(KEY_P_TITLE);
			int iDescription = c.getColumnIndex(KEY_P_DESCRIPTION);
			int iType = c.getColumnIndex(KEY_P_TYPE);
			int iLatitude = c.getColumnIndex(KEY_P_LATITUDE);
			int iLongitude = c.getColumnIndex(KEY_P_LONGITUDE);

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				result = result + c.getString(iRowId) + " "
						+ c.getString(iTripId) + " " + c.getString(iTitle)
						+ " " + c.getString(iDescription) + " "
						+ c.getString(iType) + " " + c.getString(iLatitude)
						+ " " + c.getString(iLongitude) + "\n";
			}

			return result;
		}
		return null;
	}

	// check for availability of the title
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

	// update trip data entry in the database
	public void updateEntryTripData(int id, Trip trip) throws SQLException {

		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(KEY_T_TITLE, trip.getTitle());
		cvUpdate.put(KEY_T_LOCATION, trip.getLocation());
		cvUpdate.put(KEY_T_DATE, trip.getDate());
		cvUpdate.put(KEY_T_DAYS, trip.getDays());
		cvUpdate.put(KEY_T_TRAVELBY, trip.getTravel());
		cvUpdate.put(KEY_T_EXPENDITURE, trip.getTotalExp());
		ourDatabase.update(DATABASE_TABLE_TRIP, cvUpdate, KEY_T_ROWID + "="
				+ id, null);

	}

	// update places data entry in the database
	public void updatePlaceData(int id, double lat, double lon)
			throws SQLException {

		ContentValues cvUpdate = new ContentValues();

		cvUpdate.put(KEY_P_LATITUDE, lat);
		cvUpdate.put(KEY_P_LONGITUDE, lon);

		ourDatabase.update(DATABASE_TABLE_PLACES, cvUpdate, KEY_P_ROWID + "="
				+ id, null);

	}

	// get the places rowid from the db
	public int getPlacesRowID(double latitude, double longitude)
			throws SQLException {

		String lat = Double.toString(latitude);
		String lon = Double.toString(longitude);

		Cursor c = ourDatabase.rawQuery("SELECT " + KEY_P_ROWID + " FROM "
				+ DATABASE_TABLE_PLACES + " WHERE " + KEY_P_LATITUDE + " =? "
				+ "and " + KEY_P_LONGITUDE + " =? ", new String[] { lat, lon });

		if (c != null) {
			c.moveToFirst();
			int irows = c.getColumnIndex(KEY_P_ROWID);

			int row = Integer.parseInt(c.getString(irows));

			return row;
		}

		return 0;

	}

	// get trip id from the db
	public int getTripId(String title) throws SQLException {

		Cursor c = ourDatabase.rawQuery("SELECT " + KEY_T_ROWID + " FROM "
				+ DATABASE_TABLE_TRIP + " WHERE " + KEY_T_TITLE + " =? ",
				new String[] { title });

		if (c != null) {

			c.moveToFirst();
			int irows = c.getColumnIndex(KEY_T_ROWID);

			int rows = Integer.parseInt(c.getString(irows));

			return rows;
		}
		return 0;

	}

	// get only the data of a trip route completely as a string array
	public String[] getTripInfoByID(int num) throws SQLException {

		Cursor c = ourDatabase.rawQuery("SELECT " + KEY_T_TITLE + ","
				+ KEY_T_LOCATION + "," + KEY_T_DATE + "," + KEY_T_DAYS + ","
				+ KEY_T_TRAVELBY + "," + KEY_T_EXPENDITURE + " FROM "
				+ DATABASE_TABLE_TRIP + " WHERE " + KEY_T_ROWID + " = " + num,
				null);

		if (c != null) {
			c.moveToFirst();

			int iTitle = c.getColumnIndex(KEY_T_TITLE);
			int iLocation = c.getColumnIndex(KEY_T_LOCATION);
			int iDate = c.getColumnIndex(KEY_T_DATE);
			int iDays = c.getColumnIndex(KEY_T_DAYS);
			int iTravel = c.getColumnIndex(KEY_T_TRAVELBY);
			int iExpenditure = c.getColumnIndex(KEY_T_EXPENDITURE);

			// String result = c.getString(iTitle) + "," +
			// c.getString(iLocation)
			// + "," + c.getString(iDate) + "," + c.getString(iDays) + ","
			// + c.getString(iTravel) + "," + c.getString(iExpenditure);

			String[] results = new String[6];

			results[0] = c.getString(iTitle);
			results[1] = c.getString(iLocation);
			results[2] = c.getString(iDate);
			results[3] = c.getString(iDays);
			results[4] = c.getString(iTravel);
			results[5] = c.getString(iExpenditure);

			return results;
		}

		return null;
	}

	// get only the place from the id
	public ArrayList<Marker> getPlacesById(int num) throws SQLException {

		ArrayList<Marker> markers = new ArrayList<Marker>();

		Cursor c = ourDatabase.rawQuery("SELECT " + KEY_P_TITLE + ","
				+ KEY_P_DESCRIPTION + "," + KEY_P_TYPE + "," + KEY_P_LATITUDE
				+ "," + KEY_P_LONGITUDE + " FROM " + DATABASE_TABLE_PLACES
				+ " WHERE " + KEY_P_TRIP_ID + " = " + num, null);

		// int iRowId = c.getColumnIndex(KEY_P_ROWID);
		// int iTripId = c.getColumnIndex(KEY_P_TRIP_ID);
		int iTitle = c.getColumnIndex(KEY_P_TITLE);
		int iDescription = c.getColumnIndex(KEY_P_DESCRIPTION);
		int iType = c.getColumnIndex(KEY_P_TYPE);
		int iLatitude = c.getColumnIndex(KEY_P_LATITUDE);
		int iLongitude = c.getColumnIndex(KEY_P_LONGITUDE);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

			String title = c.getString(iTitle);
			String des = c.getString(iDescription);
			String type = c.getString(iType);
			String latitude = c.getString(iLatitude);
			String longitude = c.getString(iLongitude);

			Marker marker = new Marker(title, des, Integer.parseInt(type),
					Double.parseDouble(latitude), Double.parseDouble(longitude));

			markers.add(marker);

			// result = result + c.getString(iRowId) + " " +
			// c.getString(iTripId)
			// + " " + c.getString(iTitle) + " "
			// + c.getString(iDescription) + " " + c.getString(iType)
			// + " " + c.getString(iLatitude) + " "
			// + c.getString(iLongitude) + "\n";
		}

		return markers;

	}

	// delete place entry from the db
	public void deletePlaceEntry(double latitude, double longitude) {

		// ourDatabase.delete(DATABASE_TABLE_TRIP, KEY_T_TITLE + "=", null);

		String lat = Double.toString(latitude);
		String lon = Double.toString(longitude);
		ourDatabase
				.delete(DATABASE_TABLE_PLACES, KEY_P_LATITUDE + " =? "
						+ " and " + KEY_P_LONGITUDE + " =? ", new String[] {
						lat, lon });
	}

	// delete trip entry from the db
	public void deleteTripEntry(String title) {

		ourDatabase.delete(DATABASE_TABLE_TRIP, KEY_T_TITLE + "=?",
				new String[] { title });

	}

	// get all the titles from the db
	public String getTitles() throws SQLException {

		Cursor c = ourDatabase.rawQuery("SELECT " + KEY_P_TITLE + " FROM "
				+ DATABASE_TABLE_TRIP, null);
		String result = "";
		int iTitle = c.getColumnIndex(KEY_T_TITLE);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iTitle) + "\n";
		}

		return result;
	}

}
