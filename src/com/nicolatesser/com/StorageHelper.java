package com.nicolatesser.com;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

public class StorageHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 2;

	private static final String CHECK_INS_OUTS_TABLE_NAME = "check_ins_outs";
	public static final String ID = "_id";
	public static final String LOCATION_NAME = "location";
	public static final String TIME = "time";
	public static final String CHECK_TYPE = "check_type";
	public static final String MATCH_TYPE = "match_type";

	private static final String CHECK_INS_OUTS_TABLE_CREATE = "CREATE TABLE "
			+ CHECK_INS_OUTS_TABLE_NAME + " (" + ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + LOCATION_NAME
			+ " TEXT, " + TIME + " INTEGER, " + CHECK_TYPE + " INTEGER, "
			+ MATCH_TYPE + " TEXT);";

	private static final String DATABASE_NAME = "home_work_time_tracker";

	public StorageHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
		
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CHECK_INS_OUTS_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// db.execSQL(DICTIONARY_TABLE_CREATE);
		// do nothing
	}

	/**
	 * Add a word to the dictionary.
	 * 
	 * @return rowId or -1 if failed
	 */
	public long addCheckInOut(String name, String check_type, String match_type) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(LOCATION_NAME, name);
		Calendar calendar = Calendar.getInstance();
		initialValues.put(TIME, calendar.getTime().getTime());
		initialValues.put(CHECK_TYPE, check_type);
		initialValues.put(MATCH_TYPE, match_type);

		SQLiteDatabase db = getReadableDatabase();
		return db.insert(CHECK_INS_OUTS_TABLE_NAME, null, initialValues);
	}

	public Cursor getCheckInsOuts() {
		Cursor cursor = query(null, null, new String[] { ID, LOCATION_NAME,
				TIME, CHECK_TYPE, MATCH_TYPE }, TIME + " ASC", null);

		return cursor;

	}

	public TargetLocationMatch getLastCheckInOut() {
		Cursor cursor = query(null, null, new String[] { ID, LOCATION_NAME,
				TIME, CHECK_TYPE, MATCH_TYPE }, TIME + " ASC", "1");

		if (cursor == null) {
			// There are no results
			return null;
		} else {
			// Display the number of results

			cursor.moveToFirst();

			String locationName = cursor.getString(cursor
					.getColumnIndex(LOCATION_NAME));
			String matchType = cursor.getString(cursor
					.getColumnIndex(MATCH_TYPE));
			String checkType = cursor.getString(cursor
					.getColumnIndex(CHECK_TYPE));

			if (checkType.equals(TimeTrackerService.CHECK_IN)) {
				TargetLocation targetLocation = new TargetLocation(
						locationName, null, null);
				TargetLocationMatch targetLocationMatch = new TargetLocationMatch(
						targetLocation, matchType);
				return targetLocationMatch;
			} else {
				return null;
			}

		}

	}

	/**
	 * Performs a database query.
	 * 
	 * @param selection
	 *            The selection clause
	 * @param selectionArgs
	 *            Selection arguments for "?" components in the selection
	 * @param columns
	 *            The columns to return
	 * @return A Cursor over all rows matching the query
	 */
	private Cursor query(String selection, String[] selectionArgs,
			String[] columns, String sort, String limit) {
		/*
		 * The SQLiteBuilder provides a map for all possible columns requested
		 * to actual columns in the database, creating a simple column alias
		 * mechanism by which the ContentProvider does not need to know the real
		 * column names
		 */
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(CHECK_INS_OUTS_TABLE_NAME);
		// builder.setProjectionMap(mColumnMap);

		Cursor cursor = builder.query(getReadableDatabase(), columns,
				selection, selectionArgs, null, null, sort, limit);

		if (cursor == null) {
			return null;
		} else if (!cursor.moveToFirst()) {
			cursor.close();
			return null;
		}
		return cursor;
	}

}
